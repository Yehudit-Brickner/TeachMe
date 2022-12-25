package com.example.loginpage;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

import java.util.Calendar;
import java.util.Locale;

import db.LessonDB;
import impl.DateFunctions;
import impl.Lesson;

public class AddClass2 extends AppCompatActivity {



    public FirebaseFirestore firestore;
    private FirebaseAuth mAuth;
    private Button create;
    private Button add;
    public boolean error=false;
    public boolean createLesson=false;
    LinearLayout mylayout;
    public Lesson l;
    private DatePickerDialog datePickerDialog;
    private TimePickerDialog timePickerDialog;
    int hour, minute;

    Button Starttime;
    Button Endtime;
    Button dateButton;
    EditText city;
    CheckBox zoom;
    CheckBox inperson;
    Button remove;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_class2);

        mylayout = findViewById(R.id.layout_list);



        add = (Button) findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("AUTH_DEBUG", "pressed button add in AddClass2");
                addView();
            }
        });

        create = (Button) findViewById(R.id.createbtn);

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("AUTH_DEBUG", "pressed button create");


                for (int i=0; i<mylayout.getChildCount();i++){
                    System.out.println(i);
//                    System.out.println(mylayout.getChildAt(i).toString());
                    dateButton= mylayout.getChildAt(i).findViewById(R.id.datePickerButton);
                    System.out.println(dateButton.getText().toString());
                    Starttime=mylayout.getChildAt(i).findViewById(R.id.timeButtonStart);
                    Endtime=mylayout.getChildAt(i).findViewById(R.id.timeButtonEnd);
                    city=mylayout.getChildAt(i).findViewById(R.id.Ecity_acr);
                    zoom=mylayout.getChildAt(i).findViewById(R.id.checkBox_zoom_acr);
                    inperson=mylayout.getChildAt(i).findViewById(R.id.checkBox2_inperson_acr);

                }

                mAuth = FirebaseAuth.getInstance();
                FirebaseUser user = mAuth.getCurrentUser();
                String UID = user.getUid();

                EditText classname = (EditText) findViewById(R.id.editclass);
                EditText price = (EditText) findViewById(R.id.Eprice);

                if (classname.getText().toString().equals("") || price.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "please fill in the name and price", Toast.LENGTH_LONG).show();
                    error = true;
                } else {
                    l = LessonDB.getLessonFromDB(UID, classname.getText().toString());
                    if (!l.getLessonId().equals(classname.getText().toString())) {
                        l = new Lesson(classname.getText().toString(), UID, price.getText().toString(), "");
                        LessonDB.setLessonData(l);
                    }
                    Log.d("AUTH_DEBUG", l.toString());
                }




            }
        });

    }




    public void addView(){
        View myview = getLayoutInflater().inflate(R.layout.add_class_row2,null,false);

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


}