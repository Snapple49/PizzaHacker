package com.hackergames.hackathon.pizzahacker;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Scanner;

/**
 * Created by raluca.miclea on 11/19/2016.
 */

public class Auxiliary {


    public static int getTravelTime(String from, String to) throws IOException, JSONException {
        //build URL
        String distancematrixURL = "https://maps.googleapis.com/maps/api/distancematrix/json?units=metric&origins=" + from + "&destinations=" + to + "&key=";
        //distancematrixURL += URLEncoder.encode(addr, "UTF-8");
        URL url = new URL(distancematrixURL);


        // read from the URL
        Scanner scan = new Scanner(url.openStream());
        String str = new String();
        while (scan.hasNext())
            str += scan.nextLine();
        scan.close();

        // build a JSON object
        JSONObject obj = new JSONObject(str);
        if (! obj.getString("status").equals("OK"))
            return 0;

        // get the first result
        JSONObject result = obj.getJSONArray("rows").getJSONObject(0);
        JSONObject time =
                result.getJSONArray("elements").getJSONObject(1);

        int test = time.getInt("value");
        System.out.println(test);
        return test;
    }
}
