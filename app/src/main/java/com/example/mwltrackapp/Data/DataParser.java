package com.example.mwltrackapp.Data;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

public class DataParser {
    public static List<DataInfor> parseJsonFromFile (Context context,int resourceId){
        List<DataInfor> ratingEntries = new ArrayList<>();
        try {
            InputStream inputStream = context.getResources().openRawResource(resourceId);
            Reader reader = new InputStreamReader(inputStream);

            JsonParser jsonParser = new JsonParser();
            JsonArray jsonArray = jsonParser.parse(reader).getAsJsonArray();

            Gson gson = new Gson();
            for (int i = 0; i < jsonArray.size(); i++) {
                JsonObject jsonObject = jsonArray.get(i).getAsJsonObject();
                DataInfor ratingEntry = gson.fromJson(jsonObject, DataInfor.class);
                ratingEntries.add(ratingEntry);
            }

            reader.close();
            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ratingEntries;
    }
}
