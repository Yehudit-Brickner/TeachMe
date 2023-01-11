package impl;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Tutor extends Person
{
    private ArrayList<Lesson> lessons = new ArrayList<>();

    public Tutor(String uID, String firstName, String lastName, String email, String phoneNumber) {
        super(uID, firstName, lastName, email, phoneNumber);
    }

    public Tutor()
    {

    }

    public Map<String, Object> getMap(){
        Map<String, Object> tutorMap= new HashMap<>();
        tutorMap.put("uID", uID);
        tutorMap.put("firstName", firstName);
        tutorMap.put("lastName", lastName);
        tutorMap.put("email", email);
        tutorMap.put("phoneNumber", phoneNumber);
        return tutorMap;
    }

    public String toJson(){
        return new Gson().toJson(this);
    }
}
