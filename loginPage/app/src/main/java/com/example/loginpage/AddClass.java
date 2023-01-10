package com.example.loginpage;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import db.LessonDB;
import db.MeetingDB;
import impl.DateFunctions;
import impl.Lesson;
import impl.Meeting;

public class AddClass extends AppCompatActivity {



    private FirebaseFirestore firestore;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private Button create;
    private Button add;
    private boolean error=false;
    private boolean error_notified=false;
    private boolean createLesson=false;
    private LinearLayout mylayout;
    private Lesson l;
    private DatePickerDialog datePickerDialog;
    private TimePickerDialog timePickerDialog;
    private ArrayList<Meeting> newMeetings;

    private int hour, minute;
    private String datetimes;
    private String datetimee;
    private Button Starttime;
    private Button Endtime;
    private Button dateButton;
    private EditText city;
    private CheckBox zoom;
    private CheckBox inperson;
    private Button remove;
    private EditText classname;
    private EditText price;
    private String UID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_class);

        mylayout = findViewById(R.id.layout_list);

        add = (Button) findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("AUTH_DEBUG", "pressed button add in AddClass2");
                boolean ok= checkPreivius();
                if (ok) {
                    addView();
                }
            }
        });

        create = (Button) findViewById(R.id.createbtn);
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pressedCreate();
            }
        });

    }


    public boolean checkPreivius(){
        int count= mylayout.getChildCount();
        count-=1;
        error=false;

        if (count>=0) {
            dateButton = mylayout.getChildAt(count).findViewById(R.id.datePickerButton);
            Starttime = mylayout.getChildAt(count).findViewById(R.id.timeButtonStart);
            Endtime = mylayout.getChildAt(count).findViewById(R.id.timeButtonEnd);
            city = mylayout.getChildAt(count).findViewById(R.id.Ecity_acr);
            zoom = mylayout.getChildAt(count).findViewById(R.id.checkBox_zoom_acr);
            inperson = mylayout.getChildAt(count).findViewById(R.id.checkBox2_inperson_acr);

            if (dateButton.getText().toString().equals("date") || Starttime.getText().toString().contains("select time") || Endtime.getText().toString().contains("select time")) {
                error = true;
                Toast.makeText(getApplicationContext(), "please fill in date and times", Toast.LENGTH_LONG).show();
            }

            if (inperson.isChecked() == false && zoom.isChecked() == false) {
                error = true;
                Toast.makeText(getApplicationContext(), "please fill in checkbox", Toast.LENGTH_LONG).show();
            }
            if (inperson.isChecked() == true && city.getText().toString().equals("")) {
                error = true;
                Toast.makeText(getApplicationContext(), "please fill in city", Toast.LENGTH_LONG).show();
            }
        }

        return !error;
    }

    public void addView(){
        View myview = getLayoutInflater().inflate(R.layout.add_class_row,null,false);

        Starttime =(Button)myview.findViewById(R.id.timeButtonStart);
        Endtime =(Button)myview.findViewById(R.id.timeButtonEnd);
        dateButton =(Button)myview.findViewById(R.id.datePickerButton);
        city =(EditText)myview.findViewById(R.id.Ecity_acr);
        zoom =(CheckBox)myview.findViewById(R.id.checkBox_zoom_acr);
        inperson =(CheckBox)myview.findViewById(R.id.checkBox2_inperson_acr);
        remove =(Button)myview.findViewById(R.id.buttonremove);
        remove.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Log.d("AUTH_DEBUG","pressed button remove");
                RemoveView(myview);
            }
        });

        mylayout.addView(myview);
    }

    public void RemoveView(View v){
        mylayout.removeView(v);
    }

    public void initDatePicker() {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                String date = DateFunctions.makeDateString(day, month, year);
                dateButton.setText(date);
            }
        };

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int style = AlertDialog.THEME_HOLO_LIGHT;
        datePickerDialog = new DatePickerDialog(this, style, dateSetListener, year, month, day);
    }

    public void openDatePicker(View view) {
        initDatePicker();
        datePickerDialog.show();
    }

    public void popTimePickerStart(View view) {
        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int selectedHour, int selectedMinute) {
                hour= selectedHour;
                minute=selectedMinute;
                Starttime.setText(String.format(Locale.getDefault(), "%02d:%02d",hour,minute));
            }
        };
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, onTimeSetListener, hour,minute,true);
        timePickerDialog.setTitle("Select time");
        timePickerDialog.show();
    }

    public void popTimePickerEnd(View view) {
        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int selectedHour, int selectedMinute) {
                hour= selectedHour;
                minute=selectedMinute;
                Endtime.setText(String.format(Locale.getDefault(), "%02d:%02d",hour,minute));
            }
        };
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, onTimeSetListener, hour,minute,true);
        timePickerDialog.setTitle("Select time");
        timePickerDialog.show();
    }

    public void pressedCreate() {
        Log.d("AUTH_DEBUG", "pressed button create");
//        boolean ok = checkPreivius();
//        if (ok) {
            mAuth = FirebaseAuth.getInstance();
            user = mAuth.getCurrentUser();
            String UID = user.getUid();
            error = false;
            error_notified=false;
            classname = (EditText) findViewById(R.id.editclass);
            price = (EditText) findViewById(R.id.Eprice);

            if (classname.getText().toString().equals("") || price.getText().toString().equals("")) {
                error = true;
            }
            else {
                l = LessonDB.getLessonFromDB(UID, classname.getText().toString());
                if (!l.getLessonId().equals(classname.getText().toString())) {
                    l = new Lesson(classname.getText().toString(), UID, price.getText().toString(), "");
                    LessonDB.setLessonData(l);
                }
            }

            newMeetings = new ArrayList<>();
            if (!error) {
                for (int i = 0; i < mylayout.getChildCount(); i++) {

                    dateButton = mylayout.getChildAt(i).findViewById(R.id.datePickerButton);
                    Starttime = mylayout.getChildAt(i).findViewById(R.id.timeButtonStart);
                    Endtime = mylayout.getChildAt(i).findViewById(R.id.timeButtonEnd);
                    city = mylayout.getChildAt(i).findViewById(R.id.Ecity_acr);
                    zoom = mylayout.getChildAt(i).findViewById(R.id.checkBox_zoom_acr);
                    inperson = mylayout.getChildAt(i).findViewById(R.id.checkBox2_inperson_acr);
                    if (dateButton.getText().toString().equals("date") || Starttime.getText().toString().contains("select time") || Endtime.getText().toString().contains("select time")) {
                        error = true;
                    } else {
                        if (!dateButton.getText().toString().equals("date") && !Starttime.getText().toString().contains("select time") && !Endtime.getText().toString().contains("select time")) {
                            datetimes = dateButton.getText().toString() + " " + Starttime.getText().toString();
                            datetimee = dateButton.getText().toString() + " " + Endtime.getText().toString();
                        }
                    }
                    if (inperson.isChecked() == false && zoom.isChecked() == false) {
                        error = true;
                    }
                    if (inperson.isChecked() == true && city.getText().toString().equals("")) {
                        error = true;
                    }
                    if (!error) {
                        Log.d("AUTH_DEBUG", dateButton.getText().toString() + "\n" + datetimes + "\n" + datetimee + "\n" + String.valueOf(zoom.isChecked()) + "\n" + String.valueOf(inperson.isChecked()));
                        Meeting m = new Meeting(l.getLessonId(), dateButton.getText().toString(), Starttime.getText().toString(),
                                dateButton.getText().toString(), Endtime.getText().toString(), UID, zoom.isChecked(),
                                inperson.isChecked(),city.getText().toString());
                        Log.d("AUTH_DEBUG", m.toString());
                        newMeetings.add(m);

                    }
                    else {
                        Log.d("AUTH_DEBUG", "there was a problem");
                        int x= i+1;
                        error_notified=true;
                        Toast.makeText(getApplicationContext(), "something caused an error, in meeting "+ x, Toast.LENGTH_LONG).show();
                        break;
                    }
                }
            }
            else {
                if (!error_notified) {
                    error_notified=true;
                    Toast.makeText(getApplicationContext(), "something caused an error, please check that everything is filled in", Toast.LENGTH_LONG).show();
                }
            }
            if (!error) {
                for (int i = 0; i < newMeetings.size(); i++) {
//                    if(checkTimes(newMeetings.get(i))) {
                        MeetingDB.setMeeting(newMeetings.get(i));
                        l.addMeeting(newMeetings.get(i));
//                    }
                }
                LessonDB.setLessonData(l);
                Intent i = new Intent(AddClass.this, TutorHomePage.class);
                startActivity(i);
            }
            else {
                if (!error_notified) {
                    Toast.makeText(getApplicationContext(), "something caused an error, please check that everything is filled in", Toast.LENGTH_LONG).show();
                }
            }

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.topmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.topmenu:
                Intent i =new Intent(AddClass.this, TutorHomePage.class);
                startActivity(i);
        }
        return true;
    }


}