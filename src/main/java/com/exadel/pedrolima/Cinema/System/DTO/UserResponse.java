package com.exadel.pedrolima.Cinema.System.DTO;

import com.exadel.pedrolima.entity.enums.UserRole;

public class UserResponse {

    private Long id;
    private String name;
    private String email;
    private UserRole userRole;

    public UserResponse(Long id, String name, String email, UserRole userRole) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.userRole = userRole;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public UserRole getUserRole() {
        return userRole;
    }
}
