package com.robert.a6;

import com.robert.a6.R;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private DatabaseHelper dbSource;
    private ArrayAdapter<String> listAdapter;
    private List<String> displayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        TextView studentInfo = findViewById(R.id.studentInfoText);
        if (studentInfo != null) {
            studentInfo.setText("Разработчик: Бауэр Р. А.\nГруппа: Т-423901-НТ");
        }

        dbSource = new DatabaseHelper(this);
        ListView resultView = findViewById(R.id.productsList);


        listAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, displayList);
        resultView.setAdapter(listAdapter);


        Button btnComp = findViewById(R.id.btnFood);
        Button btnPeri = findViewById(R.id.btnClothes);
        Button btnSoft = findViewById(R.id.btnToys);


        btnComp.setOnClickListener(v -> refreshProductList("Компьютеры"));
        btnPeri.setOnClickListener(v -> refreshProductList("Периферия"));
        btnSoft.setOnClickListener(v -> refreshProductList("ПО и Сервис"));


        refreshProductList("Компьютеры");
    }

    private void refreshProductList(String groupName) {
        List<Product> items = dbSource.getItemsByGroup(groupName);
        displayList.clear();

        for (Product p : items) {

            StringBuilder sb = new StringBuilder();
            sb.append(p.getName().toUpperCase()).append("\n");
            sb.append("Описание: ").append(p.getDescription()).append("\n");
            sb.append(String.format(Locale.getDefault(), "Стоимость: %.0f руб.", p.getPrice()));

            displayList.add(sb.toString());
        }

        listAdapter.notifyDataSetChanged();
    }
}