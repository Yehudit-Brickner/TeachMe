package com.example.loginpage;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;



import android.graphics.Color;
import android.graphics.drawable.Drawable;
//import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import android.widget.CalendarView;


public class MainActivity10 extends AppCompatActivity {

    CalendarView simpleCalendarView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main10);

//        simpleCalendarView = (CalendarView) findViewById(R.id.simpleCalendarView); // get the reference of CalendarView
////        simpleCalendarView.setFocusedMonthDateColor(Color.RED); // set the red color for the dates of  focused month
////        simpleCalendarView.setUnfocusedMonthDateColor(Color.BLUE); // set the yellow color for the dates of an unfocused month
////        simpleCalendarView.setSelectedWeekBackgroundColor(Color.RED); // red color for the selected week's background
////        simpleCalendarView.setWeekSeparatorLineColor(Color.GREEN); // green color for the week separator line
//        // perform setOnDateChangeListener event on CalendarView
//        simpleCalendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
//            @Override
//            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
//                // display the selected date by using a toast
//                Toast.makeText(getApplicationContext(), dayOfMonth + "/" + month + "/" + year, Toast.LENGTH_LONG).show();
//            }
//        });
    }

}