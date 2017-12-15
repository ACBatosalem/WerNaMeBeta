package edu.dlsu.mobapde.wername;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.NotificationCompat;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Angel on 15/12/2017.
 */

public class AlarmTextReceiver extends BroadcastReceiver {
    DatabaseHelper databaseHelper;
    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Service.NOTIFICATION_SERVICE);

        Intent secondActivity = new Intent(context, ArrivedActivity.class);
        PendingIntent saPI = PendingIntent.getActivity(context,
                CreateActivity.PENDINGINTENT_TEXT, secondActivity,
                PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder
                = (NotificationCompat.Builder) new NotificationCompat.Builder(context).
                setSmallIcon(R.mipmap.logo)
                .setTicker("A text was sent!")
                .setContentTitle("Where are you?")
                .setContentText("Text was sent.")
                .setContentIntent(saPI)
                .setAutoCancel(true);
        notificationManager.notify(CreateActivity.NOTIFICATION_ID_TEXT, builder.build());
        databaseHelper = new DatabaseHelper(context);
        sendSMSMessage(context);
    }

    protected void sendSMSMessage(Context context) {


        SharedPreferences dsp = PreferenceManager.getDefaultSharedPreferences(context);
        long trip = dsp.getLong("trip", -1);
        Journey j = databaseHelper.getJourney(trip);
        Contact c = databaseHelper.getContact(j.getTextSentTo());
        Log.d("mmhmm", "sendSMSMessage: " + j.getMessage() + " " + c.getNumber());
        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(c.getNumber(), null, j.getMessage(), null, null);
            Toast.makeText(context.getApplicationContext(), "SMS Sent!",
                    Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(context.getApplicationContext(),
                    "SMS failed, please try again later!",
                    Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }
}