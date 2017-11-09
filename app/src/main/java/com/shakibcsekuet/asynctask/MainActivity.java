package com.shakibcsekuet.asynctask;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings.System;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.view.View.OnClickListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends Activity implements OnClickListener {

    Button btn;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn = (Button) findViewById(R.id.button);
        // because we implement OnClickListener we only have to pass "this"
        // (much easier)
        btn.setOnClickListener(this);
    }

    public void onClick(View view) {
        String str="https://api.androidhive.info/contacts/";
        // detect the view that was "clicked"
        Mytask mytask=new Mytask();
        mytask.execute(str);
    }

    private class Mytask extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {
            String jsonfeed="";
            URL url= null;
            try {
                url = new URL(params[0]);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            HttpsURLConnection conn= null;
            try {
                conn = (HttpsURLConnection) url.openConnection();
            } catch (IOException e) {
                e.printStackTrace();
            }
            InputStream is=null;
            try {
                 is=conn.getInputStream();
            } catch (IOException e) {
                e.printStackTrace();
            }
            BufferedReader br= null;
            try {
                br = new BufferedReader(new InputStreamReader(url.openStream()));
            } catch (IOException e) {
                e.printStackTrace();
            }
            String line="";
            while(line!=null){
                try {
                    line=br.readLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                jsonfeed+=line;
            }
            JSONObject job=new JSONObject();
            JSONArray jarr = null;
            try {
                 jarr=job.getJSONArray("contacts");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            JSONObject job2= new JSONObject();
            try {
                job2 = jarr.getJSONObject(0);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            String name="";
            try {
                name=job2.getString("name");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            JSONObject job3= null;
            try {
                job3 = job2.getJSONObject("phone");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            String mob="";
            try {
                mob=job3.getString("mobile");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            String s=name+" "+mob;
            return s;
        }

        @Override
        protected void onPostExecute(String result) {
            TextView txt = (TextView) findViewById(R.id.textView);
            txt.setText(result);

        }

        @Override
        protected void onPreExecute() {}

        @Override
        protected void onProgressUpdate(String... str) {
            TextView txt = (TextView) findViewById(R.id.textView);
            txt.setText(str[0]);
        }
    }
}