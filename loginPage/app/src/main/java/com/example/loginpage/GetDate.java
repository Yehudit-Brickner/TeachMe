package com.example.loginpage;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;

import java.util.Calendar;

import impl.DateFunctions;

public class GetDate extends AppCompatActivity {

    private DatePickerDialog datePickerDialog;
    private Button dateButton;

    private TimePickerDialog timePickerDialog;
    private Button timeButton;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_date);
        initDatePicker();
        dateButton = findViewById(R.id.datePickerButton);
        dateButton.setText("pick date");

        initTimePicker();
        timeButton = findViewById(R.id.timePickerButton);
        timeButton.setText("pick time");
    }

    private void initTimePicker() {
        TimePickerDialog.OnTimeSetListener timeSetListener= new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                String time= hourOfDay+":"+minute;
                timeButton.setText(time);
            }
        };
    }

    public void openTimePicker(View view) {
        timePickerDialog.show();
    }


    private void initDatePicker() {
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