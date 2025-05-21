package com.example.moonlightgarden;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ViewFlipper;

import androidx.activity.ComponentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.firebase.FirebaseApp;

public class MainActivity extends ComponentActivity {

    ViewFlipper v_flipper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        enableEdgeToEdge();
        setContentView(R.layout.activity_main);
        //Debido a un fallo a solucionar hoy, actualmente 09/05/2025 las imagenes del banner van a estar en res/drawable/
        int images[] = {R.drawable.anuncion1,R.drawable.anuncio2,R.drawable.anuncio3,R.drawable.anuncio4,R.drawable.anuncio5};


        v_flipper= findViewById(R.id.v_flipper);

        for(int image: images){
            flipperImages(image);
        }

        // Initialize Firebase
        FirebaseApp.initializeApp(this);
    }



    private void enableEdgeToEdge() {
        // Implement the edge-to-edge functionality if needed
    }

    public void flipperImages(int images){
        ImageView imageView = new ImageView(this);
        imageView.setBackgroundResource(images);

        v_flipper.addView(imageView);
        v_flipper.setFlipInterval(3000);
        v_flipper.setAutoStart(true);

        v_flipper.setInAnimation(this, android.R.anim.slide_out_right);
        v_flipper.setOutAnimation(this,android.R.anim.slide_in_left);
    }

}