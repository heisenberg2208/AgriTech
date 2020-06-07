package com.example.pankaj.farmguide;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;
import android.app.Dialog;

import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class LoginActivity extends AppCompatActivity {
    public static final int RC_SIGN_IN = 1;
    private static final int MY_LOCATION_REQUEST_CODE = 2;
    FirebaseAuth mFirebaseAuth;
    Button buy, sell;
    ImageButton diseasedetector, buyingselling;
    private FirebaseAuth.AuthStateListener mAuthStateListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Getting Language
        String iso = Locale.getDefault().getLanguage();
        final SharedPreferences sharedPreferences = getSharedPreferences("Mypref", MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("lang", iso);
        editor.commit();
        //Toast.makeText(this, iso, Toast.LENGTH_SHORT).show();

        //Location


        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            //Toast.makeText(this, "I don't have camera permission", Toast.LENGTH_SHORT).show();
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_LOCATION_REQUEST_CODE);
        } else {

            LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 100, new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    //txtLat = (TextView) findViewById(R.id.textview1);
                    Log.d("Latitude", "In Location Chnaged method");
                    Log.d("Latitude", "Lat-Lon:" + location.getLatitude() + " " + location.getLongitude());

                    SharedPreferences.Editor editor1 = getSharedPreferences("user_info",MODE_PRIVATE).edit();
                    editor1.putString("latitude", String.valueOf(location.getLatitude()));
                    editor1.putString("longitude", String.valueOf(location.getLongitude()));
                    editor1.putString("userid",FirebaseAuth.getInstance().getCurrentUser().getUid());
                    editor1.commit();
                }

                @Override
                public void onProviderDisabled(String provider) {
                    //Toast.makeText(LoginActivity.this, "Please enable Location Service", Toast.LENGTH_SHORT).show();
                    Log.d("Latitude", "disable");
                }

                @Override
                public void onProviderEnabled(String provider) {
                    Log.d("Latitude", "enable");
                }

                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {
                    Log.d("Latitude", "status");
                }
            });

            Log.d("Latitude", "Location Service already granted");
        }


        //Location end
        // Choose authentication providers
        FirebaseApp.initializeApp(this);


        if (FirebaseAuth.getInstance().getCurrentUser() != null) {

            Intent intent = new Intent(LoginActivity.this,Dashboard.class);
            startActivity(intent);
            finish();

            //If there is some user signed in
            //Toast.makeText(this, "Logged in", Toast.LENGTH_SHORT).show();
            diseasedetector = (ImageButton) findViewById(R.id.diseasedetector);
            buyingselling = (ImageButton) findViewById(R.id.buyingselling);



            diseasedetector.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //Toast.makeText(LoginActivity.this, "Already signed in...", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(LoginActivity.this, SelectDiseaseCrop.class);
                    startActivity(i);
                }
            });


            buyingselling.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    MyCustomAlertDialog();
                }
            });

            //  Intent intent = new Intent(LoginActivity.this, AddProduct.class);
            //startActivity(intent);
            //finish();


        } else {
// Create and launch sign-in intent
            //Toast.makeText(this, "Not Logged in", Toast.LENGTH_SHORT).show();
            List<AuthUI.IdpConfig> providers = Arrays.asList(

                    new AuthUI.IdpConfig.PhoneBuilder().build(),
                    new AuthUI.IdpConfig.GoogleBuilder().build()
            );

            startActivityForResult(
                    AuthUI.getInstance()
                            .createSignInIntentBuilder()
                            .setAvailableProviders(providers)
                            .build(),
                    RC_SIGN_IN);

        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            IdpResponse response = IdpResponse.fromResultIntent(data);

            if (resultCode == RESULT_OK) {
                // Successfully signed in
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                // ...

                //chaudhde
                //Intent intent = new Intent(LoginActivity.this, ProfileActivity.class);
                Intent intent = new Intent(LoginActivity.this, Dashboard.class);
                startActivity(intent);
                finish();
            } else {
                // Sign in failed. If response is null the user canceled the
                // sign-in flow using the back button. Otherwise check
                // response.getError().getErrorCode() and handle the error.
                // ...
            }
        }
    }


    // function to create popup window


    public void MyCustomAlertDialog() {
        final Dialog MyDialog = new Dialog(LoginActivity.this);
        MyDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        MyDialog.setContentView(R.layout.customdialog);

        Button buy = (Button) MyDialog.findViewById(R.id.buy);
        Button sell = (Button) MyDialog.findViewById(R.id.sell);

        buy.setEnabled(true);
        sell.setEnabled(true);

        buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, BuyerPortal.class);
                startActivity(intent);

            }
        });
        sell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, AddProduct.class);
                startActivity(intent);

            }
        });
        MyDialog.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {

        if (requestCode == MY_LOCATION_REQUEST_CODE) {


            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                //Toast.makeText(this, "Location permission granted", Toast.LENGTH_LONG).show();

            } else {

                //Toast.makeText(this, "Location permission denied", Toast.LENGTH_LONG).show();

            }
            return;
        }
    }


}

