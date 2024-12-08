package org.example.lab5678.services;

import org.example.lab5678.models.Category;
import org.example.lab5678.models.Task;
import org.example.lab5678.models.UserEntity;
import org.example.lab5678.repository.TaskRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class TaskService {
    private final TaskRepository taskRepository;
    private final CategoryService categoryService;

    public TaskService(TaskRepository taskRepository, CategoryService categoryService) {
        this.taskRepository = taskRepository;
        this.categoryService = categoryService;
    }

    public Page<Task> getTasksByUser(UserEntity user, Pageable pageable) {
        return taskRepository.findByUser(user, pageable);
    }

    public Task addTask(Task task, UserEntity user, Long categoryId) {
        Category category = categoryService.getById(categoryId);
        task.setUser(user);
        task.setCategory(category);
        return taskRepository.save(task);
    }

    public Page<Task> searchTasks(UserEntity user, String query, Pageable pageable) {
        return getTasksByUser(user, pageable);
    }
}
