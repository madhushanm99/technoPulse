package com.example.techno_pulse;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;

public class ArticleDetailActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_detail);

        String title = getIntent().getStringExtra("title");
        String author = getIntent().getStringExtra("author");
        String date = getIntent().getStringExtra("date");
        String caption = getIntent().getStringExtra("caption");
        String body = getIntent().getStringExtra("body");
        int imageRes = getIntent().getIntExtra("imageRes", R.drawable.featured_news);

        ((TextView) findViewById(R.id.article_title)).setText(title);
        ((TextView) findViewById(R.id.article_author)).setText(author);
        ((TextView) findViewById(R.id.article_date)).setText(date);
        ((TextView) findViewById(R.id.article_body)).setText(body);
        ((ImageView) findViewById(R.id.article_image)).setImageResource(imageRes);

        findViewById(R.id.back_icon).setOnClickListener(v -> finish());

        View root = findViewById(android.R.id.content);
        root.setOnTouchListener(new OnSwipeTouchListener(this) {
            @Override
            public void onSwipeRight() {
                finish();
            }
        });
    }
}
