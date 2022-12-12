package com.example.loginpage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;

import impl.Meeting;

public class Search extends AppCompatActivity {


    private Spinner classlist;
    private EditText date;
    private Spinner citylist;
    private CheckBox zoom;
    private CheckBox inperson;
    private String pickedClass;
    private String pickedCity;
    private Button searchbtn;



    private ArrayList<String> pickclasses=new ArrayList<String>();
    private ArrayList<String> pickcities=new ArrayList<String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);


        pickclasses.add("Infi1");
        pickclasses.add("Infi2");
        pickclasses.add("Intro Java");
        pickclasses.add("Intro Python");
        pickclasses.add("Intro C");
        pickclasses.add("C++");
        pickclasses.add("Linear algebra1");
        pickclasses.add("Linear algebra2");
        pickclasses.add("Data science");
        pickclasses.add("Cyber");
        Collections.sort(pickclasses);
        pickclasses.add(0,"class name");


        pickcities.add("Jerusalem");
        pickcities.add("Ariel");
        pickcities.add("Tel-aviv");
        pickcities.add("Ashdod");
        pickcities.add("Haifa");
        pickcities.add("Eilat");
        pickcities.add("Beer-Sheba");
        Collections.sort(pickcities);
        pickcities.add(0,"city");

        classlist =(Spinner)findViewById(R.id.classes_search_spinner);
        ArrayAdapter classesAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item,pickclasses);
        classlist.setAdapter(classesAdapter);

        citylist =(Spinner)findViewById(R.id.city_search_spinner);
        ArrayAdapter cityAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item,pickcities);
        citylist.setAdapter(cityAdapter);


        pickedClass = classlist.getSelectedItem().toString();
        pickedCity = citylist.getSelectedItem().toString();

        searchbtn=(Button)findViewById(R.id.searchbtn_search);

        searchbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //ArrayList<Meeting> ans=searchForClasses(pickedClass,date.getText().toString(),pickedCity,zoom.getText().toString(),inperson.getText().toString());
                Intent i =new Intent(Search.this, SearchResults.class);
                startActivity(i);
            }
        });

//        serchForClasses()


    }
}