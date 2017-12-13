package edu.dlsu.mobapde.wername;

import android.Manifest;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import static android.support.v4.widget.CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER;

public class CreateActivity extends AppCompatActivity {
    Spinner spinnerContacts, spinnerHours, spinnerMinutes;
    Button buttonSend;
    EditText etMessage, etPlateNum;
    String phoneNo, message;
    DatabaseHelper databaseHelper;
    TextView createText, contactText, historyText;

    public static final int NOTIFICATION_ID_WK = 0;
    public static final int PENDINGINTENT_SA = 0;
    public static final int PENDINGINTENT_BR = 1;
    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        databaseHelper = new DatabaseHelper(getBaseContext());

        buttonSend = (Button) findViewById(R.id.btn_send);
        etMessage = (EditText) findViewById(R.id.et_text);
        etPlateNum = (EditText) findViewById(R.id.et_plateNum);
        createText = (TextView) findViewById(R.id.create);
        contactText = (TextView) findViewById(R.id.contacts);
        historyText = (TextView) findViewById(R.id.history);

      /*  String udata="Create";
        SpannableString content = new SpannableString(udata);
        content.setSpan(new UnderlineSpan(), 0, udata.length(), 0);//where first 0 shows the starting and udata.length() shows the ending span.if you want to span only part of it than you can change these values like 5,8 then it will underline part of it.
        createText.setText(content);*/

        // Spinner for Contacts
        spinnerContacts = (Spinner) findViewById(R.id.sp_contact);
        ArrayList<String> names = new ArrayList<>();

        // Adapter for Contacts Spinner
        Cursor cursor = databaseHelper.getAllContacts();
        if(cursor.getCount() > 0) {
            String[] from = new String[]{Contact.COLUMN_NAME};
            int[] to = new int[]{android.R.id.text1};

            SimpleCursorAdapter mAdapter = new SimpleCursorAdapter(this, android.R.layout.simple_spinner_item,
                    cursor, from, to,FLAG_REGISTER_CONTENT_OBSERVER);

            mAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerContacts.setAdapter(mAdapter);
        }

        // Spinner for ETA Hours
        spinnerHours = (Spinner) findViewById(R.id.sp_hours);
        ArrayList<String> hours = new ArrayList<>();
        for(int i=0;i<24;i++)
            hours.add(String.valueOf(i));

        // Adapter for ETA Hours Spinner
        ArrayAdapter<String> hoursArrayAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, hours);
        hoursArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerHours.setAdapter(hoursArrayAdapter);

        // Spinner for ETA Minutes
        spinnerMinutes = (Spinner) findViewById(R.id.sp_minutes);
        ArrayList<String> minutes = new ArrayList<>();
        for(int i=0;i<60;i++)
            minutes.add(String.valueOf(i));

        // Adapter for ETA Minutes Spinner
        ArrayAdapter<String> minutesArrayAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, minutes);
        minutesArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMinutes.setAdapter(minutesArrayAdapter);

        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create an alarm to create notif
                String src = etSrc.getText().toString();
                String dest = etDest.getText().toString();
                String plateNum = etPlateNum.getText().toString();

                addJourney(src, dest, plateNum);
               // sendSMSMessage();
                int minutes = Integer.parseInt(spinnerMinutes.getSelectedItem().toString());
                int hours = Integer.parseInt(spinnerHours.getSelectedItem().toString());
                setAlarm(minutes, hours);

                SharedPreferences dsp = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
                SharedPreferences.Editor dspEditor = dsp.edit();
                dspEditor.putBoolean("trip", true);
                dspEditor.commit();

                Intent i = new Intent(getBaseContext(), ArrivedActivity.class);
                startActivity(i);
                finish();

            }
        });

        contactText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getBaseContext(), ContactActivity.class);
                startActivity(i);
                finish();
            }
        });

        historyText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getBaseContext(), HistoryActivity.class);
                startActivity(i);
                finish();
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
        phoneNo = "09298642815";
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