package org.example.lab5678.controllers;

import jakarta.mail.MessagingException;
import org.example.lab5678.services.EmailService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/email")
public class EmailController {
    private final EmailService emailService;

    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @GetMapping
    public String showEmailForm() {
        return "email-form";
    }

    @PostMapping
    public String sendEmail(@RequestParam String to,
                            @RequestParam String subject,
                            @RequestParam String body,
                            Model model,
                            @AuthenticationPrincipal User userDetails) {
        try {
            emailService.sendEmail(to, subject, body);
            model.addAttribute("success", "Email sent successfully!");
        } catch (MessagingException e) {
            model.addAttribute("error", "Failed to send email: " + e.getMessage());
        }
        return "email-form";
    }
}

//    @PostMapping
//    public String sendEmail(@RequestParam String to,
//                            @RequestParam String subject,
//                            @RequestParam String body,
//                            Model model,
//                            @AuthenticationPrincipal User userDetails) {
//        if (!userDetails.getAuthorities().stream().anyMatch(a->a.getAuthority().equals("ROLE_ADMIN"))) {
//            model.addAttribute("error", "Only admin can send emails");
//            return "email-form";
//        }
//        try {
//            emailService.sendEmail(to, subject, body);
//            model.addAttribute("success", "Email sent successfully!");
//        } catch (MessagingException e) {
//            model.addAttribute("error", "Failed to send email: " + e.getMessage());
//        }
//        return "email-form";
//    }

