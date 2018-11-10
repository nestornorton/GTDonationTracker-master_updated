package com.doki_feeling.gtdonationtracking.model;

public class AccountOwner {

    private static final User _instance = new User();

    /** method that returns a static instance of user
     * @return User _instance
     */
    public static User getInstance() {
        return _instance;
    }
}