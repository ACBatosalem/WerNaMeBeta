package edu.dlsu.mobapde.wername;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

public class ContactActivity extends AppCompatActivity {
    RecyclerView rvContact;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        rvContact = (RecyclerView) findViewById(R.id.rv_contacts);
        ArrayList<Contact> contacts = new ArrayList<>();
        contacts.add(new Contact("AAAAA", "09151234567"));
        contacts.add(new Contact("BBBBB", "09151234567"));
        contacts.add(new Contact("CCCCC", "09151234567"));
        contacts.add(new Contact("DDDDD", "09151234567"));
        contacts.add(new Contact("EEEEE", "09151234567"));
        contacts.add(new Contact("AAAAA", "09151234567"));
        contacts.add(new Contact("BBBBB", "09151234567"));
        contacts.add(new Contact("CCCCC", "09151234567"));
        contacts.add(new Contact("DDDDD", "09151234567"));
        contacts.add(new Contact("EEEEE", "09151234567"));

        ContactAdapter cs = new ContactAdapter(contacts);
        rvContact.setAdapter(cs);
        rvContact.setLayoutManager(new LinearLayoutManager(
                getBaseContext(),LinearLayoutManager.VERTICAL,false));
    }
}
