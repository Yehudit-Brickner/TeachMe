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

import controller.SearchController;
import db.LessonDB;
import impl.DateFunctions;
import impl.Lesson;
import impl.Meeting;

public class Search extends AppCompatActivity {



    private Button searchbtn;
    private String pickedClass;
    private Spinner classlist;

    private DatePickerDialog datePickerDialog;
    private Button dateButton1;
    private Button dateButton2;

    private ArrayList<String> Pickclasses=new ArrayList<String>();

    private ArrayList<String> info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

//        Pickclasses= LessonDB.getLessonsNames();
        Pickclasses= SearchController.lessonNames();
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


        dateButton1 = findViewById(R.id.datePickerButtonStart);
        dateButton1.setText("pick start search date");

        dateButton2 = findViewById(R.id.datePickerButtonEnd);
        dateButton2.setText("pick end search date");





        searchbtn=(Button)findViewById(R.id.searchbtn_search);
        searchbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickedSearch();
//                boolean error= false;
//                info= new ArrayList<String>();
//                if (pickedClass != "" && pickedClass != "class name" && pickedClass!=null) {
//                    info.add(pickedClass);
//                    if (!dateButton1.getText().toString().equals("pick start search date")) {
//                        info.add(dateButton1.getText().toString());
//                        if (!dateButton2.getText().toString().equals("pick end search date")) {
//                            info.add(dateButton2.getText().toString());
//                        } else {
//                            info.add(null);
//                        }
//                    }
//                    else{
//                        if (!dateButton2.getText().toString().equals("pick end search date")){
//                            error= true;
//                            Toast.makeText(getApplicationContext(), "you need to pick the start date", Toast.LENGTH_LONG).show();
//                        }
//                        info.add(null);
//                        info.add(null);
//                    }
//                    if (error==false) {
//                        Intent i = new Intent(Search.this, SearchResults.class);
//                        i.putStringArrayListExtra("info", info);
//                        startActivity(i);
//                    }
//                }
//                else {
//                    Toast.makeText(getApplicationContext(), "you need to pick a class", Toast.LENGTH_LONG).show();
//                }
            }
        });

    }

    public void clickedSearch(){
        boolean error= false;
        info= new ArrayList<String>();
        if (pickedClass != "" && pickedClass != "class name" && pickedClass!=null) {
            info.add(pickedClass);
            if (!dateButton1.getText().toString().equals("pick start search date")) {
                info.add(dateButton1.getText().toString());
                if (!dateButton2.getText().toString().equals("pick end search date")) {
                    info.add(dateButton2.getText().toString());
                } else {
                    info.add(null);
                }
            }
            else{
                if (!dateButton2.getText().toString().equals("pick end search date")){
                    error= true;
                    Toast.makeText(getApplicationContext(), "you need to pick the start date", Toast.LENGTH_LONG).show();
                }
                info.add(null);
                info.add(null);
            }
            if (error==false) {
                Intent i = new Intent(Search.this, SearchResults.class);
                i.putStringArrayListExtra("info", info);
                startActivity(i);
            }
        }
        else {
            Toast.makeText(getApplicationContext(), "you need to pick a class", Toast.LENGTH_LONG).show();
        }
    }



    public void initDatePicker(Button dbtn) {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                String date = DateFunctions.makeDateString(day, month, year);
                dbtn.setText(date);
            }
        };

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int style = AlertDialog.THEME_HOLO_LIGHT;
        datePickerDialog = new DatePickerDialog(this, style, dateSetListener, year, month, day);
    }

    public void openDatePickerStart(View view) {
        initDatePicker(dateButton1);
        datePickerDialog.show();
    }

    public void openDatePickerEnd(View view) {
        initDatePicker(dateButton2);
        datePickerDialog.show();
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
                Intent i =new Intent(Search.this, StudentHomePage.class);
                startActivity(i);
        }
        return true;
    }


}