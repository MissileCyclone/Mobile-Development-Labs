package com.robert.a3;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Button btn1, btn2, btn3, btn4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn1 = findViewById(R.id.btn1);
        btn2 = findViewById(R.id.btn2);
        btn3 = findViewById(R.id.btn3);
        btn4 = findViewById(R.id.btn4);

        // 1. Toast на 2 секунды (Short)
        btn1.setOnClickListener(v ->
                Toast.makeText(this, "Кнопка номер 1 нажата", Toast.LENGTH_SHORT).show());

        // 2. Toast на 3.5 секунды (Long)
        btn2.setOnClickListener(v ->
                Toast.makeText(this, "Кнопка номер 2 нажата", Toast.LENGTH_LONG).show());

        // 3. Диалоговое окно с иконкой и выбором
        btn3.setOnClickListener(v -> showButton3Dialog());

        // 4. Тест с множественным выбором
        btn4.setOnClickListener(v -> showAnimalsTest());
    }

    private void showButton3Dialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Кнопка 3")
                .setMessage("Вы хотите изменить цвет текста кнопок?")
                .setIcon(R.drawable.test_icon) // Убедись, что картинка лежит в res/drawable
                .setCancelable(false)
                .setPositiveButton("Да", (dialog, id) -> {
                    int red = Color.RED;
                    btn1.setTextColor(red);
                    btn2.setTextColor(red);
                    btn3.setTextColor(red);
                    btn4.setTextColor(red);
                })
                .setNegativeButton("Отмена", (dialog, id) -> {
                    dialog.dismiss();
                    Toast.makeText(this, "Окно закрыто", Toast.LENGTH_SHORT).show();
                });
        builder.create().show();
    }

    private void showAnimalsTest() {
        String[] animals = {"Тигр", "Корова", "Волк", "Жираф", "Лев"};
        boolean[] checkedItems = {false, false, false, false, false};

        // Верные ответы: Корова (1) и Жираф (3)
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Выберите только травоядных:");

        builder.setMultiChoiceItems(animals, checkedItems, (dialog, which, isChecked) -> {
            checkedItems[which] = isChecked;
        });

        builder.setPositiveButton("Готово", (dialog, id) -> {
            // Проверка: должны быть выбраны Корова и Жираф, и НИКОГО больше
            boolean isCorrect = checkedItems[1] && checkedItems[3] &&
                    !checkedItems[0] && !checkedItems[2] && !checkedItems[4];

            if (isCorrect) {
                Toast.makeText(this, "Все верно!", Toast.LENGTH_SHORT).show();
            } else {
                // Прячем кнопки при ошибке
                btn1.setVisibility(View.INVISIBLE);
                btn2.setVisibility(View.INVISIBLE);
                btn3.setVisibility(View.INVISIBLE);
                btn4.setVisibility(View.INVISIBLE);
                Toast.makeText(this, "Ошибка! Кнопки исчезли.", Toast.LENGTH_SHORT).show();
            }
        });

        builder.create().show();
    }
}