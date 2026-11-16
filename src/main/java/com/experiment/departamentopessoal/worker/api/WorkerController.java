package com.experiment.departamentopessoal.worker.api;

import com.experiment.departamentopessoal.worker.application.WorkerService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/workers")
@PreAuthorize("hasRole('ADMIN')")
public class WorkerController {

    private static final int MAX_PAGE_SIZE = 100;

    private final WorkerService workerService;

    public WorkerController(WorkerService workerService) {
        this.workerService = workerService;
    }

    @PostMapping
    public WorkerResponse create(@Valid @RequestBody WorkerRequest request) {
        return workerService.create(request);
    }

    @GetMapping
    public Page<WorkerResponse> list(
            @RequestParam(required = false) String name,
            @PageableDefault(size = 20) Pageable pageable
    ) {
        Pageable safePageable = PageRequest.of(
                pageable.getPageNumber(),
                Math.min(pageable.getPageSize(), MAX_PAGE_SIZE),
                pageable.getSort()
        );
        return workerService.list(name, safePageable);
    }

    @GetMapping("/{id}")
    public WorkerResponse get(@PathVariable UUID id) {
        return workerService.get(id);
    }

    @PutMapping("/{id}")
    public WorkerResponse update(@PathVariable UUID id, @Valid @RequestBody WorkerRequest request) {
        return workerService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public void deactivate(@PathVariable UUID id) {
        workerService.deactivate(id);
    }
}
