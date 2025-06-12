package com.example.techno_pulse;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;

public class DeveloperDetailsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_developer_details);

        findViewById(R.id.back_icon).setOnClickListener(v -> finish());

        findViewById(R.id.exit_button).setOnClickListener(v -> finish());

        View root = findViewById(android.R.id.content);
        root.setOnTouchListener(new OnSwipeTouchListener(this) {
            @Override
            public void onSwipeRight() {
                finish();
            }
        });


    }
}
