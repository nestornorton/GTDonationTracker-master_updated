package com.doki_feeling.gtdonationtracking.model;

import com.google.firebase.firestore.GeoPoint;

public class Location {
    private int id;
    private GeoPoint cord = new GeoPoint(0, 0);
    private String siteName = "";
    private String streetAddr = "";
    private String city = "";
    private String state = "";
    private String zip = "000000";
    private String type = "";
    private String phone = "";
    private String website = "";

    public Location() {
    }

    /** Method that sets the coordinates of Location and returns this location class object
     * @param latitude degree
     * @param longitude degree
     * @return this Location Object
     */
    public Location cord(double latitude, double longitude) {
        this.cord = new GeoPoint(latitude, longitude);
        return this;
    }

    /** Method that sets this siteName and returns this Location class object
     * @param siteName to set
     * @return this Location class Object
     */
    public Location siteName(String siteName) {
        this.siteName = siteName;
        return this;
    }

    /** Method that sets the street Address and returns this Location class object
     * @param streetAddr to set
     * @return this Location class Object
     */
    public Location streetAddr(String streetAddr) {
        this.streetAddr = streetAddr;
        return this;
    }

    /** Method that sets city and returns this Location class object
     * @param city to set
     * @return this Location class Object
     */
    public Location city(String city) {
        this.city = city;
        return this;
    }

    /** Method that sets the state and returns this Location class object
     * @param state to set
     * @return this Location class Object
     */
    public Location state(String state) {
        this.state = state;
        return this;
    }

    /** Method that sets the zip and returns this Location class object
     * @param zip to set
     * @return this Location class Object
     */
    public Location zip(String zip) {
        this.zip = zip;
        return this;
    }

    /** Method that sets the Location type and returns this Location class object
     * @param type of Location to set
     * @return this Location class Object
     */
    public Location type(String type) {
        this.type = type;
        return this;
    }

    /** Method that sets the phone number and returns this Location class object
     * @param phone to set
     * @return this Location class Object
     */
    public Location phone(String phone) {
        this.phone = phone;
        return this;
    }

    /** Method that sets the website link and returns this Location class object
     * @param website to set
     * @return this Location class Object
     */
    public Location website(String website) {
        this.website = website;
        return this;
    }

    /** Method that sets the Location id and returns this Location class object
     * @param id to set
     * @return this Location class Object
     */
    public Location id(int id) {
        this.id = id;
        return this;
    }

    /** Method that gets the site name from this location
     * @return siteName Location class Object
     */
    public String getSiteName() {
        return siteName;
    }

    /** Method that gets the city name from this location
     * @return city name
     */
    public String getCity() {
        return city;
    }

    /** Method that gets the street Address name from this location
     * @return streetAddr
     */
    public String getStreetAddr() {
        return streetAddr;
    }

    /** Method that gets the zip code from this location
     * @return zip code
     */
    public String getZip() {
        return zip;
    }

    /** Method that gets the location type from this location
     * @return type of location
     */
    public String getType() {
        return type;
    }

    /** Method that gets the state name from this location
     * @return state name
     */
    public String getState() {
        return state;
    }

    /** Method that gets the phone number from this location
     * @return phone number
     */
    public String getPhone() {
        return phone;
    }

    /** Method that gets the website link from this location
     * @return website link
     */
    public String getWebsite() {
        return website;
    }

    /** Method that gets the coordinates from this location
     * @return cord x,y values
     */
    public GeoPoint getCord() {
        return cord;
    }

    /** Method that gets the id from this location
     * @return id identifier
     */
    public Integer getId() {
        return id;
    }

    /** Location's toString() method that returns the siteName
     * @return siteName
     * */
    public String toString() {
        return siteName;
    }

}
