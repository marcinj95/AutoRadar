package models;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import entities.Auto;
import entities.User;

/**
 * Created by marci on 13.12.2017.
 */

public class UserRestClient {

    private String BASE_URL="http://192.168.2.14:8080/api/users/";
    private RestTemplate restTemplate = new RestTemplate();

    public User find(Long id){
        try {
            // Log.v("asd", "HUUUI");
            User user =  restTemplate.getForObject(BASE_URL + id, User.class);
//                Log.v("ID", String.valueOf(auto.getIdAuto()));
//            Log.v("MODEL", String.valueOf(auto.getModel()));
            return user;


        } catch (Exception e) {
            //Log.v("asd", "sdaasd");
            return null;


        }
    }

    public List<User> finAll(){

        try {
            return restTemplate.exchange(BASE_URL,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<User>>(){}).getBody();
        } catch (Exception e) {
            return null;
        }


    }

    public void postUser(User user){

//        HttpEntity<User> request = new HttpEntity<>(user);
//        //restTemplate.postForObject(BASE_URL, request,User.class);
//
        restTemplate.postForEntity(BASE_URL, user, User.class);

//        Map<String, String> vars = new HashMap<String, String>();
//        vars.put("id", "JS01");
//
//        try
//        {
//            /*
//                This is code to post and return a user object
//             */
//
//            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
//            restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
//            //String uri = new String("http://" + mRESTServer.getHost() + ":8080/springmvc-resttemplate-test/api/{id}");
//            User u = new User();
//            //u.setName("Johnathan M Smith");
//            //u.setUser("JS01");
//            User returns = restTemplate.postForObject(BASE_URL, u, User.class, vars);
//           // LOGGER.debug("User:  " + u.toString());
//        }
//        catch (HttpClientErrorException e)
//        {
//            e.printStackTrace();
//        }

       // ResponseEntity<User> postResponse = restTemplate.postForEntity(BASE_URL, user, User.class);


    }


}
