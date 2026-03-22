package com.robert.a5;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class NotifyFragment extends Fragment {


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_notify, container, false);
        view.findViewById(R.id.btnNotify).setOnClickListener(v -> sendPush());
        return view;
    }
    private void sendPush() {
        android.app.NotificationManager nm = (android.app.NotificationManager) getActivity().getSystemService(android.content.Context.NOTIFICATION_SERVICE);
        String channelId = "my_channel";
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            nm.createNotificationChannel(new android.app.NotificationChannel(channelId, "Уведомления", android.app.NotificationManager.IMPORTANCE_DEFAULT));
        }
        androidx.core.app.NotificationCompat.Builder builder = new androidx.core.app.NotificationCompat.Builder(getContext(), channelId)
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setContentTitle("Роберт Бауэр Александрович")
                .setContentText("Группа Т-423901-НТ")
                .setPriority(androidx.core.app.NotificationCompat.PRIORITY_DEFAULT);
        nm.notify(1, builder.build());
    }
}