package dev.artsupplier.paintingtracker.security;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;
import java.util.Collections;

@Entity
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; //user auto generated id

    @Column(unique = true, nullable = false)
    private String username; //username

    private String password; //password

    private String role; //USER | ADMIN

    //default constructor
    public User() {
    }
    public User(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    //getters and setters
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public String getRole() {
        return role;
    }
    public void setRole(String role) {
        this.role = role;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new org.springframework.security.core.authority.SimpleGrantedAuthority("ROLE_" + role));
    }
    @Override
    public boolean isAccountNonExpired() {
        return true; //account is not expired
    }
    @Override
    public boolean isAccountNonLocked() {
        return true; //account is not locked
    }
    @Override
    public boolean isCredentialsNonExpired() {
        return true; //credentials are not expired
    }
    @Override
    public boolean isEnabled() {
        return true; //account is enabled
    }
}

