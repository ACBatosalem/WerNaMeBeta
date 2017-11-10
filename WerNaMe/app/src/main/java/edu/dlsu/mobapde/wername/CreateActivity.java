package edu.dlsu.mobapde.wername;

import android.Manifest;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.SystemClock;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

import static android.support.v4.widget.CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER;

public class CreateActivity extends AppCompatActivity {
    Spinner spinnerContacts;
    Button buttonNow, buttonLater;
    EditText etHours, etMinutes, etMessage, etSrc, etDest, etPlateNum;
    String phoneNo, message;
    DatabaseHelper databaseHelper;

    public static final int NOTIFICATION_ID_WK = 0;
    public static final int PENDINGINTENT_SA = 0;
    public static final int PENDINGINTENT_BR = 1;
    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        databaseHelper = new DatabaseHelper(getBaseContext());

        buttonNow = (Button) findViewById(R.id.btn_now);
        buttonLater = (Button) findViewById(R.id.btn_later);
        etHours = (EditText) findViewById(R.id.et_hours);
        etMinutes = (EditText) findViewById(R.id.et_minutes);
        etMessage = (EditText) findViewById(R.id.et_text);
        etSrc = (EditText) findViewById(R.id.et_src);
        etDest = (EditText) findViewById(R.id.et_dest);
        etPlateNum = (EditText) findViewById(R.id.et_plateNum);

        spinnerContacts = (Spinner) findViewById(R.id.sp_contact);
        ArrayList<String> names = new ArrayList<>();
        names.add("AAAAA");
        names.add("BBBBB");
        names.add("CCCCC");
        names.add("DDDDD");
        names.add("EEEEE");

        Cursor cursor = databaseHelper.getAllContacts();

        if(cursor.getCount() > 0) {
            String[] from = new String[]{Contact.COLUMN_NAME};
            int[] to = new int[]{android.R.id.text1};

            SimpleCursorAdapter mAdapter = new SimpleCursorAdapter(this, android.R.layout.simple_spinner_item,
                    cursor, from, to,FLAG_REGISTER_CONTENT_OBSERVER);

            mAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerContacts.setAdapter(mAdapter);
        }

        buttonNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create an alarm to create notif
                String src = etSrc.getText().toString();
                String dest = etDest.getText().toString();
                String plateNum = etPlateNum.getText().toString();

                addJourney(src, dest, plateNum);
                sendSMSMessage();
                int minutes = Integer.parseInt(etMinutes.getText().toString());
                int hours = Integer.parseInt(etHours.getText().toString());
                setAlarm(minutes, hours);


            }
        });
        buttonLater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create an alarm to create notif
                String src = etSrc.getText().toString();
                String dest = etDest.getText().toString();
                String plateNum = etPlateNum.getText().toString();

                addJourney(src, dest, plateNum);
                int minutes = Integer.parseInt(etMinutes.getText().toString());
                int hours = Integer.parseInt(etHours.getText().toString());
                setAlarm(minutes, hours);
            }
        });

    }

    private void addJourney(String source, String destination, String plateNumber) {
        databaseHelper.addJourney(new Journey(source, destination, plateNumber));
    }

    private void setAlarm(int minutes, int hours) {
        AlarmManager alarmManager
                = (AlarmManager)getSystemService(Service.ALARM_SERVICE);
        Intent broadcastIntent = new Intent(getBaseContext(), AlarmReceiver.class);
        PendingIntent bcPI
                = PendingIntent.getBroadcast(getBaseContext(),
                PENDINGINTENT_BR, broadcastIntent, PendingIntent.FLAG_UPDATE_CURRENT);


        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                minutes*1000*60 + hours*60*60*1000  + SystemClock.elapsedRealtime(),
                bcPI);
    }

    protected void sendSMSMessage() {
        phoneNo = "09950553416";
        message = etMessage.getText().toString();
        Log.d("mmhmm", "sendSMSMessage: " + message);
        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNo, null, message, null, null);
            Toast.makeText(getApplicationContext(), "SMS Sent!",
                    Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(),
                    "SMS failed, please try again later!",
                    Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }
}