package edu.dlsu.mobapde.wername;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class HistoryActivity extends AppCompatActivity {
    RecyclerView rvJourney;
    DatabaseHelper databaseHelper;

    TextView createText, contactText, historyText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        rvJourney = (RecyclerView) findViewById(R.id.rv_journey);
        databaseHelper = new DatabaseHelper(getBaseContext());

        createText = (TextView) findViewById(R.id.create);
        contactText = (TextView) findViewById(R.id.contacts);
        historyText = (TextView) findViewById(R.id.history);

       /* String udata="History";
        SpannableString content = new SpannableString(udata);
        content.setSpan(new UnderlineSpan(), 0, udata.length(), 0);//where first 0 shows the starting and udata.length() shows the ending span.if you want to span only part of it than you can change these values like 5,8 then it will underline part of it.
        historyText.setText(content);*/
        /*ArrayList<Journey> journeys = new ArrayList<>();
        journeys.add(new Journey("Manila", "Makati","ABC-1234"));
        journeys.add(new Journey("Makati", "Manila","ABC-5678"));
        journeys.add(new Journey("Quezon City", "Makati","DEF-1234"));
        journeys.add(new Journey("Imus", "Baclaran","GHI-1234"));
        journeys.add(new Journey("MOA", "Makati","XYZ-1234"));
        journeys.add(new Journey("Makati", "MOA","XYZ-5678"));
        journeys.add(new Journey("SM", "Makati","LMN-1234"));
        journeys.add(new Journey("Imus", "SM","LMN-1234"));*/


        contactText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getBaseContext(), ContactActivity.class);
                startActivity(i);
                finish();
            }
        });

        createText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getBaseContext(), CreateActivity.class);
                startActivity(i);
                finish();
            }
        });

        JourneyAdapter js = new JourneyAdapter(getBaseContext(), databaseHelper.getAllJourneysCursor());
        rvJourney.setAdapter(js);
        rvJourney.setLayoutManager(new LinearLayoutManager(
                getBaseContext(),LinearLayoutManager.VERTICAL,false));
    }
}
