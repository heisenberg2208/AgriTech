package com.example.pankaj.farmguide;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class CropSelectorActivity extends AppCompatActivity {

    List<Crop> list;
    RecyclerView rvCropList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop_selector);


        Intent i = getIntent();
        String address = i.getStringExtra("address");
        String soiltype = i.getStringExtra("soiltype");
        String budget = i.getStringExtra("budget");
        String landsize = i.getStringExtra("landsize");
        String sprinkler = i.getStringExtra("sprinkler");
        String drip = i.getStringExtra("drip");
        String ph = i.getStringExtra("ph");

        Toast.makeText(this, address + " " +soiltype + " " +budget + " " +landsize + " " +sprinkler + " " +drip + " ", Toast.LENGTH_SHORT).show();



        rvCropList = (RecyclerView)findViewById(R.id.rvCropList);
        rvCropList.setHasFixedSize(true);
        rvCropList.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<>();


        list.add(
                new Crop(
                        1,
                        "Wheat",
                        "Required approximate ph of 6.0",
                        R.drawable.wheat,
                        1,6.0));
        list.add(
                new Crop(
                        1,
                        "Corn",
                        "Required approximate ph of 5.5",
                        R.drawable.corn,
                        2,5.5));

        list.add(
                new Crop(
                        1,
                        "Cotton",
                        "Required approximate ph of 6.0",
                        R.drawable.cotton,
                        2,6.0));

        CropAdapter adapter = new CropAdapter(this, list);

        rvCropList.setAdapter(adapter);

    }
}
