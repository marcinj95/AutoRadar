package com.example.marci.autoradar;

import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import entities.Auto;
import entities.User;
import models.UserRestClient;

public class RegisterActivity extends AppCompatActivity {

    public static User newUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);



        final EditText name = findViewById(R.id.editTextName);
        final EditText city = findViewById(R.id.editTextCity);
        final  EditText adres = findViewById(R.id.editTextAdres);
        final EditText tel = findViewById(R.id.editTextTel);
        final  EditText pass = findViewById(R.id.editTextPass);
        final  EditText email = findViewById(R.id.editTextEmail);
        final  Button button = findViewById(R.id.buttonRegReg);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(tel.getText().toString().length() == 9)
                {
                    if(email.getText().toString().contains("@"))
                    {
                        User user = new User();

            //user.setIdUser(16L);
            user.setAdress(adres.getText().toString());
            user.setCity(city.getText().toString());
            user.setEmail(email.getText().toString());
            user.setName(name.getText().toString());
            user.setPassword(pass.getText().toString());
            user.setTel(Integer.parseInt(tel.getText().toString()));
            newUser = user;

            new HttpRequestAsk().execute();


//                        RestTemplate restTemplate = new RestTemplate();
//
                        //restTemplate.postForObject("http://192.168.2.14:8080/api/users/", user, User.class);
////////////////////////////////////////////////////////////////
//                        User map = new User();
//
//                        HttpHeaders headers = new HttpHeaders();
//                        headers.setContentType(MediaType.APPLICATION_JSON);
//
//                        final HttpEntity<User> entity = new HttpEntity<User>(map,headers);
//                        JSONObject jsonObject = null;
//
//                        try{
//                            RestTemplate restTemplate = new RestTemplate();
//                            //HttpEntity<User> request = new HttpEntity<>(new User());
//                            ResponseEntity<String> response = restTemplate.exchange("http://192.168.2.14:8080/api/users/",
//                                    HttpMethod.POST, entity, String.class);
//
//                            if (response.getStatusCode() == HttpStatus.CREATED) {
//                                try {
//                                    jsonObject = new JSONObject(response.getBody());
//                                } catch (JSONException e) {
//                                    throw new RuntimeException("JSONException occurred");
//                                }
//                            }
//
//                        }catch (Exception e){
//                            e.printStackTrace();
//                        }

                        ///////////////////////////////////////////////////////////////////

//                        RestTemplate restTemplate = new RestTemplate();
//                        HttpEntity<User> request = new HttpEntity<>(new User());
//                        ResponseEntity<User> response = restTemplate
//                                .exchange("http://192.168.2.14:8080/api/users/", HttpMethod.POST, request, User.class);

                       // assertThat(response.getStatusCode(), is(HttpStatus.CREATED));

//                        try {
//                            // JSON here
//                        }
//                        catch (Exception e) {
//                            // TODO Auto-generated catch block
//                            e.printStackTrace();
//                        }

                    }else
                    {
                        Toast.makeText(RegisterActivity.this, "Niepoprawny adres email", Toast.LENGTH_SHORT).show();
                    }

                }
                else
                {
                    Toast.makeText(RegisterActivity.this, "format telefonu : XXXYYYZZZ", Toast.LENGTH_SHORT).show();
                }

            }
        });






    }



    private class HttpRequestAsk extends AsyncTask<Void, Void, User> {

        @Override
        protected User doInBackground(Void... params) {
            //UserRestClient userRestClient = new UserRestClient();
           // return userRestClient.finAll();
            return newUser;


        }

        @Override
        protected void onPostExecute(User user) {
            //listUser = users;
            //Toast.makeText(LoginActivity.this, String.valueOf(listUser.get(0).getAutos().size()), Toast.LENGTH_LONG).show();

            UserRestClient userRestClient = new UserRestClient();
            userRestClient.postUser(user);
            Toast.makeText(RegisterActivity.this, "Pomyślnie zarejestrowano!", Toast.LENGTH_LONG).show();
            finish();
        }
    }
}
