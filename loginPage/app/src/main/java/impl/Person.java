package impl;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import db.DataCenterDB;
import interfaces.IPerson;


public class Person implements IPerson {
    protected String uID = "";
    protected String firstName = "";
    protected String lastName = "";
    protected String email = "";
    protected String phoneNumber = "";

    public Person(String firstName, String lastName, String email, String phoneNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.uID = "";
    }

    public Person(String uID, String firstName, String lastName, String email, String phoneNumber) {
        this.uID = uID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public Person() {
    }

    public Person(JsonObject json){
        this.uID=String.valueOf(json.get("uID"));
        this.firstName=String.valueOf(json.get("firstName"));
        this.lastName=String.valueOf(json.get("lastName"));
        this.email=String.valueOf(json.get("email"));
        this.phoneNumber=String.valueOf(json.get("phoneNumber"));
    }


    @Override
    public String getUID() {
        return uID;
    }

    @Override
    public String getFirstName() {
        return firstName;
    }

    @Override
    public String getLastName() {
        return lastName;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public String getPhoneNumber() {
        return phoneNumber;
    }

    @Override
    public void setUID(String uID) {
        this.uID = uID;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public Map<String, Object> getPersonMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("uID", uID);
        map.put("firstName", firstName);
        map.put("lastName", lastName);
        map.put("email", email);
        map.put("phoneNumber", phoneNumber);
        return (map);
    }

    @Override
    public String toString() {
        return "Person{" +
                "uID='" + uID + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return uID.equals(person.uID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uID);
    }


//    public Map<String, Object> getMap() {
//        Map<String, Object> personMap = new HashMap<>();
//        personMap.put("uID", this.uID);
//        personMap.put("firstName", this.firstName);
//        personMap.put("lastName", this.lastName);
//        personMap.put("email", this.email);
//        personMap.put("phoneNumber", this.phoneNumber);
//        return personMap;
//
//    }

    public String toJson(){
        return new Gson().toJson(this);
    }
}
