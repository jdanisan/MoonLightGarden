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

import com.example.moonlightgarden.R;
import com.squareup.picasso.Picasso;

public class home_fragment extends Fragment {

    private ViewFlipper viewFlipper;

    public home_fragment() {
        // Empty constructor required
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.home_layout, container, false);

        // Configure ViewFlipper
        viewFlipper = rootView.findViewById(R.id.v_flipper);
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

        for (String url : imageUrls) {
            ImageView imageView = new ImageView(getContext());
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            Picasso.get().load(url).into(imageView);
            viewFlipper.addView(imageView);
        }
        viewFlipper.setAutoStart(true);
        viewFlipper.setFlipInterval(3000);
        viewFlipper.startFlipping();

        // Configure CardView for btn2_pp
        /*CardView btn2 = rootView.findViewById(R.id.);
        ImageView imageView = btn2.findViewById(R.id.iv_tomate);
        TextView nameTextView = btn2.findViewById(R.id.tv_tomate);
        TextView descriptionTextView = btn2.findViewById(R.id.tv_tomate_description);

        // Set dynamic content for btn2
        imageView.setImageResource(R.drawable.acelga);
        nameTextView.setText("Acelga");
        descriptionTextView.setText("Descripción de la acelga");*/

        return rootView;
    }

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