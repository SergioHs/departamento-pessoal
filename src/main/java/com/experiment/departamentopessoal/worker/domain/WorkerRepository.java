package com.experiment.departamentopessoal.worker.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface WorkerRepository extends JpaRepository<Worker, UUID> {
    Page<Worker> findByFullNameContainingIgnoreCase(String fullName, Pageable pageable);
}
