package com.example.marci.autoradar;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import entities.Auto;
import models.AutoRestClient;

public class MyActivity extends AppCompatActivity {

    private Button buttonFind;
    private EditText editTextId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

       // buttonFind = findViewById(R.id.buttonFind);
        buttonFind = findViewById(R.id.buttonFind);
        editTextId = findViewById(R.id.editTextId);

        buttonFind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int id = Integer.parseInt(editTextId.getText().toString());
                new HttpRequestAsk(id).execute();

            }
        });

    }



    private class HttpRequestAsk extends AsyncTask<Void, Void, Auto> {


        private Long id;

        public HttpRequestAsk(int id){
            this.id =Long.valueOf(id);
        }


        @Override
        protected Auto doInBackground(Void... voids) {
            AutoRestClient autoRestClient = new AutoRestClient();
            return autoRestClient.find(id);
        }

        @Override
        protected void onPostExecute(Auto auto) {
            TextView textViewId = findViewById(R.id.textViewId);
            textViewId.setText(String.valueOf(auto.getIdAuto()));

            TextView textViewModel = findViewById(R.id.textViewModel);
            textViewModel.setText(String.valueOf(auto.getModel()));

            TextView textViewCarBrand = findViewById(R.id.textViewCarBrand);
            textViewCarBrand.setText(String.valueOf(auto.getCarBrand()));


        }
    }


}
