package edu.dlsu.mobapde.wername;

/**
 * Created by Angel on 31/10/2017.
 */

public class Journey {
    private String source;
    private String destination;
    private String plate_number;

    public Journey(String source, String destination, String plate_number) {
        this.source = source;
        this.destination = destination;
        this.plate_number = plate_number;
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
}
