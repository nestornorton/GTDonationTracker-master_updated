package com.doki_feeling.gtdonationtracking;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import com.doki_feeling.gtdonationtracking.model.AccountOwner;
import com.doki_feeling.gtdonationtracking.model.Donation;
import com.doki_feeling.gtdonationtracking.model.DonationList;
import com.doki_feeling.gtdonationtracking.model.Location;
import com.doki_feeling.gtdonationtracking.model.LocationList;
import com.doki_feeling.gtdonationtracking.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;


public class MainActivity extends BaseActivity {

    private FragmentPagerAdapter adapterViewPager;

    private DrawerLayout mDrawerLayout;
    private FirebaseAuth mAuth;
    private FirebaseFirestore mDatabase;
    private User mUser;

    private static Fragment mFragmentPeople;
    private static Fragment mFragmentLocation;
    private static Fragment mFragmentDonation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseFirestore.getInstance();
        mUser = AccountOwner.getInstance();
        mDrawerLayout = findViewById(R.id.drawer_layout);

        // Setup fragment pager for bottom view navigation
        final ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);
        adapterViewPager = new MyPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapterViewPager);

        // Setup top toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu_white_24dp);

        // Setup sidebar
        NavigationView sidebarView = findViewById(R.id.sidebar);
        sidebarView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                        menuItem.setChecked(true);
                        switch (menuItem.getItemId()) {
                            case R.id.sidebar_logout:
                                mDatabase.disableNetwork();
                                mAuth.signOut();
                                mUser.signOut();
                                mFragmentDonation.onDestroy();
                                mFragmentLocation.onDestroy();
                                mFragmentPeople.onDestroy();
                                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                                startActivity(intent);
                                finish();
                                break;
                            case R.id.sidebar_import_location:
                                locationImporter();
                                break;
                        }
                        mDrawerLayout.closeDrawers();
                        menuItem.setChecked(false);
                        return true;
                    }
                }
        );

        // Setup bottom bar
        BottomNavigationView bottomNavigation = findViewById(R.id.navigation);
        bottomNavigation.setOnNavigationItemSelectedListener(
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.navigation_people:
                            viewPager.setCurrentItem(0);
                            return true;
                        case R.id.navigation_donation:
                            viewPager.setCurrentItem(1);
                            return true;
                        case R.id.navigation_location:
                            viewPager.setCurrentItem(2);
                            return true;
                    }
                    return false;
                }
            }
        );

        // Close login activity if it's still running
        Intent intent = new Intent("Close LoginActivity.");
        sendBroadcast(intent);

        // Initialize all fragments for data prefetch
        // ! It doesn't work !
        mFragmentLocation = FragmentLocation.newInstance();
        mFragmentPeople = FragmentPeople.newInstance();
        mFragmentDonation = FragmentDonation.newInstance();

        LocationList.clear();
        mDatabase.collection("locations")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (DocumentSnapshot document : task.getResult()) {
                                LocationList.add(document.toObject(Location.class));
                            }
                        }
                    }
                });

        DonationList.clear();
        mDatabase.collection("donation")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (DocumentSnapshot document : task.getResult()) {
                                DonationList.add(document.toObject(Donation.class));
                            }
                        }
                    }
                });
//
//        FirebaseFirestore mFirestore = FirebaseFirestore.getInstance();
//
//        mLocationRef = mFirestore.collection("locations");
//        LocationList

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private Boolean exit = false;
    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawers();
        } else {
            if (exit) {
                finish(); // finish activity
            } else {
                Toast.makeText(this, getString(R.string.exit_hint),
                        Toast.LENGTH_SHORT).show();
                exit = true;
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        exit = false;
                    }
                }, 3 * 1000);

            }
        }
    }

    /**
     * Imports location data from InputStream and sets the data to create a new location. New location
     * is added to the database.
     */
    public void locationImporter() {
        Location this_site;

        InputStream is = getResources().openRawResource(R.raw.location_data);
        BufferedReader br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));

        String line;
        Toast.makeText(this, getString(R.string.import_csv_start),
                Toast.LENGTH_SHORT).show();
        try {
            br.readLine(); //get rid of header line
            while ((line = br.readLine()) != null) {
                String[] tokens = line.split(",");
                this_site = new Location()
                        .id(Integer.parseInt(tokens[0]))
                        .siteName(tokens[1])
                        .cord(Double.parseDouble(tokens[2]), Double.parseDouble(tokens[3]))
                        .streetAddr(tokens[4])
                        .city(tokens[5])
                        .state(tokens[6])
                        .zip(tokens[7])
                        .type(tokens[8])
                        .phone(tokens[9])
                        .website(tokens[10]);
                //mLocationList.add(this_site);
                mDatabase.collection("locations").add(this_site);
            }
            br.close();
            Toast.makeText(this, getString(R.string.import_csv_end),
                    Toast.LENGTH_SHORT).show();
        }  catch (IOException e) {
            Toast.makeText(this, getString(R.string.import_csv_error),
                    Toast.LENGTH_SHORT).show();
        }
    }

    public static class MyPagerAdapter extends FragmentPagerAdapter {
        private static int NUM_ITEMS = 3;

        public MyPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        // Returns total number of pages
        @Override
        public int getCount() {
            return NUM_ITEMS;
        }

        // Returns the fragment to display for that page
        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0: // Fragment # 0 - This will show FirstFragment
                    return mFragmentPeople;
                case 1: // Fragment # 0 - This will show FirstFragment different title
                    return mFragmentDonation;
                case 2: // Fragment # 1 - This will show SecondFragment
                    return mFragmentLocation;
                default:
                    return null;
            }
        }

        // Returns the page title for the top indicator
        @Override
        public CharSequence getPageTitle(int position) {
            return "Page " + position;
        }

    }

}
