package controller;

import android.widget.Switch;

import com.google.firebase.Timestamp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import db.PersonDataDB;
import impl.Lesson;
import impl.Meeting;
import impl.Tutor;
import model.PersonModel;
import model.SearchModel;

public class SearchController {

    public static ArrayList<String> lessonNames(){
        return SearchModel.lessonNames();
    }

    public static void switchClicked(Switch myswitch,ArrayList<Lesson> lessons){
        if (myswitch.isChecked()) {
            Collections.sort(lessons, new Comparator<Lesson>(){
                public int compare(Lesson l1, Lesson l2){
//                    Tutor t1= PersonDataDB.getTutorFromDB(l1.getTutorId());
                    Tutor t1= PersonModel.getTutor(l1.getTutorId());
                    String s1=t1.getFirstName()+" "+t1.getFirstName();
//                    Tutor t2= PersonDataDB.getTutorFromDB(l2.getTutorId());
                    Tutor t2= PersonModel.getTutor(l2.getTutorId());
                    String s2=t2.getFirstName()+" "+t2.getFirstName();
                    if(s1.compareTo(s2)<0)
                        return -1;
                    else{
                        return 1;
                    }
                }
            });
        }
        else{
            Collections.sort(lessons, new Comparator<Lesson>(){
                public int compare(Lesson l1, Lesson l2){
                    if(l1.getPrice().compareTo(l2.getPrice())<0)
                        return 1;
                    else{
                        return -1;
                    }
                }
            });
        }
    }

    public static void orderMeetings(ArrayList<Meeting> mymeeting){
        Collections.sort(mymeeting, new Comparator<Meeting>(){
            public int compare(Meeting m1, Meeting m2){
                Timestamp t1=m1.getStartDateTime();
                Timestamp t2=m2.getStartDateTime();
                if(t1.compareTo(t2)<0)
                    return -1;
                else{
                    return 1;
                }
            }
        });
    }
}
