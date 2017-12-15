package com.example.marci.autoradar;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.text.SimpleDateFormat;

import entities.Auto;

public class AutoDetailActivity extends AppCompatActivity {

    private Auto auto;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auto_detail);

       // getIntent().getSerializableExtra("AutoS");

        Intent i = getIntent();
        auto = (Auto)i.getSerializableExtra("AutoS");

        TextView title = findViewById(R.id.textViewTitleAutoDetail);
        title.setText(auto.getTitle());

        TextView desc = findViewById(R.id.textViewDescInScroll);
        desc.setText(auto.getDescription());

        TextView price = findViewById(R.id.textViewPriceDetail);
        price.setText("Cena: " + auto.getPrice());

        String date = new SimpleDateFormat("dd-MM-yyyy").format(auto.getCreatedAt());

        TextView dataCity = findViewById(R.id.textViewCityDateDetail);
        dataCity.setText(auto.getUser().getCity() + " " + date);

        TextView tel = findViewById(R.id.textViewTelDetail);
        tel.setText("Tel: " + auto.getUser().getTel());
    }
}
