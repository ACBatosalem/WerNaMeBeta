package edu.dlsu.mobapde.wername;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class ContactActivity extends AppCompatActivity {
    private static final int RESULT_PICK_CONTACT = 0;
    RecyclerView rvContact;
    Button addLayout;
    DatabaseHelper databaseHelper;
    TextView createText, contactText, historyText, noContents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        databaseHelper = new DatabaseHelper(getBaseContext());

        addLayout = (Button) findViewById(R.id.layout_add);

        rvContact = (RecyclerView) findViewById(R.id.rv_contacts);

        createText = (TextView) findViewById(R.id.create);
        contactText = (TextView) findViewById(R.id.contacts);
        historyText = (TextView) findViewById(R.id.history);
        noContents = (TextView) findViewById(R.id.noContents);

      /*  String udata="Contacts";
        SpannableString content = new SpannableString(udata);
        content.setSpan(new UnderlineSpan(), 0, udata.length(), 0);//where first 0 shows the starting and udata.length() shows the ending span.if you want to span only part of it than you can change these values like 5,8 then it will underline part of it.
        contactText.setText(content);*/

        ContactAdapter cs = new ContactAdapter(getBaseContext(),
                databaseHelper.getAllContacts());
        rvContact.setAdapter(cs);
        rvContact.setLayoutManager(new LinearLayoutManager(
                getBaseContext(),LinearLayoutManager.VERTICAL,false));

        Cursor cursor = databaseHelper.getAllContacts();
        if(cursor.getCount() <= 0) {
            noContents.setVisibility(View.VISIBLE);
            noContents.setText("No contacts yet.");
            rvContact.setVisibility(View.GONE);
        } else {
            noContents.setVisibility(View.GONE);
            rvContact.setVisibility(View.VISIBLE);
        }

        addLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent contactPickerIntent = new Intent(Intent.ACTION_PICK,
                        ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
                startActivityForResult(contactPickerIntent, RESULT_PICK_CONTACT);
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

        historyText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getBaseContext(), HistoryActivity.class);
                startActivity(i);
                finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // check whether the result is ok
        if(resultCode == RESULT_OK) {
            // Check for the request code, we might be usign multiple startActivityForReslut
            switch (requestCode) {
                case RESULT_PICK_CONTACT:
                    Cursor cursor = null;
                    String phoneNo, contactName;
                    try {
                        Uri uri = data.getData();
                        cursor = getContentResolver().query(uri, null, null, null, null);
                        cursor.moveToFirst();
                        int  phoneNoIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                        phoneNo = cursor.getString(phoneNoIndex);

                        int nameColumnIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
                        contactName = cursor.getString(nameColumnIndex);
                        databaseHelper.addContact(new Contact(contactName,phoneNo));
                        cursor.close();

                        Intent i = new Intent(getBaseContext(), ContactActivity.class);
                        startActivity(i);
                        finish();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
            }
        } else {
            Log.e("MainActivity", "Failed to pick contact");
        }
    }
}
