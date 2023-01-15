package impl;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

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

    public Tutor(JsonObject json){
        this.uID=String.valueOf(json.get("uID"));
        this.firstName=String.valueOf(json.get("firstName"));
        this.lastName=String.valueOf(json.get("lastName"));
        this.email=String.valueOf(json.get("email"));
        this.phoneNumber=String.valueOf(json.get("phoneNumber"));
    }

//    public Map<String, Object> getMyMap(){
//        Map<String, Object> tutorMap= new HashMap<>();
//        tutorMap.put("uID", uID);
//        tutorMap.put("firstName", firstName);
//        tutorMap.put("lastName", lastName);
//        tutorMap.put("email", email);
//        tutorMap.put("phoneNumber", phoneNumber);
//        return tutorMap;
//    }

    public String toJson(){
        return new Gson().toJson(this);
    }


}
