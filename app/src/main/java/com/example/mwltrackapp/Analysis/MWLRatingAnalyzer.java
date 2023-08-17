package com.example.mwltrackapp.Analysis;

import android.util.Log;

import com.example.mwltrackapp.Data.DataInfor;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class MWLRatingAnalyzer {
    private static final String TAG = "MWLRatingAnalyzer";

    public static void analyzeMWLRatings(List<DataInfor> ratingEntries) {
        for (int i = 0; i < ratingEntries.size(); i++) {
            DataInfor currentEntry = ratingEntries.get(i);

            if (currentEntry.getMWLRating() != null) {
                String currentDateStr = currentEntry.getMonth() + " " + currentEntry.getDay() + ", " + currentEntry.getYear();
                String currentTimeStr = currentEntry.getTime();
                int currentMwlRating = Integer.parseInt(currentEntry.getMWLRating());

                if (i < ratingEntries.size() - 1) {
                    DataInfor nextEntry = ratingEntries.get(i + 1);
                    String nextDateStr = nextEntry.getMonth() + " " + nextEntry.getDay() + ", " + nextEntry.getYear();
                    String nextTimeStr = nextEntry.getTime();

                    SimpleDateFormat format = new SimpleDateFormat("MMM d, yyyy HH:mm:ss");
                    try {
                        Date currentDate = format.parse(currentDateStr + " " + currentTimeStr);
                        Date nextDate = format.parse(nextDateStr + " " + nextTimeStr);
                        long durationMillis = nextDate.getTime() - currentDate.getTime();

                        double durationHours = (double) durationMillis / (60 * 60 * 1000);

                        Log.d(TAG, "Date: " + currentDateStr);
                        Log.d(TAG, "Duration: " + String.format("%.1f", durationHours) + "h");
                        Log.d(TAG, "MWL Rating: " + currentMwlRating);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}