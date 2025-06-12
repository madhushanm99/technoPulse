package com.example.techno_pulse;

import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.auth.FirebaseUser;
import android.util.Log;

public class UserDetailsActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private String userId;
    private TextView usernameTextView, emailTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance("https://technopulse-default-rtdb.firebaseio.com/").getReference("users");
        userId = mAuth.getCurrentUser().getUid();


        usernameTextView = findViewById(R.id.username);
        emailTextView = findViewById(R.id.email);
        ImageView profileImage = findViewById(R.id.profile_image);

        findViewById(R.id.back_icon).setOnClickListener(v -> finish());

        fetchUserData();

        findViewById(R.id.edit_info).setOnClickListener(v -> showEditDialog());

        findViewById(R.id.sign_out).setOnClickListener(v -> showSignOutDialog());
    }

    private void fetchUserData() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            Log.d("UserDetails", "Current UID: " + user.getUid());
        }
        if (user == null) {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
            return;
        }

        String email = user.getEmail();
        emailTextView.setText(email);

        if (user != null && userId != null) {
            mDatabase.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        String username = snapshot.child("username").getValue(String.class);
                        if (username != null) {
                            usernameTextView.setText(username);
                        } else {
                            Log.e("UserDetails", "Username field missing in database");
                        }
                    } else {
                        Log.e("UserDetails", "No user data found at path: users/" + userId);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Log.e("UserDetails", "Database error: " + error.getMessage());
                    Toast.makeText(UserDetailsActivity.this,
                            "Failed to load user data: " + error.getMessage(),
                            Toast.LENGTH_LONG).show();
                }
            });
        }
    }


    private void showEditDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.CustomDialog);
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_edit_user_info, null);
        builder.setView(dialogView);
        AlertDialog dialog = builder.create();

        EditText usernameEdit = dialogView.findViewById(R.id.edit_username);
        EditText emailEdit = dialogView.findViewById(R.id.edit_email);
        EditText passwordEdit = dialogView.findViewById(R.id.edit_password);
        ImageView passwordToggle = dialogView.findViewById(R.id.password_toggle);

        usernameEdit.setText(usernameTextView.getText());
        emailEdit.setText(emailTextView.getText());
        passwordEdit.setText("");

        setupPasswordToggle(passwordEdit, passwordToggle);

        dialogView.findViewById(R.id.btn_save).setOnClickListener(v -> {
            updateUserData(
                    usernameEdit.getText().toString(),
                    emailEdit.getText().toString(),
                    passwordEdit.getText().toString()
            );
            dialog.dismiss();
        });

        dialogView.findViewById(R.id.btn_cancel).setOnClickListener(v -> dialog.dismiss());

        dialog.show();
        dialog.getWindow().setLayout(
                (int)(getResources().getDisplayMetrics().widthPixels * 0.9),
                android.view.WindowManager.LayoutParams.WRAP_CONTENT
        );
    }

    private void showSignOutDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.CustomDialog);
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_sign_out, null);
        builder.setView(dialogView);
        AlertDialog dialog = builder.create();
        dialog.setCancelable(false);

        if (dialog.getWindow() != null) {
            dialog.getWindow().getAttributes().windowAnimations = R.style.DialogSlideDownAnimation;
        }

        Button btnOk = dialogView.findViewById(R.id.btn_ok);
        Button btnCancel = dialogView.findViewById(R.id.btn_cancel);

        btnOk.setOnClickListener(view -> {

            Intent intent = new Intent(this, Splash2Activity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();

            dialog.dismiss();
        });

        btnCancel.setOnClickListener(view -> dialog.dismiss());

        dialog.show();

        if (dialog.getWindow() != null) {
            int width = (int)(getResources().getDisplayMetrics().widthPixels * 0.9);
            dialog.getWindow().setLayout(width, android.view.WindowManager.LayoutParams.WRAP_CONTENT);}
    }

    private void updateUserData(String newUsername, String newEmail, String newPassword) {
        if (!newUsername.equals(usernameTextView.getText().toString())) {
            mDatabase.child(userId).child("username").setValue(newUsername)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            usernameTextView.setText(newUsername);
                        } else {
                            Toast.makeText(this, "Username update failed", Toast.LENGTH_SHORT).show();
                        }
                    });
        }

        if (!newEmail.equals(emailTextView.getText().toString())) {
            mAuth.getCurrentUser().updateEmail(newEmail)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            emailTextView.setText(newEmail);
                        } else {
                            Toast.makeText(this, "Email update failed: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
        }

        if (!newPassword.isEmpty()) {
            mAuth.getCurrentUser().updatePassword(newPassword)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(this, "Password updated successfully", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(this, "Password update failed: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
        }
    }

    private void setupPasswordToggle(EditText passwordEdit, ImageView passwordToggle) {
        passwordToggle.setOnClickListener(v -> {
            int inputType = passwordEdit.getInputType();
            if ((inputType & InputType.TYPE_TEXT_VARIATION_PASSWORD) > 0) {
                passwordEdit.setInputType(InputType.TYPE_CLASS_TEXT);
                passwordToggle.setImageResource(R.drawable.ic_visibility);
            } else {
                passwordEdit.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                passwordToggle.setImageResource(R.drawable.ic_visibility_off);
            }
            passwordEdit.setSelection(passwordEdit.getText().length());
        });
    }

    private void setupSignOutButton() {
        findViewById(R.id.sign_out).setOnClickListener(v -> {

            mAuth.signOut();
            startActivity(new Intent(this, Splash2Activity.class));
            finishAffinity();
        });
    }
}
