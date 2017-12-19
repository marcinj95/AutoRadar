package com.example.marci.autoradar;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
        if(auto.getUser()!=null){
            dataCity.setText(auto.getUser().getCity() + " " + date);
        }else{
            dataCity.setText("Brak");
        }


        TextView tel = findViewById(R.id.textViewTelDetail);
        if(auto.getUser()!=null){
            tel.setText("Tel: " + auto.getUser().getTel());
        }else{
            tel.setText("Brak");
        }


        if(auto.getImage()!=null){
            ImageView image = findViewById(R.id.imageViewAutoDetail);
            Bitmap bitmap = BitmapFactory.decodeByteArray(auto.getImage(), 0, auto.getImage().length);
            image.setImageBitmap(bitmap);
        }





    }
}
