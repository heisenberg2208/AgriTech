package com.example.pankaj.farmguide;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Toast;

import com.getkeepsafe.taptargetview.TapTarget;
import com.getkeepsafe.taptargetview.TapTargetSequence;
import com.getkeepsafe.taptargetview.TapTargetView;

import java.util.Locale;


public class Dashboard extends AppCompatActivity {

    CardView cs, sd, tl, dd, cb, bs;
    String lang, t1, t2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        cs = (CardView) findViewById(R.id.cropSelector);
        sd = (CardView) findViewById(R.id.sowingDate);
        tl = (CardView) findViewById(R.id.timeline);
        dd = (CardView) findViewById(R.id.diseaseDetector);
        cb = (CardView) findViewById(R.id.chatbot);
        bs = (CardView) findViewById(R.id.buyingSelling);
        SharedPreferences sharedPreferences = getSharedPreferences("Mypref", MODE_PRIVATE);
        lang = sharedPreferences.getString("lang", "");
        //Toast.makeText(this, lang, Toast.LENGTH_SHORT).show();
        t1 = "It helps to select best crop for you";
        t2 = "It helps to select you optimal sowing date";
        if (lang.equals("hi")) {
            t1 = "यह अच्छी फसल का चयन करने में मदद करेगा";
            t2 = "यह  इष्टतम बुवाई की तारीख का चयन करने में मदद करेगा";
        } else {
        }
        final Drawable droid = ContextCompat.getDrawable(this, R.drawable.crop_selector);
        final Rect droidTarget = new Rect(0, 0, droid.getIntrinsicWidth() * 2, droid.getIntrinsicHeight() * 2);
        TapTargetSequence tapTarget = new TapTargetSequence(this)
                .targets(
                        TapTarget.forView(findViewById(R.id.cropSelector), t1, "").transparentTarget(true).titleTextSize(20).descriptionTextSize(15),
                        TapTarget.forView(findViewById(R.id.sowingDate), t2, "").transparentTarget(true))

                .listener(new TapTargetSequence.Listener() {
                    // This listener will tell us when interesting(tm) events happen in regards
                    // to the sequence
                    @Override
                    public void onSequenceFinish() {
                        // Yay
                    }

                    @Override
                    public void onSequenceStep(TapTarget lastTarget, boolean targetClicked) {

                    }


                    @Override
                    public void onSequenceCanceled(TapTarget lastTarget) {
                        // Boo
                    }
                });
        tapTarget.start();

        cs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Dashboard.this, CropSelectionInput.class);
                startActivity(i);
            }
        });
        cb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String pkg= new String ("com.directlineex.brsingh.directlineandroidexample");

                Intent i = getPackageManager().getLaunchIntentForPackage(pkg);
                if (i != null)
                {

                    startActivity(i);
                }
                else
                {
                    Intent intent = new Intent(Dashboard.this, ChatbotActivity.class);
                    startActivity(intent);
                }

            }
        });
        sd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Boolean a = openApp(getApplicationContext(), "com.shawnlin.numberpicker.sample");


            }
        });
        tl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Boolean a = openApp(getApplicationContext(), "com.example.pankaj.timeline");
            }
        });
        bs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Dashboard.this, AddProduct.class);
                startActivity(intent);
            }
        });
        dd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Dashboard.this, SelectDiseaseCrop.class);
                startActivity(i);
            }
        });
       /* sp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FarmerDashboard.this,SellerPortal.class);
                startActivity(intent);
            }
        });
        km.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FarmerDashboard.this,ChatbotActivity.class);
                startActivity(intent);
            }
        });
        in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FarmerDashboard.this,Test2Activity.class);
                startActivity(intent);
            }
        });
        pp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FarmerDashboard.this,GraphActivity.class);
                startActivity(intent);
            }
        });
        he.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FarmerDashboard.this,help.class);
                startActivity(intent);
            }
        });
*/
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
