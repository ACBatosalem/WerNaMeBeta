package edu.dlsu.mobapde.wername;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;

public class CreateActivity extends AppCompatActivity {
    Spinner spinnerContacts;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        spinnerContacts = (Spinner) findViewById(R.id.sp_contact);
        ArrayList<String> names = new ArrayList<String>();
        names.add("AAAAA");
        names.add("BBBBB");
        names.add("CCCCC");
        names.add("DDDDD");
        names.add("EEEEE");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, names);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerContacts.setAdapter(adapter);

    }
}
