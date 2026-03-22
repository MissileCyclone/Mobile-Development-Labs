package com.robert.a5;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class TestFragment extends Fragment {


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_test, container, false);
        view.findViewById(R.id.btnAnswer).setOnClickListener(v -> {
            String[] variants = {"12 вольт", "220 вольт", "380 вольт"};
            new androidx.appcompat.app.AlertDialog.Builder(getContext())
                    .setTitle("Выберите правильный вариант")
                    .setItems(variants, (dialog, which) -> {
                        if (which == 1) { // 220 вольт
                            android.widget.Toast.makeText(getContext(), "Правильно!", android.widget.Toast.LENGTH_SHORT).show();
                        } else {
                            android.widget.Toast.makeText(getContext(), "Неверно, попробуйте еще раз", android.widget.Toast.LENGTH_SHORT).show();
                        }
                    }).show();
        });
        return view;
    }
}