package com.doki_feeling.gtdonationtracking.model;

import java.util.Date;

public class Donation {

    public static final String FIELD_NAME = "itemName";
    public static final String FIELD_CATEGORY = "category";
    public static final String FIELD_TIME = "receiveTime";
    public static final String FIELD_SITE = "site";

    private String itemName = "";
    private String category = "";
    private String description = "";
    private String comments = "";
    private String site = "";
    private Date receiveTime = new Date(0);

    /**
     * Constructor for a Donation object without attributes
     */
    public Donation() {

    }

    /**
     * Constructor for a Donation object
     * @param itemName the name of the donated item
     * @param receiveTime time that the item is received as a donation
     */
    public Donation(String itemName, Date receiveTime) {
        this.itemName = itemName;
        this.receiveTime = receiveTime;
    }

    /**
     * Constructor for a Donation object
     * @param itemName the name of the donated item
     * @param category the category of the donated item (ex. clothing, toys, etc.)
     * @param site the location the item is donated at
     * @param receiveTime time (ms) that the item is received as a donation
     */
    public Donation(String itemName, String category, String site, Date receiveTime) {
        this.itemName = itemName;
        this.category = category;
        this.site = site;
        this.receiveTime = receiveTime;
    }

    /**
     * Getter for itemName
     * @return the name of the donated item
     */
    public String getItemName() {
        return itemName;
    }

    /**
     * Getter for category
     * @return the category of the donated item
     */
    public String getCategory() {
        return category;
    }

    /**
     * Getter for description
     * @return the description of the donated item
     */
    public String getDescription() {
        return description;
    }

    /**
     * Getter for comments
     * @return the comments of the donated item
     */
    public String getComments() {
        return comments;
    }

    /**
     * Getter for site
     * @return the location the donation was made at
     */
    public String getSite() {
        return site;
    }

    /**
     * Getter for receiveTime
     * @return the time (ms) the donation was received
     */
    public Date getReceiveTime() {
        return receiveTime;
    }

    /**
     * Setter for itemName
     * @param itemName the name of the donated item
     */
    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    /**
     * Setter for category
     * @param category the category of the donated item
     */
    public void setCategory(String category) {
        this.category = category;
    }

    /**
     * Setter for description
     * @param description the description of the donated item
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Setter for comments
     * @param comments the comments on the donated item
     */
    public void setComments(String comments) {
        this.comments = comments;
    }

    /**
     * Setter for site
     * @param site the location of the donated item
     */
    public void setSite(String site) {
        this.site = site;
    }

    /**
     * Setter for receiveTime
     * @param receiveTime the time the donated item was received
     */
    public void setReceiveTime(Date receiveTime) {
        this.receiveTime = receiveTime;
    }
}
