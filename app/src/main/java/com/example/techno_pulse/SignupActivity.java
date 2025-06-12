package com.example.techno_pulse;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;
import android.widget.EditText;
import android.widget.Toast;
import android.text.InputType;

import com.google.android.material.textfield.TextInputLayout;
import android.util.Patterns;
import java.util.regex.Pattern;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseUser;
import android.util.Log;

public class SignupActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private TextInputLayout emailInputLayout, usernameInputLayout, passwordInputLayout, confirmPasswordInputLayout;
    private static final Pattern USERNAME_PATTERN = Pattern.compile("^[a-zA-Z0-9_]{3,20}$");
    private static final Pattern PASSWORD_PATTERN = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$!]).{8,}$");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance("https://technopulse-default-rtdb.firebaseio.com/").getReference("users");


        emailInputLayout = findViewById(R.id.emailInputLayout);
        usernameInputLayout = findViewById(R.id.usernameInputLayout);
        passwordInputLayout = findViewById(R.id.passwordInputLayout);
        passwordInputLayout.setEndIconDrawable(R.drawable.ic_visibility_off);

        passwordInputLayout.setEndIconOnClickListener(v -> {
            EditText editText = passwordInputLayout.getEditText();
            if (editText != null) {
                int inputType = editText.getInputType();
                if ((inputType & InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD) == InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD) {
                    editText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    passwordInputLayout.setEndIconDrawable(R.drawable.ic_visibility_off);
                } else {
                    editText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    passwordInputLayout.setEndIconDrawable(R.drawable.ic_visibility);
                }
                editText.setSelection(editText.getText().length());
            }
        });

        confirmPasswordInputLayout = findViewById(R.id.confirmPasswordInputLayout);
        confirmPasswordInputLayout.setEndIconDrawable(R.drawable.ic_visibility_off);

        confirmPasswordInputLayout.setEndIconOnClickListener(v -> {
            EditText editText = confirmPasswordInputLayout.getEditText();
            if (editText != null) {
                int inputType = editText.getInputType();
                if ((inputType & InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD) == InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD) {
                    editText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    confirmPasswordInputLayout.setEndIconDrawable(R.drawable.ic_visibility_off);
                } else {
                    editText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    confirmPasswordInputLayout.setEndIconDrawable(R.drawable.ic_visibility);
                }
                editText.setSelection(editText.getText().length());
            }
        });

        TextInputEditText emailEditText = findViewById(R.id.email);
        TextInputEditText usernameEditText = findViewById(R.id.username);
        TextInputEditText passwordEditText = findViewById(R.id.password);
        TextInputEditText confirmPasswordEditText = findViewById(R.id.confirmPassword);

        emailEditText.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                emailInputLayout.setHint("");
            } else {
                if (emailEditText.getText().toString().isEmpty()) {
                    emailInputLayout.setHint("Email address");
                }
            }
        });

        usernameEditText.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                usernameInputLayout.setHint("");
            } else {
                if (usernameEditText.getText().toString().isEmpty()) {
                    usernameInputLayout.setHint("Username");
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

        confirmPasswordEditText.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                confirmPasswordInputLayout.setHint("");
            } else {
                if (confirmPasswordEditText.getText().toString().isEmpty()) {
                    confirmPasswordInputLayout.setHint("Confirm Password");
                }
            }
        });

        Button signupBtn = findViewById(R.id.signupBtn);
        signupBtn.setOnClickListener(v -> {

            if (!validateSignUpForm()) {
                Toast.makeText(this, "Validation failed", Toast.LENGTH_SHORT).show();
                return;
            }
            String email = ((EditText) findViewById(R.id.email)).getText().toString().trim();
            String username = ((EditText) findViewById(R.id.username)).getText().toString().trim();
            String password = ((EditText) findViewById(R.id.password)).getText().toString().trim();

            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            if (user == null) {
                                Toast.makeText(this, "User authentication failed", Toast.LENGTH_SHORT).show();
                                return;
                            }

                            String userId = user.getUid();
                            Log.d("SignupActivity", "User ID: " + userId);

                            mDatabase.child(userId).child("username").setValue(username)
                                    .addOnCompleteListener(dbTask -> {
                                        if (dbTask.isSuccessful()) {
                                            Toast.makeText(this, "Registration successful. Welcome!", Toast.LENGTH_SHORT).show();


                                            Intent intent = new Intent(SignupActivity.this, DashboardActivity.class);
                                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                            startActivity(intent);
                                            finish();
                                        }else {
                                            Exception error = dbTask.getException();
                                            Log.e("SignupActivity", "Failed to save username", error);
                                            Toast.makeText(this, "Failed to save user data: " + error.getMessage(), Toast.LENGTH_LONG).show();
                                        }
                                    });
                        } else {
                            Exception exception = task.getException();
                            String error = exception != null ? exception.getMessage() : "Unknown error";

                            if (error != null && error.toLowerCase().contains("email address is already in use")) {
                                emailInputLayout.setError("Email already registered");
                            } else {
                                Toast.makeText(this, "Sign-up failed: " + error, Toast.LENGTH_LONG).show();
                            }
                        }
                    });


        });

        TextView alreadyHaveAccount = findViewById(R.id.goToLogin);
        alreadyHaveAccount.setOnClickListener(v -> {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        });

        }

    private boolean validateSignUpForm() {
        String email = ((EditText) findViewById(R.id.email)).getText().toString().trim();
        String username = ((EditText) findViewById(R.id.username)).getText().toString().trim();
        String password = ((EditText) findViewById(R.id.password)).getText().toString().trim();
        String confirmPassword = ((EditText) findViewById(R.id.confirmPassword)).getText().toString().trim();

        boolean isValid = true;

        if (email.isEmpty()) {
            emailInputLayout.setError("Email is required");
            isValid = false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailInputLayout.setError("Enter a valid email");
            isValid = false;
        } else {
            emailInputLayout.setError(null);
        }

        if (username.isEmpty()) {
            usernameInputLayout.setError("Username is required");
            isValid = false;
        } else if (!USERNAME_PATTERN.matcher(username).matches()) {
            usernameInputLayout.setError("3-20 chars (letters, numbers, underscores)");
            isValid = false;
        } else {
            usernameInputLayout.setError(null);
        }

        if (password.isEmpty()) {
            passwordInputLayout.setError("Password is required");
            isValid = false;
        } else if (!PASSWORD_PATTERN.matcher(password).matches()) {
            passwordInputLayout.setError("Min 8 chars, 1 uppercase, 1 lowercase, 1 number, 1 special char (@#$!)");
            isValid = false;
        } else {
            passwordInputLayout.setError(null);
        }

        if (confirmPassword.isEmpty()) {
            confirmPasswordInputLayout.setError("Confirm your password");
            isValid = false;
        } else if (!confirmPassword.equals(password)) {
            confirmPasswordInputLayout.setError("Passwords do not match");
            isValid = false;
        } else {
            confirmPasswordInputLayout.setError(null);
        }

        return isValid;
    }

    private void performSignup() {
        if (!validateSignUpForm()) {
            Toast.makeText(this, "Validation failed", Toast.LENGTH_SHORT).show();
            return; // Stop if validation fails
        }
        String email = ((EditText) findViewById(R.id.email)).getText().toString().trim();
        String username = ((EditText) findViewById(R.id.username)).getText().toString().trim();
        String password = ((EditText) findViewById(R.id.password)).getText().toString().trim();

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        if (user == null) {
                            Toast.makeText(this, "User authentication failed", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        String userId = user.getUid();
                        Log.d("SignupActivity", "User ID: " + userId);
                        mDatabase.child(userId).child("username").setValue(username)
                                .addOnCompleteListener(dbTask -> {
                                    if (dbTask.isSuccessful()) {
                                        Log.d("SignupActivity", "About to navigate to loginActivity");
                                        Toast.makeText(this, "Navigating to login", Toast.LENGTH_SHORT).show();
                                        mAuth.signOut();
                                        startActivity(new Intent(this, LoginActivity.class));
                                        finish();
                                    } else {
                                        Toast.makeText(this, "Failed to save username", Toast.LENGTH_SHORT).show();
                                        // Optionally, log the error for debugging:
                                        Log.e("SignupActivity", "Failed to save username: " + dbTask.getException());
                                    }
                                });
                    } else {
                        Exception exception = task.getException();
                        String error = exception != null ? exception.getMessage() : "Unknown error";
                        if (error != null && error.toLowerCase().contains("email address is already in use")) {
                            emailInputLayout.setError("Email already registered");
                        } else {
                            Toast.makeText(this, "Sign-up failed: " + error, Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
}

