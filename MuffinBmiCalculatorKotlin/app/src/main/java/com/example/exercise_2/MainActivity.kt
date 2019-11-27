package com.example.exercise_2

import android.content.Intent
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RadioGroup
import android.widget.SeekBar
import android.widget.TextView

import java.text.NumberFormat
import java.time.LocalDate
import java.util.ArrayList

class MainActivity : AppCompatActivity() {

    private var bmilayout: LinearLayout? = null
    private var ppmLayout: LinearLayout? = null
    private var homeLayout: LinearLayout? = null
    private val imageLayout: ConstraintLayout? = null

    private val statistics = ArrayList<String>()

    private var weight = 0.0
    private var height = 0.0
    private var age = 0
    private var gender = 0

    //  RadioGroup genderRadioGroup = (RadioGroup) findViewById(R.id.genderRadioGroup);

    private var bmiTextView: TextView? = null
    private var weightTextView: TextView? = null
    private var heightTextView: TextView? = null
    private var ageTextView: TextView? = null


    private var mTextMessage: TextView? = null

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                bmilayout!!.visibility = View.INVISIBLE
                ppmLayout!!.visibility = View.INVISIBLE
                //                    mTextMessage.setText(LocalDate.now().toString());
                homeLayout!!.visibility = View.VISIBLE
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_dashboard -> {
                bmilayout!!.visibility = View.VISIBLE
                ppmLayout!!.visibility = View.INVISIBLE
                homeLayout!!.visibility = View.INVISIBLE
                mTextMessage!!.text = ""
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_notifications -> {
                bmilayout!!.visibility = View.INVISIBLE
                ppmLayout!!.visibility = View.VISIBLE
                homeLayout!!.visibility = View.INVISIBLE
                mTextMessage!!.text = ""
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    // listener object for the SeekBar's progress changed events
    private val weightSeekBarListener = object : SeekBar.OnSeekBarChangeListener {
        // update height, then call calculate
        override fun onProgressChanged(weightSeekBar: SeekBar, progress: Int, fromUser: Boolean) {
            weight = progress.toDouble() // set height based on progress

            calculate() // calculate and display tip and total
            ppmResultDetails(calculatePPM())
        }

        override fun onStartTrackingTouch(seekBar: SeekBar) {}

        override fun onStopTrackingTouch(seekBar: SeekBar) {}
    }

    private val heightSeekBarListener = object : SeekBar.OnSeekBarChangeListener {
        // update height, then call calculate
        override fun onProgressChanged(heightSeekBar: SeekBar, progress: Int, fromUser: Boolean) {
            height = progress.toDouble() // set height based on progress

            calculate() // calculate and display tip and total
            ppmResultDetails(calculatePPM())
        }

        override fun onStartTrackingTouch(seekBar: SeekBar) {}

        override fun onStopTrackingTouch(seekBar: SeekBar) {}
    }

    private val ageSeekBarListener = object : SeekBar.OnSeekBarChangeListener {
        // update height, then call calculate
        override fun onProgressChanged(ageSeekBar: SeekBar, progress: Int, fromUser: Boolean) {
            age = progress // set height based on progress
            ageTextView!!.text = heightFormat.format(age.toLong())
            ppmResultDetails(calculatePPM())
            // calculate and display tip and total
        }

        override fun onStartTrackingTouch(seekBar: SeekBar) {}

        override fun onStopTrackingTouch(seekBar: SeekBar) {}
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navView = findViewById<BottomNavigationView>(R.id.nav_view)
        mTextMessage = findViewById(R.id.message)
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        findViewById<View>(R.id.chartButton).setOnClickListener { showChart() }

        findViewById<View>(R.id.quizButton).setOnClickListener { startQuiz() }

        homeLayout = findViewById(R.id.homeLayout)
        bmilayout = findViewById(R.id.bmiLayout)
        ppmLayout = findViewById(R.id.dietLayout)
        bmilayout!!.visibility = View.INVISIBLE
        ppmLayout!!.visibility = View.INVISIBLE

        val genderRadioGroup = findViewById<View>(R.id.genderRadioGroup) as RadioGroup

        genderRadioGroup.setOnCheckedChangeListener { group, checkedId ->
            val radioButton = genderRadioGroup.findViewById<View>(checkedId)
            gender = genderRadioGroup.indexOfChild(radioButton)
            ppmResultDetails(calculatePPM())
        }

        bmiTextView = findViewById<View>(R.id.bmiTextView) as TextView

        weightTextView = findViewById<View>(R.id.weightTextView) as TextView
        heightTextView = findViewById<View>(R.id.heightTextView) as TextView
        ageTextView = findViewById<View>(R.id.ageTextView) as TextView

        weightTextView!!.text = weightFormat.format(0)
        heightTextView!!.text = heightFormat.format(0)
        ageTextView!!.text = ageFormat.format(0)

        val weightSeekBar = findViewById<View>(R.id.weightSeekBar) as SeekBar
        weightSeekBar.setOnSeekBarChangeListener(weightSeekBarListener)

        val heightSeekBar = findViewById<View>(R.id.heightSeekBar) as SeekBar
        heightSeekBar.setOnSeekBarChangeListener(heightSeekBarListener)

        val ageSeekBar = findViewById<View>(R.id.ageSeekBar) as SeekBar
        ageSeekBar.setOnSeekBarChangeListener(ageSeekBarListener)


    }


    // calculate and display tip and total amounts
    private fun calculate(): Double {
        // format height and display in weightTextView
        heightTextView!!.text = heightFormat.format(height)
        weightTextView!!.text = weightFormat.format(weight)

        // calculate the tip and total
        val BMI = weight / (height / 100 * (height / 100))

        // display tip and total formatted as currency
        bmiTextView!!.text = bmiFormat.format(BMI)
        showBmi(BMI)
        return BMI
    }

    private fun calculatePPM(): Float {
        return if (gender == 0) {
            (10 * weight + height * 6.25 - (5 * age).toDouble() - 161.0).toFloat()
        } else {
            (10 * weight + height * 6.25 - 5 * age + 5).toFloat()
        }
    }

    private fun ppmResultDetails(ppmResult: Float) {
        val very_low = findViewById<ImageView>(R.id.veryLowImageView)
        val low = findViewById<ImageView>(R.id.lowImageView)
        val normal = findViewById<ImageView>(R.id.normalImageView)
        val high = findViewById<ImageView>(R.id.highImageView)
        val ppmResultTextView = findViewById<View>(R.id.ppmResultTextView) as TextView
        ppmResultTextView.text = String.format("%.2f", ppmResult)
        if (ppmResult < 1600) {
            very_low.findViewById<View>(R.id.veryLowImageView).visibility = View.VISIBLE
            low.findViewById<View>(R.id.lowImageView).visibility = View.INVISIBLE
            normal.findViewById<View>(R.id.normalImageView).visibility = View.INVISIBLE
            high.findViewById<View>(R.id.highImageView).visibility = View.INVISIBLE
        } else if (ppmResult >= 1600 && ppmResult < 1800) {
            very_low.findViewById<View>(R.id.veryLowImageView).visibility = View.INVISIBLE
            low.findViewById<View>(R.id.lowImageView).visibility = View.VISIBLE
            normal.findViewById<View>(R.id.normalImageView).visibility = View.INVISIBLE
            high.findViewById<View>(R.id.highImageView).visibility = View.INVISIBLE
        } else if (ppmResult >= 1800 && ppmResult < 2000) {
            very_low.findViewById<View>(R.id.veryLowImageView).visibility = View.INVISIBLE
            low.findViewById<View>(R.id.lowImageView).visibility = View.INVISIBLE
            normal.findViewById<View>(R.id.normalImageView).visibility = View.VISIBLE
            high.findViewById<View>(R.id.highImageView).visibility = View.INVISIBLE
        } else if (ppmResult >= 2000) {
            very_low.findViewById<View>(R.id.veryLowImageView).visibility = View.INVISIBLE
            low.findViewById<View>(R.id.lowImageView).visibility = View.INVISIBLE
            normal.findViewById<View>(R.id.normalImageView).visibility = View.INVISIBLE
            high.findViewById<View>(R.id.highImageView).visibility = View.VISIBLE
        }
    }

    private fun showBmi(bmi: Double) {
        val showBmiTextView = findViewById<View>(R.id.showBmiTextView) as TextView
        if (bmi < 18.5)
            showBmiTextView.setText(R.string.bmi_underweight)
        else if (bmi >= 18.5 && bmi <= 25)
            showBmiTextView.setText(R.string.bmi_normalweight)
        else if (bmi > 25 && bmi <= 30)
            showBmiTextView.setText(R.string.bmi_overweight)
        else
            showBmiTextView.setText(R.string.bmi_obese)
    }

    private fun startQuiz() {
        val intent = Intent(this, QuizActivity::class.java)
        startActivity(intent)
    }

    private fun showChart() {
        statistics.add(calculate().toString())
        val intent = Intent(this, ChartActivity::class.java)
        intent.putStringArrayListExtra("statistics", statistics)
        startActivity(intent)
    }

    companion object {

        private val weightFormat = NumberFormat.getNumberInstance()
        private val heightFormat = NumberFormat.getNumberInstance()
        private val ageFormat = NumberFormat.getNumberInstance()
        private val bmiFormat = NumberFormat.getNumberInstance()
    }


}
