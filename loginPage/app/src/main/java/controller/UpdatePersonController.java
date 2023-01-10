package controller;

import android.widget.CheckBox;

import db.PersonDataDB;
import model.UpdatePersonModel;

public class UpdatePersonController {


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
        UpdatePersonModel.updateModel(UID,fname,lname,email,phone,isS,isT);
    }
}
