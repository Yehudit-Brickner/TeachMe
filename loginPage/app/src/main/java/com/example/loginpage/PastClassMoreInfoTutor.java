package com.example.loginpage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class PastClassMoreInfoTutor extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_past_class_more_info_tutor);



        TextView classname =(TextView)findViewById(R.id.pcmi_classname);
//        classname.setText(classname.getText().toString()+ );

        TextView studentname =(TextView)findViewById(R.id.pcmi_studentname);
        //        studentname.setText(tutorname.getText().toString()+ );

        TextView date =(TextView)findViewById(R.id.pcmi_date);
        //        date.setText(date.getText().toString()+ );

        TextView starttime =(TextView)findViewById(R.id.pcmi_starttime);
        //        starttime.setText(starttime.getText().toString()+ );

        TextView endtime =(TextView)findViewById(R.id.pcmi_endtime);
        //        endtime.setText(endtime.getText().toString()+ );

        TextView price =(TextView)findViewById(R.id.pcmi_price_per_hour);
        //        price.setText(price.getText().toString()+ );

        EditText summary= (EditText) findViewById(R.id.pcmi_class_summaryE);

        Button updateSummary=(Button) findViewById(R.id.updatesummmary);
        updateSummary.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
               // meeting.setsummary(summary.getText.toString());
                Toast.makeText(getApplicationContext(), "summary updated", Toast.LENGTH_LONG).show();
            }
        });

        ImageButton whatsapp =(ImageButton)findViewById(R.id.whatsappbtn1);
        whatsapp.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "this feature will be coming soon!", Toast.LENGTH_LONG).show();
            }
        });

    }
}