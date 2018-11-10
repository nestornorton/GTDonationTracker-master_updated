package com.doki_feeling.gtdonationtracking.model;

public enum Privilege {
    Admin("Administrator", "Admin"),
    User("Normal User", "User"),
    Local("Location Employee", "Local"),
    Manager("Manager", "Manager"),
    Guest("Guest", "Guest");

    private final String privilege;
    private final String description;

    // sets the variables of a new instance of privelege
    Privilege(String description, String privilege) {
        this.description = description;
        this.privilege = privilege;
    }

    // returns a description of the current privilege
    public String toString() {
        return description;
    }

    // returns the current level of privilege
    public String getPrivilege() {
        return privilege;
    }

    // returns a description of the current privilege
    public String getDescription() {
        return description;
    }
}
