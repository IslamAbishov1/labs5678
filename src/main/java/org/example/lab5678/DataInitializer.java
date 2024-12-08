package org.example.lab5678;

import org.example.lab5678.services.CategoryService;
import org.example.lab5678.services.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final CategoryService categoryService;
    private final UserService userService;

    public DataInitializer(CategoryService categoryService, UserService userService) {
        this.categoryService = categoryService;
        this.userService = userService;
    }

    @Override
    public void run(String... args) {
        categoryService.addCategoryIfNotExists("Reading");
        categoryService.addCategoryIfNotExists("Work");
        categoryService.addCategoryIfNotExists("Hobby");

        System.out.println("Initial categories ensured: Reading, Work, Hobby");

        if (userService.findByUsername("admin") == null) {
            userService.createUser("admin", "adminpass", "ADMIN");
        }

        if (userService.findByUsername("user") == null) {
            userService.createUser("user", "userpass", "USER");
        }

        System.out.println("Users ensured: admin (ADMIN), user (USER)");
    }
}
