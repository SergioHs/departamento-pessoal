package com.experiment.departamentopessoal.auth.domain;

import com.experiment.departamentopessoal.company.domain.Company;
import com.experiment.departamentopessoal.shared.domain.BaseEntity;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "user_account", indexes = {
        @Index(name = "idx_user_account_email", columnList = "email"),
        @Index(name = "idx_user_account_company_id", columnList = "company_id")
})
public class UserAccount extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "company_id", nullable = false, foreignKey = @ForeignKey(name = "fk_user_account_company"))
    private Company company;

    @Column(name = "email", nullable = false, unique = true, length = 180)
    private String email;

    @Column(name = "password_hash", nullable = false, length = 100)
    private String passwordHash;

    @Column(name = "active", nullable = false)
    private boolean active = true;

    @Column(name = "token_version", nullable = false)
    private long tokenVersion = 0L;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "user_account_role", joinColumns = @JoinColumn(name = "user_account_id"))
    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false, length = 30)
    private Set<UserRole> roles = new LinkedHashSet<>();

    protected UserAccount() {
    }

    public UserAccount(Company company, String email, String passwordHash, boolean active, Set<UserRole> roles) {
        this.company = company;
        this.email = email;
        this.passwordHash = passwordHash;
        this.active = active;
        this.roles = new LinkedHashSet<>(roles);
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public long getTokenVersion() {
        return tokenVersion;
    }

    public void setTokenVersion(long tokenVersion) {
        this.tokenVersion = tokenVersion;
    }

    public Set<UserRole> getRoles() {
        return Set.copyOf(roles);
    }

    public void setRoles(Set<UserRole> roles) {
        this.roles = new LinkedHashSet<>(roles);
    }
}
