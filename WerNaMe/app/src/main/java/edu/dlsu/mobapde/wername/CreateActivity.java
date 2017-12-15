package edu.dlsu.mobapde.wername;

import android.Manifest;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static android.support.v4.widget.CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER;

public class CreateActivity extends AppCompatActivity
        implements TextDialog.TextDialogListener{
    Spinner spinnerContacts, spinnerHours, spinnerMinutes;
    Button buttonSend, buttonSrc, buttonDesti;
    EditText etMessage, etPlateNum;
    String phoneNo, message;
    DatabaseHelper databaseHelper;
    TextView createText, contactText, historyText;
    public AlarmManager alarmManager;
    String contactName;

    public static final int NOTIFICATION_ID_WK = 0;
    public static final int NOTIFICATION_ID_TEXT = 1;
    public static final int NOTIFICATION_ID_NOT = 2;
    public static final int PENDINGINTENT_SA = 0;
    public static final int PENDINGINTENT_BR = 1;
    public static final int PENDINGINTENT_TEXT_NOT = 3;
    public static final int PENDINGINTENT_TEXT = 2;
    public static final int PENDINGINTENT_NOT_BR = 7;
    public static final int PENDINGINTENT_TEXT_BR = 4;
    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS = 0;
    private static final int PLACE_PICKER_SRC_REQUEST = 1;
    private static final int PLACE_PICKER_DEST_REQUEST = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        databaseHelper = new DatabaseHelper(getBaseContext());

        buttonSend = (Button) findViewById(R.id.btn_send);
        buttonSrc = (Button) findViewById(R.id.btn_src);
        buttonDesti = (Button) findViewById(R.id.btn_dest);
        etMessage = (EditText) findViewById(R.id.et_text);
        etPlateNum = (EditText) findViewById(R.id.et_plateNum);
        createText = (TextView) findViewById(R.id.create);
        contactText = (TextView) findViewById(R.id.contacts);
        historyText = (TextView) findViewById(R.id.history);


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

        spinnerContacts.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onItemSelected(AdapterView parent, View view,
                                       int pos, long log) {

                Cursor c = (Cursor)parent.getItemAtPosition(pos);
                String name = c.getString(c.getColumnIndexOrThrow(Contact.COLUMN_NAME));
                setContactName(name);
            }

            public void onNothingSelected(AdapterView arg0) {

            }
        });

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

        /* Button Listeners */
        buttonSrc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();

                Intent intent;
                try {
                    intent = builder.build(getApplicationContext());
                    startActivityForResult(intent, PLACE_PICKER_SRC_REQUEST);
                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }
            }
        });

        buttonDesti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();

                Intent intent;
                try {
                    intent = builder.build(getApplicationContext());
                    startActivityForResult(intent, PLACE_PICKER_DEST_REQUEST);
                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }
            }
        });

        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create an alarm to create notif
                String src = buttonSrc.getText().toString();
                String dest = buttonDesti.getText().toString();
                String plateNum = etPlateNum.getText().toString();
                String message = etMessage.getText().toString();
                int minutes = Integer.parseInt(spinnerMinutes.getSelectedItem().toString());
                int hours = Integer.parseInt(spinnerHours.getSelectedItem().toString());
                long startTime = System.currentTimeMillis();
                long elapsedTime = minutes*1000*60 + hours*60*60*1000 + startTime;

                Log.d("AYAW NA", message);

                if(src.equals("Source") || dest.equals("Destination") || plateNum.equals("") ||
                        (minutes == 0 && hours == 0) || message.equals("")) {
                    Toast.makeText(getApplicationContext(),
                            "Please fill out all fields",
                            Toast.LENGTH_SHORT).show();
                } else {
                    DateFormat df = DateFormat.getTimeInstance(DateFormat.SHORT);
                    message += src + "->" + dest + ".ETA: " + df.format(new Date(elapsedTime));
                    long id = addJourney(src, dest, plateNum, startTime, elapsedTime, message);
                    // sendSMSMessage();
                    setAlarm(elapsedTime);

                    SharedPreferences dsp = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
                    SharedPreferences.Editor dspEditor = dsp.edit();
                    dspEditor.putLong("trip", id);
                    dspEditor.commit();

                    long trip = dsp.getLong("trip", -1);
                    Log.d("id journey", "onClick: "+trip);

                    TextDialog td = new TextDialog();
                    td.show(getSupportFragmentManager(), "");
                }
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

    private void setContactName(String name) {
        Log.d("Name", name);
        contactName = name;
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == PLACE_PICKER_SRC_REQUEST) {
            System.out.println("Hello World");
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(intent, this);
                //String address = String.format("Place: %s", place.getName());
                buttonSrc.setText(place.getName());
            }
            else
                System.out.println(resultCode);
        }
        else if (requestCode == PLACE_PICKER_DEST_REQUEST) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(intent, this);
                //String address = String.format("Place: %s", place.getName());
                buttonDesti.setText(place.getName());
            }
        }
    }

    private long addJourney(String source, String destination, String plateNumber,
                            long startTime, long estimatedTA, String message) {
        Log.d("ayaw na", message);
        return databaseHelper.addJourney(new Journey(source, destination, plateNumber,
                startTime, estimatedTA,  message, contactName));
    }

    private void setAlarm(long elapsedTime) {
        alarmManager
                = (AlarmManager)getSystemService(Service.ALARM_SERVICE);
        Intent broadcastIntent = new Intent(getBaseContext(), AlarmReceiver.class);
        PendingIntent bcPI
                = PendingIntent.getBroadcast(getBaseContext(),
                PENDINGINTENT_BR, broadcastIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        alarmManager.set(AlarmManager.RTC_WAKEUP,
                        elapsedTime,
                        bcPI);
    }



    @Override
    public void onDialogClick(boolean sendText) {
        if(sendText) {
            //TODO get number and send text
            sendSMSMessage(getBaseContext());
        }
        int minutes = Integer.parseInt(spinnerMinutes.getSelectedItem().toString());
        int hours = Integer.parseInt(spinnerHours.getSelectedItem().toString());
        long startTime = System.currentTimeMillis();
        long elapsedTime = minutes*1000*60 + hours*60*60*1000 + startTime;
        setAlarm(elapsedTime);
        Intent i = new Intent(getBaseContext(), ArrivedActivity.class);
        startActivity(i);
        finish();
    }

    protected void sendSMSMessage(Context context) {


        SharedPreferences dsp = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        long trip = dsp.getLong("trip", -1);
        Journey j = databaseHelper.getJourney(trip);
        Contact c = databaseHelper.getContact(j.getTextSentTo());
        Log.d("mmhmm", "sendSMSMessage: " + j.getMessage() + " " + c.getNumber());
        boolean sent = false;
        for(int i=0; i<3 && !sent; i++) {
            try {
                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage(c.getNumber(), null, j.getMessage(), null, null);
                sent = true;
                Toast.makeText(getApplicationContext(), "SMS Sent!",
                        Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(),
                        "SMS failed, please try again later!",
                        Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        }

        if(!sent) {
            AlarmManager alarmManager
                    = (AlarmManager)getSystemService(Service.ALARM_SERVICE);
            Intent broadcastIntent = new Intent(getBaseContext(), AlarmNotSentReceiver.class);
            PendingIntent bcPI
                    = PendingIntent.getBroadcast(getBaseContext(),
                    PENDINGINTENT_NOT_BR, broadcastIntent, PendingIntent.FLAG_UPDATE_CURRENT);

            alarmManager.set(AlarmManager.RTC_WAKEUP,
                    System.currentTimeMillis(),
                    bcPI);
        }
    }


}