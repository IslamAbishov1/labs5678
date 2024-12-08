package org.example.lab5678.controllers;

import org.example.lab5678.models.Photo;
import org.example.lab5678.models.UserEntity;
import org.example.lab5678.services.PhotoService;
import org.example.lab5678.services.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequestMapping("/photos")
public class PhotoController {
    private final PhotoService photoService;
    private final UserService userService;

    public PhotoController(PhotoService photoService, UserService userService) {
        this.photoService = photoService;
        this.userService = userService;
    }

    @GetMapping
    public String showUploadForm(Model model) {
        return "upload-photo";
    }

    @PostMapping
    public String uploadPhoto(@RequestParam("file") MultipartFile file,
                              @AuthenticationPrincipal User userDetails,
                              Model model) {
        try {
            Photo photo = photoService.uploadPhoto(file);
            UserEntity user = userService.findByUsername(userDetails.getUsername());
            user.setPhotoLink("/images/" + photo.getFileName());
            userService.updateUser(user);
            model.addAttribute("success", "Photo uploaded successfully: " + photo.getFileName());
        } catch (IOException e) {
            model.addAttribute("error", "Failed to upload photo: " + e.getMessage());
        }
        return "upload-photo";
    }
}
