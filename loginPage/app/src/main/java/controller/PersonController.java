package controller;

import android.widget.CheckBox;

import db.PersonDataDB;
import impl.Student;
import impl.Tutor;
import interfaces.IPerson;
import model.PersonModel;

public class PersonController {


    public static boolean checkPermision(String type, String UID){
        if (type.equals("student")){
            if(PersonDataDB.getStudentFromDB(UID)!=null){
                return true;
            }
            return false;
        }
        else {
            if (PersonDataDB.getTutorFromDB(UID) != null) {
                return true;
            }
            return false;
        }

    }

    public static void addPermision(boolean hasPermission, CheckBox check){
        if(hasPermission){
            check.setChecked(true);
        }
    }

    public static void updateControl(String UID, String fname, String lname, String phone, String email, boolean isS, boolean isT){
        PersonModel.updateModel(UID,fname,lname,email,phone,isS,isT);
    }

    public static Tutor getTutor(String uid){
        return PersonModel.getTutor(uid);
    }

    public static Student getStudent(String uid){
        return PersonModel.getStudent(uid);
    }

    public static void setPerson(IPerson person, boolean is_tutor, boolean is_student){
        PersonModel.setPerson(person,is_tutor,is_student);
    }
}
