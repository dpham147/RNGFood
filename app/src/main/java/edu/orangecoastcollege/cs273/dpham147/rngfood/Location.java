package edu.orangecoastcollege.cs273.dpham147.rngfood;

import android.net.Uri;

/**
 * Created by Kyubey on 2016-11-01.
 */

public class Location {
    private int id;
    private String location;
    private Uri image;

    // TODO: Getter and setter for image URI, add URI to DB, make the image chageable

    public Location() {
        id = -1;
        location = "";
    }

    public Location(String loc) {
        this(-1, loc);
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
