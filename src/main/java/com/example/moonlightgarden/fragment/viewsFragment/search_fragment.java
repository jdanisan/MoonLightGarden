package com.example.moonlightgarden.fragment.viewsFragment;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.example.moonlightgarden.R;

public class search_fragment extends Fragment {

    private EditText searchInput;
    private RecyclerView recyclerView;
    private BottomNavigationView bottomNavigationView;

    public search_fragment() {
        // Constructor vacío requerido
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflar layout del fragmento
        return inflater.inflate(R.layout.search_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        searchInput = view.findViewById(R.id.search_input);
        recyclerView = view.findViewById(R.id.recycler_view);
        bottomNavigationView = view.findViewById(R.id.bottom_nav);

        // TODO: Configura tu RecyclerView con LayoutManager y Adapter
        // recyclerView.setLayoutManager(...);
        // recyclerView.setAdapter(...);

        // Ejemplo: escucha cambios de texto en la barra de búsqueda
        searchInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // No hacer nada antes del cambio
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Aquí puedes filtrar tu RecyclerView
                // filtrarLista(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                // No hacer nada después del cambio
            }
        });


    }
}
