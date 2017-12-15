package edu.dlsu.mobapde.wername;

/**
 * Created by Angel on 31/10/2017.
 */

import java.util.Calendar;
import java.util.Date;

public class Journey {

    public static final String TABLE_NAME = "journey";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_SOURCE = "srcLoc";
    public static final String COLUMN_DESTINATION = "destLoc";
    public static final String COLUMN_PLATENUMBER = "plateNum";
    public static final String COLUMN_STARTTIME = "startTime";
    public static final String COLUMN_ESTIMATEDTA = "estTA";
    public static final String COLUMN_ACTUALTA = "actTA";
    public static final String COLUMN_TEXTSENTTO = "textSentTo";
    public static final String COLUMN_MESSAGE = "message";

    private long id;
    private String source;
    private String destination;
    private String plate_number;
    private long startTime;
    private long estimatedTA;
    private long actualTA;
    private String textSentTo;
    private String message;



    public Journey() {}

    public Journey(String source, String destination, String plate_number, long startTime, long estimatedTA, String message, String textSentTo) {
        this.source = source;
        this.destination = destination;
        this.plate_number = plate_number;
        this.startTime = startTime;
        this.estimatedTA = estimatedTA;
        this.actualTA = estimatedTA;
        this.textSentTo = textSentTo;
        this.message = message;
    }

    public Journey(String source, String destination, String plate_number, long startTime, long estimatedTA, long actualTA, String textSentTo, String message) {
        this.source = source;
        this.destination = destination;
        this.plate_number = plate_number;
        this.startTime = startTime;
        this.estimatedTA = estimatedTA;
        this.actualTA = actualTA;
        this.textSentTo = textSentTo;
        this.message = message;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getPlate_number() {
        return plate_number;
    }

    public void setPlate_number(String plate_number) {
        this.plate_number = plate_number;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getEstimatedTA() {
        return estimatedTA;
    }

    public void setEstimatedTA(long estimatedTA) {
        this.estimatedTA = estimatedTA;
    }

    public long getActualTA() {
        return actualTA;
    }

    public void setActualTA(long actualTA) {
        this.actualTA = actualTA;
    }

    public String getTextSentTo() {
        return textSentTo;
    }

    public void setTextSentTo(String textSentTo) {
        this.textSentTo = textSentTo;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
