package com.example.loginpage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import impl.Lesson;
import impl.Meeting;

public class AddClass extends AppCompatActivity {

    public FirebaseFirestore firestore;
    private FirebaseAuth mAuth;
    private Button create;
    public boolean error=false;
    public LinearLayout mylayout;
    public Lesson l;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_class);
        error=false;
        create=(Button)findViewById(R.id.createbtn);
        create.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                mAuth = FirebaseAuth.getInstance();
                FirebaseUser user = mAuth.getCurrentUser();
                updateUI(user);
                String UID=user.getUid();
                EditText classname=(EditText)findViewById(R.id.editclass);
                EditText price=(EditText)findViewById(R.id.Eprice);
                if(classname.getText().toString().equals("") || price.getText().toString().equals("") ){
                    Toast.makeText(getApplicationContext(), "please fill in the name and price", Toast.LENGTH_LONG).show();
                    error=true;
                }
                else {
//                boolean b;
//                b=checkforlesson(UID,classname);
//                if (!b) {
                l=new Lesson(classname.getText().toString(),UID,price.getText().toString(),"");
                String LID = l.getLessonId();
                Log.d("AUTH_DEBUG", l.toString());
//                l.setLessonId(LID);
//                }
//                else{
//                    String l=getLessonID(UID,classname);
//                }

                    //meeting 1
                    EditText date1 = (EditText) findViewById(R.id.Edate_acr1);
                    EditText st1 = (EditText) findViewById(R.id.Estarttime_acr1);
                    EditText et1 = (EditText) findViewById(R.id.Eendtime_acr1);
                    CheckBox z1 = (CheckBox) findViewById(R.id.checkBox_zoom_acr1);
                    CheckBox inp1 = (CheckBox) findViewById(R.id.checkBox2_inperson_acr1);

                    if (date1.getText().toString().length() > 0 && st1.getText().toString().length() > 0 && et1.getText().toString().length() > 0) {
                        String datetimes1 = date1.getText().toString() + " " + st1.getText().toString();
                        String datetimee1 = date1.getText().toString() + " " + et1.getText().toString();
                        if (inp1.isChecked() == false && z1.isChecked() == false) {
                            Toast.makeText(getApplicationContext(), "please mark if meeting1 can be on zoom or is in person or both", Toast.LENGTH_LONG).show();
                            error=true;
                        }
                        else {
                            Log.d("AUTH_DEBUG", date1.getText().toString() + "\n" + datetimes1 + "\n" + datetimee1 + "\n" + String.valueOf(z1.isChecked()) + "\n" + String.valueOf(inp1.isChecked()));
                            Meeting m=new Meeting("m1",date1.getText().toString(),datetimes1,date1.getText().toString(),datetimee1/* String.valueOf(z1.isChecked()),String.valueOf(inp1.isChecked())*/);
                            l.addMeeting(m);
            //                    String MID=m.getMeetingId();
            //                    m.setMeetingID(MID);
                            Toast.makeText(getApplicationContext(), "created meeting1", Toast.LENGTH_LONG).show();
                        }
                    }
                    else{
                        Log.d("AUTH_DEBUG", "time or date is empty1");
                    }
                    //meeting 2
                    EditText date2 = (EditText) findViewById(R.id.Edate_acr2);
                    EditText st2 = (EditText) findViewById(R.id.Estarttime_acr2);
                    EditText et2 = (EditText) findViewById(R.id.Eendtime_acr2);
                    CheckBox z2 = (CheckBox) findViewById(R.id.checkBox_zoom_acr2);
                    CheckBox inp2 = (CheckBox) findViewById(R.id.checkBox2_inperson_acr2);
                    if (date2.getText().toString().length() > 0 && st2.getText().toString().length() > 0 && et2.getText().toString().length() > 0) {
                        String datetimes2 = date2.getText().toString() + " " + st2.getText().toString();
                        String datetimee2 = date2.getText().toString() + " " + et2.getText().toString();
                        if (inp2.isChecked() == false && z2.isChecked() == false) {
                            Toast.makeText(getApplicationContext(), "please mark if meeting2 can be on zoom or is in person or both", Toast.LENGTH_LONG).show();
                            error=true;
                        }
                        else {
                            Log.d("AUTH_DEBUG", date2.getText().toString() + "\n" + datetimes2 + "\n" + datetimee2 + "\n" + String.valueOf(z2.isChecked()) + "\n" + String.valueOf(inp2.isChecked()));
                            Meeting m= new Meeting("m2",date2.getText().toString(),datetimes2,date2.getText().toString(),datetimee2/*,String.valueOf(z2.isChecked()),String.valueOf(inp2.isChecked())*/);

                            l.addMeeting(m);
        //                    String MID=m.getMeetingId();
        //                    m.setMeetingID(MID);
                            Toast.makeText(getApplicationContext(), "created meeting2", Toast.LENGTH_LONG).show();
                        }
                    }
                    else{
                        Log.d("AUTH_DEBUG", "time or date is empty2");
                    }


                    //meeting 3
                    EditText date3 = (EditText) findViewById(R.id.Edate_acr3);
                    EditText st3 = (EditText) findViewById(R.id.Estarttime_acr3);
                    EditText et3 = (EditText) findViewById(R.id.Eendtime_acr3);
                    CheckBox z3 = (CheckBox) findViewById(R.id.checkBox_zoom_acr3);
                    CheckBox inp3 = (CheckBox) findViewById(R.id.checkBox2_inperson_acr3);
                    if (date3.getText().toString().length() > 0 && st3.getText().toString().length() > 0 && et3.getText().toString().length() > 0) {
                        String datetimes3 = date3.getText().toString() + " " + st3.getText().toString();
                        String datetimee3 = date3.getText().toString() + " " + et3.getText().toString();
                        if (inp3.isChecked() == false && z3.isChecked() == false) {
                            Toast.makeText(getApplicationContext(), "please mark if meeting3 can be on zoom or is in person or both", Toast.LENGTH_LONG).show();
                            error=true;
                        }
                        else {
                            Log.d("AUTH_DEBUG", date3.getText().toString() + "\n" + datetimes3 + "\n" + datetimee3 + "\n" + String.valueOf(z3.isChecked()) + "\n" + String.valueOf(inp3.isChecked()));
                            Meeting m= new Meeting("m3",date3.getText().toString(),datetimes3,date3.getText().toString(),datetimee3/*,String.valueOf(z3.isChecked()),String.valueOf(inp3.isChecked())*/);
                            l.addMeeting(m);
        //                    String MID=m.getMeetingId();
        //                    m.setMeetingID(MID);
                            Toast.makeText(getApplicationContext(), "created meeting3", Toast.LENGTH_LONG).show();
                        }
                    }
                    else{
                        Log.d("AUTH_DEBUG", "time or date is empty3");
                    }


                    //meeting 4
                    EditText date4 = (EditText) findViewById(R.id.Edate_acr4);
                    EditText st4 = (EditText) findViewById(R.id.Estarttime_acr4);
                    EditText et4 = (EditText) findViewById(R.id.Eendtime_acr4);
                    CheckBox z4 = (CheckBox) findViewById(R.id.checkBox_zoom_acr4);
                    CheckBox inp4 = (CheckBox) findViewById(R.id.checkBox2_inperson_acr4);
                    if (date4.getText().toString().length() > 0 && st4.getText().toString().length() > 0 && et4.getText().toString().length() > 0) {
                        String datetimes4 = date4.getText().toString() + " " + st4.getText().toString();
                        String datetimee4 = date4.getText().toString() + " " + et4.getText().toString();
                        if (inp4.isChecked() == false && z4.isChecked() == false) {
                            Toast.makeText(getApplicationContext(), "please mark if meeting4 can be on zoom or is in person or both", Toast.LENGTH_LONG).show();
                            error=true;
                        }
                        else {
                            Log.d("AUTH_DEBUG", date4.getText().toString() + "\n" + datetimes4 + "\n" + datetimee4 + "\n" + String.valueOf(z4.isChecked()) + "\n" + String.valueOf(inp4.isChecked()));
                            Meeting m= new Meeting("m4",date4.getText().toString(),datetimes4,date4.getText().toString(),datetimee4/*,String.valueOf(z4.isChecked()),String.valueOf(inp4.isChecked())*/);
                            l.addMeeting(m);
//                    String MID=m.getMeetingId();
//                    m.setMeetingID(MID);
                            Toast.makeText(getApplicationContext(), "created meeting4", Toast.LENGTH_LONG).show();
                        }
                    }
                    else{
                        Log.d("AUTH_DEBUG", "time or date is empty4");
                    }

                }
                if (!error) {
                    Log.d("AUTH_DEBUG", l.toString());
                    Intent i = new Intent(AddClass.this, TutorHomePage.class);
                    startActivity(i);
                }
            }
        });

    }



    public void updateUI(FirebaseUser user) {
    }

}