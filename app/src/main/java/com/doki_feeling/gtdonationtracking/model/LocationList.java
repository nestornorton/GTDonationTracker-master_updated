package com.doki_feeling.gtdonationtracking.model;

import java.util.ArrayList;

public class LocationList {
    private static final ArrayList<Location> locationList = new ArrayList<Location>();

    public LocationList() {
    }

    /**
     * Method that returns the static instance of locationList collection.
     *
     * @return locationList static ArrayList Object
     */
    public static ArrayList<Location> getInstance() {
        return locationList;
    }

    /**
     * Method that returns Location by Id input
     *
     * @param id value of the location to retrieve in locationList ArrayList
     * @return locationList static ArrayList Object
     */
    public static Location getLocationById(int id) {
        return locationList.get(id);
    }

    /**
     * Method that returns the name of all Locations in locationList ArrayList.
     *
     * @return a array of String Objects corresponding to the names of all locations in the
     * locationList Object
     */
    public static String[] getNameList() {
        String[] strings = new String[locationList.size()];
        for (int i = 0; i < locationList.size(); i++) {
            strings[i] = locationList.get(i).getSiteName();
        }
        return strings;
    }

    /**
     * Method that returns the name of all Locations in locationList ArrayList, with added "ANY
     * LOCATION" String in the 0 index of the array.
     *
     * @return a array of String Objects corresponding to the names of all locations in the
     * locationList Object
     */
    public static String[] getNameListWithAll() {
        String[] strings = new String[locationList.size() + 1];
        strings[0] = "ANY LOCATION";
        for (int i = 1; i < locationList.size() + 1; i++) {
            strings[i] = locationList.get(i - 1).getSiteName();
        }
        return strings;
    }

    /**
     * Method that adds a location object into the locationList ArrayList Object
     *
     * @param location to add to the locationList ArrayList Object
     * @return <code>void</code>
     */
    public static void add(Location location) {
        locationList.add(location);
    }

    /**
     * Method that clears (sets it empty) the locationList ArrayList
     * @return <code>void</code>
     */
    public static void clear() {
        locationList.clear();
    }
}
