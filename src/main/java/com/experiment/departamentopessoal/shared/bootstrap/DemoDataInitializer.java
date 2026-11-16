package com.experiment.departamentopessoal.shared.bootstrap;

import com.experiment.departamentopessoal.auth.domain.UserAccount;
import com.experiment.departamentopessoal.auth.domain.UserAccountRepository;
import com.experiment.departamentopessoal.auth.domain.UserRole;
import com.experiment.departamentopessoal.company.domain.Company;
import com.experiment.departamentopessoal.company.domain.CompanyRepository;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@ConditionalOnProperty(name = "app.bootstrap-demo-data", havingValue = "true")
public class DemoDataInitializer implements ApplicationRunner {

    private final CompanyRepository companyRepository;
    private final UserAccountRepository userAccountRepository;
    private final PasswordEncoder passwordEncoder;

    public DemoDataInitializer(CompanyRepository companyRepository,
                               UserAccountRepository userAccountRepository,
                               PasswordEncoder passwordEncoder) {
        this.companyRepository = companyRepository;
        this.userAccountRepository = userAccountRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(org.springframework.boot.ApplicationArguments args) {
        if (userAccountRepository.count() > 0) {
            return;
        }
        Company company = companyRepository.save(new Company("Empresa Demo", null, true));
        UserAccount user = new UserAccount(company, "admin@local", passwordEncoder.encode("admin123"), true, Set.of(UserRole.ADMIN));
        userAccountRepository.save(user);
    }
}
