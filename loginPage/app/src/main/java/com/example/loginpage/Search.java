package com.example.loginpage;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TimePicker;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Locale;

import db.LessonDB;
import impl.DateFunctions;
import impl.Lesson;
import impl.Meeting;

public class Search extends AppCompatActivity {



    private Button searchbtn;
    private String pickedClass;
    private Spinner classlist;

    private DatePickerDialog datePickerDialog;
    private Button dateButton;

    private ArrayList<String> Pickclasses=new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        Pickclasses= LessonDB.getLessonsNames();

        Collections.sort(Pickclasses);
        Pickclasses.add(0,"class name");

        classlist =(Spinner)findViewById(R.id.classes_search_spinner);
        ArrayAdapter classesAdapter = new ArrayAdapter<>(this, R.layout.my_selected_class,Pickclasses);
        classesAdapter.setDropDownViewResource(R.layout.classlist_dropdown);
        classlist.setAdapter(classesAdapter);

        classlist.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                Object item = parent.getItemAtPosition(pos);
                pickedClass=item.toString();
                Log.d("AUTH_DEBUG",item.toString());
            }
            public void onNothingSelected(AdapterView<?> parent) {
                pickedClass="";
            }
        });



        initDatePicker();
        dateButton = findViewById(R.id.datePickerButton);
        dateButton.setText("pick date");



        searchbtn=(Button)findViewById(R.id.searchbtn_search);
        searchbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                Log.d("AUTH_DEBUG","search: class = "+ pickedClass+", date = "+date.getText().toString());

                if (pickedClass != "" && pickedClass != "class name" && pickedClass!=null) {
                        Log.d("AUTH_DEBUG",dateButton.getText().toString());
                    if (!dateButton.getText().toString().equals("pick date")) {
                        Intent i = new Intent(Search.this, SearchResults.class);
                        i.putExtra("num","2");
                        i.putExtra("class", pickedClass);
                        i.putExtra("date",dateButton.getText().toString());
                        startActivity(i);
                    } else {
                        Intent i = new Intent(Search.this, SearchResults.class);
                        i.putExtra("num","1");
                        i.putExtra("class", pickedClass);
                        startActivity(i);
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "you need to pick a class", Toast.LENGTH_LONG).show();
                }
            }
        });

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
        datePickerDialog.show();
    }



}