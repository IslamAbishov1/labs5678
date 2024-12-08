package org.example.lab5678.repository;

import org.example.lab5678.models.Task;
import org.example.lab5678.models.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {
    Page<Task> findByUser(UserEntity user, Pageable pageable);
}
