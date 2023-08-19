    package com.example.mwltrackapp.Analysis;

    import android.util.Log;
    import android.widget.TextView;

    import com.example.mwltrackapp.Data.DataInfor;

    import org.json.JSONObject;

    import java.text.ParseException;
    import java.text.SimpleDateFormat;
    import java.util.Date;
    import java.util.HashMap;
    import java.util.List;
    import java.util.Map;

    public class MWLRatingAnalyzer {
        private static final String TAG = "MWLRatingAnalyzer";

        private static Map<String, Double> ratingDurationMap = new HashMap<>();

        public static void analyzeMWLRatings(List<DataInfor> ratingEntries) {
            String currentRating = null;
            Date startTime = null;
            double MinDuration = 0;
            double MaxDuration = 0;

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

                                if (MinDuration == 0) {
                                    MinDuration = durationHours;
                                    MaxDuration = durationHours;
                                } else {
                                    if (durationHours < MinDuration){
                                        MinDuration = durationHours;
                                    }   else {if (durationHours > MaxDuration){

                                        MaxDuration = durationHours;
                                    }}}

                                Log.d(TAG,"Max: " + MaxDuration + " Min: "+ MinDuration);

                                if (ratingDurationMap.containsKey(currentRating)) {
                                    double oldValue = ratingDurationMap.get(currentRating);
                                    double newValue = oldValue + durationHours;
                                    ratingDurationMap.put(currentRating, newValue);
                                } else {
                                    ratingDurationMap.put(currentRating, durationHours);
                                }

                                Log.d(TAG, ratingDurationMap.toString());
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

        public Map<String, Double> getRatingDurationMap() {
            return ratingDurationMap;
        }
    }