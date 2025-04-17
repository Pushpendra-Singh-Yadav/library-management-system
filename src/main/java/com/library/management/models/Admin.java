package com.library.management.models;

public class Admin extends User {
    private static final String ROLE = "ADMIN";

    public Admin(String userId, String username, String password, String name, String email) {
        super(userId, username, password, name, email);
    }

    @Override
    public String getRole() {
        return ROLE;
    }

    // Admin-specific methods can be added here
    public boolean hasAdminPrivileges() {
        return true;
    }
} 