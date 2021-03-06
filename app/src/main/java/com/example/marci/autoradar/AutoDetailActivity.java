package com.example.marci.autoradar;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;

import entities.Auto;
import models.AutoRestClient;

public class AutoDetailActivity extends AppCompatActivity {

    private Auto auto;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auto_detail);

       // getIntent().getSerializableExtra("AutoS");

        Intent i = getIntent();
        auto = (Auto)i.getSerializableExtra("AutoS");
        //auto.setImage(null);

        TextView toMap = findViewById(R.id.textViewGoToMapDetail);
        toMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AutoDetailActivity.this, MapsActivity.class);
                intent.putExtra("AutoS", auto);


                startActivity((intent));
            }
        });

        TextView title = findViewById(R.id.textViewTitleAutoDetail);
        title.setText(auto.getTitle());

        TextView desc = findViewById(R.id.textViewDescInScroll);
        desc.setText(auto.getDescription());

        TextView price = findViewById(R.id.textViewPriceDetail);
        price.setText("Cena: " + auto.getPrice());

       // String date = new SimpleDateFormat("dd-MM-yyyy").format(auto.getCreatedAt());

       // TextView dataCity = findViewById(R.id.textViewCityDateDetail);
//        if(auto.getUser()!=null){
//            dataCity.setText(auto.getUser().getCity() + " " + date);
//        }else{
//            dataCity.setText("Brak");
//        }

        new AsyncGetData().execute();

//        TextView tel = findViewById(R.id.textViewTelDetail);
//        if(auto.getUser()!=null){
//            tel.setText("Tel: " + auto.getUser().getTel());
//        }else{
//            tel.setText("Brak");
//        }

            new AsyncGetDataTel().execute();

        if(auto.getImage()!=null){
            ImageView image = findViewById(R.id.imageViewAutoDetail);
            Bitmap bitmap = BitmapFactory.decodeByteArray(auto.getImage(), 0, auto.getImage().length);
            image.setImageBitmap(bitmap);
        }

        if (MainActivity.ifUserAutos == true) {
            TextView edit  = findViewById(R.id.textViewEdit);
            edit.setVisibility(TextView.VISIBLE);

            edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(AutoDetailActivity.this, NewAutoActivity.class);
                    intent.putExtra("AutoS",auto );
                    startActivity(intent);
                }
            });
        }


        //Toast.makeText(this, String.valueOf(auto.getIdAuto()), Toast.LENGTH_LONG).show();




    }


    private class AsyncGetData extends AsyncTask<Void, Void,String> {

        @Override
        protected void onPostExecute(String aVoid) {
            super.onPostExecute(aVoid);
            TextView dataCity = findViewById(R.id.textViewCityDateDetail);
            dataCity.setText(aVoid);

        }

        @Override
        protected String doInBackground(Void... voids) {

            AutoRestClient autoRestClient = new AutoRestClient();
            // Auto auto = autoRestClient.find(12L);
            return autoRestClient.getCityAndDateUser(auto.getIdAuto());

        }
    }

    private class AsyncGetDataTel extends AsyncTask<Void, Void,String> {

        @Override
        protected void onPostExecute(String aVoid) {
            super.onPostExecute(aVoid);
            TextView tel = findViewById(R.id.textViewTelDetail);
            tel.setText(aVoid);

        }

        @Override
        protected String doInBackground(Void... voids) {

            AutoRestClient autoRestClient = new AutoRestClient();
            // Auto auto = autoRestClient.find(12L);
            return autoRestClient.getUserTel(auto.getIdAuto());

        }
    }
}
