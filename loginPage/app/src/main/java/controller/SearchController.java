package controller;

import android.widget.Switch;

import com.google.firebase.Timestamp;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

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

    public static void switchClicked(Switch myswitch, ArrayList<Lesson> lessons){
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


    public static ArrayList<Meeting> betweenDates(ArrayList<Meeting> meetings, String StartDate, String EndDate ){
        Date date1;
        Date date2;
        Timestamp t1=null;
        Timestamp t2=null;
        if (StartDate==null) {
            return meetings;
        }
        else{
            try {
                date1=new SimpleDateFormat("dd/MM/yyyy").parse(StartDate);
                t1=new Timestamp(date1);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        if(EndDate!=null){
            try {
//                date2=new SimpleDateFormat("dd/MM/yyyy").parse(EndDate);
                Calendar cal = Calendar.getInstance();
                cal.setTime(new SimpleDateFormat("dd/MM/yyyy").parse( EndDate ) );
                cal.add( Calendar.DATE, 1 );
                date2=cal.getTime();
                t2=new Timestamp(date2);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        ArrayList<Meeting> newMeetings= new ArrayList<>();

        if (t1!=null && t2!=null){
            for (int i=0; i< meetings.size();i++){
                if (meetings.get(i).getStartDateTime().compareTo(t1)>=0 && meetings.get(i).getStartDateTime().compareTo(t2)<=0){
                    newMeetings.add(meetings.get(i));
                }
            }
            return newMeetings;
        }

        if(t1!=null && t2==null){
            for (int i=0; i< meetings.size();i++){
                if (meetings.get(i).getStartDateTime().compareTo(t1)>=0 ){
                    newMeetings.add(meetings.get(i));
                }
            }
            return newMeetings;
        }



        return meetings;
    }
}
