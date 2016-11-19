package com.hackergames.hackathon.pizzahacker;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
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
    @Override
    protected void onCreate(Bundle savedInstanceState){
        main = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
                            input.put("VendorID", "0123456789");
                            input.put("OrderID", t.getText());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        AsyncConnection as = new AsyncConnection(input, main);
                    }
                });
                pcl = new PropertyChangeListener() {
                    @Override
                    public void propertyChange(PropertyChangeEvent evt) {
                        
                    }
                }

            }
        });

    }
}
