package com.example.moonlightgarden.fragment.viewsFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

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
                "https://raw.githubusercontent.com/jdanisan/ImgBannerTFG/master/anuncio2.jpeg",
                "https://raw.githubusercontent.com/jdanisan/ImgBannerTFG/master/anuncio3.jpg",
                "https://raw.githubusercontent.com/jdanisan/ImgBannerTFG/master/anuncio4.jpg",
                "https://raw.githubusercontent.com/jdanisan/ImgBannerTFG/master/anuncio5.png",
                "https://raw.githubusercontent.com/jdanisan/ImgBannerTFG/master/anuncio_elefantito.jpeg",
                "https://raw.githubusercontent.com/jdanisan/ImgBannerTFG/master/anuncio_fresas.png",
                "https://raw.githubusercontent.com/jdanisan/ImgBannerTFG/master/anuncio_lechuga_r.png",
                "https://raw.githubusercontent.com/jdanisan/ImgBannerTFG/master/anuncio_naranja.png",
                "https://raw.githubusercontent.com/jdanisan/ImgBannerTFG/master/anuncion1.jpg"
        };
        //Se ha agregado el raw delante de las redirecciones para poder añadir las imágenes desde el repositorio de GitHub

        for (String url : imageUrls) {
            ImageView imageView = new ImageView(getContext());
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            Picasso.get().load(url).into(imageView);
            viewFlipper.addView(imageView);
        }
        viewFlipper.setAutoStart(true);
        viewFlipper.setFlipInterval(3000);
        viewFlipper.startFlipping();




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