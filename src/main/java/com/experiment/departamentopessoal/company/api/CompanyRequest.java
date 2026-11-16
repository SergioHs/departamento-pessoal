package com.experiment.departamentopessoal.company.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CompanyRequest {

    @NotBlank
    @Size(max = 150)
    private final String name;

    @Size(max = 20)
    private final String document;

    private final Boolean active;

    @JsonCreator
    public CompanyRequest(@JsonProperty("name") String name,
                          @JsonProperty("document") String document,
                          @JsonProperty("active") Boolean active) {
        this.name = name;
        this.document = document;
        this.active = active;
    }

    public String name() {
        return name;
    }

    public String document() {
        return document;
    }

    public Boolean active() {
        return active;
    }
}
