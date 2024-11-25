package com.mohammedsaqibkhan.userservice.service;

import com.mohammedsaqibkhan.userservice.model.User;
import com.mohammedsaqibkhan.userservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class PasswordResetService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private UserRepository userRepository;

    // Dummy method for checking if the email exists (replace with actual logic)
    private boolean isEmailRegistered(String email) {
        System.out.println("Input email: [" + email + "]");
        Optional<User> user = userRepository.findByEmail(email);
        System.out.println("Repository response: " + user);
        System.out.println("Checking email: " + email);
        System.out.println("Found user: " + (user.isPresent() ? user.get() : "No user found"));
        return user.filter(value -> email.equals(value.getEmail())).isPresent();
    } // eeverythingg010@gmail.com


    public void sendResetLink(String email) throws Exception {
        if (!isEmailRegistered(email)) {
            throw new Exception("Email not registered");
        }

        // Generate a unique token (using UUID)
        String token = UUID.randomUUID().toString();
        String resetLink = "http://localhost:3000/reset-password?token=" + token;

        // Save the token in memory or database (you can use a Map, Cache, or Database)
        // For demonstration purposes, we're just printing the token here.
        System.out.println("Generated reset token: " + token);

        // Send the email with the reset link
        sendResetEmail(email, resetLink);
    }

    private void sendResetEmail(String email, String resetLink) {
        System.out.println("Sending email to: " + email);
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Password Reset Request");
        message.setText("To reset your password, click the following link: " + resetLink);
        message.setFrom("trickshotguru9603@gmail.com");

        try {
            mailSender.send(message);
            System.out.println("Email sent successfully to: " + email);
        } catch (Exception e) {
            System.err.println("Failed to send email to: " + email);
            e.printStackTrace();
        }
    }

}

