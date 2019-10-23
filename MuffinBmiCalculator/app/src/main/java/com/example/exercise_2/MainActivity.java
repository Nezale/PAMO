package com.example.exercise_2;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.annotation.NonNull;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity {

    private LinearLayout bmilayout;
    private LinearLayout ppmLayout;
    private ConstraintLayout imageLayout;

    private static final NumberFormat weightFormat = NumberFormat.getNumberInstance();
    private static final NumberFormat heightFormat = NumberFormat.getNumberInstance();
    private static final NumberFormat ageFormat = NumberFormat.getNumberInstance();
    private static final NumberFormat bmiFormat = NumberFormat.getNumberInstance();

    private double weight = 0;
    private double height = 0;
    private int age = 0;
    private int gender = 0;

  //  RadioGroup genderRadioGroup = (RadioGroup) findViewById(R.id.genderRadioGroup);

    private TextView bmiTextView;
    private TextView weightTextView;
    private TextView heightTextView;
    private TextView ageTextView;


    private TextView mTextMessage;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    bmilayout.setVisibility(View.INVISIBLE);
                    ppmLayout.setVisibility(View.INVISIBLE);
                    mTextMessage.setText("Siemanko witam w mojej kuchni");
                    return true;
                case R.id.navigation_dashboard:
                    bmilayout.setVisibility(View.VISIBLE);
                    ppmLayout.setVisibility(View.INVISIBLE);
                    mTextMessage.setText("");
                    return true;
                case R.id.navigation_notifications:
                    bmilayout.setVisibility(View.INVISIBLE);
                    ppmLayout.setVisibility(View.VISIBLE);
                    mTextMessage.setText("");
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        mTextMessage = findViewById(R.id.message);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        bmilayout = findViewById(R.id.bmiLayout);
        ppmLayout = findViewById(R.id.dietLayout);

        final RadioGroup genderRadioGroup = (RadioGroup) findViewById(R.id.genderRadioGroup);

        genderRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                View radioButton = genderRadioGroup.findViewById(checkedId);
                gender = genderRadioGroup.indexOfChild(radioButton);
                ppmResultDetails(calculatePPM());
            }
        });

        bmiTextView = (TextView) findViewById(R.id.bmiTextView);

        weightTextView = (TextView) findViewById(R.id.weightTextView);
        heightTextView = (TextView) findViewById(R.id.heightTextView);
        ageTextView = (TextView) findViewById(R.id.ageTextView);

        weightTextView.setText(weightFormat.format(0));
        heightTextView.setText(heightFormat.format(0));
        ageTextView.setText(ageFormat.format(0));

        SeekBar weightSeekBar = (SeekBar) findViewById(R.id.weightSeekBar);
        weightSeekBar.setOnSeekBarChangeListener(weightSeekBarListener);

        SeekBar heightSeekBar = (SeekBar) findViewById(R.id.heightSeekBar);
        heightSeekBar.setOnSeekBarChangeListener(heightSeekBarListener);

        SeekBar ageSeekBar = (SeekBar) findViewById(R.id.ageSeekBar);
        ageSeekBar.setOnSeekBarChangeListener(ageSeekBarListener);
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

    private float calculatePPM() {
        if (gender == 0) {
            return (float) ((10 * weight) + (height * 6.25) - (5 * age) - 161);
        } else {
            return (float) ((10 * weight) + (height * 6.25) - (5 * age) + 5);
        }
    }

    private void ppmResultDetails(float ppmResult) {
        final ImageView very_low = findViewById(R.id.veryLowImageView);
        final ImageView low = findViewById(R.id.lowImageView);
        final ImageView normal = findViewById(R.id.normalImageView);
        final ImageView high = findViewById(R.id.highImageView);
        final TextView ppmResultTextView = (TextView) findViewById(R.id.ppmResultTextView);
        ppmResultTextView.setText(String.format("%.2f", ppmResult));
        if (ppmResult < 1600) {
            very_low.findViewById(R.id.veryLowImageView).setVisibility(View.VISIBLE);
            low.findViewById(R.id.lowImageView).setVisibility(View.INVISIBLE);
            normal.findViewById(R.id.normalImageView).setVisibility(View.INVISIBLE);
            high.findViewById(R.id.highImageView).setVisibility(View.INVISIBLE);
        } else if (ppmResult >= 1600 && ppmResult < 1800) {
            very_low.findViewById(R.id.veryLowImageView).setVisibility(View.INVISIBLE);
            low.findViewById(R.id.lowImageView).setVisibility(View.VISIBLE);
            normal.findViewById(R.id.normalImageView).setVisibility(View.INVISIBLE);
            high.findViewById(R.id.highImageView).setVisibility(View.INVISIBLE);
        } else if (ppmResult >= 1800 && ppmResult < 2000) {
            very_low.findViewById(R.id.veryLowImageView).setVisibility(View.INVISIBLE);
            low.findViewById(R.id.lowImageView).setVisibility(View.INVISIBLE);
            normal.findViewById(R.id.normalImageView).setVisibility(View.VISIBLE);
            high.findViewById(R.id.highImageView).setVisibility(View.INVISIBLE);
        } else if (ppmResult >= 2000) {
            very_low.findViewById(R.id.veryLowImageView).setVisibility(View.INVISIBLE);
            low.findViewById(R.id.lowImageView).setVisibility(View.INVISIBLE);
            normal.findViewById(R.id.normalImageView).setVisibility(View.INVISIBLE);
            high.findViewById(R.id.highImageView).setVisibility(View.VISIBLE);
        }
    }

    // listener object for the SeekBar's progress changed events
    private final SeekBar.OnSeekBarChangeListener weightSeekBarListener =
            new SeekBar.OnSeekBarChangeListener() {
                // update height, then call calculate
                @Override
                public void onProgressChanged(SeekBar weightSeekBar, int progress, boolean fromUser) {
                    weight = progress; // set height based on progress

                    calculate(); // calculate and display tip and total
                    ppmResultDetails(calculatePPM());
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) { }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) { }
            };

    private final SeekBar.OnSeekBarChangeListener heightSeekBarListener =
            new SeekBar.OnSeekBarChangeListener() {
                // update height, then call calculate
                @Override
                public void onProgressChanged(SeekBar heightSeekBar, int progress, boolean fromUser) {
                    height = progress; // set height based on progress

                    calculate(); // calculate and display tip and total
                    ppmResultDetails(calculatePPM());
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) { }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) { }
            };

    private final SeekBar.OnSeekBarChangeListener ageSeekBarListener =
            new SeekBar.OnSeekBarChangeListener() {
                // update height, then call calculate
                @Override
                public void onProgressChanged(SeekBar ageSeekBar, int progress, boolean fromUser) {
                    age = progress; // set height based on progress
                    ageTextView.setText(heightFormat.format(age));
                    ppmResultDetails(calculatePPM());
                    // calculate and display tip and total
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
