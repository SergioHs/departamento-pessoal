package com.experiment.departamentopessoal.company.application;

import com.experiment.departamentopessoal.company.api.CompanyRequest;
import com.experiment.departamentopessoal.company.api.CompanyResponse;
import com.experiment.departamentopessoal.company.domain.Company;
import com.experiment.departamentopessoal.company.domain.CompanyRepository;
import com.experiment.departamentopessoal.shared.application.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class CompanyService {

    private final CompanyRepository companyRepository;

    public CompanyService(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    @Transactional
    public CompanyResponse create(CompanyRequest request) {
        Company company = new Company(request.name(), request.document(), request.active() == null || request.active());
        return toResponse(companyRepository.save(company));
    }

    @Transactional(readOnly = true)
    public Page<CompanyResponse> list(Pageable pageable) {
        return companyRepository.findAll(pageable).map(this::toResponse);
    }

    @Transactional(readOnly = true)
    public CompanyResponse get(UUID id) {
        return toResponse(findCompany(id));
    }

    @Transactional
    public CompanyResponse update(UUID id, CompanyRequest request) {
        Company company = findCompany(id);
        company.setName(request.name());
        company.setDocument(request.document());
        if (request.active() != null) {
            company.setActive(request.active());
        }
        return toResponse(companyRepository.save(company));
    }

    @Transactional
    public void deactivate(UUID id) {
        Company company = findCompany(id);
        company.setActive(false);
        companyRepository.save(company);
    }

    private Company findCompany(UUID id) {
        return companyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Empresa não encontrada"));
    }

    private CompanyResponse toResponse(Company company) {
        return new CompanyResponse(
                company.getId(),
                company.getName(),
                company.getDocument(),
                company.isActive(),
                company.getCreatedAt(),
                company.getUpdatedAt(),
                company.getVersion()
        );
    }
}
