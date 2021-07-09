package com.example.womanssafety;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity implements LocationListener{

    Button sos;
    int clickcount=0;
    LocationManager manager;
    String lati, longi;
    String link;
    SmsManager smsManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sos=findViewById(R.id.sos);

        smsManager=SmsManager.getDefault();

        manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION)!=PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_COARSE_LOCATION)!=PackageManager.PERMISSION_GRANTED){
            return;
        }
        manager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0,this::onLocationChanged);

        sos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickcount=clickcount+1;
                if(clickcount==3) {
                    String number = "9699421928";
                    link="https://www.google.com/maps/dir///@"+lati+","+longi+",14z";
                    String message="I am in trouble please help me \n"+link;
                    smsManager.sendTextMessage(number,"",message,null,null);
                    //first time clicked to do this
                    Toast.makeText(getApplicationContext(),"Message send", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        lati = ""+location.getLatitude();
        longi = ""+location.getLongitude();
    }
}

