package edu.orangecoastcollege.cs273.dpham147.rngfood;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.GestureDetector;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class FoodListActivity extends AppCompatActivity {

    private static final int REQUEST_CODE = 1000;
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

    public void changeImage(View view)
    {
        ArrayList<String> permList = new ArrayList<>();

        int cameraPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        if (cameraPermission != PackageManager.PERMISSION_GRANTED)
            permList.add(Manifest.permission.CAMERA);

        int readPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        if (readPermission != PackageManager.PERMISSION_GRANTED)
            permList.add(Manifest.permission.READ_EXTERNAL_STORAGE);

        int writePermission = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (writePermission != PackageManager.PERMISSION_GRANTED)
            permList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permList.size() > 0)
        {
            String[] perms = new String[permList.size()];
            ActivityCompat.requestPermissions(this, permList.toArray(perms), REQUEST_CODE);
        }

        if (cameraPermission == PackageManager.PERMISSION_GRANTED
                && readPermission == PackageManager.PERMISSION_GRANTED
                && writePermission == PackageManager.PERMISSION_GRANTED)
        {
            Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(galleryIntent, REQUEST_CODE);
        }
        else
            Toast.makeText(this, "Gimme permissions faggot.", Toast.LENGTH_LONG).show();


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data != null && requestCode == REQUEST_CODE && resultCode == RESULT_OK) {

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

