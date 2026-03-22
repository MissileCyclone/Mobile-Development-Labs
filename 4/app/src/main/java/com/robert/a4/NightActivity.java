package com.robert.a4;

import android.os.Bundle;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class NightActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_night);

        findViewById(R.id.btnSecret).setOnClickListener(v -> {
            new AlertDialog.Builder(this)
                    .setTitle("Вопрос")
                    .setMessage("Ты спишь?")
                    .setPositiveButton("Да", (d, id) -> finishAffinity()) // Выход из приложения
                    .setNegativeButton("Нет", (d, id) -> {
                        startActivity(new Intent(this, MainActivity.class)); // На главный экран
                    }).show();
        });
    }
}