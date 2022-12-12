package impl;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import db.DataCenterDB;
import interfaces.IPerson;


public class Person implements IPerson
{
    protected String uID = "";
    protected String firstName;
    protected String lastName;
    protected String email;
    protected String phoneNumber;

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
    public Map<String, Object> getPersonMap()
    {
        Map<String, Object> map = new HashMap<>();
        map.put("uID", uID);
        map.put("firstName", firstName);
        map.put("lastName", lastName);
        map.put("email", email);
        map.put("phoneNumber", phoneNumber);
        return (map);
    }
}