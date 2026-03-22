package com.robert.a5;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class FeedbackFragment extends Fragment {


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_feedback, container, false);
        EditText editFio = view.findViewById(R.id.editFio);
        EditText editEmail = view.findViewById(R.id.editEmail);
        view.findViewById(R.id.btnSend).setOnClickListener(v -> {
            new androidx.appcompat.app.AlertDialog.Builder(getContext())
                    .setTitle("Заявка принята")
                    .setMessage("ФИО: " + editFio.getText() + "\nEmail: " + editEmail.getText())
                    .setPositiveButton("ОК", null).show();
            editFio.setText(""); editEmail.setText(""); // Очистка полей
        });
        return view;
    }
}