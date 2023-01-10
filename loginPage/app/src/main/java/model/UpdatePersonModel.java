package model;

import db.PersonDataDB;

public class UpdatePersonModel {


    public static void updateModel(String UID, String fname, String lname, String phone, String email, boolean isS, boolean isT){
        PersonDataDB.updatePersonData(UID,fname,lname,email,phone,isS,isT);
    }
}
