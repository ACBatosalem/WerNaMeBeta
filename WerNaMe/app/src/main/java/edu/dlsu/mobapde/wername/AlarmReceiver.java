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

import static edu.dlsu.mobapde.wername.CreateActivity.PENDINGINTENT_BR;
import static edu.dlsu.mobapde.wername.CreateActivity.PENDINGINTENT_TEXT_BR;

/**
 * Created by Angel on 02/11/2017.
 */

public class AlarmReceiver extends BroadcastReceiver {

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
                .setTicker("Need more time?")
                .setContentTitle("Have you arrived at your destination?")
                .setContentText("Click here to extend time or end journey. A text will be sent after 30 minutes if no action is done")
                .setContentIntent(saPI)
                .setAutoCancel(true);
        notificationManager.notify(CreateActivity.NOTIFICATION_ID_WK, builder.build());

        AlarmManager alarmManager
                = (AlarmManager) context.getSystemService(Service.ALARM_SERVICE);
        Intent broadcastIntent = new Intent(context, AlarmTextReceiver.class);
        PendingIntent bcPI
                = PendingIntent.getBroadcast(context,
                PENDINGINTENT_TEXT_BR, broadcastIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        alarmManager.set(AlarmManager.RTC_WAKEUP,
                System.currentTimeMillis() + 3*1000*60,
                bcPI);

        boolean alarmUp = (bcPI != null);

        if (alarmUp)
        {
            Log.d("myTag", "Alarm is already active");
        } else Log.d("myTag", "AYOKO NA");
    }
}
