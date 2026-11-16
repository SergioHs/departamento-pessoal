package com.experiment.departamentopessoal.worker.api;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

public class WorkerResponse {

    private final UUID id;
    private final UUID companyId;
    private final String fullName;
    private final String cpf;
    private final String rg;
    private final LocalDate birthDate;
    private final String gender;
    private final String motherName;
    private final String email;
    private final String phone;
    private final String zipCode;
    private final String street;
    private final String number;
    private final String complement;
    private final String district;
    private final String city;
    private final String state;
    private final String pisPasep;
    private final String ctpsNumber;
    private final String ctpsSeries;
    private final LocalDate admissionDate;
    private final BigDecimal salary;
    private final String jobTitle;
    private final String department;
    private final boolean fgtsOptIn;
    private final String inssNumber;
    private final int dependentsIrCount;
    private final boolean withholdsIrrf;
    private final boolean active;
    private final Instant createdAt;
    private final Instant updatedAt;
    private final long version;

    public WorkerResponse(UUID id, UUID companyId, String fullName, String cpf, String rg, LocalDate birthDate, String gender,
                          String motherName, String email, String phone, String zipCode, String street, String number,
                          String complement, String district, String city, String state, String pisPasep, String ctpsNumber,
                          String ctpsSeries, LocalDate admissionDate, BigDecimal salary, String jobTitle, String department,
                          boolean fgtsOptIn, String inssNumber, int dependentsIrCount, boolean withholdsIrrf, boolean active,
                          Instant createdAt, Instant updatedAt, long version) {
        this.id = id;
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

    public UUID companyId() {
        return companyId;
    }

    @JsonProperty("companyId")
    public UUID getCompanyId() {
        return companyId;
    }

    public String fullName() {
        return fullName;
    }

    @JsonProperty("fullName")
    public String getFullName() {
        return fullName;
    }

    public String cpf() {
        return cpf;
    }

    @JsonProperty("cpf")
    public String getCpf() {
        return cpf;
    }

    public String rg() {
        return rg;
    }

    @JsonProperty("rg")
    public String getRg() {
        return rg;
    }

    public LocalDate birthDate() {
        return birthDate;
    }

    @JsonProperty("birthDate")
    public LocalDate getBirthDate() {
        return birthDate;
    }

    public String gender() {
        return gender;
    }

    @JsonProperty("gender")
    public String getGender() {
        return gender;
    }

    public String motherName() {
        return motherName;
    }

    @JsonProperty("motherName")
    public String getMotherName() {
        return motherName;
    }

    public String email() {
        return email;
    }

    @JsonProperty("email")
    public String getEmail() {
        return email;
    }

    public String phone() {
        return phone;
    }

    @JsonProperty("phone")
    public String getPhone() {
        return phone;
    }

    public String zipCode() {
        return zipCode;
    }

    @JsonProperty("zipCode")
    public String getZipCode() {
        return zipCode;
    }

    public String street() {
        return street;
    }

    @JsonProperty("street")
    public String getStreet() {
        return street;
    }

    public String number() {
        return number;
    }

    @JsonProperty("number")
    public String getNumber() {
        return number;
    }

    public String complement() {
        return complement;
    }

    @JsonProperty("complement")
    public String getComplement() {
        return complement;
    }

    public String district() {
        return district;
    }

    @JsonProperty("district")
    public String getDistrict() {
        return district;
    }

    public String city() {
        return city;
    }

    @JsonProperty("city")
    public String getCity() {
        return city;
    }

    public String state() {
        return state;
    }

    @JsonProperty("state")
    public String getState() {
        return state;
    }

    public String pisPasep() {
        return pisPasep;
    }

    @JsonProperty("pisPasep")
    public String getPisPasep() {
        return pisPasep;
    }

    public String ctpsNumber() {
        return ctpsNumber;
    }

    @JsonProperty("ctpsNumber")
    public String getCtpsNumber() {
        return ctpsNumber;
    }

    public String ctpsSeries() {
        return ctpsSeries;
    }

    @JsonProperty("ctpsSeries")
    public String getCtpsSeries() {
        return ctpsSeries;
    }

    public LocalDate admissionDate() {
        return admissionDate;
    }

    @JsonProperty("admissionDate")
    public LocalDate getAdmissionDate() {
        return admissionDate;
    }

    public BigDecimal salary() {
        return salary;
    }

    @JsonProperty("salary")
    public BigDecimal getSalary() {
        return salary;
    }

    public String jobTitle() {
        return jobTitle;
    }

    @JsonProperty("jobTitle")
    public String getJobTitle() {
        return jobTitle;
    }

    public String department() {
        return department;
    }

    @JsonProperty("department")
    public String getDepartment() {
        return department;
    }

    public boolean fgtsOptIn() {
        return fgtsOptIn;
    }

    @JsonProperty("fgtsOptIn")
    public boolean isFgtsOptIn() {
        return fgtsOptIn;
    }

    public String inssNumber() {
        return inssNumber;
    }

    @JsonProperty("inssNumber")
    public String getInssNumber() {
        return inssNumber;
    }

    public int dependentsIrCount() {
        return dependentsIrCount;
    }

    @JsonProperty("dependentsIrCount")
    public int getDependentsIrCount() {
        return dependentsIrCount;
    }

    public boolean withholdsIrrf() {
        return withholdsIrrf;
    }

    @JsonProperty("withholdsIrrf")
    public boolean isWithholdsIrrf() {
        return withholdsIrrf;
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
