/**
 * Copyright (C) June 2016
 * The Popular Movies Stage II project
 */

package com.sagarrathod.popularmovies.activities;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
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

public class PosterGridActivity extends AppCompatActivity {

    /**
     * Sets the activity_image_grid_view layout for this activity.
     * Shows {@link PosterGridFragment} layout if a device is in portrait orientation.
     * Shows {@link PosterGridFragment} and
     * {@link MovieDetailsFragment} if a device is in landscape orientation.
     * Initializes the defaults values for shared preference.
     *
     * @param savedInstanceState The saved instance state of this activity.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_grid_view);

        // Adding Toolbar to Main screen
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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
        }
        return super.onOptionsItemSelected(item);
    }
}
