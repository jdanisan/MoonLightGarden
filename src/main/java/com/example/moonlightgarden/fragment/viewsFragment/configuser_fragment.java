package com.example.moonlightgarden.fragment.viewsFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.moonlightgarden.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;



public class configuser_fragment  extends Fragment {

    private Button btnChangePassword;
    private Button btnLogout;
    private BottomNavigationView bottomNavigationView;

    public configuser_fragment() {
        // Constructor vacío requerido
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Infla el layout para este fragmento
        return inflater.inflate(R.layout.configuser_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Inicialización de vistas
        btnChangePassword = view.findViewById(R.id.btn_change_password);
        btnLogout = view.findViewById(R.id.btn_logout);
        bottomNavigationView = view.findViewById(R.id.bottom_nav);

        // Listener para "Cambiar contraseña"
        btnChangePassword.setOnClickListener(v ->
                Toast.makeText(getContext(), "Cambiar contraseña", Toast.LENGTH_SHORT).show()
        );

        // Listener para "Cerrar sesión"
        btnLogout.setOnClickListener(v ->
                Toast.makeText(getContext(), "Sesión cerrada", Toast.LENGTH_SHORT).show()
        );


    }
}