package edu.dlsu.mobapde.wername;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

import static edu.dlsu.mobapde.wername.CreateActivity.PENDINGINTENT_TEXT_BR;

/**
 * Created by Angel on 16/12/2017.
 */

public class AlarmNotSentReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Service.NOTIFICATION_SERVICE);

        Intent secondActivity = new Intent(context, ArrivedActivity.class);
        PendingIntent saPI = PendingIntent.getActivity(context,
                CreateActivity.PENDINGINTENT_TEXT_NOT, secondActivity,
                PendingIntent.FLAG_UPDATE_CURRENT);


        NotificationCompat.Builder builder
                = (NotificationCompat.Builder) new NotificationCompat.Builder(context).
                setSmallIcon(R.mipmap.logo)
                .setTicker("Text not sent")
                .setContentTitle("Text was not sent")
                .setContentText("Click here for more options.")
                .setContentIntent(saPI)
                .setAutoCancel(true);
        notificationManager.notify(CreateActivity.NOTIFICATION_ID_NOT, builder.build());


    }
}
