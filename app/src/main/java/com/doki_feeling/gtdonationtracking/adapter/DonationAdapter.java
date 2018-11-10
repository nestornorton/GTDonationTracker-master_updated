package com.doki_feeling.gtdonationtracking.adapter;

import com.doki_feeling.gtdonationtracking.DonationDetailActivity;
import com.doki_feeling.gtdonationtracking.R;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.doki_feeling.gtdonationtracking.model.Donation;
import com.doki_feeling.gtdonationtracking.model.Donation;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class DonationAdapter extends FirestoreAdapter<DonationAdapter.ViewHolder> {

    public interface OnDonationSelectedListener {

        void onDonationSelected(DocumentSnapshot donation);
    }

    private OnDonationSelectedListener mListener;

    public DonationAdapter(Query query, OnDonationSelectedListener listener) {
        super(query);
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_donation, parent, false);
        return new ViewHolder(v);
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.bind(getSnapshot(position), mListener);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView nameView;
        TextView siteView;
        TextView typeView;

        public ViewHolder(View itemView) {
            super(itemView);
            nameView = itemView.findViewById(R.id.donation_card_name);
            siteView = itemView.findViewById(R.id.donation_card_location);
            typeView = itemView.findViewById(R.id.donation_card_category);
        }
	
        /**
         * Translates DocumentSnapshot objects into Donation objects
	 * and set the parameters of the new donation item
         * New donation item is then used to setup the view for the new item
         *
	 * @param snapshot        The DocumentSnapshot object to be 
	 *                        convertend into a donation item
	 * @param listener        The onDonationSelectedListener used to
	 *                        call the onDonationSelected method
         *                        on the new donation item
         * @return                <code>void</code>
	 */
        public void bind(final DocumentSnapshot snapshot,
                         final OnDonationSelectedListener listener) {
            Donation donation = snapshot.toObject(Donation.class);
            nameView.setText(donation.getItemName());
            siteView.setText(donation.getSite());
            typeView.setText(donation.getCategory());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        listener.onDonationSelected(snapshot);
                    }
                }
            });

        }

    }
}
