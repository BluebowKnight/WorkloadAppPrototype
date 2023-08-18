package com.example.mwltrackapp.Analysis;

import android.util.Log;

import com.example.mwltrackapp.Data.DataInfor;
import com.example.mwltrackapp.Data.DataParser;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class MWLRatingAnalyzer {
    private static final String TAG = "MWLRatingAnalyzer";

    public static void analyzeMWLRatings(List<DataInfor> ratingEntries) {
        String currentRating = null;
        Date startTime = null;

        SimpleDateFormat dateFormat = new SimpleDateFormat("MMM d, yyyy HH:mm:ss");

        for (int i = 0; i < ratingEntries.size(); i++) {
            DataInfor currentEntry = ratingEntries.get(i);

            if (currentEntry.getMWLRating() != null) {
                String currentDateStr = currentEntry.getMonth() + " " + currentEntry.getDay() + ", " + currentEntry.getYear();
                String currentTimeStr = currentEntry.getTime();
                String currentMwlRating = currentEntry.getMWLRating();

                if (!currentMwlRating.equals(currentRating)) {
                    if (startTime != null) {
                        try {
                            Date currentDate = dateFormat.parse(currentDateStr + " " + currentTimeStr);
                            long durationMillis = currentDate.getTime() - startTime.getTime();
                            double durationHours = (double) durationMillis / (60 * 60 * 1000);

                            Log.d(TAG, "MWL Rating " + currentRating + "; Duration " + String.format("%.2f", durationHours) + "h");
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                    currentRating = currentMwlRating;
                    try {
                        startTime = dateFormat.parse(currentDateStr + " " + currentTimeStr);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
