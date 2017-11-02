package edu.dlsu.mobapde.wername;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;

public class CreateActivity extends AppCompatActivity {
    Spinner spinnerContacts;
    Button buttonNow, buttonLater;
    EditText etHours, etMinutes;

    public static final int NOTIFICATION_ID_WK = 0;
    public static final int PENDINGINTENT_SA = 0;
    public static final int PENDINGINTENT_BR = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        buttonNow = (Button) findViewById(R.id.btn_now);
        buttonLater = (Button) findViewById(R.id.btn_later);
        etHours = (EditText) findViewById(R.id.et_hours);
        etMinutes = (EditText) findViewById(R.id.et_minutes);

        spinnerContacts = (Spinner) findViewById(R.id.sp_contact);
        ArrayList<String> names = new ArrayList<>();
        names.add("AAAAA");
        names.add("BBBBB");
        names.add("CCCCC");
        names.add("DDDDD");
        names.add("EEEEE");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, names);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerContacts.setAdapter(adapter);

        View.OnClickListener cl = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create an alarm to create notif

                AlarmManager alarmManager
                        = (AlarmManager)getSystemService(Service.ALARM_SERVICE);

                int minutes = Integer.parseInt(etMinutes.getText().toString());
                int hours = Integer.parseInt(etHours.getText().toString());

                Intent broadcastIntent = new Intent(getBaseContext(), AlarmReceiver.class);
                PendingIntent bcPI
                        = PendingIntent.getBroadcast(getBaseContext(),
                        PENDINGINTENT_BR, broadcastIntent, PendingIntent.FLAG_UPDATE_CURRENT);

                alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                        minutes*1000 + hours*60*60*1000 + SystemClock.elapsedRealtime(),
                        bcPI);
            }
        };

        buttonNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create an alarm to create notif
                AlarmManager alarmManager
                        = (AlarmManager)getSystemService(Service.ALARM_SERVICE);

                int minutes = Integer.parseInt(etMinutes.getText().toString());
                int hours = Integer.parseInt(etHours.getText().toString());

                Intent broadcastIntent = new Intent(getBaseContext(), AlarmReceiver.class);
                PendingIntent bcPI
                        = PendingIntent.getBroadcast(getBaseContext(),
                        PENDINGINTENT_BR, broadcastIntent, PendingIntent.FLAG_UPDATE_CURRENT);


                alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                        minutes*1000 + hours*60*60*1000  + SystemClock.elapsedRealtime(),
                        bcPI);
            }
        });
        //buttonLater.setOnClickListener(cl);

    }
}
