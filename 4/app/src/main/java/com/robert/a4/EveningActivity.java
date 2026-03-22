package com.robert.a4;

import android.os.Bundle;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class EveningActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evening);

        sendNotification("Режим", "ПОРА СПАТЬ!");

        findViewById(R.id.btnNext).setOnClickListener(v -> startActivity(new Intent(this, NightActivity.class)));
        findViewById(R.id.btnBack).setOnClickListener(v -> finish());
    }

    private void sendNotification(String title, String text) {
        NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        String cid = "evening_channel";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            nm.createNotificationChannel(new NotificationChannel(cid, "Evening", NotificationManager.IMPORTANCE_DEFAULT));
        }
        NotificationCompat.Builder b = new NotificationCompat.Builder(this, cid)
                .setSmallIcon(android.R.drawable.ic_lock_power_off)
                .setContentTitle(title).setContentText(text);
        nm.notify(2, b.build());
    }
}