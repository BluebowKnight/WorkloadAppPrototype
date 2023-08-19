package com.example.mwltrackapp;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.mwltrackapp.Analysis.MWLRatingAnalyzer;
import com.example.mwltrackapp.Data.DataInfor;
import com.example.mwltrackapp.Data.DataParser;

import java.util.List;
import java.util.Map;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        List<DataInfor> ratingEntries = DataParser.parseJsonFromFile(this, R.raw.test_data);

        TextView Duration = findViewById(R.id.duration);
        TextView LowMWL = findViewById(R.id.low_mwl);
        TextView MediumMWL = findViewById(R.id.medium_mwl);
        TextView HighMWL = findViewById(R.id.high_mwl);

        MWLRatingAnalyzer analyzer = new MWLRatingAnalyzer();  // 创建 MWLRatingAnalyzer 实例
        analyzer.analyzeMWLRatings(ratingEntries);  // 调用 analyzeMWLRatings 方法进行分析

        Map<String, Double> ratingDurationMap = analyzer.getRatingDurationMap();  // 获取 ratingDurationMap

        Double minDuration = Double.MAX_VALUE;
        for (Double duration : ratingDurationMap.values()) {
            if (duration < minDuration) {
                minDuration = duration;
            }
        }

        Log.d(TAG, "Min Duration: " + minDuration);

        // 在这里使用 ratingDurationMap，例如打印它的内容
        for (Map.Entry<String, Double> entry : ratingDurationMap.entrySet()) {
            String rating = entry.getKey();
            Double duration = entry.getValue();
            Log.d(TAG, "MWL Rating: " + rating + ", Duration: " + duration + "h");
        }

        Double LMWLTime = ratingDurationMap.get("1") + ratingDurationMap.get("2");
        Double HMWLTime = ratingDurationMap.get("4") + ratingDurationMap.get("5");
        Log.d(TAG,"Just Check: " + LMWLTime);

        String duration = String.format("%.2f",minDuration) + "h";
        String lowMWL = String.format("%.2f", LMWLTime) + "h";
        String mediumMWL = String.format("%.2f", ratingDurationMap.get("1")) + "h";
        String highMWL = String.format("%.2f", HMWLTime) + "h";

        for (DataInfor entry : ratingEntries) {
            String month = entry.getMonth();
            String day = entry.getDay();
            String year = entry.getYear();
            String time = entry.getTime();
            String mwlrating = entry.getMWLRating();
            String sleepRating = entry.getSleepRating();

            // 现在你可以使用解析后的数据进行操作，例如显示在 TextView 中
            String data = "Date: " + month + " " + day + ", " + year + "\n"
                    + "Time: " + time + "\n"
                    + "MWL Rating: " + mwlrating + "\n"
                    + "Sleep Rating: " + sleepRating + "\n\n";

            // 将数据添加到你的 TextView 或其他视图中
            Log.d(TAG, "Data:" + data);
        }

        Duration.setText(duration);
        LowMWL.setText(lowMWL);
        MediumMWL.setText(mediumMWL);
        HighMWL.setText(highMWL);

        //MWLRatingAnalyzer.analyzeMWLRatings(ratingEntries);
    }
}