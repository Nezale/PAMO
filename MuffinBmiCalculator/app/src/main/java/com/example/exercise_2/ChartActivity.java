package com.example.exercise_2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;

import java.time.LocalDate;
import java.util.ArrayList;

public class ChartActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);
        Button backButton = (Button) findViewById(R.id.backButton);
        WebView chartWebView = (WebView) findViewById(R.id.chartWebView);
        Intent intent = getIntent();
        ArrayList<String> statistics = intent.getStringArrayListExtra("statistics");
        String value = getValuesString(statistics);
        WebSettings webSettings = chartWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        String htmlData = "<html>"
                + "  <head>"
                + "    <script type=\"text/javascript\" src=\"https://www.gstatic.com/charts/loader.js\"></script>"
                + "    <script type=\"text/javascript\">"
                + "      google.charts.load('current', {'packages':['corechart']});"
                + "      google.charts.setOnLoadCallback(drawChart);"
                + "      function drawChart() {"
                + "        var data = google.visualization.arrayToDataTable(["
                + "          ['?', 'BMI']," + value + "]);"
                + "        var options = {\n"
                + "          title: 'Zmiana BMI w czasie',\n"
                + "          curveType: 'function',\n"
                + "          legend: { position: 'bottom' }\n"
                + "        };\n"
                + "        var chart = new google.visualization.LineChart(document.getElementById('curve_chart'));"
                + "        chart.draw(data, options);"
                + "      }"
                + "    </script>"
                + "  </head>"
                + "  <body>"
                + "    <div id=\"curve_chart\" style=\"width: 375px; height: 480px;\"></div>"
                + "  </body>"
                + "</html>";
        chartWebView.loadData(htmlData, "text/html", "UTF-8");
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private String getValuesString(ArrayList<String> statistics) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < statistics.size(); i++) {
            result.append("['Pomiar ").append(i).append("',").append(Float.valueOf(statistics.get(i))).append("],");
        }
        return result.toString();
    }
}
