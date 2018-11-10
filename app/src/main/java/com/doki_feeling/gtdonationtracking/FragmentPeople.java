package com.doki_feeling.gtdonationtracking;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class FragmentPeople extends Fragment {

    /**
     * method newInstance for creating fragment with arguments
     *
     * @return fragmentPeople class Object
     */
    public static FragmentPeople newInstance() {
        FragmentPeople fragmentPeople = new FragmentPeople();
        return fragmentPeople;
    }


    // Inflate the view for the fragment based on layout XML
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_people, container, false);
        TextView tvLabel = (TextView) view.findViewById(R.id.fragment_people_text);
        tvLabel.setText("People");
        return view;
    }
}
