package edu.dlsu.mobapde.wername;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ArrivedActivity extends AppCompatActivity {
    Button bExtend, bArrived, bCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arrived);
        SharedPreferences dsp =
                PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        long trip = dsp.getLong("trip", -1);
        bExtend = (Button) findViewById(R.id.btn_extend);
        bArrived = (Button) findViewById(R.id.btn_arrived);
        bCancel = (Button) findViewById(R.id.btn_cancel);

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
