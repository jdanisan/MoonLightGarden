package com.example.moonlightgarden.fragment.viewsFragment;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.example.moonlightgarden.R;
import com.example.moonlightgarden.models.foodItem;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class home_fragment extends Fragment {

    private ViewFlipper viewFlipper;         // Componente para el carrusel de imágenes
    private DatabaseReference databaseRef;   // Referencia a la base de datos Firebase
    private LinearLayout scrollContent;      // Contenedor para las tarjetas de productos

    public home_fragment() {
        // Constructor vacío requerido por Android
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflar el layout del fragmento
        View rootView = inflater.inflate(R.layout.home_layout, container, false);

        // Inicializar el contenedor de tarjetas
        scrollContent = rootView.findViewById(R.id.scroll_content);

        // Conectar con la base de datos de Firebase (nodo "food")
        databaseRef = FirebaseDatabase.getInstance().getReference("food");

        // Inicializar el carrusel de imágenes
        viewFlipper = rootView.findViewById(R.id.v_flipper);
        setUpViewFlipper();

        // Configurar los clics en las tarjetas principales (Judia, Tomate, etc.)
        configureCardListeners(rootView);

        // Inicializamos el botón del foro
        ImageView btnForum = rootView.findViewById(R.id.btn_forum);
        if (btnForum != null) {
            btnForum.setOnClickListener(v -> {
                requireActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main, new ForumFragment()) // tu contenedor principal
                        .addToBackStack(null)
                        .commit();
            });
        }

        return rootView;

    }

    /**
     * Configura el carrusel de imágenes utilizando ViewFlipper y Picasso para cargar imágenes online.
     */
    private void setUpViewFlipper() {
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

        // Agregar cada imagen al ViewFlipper
        for (String url : imageUrls) {
            ImageView imageView = new ImageView(getContext());
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            Picasso.get().load(url).into(imageView);
            viewFlipper.addView(imageView);
        }

        // Iniciar el carrusel automático
        viewFlipper.setAutoStart(true);
        viewFlipper.setFlipInterval(3000); // Cambia cada 3 segundos
        viewFlipper.startFlipping();
    }

    /**
     * Configura los listeners para las tarjetas fijas del layout (Judia, Tomate, etc.)
     */
    private void configureCardListeners(View rootView) {
        // Obtener las referencias a los CardViews por su ID
        CardView cardJudia = rootView.findViewById(R.id.cardJudia);
        CardView cardCalabacin = rootView.findViewById(R.id.cardCalabacin);
        CardView cardPimiento = rootView.findViewById(R.id.cardPimiento);
        CardView cardNaranja = rootView.findViewById(R.id.cardNaranja);
        CardView cardZanahoria = rootView.findViewById(R.id.cardZanahoria);
        CardView cardAcelga = rootView.findViewById(R.id.cardAcelga);
        CardView cardCebolla = rootView.findViewById(R.id.cardCebolla);
        CardView cardLechuga = rootView.findViewById(R.id.cardLechuga);

        // Asignar eventos de clic
        if (cardJudia != null) cardJudia.setOnClickListener(v -> fetchProductCard("judia"));
        if (cardCalabacin != null) cardCalabacin.setOnClickListener(v -> fetchProductCard("calabacin"));
        if (cardPimiento != null) cardPimiento.setOnClickListener(v -> fetchProductCard("pimiento"));
        if (cardNaranja != null) cardNaranja.setOnClickListener(v -> fetchProductCard("naranja"));
        if (cardZanahoria != null) cardZanahoria.setOnClickListener(v -> fetchProductCard("zanahoria"));
        if (cardAcelga != null) cardAcelga.setOnClickListener(v -> fetchProductCard("acelga"));
        if (cardCebolla != null) cardCebolla.setOnClickListener(v -> fetchProductCard("cebolla"));
        if (cardLechuga != null) cardLechuga.setOnClickListener(v -> fetchProductCard("lechuga"));
    }

    /**
     * Obtiene un producto desde Firebase y crea dinámicamente una tarjeta para mostrarlo.
     *
     * @param searchTerm Palabra clave para buscar el producto por nombre.
     */
    private void fetchProductCard(String searchTerm) {
        // Limpiar el contenedor antes de mostrar nuevos resultados
        scrollContent.removeAllViews();

        // Escuchar una sola vez desde la base de datos
        databaseRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot data : snapshot.getChildren()) {
                    foodItem item = data.getValue(foodItem.class);
                    if (item == null) continue;

                    // Comparar el nombre del producto con el término de búsqueda
                    if (item.getFood_name().toLowerCase().contains(searchTerm)) {
                        // Inflar el layout de la tarjeta desde XML
                        View cardView = LayoutInflater.from(getContext())
                                .inflate(R.layout.food_card_item, scrollContent, false);

                        // Configurar los elementos visuales de la tarjeta
                        TextView name = cardView.findViewById(R.id.product_name);
                        TextView desc = cardView.findViewById(R.id.product_description);
                        ImageView image = cardView.findViewById(R.id.product_image);

                        name.setText(item.getFood_name());
                        desc.setText(item.getFood_description());
                        Picasso.get().load(item.getfood_image_url()).into(image);

                        // Alternar la visibilidad de la descripción al hacer clic


                            AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
                            LayoutInflater inflater = getLayoutInflater();

                            // Inflar tu layout personalizado del Alert
                            View alertView = inflater.inflate(R.layout.product_alert, null);

                            // Si ese layout tiene texto, imagen, etc., puedes llenarlos aquí
                            TextView alertName = alertView.findViewById(R.id.alert_name);
                            TextView alertDesc = alertView.findViewById(R.id.alert_desc);
                            ImageView alertImage = alertView.findViewById(R.id.alert_image);

                            alertName.setText(item.getFood_name());
                            alertDesc.setText(item.getFood_description());
                            Picasso.get().load(item.getfood_image_url()).into(alertImage);

                            builder.setView(alertView);
                            builder.setTitle("Información del producto")
                                    .setPositiveButton("¡Lo plantaré!", null)
                                    .setNegativeButton("Cerrar", null);

                            AlertDialog dialog = builder.create();
                            dialog.show();




                        break; // Solo mostrar un resultado
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "Error al cargar los datos", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
