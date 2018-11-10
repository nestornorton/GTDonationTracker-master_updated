package com.doki_feeling.gtdonationtracking.adapter;

import com.doki_feeling.gtdonationtracking.LocationDetailActivity;
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
import android.widget.ImageButton;
import android.widget.TextView;

import com.doki_feeling.gtdonationtracking.model.Location;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class LocationAdapter extends FirestoreAdapter<LocationAdapter.ViewHolder> {

    public interface OnLocationSelectedListener {

        void onLocationSelected(DocumentSnapshot location);
    }

    private OnLocationSelectedListener mListener;

    public LocationAdapter(Query query, OnLocationSelectedListener listener) {
        super(query);
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_location, parent, false);
        return new ViewHolder(v);
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.bind(getSnapshot(position), mListener);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView nameView;
        TextView addrView;
        TextView typeView;
        TextView phoneView;

        public ViewHolder(View itemView) {
            super(itemView);
            nameView = itemView.findViewById(R.id.location_card_name);
            addrView = itemView.findViewById(R.id.location_card_street);
            typeView = itemView.findViewById(R.id.location_card_type);
            phoneView = itemView.findViewById(R.id.location_card_phone);
        }

        /**
         * Translates DocumentSnapshot objects into Donation objects
         * and set the parameters of the new donation item
         * New donation item is then used to setup the view for the new item
         *
         * @param snapshot The DocumentSnapshot object to be
         *                 converted into a donation item
         * @param listener The onDonationSelectedListener used to
         *                 call the onDonationSelected method
         *                 on the new donation item
         * @return <code>void</code>
         */
        public void bind(final DocumentSnapshot snapshot,
                         final OnLocationSelectedListener listener) {
            Location location = snapshot.toObject(Location.class);
            nameView.setText(location.getSiteName());
            addrView.setText(location.getStreetAddr());
            typeView.setText(location.getType());
            phoneView.setText(location.getPhone());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        listener.onLocationSelected(snapshot);
                    }
                }
            });

        }

    }
}
