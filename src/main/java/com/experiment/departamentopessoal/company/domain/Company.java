package com.experiment.departamentopessoal.company.domain;

import com.experiment.departamentopessoal.shared.domain.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Table;

@Entity
@Table(name = "company", indexes = {
        @Index(name = "idx_company_name", columnList = "name")
})
public class Company extends BaseEntity {

    @Column(name = "name", nullable = false, length = 150)
    private String name;

    @Column(name = "document", length = 20)
    private String document;

    @Column(name = "active", nullable = false)
    private boolean active = true;

    protected Company() {
    }

    public Company(String name, String document, boolean active) {
        this.name = name;
        this.document = document;
        this.active = active;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
