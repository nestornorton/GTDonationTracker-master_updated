package com.doki_feeling.gtdonationtracking;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.doki_feeling.gtdonationtracking.adapter.DonationAdapter;
import com.doki_feeling.gtdonationtracking.model.Donation;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;

import java.util.ArrayList;

public class DonationDetailActivity extends BaseActivity
        implements EventListener<DocumentSnapshot> {

    public static final String KEY_DONATION_ID = "key_donation_id";

    private FirebaseFirestore mFirestore;
    private Donation mDonation;
    private DocumentReference mDonationRef;
    private ListenerRegistration mDonationRegistration;

    private RecyclerView mRecyclerView;
    private ArrayList<Donation> mDonationList;
    private DonationAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donation_detail);
        String donationId = getIntent().getExtras().getString(KEY_DONATION_ID);
        mFirestore = FirebaseFirestore.getInstance();
        mDonationRef = mFirestore.collection("donation").document(donationId);
        mDonationRegistration = mDonationRef.addSnapshotListener(this);
    }

    @Override
    public void onEvent(DocumentSnapshot snapshot, FirebaseFirestoreException e) {
        if (e != null) {
            return;
        }
        onDonationLoaded(snapshot.toObject(Donation.class));
    }

    private void onDonationLoaded(Donation donation) {
        ((TextView) (findViewById(R.id.donation_detail_addr))).setText(donation.getSite());
        ((TextView) (findViewById(R.id.donation_detail_name))).setText(donation.getItemName());
        ((TextView) (findViewById(R.id.donation_detail_type))).setText(donation.getCategory());
        ((TextView) (findViewById(R.id.donation_detail_time))).setText(donation.getReceiveTime().toString());
    }

    @Override
    public void onStop() {
        super.onStop();

        if (mDonationRegistration != null) {
            mDonationRegistration.remove();
            mDonationRegistration = null;
        }
    }
}
