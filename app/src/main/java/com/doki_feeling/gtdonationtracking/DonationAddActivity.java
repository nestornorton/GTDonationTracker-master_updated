package com.doki_feeling.gtdonationtracking;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.doki_feeling.gtdonationtracking.adapter.LocationAdapter;
import com.doki_feeling.gtdonationtracking.model.Donation;
import com.doki_feeling.gtdonationtracking.model.LocationList;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.Date;

public class DonationAddActivity extends BaseActivity implements
        View.OnClickListener {

    private EditText mItemNameField;
    private EditText mItemCategoryField;
    private Spinner mSiteSpinner;

    private LocationAdapter mAdapter;
    private ArrayAdapter mLocationAdapter;
    private FirebaseFirestore mFirestore;
    private Query mQuery;

    private final int LIMIT = 50;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donation_add);
        mItemNameField = findViewById(R.id.donation_input_name);
        mItemCategoryField = findViewById(R.id.donation_input_category);
        findViewById(R.id.donation_submit_button).setOnClickListener(this);

        // Firestore instance
        mFirestore = FirebaseFirestore.getInstance();
        String[] locationList = LocationList.getNameList();

        mLocationAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item,
                locationList);
        mSiteSpinner = findViewById(R.id.donation_input_site);
        mSiteSpinner.setAdapter(mLocationAdapter);
        mSiteSpinner.setSelection(0);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Method that returns whether a Menu is to be displayed to the User in the User Interface.
     * This method returns true.
     *
     * @param menu the menu to be shown to the user in the User Interface
     * @return true since we want menu to be shown
     */
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    private void onSubmitDonation() {
        Donation newDonation = new Donation(mItemNameField.getText().toString(),
                mItemCategoryField.getText().toString(),
                mSiteSpinner.getSelectedItem().toString(),
                new Date(System.currentTimeMillis()));
//        Intent intent = new Intent("Update Donation List.");
//        sendBroadcast(intent);
        mFirestore.collection("donation").add(newDonation);
        updateUI();
    }

    private void updateUI() {
        finish();
    }

    private boolean validateForm() {
        boolean valid = true;

        String fieldRequired = getResources().getString(R.string.error_field_required);

        String itemName = mItemNameField.getText().toString();
        if (TextUtils.isEmpty(itemName)) {
            mItemNameField.setError(fieldRequired);
            valid = false;
        } else {
            mItemNameField.setError(null);
        }

        String category = mItemCategoryField.getText().toString();
        if (TextUtils.isEmpty(category)) {
            mItemCategoryField.setError(fieldRequired);
            valid = false;
        } else {
            mItemCategoryField.setError(null);
        }

        return valid;
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.donation_submit_button) {
            if (validateForm()) {
                onSubmitDonation();
            }
        }
    }
}
