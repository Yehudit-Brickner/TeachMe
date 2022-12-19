package com.example.loginpage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;

import db.LessonDB;
import impl.Lesson;
import impl.Meeting;

public class Search extends AppCompatActivity {

    private EditText date;
    private Button searchbtn;
    public String pickedClass;
    private Spinner classlist;

    private ArrayList<String> Pickclasses=new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        Pickclasses= LessonDB.getLessonsNames();

        Collections.sort(Pickclasses);
        Pickclasses.add(0,"class name");

        classlist =(Spinner)findViewById(R.id.classes_search_spinner);
        ArrayAdapter classesAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item,Pickclasses);
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


        date=(EditText)findViewById(R.id.choosedate_search);
        searchbtn=(Button)findViewById(R.id.searchbtn_search);
        searchbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                date=(EditText)findViewById(R.id.choosedate_search);
                Log.d("AUTH_DEBUG","search: class = "+ pickedClass+", date = "+date.getText().toString());

                if (pickedClass != "" && pickedClass != "class name" && pickedClass!=null) {
                    if (date.getText().toString().length() > 0) {
                        Intent i = new Intent(Search.this, SearchResults.class);
                        i.putExtra("num","2");
                        i.putExtra("class", pickedClass);
                        i.putExtra("date",date.getText().toString());
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
}