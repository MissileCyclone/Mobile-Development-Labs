package com.robert.a8;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.textfield.TextInputEditText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.button_next).setOnClickListener(v -> {

            int math = getVal(R.id.math_input);
            int rus = getVal(R.id.russian_input);


            if (math < 39 || rus < 40) {
                Toast.makeText(this, "Проверьте баллы по Математике и Русскому!", Toast.LENGTH_SHORT).show();
                return;
            }

            Intent intent = new Intent(this, ResultsActivity.class);
            intent.putExtra("math", math);
            intent.putExtra("rus", rus);
            intent.putExtra("social", getVal(R.id.society_input));
            intent.putExtra("inform", getVal(R.id.it_input));
            intent.putExtra("chemistry", getVal(R.id.chemistry_input));
            intent.putExtra("physics", getVal(R.id.physics_input));
            intent.putExtra("eng", getVal(R.id.flanguage_input));
            intent.putExtra("geo", getVal(R.id.geography_input));
            startActivity(intent);
        });
    }

    private int getVal(int id) {
        TextInputEditText input = findViewById(id);
        if (input == null || input.getText() == null) return 0;
        String s = input.getText().toString().trim();
        try { return s.isEmpty() ? 0 : Integer.parseInt(s); } catch (Exception e) { return 0; }
    }
}