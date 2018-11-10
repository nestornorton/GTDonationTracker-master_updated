package com.doki_feeling.gtdonationtracking;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.doki_feeling.gtdonationtracking.adapter.DonationAdapter;
import com.doki_feeling.gtdonationtracking.model.Donation;
import com.doki_feeling.gtdonationtracking.model.Location;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;

public class LocationDetailActivity extends BaseActivity
        implements EventListener<DocumentSnapshot>,
        DonationAdapter.OnDonationSelectedListener {

    public static final String KEY_LOCATION_ID = "key_location_id";

    private FirebaseFirestore mFirestore;
    private Location mLocation;
    private DocumentReference mLocationRef;
    private Query mQuery;
    private ListenerRegistration mLocationRegistration;


    private RecyclerView mRecyclerView;
    private ArrayList<Donation> mDonationList;
    private DonationAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_location_detail);
        String locationId = getIntent().getExtras().getString(KEY_LOCATION_ID);
        mFirestore = FirebaseFirestore.getInstance();
        mLocationRef = mFirestore.collection("locations").document(locationId);
        mLocationRegistration = mLocationRef.addSnapshotListener(this);

    }

    @Override
    public void onEvent(DocumentSnapshot snapshot, FirebaseFirestoreException e) {
        if (e != null) {
            return;
        }
        onLocationLoaded(snapshot.toObject(Location.class));
    }

    private void onLocationLoaded(Location location) {
        ((TextView) (findViewById(R.id.location_detail_addr))).setText(location.getStreetAddr());
        ((TextView) (findViewById(R.id.location_detail_city))).setText(location.getCity());
        ((TextView) (findViewById(R.id.location_detail_key))).setText(Integer.toString(location.getId()));
        ((TextView) (findViewById(R.id.location_detail_name))).setText(location.getSiteName());
        ((TextView) (findViewById(R.id.location_detail_phone))).setText(location.getPhone());
        ((TextView) (findViewById(R.id.location_detail_type))).setText(location.getType());
        ((TextView) (findViewById(R.id.location_detail_website))).setText(location.getWebsite());
        ((TextView) (findViewById(R.id.location_detail_zip))).setText(location.getZip());
        ((TextView) (findViewById(R.id.location_detail_latitude))).setText(Double.toString(location.getCord().getLatitude()));
        ((TextView) (findViewById(R.id.location_detail_longitude))).setText(Double.toString(location.getCord().getLongitude()));


        mRecyclerView = this.findViewById(R.id.recycler_donation_on_location);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mQuery = mFirestore.collection("donation").whereEqualTo("site", location.getSiteName());
        mAdapter = new DonationAdapter(mQuery, this);
        mAdapter.startListening();
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onDonationSelected(DocumentSnapshot donation) {
        // Open donation detail page (new activity)
        Intent intent = new Intent(this, DonationDetailActivity.class);
        intent.putExtra(DonationDetailActivity.KEY_DONATION_ID, donation.getId());
        startActivity(intent);
    }
}
