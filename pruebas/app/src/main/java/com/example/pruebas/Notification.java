package com.example.pruebas;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class Notification extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent){
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "ID")
                .setSmallIcon(R.drawable.logo)
                .setColor(Color.BLUE)
                .setContentTitle("THE DRAGON´S CAVE")
                .setContentText("Rápido puedes recibir la bendición de la Diosa")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        System.out.println("Canal temrinando de crearse");
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from((context));
        notificationManagerCompat.notify(200, builder.build());
    }
}
