package edu.dlsu.mobapde.wername;

/**
 * Created by Angel on 31/10/2017.
 */

public class Journey {

    public static final String TABLE_NAME = "journey";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_SOURCE = "srcLoc";
    public static final String COLUMN_DESTINATION = "destLoc";
    public static final String COLUMN_PLATENUMBER = "plateNum";
    public static final String COLUMN_ESTIMATEDTA = "estTA";
    public static final String COLUMN_ACTUALTA = "actTA";
    public static final String COLUMN_TEXTSENT = "textSent";

    private long id;
    private String source;
    private String destination;
    private String plate_number;
    private String estimatedTA;
    private String actualTA;
    private String textSent;

    public Journey() {}

    public Journey(String source, String destination, String plate_number) {
        this.source = source;
        this.destination = destination;
        this.plate_number = plate_number;
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

    public String getEstimatedTA() {
        return estimatedTA;
    }

    public void setEstimatedTA(String estimatedTA) {
        this.estimatedTA = estimatedTA;
    }

    public String getActualTA() {
        return actualTA;
    }

    public void setActualTA(String actualTA) {
        this.actualTA = actualTA;
    }

    public String getTextSent() {
        return textSent;
    }

    public void setTextSent(String textSent) {
        this.textSent = textSent;
    }
}
