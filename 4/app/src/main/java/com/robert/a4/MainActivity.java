package com.robert.a4;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btnMorning).setOnClickListener(v -> startActivity(new Intent(this, MorningActivity.class)));
        findViewById(R.id.btnDay).setOnClickListener(v -> startActivity(new Intent(this, DayActivity.class)));
        findViewById(R.id.btnEvening).setOnClickListener(v -> startActivity(new Intent(this, EveningActivity.class)));
        findViewById(R.id.btnNight).setOnClickListener(v -> startActivity(new Intent(this, NightActivity.class)));
    }
}