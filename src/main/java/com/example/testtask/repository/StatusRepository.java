package com.example.testtask.repository;

import com.example.testtask.model.Status;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatusRepository extends JpaRepository<Status, Integer> {
    Status findByName(String name);
}
