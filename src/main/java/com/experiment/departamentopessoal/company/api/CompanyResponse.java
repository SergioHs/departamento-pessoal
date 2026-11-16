package com.experiment.departamentopessoal.company.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.Instant;
import java.util.UUID;

public class CompanyResponse {

    private final UUID id;
    private final String name;
    private final String document;
    private final boolean active;
    private final Instant createdAt;
    private final Instant updatedAt;
    private final long version;

    public CompanyResponse(UUID id, String name, String document, boolean active, Instant createdAt, Instant updatedAt, long version) {
        this.id = id;
        this.name = name;
        this.document = document;
        this.active = active;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.version = version;
    }

    public UUID id() {
        return id;
    }

    @JsonProperty("id")
    public UUID getId() {
        return id;
    }

    public String name() {
        return name;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    public String document() {
        return document;
    }

    @JsonProperty("document")
    public String getDocument() {
        return document;
    }

    public boolean active() {
        return active;
    }

    @JsonProperty("active")
    public boolean isActive() {
        return active;
    }

    public Instant createdAt() {
        return createdAt;
    }

    @JsonProperty("createdAt")
    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant updatedAt() {
        return updatedAt;
    }

    @JsonProperty("updatedAt")
    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public long version() {
        return version;
    }

    @JsonProperty("version")
    public long getVersion() {
        return version;
    }
}
