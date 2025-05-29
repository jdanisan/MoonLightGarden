package com.example.moonlightgarden.fragment.viewsFragment;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.moonlightgarden.R;
import com.example.moonlightgarden.models.foodItem;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class search_fragment extends Fragment {

    private LinearLayout scrollContent;
    private DatabaseReference databaseRef;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.search_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        scrollContent = view.findViewById(R.id.scroll_content);
        EditText searchInput = view.findViewById(R.id.search_input);

        databaseRef = FirebaseDatabase.getInstance().getReference("food");

        databaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                scrollContent.removeAllViews(); // Limpiar antes de añadir nuevas vistas

                for (DataSnapshot data : snapshot.getChildren()) {
                    foodItem item = data.getValue(foodItem.class);
                    if (item == null) continue;

                    // Inflar la tarjeta personalizada
                    View cardView = LayoutInflater.from(getContext())
                            .inflate(R.layout.food_card_item, scrollContent, false);

                    // Referencias a los elementos del XML
                    TextView name = cardView.findViewById(R.id.product_name);
                    TextView desc = cardView.findViewById(R.id.product_description);
                    ImageView image = cardView.findViewById(R.id.product_image);

                    // Setear datos
                    name.setText(item.getFood_name());
                    desc.setText(item.getFood_description());
                    Picasso.get().load(item.getfood_image_url()).into(image);

                    // Añadir listener para expandir la descripción
                    cardView.setOnClickListener(v -> {
                        if (desc.getMaxLines() == 1) {
                            desc.setMaxLines(Integer.MAX_VALUE); // Expandir descripción
                        } else {
                            desc.setMaxLines(1); // Colapsar descripción
                        }
                    });

                    // Añadir la tarjeta al contenedor
                    scrollContent.addView(cardView);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Manejo de errores
            }
        });

        // Agregar TextWatcher para filtrar elementos según la entrada de búsqueda
        searchInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String query = s.toString().toLowerCase(); // Convertir la consulta a minúsculas

                scrollContent.removeAllViews(); // Limpiar el contenedor

                databaseRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot data : snapshot.getChildren()) {
                            foodItem item = data.getValue(foodItem.class);
                            if (item == null) continue;

                            // Verificar si food_name coincide con la consulta
                            if (item.getFood_name().toLowerCase().contains(query)) {
                                // Inflar la tarjeta personalizada
                                View cardView = LayoutInflater.from(getContext())
                                        .inflate(R.layout.food_card_item, scrollContent, false);

                                // Referencias a los elementos del XML
                                TextView name = cardView.findViewById(R.id.product_name);
                                TextView desc = cardView.findViewById(R.id.product_description);
                                ImageView image = cardView.findViewById(R.id.product_image);

                                // Setear datos
                                name.setText(item.getFood_name());
                                desc.setText(item.getFood_description());
                                Picasso.get().load(item.getfood_image_url()).into(image);

                                // Añadir listener para expandir la descripción
                                cardView.setOnClickListener(v -> {
                                    if (desc.getMaxLines() == 1) {
                                        desc.setMaxLines(Integer.MAX_VALUE); // Expandir descripción
                                    } else {
                                        desc.setMaxLines(1); // Colapsar descripción
                                    }
                                });

                                // Añadir la tarjeta al contenedor
                                scrollContent.addView(cardView);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        // Manejo de errores
                    }
                });
            }
        });
    }
}