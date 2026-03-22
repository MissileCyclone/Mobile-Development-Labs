package com.robert.a7;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.DrawableRes;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private ActivityResultLauncher<String> cameraPermissionLauncher;
    private ActivityResultLauncher<Intent> scanLauncher;

    private static final Map<String, QrInfo> SUPPORTED_CODES;

    static {
        Map<String, QrInfo> codes = new HashMap<>();

        codes.put("дом", new QrInfo(R.string.house_title, R.string.house_description, android.R.drawable.ic_menu_myplaces));
        codes.put("университет", new QrInfo(R.string.campus_title, R.string.campus_description, android.R.drawable.ic_menu_directions));
        codes.put("студент", new QrInfo(R.string.student_title, R.string.student_description, android.R.drawable.ic_menu_info_details));
        SUPPORTED_CODES = Collections.unmodifiableMap(codes);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Button scanButton = findViewById(R.id.scanButton);
        setupLaunchers();
        scanButton.setOnClickListener(v -> tryLaunchScanner());
    }

    private void setupLaunchers() {
        cameraPermissionLauncher = registerForActivityResult(
                new ActivityResultContracts.RequestPermission(),
                isGranted -> {
                    if (isGranted) launchScanner();
                    else Toast.makeText(this, "Доступ к камере отклонен", Toast.LENGTH_SHORT).show();
                }
        );

        scanLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    IntentResult scanResult = IntentIntegrator.parseActivityResult(result.getResultCode(), result.getData());
                    if (scanResult != null && scanResult.getContents() != null) {
                        handleScanResult(scanResult.getContents());
                    }
                }
        );
    }

    private void tryLaunchScanner() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            launchScanner();
        } else {
            cameraPermissionLauncher.launch(Manifest.permission.CAMERA);
        }
    }

    private void launchScanner() {
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE);
        integrator.setOrientationLocked(true);
        integrator.setPrompt("Наведите на QR-код");
        integrator.setBeepEnabled(true);
        scanLauncher.launch(integrator.createScanIntent());
    }

    private void handleScanResult(String contents) {
        String normalized = contents.trim().toLowerCase(Locale.getDefault());
        QrInfo info = SUPPORTED_CODES.get(normalized);

        if (info == null) {
            Toast.makeText(this, "Код не распознан", Toast.LENGTH_SHORT).show();
            return;
        }


        Intent detailIntent = new Intent(this, DetailActivity.class);
        detailIntent.putExtra(DetailActivity.EXTRA_TITLE, getString(info.titleRes));
        detailIntent.putExtra(DetailActivity.EXTRA_DESCRIPTION, getString(info.descriptionRes));
        detailIntent.putExtra(DetailActivity.EXTRA_IMAGE, info.imageRes);
        startActivity(detailIntent);
    }

    private static class QrInfo {
        final int titleRes, descriptionRes, imageRes;
        QrInfo(int t, int d, int i) {
            this.titleRes = t; this.descriptionRes = d; this.imageRes = i;
        }
    }
}