package com.doki_feeling.gtdonationtracking;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class FragmentItem extends Fragment {
    // Store instance variables
    private String title;
    private int page;

    /**
     * newInstance constructor for creating fragment with arguments
     *
     * @return fragmentItem Class Object
     */
    public static FragmentItem newInstance() {
        FragmentItem fragmentItem = new FragmentItem();
        return fragmentItem;
    }

    // Inflate the view for the fragment based on layout XML
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item, container, false);
        TextView tvLabel = (TextView) view.findViewById(R.id.fragment_item_text);
        tvLabel.setText("Item");
        return view;
    }
}
