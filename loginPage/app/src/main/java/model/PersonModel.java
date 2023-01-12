package model;

import db.PersonDataDB;
import impl.Student;
import impl.Tutor;
import interfaces.IPerson;

public class PersonModel {


    public static void updateModel(String UID, String fname, String lname, String phone, String email, boolean isS, boolean isT){
        PersonDataDB.updatePersonData(UID,fname,lname,email,phone,isS,isT);
    }
    public static Tutor getTutor(String uid){
        return PersonDataDB.getTutorFromDB(uid);
    }

    public static Student getStudent(String uid){
        return PersonDataDB.getStudentFromDB(uid);
    }

    public static void setPerson(IPerson person, boolean is_tutor, boolean is_student){
        PersonDataDB.setPersonData(person,is_tutor,is_student);
    }
}
