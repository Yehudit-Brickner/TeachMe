package model;

import java.util.ArrayList;

import db.LessonDB;

public class SearchModel {



    public static ArrayList<String> lessonNames(){
        return LessonDB.getLessonsNames();
    }
}
