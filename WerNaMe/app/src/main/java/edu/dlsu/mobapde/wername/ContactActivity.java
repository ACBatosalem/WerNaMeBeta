package edu.dlsu.mobapde.wername;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import java.util.ArrayList;

public class ContactActivity extends AppCompatActivity {
    private static final int RESULT_PICK_CONTACT = 0;
    RecyclerView rvContact;
    LinearLayout addLayout;
    DatabaseHelper databaseHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        databaseHelper = new DatabaseHelper(getBaseContext());

        addLayout = (LinearLayout) findViewById(R.id.layout_add);

        rvContact = (RecyclerView) findViewById(R.id.rv_contacts);

        ContactAdapter cs = new ContactAdapter(getBaseContext(),
                databaseHelper.getAllContacts());
        rvContact.setAdapter(cs);
        rvContact.setLayoutManager(new LinearLayoutManager(
                getBaseContext(),LinearLayoutManager.VERTICAL,false));

        addLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent contactPickerIntent = new Intent(Intent.ACTION_PICK,
                        ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
                startActivityForResult(contactPickerIntent, RESULT_PICK_CONTACT);
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
