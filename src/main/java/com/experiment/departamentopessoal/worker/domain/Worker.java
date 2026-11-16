package com.experiment.departamentopessoal.worker.domain;

import com.experiment.departamentopessoal.company.domain.Company;
import com.experiment.departamentopessoal.shared.domain.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "worker", indexes = {
        @Index(name = "idx_worker_company_id", columnList = "company_id"),
        @Index(name = "idx_worker_full_name", columnList = "full_name"),
        @Index(name = "idx_worker_cpf", columnList = "cpf")
})
public class Worker extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "company_id", nullable = false, foreignKey = @ForeignKey(name = "fk_worker_company"))
    private Company company;

    @Column(name = "full_name", nullable = false, length = 180)
    private String fullName;

    @Column(name = "cpf", nullable = false, length = 14)
    private String cpf;

    @Column(name = "rg", length = 20)
    private String rg;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @Column(name = "gender", length = 20)
    private String gender;

    @Column(name = "mother_name", length = 180)
    private String motherName;

    @Column(name = "email", length = 180)
    private String email;

    @Column(name = "phone", length = 20)
    private String phone;

    @Column(name = "zip_code", length = 10)
    private String zipCode;

    @Column(name = "street", length = 180)
    private String street;

    @Column(name = "number", length = 20)
    private String number;

    @Column(name = "complement", length = 120)
    private String complement;

    @Column(name = "district", length = 120)
    private String district;

    @Column(name = "city", length = 120)
    private String city;

    @Column(name = "state", length = 2)
    private String state;

    @Column(name = "pis_pasep", length = 14)
    private String pisPasep;

    @Column(name = "ctps_number", length = 20)
    private String ctpsNumber;

    @Column(name = "ctps_series", length = 20)
    private String ctpsSeries;

    @Column(name = "admission_date", nullable = false)
    private LocalDate admissionDate;

    @Column(name = "salary", nullable = false, precision = 12, scale = 2)
    private BigDecimal salary;

    @Column(name = "job_title", length = 120)
    private String jobTitle;

    @Column(name = "department", length = 120)
    private String department;

    @Column(name = "fgts_opt_in", nullable = false)
    private boolean fgtsOptIn;

    @Column(name = "inss_number", length = 20)
    private String inssNumber;

    @Column(name = "dependents_ir_count", nullable = false)
    private int dependentsIrCount;

    @Column(name = "withholds_irrf", nullable = false)
    private boolean withholdsIrrf;

    @Column(name = "active", nullable = false)
    private boolean active = true;

    protected Worker() {
    }

    public Worker(Company company, String fullName, String cpf, String rg, LocalDate birthDate, String gender, String motherName,
                  String email, String phone, String zipCode, String street, String number, String complement, String district,
                  String city, String state, String pisPasep, String ctpsNumber, String ctpsSeries, LocalDate admissionDate,
                  BigDecimal salary, String jobTitle, String department, boolean fgtsOptIn, String inssNumber,
                  int dependentsIrCount, boolean withholdsIrrf, boolean active) {
        this.company = company;
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

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getRg() {
        return rg;
    }

    public void setRg(String rg) {
        this.rg = rg;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getMotherName() {
        return motherName;
    }

    public void setMotherName(String motherName) {
        this.motherName = motherName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getComplement() {
        return complement;
    }

    public void setComplement(String complement) {
        this.complement = complement;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPisPasep() {
        return pisPasep;
    }

    public void setPisPasep(String pisPasep) {
        this.pisPasep = pisPasep;
    }

    public String getCtpsNumber() {
        return ctpsNumber;
    }

    public void setCtpsNumber(String ctpsNumber) {
        this.ctpsNumber = ctpsNumber;
    }

    public String getCtpsSeries() {
        return ctpsSeries;
    }

    public void setCtpsSeries(String ctpsSeries) {
        this.ctpsSeries = ctpsSeries;
    }

    public LocalDate getAdmissionDate() {
        return admissionDate;
    }

    public void setAdmissionDate(LocalDate admissionDate) {
        this.admissionDate = admissionDate;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public boolean isFgtsOptIn() {
        return fgtsOptIn;
    }

    public void setFgtsOptIn(boolean fgtsOptIn) {
        this.fgtsOptIn = fgtsOptIn;
    }

    public String getInssNumber() {
        return inssNumber;
    }

    public void setInssNumber(String inssNumber) {
        this.inssNumber = inssNumber;
    }

    public int getDependentsIrCount() {
        return dependentsIrCount;
    }

    public void setDependentsIrCount(int dependentsIrCount) {
        this.dependentsIrCount = dependentsIrCount;
    }

    public boolean isWithholdsIrrf() {
        return withholdsIrrf;
    }

    public void setWithholdsIrrf(boolean withholdsIrrf) {
        this.withholdsIrrf = withholdsIrrf;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
