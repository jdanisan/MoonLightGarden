package com.example.moonlightgarden;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.moonlightgarden.models.Topic;
import com.google.firebase.auth.FirebaseAuth;
import com.example.moonlightgarden.fragment.viewsFragment.Calendar_moon;
import com.example.moonlightgarden.fragment.viewsFragment.configuser_fragment;
import com.example.moonlightgarden.fragment.viewsFragment.home_fragment;
import com.example.moonlightgarden.fragment.viewsFragment.search_fragment;
import com.example.moonlightgarden.fragment.viewsFragment.statistics_fragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.example.moonlightgarden.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import android.util.Log;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.view.View;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatDelegate;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.default_layout);


        Log.d("TAG", "Mi código de base de datos se está ejecutandokkkkkkkkkkk");
        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        //Esto fue una prueba que se realizo en primeras instancias, y nos sirve en un futuro como tests
        //DatabaseReference myRef = database.getReference("message");
        //myRef.setValue("Hello, World!");

        DatabaseReference topicsRef = database.getReference("topics");

        // Crear un objeto Topic
        Topic topic = new Topic("precios", "Precios", "Comparte y consulta precios de semillas, abonos, etc.");
        Topic topic2 = new Topic("consejos", "Consejos de plantación", "Técnicas, tiempos, riego y abono");
        Topic topic3 = new Topic("compra_semilla", "Compra de semilla o semillas", "Dónde y cómo comprar semillas fiables");
        Topic topic4 = new Topic("mejores_variedades", "Mejores variedades de semillas", "Recomendaciones por clima y propósito");

        // Guardarlo en Firebase bajo la clave "precios"
        topicsRef.child(topic.getId()).setValue(topic);
        topicsRef.child(topic2.getId()).setValue(topic2);
        topicsRef.child(topic3.getId()).setValue(topic3);
        topicsRef.child(topic4.getId()).setValue(topic4);

        // Log a message to indicate that the database code is running
        Log.d("TAG", "Mi código de base de datos se está ejecutando");

        // Configurar el idioma globalmente para Firebase
        FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.setLanguageCode("es"); // Configura el idioma deseado

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

    public void toggleTheme(View view) {
        ImageView themeToggle = view.findViewById(R.id.themeToggle);

        // Alternar modo oscuro / claro
        int nightMode = (getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK) == Configuration.UI_MODE_NIGHT_YES
                ? AppCompatDelegate.MODE_NIGHT_NO
                : AppCompatDelegate.MODE_NIGHT_YES;

        AppCompatDelegate.setDefaultNightMode(nightMode);



        // Guardar preferencia
        SharedPreferences prefs = getSharedPreferences("settings", MODE_PRIVATE);
        prefs.edit().putInt("themeMode", nightMode).apply();
    }

}