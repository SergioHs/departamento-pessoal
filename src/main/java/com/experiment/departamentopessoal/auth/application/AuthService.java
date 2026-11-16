package com.experiment.departamentopessoal.auth.application;

import com.experiment.departamentopessoal.auth.api.AuthLoginRequest;
import com.experiment.departamentopessoal.auth.api.AuthLoginResponse;
import com.experiment.departamentopessoal.auth.api.AuthUserResponse;
import com.experiment.departamentopessoal.auth.domain.UserAccount;
import com.experiment.departamentopessoal.auth.domain.UserAccountRepository;
import com.experiment.departamentopessoal.auth.domain.UserRole;
import com.experiment.departamentopessoal.shared.config.JwtProperties;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.servlet.http.HttpServletRequest;
import java.time.Instant;
import java.util.List;

@Service
public class AuthService {

    private final UserAccountRepository userAccountRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtEncoder jwtEncoder;
    private final JwtProperties jwtProperties;
    private final LoginRateLimiter loginRateLimiter;

    public AuthService(UserAccountRepository userAccountRepository,
                       PasswordEncoder passwordEncoder,
                       JwtEncoder jwtEncoder,
                       JwtProperties jwtProperties,
                       LoginRateLimiter loginRateLimiter) {
        this.userAccountRepository = userAccountRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtEncoder = jwtEncoder;
        this.jwtProperties = jwtProperties;
        this.loginRateLimiter = loginRateLimiter;
    }

    @Transactional(readOnly = true)
    public AuthLoginResponse login(AuthLoginRequest request, String remoteAddress) {
        String rateLimitKey = request.email().toLowerCase() + "|" + remoteAddress;
        loginRateLimiter.check(rateLimitKey);

        UserAccount user = userAccountRepository.findByEmailIgnoreCase(request.email())
                .orElseThrow(InvalidCredentialsException::new);

        if (!user.isActive() || !passwordEncoder.matches(request.password(), user.getPasswordHash())) {
            loginRateLimiter.registerFailure(rateLimitKey);
            throw new InvalidCredentialsException();
        }

        loginRateLimiter.reset(rateLimitKey);

        Instant now = Instant.now();
        Instant expiresAt = now.plus(jwtProperties.getTokenValidity());
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .subject(user.getEmail())
                .issuer(jwtProperties.getIssuer())
                .issuedAt(now)
                .expiresAt(expiresAt)
                .claim("companyId", user.getCompany().getId().toString())
                .claim("roles", user.getRoles().stream().map(UserRole::name).toList())
                .claim("tokenVersion", user.getTokenVersion())
                .build();

        String token = jwtEncoder.encode(JwtEncoderParameters.from(
                JwsHeader.with(MacAlgorithm.HS256).type("JWT").build(),
                claims
        )).getTokenValue();

        return new AuthLoginResponse("Bearer", token, expiresAt, toUserResponse(user));
    }

    @Transactional(readOnly = true)
    public AuthUserResponse me(String email) {
        UserAccount user = userAccountRepository.findByEmailIgnoreCase(email)
                .orElseThrow(InvalidCredentialsException::new);
        return toUserResponse(user);
    }

    private AuthUserResponse toUserResponse(UserAccount user) {
        return new AuthUserResponse(
                user.getId(),
                user.getEmail(),
                user.getCompany().getId(),
                user.getRoles().stream().map(Enum::name).toList(),
                user.isActive()
        );
    }
}
