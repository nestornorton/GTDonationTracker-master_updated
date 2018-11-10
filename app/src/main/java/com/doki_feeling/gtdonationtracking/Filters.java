package com.doki_feeling.gtdonationtracking;

import android.content.Context;
import android.text.TextUtils;
import com.google.firebase.firestore.Query;


/**
 * Object for passing filters around.
 */
public class Filters {

    private String searchCondition = null;
    private String name = null;
    private String category = null;
    private String site = null;

    public Filters() {}

    // gets a new instance of filter with all variables as NULL
    public static Filters getDefault() {
        Filters filters = new Filters();
        return filters;
    }

    // checks if there is a filter on the category
    public boolean hasCategory() {
        return !(TextUtils.isEmpty(category));
    }

    // checks if there is a filter on the site
    public boolean hasSite() {
        return !(TextUtils.isEmpty(site));
    }

    // checks if there is a filter on the name
    public boolean hasName() {
        return !(TextUtils.isEmpty(name));
    }

    // return the filter on the category
    public String getCategory() {
        return category;
    }

    // sets a filter on the category
    public void setCategory(String category) {
        this.category = category;
    }

    // returns the filter on the site
    public String getSite() {
        return site;
    }

    // sets a filter on the site
    public void setSite(String site) {
        this.site = site;
    }

    // returns the filter on the name
    public String getName() {
        return name;
    }

    // sets a filter on the name
    public void setName(String name) {
        this.name = name;
    }

}
