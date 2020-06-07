package com.shawnlin.numberpicker.sample;

import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import com.shawnlin.numberpicker.NumberPicker;

import java.util.HashMap;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private static String TAG = "NumberPicker";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final NumberPicker numberPicker = findViewById(R.id.number_picker);

        // Set divider color
        numberPicker.setDividerColor(ContextCompat.getColor(this, R.color.colorPrimary));
        numberPicker.setDividerColorResource(R.color.colorPrimary);

        // Set formatter
        numberPicker.setFormatter(getString(R.string.number_picker_formatter));
        numberPicker.setFormatter(R.string.number_picker_formatter);

        // Set selected text color
        numberPicker.setSelectedTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
        numberPicker.setSelectedTextColorResource(R.color.colorPrimary);

        // Set locale
        numberPicker.setLocale(new Locale("ar")); //comment this line to show the picker in english

        // Set selected text size
        numberPicker.setSelectedTextSize(getResources().getDimension(R.dimen.selected_text_size));
        numberPicker.setSelectedTextSize(R.dimen.selected_text_size);

        // Set text color
        numberPicker.setTextColor(ContextCompat.getColor(this, R.color.dark_grey));
        numberPicker.setTextColorResource(R.color.dark_grey);

        // Set text size
        numberPicker.setTextSize(getResources().getDimension(R.dimen.text_size));
        numberPicker.setTextSize(R.dimen.text_size);

        // Set typeface
        numberPicker.setTypeface(Typeface.create(getString(R.string.roboto_light), Typeface.NORMAL));
        numberPicker.setTypeface(getString(R.string.roboto_light), Typeface.NORMAL);
        numberPicker.setTypeface(getString(R.string.roboto_light));
        numberPicker.setTypeface(R.string.roboto_light, Typeface.NORMAL);
        numberPicker.setTypeface(R.string.roboto_light);

        // Set value
        numberPicker.setMaxValue(59);
        numberPicker.setMinValue(0);
        numberPicker.setValue(3);

        // Set string values
//        String[] data = {"A", "B", "C", "D", "E", "F", "G", "H", "I"};
//        numberPicker.setMinValue(1);
//        numberPicker.setMaxValue(data.length);
//        numberPicker.setDisplayedValues(data);

        // Set fading edge enabled
        numberPicker.setFadingEdgeEnabled(true);

        // Set scroller enabled
        numberPicker.setScrollerEnabled(true);

        // Set wrap selector wheel
        numberPicker.setWrapSelectorWheel(true);

        final String[] data = {"2019-6-16 to 2019-6-19", "2019-6-20 to 2019-6-23", "2019-6-24 to 2019-6-27", "2019-6-28 to 2019-7-1", "2019-7-2 to 2019-7-5", "2019-7-6 to 2019-7-9", "2019-7-10 to 2019-7-13"};
        numberPicker.setMinValue(1);
        numberPicker.setMaxValue(data.length);
        numberPicker.setDisplayedValues(data);
        final HashMap<String, Double> hm = new HashMap<>();
        hm.put("2019-6-16 to 2019-6-19", Double.valueOf(1218.875d));
        hm.put("2019-6-20 to 2019-6-23", Double.valueOf(1924.125d));
        hm.put("2019-6-24 to 2019-6-27", Double.valueOf(1580.25d));
        hm.put("2019-6-28 to 2019-7-1", Double.valueOf(1349.25d));
        hm.put("2019-7-2 to 2019-7-5", Double.valueOf(1288.0d));
        hm.put("2019-7-6 to 2019-7-9", Double.valueOf(1011.5d));
        hm.put("2019-7-10 to 2019-7-13", Double.valueOf(1027.25d));
        numberPicker.setFadingEdgeEnabled(true);
        numberPicker.setScrollerEnabled(true);
        numberPicker.setWrapSelectorWheel(true);
        numberPicker.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Log.d(MainActivity.TAG, "Click on current value");
            }
        });
        numberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                Log.d(MainActivity.TAG, String.format(Locale.US, "oldVal: %d, newVal: %d", new Object[]{Integer.valueOf(oldVal), Integer.valueOf(newVal)}));
                TextView textView = findViewById(R.id.yeild);
                StringBuilder sb = new StringBuilder();
                sb.append(((Double) hm.get(data[newVal - 1])).toString());
                sb.append("Kg/Hector");
                textView.setText(sb.toString());
            }
        });
    }

}
