package com.robert.a2;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText num1, num2;
    private TextView tvResult, opSymbol;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        num1 = findViewById(R.id.num1);
        num2 = findViewById(R.id.num2);
        tvResult = findViewById(R.id.tvResult);
        opSymbol = findViewById(R.id.opSymbol);

        // Кнопки действий
        findViewById(R.id.btnAdd).setOnClickListener(v -> calculate('+'));
        findViewById(R.id.btnSub).setOnClickListener(v -> calculate('-'));
        findViewById(R.id.btnMul).setOnClickListener(v -> calculate('*'));
        findViewById(R.id.btnDiv).setOnClickListener(v -> calculate('/'));

        // Очистка
        findViewById(R.id.btnClear).setOnClickListener(v -> {
            num1.setText("");
            num2.setText("");
            tvResult.setText("");
            opSymbol.setText("?");
        });
    }

    private void calculate(char op) {
        String s1 = num1.getText().toString();
        String s2 = num2.getText().toString();

        if (s1.isEmpty() || s2.isEmpty()) {
            Toast.makeText(this, "Введите числа", Toast.LENGTH_SHORT).show();
            return;
        }

        double n1 = Double.parseDouble(s1);
        double n2 = Double.parseDouble(s2);
        double res = 0;
        opSymbol.setText(String.valueOf(op));

        if (op == '+') res = n1 + n2;
        else if (op == '-') res = n1 - n2;
        else if (op == '*') res = n1 * n2;
        else if (op == '/') {
            if (n2 == 0) {
                tvResult.setText("Error");
                Toast.makeText(this, "На 0 делить нельзя!", Toast.LENGTH_SHORT).show();
                return;
            }
            res = n1 / n2;
        }
        tvResult.setText(String.valueOf(res));
    }
}