package models;

import android.util.Log;

import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.*;
import org.springframework.core.*;


import java.util.List;

import entities.*;

public class AutoRestClient {

    private String BASE_URL="http://192.168.2.14:8080/api/autos/";
    private RestTemplate restTemplate = new RestTemplate();

    public Auto find(Long id){
        try {
           // Log.v("asd", "HUUUI");
                Auto auto =  restTemplate.getForObject(BASE_URL + id, Auto.class);
//                Log.v("ID", String.valueOf(auto.getIdAuto()));
//            Log.v("MODEL", String.valueOf(auto.getModel()));
            return auto;


        } catch (Exception e) {
            //Log.v("asd", "sdaasd");
            return null;


        }
    }

    public List<Auto> finAll(){

//        try {
//                return restTemplate.exchange(BASE_URL,
//                        HttpMethod.GET,
//                        null,
//                        new ParameterizedTypeReference<List<Auto>>(){}).getBody();
//        } catch (Exception e) {
//            return null;
//        }

//        ResponseEntity<Auto[]> responseEntity = restTemplate.getForEntity(BASE_URL, Auto[].class);
//
//        Auto[] objects = responseEntity.getBody();
//
//        MediaType contentType = responseEntity.getHeaders().getContentType();
//        HttpStatus statusCode = responseEntity.getStatusCode();
//
//        return responseEntity;

        ResponseEntity<List<Auto>> rateResponse =
                restTemplate.exchange(BASE_URL,
                        HttpMethod.GET, null, new ParameterizedTypeReference<List<Auto>>() {
                        });
        List<Auto> rates = rateResponse.getBody();

        Long i;
        String url="http://192.168.2.14:8080/api/autos/user/";
        for(Auto rate : rates)
        {
            i=rate.getIdAuto();
            User user =  restTemplate.getForObject(url + i, User.class);
            if(user != null)
            {
                rate.setUser(user);
            }

        }

        return rates;



    }






}
