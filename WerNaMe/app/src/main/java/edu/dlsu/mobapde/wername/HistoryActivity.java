package edu.dlsu.mobapde.wername;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

public class HistoryActivity extends AppCompatActivity {
    RecyclerView rvJourney;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        rvJourney = (RecyclerView) findViewById(R.id.rv_journey);
        ArrayList<Journey> journeys = new ArrayList<>();
        journeys.add(new Journey("Manila", "Makati","ABC-1234"));
        journeys.add(new Journey("Makati", "Manila","ABC-5678"));
        journeys.add(new Journey("Quezon City", "Makati","DEF-1234"));
        journeys.add(new Journey("Imus", "Baclaran","GHI-1234"));
        journeys.add(new Journey("MOA", "Makati","XYZ-1234"));
        journeys.add(new Journey("Makati", "MOA","XYZ-5678"));
        journeys.add(new Journey("SM", "Makati","LMN-1234"));
        journeys.add(new Journey("Imus", "SM","LMN-1234"));

        JourneyAdapter js = new JourneyAdapter(journeys);
        rvJourney.setAdapter(js);
        rvJourney.setLayoutManager(new LinearLayoutManager(
                getBaseContext(),LinearLayoutManager.VERTICAL,false));
    }
}
