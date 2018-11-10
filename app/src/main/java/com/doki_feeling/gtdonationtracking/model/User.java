package com.doki_feeling.gtdonationtracking.model;

public class User {
    private String username;
    private String email;
    private String phone_number;
    private boolean locked;
    private Privilege privilege;

    // Creates a new User instance and sets variables to 0
    public User() {
        this.username = "";
        this.email = "";
        this.locked = false;
        this.phone_number = "";
        this.privilege = Privilege.Guest;
    }

    // creates a new User instance and sets variable to the inputted values
    public User(String username,
                String email,
                String phone_number,
                boolean lock,
                Privilege privilege) {
        this.username = username;
        this.email = email;
        this.locked = lock;
        this.phone_number = phone_number;
        this.privilege = privilege;
    }

    // updates the variables of this instance of User
    public void updateAll(String username,
                          String email,
                          String phone_number,
                          boolean lock,
                          Privilege privilege) {
        this.username = username;
        this.email = email;
        this.locked = lock;
        this.phone_number = phone_number;
        this.privilege = privilege;
    }

    /**
     * sets the username variable
     *
     * @param username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * sets the Email variable for User
     *
     * @param email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Sets the phone number variable
     *
     * @param phone_number
     */
    public void setPhone(String phone_number) {
        this.phone_number = phone_number;
    }

    /**
     * Sets the privilege type of this User
     *
     * @param privilege type to set
     */
    public void setPrivilege(Privilege privilege) {
        this.privilege = privilege;
    }

    /**
     * sets the locked variable to locked
     *
     * @param locked boolean flag
     */
    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    // sets the locked variable
    public void lockAccount() {
        this.locked = true;
    }

    // sets the locked variable to unlocked
    public void unlockAccount() {
        this.locked = false;
    }

    // returns the username of this instance of user
    public String getUsername() {
        return username;
    }

    // returns the email of this instance of user
    public String getEmail() {
        return email;
    }

    // returns the phone number of this instance of user
    public String getPhone() {
        return phone_number;
    }

    // returns the privelege of this instance of user
    public Privilege getPrivilege() {
        return privilege;
    }

    // This name is for Firebase!, returns the locked variable of this instance of user
    public boolean isLocked() {
        return locked;
    }

    // signs the current user out and sets variables to 0
    public void signOut() {
        this.username = "";
        this.email = "";
        this.locked = false;
        this.phone_number = "";
        this.privilege = Privilege.Guest;
    }
}
