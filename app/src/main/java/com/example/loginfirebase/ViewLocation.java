package com.example.loginfirebase;

import android.Manifest;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import static android.content.pm.PackageManager.PERMISSION_GRANTED;

public class ViewLocation extends AppCompatActivity {

    private Button getLocation, stopGPS;
    private static final int REQUEST_CODE_PERMISSION = 2;
    String mPermission = Manifest.permission.ACCESS_FINE_LOCATION;

    GPSTracker gps;

    //Thread
    Handler h = new Handler();
    Thread task;
    private long startTime;
    private int counter = 1;
    private String timeString;
    private TextView timeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_location);
        try{
            if(ActivityCompat.checkSelfPermission(this, mPermission) != PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(this, new String[]{mPermission},REQUEST_CODE_PERMISSION);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }

        getLocation = (Button) findViewById(R.id.getLocation);
        stopGPS = (Button) findViewById(R.id.stopGPS);
        timeView = (TextView) findViewById(R.id.timer);

        getLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTimer();
                counter = 1;

                gps = new GPSTracker(ViewLocation.this);

                if(gps.canGetLocation()){
                    double latitude = gps.getLatitude();
                    double longitude = gps.getLongitude();

                    Toast.makeText(getApplicationContext(),"Current location is \n Latitude: " + latitude +
                            "\nLongitude: " + longitude, Toast.LENGTH_LONG).show();

                    Log.d("pass","p");
                }
                else{
                    gps.showSettingsAlert();
                    Log.d("failed","f");
                }
            }
        });

        stopGPS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopHandlerTask();
                timeView.setText("Location Service is Stopped");
            }
        });

    }

    private void stopHandlerTask() {
        h.removeCallbacks(task);
    }

    public void startTimer(){
        startTime = System.currentTimeMillis();
        task = new Thread(){
            @Override
            public void run(){
                long millis = System.currentTimeMillis()-startTime;
                long secs = millis/1000 % 60; // seconds, 0-59

                timeString = String.format("%02d", secs);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        timeView.setText(timeString);
                    }
                });
                h.postDelayed(task,1000);
            }
        };
        h.postDelayed(task,1000);
    }
}
