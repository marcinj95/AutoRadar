package models;

/**
 * Created by marci on 08.12.2017.
 */

import android.util.Log;

import org.springframework.http.HttpMethod;
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

        try {
                return restTemplate.exchange(BASE_URL,
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<List<Auto>>(){}).getBody();
        } catch (Exception e) {
            return null;
        }


    }






}
