package com.example.techno_pulse;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(() -> {
            FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

            if (currentUser != null) {
                // User is signed in, redirect to dashboard
                startActivity(new Intent(SplashActivity.this, DashboardActivity.class));
            } else {
                // User not signed in, go to login or intro screen
                startActivity(new Intent(SplashActivity.this, Splash2Activity.class));
            }

            finish(); // Prevent back navigation to this activity
        }, 2000); // 2 seconds delay
    }
}

