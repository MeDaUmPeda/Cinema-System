package com.exadel.pedrolima.Cinema.System.DTO;

import com.exadel.pedrolima.entity.enums.UserRole;

public class UserRequest {

    private String name;
    private String email;
    private UserRole userRole;

    public UserRequest(){

    }

    public UserRequest(String name, String email, UserRole userRole) {
        this.name = name;
        this.email = email;
        this.userRole = userRole;
    }

    public String getName() {return name; }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserRole getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }
}