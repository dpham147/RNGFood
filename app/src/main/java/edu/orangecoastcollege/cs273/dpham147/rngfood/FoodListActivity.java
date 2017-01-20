package edu.orangecoastcollege.cs273.dpham147.rngfood;

import android.content.Context;
import android.content.DialogInterface;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.GestureDetector;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;
import java.util.Random;

public class FoodListActivity extends AppCompatActivity {

    private DBHelper database;
    private List<Location> locationList;
    private LocationListAdapter locationListAdapter;
    private SensorManager sensorManager;
    private Sensor accelerometer;
    private ShakeDetector shakeDetector;
    private Context context;

    private EditText locationEditText;
    private ListView locationListView;
    private Button addLocationButton;

    private GestureDetector gestureDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_list);

        database = new DBHelper(this);

        // database.deleteAllLocations();

        locationList = database.getAllLocations();

        locationListAdapter = new LocationListAdapter(this, R.layout.loc_list_item, locationList);

        locationListView = (ListView) findViewById(R.id.locationListView);

        locationListView.setAdapter(locationListAdapter);

        locationEditText = (EditText) findViewById(R.id.locationEditText);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        context = this;
        shakeDetector = new ShakeDetector(new ShakeDetector.OnShakeListener() {
            @Override
            public void onShake() {
                selectRandomLocation();
            }
        });

    }

    public void addLocation(View view) {
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

    protected void selectRandomLocation() {
        Random random = new Random();
        int n = random.nextInt(locationList.size());

        Location selectedLocation = locationList.get(n);
        new AlertDialog.Builder(this).setTitle("Go eat at").setMessage(selectedLocation.getLocation()).setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).show();

    }

    public void clear(View view){
        if (view instanceof LinearLayout)
        {
            Location selectedLocation = (Location) view.getTag();
            locationListAdapter.remove(selectedLocation);
            //database.deleteLocation(selectedLocation);
            locationListAdapter.notifyDataSetChanged();


        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(shakeDetector, accelerometer, SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(shakeDetector);
    }
}

