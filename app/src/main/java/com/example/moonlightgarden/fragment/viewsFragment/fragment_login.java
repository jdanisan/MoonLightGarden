package com.example.moonlightgarden.fragment.viewsFragment;



import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.moonlightgarden.R;


public class fragment_login extends Fragment {

    private EditText emailEditText;
    private Button emailContinueButton;
    private Button googleContinueButton;

    public fragment_login() {
        // Constructor vacío obligatorio
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.authactivity, container, false); // Asegúrate de que el nombre coincida
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Referencias a los elementos del layout
        emailEditText = view.findViewById(R.id.emailEditText);
        emailContinueButton = view.findViewById(R.id.emailContinueButton);
        googleContinueButton = view.findViewById(R.id.googleContinueButton);

        // Acción del botón "Continue"
        emailContinueButton.setOnClickListener(v -> {
            String email = emailEditText.getText().toString().trim();
            if (email.isEmpty()) {
                Toast.makeText(getContext(), "Por favor, ingresa un correo", Toast.LENGTH_SHORT).show();
            } else {
                // Aquí podrías validar el correo o continuar al siguiente paso
                Toast.makeText(getContext(), "Correo ingresado: " + email, Toast.LENGTH_SHORT).show();
            }
        });

        // Acción del botón "Continue with Google"
        googleContinueButton.setOnClickListener(v -> {
            // Aquí iría la lógica para iniciar sesión con Google
            Toast.makeText(getContext(), "Iniciar con Google (sin implementar)", Toast.LENGTH_SHORT).show();
        });
    }
}
