package com.example.pankaj.farmguide;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.Locale;

public class CropSelectionInput extends AppCompatActivity {

    TextView tvAddress;
    Spinner spnType;
    EditText etBudget, etLandSize,etpH;
    CheckBox cbSprinkler, cbDrip;
    Button btnProceed;


    String[] spinnerTitles;
    int[] spinnerImages;
    private boolean isUserInteracting;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop_selection_input);

        tvAddress = (TextView) findViewById(R.id.tvAddress);
        spnType = (Spinner) findViewById(R.id.spnType);
        etBudget = (EditText) findViewById(R.id.etBudget);
        etLandSize = (EditText) findViewById(R.id.etLandSize);
        cbSprinkler = (CheckBox) findViewById(R.id.cbSprinkler);
        cbDrip = (CheckBox) findViewById(R.id.cbDrip);
        btnProceed = (Button) findViewById(R.id.btnProceed);
        etpH = (EditText) findViewById(R.id.etpH);

        double lat = 30.51;
        double lon = 76.65;
        Geocoder g = new Geocoder(CropSelectionInput.this, Locale.ENGLISH);
        try {

            List<Address> addressList = g.getFromLocation(lat, lon, 1);
            Address address = addressList.get(0);
            String add = address.getAdminArea();
            tvAddress.setText(add);


        } catch (Exception e) {
            e.printStackTrace();
        }

        spinnerTitles = new String[]{"Alluvial", "Black", "Red", "Desert", "Laterite", "Mountain"};
        spinnerImages = new int[]{
                R.drawable.alluvial, R.drawable.black, R.drawable.red, R.drawable.desert, R.drawable.laterite, R.drawable.mountain
        };


        CustomAdapter mCustomAdapter = new CustomAdapter(CropSelectionInput.this, spinnerTitles, spinnerImages);
        spnType.setAdapter(mCustomAdapter);

        spnType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (isUserInteracting) {
                    Toast.makeText(CropSelectionInput.this, spinnerTitles[i], Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        btnProceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(CropSelectionInput.this, CropSelectorActivity.class);
                int posSelectedType = spnType.getSelectedItemPosition();

                String Address = tvAddress.getText().toString();
                i.putExtra("address",Address);

                String type = spinnerTitles[posSelectedType];
                i.putExtra("soiltype",type);

                String budget = etBudget.getText().toString();
                i.putExtra("budget",budget);

                String landSize = etLandSize.getText().toString();
                i.putExtra("landsize",landSize);

                i.putExtra("sprinkler","false");
                i.putExtra("drip","false");

                if(cbSprinkler.isChecked())
                    i.putExtra("sprinkler","true");
                if(cbDrip.isChecked())
                    i.putExtra("drip","true");

                String ph = etpH.getText().toString();
                if(ph!=null)
                    i.putExtra("ph",ph);
                startActivity(i);
            }
        });


    }
}
