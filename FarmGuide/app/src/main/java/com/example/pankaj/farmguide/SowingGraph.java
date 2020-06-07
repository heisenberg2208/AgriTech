package com.example.pankaj.farmguide;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class SowingGraph extends AppCompatActivity {
    ImageButton imgbtnSowingGraph;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sowing_graph);

        imgbtnSowingGraph = (ImageButton) findViewById(R.id.imgbtnSowingGraph);
        imgbtnSowingGraph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Boolean a = openApp(getApplicationContext(), "ui.test.myapplication");
            }
        });
    }
    public Boolean openApp(Context context, String s) {
        PackageManager manager = context.getPackageManager();
        try {
            Intent i = manager.getLaunchIntentForPackage(s);
            if (i == null) {
                return false;
            }
            i.addCategory(Intent.CATEGORY_LAUNCHER);

            context.startActivity(i);
            return true;
        } catch (ActivityNotFoundException e) {
            return false;
        }
    }
}
