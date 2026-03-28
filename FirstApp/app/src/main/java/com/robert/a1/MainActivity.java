package com.robert.a1;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Инициализация элементов интерфейса
        final TextView tvName = findViewById(R.id.tvName);
        final TextView tvGroup = findViewById(R.id.tvGroup);
        final ImageView mainImage = findViewById(R.id.mainImage);
        Button btnToggleText = findViewById(R.id.btnToggleText);
        ImageButton btnToggleImage = findViewById(R.id.btnToggleImage);

        // d. Обработка нажатия для скрытия/раскрытия текста
        btnToggleText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Если текст виден — скрываем, если скрыт — показываем
                if (tvName.getVisibility() == View.VISIBLE) {
                    tvName.setVisibility(View.INVISIBLE);
                    tvGroup.setVisibility(View.INVISIBLE);
                } else {
                    tvName.setVisibility(View.VISIBLE);
                    tvGroup.setVisibility(View.VISIBLE);
                }
            }
        });

        // e. Обработка нажатия для скрытия/раскрытия картинки
        btnToggleImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mainImage.getVisibility() == View.VISIBLE) {
                    mainImage.setVisibility(View.GONE);
                } else {
                    mainImage.setVisibility(View.VISIBLE);
                }
            }
        });
    }
}