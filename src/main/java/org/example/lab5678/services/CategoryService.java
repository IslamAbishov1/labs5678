package org.example.lab5678.services;

import org.example.lab5678.models.Category;
import org.example.lab5678.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Category addCategoryIfNotExists(String name) {
        Optional<Category> existing = categoryRepository.findByName(name);
        if (existing.isPresent()) return existing.get();
        return categoryRepository.save(new Category(name));
    }

    public Category getById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Category not found:" + id));
    }
}
