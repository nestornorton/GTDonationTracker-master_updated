package com.doki_feeling.gtdonationtracking.model;

import android.arch.lifecycle.ViewModel;

import com.doki_feeling.gtdonationtracking.Filters;

public class DonationViewModel extends ViewModel {
    private Filters mFilters;

    public DonationViewModel() {
        mFilters = Filters.getDefault();
    }

    /**
     * method that returns the Filters specified by the User to view donations by filter.
     *
     * @return the Filters in DonationViewModel
     */
    public Filters getFilters() {
        return mFilters;
    }

    /**
     * Method that allows the filters to be set in DonatoinViewModel
     *
     * @param mFilters to set mFilters class variable to
     */
    public void setFilters(Filters mFilters) {
        this.mFilters = mFilters;
    }
}
