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
import com.squareup.picasso.Picasso;

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

        // Inicializar el ViewFlipper
        v_flipper = findViewById(R.id.v_flipper);

        // Lista de URLs de imágenes
        String[] imageUrls = {
                "https://github.com/jdanisan/ImgBannerTFG/blob/master/anuncio2.jpeg?raw=true",
                "https://github.com/jdanisan/ImgBannerTFG/blob/master/anuncio3.jpg?raw=true",
                "https://github.com/jdanisan/ImgBannerTFG/blob/master/anuncio4.jpg?raw=true",
                "https://github.com/jdanisan/ImgBannerTFG/blob/master/anuncio5.png?raw=true",
                "https://github.com/jdanisan/ImgBannerTFG/blob/master/anuncio_elefantito.jpeg?raw=true",
                "https://github.com/jdanisan/ImgBannerTFG/blob/master/anuncio_fresas.png?raw=true",
                "https://github.com/jdanisan/ImgBannerTFG/blob/master/anuncio_lechuga_r.png?raw=true",
                "https://github.com/jdanisan/ImgBannerTFG/blob/master/anuncio_naranja.png?raw=true",
                "https://github.com/jdanisan/ImgBannerTFG/blob/master/anuncion1.jpg?raw=true"
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
