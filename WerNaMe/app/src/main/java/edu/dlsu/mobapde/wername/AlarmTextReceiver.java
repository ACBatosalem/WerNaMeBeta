package edu.dlsu.mobapde.wername;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.NotificationCompat;

/**
 * Created by Angel on 15/12/2017.
 */

public class AlarmTextReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Service.NOTIFICATION_SERVICE);

        Intent secondActivity = new Intent(context, ArrivedActivity.class);
        PendingIntent saPI = PendingIntent.getActivity(context,
                CreateActivity.PENDINGINTENT_SA, secondActivity,
                PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder
                = (NotificationCompat.Builder) new NotificationCompat.Builder(context).
                setSmallIcon(R.mipmap.logo)
                .setTicker("A text was sent!")
                .setContentTitle("Where are you?")
                .setContentText("Text was sent.")
                .setContentIntent(saPI)
                .setAutoCancel(true);
        notificationManager.notify(CreateActivity.NOTIFICATION_ID_WK, builder.build());
    }
}