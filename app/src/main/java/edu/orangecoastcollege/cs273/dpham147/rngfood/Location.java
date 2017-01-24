package edu.orangecoastcollege.cs273.dpham147.rngfood;

import android.net.Uri;

/**
 * Created by Kyubey on 2016-11-01.
 */

public class Location {
    private int id;
    private String location;
    private Uri image;

    // TODO:  add URI to DB, make the image chageable

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
        image = Uri.EMPTY;
    }

    public Location(int newId, String loc, Uri uri) {
        id = newId;
        location = loc;
        image = uri;
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

    public Uri getUri() {
        return image;
    }

    public void setUri(Uri uri) {
        image = uri;
    }
}
