package models;

import android.location.Address;
import android.util.Log;

import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.*;
import org.springframework.core.*;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import entities.*;

public class AutoRestClient {

   // private String BASE_URL="http://192.168.2.14:8080/api/autos/";
   private String BASE_URL="http://192.168.100.10:8080/api/autos/";
    private RestTemplate restTemplate = new RestTemplate();

    public Auto find(Long id){
        try {

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


        ResponseEntity<List<Auto>> rateResponse =
                restTemplate.exchange(BASE_URL,
                        HttpMethod.GET, null, new ParameterizedTypeReference<List<Auto>>() {
                        });
        List<Auto> rates = rateResponse.getBody();

        Long i;
        String url="http://192.168.100.10:8080/api/autos/user/";
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

    public List<Auto> finAllNoUser(){


        ResponseEntity<List<Auto>> rateResponse =
                restTemplate.exchange(BASE_URL,
                        HttpMethod.GET, null, new ParameterizedTypeReference<List<Auto>>() {
                        });
        List<Auto> rates = rateResponse.getBody();

//        Long i;
//        String url="http://192.168.2.14:8080/api/autos/user/";
//        for(Auto rate : rates)
//        {
//            i=rate.getIdAuto();
//            User user =  restTemplate.getForObject(url + i, User.class);
//            if(user != null)
//            {
//                rate.setUser(user);
//            }
//
//        }

        return rates;



    }

    public String getCityAndDateUser(Long id){

        String url="http://192.168.100.10:8080/api/autos/getCityAndDateUser/" + String.valueOf(id);

        String s = restTemplate.getForObject(url, String.class);


        return s;



    }

    public String getUserTel(Long id){

        String url="http://192.168.100.10:8080/api/autos/getUserTel/" + String.valueOf(id);

        String s = restTemplate.getForObject(url, String.class);


        return s;



    }

    public List<Auto> findAllByUser(User user2){

        ResponseEntity<List<Auto>> rateResponse =
                restTemplate.exchange(BASE_URL + "autolistuser/" + user2.getIdUser(),
                        HttpMethod.GET, null, new ParameterizedTypeReference<List<Auto>>() {
                        });
        List<Auto> rates = rateResponse.getBody();

//        Long i;
//        String url="http://192.168.100.10:8080/api/autos/user/";
//        for(Auto rate : rates)
//        {
//            i=rate.getIdAuto();
//            User user =  restTemplate.getForObject(url + i, User.class);
//            if(user != null)
//            {
//                rate.setUser(user);
//            }
//
//        }

        return rates;

    }

    public void postAuto(Auto auto){

        restTemplate.postForEntity(BASE_URL, auto, Auto.class);



    }

    public void updateAuto(Auto auto){

        Map<String,Long> map = new HashMap<>();
        map.put("idAuto", auto.getIdAuto());
        //map.put("name", "Ram");

        restTemplate.put(BASE_URL+ auto.getIdAuto().toString(), auto, map);

//        restTemplate.put();



    }






}
