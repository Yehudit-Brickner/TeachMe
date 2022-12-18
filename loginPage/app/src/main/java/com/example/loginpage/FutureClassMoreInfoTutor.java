package com.example.loginpage;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

public class FutureClassMoreInfoTutor extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_future_class_more_info_tutor);



        TextView classname =(TextView)findViewById(R.id.fcmi_classname);
//        classname.setText(classname.getText().toString()+ );

        TextView tutorname =(TextView)findViewById(R.id.fcmi_classname);
        //        tutorname.setText(tutorname.getText().toString()+ );

        TextView date =(TextView)findViewById(R.id.fcmi_classname);
        //        date.setText(date.getText().toString()+ );

        TextView starttime =(TextView)findViewById(R.id.fcmi_classname);
        //        starttime.setText(starttime.getText().toString()+ );

        TextView endtime =(TextView)findViewById(R.id.fcmi_classname);
        //        endtime.setText(endtime.getText().toString()+ );

        TextView price =(TextView)findViewById(R.id.fcmi_classname);
        //        price.setText(price.getText().toString()+ );

        ImageButton whatsapp =(ImageButton)findViewById(R.id.whatsappbtn2);



    }
}