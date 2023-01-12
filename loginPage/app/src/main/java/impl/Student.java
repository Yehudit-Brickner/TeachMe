package impl;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.util.HashMap;
import java.util.Map;

public class Student extends Person
{
    public Student()
    {

    }

    public Student(String uID, String firstName, String lastName, String email, String phoneNumber) {
        super(uID, firstName, lastName, email, phoneNumber);
    }

    public Student(JsonObject json){
        this.uID=String.valueOf(json.get("uID"));
        this.firstName=String.valueOf(json.get("firstName"));
        this.lastName=String.valueOf(json.get("lastName"));
        this.email=String.valueOf(json.get("email"));
        this.phoneNumber=String.valueOf(json.get("phoneNumber"));
    }

//    public Map<String, Object> getMyMap(){
//        Map<String, Object> studentMap= new HashMap<>();
//        studentMap.put("uID", uID);
//        studentMap.put("firstName", firstName);
//        studentMap.put("lastName", lastName);
//        studentMap.put("email", email);
//        studentMap.put("phoneNumber", phoneNumber);
//        return studentMap;
//    }

    public String toJson(){
        return new Gson().toJson(this);
    }
}
