package impl;
import java.lang.reflect.Field;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import db.DataCenterDB;
import interfaces.IPerson;


public class Person implements IPerson {
    protected String uID;
    protected String firstName;
    protected String lastName;
    protected String email;
    protected String phoneNumber;


    public Person(String firstName, String lastName, String email, String phoneNumber, String isStudent, String isTutor) {
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

    public String getUID() {
        return uID;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }



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



    public Map<String, Object> getPersonMap()
    {
        Map<String, Object> map = new HashMap<>();
        // Use MyObject.class.getFields() instead of getDeclaredFields()
        // If you are interested in public fields only
        for (Field field : DataCenterDB.class.getDeclaredFields()) {
            // Skip this if you intend to access to public fields only
            try {
                map.put(field.getName(), field.get(this));
            } catch (IllegalAccessException e) {
            }
        }
        return (map);
    }
}

