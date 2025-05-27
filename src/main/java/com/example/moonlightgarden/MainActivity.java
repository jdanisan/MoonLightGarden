package com.example.moonlightgarden;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ViewFlipper;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.squareup.picasso.Picasso;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import login.AuthActivity;

import com.example.moonlightgarden.fragment.viewsFragment.Calendar_moon;
import com.example.moonlightgarden.fragment.viewsFragment.ViewFragment;
import com.example.moonlightgarden.fragment.viewsFragment.activity_main;
import com.example.moonlightgarden.fragment.viewsFragment.configuser_fragment;
import com.example.moonlightgarden.fragment.viewsFragment.search_fragment;
import com.example.moonlightgarden.fragment.viewsFragment.statistics_fragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    ViewFlipper v_flipper;
    BottomNavigationView bottomNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

        if (currentUser == null) {
            // Redirect to login
            Intent intent = new Intent(MainActivity.this, AuthActivity.class);
            startActivity(intent);
            finish();
            return;
        }

        //Iniciar el BottomNavigationView
        bottomNavigation = findViewById(R.id.bottom_nav);

        //Listener ÚNICO
        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int itemId = menuItem.getItemId();

                if (itemId == R.id.nav_home) {
                    loadFragment(new activity_main());
                    return true;
                } else if (itemId == R.id.nav_search) {
                    loadFragment(new search_fragment());
                    return true;
                } else if (itemId == R.id.nav_statistics) {
                    loadFragment(new statistics_fragment());
                    return true;
                } else if (itemId == R.id.nav_calendar) {
                    loadFragment(new Calendar_moon());
                    return true;
                } else if (itemId == R.id.nav_profile) {
                    loadFragment(new configuser_fragment());
                    return true;
                }

                return false;
            }
        });

        // Mostrar el fragmento por defecto
        loadFragment(ViewFragment.newInstance("home")); // o "home" si así lo llamás

        // Inicializar el ViewFlipper
        v_flipper = findViewById(R.id.v_flipper);

        // Lista de URLs de imágenes
        String[] imageUrls = {
                "https://github.com/jdanisan/MoonLightGarden/blob/imagenes/anuncio2.jpeg",
                "https://github.com/jdanisan/MoonLightGarden/blob/imagenes/anuncio_elefantito.jpeg",
                "https://github.com/jdanisan/MoonLightGarden/blob/imagenes/anuncio_fresas.png",
                "https://github.com/jdanisan/MoonLightGarden/blob/imagenes/anuncio3.jpg",
                "https://github.com/jdanisan/MoonLightGarden/blob/imagenes/anuncio4.jpg",
                "https://github.com/jdanisan/MoonLightGarden/blob/imagenes/anuncio_lechuga_r.png",
                "https://github.com/jdanisan/MoonLightGarden/blob/imagenes/anuncio5.png",
                "https://github.com/jdanisan/MoonLightGarden/blob/imagenes/anuncio_naranja.png",
                "https://github.com/jdanisan/MoonLightGarden/blob/imagenes/anuncion1.jpg"
        };

        // Llamar al método para agregar imágenes al ViewFlipper
        flipperImagesFromUrls(imageUrls);

    }

    /**
     * Método para cargar imágenes desde URLs y agregarlas al ViewFlipper.
     *
     * @param imageUrls Lista de URLs de imágenes.
     */
    private void flipperImagesFromUrls(String[] imageUrls) {
        for (String url : imageUrls) {
            // Crear un ImageView dinámicamente
            ImageView imageView = new ImageView(this);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

            // Cargar la imagen desde la URL usando Picasso
            Picasso.get()
                    .load(url)
                    .into(imageView);

            // Agregar el ImageView al ViewFlipper
            v_flipper.addView(imageView);
        }

        // Configurar el ViewFlipper
        v_flipper.setFlipInterval(3000); // Cambiar cada 3 segundos
        v_flipper.setAutoStart(true); // Iniciar automáticamente
        v_flipper.setInAnimation(this, android.R.anim.slide_in_left); // Animación de entrada
        v_flipper.setOutAnimation(this, android.R.anim.slide_out_right); // Animación de salida
    }

    private void loadFragment(androidx.fragment.app.Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, fragment)
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
