package com.example.marci.autoradar;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import adapters.AutoListAdapter;
import entities.Auto;
import models.AutoRestClient;

public class AutoListActivity extends AppCompatActivity {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auto_list);
        new HttpRequestAsk().execute();
    }

    private class HttpRequestAsk extends AsyncTask<Void, Void, List<Auto>> {

        @Override
        protected List<Auto> doInBackground(Void... voids) {
            AutoRestClient autoRestClient = new AutoRestClient();
            return autoRestClient.finAll();

        }

        @Override
        protected void onPostExecute(List<Auto> autos) {
            ListView listViewAuto = findViewById(R.id.listViewAuto);
            listViewAuto.setAdapter(new AutoListAdapter(AutoListActivity.this, autos));

        }
    }

}
