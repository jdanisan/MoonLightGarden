package com.example.moonlightgarden;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import com.example.moonlightgarden.fragment.viewsFragment.Calendar_moon;
import com.example.moonlightgarden.fragment.viewsFragment.configuser_fragment;
import com.example.moonlightgarden.fragment.viewsFragment.home_fragment;
import com.example.moonlightgarden.fragment.viewsFragment.search_fragment;
import com.example.moonlightgarden.fragment.viewsFragment.statistics_fragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.example.moonlightgarden.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.default_layout);

        // Load the initial fragment
        if (savedInstanceState == null) {
            loadFragment(new home_fragment());
        }

        // Configure the BottomNavigationView
        BottomNavigationView bottomNav = findViewById(R.id.bottom_nav);
        bottomNav.setOnItemSelectedListener(item -> {
            Fragment selectedFragment = null;

            if (item.getItemId() == R.id.nav_calendar) {
                selectedFragment = new Calendar_moon();
            } else if (item.getItemId() == R.id.nav_home) {
                selectedFragment = new home_fragment();
            } else if (item.getItemId() == R.id.nav_profile) {
                selectedFragment = new configuser_fragment();
            } else if (item.getItemId() == R.id.nav_search) {
                selectedFragment = new search_fragment();
            } else if (item.getItemId() == R.id.nav_statistics) {
                selectedFragment = new statistics_fragment();
            } else {
                selectedFragment = new home_fragment();
            }

            if (selectedFragment != null) {
                loadFragment(selectedFragment);
            }
            return true;
        });
    }

    private void loadFragment(@NonNull Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit();
    }
}