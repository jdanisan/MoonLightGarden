package com.example.moonlightgarden.fragment.viewsFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.squareup.picasso.Picasso;

import com.example.moonlightgarden.R;

public class activity_main extends Fragment {

    private ViewFlipper viewFlipper;

    public activity_main() {
        // Constructor vacío requerido
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_main, container, false);

        viewFlipper = rootView.findViewById(R.id.v_flipper);
        // Aquí podrías agregar imágenes dinámicamente al ViewFlipper
        // ejemplo:
        // viewFlipper.addView(crearImagen(R.drawable.tu_imagen));
        // viewFlipper.setAutoStart(true);
        // viewFlipper.setFlipInterval(3000);
        // viewFlipper.startFlipping();
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

        // Agregar imágenes al ViewFlipper
        for (String url : imageUrls) {
            ImageView imageView = new ImageView(getContext());
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

            // Cargar la imagen con Picasso
            Picasso.get().load(url).into(imageView);

            // Agregar el ImageView al ViewFlipper
            viewFlipper.addView(imageView);
        }

        // Configurar el ViewFlipper
        viewFlipper.setAutoStart(true);
        viewFlipper.setFlipInterval(3000); // Cambiar cada 3 segundos
        viewFlipper.startFlipping();

        return rootView;
    }

    // Si vas a usar los botones onClick declarados en XML (como onButton1Click),
    // también necesitas estos métodos en tu Fragment o Activity:

    public void onButton1Click(View view) {
        Toast.makeText(getContext(), "Botón 1 presionado", Toast.LENGTH_SHORT).show();
    }

    public void onButton2Click(View view) {
        Toast.makeText(getContext(), "Botón 2 presionado", Toast.LENGTH_SHORT).show();
    }

    public void onButton3Click(View view) {
        Toast.makeText(getContext(), "Botón 3 presionado", Toast.LENGTH_SHORT).show();
    }

    public void onButton4Click(View view) {
        Toast.makeText(getContext(), "Botón 4 presionado", Toast.LENGTH_SHORT).show();
    }


}
