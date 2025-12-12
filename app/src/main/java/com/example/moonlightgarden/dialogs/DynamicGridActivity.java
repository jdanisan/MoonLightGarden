package com.example.moonlightgarden.dialogs;

import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.moonlightgarden.R;

public class DynamicGridActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Crear un GridLayout
        GridLayout gridLayout = new GridLayout(this);
        gridLayout.setLayoutParams(new GridLayout.LayoutParams(
                new ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                )
        ));
        gridLayout.setColumnCount(2); // Número de columnas
        gridLayout.setPadding(16, 16, 16, 16); // Espaciado interno
        gridLayout.setUseDefaultMargins(true); // Usar márgenes predeterminados

        // Datos de ejemplo para las tarjetas
        String[] foodNames = {"Acelga"};
        int[] foodImages = {R.drawable.acelga};
        String[] foodDescriptions = {"Descripción o Precio"};

        // Agregar tarjetas dinámicamente
        for (int i = 0; i < foodNames.length; i++) {
            // Crear un CardView
            CardView cardView = new CardView(this);
            cardView.setLayoutParams(new GridLayout.LayoutParams(
                    new ViewGroup.LayoutParams(
                            ViewGroup.LayoutParams.WRAP_CONTENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT
                    )
            ));
            cardView.setRadius(12); // Esquinas redondeadas
            cardView.setCardElevation(4); // Elevación
            cardView.setClickable(true); // Habilitar clic
            cardView.setFocusable(true); // Habilitar enfoque

            // Crear un LinearLayout dentro del CardView
            LinearLayout linearLayout = new LinearLayout(this);
            linearLayout.setOrientation(LinearLayout.VERTICAL); // Orientación vertical
            linearLayout.setPadding(8, 8, 8, 8); // Espaciado interno

            // Agregar ImageView
            ImageView imageView = new ImageView(this);
            imageView.setLayoutParams(new LinearLayout.LayoutParams(120, 120)); // Tamaño de la imagen
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP); // Escalado de la imagen
            imageView.setImageResource(foodImages[i]); // Imagen de recurso
            linearLayout.addView(imageView); // Agregar al LinearLayout

            // Agregar TextView para el nombre del alimento
            TextView foodNameTextView = new TextView(this);
            foodNameTextView.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            ));
            foodNameTextView.setText(foodNames[i]); // Texto del nombre
            foodNameTextView.setTextSize(16); // Tamaño del texto
            foodNameTextView.setGravity(android.view.Gravity.CENTER); // Centrar el texto
            linearLayout.addView(foodNameTextView); // Agregar al LinearLayout

            // Agregar TextView para la descripción
            TextView foodDescriptionTextView = new TextView(this);
            foodDescriptionTextView.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            ));
            foodDescriptionTextView.setText(foodDescriptions[i]); // Texto de la descripción
            foodDescriptionTextView.setTextSize(14); // Tamaño del texto
            foodDescriptionTextView.setTextColor(getResources().getColor(android.R.color.darker_gray)); // Color del texto
            foodDescriptionTextView.setGravity(android.view.Gravity.CENTER); // Centrar el texto
            linearLayout.addView(foodDescriptionTextView); // Agregar al LinearLayout

            // Agregar LinearLayout al CardView
            cardView.addView(linearLayout);

            // Agregar CardView al GridLayout
            gridLayout.addView(cardView);
        }

        // Establecer el GridLayout como la vista principal
        setContentView(gridLayout);
    }
}