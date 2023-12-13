package com.example.testtask.repository;

import com.example.testtask.model.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    Page<Task> findTasksByAuthor_Id(Long id, Pageable pageable);

    Page<Task> findTasksByExecutor_Id(Long id, Pageable pageable);
}
