package com.example.techno_pulse;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.textfield.TextInputEditText;
import android.util.Patterns;
import com.google.firebase.auth.FirebaseUser;
import androidx.appcompat.app.AlertDialog;


public class LoginActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private TextInputLayout emailInputLayout, passwordInputLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        emailInputLayout = findViewById(R.id.emailInputLayout);
        passwordInputLayout = findViewById(R.id.passwordInputLayout);
        passwordInputLayout.setEndIconDrawable(R.drawable.ic_visibility_off);

        passwordInputLayout.setEndIconOnClickListener(v -> {
            EditText editText = passwordInputLayout.getEditText();
            if (editText != null) {
                int inputType = editText.getInputType();
                if ((inputType & InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD) == InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD) {
                    // Currently visible, hide password
                    editText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    passwordInputLayout.setEndIconDrawable(R.drawable.ic_visibility_off);
                } else {
                    // Currently hidden, show password
                    editText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    passwordInputLayout.setEndIconDrawable(R.drawable.ic_visibility);
                }
                editText.setSelection(editText.getText().length());
            }
        });

        // Login Button
        Button loginBtn = findViewById(R.id.loginBtn);
        loginBtn.setOnClickListener(v -> {
            if (!validateLoginForm()) {
                return; // Stop if validation fails
            }
            performLogin();
        });

        TextView forgotPassword = findViewById(R.id.forgotPassword);
        forgotPassword.setOnClickListener(v -> showForgotPasswordDialog());

        // Go to Signup Link
        TextView goToSignup = findViewById(R.id.goToSignup);
        goToSignup.setOnClickListener(v -> {
            startActivity(new Intent(this, SignupActivity.class));
        });

        EditText passwordEditText = findViewById(R.id.password);
        TextInputEditText emailEditText = findViewById(R.id.email);

// Email field focus listener
        emailEditText.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                emailInputLayout.setHint("");
            } else {
                if (emailEditText.getText().toString().isEmpty()) {
                    emailInputLayout.setHint("Email address");
                }
            }
        });

        passwordEditText.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                passwordInputLayout.setHint("");
            } else {
                if (passwordEditText.getText().toString().isEmpty()) {
                    passwordInputLayout.setHint("Password");
                }
            }
        });
    }

    private void showForgotPasswordDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.CustomDialog);
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_forgot_password, null);
        builder.setView(dialogView);
        AlertDialog dialog = builder.create();

        EditText emailEdit = dialogView.findViewById(R.id.forgot_email);
        Button btnReset = dialogView.findViewById(R.id.btn_reset);
        Button btnCancel = dialogView.findViewById(R.id.btn_cancel);

        btnReset.setOnClickListener(v -> {
            String email = emailEdit.getText().toString().trim();
            if (!email.isEmpty()) {
                FirebaseAuth.getInstance().sendPasswordResetEmail(email)
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                Toast.makeText(this, "Password reset email sent", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(this, "Failed to send reset email: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                            }
                            dialog.dismiss();
                        });
            } else {
                Toast.makeText(this, "Enter your email address", Toast.LENGTH_SHORT).show();
            }
        });

        btnCancel.setOnClickListener(v -> dialog.dismiss());

        dialog.show();
    }

    private boolean validateLoginForm() {
        TextInputLayout emailLayout = findViewById(R.id.emailInputLayout);
        TextInputLayout passwordLayout = findViewById(R.id.passwordInputLayout);

        String email = ((TextInputEditText) findViewById(R.id.email)).getText().toString().trim();
        String password = ((TextInputEditText) findViewById(R.id.password)).getText().toString();

        boolean valid = true;

        if (email.isEmpty()) {
            emailLayout.setError("Email is required");
            valid = false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailLayout.setError("Enter a valid email");
            valid = false;
        } else {
            emailLayout.setError(null);
        }

        if (password.isEmpty()) {
            passwordLayout.setError("Password is required");
            valid = false;
        } else {
            passwordLayout.setError(null);
        }

        return valid;
    }

    private void performLogin() {
        String email = ((EditText) findViewById(R.id.email)).getText().toString().trim();
        String password = ((EditText) findViewById(R.id.password)).getText().toString().trim();

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Email and password are required", Toast.LENGTH_SHORT).show();
            return;
        }

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        if (user != null) {
                            startActivity(new Intent(this, DashboardActivity.class));
                            finish();
                        }
                    } else {
                        Toast.makeText(this, "Login failed: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
    }
}
