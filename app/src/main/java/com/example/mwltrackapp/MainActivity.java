package com.example.mwltrackapp;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.example.mwltrackapp.Analysis.MWLRatingAnalyzer;
import com.example.mwltrackapp.Data.DataInfor;
import com.example.mwltrackapp.Data.DataParser;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        List<DataInfor> ratingEntries = DataParser.parseJsonFromFile(this, R.raw.test_data);

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

        MWLRatingAnalyzer.analyzeMWLRatings(ratingEntries);
    }
}