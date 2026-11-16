package com.experiment.departamentopessoal.worker.application;

import com.experiment.departamentopessoal.company.domain.Company;
import com.experiment.departamentopessoal.company.domain.CompanyRepository;
import com.experiment.departamentopessoal.shared.application.ResourceNotFoundException;
import com.experiment.departamentopessoal.worker.api.WorkerRequest;
import com.experiment.departamentopessoal.worker.api.WorkerResponse;
import com.experiment.departamentopessoal.worker.domain.Worker;
import com.experiment.departamentopessoal.worker.domain.WorkerRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class WorkerService {

    private final WorkerRepository workerRepository;
    private final CompanyRepository companyRepository;

    public WorkerService(WorkerRepository workerRepository, CompanyRepository companyRepository) {
        this.workerRepository = workerRepository;
        this.companyRepository = companyRepository;
    }

    @Transactional
    public WorkerResponse create(WorkerRequest request) {
        Company company = findCompany(request.companyId());
        Worker worker = new Worker(
                company,
                request.fullName(),
                request.cpf(),
                request.rg(),
                request.birthDate(),
                request.gender(),
                request.motherName(),
                request.email(),
                request.phone(),
                request.zipCode(),
                request.street(),
                request.number(),
                request.complement(),
                request.district(),
                request.city(),
                request.state(),
                request.pisPasep(),
                request.ctpsNumber(),
                request.ctpsSeries(),
                request.admissionDate(),
                request.salary(),
                request.jobTitle(),
                request.department(),
                request.fgtsOptIn(),
                request.inssNumber(),
                request.dependentsIrCount(),
                request.withholdsIrrf(),
                request.active() == null || request.active()
        );
        return toResponse(workerRepository.save(worker));
    }

    @Transactional(readOnly = true)
    public Page<WorkerResponse> list(String name, Pageable pageable) {
        if (name == null || name.isBlank()) {
            return workerRepository.findAll(pageable).map(this::toResponse);
        }
        return workerRepository.findByFullNameContainingIgnoreCase(name.trim(), pageable)
                .map(this::toResponse);
    }

    @Transactional(readOnly = true)
    public WorkerResponse get(UUID id) {
        return toResponse(findWorker(id));
    }

    @Transactional
    public WorkerResponse update(UUID id, WorkerRequest request) {
        Worker worker = findWorker(id);
        worker.setCompany(findCompany(request.companyId()));
        worker.setFullName(request.fullName());
        worker.setCpf(request.cpf());
        worker.setRg(request.rg());
        worker.setBirthDate(request.birthDate());
        worker.setGender(request.gender());
        worker.setMotherName(request.motherName());
        worker.setEmail(request.email());
        worker.setPhone(request.phone());
        worker.setZipCode(request.zipCode());
        worker.setStreet(request.street());
        worker.setNumber(request.number());
        worker.setComplement(request.complement());
        worker.setDistrict(request.district());
        worker.setCity(request.city());
        worker.setState(request.state());
        worker.setPisPasep(request.pisPasep());
        worker.setCtpsNumber(request.ctpsNumber());
        worker.setCtpsSeries(request.ctpsSeries());
        worker.setAdmissionDate(request.admissionDate());
        worker.setSalary(request.salary());
        worker.setJobTitle(request.jobTitle());
        worker.setDepartment(request.department());
        worker.setFgtsOptIn(request.fgtsOptIn());
        worker.setInssNumber(request.inssNumber());
        worker.setDependentsIrCount(request.dependentsIrCount());
        worker.setWithholdsIrrf(request.withholdsIrrf());
        if (request.active() != null) {
            worker.setActive(request.active());
        }
        return toResponse(workerRepository.save(worker));
    }

    @Transactional
    public void deactivate(UUID id) {
        Worker worker = findWorker(id);
        worker.setActive(false);
        workerRepository.save(worker);
    }

    private Worker findWorker(UUID id) {
        return workerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Colaborador não encontrado"));
    }

    private Company findCompany(UUID companyId) {
        return companyRepository.findById(companyId)
                .orElseThrow(() -> new ResourceNotFoundException("Empresa não encontrada"));
    }

    private WorkerResponse toResponse(Worker worker) {
        return new WorkerResponse(
                worker.getId(),
                worker.getCompany().getId(),
                worker.getFullName(),
                worker.getCpf(),
                worker.getRg(),
                worker.getBirthDate(),
                worker.getGender(),
                worker.getMotherName(),
                worker.getEmail(),
                worker.getPhone(),
                worker.getZipCode(),
                worker.getStreet(),
                worker.getNumber(),
                worker.getComplement(),
                worker.getDistrict(),
                worker.getCity(),
                worker.getState(),
                worker.getPisPasep(),
                worker.getCtpsNumber(),
                worker.getCtpsSeries(),
                worker.getAdmissionDate(),
                worker.getSalary(),
                worker.getJobTitle(),
                worker.getDepartment(),
                worker.isFgtsOptIn(),
                worker.getInssNumber(),
                worker.getDependentsIrCount(),
                worker.isWithholdsIrrf(),
                worker.isActive(),
                worker.getCreatedAt(),
                worker.getUpdatedAt(),
                worker.getVersion()
        );
    }
}
