package com.experiment.departamentopessoal.auth.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import java.util.UUID;

public class AuthUserResponse {

    private final UUID id;
    private final String email;
    private final UUID companyId;
    private final List<String> roles;
    private final boolean active;

    public AuthUserResponse(UUID id, String email, UUID companyId, List<String> roles, boolean active) {
        this.id = id;
        this.email = email;
        this.companyId = companyId;
        this.roles = List.copyOf(roles);
        this.active = active;
    }

    public UUID id() {
        return id;
    }

    @JsonProperty("id")
    public UUID getId() {
        return id;
    }

    public String email() {
        return email;
    }

    @JsonProperty("email")
    public String getEmail() {
        return email;
    }

    public UUID companyId() {
        return companyId;
    }

    @JsonProperty("companyId")
    public UUID getCompanyId() {
        return companyId;
    }

    public List<String> roles() {
        return roles;
    }

    @JsonProperty("roles")
    public List<String> getRoles() {
        return roles;
    }

    public boolean active() {
        return active;
    }

    @JsonProperty("active")
    public boolean isActive() {
        return active;
    }
}
