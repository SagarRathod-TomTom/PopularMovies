/**
 * Copyright (C) June 2016
 * The Popular Movies Stage II project
 */
package com.sagarrathod.popularmovies.activities;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.sagarrathod.popularmovies.R;
import com.sagarrathod.popularmovies.fragments.SettingsFragment;

/**
 * Shows the application settings.
 *
 * @author Sagar Rathod
 * @version 1.0
 */

public class SettingsActivity extends AppCompatActivity {

    /**
     * Shows [@link SettingsFragment}.
     *
     * @param savedInstanceState The saved instance state of this activity.
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

       ActionBar actionBar = getSupportActionBar();
        if(actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        // Display the fragment as the main content.
        getFragmentManager().beginTransaction()
                .replace(R.id.container, new SettingsFragment())
                .commit();
    }

}
