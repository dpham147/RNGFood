package edu.orangecoastcollege.cs273.dpham147.rngfood;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;
import java.util.Random;

public class FoodListActivity extends AppCompatActivity implements GestureDetector.OnGestureListener {

    private DBHelper database;
    private List<Location> locationList;
    private LocationListAdapter locationListAdapter;

    private EditText locationEditText;
    private ListView locationListView;
    private Button addLocationButton;

    private GestureDetector gestureDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_list);

        database = new DBHelper(this);
        locationList = database.getAllLocations();

        locationListAdapter = new LocationListAdapter(this, R.layout.loc_list_item, locationList);

        locationListView = (ListView) findViewById(R.id.locationListView);

        locationListView.setAdapter(locationListAdapter);

        locationEditText = (EditText) findViewById(R.id.locationEditText);

        addLocationButton = (Button) findViewById(R.id.addLocationButton);

        gestureDetector = new GestureDetector(this, this);
    }

    protected void addLocation(View view) {
        String location = locationEditText.getText().toString();
        if (location.isEmpty()) {
            Toast.makeText(this, "Do you even eat bro?", Toast.LENGTH_SHORT).show();
        }
        else {
            Location newLoc = new Location(location);
            locationListAdapter.add(newLoc);
            database.addLoc(newLoc);
            locationEditText.setText("");
        }
    }

    protected void selectRandomLocation(View view) {
        Random random = new Random();
        int n = random.nextInt(locationList.size());

        Location selectedLocation = locationList.get(n);

        Intent toDetails = new Intent(this, LocationDetailsActivity.class);
        toDetails.putExtra("Location", selectedLocation.getLocation());
        startActivity(toDetails);
    }

    @Override
    public boolean dispatchTouchEvent (MotionEvent ev) {
        super.dispatchTouchEvent(ev);
        return gestureDetector.onTouchEvent(ev);
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {
        if (addLocationButton.isEnabled()) {
            addLocationButton.setEnabled(false);
        }
        else
            addLocationButton.setEnabled(true);
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        return false;
    }
}
