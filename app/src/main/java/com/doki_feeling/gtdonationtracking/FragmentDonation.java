package com.doki_feeling.gtdonationtracking;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.doki_feeling.gtdonationtracking.adapter.DonationAdapter;
import com.doki_feeling.gtdonationtracking.model.Donation;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;

public class FragmentDonation extends Fragment implements
        DonationAdapter.OnDonationSelectedListener,
        FilterDialogFragment.FilterListener {

    private static final String TAG = "FragmentDonation";

    private static final int LIMIT = 50;

    private FirebaseFirestore mFirestore;
    private Query mQuery;

    private DonationAdapter mAdapter;

    private RecyclerView mDonationRecycler;
    private LinearLayoutManager mLayoutManager;
    private FilterDialogFragment mFilterDialog;


    /**
     * newInstance constructor for creating fragment with arguments
     *
     * @return FragmentDonation class Object
     */

    public static FragmentDonation newInstance() {
        return new FragmentDonation();
    }

    // Inflate the view for the fragment based on layout XML
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Firestore instance
        mFirestore = FirebaseFirestore.getInstance();

        // Get ${LIMIT} donations
        mQuery = mFirestore.collection("donation").limit(LIMIT);

        // RecyclerView
        mAdapter = new DonationAdapter(mQuery, this) {
            @Override
            protected void onDataChanged() {
                // Hide data if query is empty
                if (getItemCount() == 0) {
                    Toast.makeText(getActivity(), getString(R.string.no_entry),
                            Toast.LENGTH_SHORT).show();
                    mDonationRecycler.setVisibility(View.GONE);
                } else {
                    mDonationRecycler.setVisibility(View.VISIBLE);
                }
            }

            @Override
            protected void onError(FirebaseFirestoreException e) {
                // Show a snackbar on errors
                Snackbar.make(getActivity().findViewById(android.R.id.content),
                        "Error: Database query failed.", Snackbar.LENGTH_LONG).show();
            }
        };

        View view = inflater.inflate(R.layout.fragment_donation, container, false);
        return view;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        mDonationRecycler = getView().findViewById(R.id.recycler_donation);
        mLayoutManager = new LinearLayoutManager(getView().getContext());
        mDonationRecycler.setLayoutManager(mLayoutManager);
        mAdapter.startListening();
        mDonationRecycler.setAdapter(mAdapter);

        // Set floating button for Add Donation
        FloatingActionButton addButton = getView().findViewById(R.id.donation_add_button);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), DonationAddActivity.class);
                startActivity(intent);
            }
        });

        // Set floating button for Filtering
        mFilterDialog = new FilterDialogFragment();
        FloatingActionButton filterButton = getView().findViewById(R.id.filter_button);
        filterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mFilterDialog.show(getChildFragmentManager(), FilterDialogFragment.TAG);
            }
        });

    }

    @Override
    public void onDonationSelected(DocumentSnapshot donation) {
        // Open donation detail page (new activity)
        Intent intent = new Intent(getContext(), DonationDetailActivity.class);
        intent.putExtra(DonationDetailActivity.KEY_DONATION_ID, donation.getId());
        startActivity(intent);
    }


    @Override
    public void onFilter(Filters filters) {
        // Construct query basic query
        Query query = mFirestore.collection("donation");

        // Category (equality filter)
        if (filters.hasCategory()) {
            query = query.whereEqualTo(Donation.FIELD_CATEGORY, filters.getCategory());
        }

        // City (equality filter)
        if (filters.hasSite()) {
            query = query.whereEqualTo(Donation.FIELD_SITE, filters.getSite());
        }

        // Price (equality filter)
        if (filters.hasName()) {
            query = query.whereEqualTo(Donation.FIELD_NAME, filters.getName());
        }

        // Limit items
        query = query.limit(LIMIT);

        // Update the query
        mAdapter.setQuery(query);

        Log.d(TAG, "Set new query for the adapter: " + filters.getSite());
//
//        // Set header
//        mCurrentSearchView.setText(Html.fromHtml(filters.getSearchDescription(this)));
//        mCurrentSortByView.setText(filters.getOrderDescription(this));
//
//        // Save filters
    }

}
