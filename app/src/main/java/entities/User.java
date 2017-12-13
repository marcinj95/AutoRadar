package entities;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by marci on 12.12.2017.
 */

public class User implements Serializable {

    public static User mUser;
    private Long idUser;
    private String name;
    private String city;
    private String email;
    private String adress;
    private String password;
    private int tel;
    private List<Auto> autos;

    public List<Auto> getAutos() {
        return autos;
    }

    public void setAutos(List<Auto> autos) {
        this.autos = autos;
    }

    public Long getIdUser() {
        return idUser;
    }

    public void setIdUser(Long idUser) {
        this.idUser = idUser;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getTel() {
        return tel;
    }

    public void setTel(int tel) {
        this.tel = tel;
    }

//    public Set<Auto> getAutos() {
//        return autos;
//    }
//
//    public void setAutos(Set<Auto> autos) {
//        this.autos = autos;
//    }
}
