/**
 * Copyright (C) June 2016
 * The Popular Movies Stage II project
 */

package com.sagarrathod.popularmovies.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.sagarrathod.popularmovies.R;
import com.sagarrathod.popularmovies.fragments.MovieDetailsFragment;
import com.sagarrathod.popularmovies.fragments.PosterGridFragment;

/**
 * It is the main activity and gets started when an application is launched.
 *
 * @author Sagar Rathod
 * @version 1.0
 */

public class PosterGridActivity extends AppCompatActivity implements TabLayout.OnTabSelectedListener {

    /**
     * Sets the activity_image_grid_view layout for this activity.
     * Shows {@link PosterGridFragment} layout if a device is in portrait orientation.
     * Shows {@link PosterGridFragment} and
     * {@link MovieDetailsFragment} if a device is in landscape orientation.
     * Initializes the defaults values for shared preference.
     *
     * @param savedInstanceState The saved instance state of this activity.
     */

    private PosterGridFragment mPosterGridFragment;
    private DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_grid_view);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer);

        // Set behavior of Navigation drawer
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    // This method will trigger on item Click of navigation menu
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        // Set item in checked state
                        menuItem.setChecked(true);
                        // TODO: handle navigation
                        // Closing drawer on item click
                        mDrawerLayout.closeDrawers();
                        return true;
                    }
                });

        // Adding Toolbar to Main screen
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Adding menu icon to Toolbar
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {

            VectorDrawableCompat indicator
                    = VectorDrawableCompat.create(getResources(), R.drawable.ic_menu, getTheme());
            indicator.setTint(ResourcesCompat.getColor(getResources(),R.color.white,getTheme()));

            supportActionBar.setHomeAsUpIndicator(indicator);
            supportActionBar.setDisplayHomeAsUpEnabled(true);
        }

        TabLayout tabLayout = (TabLayout) findViewById(R.id.movie_tab);

        String arr[] = getResources().getStringArray(R.array.movie_entries);

        tabLayout.addTab(tabLayout.newTab().setText(arr[0]));
        tabLayout.addTab(tabLayout.newTab().setText(arr[1]));
        tabLayout.addTab(tabLayout.newTab().setText(arr[2]));

        tabLayout.addOnTabSelectedListener(this);

        FragmentManager fragmentManager = getSupportFragmentManager();

        mPosterGridFragment = (PosterGridFragment)
                fragmentManager.findFragmentById(R.id.poster_grid_fragment);

        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);
    }

    /**
     * Handles the Settings menu items event and launch a SettingsActivity.
     *
     * @param item The selected menu item from action menu.
     * @return boolean The successful selection of menu item.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
            return true;
        }else if (id == android.R.id.home) {
            mDrawerLayout.openDrawer(GravityCompat.START);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        int pos = tab.getPosition();
        String movieEntryValues[] = getResources().getStringArray(R.array.movie_entry_values);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(getString(R.string.movies_type), movieEntryValues[pos]);
        editor.commit();

        mPosterGridFragment.changeMovieList();

    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

}
