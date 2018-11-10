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

import com.doki_feeling.gtdonationtracking.adapter.LocationAdapter;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;

public class FragmentLocation extends Fragment implements
        LocationAdapter.OnLocationSelectedListener {


    private static final String TAG = "FragmentLocation";

    private static final int LIMIT = 50;

    private FirebaseFirestore mFirestore;
    private Query mQuery;

    private LocationAdapter mAdapter;

    private RecyclerView mLocationRecycler;
    private LinearLayoutManager mLayoutManager;
    private ViewGroup mEmptyView;

    private FloatingActionButton mLocationButton;


    /**
     * newInstance constructor for creating fragment with arguments
     *
     * @return FragmentLocation Object
     */
    public static FragmentLocation newInstance() {
        return new FragmentLocation();
    }

    // Inflate the view for the fragment based on layout XML
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Firestore instance
        mFirestore = FirebaseFirestore.getInstance();

        // Get ${LIMIT} locations
        mQuery = mFirestore.collection("locations").limit(LIMIT);

        // RecyclerView
        mAdapter = new LocationAdapter(mQuery, this) {
            @Override
            protected void onDataChanged() {
                // Hide data if query is empty
                if (getItemCount() == 0) {
                    Log.d(TAG, "No entry from the query!");
                    mLocationRecycler.setVisibility(View.GONE);
                    mEmptyView.setVisibility(View.VISIBLE);
                } else {
                    mLocationRecycler.setVisibility(View.VISIBLE);
                    mEmptyView.setVisibility(View.GONE);
                }
            }

            @Override
            protected void onError(FirebaseFirestoreException e) {
                // Show a snackbar on errors
                Snackbar.make(getActivity().findViewById(android.R.id.content),
                        "Error: Database query failed.", Snackbar.LENGTH_LONG).show();
            }
        };

        View view = inflater.inflate(R.layout.fragment_location, container, false);

        return view;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "Location fragment created.");

        mLocationRecycler = getView().findViewById(R.id.recycler_location);
        mLayoutManager = new LinearLayoutManager(getView().getContext());
        mLocationRecycler.setLayoutManager(mLayoutManager);
        mAdapter = new LocationAdapter(mQuery, this);
        mAdapter.startListening();
        mLocationRecycler.setAdapter(mAdapter);

        // Set floating button
        FloatingActionButton fab = getView().findViewById(R.id.show_location_button);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), MapsActivity.class);
                startActivity(intent);
            }
        });

        Log.d(TAG, "" + mAdapter.getItemCount());
    }

    @Override
    public void onLocationSelected(DocumentSnapshot location) {
        // Open location detail page (new activity)
        Intent intent = new Intent(getContext(), LocationDetailActivity.class);
        intent.putExtra(LocationDetailActivity.KEY_LOCATION_ID, location.getId());
        startActivity(intent);
    }
}
