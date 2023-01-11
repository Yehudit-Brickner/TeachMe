package impl;

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

    public Map<String, Object> getMap(){
        Map<String, Object> studentMap= new HashMap<>();
        studentMap.put("uID", uID);
        studentMap.put("firstName", firstName);
        studentMap.put("lastName", lastName);
        studentMap.put("email", email);
        studentMap.put("phoneNumber", phoneNumber);
        return studentMap;
    }
}
