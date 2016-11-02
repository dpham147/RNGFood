package edu.orangecoastcollege.cs273.dpham147.rngfood;

/**
 * Created by Kyubey on 2016-11-01.
 */

public class Location {
    private int id;
    private String location;

    public Location() {
        id = -1;
        location = "";
    }

    public Location(String loc) {
        location = loc;
    }

    public Location(int newId, String loc) {
        id = newId;
        location = loc;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String newLoc) {
        location = newLoc;
    }
}
