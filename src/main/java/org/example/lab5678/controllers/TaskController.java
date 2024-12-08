package org.example.lab5678.controllers;

import org.example.lab5678.models.Task;
import org.example.lab5678.models.UserEntity;
import org.example.lab5678.services.TaskService;
import org.example.lab5678.services.UserService;
import org.example.lab5678.services.CategoryService;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;
    private final CategoryService categoryService;
    private final UserService userService;

    public TaskController(TaskService taskService, CategoryService categoryService, UserService userService) {
        this.taskService = taskService;
        this.categoryService = categoryService;
        this.userService = userService;
    }

    @GetMapping
    public String showTasks(@AuthenticationPrincipal User userDetails,
                            @RequestParam(defaultValue = "0") int page,
                            @RequestParam(defaultValue = "") String query,
                            Model model) {
        UserEntity user = userService.findByUsername(userDetails.getUsername());
        var pageable = PageRequest.of(page, 10);
        var tasks = taskService.searchTasks(user, query, pageable);

        model.addAttribute("currentUser", user);

        model.addAttribute("tasks", tasks.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", tasks.getTotalPages());
        return "tasks";
    }

    @GetMapping("/add")
    public String showAddTaskForm(@AuthenticationPrincipal User userDetails, Model model) {
        model.addAttribute("task", new Task());
        model.addAttribute("categories", categoryService.getAllCategories());
        return "add-task";
    }

    @PostMapping("/add")
    public String addTask(@ModelAttribute Task task,
                          @RequestParam Long categoryId,
                          @AuthenticationPrincipal User userDetails) {
        UserEntity user = userService.findByUsername(userDetails.getUsername());
        taskService.addTask(task, user, categoryId);
        return "redirect:/tasks";
    }

//    @GetMapping("/add")
//    public String showAddTaskForm(@AuthenticationPrincipal User userDetails, Model model) {
//        if (!userDetails.getAuthorities().stream().anyMatch(a->a.getAuthority().equals("ROLE_ADMIN"))) {
//            return "redirect:/tasks";
//        }
//        model.addAttribute("task", new Task());
//        model.addAttribute("categories", categoryService.getAllCategories());
//        return "add-task";
//    }
//
//    @PostMapping("/add")
//    public String addTask(@ModelAttribute Task task,
//                          @RequestParam Long categoryId,
//                          @AuthenticationPrincipal User userDetails) {
//        if (!userDetails.getAuthorities().stream().anyMatch(a->a.getAuthority().equals("ROLE_ADMIN"))) {
//            return "redirect:/tasks";
//        }
//        UserEntity adminUser = userService.findByUsername(userDetails.getUsername());
//        taskService.addTask(task, adminUser, categoryId);
//        return "redirect:/tasks";
//    }
}
