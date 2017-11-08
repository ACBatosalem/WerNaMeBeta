package edu.dlsu.mobapde.wername;

/**
 * Created by Angel on 01/11/2017.
 */

public class Contact {
    public static final String TABLE_NAME = "contact";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_NUMBER = "number";

    private long id;
    private String name;
    private String number;


    public Contact() {}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
