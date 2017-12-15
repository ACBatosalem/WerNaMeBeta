package edu.dlsu.mobapde.wername;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Troy Mirafuentes on 11/9/2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String SCHEMA = "wername";
    public static final int VERSION = 3;
    public DatabaseHelper(Context context) {
        super(context, SCHEMA, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql = "CREATE TABLE " + Journey.TABLE_NAME + " ("
                + Journey.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + Journey.COLUMN_SOURCE + " TEXT, "
                + Journey.COLUMN_DESTINATION + " TEXT, "
                + Journey.COLUMN_PLATENUMBER + " TEXT, "
                + Journey.COLUMN_STARTTIME + " INTEGER, "
                + Journey.COLUMN_ESTIMATEDTA + " INTEGER, "
                + Journey.COLUMN_ACTUALTA + " INTEGER, "
                + Journey.COLUMN_TEXTSENTTO + " TEXT, "
                + Journey.COLUMN_MESSAGE + " TEXT "
                +");";
        Log.d("Query", sql);
        sqLiteDatabase.execSQL(sql);

        sql = "CREATE TABLE " + Contact.TABLE_NAME + " ("
                + Contact.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
                + Contact.COLUMN_NAME + " TEXT, "
                + Contact.COLUMN_NUMBER + " TEXT "
                + ");";
        sqLiteDatabase.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        // will only be called if there is change in version number

        // you want to migrate to the new db
        // drop the current tables
        // call onCreate to get the latest db design
        String sql = "DROP TABLE IF EXISTS " + Journey.TABLE_NAME + ";"
                    + "DROP TABLE IF EXISTS " + Contact.TABLE_NAME + ";";
        sqLiteDatabase.execSQL(sql);
        onCreate(sqLiteDatabase);
    }

    // Add Journey
    public long addJourney(Journey journey) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(Journey.COLUMN_SOURCE, journey.getSource());
        contentValues.put(Journey.COLUMN_DESTINATION, journey.getDestination());
        contentValues.put(Journey.COLUMN_PLATENUMBER, journey.getPlate_number());
        contentValues.put(Journey.COLUMN_STARTTIME, journey.getStartTime());
        contentValues.put(Journey.COLUMN_ESTIMATEDTA, journey.getEstimatedTA());
        contentValues.put(Journey.COLUMN_ACTUALTA, journey.getActualTA());
        contentValues.put(Journey.COLUMN_TEXTSENTTO, journey.getTextSentTo());
        contentValues.put(Journey.COLUMN_MESSAGE, journey.getMessage());

        long id = db.insert(Journey.TABLE_NAME,
                null,
                contentValues);
        db.close();
        return id;
    }

    // Edit Journey
    public boolean editJourney(long currentId, Journey journey) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(Journey.COLUMN_SOURCE, journey.getSource());
        contentValues.put(Journey.COLUMN_DESTINATION, journey.getDestination());

        contentValues.put(Journey.COLUMN_PLATENUMBER, journey.getPlate_number());
        contentValues.put(Journey.COLUMN_STARTTIME, journey.getStartTime());
        contentValues.put(Journey.COLUMN_ESTIMATEDTA, journey.getEstimatedTA());
        contentValues.put(Journey.COLUMN_ACTUALTA, journey.getActualTA());
        contentValues.put(Journey.COLUMN_TEXTSENTTO, journey.getTextSentTo());
        contentValues.put(Journey.COLUMN_MESSAGE, journey.getMessage());

        int rows = db.update(Journey.TABLE_NAME,
                contentValues,
                Journey.COLUMN_ID + "=?",
                new String[]{currentId+""});
        db.close();
        return (rows > 0);
    }

    // Remove Journey
    public boolean removeJourney(long id) {
        SQLiteDatabase db = getWritableDatabase();
        int rows = db.delete(Journey.TABLE_NAME,
                Journey.COLUMN_ID + " =?",
                new String[]{id+""});
        db.close();
        return (rows > 0);
    }

    // Retrieve Journey
    public Journey getJourney(long id) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.query(Journey.TABLE_NAME,
                null, // SELECT *
                Journey.COLUMN_ID + " =? ", // where clause
                new String[]{id+""}, // where args
                null, // groupby
                null, // having
                null); // orderby

        Journey j = null;
        if (c.moveToFirst()) {
            j = new Journey();
            String source = c.getString(c.getColumnIndex(Journey.COLUMN_SOURCE));
            String destination = c.getString(c.getColumnIndex(Journey.COLUMN_DESTINATION));
            String plateNum = c.getString(c.getColumnIndex(Journey.COLUMN_PLATENUMBER));
            long startTime = c.getLong(c.getColumnIndex(Journey.COLUMN_STARTTIME));
            long eta = c.getLong(c.getColumnIndex(Journey.COLUMN_ESTIMATEDTA));
            long ata = c.getLong(c.getColumnIndex(Journey.COLUMN_ACTUALTA));
            String textSent = c.getString(c.getColumnIndex(Journey.COLUMN_TEXTSENTTO));
            String msg = c.getString(c.getColumnIndex(Journey.COLUMN_MESSAGE));

            j.setSource(source);
            j.setDestination(destination);
            j.setPlate_number(plateNum);
            j.setStartTime(startTime);
            j.setEstimatedTA(eta);
            j.setActualTA(ata);
            j.setTextSentTo(textSent);
            j.setMessage(msg);
            j.setId(id);
        }

        c.close();
        db.close();

        return j;
    }

    // Retrieve all Journeys
    public ArrayList<Journey> getAllJourneys() {
        SQLiteDatabase db = getReadableDatabase();

        return null;
    }

    // Add Contact
    public boolean addContact(Contact contact) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(Contact.COLUMN_NAME, contact.getName());
        contentValues.put(Contact.COLUMN_NUMBER, contact.getNumber());

        long id = db.insert(Contact.TABLE_NAME,
                null,
                contentValues);
        db.close();
        return (id != -1);
    }

    // Edit Contact
    public boolean editContact(long currentId, Contact newContact) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(Contact.COLUMN_NAME, newContact.getName());
        contentValues.put(Contact.COLUMN_NUMBER, newContact.getNumber());

        int rows = db.update(Contact.TABLE_NAME,
                contentValues,
                Contact.COLUMN_ID + "=?",       // not sure how this would work tbf
                new String[] {currentId+""});
        db.close();
        return (rows > 0);
    }

    // Remove Contact
    public boolean removeContact(int id) {
        SQLiteDatabase db = getWritableDatabase();
        int rows = db.delete(Journey.TABLE_NAME,
                Contact.COLUMN_ID + " =?",
                new String[]{id+""});
        db.close();
        return (rows > 0);
    }

    // Retrieve Journey
    public Contact getContact(String nameContact) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.query(Contact.TABLE_NAME,
                null, // SELECT *
                Contact.COLUMN_NAME + " =? ", // where clause
                new String[]{nameContact}, // where args
                null, // groupby
                null, // having
                null); // orderby

        Contact p = null;
        if (c.moveToFirst()) {
            p = new Contact();
            String name = c.getString(c.getColumnIndex(Contact.COLUMN_NAME));
            String number = c.getString(c.getColumnIndex(Contact.COLUMN_NUMBER));

            p.setName(name);
            p.setNumber(number);
        }

        c.close();
        db.close();

        return p;
    }

    // Retrieve all Contacts
    public Cursor getAllContacts() {

        SQLiteDatabase db = getReadableDatabase();
        return db.query(Contact.TABLE_NAME,null,null,null,null,null,null);
    }

    // Retrieve All Journeys
    public Cursor getAllJourneysCursor() {
        SQLiteDatabase db = getReadableDatabase();

        return db.query(Journey.TABLE_NAME, null, null, null, null, null, Journey.COLUMN_ACTUALTA + " DESC");
    }
}
