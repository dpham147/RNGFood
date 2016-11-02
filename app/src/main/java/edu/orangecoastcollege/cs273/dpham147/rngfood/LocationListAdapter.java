package edu.orangecoastcollege.cs273.dpham147.rngfood;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kyubey on 2016-11-01.
 */

public class LocationListAdapter extends ArrayAdapter<Location> {
    private Context mContext;
    private List<Location> mLocationList = new ArrayList<>();
    private int mResourceId;

    private TextView locationListTextView;

    public LocationListAdapter(Context c, int rId, List<Location> locations) {
        super(c, rId, locations);
        mContext = c;
        mResourceId = rId;
        mLocationList = locations;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Location selectedLocation = mLocationList.get(position);
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(mResourceId, null);

        locationListTextView = (TextView) view.findViewById(R.id.locationListTextView);

        locationListTextView.setText(selectedLocation.getLocation());

        return view;
    }
}
