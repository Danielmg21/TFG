package com.example.pruebas.utilidades;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.pruebas.GameActivity;
import com.example.pruebas.MainScreen;
import com.example.pruebas.R;

import java.util.Random;

public class Reminder extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        PendingIntent contentIntent = PendingIntent.getActivity(context, 0,
                new Intent(context, MainScreen.class), 0);
        int random = Integer.parseInt(intent.getStringExtra("id"));
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "notifyChanel")
                .setSmallIcon(R.drawable.logo)
                .setColor(Color.BLUE)
                .setContentTitle("THE DRAGON´S CAVE")
                .setContentText("Rápido puedes recibir la bendición de la Diosa")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true);
        builder.setContentIntent(contentIntent);
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from((context));
        notificationManagerCompat.notify(random, builder.build());
    }
}
