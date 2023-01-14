package com.example.loginpage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import controller.LessonMeetingController;
import impl.Lesson;
import impl.Meeting;
import impl.Student;
import impl.Tutor;

public class FutureClassMoreInfoTutor extends AppCompatActivity {


    private Intent intent;
    private String MID;
    private String LID;
    private String TID;
    private String sId;
    private Meeting m;
    private Lesson l;
    private Tutor t;
    private Student s;
    private TextView classname;
    private TextView studentname;
    private TextView date;
    private TextView starttime;
    private TextView endtime;
    private TextView price;
    private TextView zoom;
    private TextView inperson;
    private TextView city;
    private ImageButton whatsapp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_future_class_more_info_tutor);

        intent=getIntent();
        MID = intent.getStringExtra("mID");
        TID = intent.getStringExtra("tID");
        LID = intent.getStringExtra("lID");
        m= LessonMeetingController.getMeeting(TID,LID,MID);
        t= LessonMeetingController.getTutor(TID);
        l= LessonMeetingController.getLesson(TID,LID);

        sId=m.getStudentId();
        if(!sId.equals("")) {
            s= LessonMeetingController.getStudent(sId);
        }

        classname =(TextView)findViewById(R.id.fcmi_classname);
        classname.setText(classname.getText().toString()+ m.getLessonId());

        studentname =(TextView)findViewById(R.id.fcmi_studentname);
        if (!sId.equals("")) {
            studentname.setText(studentname.getText().toString() + s.getFirstName() + " " + s.getLastName());
        }
        date =(TextView)findViewById(R.id.fcmi_date);
        date.setText(date.getText().toString()+ m.getDateStart());

        starttime =(TextView)findViewById(R.id.fcmi_starttime);
        starttime.setText(starttime.getText().toString()+ m.getTimeStart());

        endtime =(TextView)findViewById(R.id.fcmi_endtime);
        endtime.setText(endtime.getText().toString()+m.getTimeEnd() );

        price =(TextView)findViewById(R.id.fcmi_price_per_hour);
        price.setText(price.getText().toString()+ l.getPrice());

        zoom =(TextView)findViewById(R.id.fcmi_zoom);
        zoom.setText(zoom.getText().toString()+ String.valueOf(m.isZoom()));

        inperson =(TextView)findViewById(R.id.fcmi_inperson);
        inperson.setText(inperson.getText().toString()+ String.valueOf(m.isInPerson()));

        city =(TextView)findViewById(R.id.fcmi_city);
        city.setText(city.getText().toString()+ m.getCity());


        whatsapp =(ImageButton)findViewById(R.id.whatsappbtn2);


        whatsapp.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "this feature will be coming soon!", Toast.LENGTH_LONG).show();
//                boolean installed=isAppInstaled("com.whatsapp");
//                installed=true;
//                if(installed){
//                    Intent sendIntent = new Intent();
//                    sendIntent.setAction(Intent.ACTION_SEND);
//                    String num="+972549761411";
//                    String text="hello world";
////                    sendIntent.setData(Uri.parse("http://whatsapp.com/send?phone="+num+"&text="+text));
//                    sendIntent.setData(Uri.parse("http://api.whatsapp.com/send?phone="+num+"&text="+text));
////                    sendIntent.setData(Uri.parse("https://wa.me/"+num+"&text="+text));
//                    startActivity(sendIntent);
//                }
//                else{
//                    Toast.makeText(getApplicationContext(), "sorry, you don't have whatsapp", Toast.LENGTH_LONG).show();
//                }
            }
        });


    }




    private void checkWhatsapp() {
        String packageName = "com.whatsapp";
        String mesgToShare = "Hey, I am searching for Whatsapp in your device.";

        boolean gotPackage = false;
        Intent shareIntent = new Intent(android.content.Intent.ACTION_SEND );
        shareIntent.setType( "text/plain" );
        shareIntent.putExtra( android.content.Intent.EXTRA_TEXT, mesgToShare );
        List<ResolveInfo> activityList = getPackageManager().queryIntentActivities(shareIntent, 0 );
        for ( final ResolveInfo app : activityList ) {
            if ( (app.activityInfo.name).contains(packageName)) {
                gotPackage = true;
                final ActivityInfo activity = app.activityInfo;
                ComponentName name = new ComponentName( activity.applicationInfo.packageName, activity.name );
                shareIntent.addCategory( Intent.CATEGORY_LAUNCHER );
                shareIntent.setFlags( Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED );
                shareIntent.setComponent( name );
                startActivity( shareIntent );
                break; // We already found what we were looking for. Don't need to execute the rest of the Loop
            }
        }

        if ( !gotPackage )
            Log.e("TAG", "Whatsapp is not installed in your device");
    }





    private boolean appInstalled(String url){
        PackageManager pm = getApplicationContext().getPackageManager();
        boolean installed;
        try {
            pm.getPackageInfo(url,PackageManager.GET_ACTIVITIES);
            installed = true;
        }
        catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            installed = false;
        }
        return installed;
    }

    public boolean isAppInstaled(String s){
        PackageManager packageManager = getPackageManager();
        boolean isInstalled;
        try{
//            packageManager.getPackageInfo(s,packageManager.GET_ACTIVITIES);
            packageManager.getPackageInfo(s,0);
            isInstalled=true;
        } catch (PackageManager.NameNotFoundException e) {
            isInstalled=false;
            e.printStackTrace();
        }
    return isInstalled;
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
                Intent i =new Intent(FutureClassMoreInfoTutor.this, TutorHomePage.class);
                startActivity(i);
        }
        return true;
    }
}