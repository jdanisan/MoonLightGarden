package com.example.moonlightgarden.fragment.viewsFragment;

import android.content.Intent;
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
import login.AuthActivity;// Ensure this matches the actual package
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

        btnChangePassword = view.findViewById(R.id.btn_change_password);
        btnLogout = view.findViewById(R.id.btn_logout);
        bottomNavigationView = view.findViewById(R.id.bottom_nav);

        btnChangePassword.setOnClickListener(v ->
                Toast.makeText(getContext(), "Change Password", Toast.LENGTH_SHORT).show()
        );

        btnLogout.setOnClickListener(v -> {
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(requireActivity(), AuthActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            requireActivity().startActivity(intent);
        });
    }
}