package com.shakibcsekuet.asynctask;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings.System;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.view.View.OnClickListener;

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
        // detect the view that was "clicked"
        switch (view.getId()) {
            case R.id.button:
                new Mytask().execute("");
                break;
        }
    }

    private class Mytask extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {
            for (int i = 0; i < 5; i++) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Thread.interrupted();
                }
                publishProgress(i+"");

            }
            return "Executed";
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