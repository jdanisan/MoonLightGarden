package com.example.moonlightgarden.fragment.viewsFragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.moonlightgarden.R;

import login.AuthActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class configuser_fragment extends Fragment {

    private Button btnChangePassword;
    private Button btnLogout;
    private BottomNavigationView bottomNavigationView;

    public configuser_fragment() {
        // Empty constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.configuser_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        FirebaseAuth auth = FirebaseAuth.getInstance();
        if (auth != null) {
            auth.setLanguageCode("es"); // Set the desired language
        }

        // Initialize UI elements
        btnChangePassword = view.findViewById(R.id.btn_change_password);
        btnLogout = view.findViewById(R.id.btn_logout);
        bottomNavigationView = view.findViewById(R.id.bottom_nav);
        EditText editTextPassword = view.findViewById(R.id.editTextPassword);

        // TextViews for user information
        TextView textViewName = view.findViewById(R.id.textViewName);
        TextView textViewSecondName = view.findViewById(R.id.textViewSecondName);
        TextView textViewBirthDate = view.findViewById(R.id.textViewBirthDate);
        TextView textViewEmail = view.findViewById(R.id.textViewEmail);

        // Initially disable the EditText
        editTextPassword.setEnabled(false);

        // Update TextViews with user information
        if (auth.getCurrentUser() != null) {
            String email = auth.getCurrentUser().getEmail();
            String displayName = auth.getCurrentUser().getDisplayName();

            if (email != null) {
                textViewEmail.setText(email);
            } else {
                textViewEmail.setText("Correo no disponible");
            }

            if (displayName != null) {
                textViewName.setText(displayName);
            } else {
                textViewName.setText("Nombre no disponible");
            }

            // Placeholder for additional user data (e.g., second name, birth date)
            textViewSecondName.setText("Apellido no disponible");
            textViewBirthDate.setText("Fecha de nacimiento no disponible");
        } else {
            Toast.makeText(getContext(), "No hay usuario autenticado.", Toast.LENGTH_SHORT).show();
        }

        // Change password logic
        btnChangePassword.setOnClickListener(v -> {
            if (auth.getCurrentUser() != null) {
                boolean isGoogleUser = auth.getCurrentUser().getProviderData().stream()
                        .anyMatch(userInfo -> "google.com".equals(userInfo.getProviderId()));

                if (!isGoogleUser) {
                    new AlertDialog.Builder(requireContext())
                            .setTitle("Confirmación")
                            .setMessage("¿Estás seguro de que deseas cambiar tu contraseña?")
                            .setPositiveButton("Sí", (dialog, which) -> {
                                editTextPassword.setEnabled(true);

                                String newPassword = editTextPassword.getText().toString().trim();
                                if (!newPassword.isEmpty()) {
                                    auth.getCurrentUser().updatePassword(newPassword)
                                            .addOnCompleteListener(task -> {
                                                if (task.isSuccessful()) {
                                                    Toast.makeText(getContext(), "Contraseña actualizada correctamente.", Toast.LENGTH_SHORT).show();
                                                    editTextPassword.setEnabled(false);
                                                } else {
                                                    Toast.makeText(getContext(), "Error al actualizar la contraseña.", Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                } else {
                                    Toast.makeText(getContext(), "Por favor, ingresa una nueva contraseña.", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .setNegativeButton("No", (dialog, which) -> dialog.dismiss())
                            .show();
                } else {
                    Toast.makeText(getContext(), "Esta opción no está habilitada para usuarios loggeados con Google.", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getContext(), "No hay usuario autenticado.", Toast.LENGTH_SHORT).show();
            }
        });

        // Logout logic
        btnLogout.setOnClickListener(v -> {
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(requireActivity(), AuthActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            requireActivity().startActivity(intent);
        });
    }
}