package com.example.pankaj.farmguide;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;

public class OpenShopActivity extends AppCompatActivity {

    EditText etPlace;
    Spinner spnOptions;
    Button btnSearch;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_shop);

        etPlace = (EditText)findViewById(R.id.etPlace);
        spnOptions = (Spinner)findViewById(R.id.spnOptions);
        btnSearch = (Button)findViewById(R.id.btnSearch);

        final ArrayList<String> opt = new ArrayList<>();
        opt.add("Rcf Stores");
        //opt.add("Restaurants");
        //opt.add("Petrol Station");
        // opt.add("Chemist");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,opt);
        spnOptions.setAdapter(adapter);

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String place = etPlace.getText().toString();
                if(place.length()==0)
                {
                    etPlace.setError("place is empty");
                    etPlace.requestFocus();
                    return;
                }
                String ser = opt.get(spnOptions.getSelectedItemPosition());
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse("geo:0,0?q="+place+","+ser));
                startActivity(i);

            }
        });
    }
}
