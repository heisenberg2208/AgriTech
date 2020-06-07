package com.example.pankaj.farmguide;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.StaticLabelsFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;

public class DemandSupplyActivity extends AppCompatActivity {

    private static final String TAG = "MyDemandSupply";
    Button btnSeeMap;
    TextView tvDemandSupplyResult;
    GraphView graphViewDemandSupply;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference collectionReference = db.collection("DemandSupply");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demand_supply);

        btnSeeMap = (Button) findViewById(R.id.btnSeeMap);
        //btnReceive = (Button) findViewById(R.id.btnReceive);
        tvDemandSupplyResult = (TextView) findViewById(R.id.tvDemandSupplyResult);
        graphViewDemandSupply = (GraphView) findViewById(R.id.graphViewDemandSupply);


//        ArrayList<DemandSupply> demandSupplyArray = new ArrayList<>();
//        demandSupplyArray.add(new DemandSupply("Corn", "Punjab", 2016, 30.39, 12000, 500, 80));
//        demandSupplyArray.add(new DemandSupply("Corn", "Punjab", 2017, 31.39, 1000, 700, 100));
//        demandSupplyArray.add(new DemandSupply("Corn", "Punjab", 2018, 32.39, 1100, 1400, 500));
//        demandSupplyArray.add(new DemandSupply("Corn", "Punjab", 2019, 33.39, 1500, 5000, 1000));
//        for (DemandSupply demandSupply : demandSupplyArray) {
//
//            collectionReference.add(demandSupply).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
//                @Override
//                public void onSuccess(DocumentReference documentReference) {
//
//                    Log.d(TAG, "upload data done");
//                }
//            }).addOnFailureListener(new OnFailureListener() {
//                @Override
//                public void onFailure(@NonNull Exception e) {
//
//                    Log.d(TAG, "upload data failed");
//                }
//            });
//        }
        btnSeeMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                Boolean a = openApp(getApplicationContext(),"ui.test.heatmap");
            }
        });

        Intent i = getIntent();
        String crop = i.getStringExtra("crop");

        if(crop!=null)
        {
            Log.d(TAG,crop);
            collectionReference.whereEqualTo("crop_name", crop)
                    .get()
                    .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                            ArrayList<String> year = new ArrayList<>();
                            ArrayList<String> demand = new ArrayList<>();
                            ArrayList<String> supply = new ArrayList<>();

                            for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                                DemandSupply demandSupply = documentSnapshot.toObject(DemandSupply.class);

                                year.add(String.valueOf(demandSupply.getYear()));
                                demand.add(String.valueOf(demandSupply.getDemand()));
                                supply.add(String.valueOf(demandSupply.getSupply()));
                            }

                            String[] x = new String[year.size()];
                            x = year.toArray(x);
                            Log.d(TAG,x.toString());

                            String[] y1 = new String[demand.size()];
                            y1 = demand.toArray(y1);
                            Log.d(TAG,y1.toString());

                            String[] y2 = new String[supply.size()];
                            y2 = supply.toArray(y2);
                            Log.d(TAG,y2.toString());

                            StaticLabelsFormatter staticLabelsFormatter = new StaticLabelsFormatter(graphViewDemandSupply);
                            staticLabelsFormatter.setHorizontalLabels(x);

                            graphViewDemandSupply.getGridLabelRenderer().setLabelFormatter(staticLabelsFormatter);
//                                graphViewDemandSupply.getGridLabelRenderer().setLabelHorizontalHeight(25);
//                                graphViewDemandSupply.getGridLabelRenderer().setLabelVerticalWidth(60);
//                                graphViewDemandSupply.getGridLabelRenderer().setVerticalAxisTitleColor(Color.RED);
//                                //graph.getGridLabelRenderer().setVerticalAxisTitleTextSize(35);
//                                graphViewDemandSupply.getGridLabelRenderer().setTextSize(25);


                            graphViewDemandSupply.getGridLabelRenderer().setVerticalAxisTitle("Demand & Supply");
                            graphViewDemandSupply.getGridLabelRenderer().setHorizontalAxisTitle("Slots");

                            //processing Y-axis

                            DataPoint[] values1 = new DataPoint[y1.length];
                            //creating an object of type DataPoint[] of size 'n'
                            for (int i = 0; i < y1.length; i++) {
                                DataPoint v = new DataPoint(i, Double.parseDouble(y1[i]));
                                values1[i] = v;
                            }

                            LineGraphSeries<DataPoint> series1 = new LineGraphSeries<DataPoint>(values1);

                            DataPoint[] values2 = new DataPoint[y2.length];
                            //creating an object of type DataPoint[] of size 'n'
                            for (int i = 0; i < y2.length; i++) {
                                DataPoint v = new DataPoint(i, Double.parseDouble(y2[i]));
                                values2[i] = v;
                            }

                            LineGraphSeries<DataPoint> series2 = new LineGraphSeries<DataPoint>(values2);


                            series1.setColor(Color.GREEN);
                            graphViewDemandSupply.addSeries(series1);

                            series2.setColor(Color.RED);
                            graphViewDemandSupply.addSeries(series2);


                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                    if (e != null) {
                        Log.d(TAG, e.toString());
                    }
                }
            });

        }



    }

    public Boolean openApp(Context context, String s)
    {
        PackageManager manager = context.getPackageManager();
        try {
            Intent i = manager.getLaunchIntentForPackage(s);
            if(i == null)
            {
                return false;
            }
            i.addCategory(Intent.CATEGORY_LAUNCHER);
            i.putExtra("type","demand");

            context.startActivity(i);
            return true;
        }catch (ActivityNotFoundException e)
        {
            return  false;
        }
    }
}
