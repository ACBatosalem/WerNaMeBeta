package edu.dlsu.mobapde.wername;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {
    LinearLayout history, ice_list, createJourney;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new DatabaseHelper(getBaseContext());

        history = (LinearLayout) findViewById(R.id.history);
        ice_list = (LinearLayout) findViewById(R.id.ice);
        createJourney = (LinearLayout) findViewById(R.id.create);

        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getBaseContext(), HistoryActivity.class);
                startActivity(i);
            }
        });

        ice_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getBaseContext(), ContactActivity.class);
                startActivity(i);
            }
        });

        createJourney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getBaseContext(), CreateActivity.class);
                startActivity(i);
            }
        });
    }
}
