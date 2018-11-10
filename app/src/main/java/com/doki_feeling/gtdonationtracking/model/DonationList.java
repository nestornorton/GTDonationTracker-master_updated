package com.doki_feeling.gtdonationtracking.model;

import android.util.SparseArray;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

public class DonationList {
    private static final ArrayList<Donation> donationList = new ArrayList<>();

    /**
     * Constructor for an empty DonationList
     */
    public DonationList() {};

    /**
     * Getter for the current instance of donationList
     * @return the ArrayList of Donation objects
     */
    public static ArrayList<Donation> getInstance() {
        return donationList;
    }

    /**
     * Getter for a Donation in the donationList matching a specific id
     * @param id the identifier of the Donation being looked for
     * @return the Donation matching the id
     */
    public static Donation getDonationById(int id) { return donationList.get(id); }

    /**
    * Gets the list of the names of all donations in the DonationList
    * @return the names of all donations in the donationList as an Array of Strings
    */
    public static String[] getNameList() {
        String[] strings = new String[donationList.size()];
        for (int i = 0 ; i < donationList.size() ;i++) {
            strings[i] = donationList.get(i).getItemName();
        }
        return strings;
    }

    /**
     * Adds a Donation to the donationList
     * @param donation the Donation object being added to the donationList
     */
    public static void add(Donation donation) {
        donationList.add(donation);
    }

    /**
     * Clears the donationList
     */
    public static void clear() { donationList.clear(); }

}
