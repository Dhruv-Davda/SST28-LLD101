package com.example.reports;

public class AccessControl {

    public boolean canAccess(User user, String level) {
        String userRole = user.getRole();

        if ("PUBLIC".equals(level)) return true;
        if ("FACULTY".equals(level)) {
            return "FACULTY".equals(userRole) || "ADMIN".equals(userRole);
        }
        if ("ADMIN".equals(level)) {
            return "ADMIN".equals(userRole);
        }
        return false;
    }
}
