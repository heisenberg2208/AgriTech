package com.example.pankaj.farmguide;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class ProfileActivity extends AppCompatActivity {
    EditText UserLocation,UserName;
    FirebaseFirestore mFirestore;
    Button AddUser;
    String loc;
    private static final int MY_LOCATION_REQUEST_CODE = 2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        UserLocation = findViewById(R.id.UserLocation);
        UserName = findViewById(R.id.UserName);
        AddUser = findViewById(R.id.AddUser);
        mFirestore = FirebaseFirestore.getInstance();

        if (ContextCompat.checkSelfPermission(ProfileActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            //Toast.makeText(ProfileActivity.this, "I don't have camera permission", Toast.LENGTH_SHORT).show();
            ActivityCompat.requestPermissions(ProfileActivity.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_LOCATION_REQUEST_CODE);
        }
        else
        {

            LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 100, new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    //txtLat = (TextView) findViewById(R.id.textview1);
                    Log.d("Latitude" , "In Location Chnaged method");
                    Log.d("Latitude","Lat-Lon:"+location.getLatitude() + " " + location.getLongitude());
                    loc = ""+location.getLatitude()+","+location.getLongitude();
                }

                @Override
                public void onProviderDisabled(String provider) {
                    Toast.makeText(ProfileActivity.this, "Please enable Location Service", Toast.LENGTH_SHORT).show();
                    Log.d("Latitude","disable");
                }

                @Override
                public void onProviderEnabled(String provider) {
                    Log.d("Latitude","enable");
                }

                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {
                    Log.d("Latitude","status");
                }
            });

            Log.d("Latitude","Location Service already granted");
        }




        AddUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name,location;




                //Location end



                name = UserName.getText().toString();
                location = UserLocation.getText().toString();

                Map<Object, Object> data = new HashMap<>();
                Toast.makeText(ProfileActivity.this, loc, Toast.LENGTH_SHORT).show();
                data.put("Name", name);
                data.put("Location", loc);
                data.put("Rating", 0);
                mFirestore.collection("Seller").document(FirebaseAuth.getInstance().getCurrentUser().getUid().toString())
                        .set(data)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d("profile", "DocumentSnapshot successfully written!");

                                Intent intent = new Intent(ProfileActivity.this,AddProduct.class);
                                startActivity(intent);
                                finish();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w("profile", "Error writing document", e);
                            }
                        });

            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {

        if (requestCode == MY_LOCATION_REQUEST_CODE){


            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                //Toast.makeText(this, "Location permission granted", Toast.LENGTH_LONG).show();

            } else {

                //Toast.makeText(this, "Location permission denied", Toast.LENGTH_LONG).show();

            }
            return;
        }
    }



}
