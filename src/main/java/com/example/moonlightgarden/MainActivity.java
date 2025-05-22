package com.example.moonlightgarden;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ViewFlipper;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.moonlightgarden.fragment.viewsFragment.Calendar_moon;
import com.example.moonlightgarden.fragment.viewsFragment.ViewFragment;
import com.example.moonlightgarden.fragment.viewsFragment.activity_main;
import com.example.moonlightgarden.fragment.viewsFragment.configuser_fragment;
import com.example.moonlightgarden.fragment.viewsFragment.search_fragment;
import com.example.moonlightgarden.fragment.viewsFragment.statistics_fragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.FirebaseApp;

public class MainActivity extends AppCompatActivity {

    ViewFlipper v_flipper;
    BottomNavigationView bottomNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Enable edge-to-edge display
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        // Apply window insets to the main view
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize bottom navigation view
        bottomNavigation = findViewById(R.id.bottom_nav);

        // Mostrar el fragmento por defecto
        loadFragment(ViewFragment.newInstance("calendar")); // o "home" si así lo llamás

        // Usar implementación clásica con clase anónima (NO lambda)
        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                if(menuItem.getItemId()==R.id.nav_home){
                    loadFragment(new activity_main());
                }

                if(menuItem.getItemId()==R.id.nav_search){
                    loadFragment(new search_fragment());
                }

                if(menuItem.getItemId()==R.id.nav_statistics){
                    loadFragment(new statistics_fragment());
                }

                if(menuItem.getItemId()==R.id.nav_calendar){
                    loadFragment(new Calendar_moon());
                }

                if(menuItem.getItemId()==R.id.nav_profile){
                    loadFragment(new configuser_fragment());
                }


                return true;
            }
        });

        // Banner de imágenes
        int[] images = {
                R.drawable.anuncion1,
                R.drawable.anuncio2,
                R.drawable.anuncio3,
                R.drawable.anuncio4,
                R.drawable.anuncio5
        };

        v_flipper = findViewById(R.id.v_flipper);
        for (int image : images) {
            flipperImages(image);
        }

    }

    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();
    }

    private void enableEdgeToEdge() {
        // Aquí puedes personalizar edge-to-edge si lo usas
    }

    public void flipperImages(int imageResId) {
        ImageView imageView = new ImageView(this);
        imageView.setBackgroundResource(imageResId);

        v_flipper.addView(imageView);
        v_flipper.setFlipInterval(3000);
        v_flipper.setAutoStart(true);
        v_flipper.setInAnimation(this, android.R.anim.slide_out_right);
        v_flipper.setOutAnimation(this, android.R.anim.slide_in_left);
    }
}
