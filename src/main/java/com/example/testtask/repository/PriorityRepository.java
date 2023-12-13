package com.example.testtask.repository;

import com.example.testtask.model.Priority;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PriorityRepository extends JpaRepository<Priority, Integer> {
    Priority findByName(String name);
}
