package com.hackergames.hackathon.pizzahacker;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.wearable.view.WatchViewStub;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.content.Context;
import android.os.Bundle;
import android.support.wearable.view.WatchViewStub;
import android.view.View;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class MainActivity extends Activity {

    private TextView mTextView;
    private JSONObject responseFromServer;
    private PropertyChangeListener pcl;
    MainActivity main;
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    @Override
    protected void onCreate(Bundle savedInstanceState){
        main = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        int permission = ActivityCompat.checkSelfPermission(main, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        responseFromServer = new JSONObject();
        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    main,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
        final WatchViewStub stub = (WatchViewStub) findViewById(R.id.watch_view_stub);
        stub.setOnLayoutInflatedListener(new WatchViewStub.OnLayoutInflatedListener() {
            @Override
            public void onLayoutInflated(WatchViewStub stub) {
                mTextView = (TextView) stub.findViewById(R.id.text);

                Button button = (Button)findViewById(R.id.codeAcceptButton);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EditText t = (EditText)findViewById(R.id.inputCode);
                        JSONObject input = new JSONObject();
                        try {
                            input.put("CountryCode", "NL");
                            input.put("VendorId", "36040d87-684e-4aee-9e03-3f4e17010b26");
                            input.put("OrderId", "9ccf5677-205a-4fb6-8262-c57b398936c0");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        AsyncConnection as = new AsyncConnection(input, main);
                        as.execute();
                        TextView tv = (TextView) findViewById(R.id.jsonResponseField);
                        if(responseFromServer.has("\"OrderStatus\"")) {
                            try {
                                String orderStatus = responseFromServer.get("\"OrderStatus\"").toString().replaceAll("\"","").replaceAll(",","");
                                tv.setText(orderStatus);
                                //CHECK THE STATUS CODE AND ACT ACCORDINGLY!
                                if(orderStatus.equals("820")){
                                    //Start cookingactivity
                                    Intent i = new Intent(MainActivity.this, CookingActivity.class);
                                    startActivity(i);
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                    }
                });
            }
        });
    }

    public void setResponseJson(JSONObject response){
        this.responseFromServer = response;
        System.out.println(response.toString());
        Button button = (Button)findViewById(R.id.codeAcceptButton);
    }
}
