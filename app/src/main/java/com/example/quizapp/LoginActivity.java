package com.example.quizapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private EditText emailEditText, passwordEditText;
    private Button loginBtn, googleSignInBtn;
    private TextView forgotPasswordText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        auth = FirebaseAuth.getInstance();

        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        loginBtn = findViewById(R.id.loginBtn);
        googleSignInBtn = findViewById(R.id.googleSignInBtn);
        forgotPasswordText = findViewById(R.id.forgotPasswordText);

        loginBtn.setOnClickListener(v -> {
            String email = emailEditText.getText().toString().trim();
            String password = passwordEditText.getText().toString().trim();

            if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
                Toast.makeText(LoginActivity.this, "Please enter all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this, "Login failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        });

        googleSignInBtn.setOnClickListener(v ->
                Toast.makeText(this, "Google Sign-In not implemented", Toast.LENGTH_SHORT).show()
        );

        forgotPasswordText.setOnClickListener(v -> {
            String email = emailEditText.getText().toString().trim();
            if (TextUtils.isEmpty(email)) {
                Toast.makeText(this, "Enter your email to reset password", Toast.LENGTH_SHORT).show();
            } else {
                auth.sendPasswordResetEmail(email).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(this, "Reset link sent to your email", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}
