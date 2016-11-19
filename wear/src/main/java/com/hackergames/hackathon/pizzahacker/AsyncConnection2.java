package com.hackergames.hackathon.pizzahacker;

import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by Michael on 19-11-2016.
 */

public class AsyncConnection2  extends AsyncTask<Void, Void, Void> {

    private MainActivity main;
    String from, to;

    public AsyncConnection2(String from, String to, MainActivity main) {
        this.from = from;
        this.to = to;
        this.main = main;
    }

    @Override
    protected Void doInBackground(Void... params) {
        try {
            String urlString = "https://maps.googleapis.com/maps/api/distancematrix/json?units=metric&origins=" + from + "&destinations=" + to + "&key=";
            URL url = new URL(urlString);
            Scanner sc = new Scanner(url.openStream());
            StringBuilder sb = new StringBuilder();
            String line = null;
            while (sc.hasNext())
                sb.append(sc.nextLine());
            String toReturn = sb.toString();
            // build a JSON object
            JSONObject obj = new JSONObject(toReturn);
            JSONObject result = obj.getJSONArray("rows").getJSONObject(0);
            JSONObject elements = result.getJSONArray("elements").getJSONObject(0);
            int duration = elements.getJSONObject("duration").getInt("value");


            //int test = time.getInt("value");
            System.out.println("Expected test: " + duration);
            main.setTimeBetween(duration);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
