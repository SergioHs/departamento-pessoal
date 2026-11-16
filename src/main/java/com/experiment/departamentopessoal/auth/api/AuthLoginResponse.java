package com.experiment.departamentopessoal.auth.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.Instant;

public class AuthLoginResponse {

    private final String tokenType;
    private final String accessToken;
    private final Instant expiresAt;
    private final AuthUserResponse user;

    public AuthLoginResponse(String tokenType, String accessToken, Instant expiresAt, AuthUserResponse user) {
        this.tokenType = tokenType;
        this.accessToken = accessToken;
        this.expiresAt = expiresAt;
        this.user = user;
    }

    public String tokenType() {
        return tokenType;
    }

    @JsonProperty("tokenType")
    public String getTokenType() {
        return tokenType;
    }

    public String accessToken() {
        return accessToken;
    }

    @JsonProperty("accessToken")
    public String getAccessToken() {
        return accessToken;
    }

    public Instant expiresAt() {
        return expiresAt;
    }

    @JsonProperty("expiresAt")
    public Instant getExpiresAt() {
        return expiresAt;
    }

    public AuthUserResponse user() {
        return user;
    }

    @JsonProperty("user")
    public AuthUserResponse getUser() {
        return user;
    }
}
