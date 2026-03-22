package com.robert.a4;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MorningActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_morning);

        findViewById(R.id.btnNext).setOnClickListener(v -> startActivity(new Intent(this, DayActivity.class)));
        findViewById(R.id.btnBack).setOnClickListener(v -> finish());
    }
}