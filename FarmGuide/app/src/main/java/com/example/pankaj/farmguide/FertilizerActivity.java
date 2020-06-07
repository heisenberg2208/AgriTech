package com.example.pankaj.farmguide;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class FertilizerActivity extends AppCompatActivity {

    RadioGroup rgCropName, rgLocation;
    RadioButton rbWheat, rbRice, rbCotton, rbSoyabean, rbMaize, rbMaharashtra, rbPunjab;
    EditText etYield, etN, etP, etK, etFertiLandSize;
    Button btnFertiCalculate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fertilizer);

        rgCropName = (RadioGroup) findViewById(R.id.rgCropName);
        rgLocation = (RadioGroup) findViewById(R.id.rgLocation);

        rbWheat  = (RadioButton) findViewById(R.id.rbWheat);
        rbRice  = (RadioButton) findViewById(R.id.rbRice);
        rbCotton  = (RadioButton) findViewById(R.id.rbCotton);
        rbSoyabean  = (RadioButton) findViewById(R.id.rbSoyabean);
        rbMaize  = (RadioButton) findViewById(R.id.rbMaize);
        rbMaharashtra  = (RadioButton) findViewById(R.id.rbMaharashtra);
        rbPunjab  = (RadioButton) findViewById(R.id.rbPunjab);

        etYield = (EditText) findViewById(R.id.etYield);
        etN = (EditText) findViewById(R.id.etN);
        etP = (EditText) findViewById(R.id.etP);
        etK= (EditText) findViewById(R.id.etK);
        etFertiLandSize = (EditText) findViewById(R.id.etFertiLandSize);

        btnFertiCalculate = (Button) findViewById(R.id.btnFertiCalculate);


        btnFertiCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int s1 = rgCropName.getCheckedRadioButtonId();
                RadioButton rb = (RadioButton)findViewById(s1);
                String cropname = rb.getText().toString();

                int s2 = rgLocation.getCheckedRadioButtonId();
                RadioButton rb1 = (RadioButton)findViewById(s2);
                String location = rb1.getText().toString();

                String yield = etYield.getText().toString();
                String N = etN.getText().toString();
                String P = etP.getText().toString();
                String K = etK.getText().toString();
                String landsize = etFertiLandSize.getText().toString();

                Toast.makeText(FertilizerActivity.this, cropname+" "+location+" "+yield+" "+N+" "+P+" "+K+" "+landsize, Toast.LENGTH_SHORT).show();

                if(yield.length()==0||N.length()==0||P.length()==0||K.length()==0||landsize.length()==0)
                {
                    Toast.makeText(FertilizerActivity.this, "Enter correct data", Toast.LENGTH_SHORT).show();
                    return;
                }

                double y = Double.parseDouble(yield);
                double n = Double.parseDouble(N);
                double p = Double.parseDouble(P);
                double k = Double.parseDouble(K);
                double ls = Double.parseDouble(landsize);



            }
        });

    }
}
