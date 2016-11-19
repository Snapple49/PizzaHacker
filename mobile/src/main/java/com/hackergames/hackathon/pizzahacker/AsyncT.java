package com.hackergames.hackathon.pizzahacker;

import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by Michael on 19-11-2016.
 */

public class AsyncT extends AsyncTask<Void, Void, Void> {

    private JSONObject inputJson;
    private MainActivity main;
    public AsyncT(JSONObject response, MainActivity main){
        this.inputJson = response;
        this.main = main;
    }
    @Override
    protected Void doInBackground(Void... params) {
        try{
            URL url = new URL("http://172.168.2.32/");
            HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setRequestProperty("Content-Type", "application/json");
            httpURLConnection.connect();

            DataOutputStream wr = new DataOutputStream(httpURLConnection.getOutputStream());
            wr.writeBytes(inputJson.toString());
            wr.flush();
            wr.close();
            Scanner br = new Scanner(httpURLConnection.getInputStream());
            List<String> response = new ArrayList<String>();
            while(br.hasNext()){
                response.add(br.nextLine());
            }
            int frontIndex = 0, toIndex = 0;
            for(int i = 0; i < response.size(); i++){
                String s = response.get(i);
                if(s.contains("{"))
                    frontIndex = i+1;
                if(s.contains("}"))
                    toIndex = i;
            }
            List<String> reducedResponse = response.subList(frontIndex, toIndex);
            JSONObject responseJson = new JSONObject();
            for(String s : reducedResponse){
                String[] split = s.split(":");
                responseJson.put(split[0].trim(), split[1].trim());
            }
            main.response(responseJson);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
