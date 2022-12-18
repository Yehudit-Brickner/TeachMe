package impl;

import java.util.ArrayList;

public class Tutor extends Person
{
    private ArrayList<Lesson> lessons = new ArrayList<>();

    public Tutor(String uID, String firstName, String lastName, String email, String phoneNumber) {
        super(uID, firstName, lastName, email, phoneNumber);
    }

    public Tutor()
    {

    }
}
