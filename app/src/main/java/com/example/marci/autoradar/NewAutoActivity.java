package com.example.marci.autoradar;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import entities.Auto;
import entities.User;
import models.AutoRestClient;

public class NewAutoActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private String carModel;
    private String carBrand;
//    private String description;
//    private  int productionYear;
//    private int price;
    private String title;
    //private int telNumber;
//    private String adress;
    private byte[] image;


    private User mUser;
    private Auto mAuto;



    private Uri file;
    Spinner spinnerCarModel;
    ImageView imageView;
    ProgressBar progressBar;
    ScrollView mainScrollView;




    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 0) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                //takePictureButton.setEnabled(true);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_auto);


        if(MainActivity.ifUserAutos == false) {
            Intent i = getIntent();
            mUser = (User) i.getSerializableExtra("UserS");

            Spinner spinnerCarBrand = findViewById(R.id.spinnerCarBrand);
            spinnerCarBrand.setOnItemSelectedListener(this);

            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                    R.array.carBrand_array, android.R.layout.simple_spinner_item);

            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerCarBrand.setAdapter(adapter);

            progressBar = findViewById(R.id.progressBar2);
            progressBar.setVisibility(View.GONE);

            mainScrollView = findViewById(R.id.newAutoMainScrollview);


            spinnerCarModel = findViewById(R.id.spinnerModel);
            spinnerCarModel.setVisibility(View.GONE);
            spinnerCarModel.setOnItemSelectedListener(this);
// Create an ArrayAdapter using the string array and a default spinner layout

            final EditText tytul = findViewById(R.id.editTextNewTitle);
            final EditText rocznik = findViewById(R.id.editTextNewYear);
            final EditText cena = findViewById(R.id.editTextNewPrice);
            final EditText opis = findViewById(R.id.editTextNewAutoDesc);
            // EditText tel = findViewById(R.id.editTextNewTel);
            final EditText adres = findViewById(R.id.editTextNewAdress);


            Button button = findViewById(R.id.buttonNewAddNewAuto);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    if (mUser != null && image != null) {
                        Auto auto = new Auto();
                        auto.setTitle(tytul.getText().toString());
                        auto.setIdPhoto(50);
                        auto.setIdEquipment(50);
                        auto.setProductionYear(Integer.parseInt(rocznik.getText().toString()));
                        auto.setPrice(Integer.parseInt(cena.getText().toString()));
                        auto.setDescription(opis.getText().toString());
                        auto.setCarBrand(carBrand);
                        auto.setModel(carModel);
                        auto.setAdres(adres.getText().toString());
                        auto.setUser(mUser);
                        auto.setImage(image);

                        new AddAutoAsync().execute(auto);
                    } else {
                        Toast.makeText(NewAutoActivity.this, "Coś poszło nie tak !", Toast.LENGTH_SHORT).show();
                    }


                }
            });

            // OBSLUGA KAMERY

            imageView = findViewById(R.id.imageViewAddPhoto);

            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                // takePictureButton.setEnabled(false);
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
            }


            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    takePicture();


                    //Toast.makeText(NewAutoActivity.this, file.getEncodedPath(), Toast.LENGTH_LONG).show();
                }
            });









        }else{

            Intent i = getIntent();
            mAuto = (Auto) i.getSerializableExtra("AutoS");

            Spinner spinnerCarBrand = findViewById(R.id.spinnerCarBrand);
            spinnerCarModel = findViewById(R.id.spinnerModel);

            spinnerCarBrand.setVisibility(Spinner.GONE);
            spinnerCarModel.setVisibility(Spinner.GONE);

            progressBar = findViewById(R.id.progressBar2);
            progressBar.setVisibility(View.GONE);

            mainScrollView = findViewById(R.id.newAutoMainScrollview);

            final EditText tytul = findViewById(R.id.editTextNewTitle);
            final EditText rocznik = findViewById(R.id.editTextNewYear);
            final EditText cena = findViewById(R.id.editTextNewPrice);
            final EditText opis = findViewById(R.id.editTextNewAutoDesc);
            // EditText tel = findViewById(R.id.editTextNewTel);
            final EditText adres = findViewById(R.id.editTextNewAdress);
            imageView = findViewById(R.id.imageViewAddPhoto);

            if(mAuto!=null){
                tytul.setText(mAuto.getTitle());
                rocznik.setText(String.valueOf(mAuto.getProductionYear()));
                cena.setText(String.valueOf(mAuto.getPrice()));
                opis.setText(mAuto.getDescription());
                adres.setText(mAuto.getAdres());

                Bitmap bitmap = BitmapFactory.decodeByteArray(mAuto.getImage(), 0, mAuto.getImage().length);

                image = mAuto.getImage();

                imageView.getLayoutParams().height = LinearLayout.LayoutParams.WRAP_CONTENT;
                imageView.getLayoutParams().width = LinearLayout.LayoutParams.WRAP_CONTENT;
                imageView.requestLayout();

                imageView.setImageBitmap(bitmap);

            }



            Button button = findViewById(R.id.buttonNewAddNewAuto);
            button.setText("Aktualizuj!");
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    if (mAuto!=null) {
                        Auto auto = new Auto();
                        auto.setIdAuto(mAuto.getIdAuto());
                        auto.setCreatedAt(mAuto.getCreatedAt());
                        auto.setTitle(tytul.getText().toString());
                        auto.setIdPhoto(50);
                        auto.setIdEquipment(50);
                        auto.setProductionYear(Integer.parseInt(rocznik.getText().toString()));
                        auto.setPrice(Integer.parseInt(cena.getText().toString()));
                        auto.setDescription(opis.getText().toString());
                        auto.setCarBrand(mAuto.getCarBrand());
                        auto.setModel(mAuto.getModel());
                        auto.setAdres(adres.getText().toString());
                        auto.setUser(mUser);
                        auto.setImage(image);
                        auto.setUser(mAuto.getUser());

                        new UpdateAutoAsync().execute(auto);
                    } else {
                        Toast.makeText(NewAutoActivity.this, "Coś poszło nie tak !", Toast.LENGTH_SHORT).show();
                    }


                }
            });




            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                // takePictureButton.setEnabled(false);
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
            }


            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    takePicture();


                    //Toast.makeText(NewAutoActivity.this, file.getEncodedPath(), Toast.LENGTH_LONG).show();
                }
            });



        }
    }

    public void takePicture() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        file = Uri.fromFile(getOutputMediaFile());
        intent.putExtra(MediaStore.EXTRA_OUTPUT, file);

        startActivityForResult(intent, 100);
    }

    private static File getOutputMediaFile(){
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "CameraDemo");

        if (!mediaStorageDir.exists()){
            if (!mediaStorageDir.mkdirs()){
                Log.d("CameraDemo", "failed to create directory");
                return null;
            }
        }

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        return new File(mediaStorageDir.getPath() + File.separator +
                "IMG_"+ timeStamp + ".jpg");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 100) {
            if (resultCode == RESULT_OK) {
                imageView.setImageURI(file);

                imageView.getLayoutParams().height = LinearLayout.LayoutParams.WRAP_CONTENT;
                imageView.getLayoutParams().width = LinearLayout.LayoutParams.WRAP_CONTENT;
                imageView.requestLayout();

                Bitmap src= BitmapFactory.decodeFile(file.getEncodedPath());

                //Bitmap bitmap1 = Bitmap.createScaledBitmap(src,586,271,false );
                Bitmap bitmap1 = Bitmap.createScaledBitmap(src,1280,720,false );

                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap1.compress(Bitmap.CompressFormat.WEBP, 90, baos);
                image = baos.toByteArray();
               // new MyTask().execute();


//                Bitmap bitmap = BitmapFactory.decodeByteArray(image , 0, image.length);
//
//                imageView.setImageBitmap(bitmap);
            }
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        // An item was selected. You can retrieve the selected item using
        // parent.getItemAtPosition(pos)

        //Spinner spinner = (Spinner) adapterView;
        //adapterView.getItemAtPosition(i);

        switch (adapterView.getId()){
            case R.id.spinnerCarBrand:

                carBrand = adapterView.getItemAtPosition(i).toString();
//                ifBrandChoosen = true;
//                ktoreAuto = i;
                getModel(i);
                spinnerCarModel.setVisibility(View.VISIBLE);




                break;


            case R.id.spinnerModel:

                carModel= adapterView.getItemAtPosition(i).toString();
                Toast.makeText(NewAutoActivity.this, carBrand + " "  + carModel, Toast.LENGTH_SHORT).show();

                break;
        }




        //Toast.makeText(this, adapterView.getItemAtPosition(i).toString(), Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }


    // Here is the AsyncTask class:
    //
    // AsyncTask<Params, Progress, Result>.
    //    Params – the type (Object/primitive) you pass to the AsyncTask from .execute()
    //    Progress – the type that gets passed to onProgressUpdate()
    //    Result – the type returns from doInBackground()
    // Any of them can be String, Integer, Void, etc.

    private class MyTask extends AsyncTask<Void, Integer, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progressBar.setVisibility(View.GONE);
//            boolean czy = false;
//            if(image!=null) czy = true;

            //Toast.makeText(NewAutoActivity.this, String.valueOf(image.length), Toast.LENGTH_LONG).show();
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            //progressStatus += 10;
            //progressBar.setProgress(values[0]);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            Bitmap src= BitmapFactory.decodeFile(file.getEncodedPath());

            Bitmap bitmap1 = Bitmap.createScaledBitmap(src,586,271,false );

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap1.compress(Bitmap.CompressFormat.WEBP, 60, baos);
            image = baos.toByteArray();

            return null;
        }
    }

    private class AddAutoAsync extends  AsyncTask<Auto, Integer, Void>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
            mainScrollView.fullScroll(ScrollView.FOCUS_UP);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            progressBar.setVisibility(View.GONE);
            Toast.makeText(NewAutoActivity.this, "Dodałeś nowe auto !", Toast.LENGTH_LONG).show();
            finish();

        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            progressBar.setProgress(values[0]);
        }

        @Override
        protected Void doInBackground(Auto... autos) {

           // Auto auto = autos[0];

            AutoRestClient autoRestClient = new AutoRestClient();
            if(autos[0]!=null && image!=null){
                autoRestClient.postAuto(autos[0]);
            }else {
                Toast.makeText(NewAutoActivity.this, "Nie dodałeś zdjęcia !", Toast.LENGTH_SHORT).show();
            }



            return null;
        }
    }


    private class UpdateAutoAsync extends  AsyncTask<Auto, Integer, Void>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
            mainScrollView.fullScroll(ScrollView.FOCUS_UP);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            progressBar.setVisibility(View.GONE);
            Toast.makeText(NewAutoActivity.this, "Zaktualizowano !", Toast.LENGTH_LONG).show();
            finish();

        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            progressBar.setProgress(values[0]);
        }

        @Override
        protected Void doInBackground(Auto... autos) {

            // Auto auto = autos[0];

            AutoRestClient autoRestClient = new AutoRestClient();
            if(autos[0]!=null ){
                autoRestClient.updateAuto(autos[0]);
            }else {
                Toast.makeText(NewAutoActivity.this, "Nie dodałeś zdjęcia !", Toast.LENGTH_SHORT).show();
            }



            return null;
        }
    }


    public void getModel(int auto){
        List<String> list = new ArrayList<>();


        Resources res = getResources();
        TypedArray ta = res.obtainTypedArray(R.array.Marki);
        int n = ta.length();
        String[][] array = new String[n][];
        for (int i = 0; i < n; ++i) {
            int id = ta.getResourceId(i, 0);
            if (id > 0) {
                array[i] = res.getStringArray(id);
               // list.add(array[n-1][i]);
            } else {
                // something wrong with the XML
            }
        }
        ta.recycle(); // Important!

        for(int x=0; x<array[auto].length; x++){
            list.add(array[auto][x]);
        }


        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, list);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinnerCarModel.setAdapter(dataAdapter);

        //Toast.makeText(this, String.valueOf(list.get(1)), Toast.LENGTH_SHORT).show();










    }
}
