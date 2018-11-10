package com.doki_feeling.gtdonationtracking;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.doki_feeling.gtdonationtracking.model.LocationList;

/**
 * Dialog Fragment containing filter form.
 */
public class FilterDialogFragment extends DialogFragment {

    public static final String TAG = "FilterDialog";

    interface FilterListener {

        void onFilter(Filters filters);

    }

    private View view;

    private TextView mCategoryView;
    private TextView mNameView;
    private Spinner mSiteSpinner;
    private Button searchButton;
    private Button cancelButton;

    private ArrayAdapter mLocationAdapter;
    private FilterListener mFilterListener;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_filter_dialog, container, false);
        mCategoryView = view.findViewById(R.id.editCategory);
        mNameView = view.findViewById(R.id.editName);

        String[] locationList = LocationList.getNameListWithAll();
        mLocationAdapter = new ArrayAdapter<String>(view.getContext(),
                android.R.layout.simple_spinner_item,
                locationList);
        mSiteSpinner = view.findViewById(R.id.filter_site_spinner);
        mSiteSpinner.setAdapter(mLocationAdapter);
        mSiteSpinner.setSelection(0);

        searchButton = view.findViewById(R.id.buttonSearch);
        cancelButton = view.findViewById(R.id.buttonCancel);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mFilterListener != null) {
                    mFilterListener.onFilter(getFilters());
                    dismiss();
                }
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mFilterListener = (FilterListener) getParentFragment();
    }

    @Override
    public void onResume() {
        super.onResume();
        getDialog().getWindow().setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
    }


    @Nullable
    private String getCategory() {
        String category = (String) mCategoryView.getText().toString();
        return category;
    }

    @Nullable
    private String getName() {
        String name = (String) mNameView.getText().toString();
        Log.d(TAG, "Search name: " + name);
        return name;
    }

    @Nullable
    private String getSelectedLocation() {
        String selected = (String) mSiteSpinner.getSelectedItem();
        if (selected.equals("ANY LOCATION")) {
            return null;
        } else {
            return selected;
        }
    }


    /**
     * Method that resets filter selection to be default if view is not null
     *
     * @return <code>void</void>
     */
    public void resetFilters() {
        if (view != null) {
            mSiteSpinner.setSelection(0);
        }
    }

    /**
     * Method that returns the Filters and if view is not null, the Name, Category and Site
     * filters set.
     *
     * @return Filters object that may have specified fields (name, category, site)
     */
    public Filters getFilters() {
        Filters filters = new Filters();

        if (view != null) {
            filters.setName(getName());
            filters.setCategory(getCategory());
            filters.setSite(getSelectedLocation());
        }

        return filters;
    }
}
