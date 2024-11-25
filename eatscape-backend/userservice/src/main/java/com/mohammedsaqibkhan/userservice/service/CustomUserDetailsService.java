package com.mohammedsaqibkhan.userservice.service;

import com.mohammedsaqibkhan.userservice.model.User;
import com.mohammedsaqibkhan.userservice.repository.UserRepository; // Make sure you have a User repository
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository; // Assume you have a User repository

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUsername(username); // Implement this method in your repository
        if (user.isEmpty()) {
            throw new UsernameNotFoundException("User not found");
        }
        User userFromDb = user.get();
        return new org.springframework.security.core.userdetails.User(userFromDb.getUsername(), userFromDb.getPassword(), userFromDb.getAuthorities());
    }
}
