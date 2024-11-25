package com.mohammedsaqibkhan.userservice.controller;


import com.mohammedsaqibkhan.userservice.service.PasswordResetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api")
public class PasswordResetController {

    @Autowired
    private PasswordResetService passwordResetService;

    @PostMapping("/reset-password")
    public String resetPassword(@RequestBody String email) {
        System.out.println("Received email: " + email);
        System.out.println("Email contains quotes: " + email.contains("\""));
        // Remove quotes if present
        email = email.replaceAll("^\"|\"$", ""); // Remove leading/trailing quotes
        System.out.println("Processed email: " + email);

        try {
            passwordResetService.sendResetLink(email);
            return "Password reset email sent.";
        } catch (Exception e) {
            e.printStackTrace(); // Log exception stack trace
            return "Failed to send password reset email: " + e.getMessage();
        }
    }


}

