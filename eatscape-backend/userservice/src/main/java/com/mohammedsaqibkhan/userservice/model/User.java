package com.mohammedsaqibkhan.userservice.model;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Data
@Table(name = "users") // Ensure this matches your database table
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String username;

    @Column(unique = true)
    private String email;

    private String password;

    // Add any other fields as necessary (e.g., email, first name, etc.)

    // Implement UserDetails methods@Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Example: Create a list of authorities based on user roles
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER")); // Replace with actual role logic
        return authorities;
    }



    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // Implement as necessary
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // Implement as necessary
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Implement as necessary
    }

    @Override
    public boolean isEnabled() {
        return true; // Implement as necessary
    }

}