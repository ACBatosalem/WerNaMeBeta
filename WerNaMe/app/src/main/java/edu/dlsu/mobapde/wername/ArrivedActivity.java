package edu.dlsu.mobapde.wername;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import static edu.dlsu.mobapde.wername.CreateActivity.PENDINGINTENT_BR;
import static edu.dlsu.mobapde.wername.CreateActivity.PENDINGINTENT_NOT_BR;
import static edu.dlsu.mobapde.wername.CreateActivity.PENDINGINTENT_TEXT_BR;

public class ArrivedActivity extends AppCompatActivity 
        implements TextDialog.TextDialogListener, CancelDialog.CancelDialogListener,
        ExtendDialog.ExtendDialogListener{
    Button bExtend, bArrived, bCancel;
    TextView tvStartTime, tvEndTime, tvPlateNum, tvTravelTime, tvMessage, tvSrc, tvDest;
    DatabaseHelper databaseHelper;
    long remTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arrived);

        SharedPreferences dsp =
                PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        long trip = dsp.getLong("trip", -1);

        databaseHelper = new DatabaseHelper(getBaseContext());

        bExtend = (Button) findViewById(R.id.btn_extend);
        bArrived = (Button) findViewById(R.id.btn_arrived);
        bCancel = (Button) findViewById(R.id.btn_cancel);
        tvStartTime = (TextView) findViewById(R.id.tv_time_start);
        tvEndTime = (TextView) findViewById(R.id.tv_time_end);
        tvPlateNum = (TextView) findViewById(R.id.tv_platenum);
        tvTravelTime = (TextView) findViewById(R.id.tv_travelTime);
        tvMessage = (TextView) findViewById(R.id.tv_message);
        tvSrc = (TextView) findViewById(R.id.tv_src);
        tvDest = (TextView) findViewById(R.id.tv_dst);

        // Get Journey details from Database
        if (trip != -1) {
            Journey j = databaseHelper.getJourney(trip);
            String source = j.getSource();
            String destination = j.getDestination();
            String plateNumber = j.getPlate_number();
            String message = j.getTextSentTo();
            long startTime = j.getStartTime();
            long travelTime = j.getEstimatedTA();
            long actualTA = j.getActualTA();
            remTime = travelTime - System.currentTimeMillis();

            DateFormat df = DateFormat.getDateTimeInstance(DateFormat.MEDIUM,DateFormat.SHORT);
            String newTravelTime = ((remTime / (1000*60*60)) % 24) + "h " +
                    ((remTime / (1000*60)) % 60) + "m";
            String newActualTA = df.format(new Date(actualTA));
            String newStartTime = df.format(new Date(startTime));

            // Set Journey details to Front end
            tvSrc.setText(source);
            tvDest.setText(destination);
            tvPlateNum.setText(plateNumber);
            tvMessage.setText(message);
            tvStartTime.setText(newStartTime);
            tvEndTime.setText(newActualTA);
            tvTravelTime.setText(newTravelTime);
        }

        bExtend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ExtendDialog td = new ExtendDialog();
                td.show(getSupportFragmentManager(), "");
            }
        });

        bArrived.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrivedDialog td = new ArrivedDialog();
                td.show(getSupportFragmentManager(), "");

            }
        });

        bCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CancelDialog td = new CancelDialog();
                td.show(getSupportFragmentManager(), "");
            }
        });

        if(trip == -1) {
            Intent i = new Intent(getBaseContext(), CreateActivity.class);
            startActivity(i);
            finish();
        }
    }

    @Override
    public void onDialogClick(boolean sendText) {
        if(sendText) {
            //TODO get number and send text
            sendSMSMessage(getBaseContext());
        }
        SharedPreferences dsp =
                PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        SharedPreferences.Editor dspEditor = dsp.edit();
        long trip = dsp.getLong("trip", -1);

        Journey currJourney = databaseHelper.getJourney(trip);
        currJourney.setActualTA(System.currentTimeMillis());
        databaseHelper.editJourney(trip, currJourney);

        dspEditor.remove("trip");
        dspEditor.commit();

        cancelAlarm();

        Intent i = new Intent(getBaseContext(), CreateActivity.class);
        startActivity(i);
        finish();
    }

    @Override
    public void onCancelClick() {
        Log.d("cancel", "ey");
        SharedPreferences dsp = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        SharedPreferences.Editor dspEditor = dsp.edit();
        long trip = dsp.getLong("trip", -1);
        databaseHelper.removeContact((int) trip);
        dspEditor.remove("trip");
        dspEditor.commit();

        cancelAlarm();

        Intent i = new Intent(getBaseContext(), CreateActivity.class);
        startActivity(i);
        finish();
    }

    public void cancelAlarm() {
        AlarmManager alarmManager
                = (AlarmManager) getSystemService(Service.ALARM_SERVICE);
        if(remTime > 0) {

            Intent broadcastIntent = new Intent(getBaseContext(), AlarmReceiver.class);
            PendingIntent bcPI
                    = PendingIntent.getBroadcast(getBaseContext(),
                    PENDINGINTENT_BR, broadcastIntent, PendingIntent.FLAG_UPDATE_CURRENT);

            alarmManager.cancel(bcPI);
            bcPI.cancel();
        } else {
            Intent broadcastIntent = new Intent(getBaseContext(), AlarmTextReceiver.class);
            PendingIntent bcPI
                    = PendingIntent.getBroadcast(getBaseContext(),
                    PENDINGINTENT_TEXT_BR, broadcastIntent, PendingIntent.FLAG_UPDATE_CURRENT);

            alarmManager.cancel(bcPI);
            bcPI.cancel();
        }

    }

    @Override
    public void extendTime(long newTime, long id) {

        Journey currJourney = databaseHelper.getJourney(id);
        currJourney.setEstimatedTA(newTime + currJourney.getEstimatedTA());
        currJourney.setActualTA(newTime + currJourney.getActualTA());
        databaseHelper.editJourney(id, currJourney);

        cancelAlarm();

        setAlarm(currJourney.getEstimatedTA());
        Log.d("myTag", "extended");

        Intent i = new Intent(getBaseContext(), ArrivedActivity.class);
        startActivity(i);
        finish();
    }

    private void setAlarm(long elapsedTime) {
        DateFormat df = DateFormat.getDateTimeInstance(DateFormat.MEDIUM,DateFormat.SHORT);
        Log.d("TIMETIME",df.format(new Date(elapsedTime)));
        AlarmManager alarmManager
                = (AlarmManager)getSystemService(Service.ALARM_SERVICE);
        Intent broadcastIntent = new Intent(getBaseContext(), AlarmReceiver.class);
        PendingIntent bcPI
                = PendingIntent.getBroadcast(getBaseContext(),
                PENDINGINTENT_BR, broadcastIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        alarmManager.set(AlarmManager.RTC_WAKEUP,
                elapsedTime,
                bcPI);
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
