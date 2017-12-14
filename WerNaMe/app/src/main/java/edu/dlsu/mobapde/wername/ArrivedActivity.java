package edu.dlsu.mobapde.wername;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.Date;

public class ArrivedActivity extends AppCompatActivity {
    Button bExtend, bArrived, bCancel;
    TextView tvStartTime, tvEndTime, tvPlateNum, tvTravelTime, tvMessage, tvSrc, tvDest;
    DatabaseHelper databaseHelper;

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

            DateFormat df = DateFormat.getTimeInstance(DateFormat.SHORT);
            String newTravelTime = df.format(new Date(travelTime));
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
                SharedPreferences dsp = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
                SharedPreferences.Editor dspEditor = dsp.edit();
                long trip = dsp.getLong("trip", -1);
                dspEditor.putLong("trip", trip);
                dspEditor.commit();

                Intent i = new Intent(getBaseContext(), ExtendActivity.class);
                startActivity(i);
                finish();
            }
        });

        bArrived.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences dsp =
                        PreferenceManager.getDefaultSharedPreferences(getBaseContext());
                SharedPreferences.Editor dspEditor = dsp.edit();
                dspEditor.remove("trip");
                dspEditor.commit();

                Intent i = new Intent(getBaseContext(), CreateActivity.class);
                startActivity(i);
                finish();


            }
        });

        if(trip == -1) {
            Intent i = new Intent(getBaseContext(), CreateActivity.class);
            startActivity(i);
            finish();
        }


    }
}
