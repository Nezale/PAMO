package com.example.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import java.text.NumberFormat;

public class MainActivity extends Activity {

    private static final NumberFormat weightFormat = NumberFormat.getNumberInstance();
    private static final NumberFormat heightFormat = NumberFormat.getNumberInstance();
    private static final NumberFormat bmiFormat = NumberFormat.getNumberInstance();

    private double weight = 0;
    private double height = 0;

    private TextView bmiTextView;
    private TextView weightTextView;
    private TextView heightTextView;

    // called when the activity is first created
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); // call superclass onCreate
        setContentView(R.layout.activity_main); // inflate the GUI

        // get references to programmatically manipulated TextViews
        bmiTextView = (TextView) findViewById(R.id.bmiTextView);
        weightTextView = (TextView) findViewById(R.id.weightTextView);
        heightTextView = (TextView) findViewById(R.id.heightTextView);
        weightTextView.setText(weightFormat.format(0));
        heightTextView.setText(heightFormat.format(0));

        SeekBar weightSeekBar = (SeekBar) findViewById(R.id.weightSeekBar);
        weightSeekBar.setOnSeekBarChangeListener(weightSeekBarListener);

        SeekBar heightSeekBar = (SeekBar) findViewById(R.id.heightSeekBar);
        heightSeekBar.setOnSeekBarChangeListener(heightSeekBarListener);
    }

    // calculate and display tip and total amounts
    private void calculate() {
        // format height and display in weightTextView
        heightTextView.setText(heightFormat.format(height));
        weightTextView.setText(weightFormat.format(weight));

        // calculate the tip and total
        double BMI = weight/((height/100)*(height/100));

        // display tip and total formatted as currency
        bmiTextView.setText(bmiFormat.format(BMI));
        showBmi(BMI);
    }

    // listener object for the SeekBar's progress changed events
    private final OnSeekBarChangeListener weightSeekBarListener =
            new OnSeekBarChangeListener() {
                // update height, then call calculate
                @Override
                public void onProgressChanged(SeekBar weightSeekBar, int progress, boolean fromUser) {
                    weight = progress; // set height based on progress

                    calculate(); // calculate and display tip and total
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) { }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) { }
            };

    private final OnSeekBarChangeListener heightSeekBarListener =
            new OnSeekBarChangeListener() {
                // update height, then call calculate
                @Override
                public void onProgressChanged(SeekBar heightSeekBar, int progress, boolean fromUser) {
                    height = progress; // set height based on progress

                    calculate(); // calculate and display tip and total
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) { }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) { }
            };

        private void showBmi(double bmi) {
        final TextView showBmiTextView = (TextView) findViewById(R.id.showBmiTextView);
        if (bmi < 18.5)
            showBmiTextView.setText(R.string.bmi_underweight);
        else if (bmi >= 18.5 && bmi <= 25)
            showBmiTextView.setText(R.string.bmi_normalweight);
        else if (bmi > 25 && bmi <= 30)
            showBmiTextView.setText(R.string.bmi_overweight);
        else
            showBmiTextView.setText(R.string.bmi_obese);
    }

}
