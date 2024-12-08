package org.example.lab5678.controllers;

import org.example.lab5678.models.UserEntity;
import org.example.lab5678.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/register")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String showRegisterForm(Model model) {
        model.addAttribute("user", new UserEntity());
        return "register";
    }

    @PostMapping
    public String registerUser(@ModelAttribute UserEntity user, Model model) {
        if (userService.findByUsername(user.getUsername()) != null) {
            model.addAttribute("error", "User already exists!");
            return "register";
        }

        userService.createUser(user.getUsername(), user.getPassword(), "USER");
        return "redirect:/login";
    }
}
