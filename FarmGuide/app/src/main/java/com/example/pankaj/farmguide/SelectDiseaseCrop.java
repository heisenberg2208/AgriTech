package com.example.pankaj.farmguide;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;

import com.google.firebase.auth.FirebaseAuth;

public class SelectDiseaseCrop extends AppCompatActivity {


    GridView gridView;
    String nameList[] = {"Tomato", "Apple", "Corn", "Grapes", "Potato", "Cherry", "Bellpepper", "Peach", "Strawberry",
            "Orange", "Pumpkin", "Raspberry", "Soyabean", "Blueberry", "Other"};
    int lettersIcon[] = {R.drawable.tomato, R.drawable.apple, R.drawable.corn, R.drawable.grapes, R.drawable.potato,
            R.drawable.cherry, R.drawable.bellpepper, R.drawable.peach, R.drawable.strawberry,
            R.drawable.orange, R.drawable.pumpkin, R.drawable.raspberry, R.drawable.soyabean, R.drawable.blueberry, R.drawable.other};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_disease_crop);

        SharedPreferences sharedPreferences = getSharedPreferences("user_info",MODE_PRIVATE);
        String latitude = sharedPreferences.getString("latitude","");
        String longitude = sharedPreferences.getString("longitude","");
        String userid = sharedPreferences.getString("userid","");

        Log.d("SelectDiseaseLOG",latitude);
        Log.d("SelectDiseaseLOG",longitude);
        Log.d("SelectDiseaseLOG", userid);


        gridView = (GridView)findViewById(R.id.gridView);

        GridAdapter adapter = new GridAdapter(SelectDiseaseCrop.this, lettersIcon, nameList);

        gridView.setAdapter(adapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i  = new Intent(SelectDiseaseCrop.this, PhotoPicker.class);
                i.putExtra("crop",nameList[position]);
                startActivity(i);
            }
        });





    }
}
