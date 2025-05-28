package login;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.moonlightgarden.MainActivity;
import com.example.moonlightgarden.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Calendar;

public class RegisterActivity extends AppCompatActivity {

    private EditText usernameEditText, birthDateEditText, emailEditText, passwordEditText;
    private CheckBox showPasswordCheckBox;
    private Button registerButton, googleRegisterButton;
    private TextView alreadyHaveAccount;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);

        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance();

        // Initialize views
        usernameEditText = findViewById(R.id.usernameEditText);
        birthDateEditText = findViewById(R.id.birthDateEditText);
        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        showPasswordCheckBox = findViewById(R.id.showPasswordCheckBox);
        registerButton = findViewById(R.id.registerButton);
        googleRegisterButton = findViewById(R.id.googleRegisterButton);
        alreadyHaveAccount = findViewById(R.id.alreadyHaveAccount);

        // Handle birth date selection
        birthDateEditText.setOnClickListener(v -> showDatePickerDialog());

        // Handle show/hide password
        showPasswordCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                passwordEditText.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            } else {
                passwordEditText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            }
            passwordEditText.setSelection(passwordEditText.getText().length()); // Keep cursor at the end
        });

        // Handle register button click
        registerButton.setOnClickListener(v -> {
            String username = usernameEditText.getText().toString().trim();
            String email = emailEditText.getText().toString().trim();
            String password = passwordEditText.getText().toString().trim();

            if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
                Toast.makeText(RegisterActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            } else {
                registerUser(email, password);
            }
        });

        // Handle "Already have an account" click
        alreadyHaveAccount.setOnClickListener(v -> {
            Intent intent = new Intent(RegisterActivity.this, AuthActivity.class);
            startActivity(intent);
            finish();
        });
    }

    private void registerUser(String email, String password) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this, task -> {
                if (task.isSuccessful()) {
                    // Registration successful
                    FirebaseUser user = auth.getCurrentUser();
                    Toast.makeText(RegisterActivity.this, "Registration successful! Welcome, " + user.getEmail(), Toast.LENGTH_SHORT).show();

                    // Redirect to MainActivity
                    Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    // Registration failed
                    Toast.makeText(RegisterActivity.this, "Registration failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
    }

    private void showDatePickerDialog() {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, (view, selectedYear, selectedMonth, selectedDay) -> {
            String date = String.format("%02d/%02d/%04d", selectedDay, selectedMonth + 1, selectedYear);
            birthDateEditText.setText(date);
        }, year, month, day);

        datePickerDialog.show();
    }
}