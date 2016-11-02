package edu.orangecoastcollege.cs273.dpham147.rngfood;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class LocationDetailsActivity extends AppCompatActivity {

    private TextView locationDetailsTextView;
    private ImageView locationDetailsImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_details);

        Intent fromMain = getIntent();

        locationDetailsTextView = (TextView) findViewById(R.id.locationDetailsTextView);
        locationDetailsImageView = (ImageView) findViewById(R.id.locationDetailsImageView);

        locationDetailsTextView.setText(fromMain.getStringExtra("Location"));

    }

    protected void toMain (View view) {
        Intent intent = new Intent(this, FoodListActivity.class);
        startActivity(intent);
    }

    protected void deleteLocation (View view) {

    }
}
