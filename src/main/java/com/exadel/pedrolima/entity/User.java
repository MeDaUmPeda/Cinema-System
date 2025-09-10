package com.exadel.pedrolima.entity;

import com.exadel.pedrolima.entity.enums.UserRole;

import java.util.Objects;

public class User {

    private Long id;
    private String Name;
    private  String Email;
    private UserRole role;

    public User(){
    }

    public User(Long id, UserRole role, String email, String name) {
        this.id = id;
        this.role = role;
        Email = email;
        Name = name;
    }

    public Long getId() {
        return id;
    }

    public UserRole getRole() {
        return role;
    }

    //revisar se isso pode
    public void setRole(UserRole role) {
        this.role = role;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) && Objects.equals(Name, user.Name) && Objects.equals(Email, user.Email) && role == user.role;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, Name, Email, role);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", Name='" + Name + '\'' +
                ", Email='" + Email + '\'' +
                ", role=" + role +
                '}';
    }
}
