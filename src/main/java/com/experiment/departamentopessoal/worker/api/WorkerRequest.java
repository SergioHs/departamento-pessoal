package com.experiment.departamentopessoal.worker.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public class WorkerRequest {

    @NotNull
    private final UUID companyId;

    @NotBlank
    @Size(max = 180)
    private final String fullName;

    @NotBlank
    @Size(max = 14)
    private final String cpf;

    @Size(max = 20)
    private final String rg;

    private final LocalDate birthDate;

    @Size(max = 20)
    private final String gender;

    @Size(max = 180)
    private final String motherName;

    @Size(max = 180)
    private final String email;

    @Size(max = 20)
    private final String phone;

    @Size(max = 10)
    private final String zipCode;

    @Size(max = 180)
    private final String street;

    @Size(max = 20)
    private final String number;

    @Size(max = 120)
    private final String complement;

    @Size(max = 120)
    private final String district;

    @Size(max = 120)
    private final String city;

    @Size(max = 2)
    private final String state;

    @Size(max = 14)
    private final String pisPasep;

    @Size(max = 20)
    private final String ctpsNumber;

    @Size(max = 20)
    private final String ctpsSeries;

    @NotNull
    private final LocalDate admissionDate;

    @NotNull
    @DecimalMin("0.00")
    private final BigDecimal salary;

    @Size(max = 120)
    private final String jobTitle;

    @Size(max = 120)
    private final String department;

    @NotNull
    private final Boolean fgtsOptIn;

    @Size(max = 20)
    private final String inssNumber;

    @NotNull
    @Min(0)
    private final Integer dependentsIrCount;

    @NotNull
    private final Boolean withholdsIrrf;

    private final Boolean active;

    @JsonCreator
    public WorkerRequest(
            @JsonProperty("companyId") UUID companyId,
            @JsonProperty("fullName") String fullName,
            @JsonProperty("cpf") String cpf,
            @JsonProperty("rg") String rg,
            @JsonProperty("birthDate") LocalDate birthDate,
            @JsonProperty("gender") String gender,
            @JsonProperty("motherName") String motherName,
            @JsonProperty("email") String email,
            @JsonProperty("phone") String phone,
            @JsonProperty("zipCode") String zipCode,
            @JsonProperty("street") String street,
            @JsonProperty("number") String number,
            @JsonProperty("complement") String complement,
            @JsonProperty("district") String district,
            @JsonProperty("city") String city,
            @JsonProperty("state") String state,
            @JsonProperty("pisPasep") String pisPasep,
            @JsonProperty("ctpsNumber") String ctpsNumber,
            @JsonProperty("ctpsSeries") String ctpsSeries,
            @JsonProperty("admissionDate") LocalDate admissionDate,
            @JsonProperty("salary") BigDecimal salary,
            @JsonProperty("jobTitle") String jobTitle,
            @JsonProperty("department") String department,
            @JsonProperty("fgtsOptIn") Boolean fgtsOptIn,
            @JsonProperty("inssNumber") String inssNumber,
            @JsonProperty("dependentsIrCount") Integer dependentsIrCount,
            @JsonProperty("withholdsIrrf") Boolean withholdsIrrf,
            @JsonProperty("active") Boolean active) {
        this.companyId = companyId;
        this.fullName = fullName;
        this.cpf = cpf;
        this.rg = rg;
        this.birthDate = birthDate;
        this.gender = gender;
        this.motherName = motherName;
        this.email = email;
        this.phone = phone;
        this.zipCode = zipCode;
        this.street = street;
        this.number = number;
        this.complement = complement;
        this.district = district;
        this.city = city;
        this.state = state;
        this.pisPasep = pisPasep;
        this.ctpsNumber = ctpsNumber;
        this.ctpsSeries = ctpsSeries;
        this.admissionDate = admissionDate;
        this.salary = salary;
        this.jobTitle = jobTitle;
        this.department = department;
        this.fgtsOptIn = fgtsOptIn;
        this.inssNumber = inssNumber;
        this.dependentsIrCount = dependentsIrCount;
        this.withholdsIrrf = withholdsIrrf;
        this.active = active;
    }

    public UUID companyId() {
        return companyId;
    }

    public String fullName() {
        return fullName;
    }

    public String cpf() {
        return cpf;
    }

    public String rg() {
        return rg;
    }

    public LocalDate birthDate() {
        return birthDate;
    }

    public String gender() {
        return gender;
    }

    public String motherName() {
        return motherName;
    }

    public String email() {
        return email;
    }

    public String phone() {
        return phone;
    }

    public String zipCode() {
        return zipCode;
    }

    public String street() {
        return street;
    }

    public String number() {
        return number;
    }

    public String complement() {
        return complement;
    }

    public String district() {
        return district;
    }

    public String city() {
        return city;
    }

    public String state() {
        return state;
    }

    public String pisPasep() {
        return pisPasep;
    }

    public String ctpsNumber() {
        return ctpsNumber;
    }

    public String ctpsSeries() {
        return ctpsSeries;
    }

    public LocalDate admissionDate() {
        return admissionDate;
    }

    public BigDecimal salary() {
        return salary;
    }

    public String jobTitle() {
        return jobTitle;
    }

    public String department() {
        return department;
    }

    public Boolean fgtsOptIn() {
        return fgtsOptIn;
    }

    public String inssNumber() {
        return inssNumber;
    }

    public Integer dependentsIrCount() {
        return dependentsIrCount;
    }

    public Boolean withholdsIrrf() {
        return withholdsIrrf;
    }

    public Boolean active() {
        return active;
    }
}
