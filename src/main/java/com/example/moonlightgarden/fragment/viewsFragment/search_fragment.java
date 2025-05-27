package com.example.moonlightgarden.fragment.viewsFragment;

import android.os.Bundle;
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

        databaseRef = FirebaseDatabase.getInstance().getReference("productos");

        databaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                scrollContent.removeAllViews(); // Limpiar si ya hay vistas

                for (DataSnapshot data : snapshot.getChildren()) {
                    foodItem item = data.getValue(foodItem.class);
                    if (item == null) continue;

                    View cardView = LayoutInflater.from(getContext()).inflate(R.layout.food_card_item, scrollContent, false);

                    TextView name = cardView.findViewById(R.id.product_name);
                    TextView desc = cardView.findViewById(R.id.product_description);
                    ImageView image = cardView.findViewById(R.id.product_image);

                    name.setText(item.getName());
                    desc.setText(item.getDescription());
                    Picasso.get().load(item.getImageUrl()).into(image);

                    scrollContent.addView(cardView);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Manejar errores de lectura
            }
        });
    }
}
