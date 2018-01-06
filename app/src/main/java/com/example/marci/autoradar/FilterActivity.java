package com.example.marci.autoradar;

import android.content.Intent;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import adapters.AutoListAdapaterRecycler;
import entities.Auto;
import models.AutoRestClient;

public class FilterActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private String carModel;
    private String carBrand;
    Spinner spinnerCarModel;
    Spinner spinnerCarBrand;


    String phrase1,carModel1,carBrand1;
    String rocznikFrom1,rocznikTo1,cenaFrom1,cenaTo1;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);



        spinnerCarBrand = findViewById(R.id.spinnerFilterCarBrand);
        spinnerCarBrand.setOnItemSelectedListener(this);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.carBrand_array, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCarBrand.setAdapter(adapter);




        spinnerCarModel = findViewById(R.id.spinnerFilterModel);
        spinnerCarModel.setVisibility(View.GONE);
        spinnerCarModel.setEnabled(false);
        spinnerCarModel.setClickable(false);
        spinnerCarModel.setOnItemSelectedListener(this);
// Create an ArrayAdapter using the string array and a default spinner layout

        final EditText phrase = findViewById(R.id.editTextFilterSomething);

        final EditText rocznikFrom = findViewById(R.id.editTextFilterFromYear);
        final EditText rocznikTo = findViewById(R.id.editTextFilterToYear);

        final EditText cenaFrom = findViewById(R.id.editTextFilterFromPrice);
        final EditText cenaTo = findViewById(R.id.editTextFilterToPrice);







        Button button = findViewById(R.id.buttonFilterSearch);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                phrase1 = phrase.getText().toString();
                carBrand1 = carBrand;
                if(carBrand1 != "Dowolna") {
                    carModel1 = carModel;
                }else {
                    carModel1=null;
                }

                rocznikFrom1 = (rocznikFrom.getText().toString());
                rocznikTo1 = (rocznikTo.getText().toString());
                cenaFrom1 = cenaFrom.getText().toString();
                cenaTo1 = (cenaTo.getText().toString());

                if(phrase1.isEmpty()) phrase1= "null1";
                if(carModel1 == null) carModel1 = "null1";
                if(rocznikFrom1.isEmpty()) rocznikFrom1 = "null1";
                if(rocznikTo1.isEmpty()) rocznikTo1 = "null1";
                if(cenaFrom1.isEmpty()) cenaFrom1 = "null1";
                if(cenaTo1.isEmpty()) cenaTo1 = "null1";



//                Toast.makeText(FilterActivity.this, phrase1 + "  " + carModel1 + " " + carBrand1
//                        + " :: " + rocznikFrom1+ "-" + rocznikTo1, Toast.LENGTH_LONG).show();

                //Toast.makeText(FilterActivity.this, String.valueOf(a), Toast.LENGTH_LONG).show();

//                AutoRestClient autoRestClient = new AutoRestClient();
//                autoRestClient.searchFor(phrase1,carBrand1,carModel1,rocznikFrom1,rocznikTo1,cenaFrom1,cenaTo1);

                new HttpRequestAsk().execute();











            }
        });





    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        switch (parent.getId()){
            case R.id.spinnerFilterCarBrand:

                carBrand = parent.getItemAtPosition(position).toString();
//                ifBrandChoosen = true;
//                ktoreAuto = i;
                //Toast.makeText(FilterActivity.this, "Position: " + position, Toast.LENGTH_SHORT).show();
                getModel(position);
                if(position!=0) {
                    spinnerCarModel.setEnabled(true);
                    spinnerCarModel.setVisibility(View.VISIBLE);
                    spinnerCarModel.setClickable(true);
                }else{
                    spinnerCarModel.setEnabled(false);
                    spinnerCarModel.setVisibility(View.GONE);
                    spinnerCarModel.setClickable(false);
                    carModel = null;
                }
                //Toast.makeText(FilterActivity.this, carBrand + " "  + carModel, Toast.LENGTH_SHORT).show();





                break;


            case R.id.spinnerFilterModel:

                if(spinnerCarModel.isEnabled()){
                    carModel= parent.getItemAtPosition(position).toString();
                   // Toast.makeText(FilterActivity.this, carBrand + " "  + carModel, Toast.LENGTH_SHORT).show();
                }



                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    public void getModel(int auto){
        List<String> list = new ArrayList<>();

        if(auto!=0) {


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

            for (int x = 0; x < array[auto].length; x++) {
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



    private class HttpRequestAsk extends AsyncTask<Void, Void, List<Auto>> {



        @Override
        protected List<Auto> doInBackground(Void... voids) {
            AutoRestClient autoRestClient = new AutoRestClient();
           return autoRestClient.searchFor(phrase1,carBrand1,carModel1,rocznikFrom1,rocznikTo1,cenaFrom1,cenaTo1);


            // return autoRestClient.finAllNoUser();

        }

        @Override
        protected void onPostExecute(List<Auto> autos) {
            super.onPostExecute(autos);

            Intent intent = new Intent();

            ArrayList<Auto> nowy = new ArrayList<>();
            nowy.addAll(autos);


            Bundle b = new Bundle();
            b.putSerializable("autos", nowy);
            intent.putExtras(b);


            setResult(RESULT_OK, intent);
            finish();




        }
    }
}
