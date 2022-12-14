package com.example.loginpage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import db.PersonDataDB;
import impl.Student;

public class TutorUpdateInfo extends AppCompatActivity {


    private FirebaseAuth mAuth;
    private FirebaseFirestore firestore;
    private EditText fname;
    private EditText lname;
    private EditText phone;
    private CheckBox addpermision;
    private Button update;
    private String UID;
    private boolean isStudent=false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutor_update_info);


        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        firestore = FirebaseFirestore.getInstance();
        UID = user.getUid();
        impl.Tutor t= PersonDataDB.getTutorFromDB(UID);

        fname=(EditText)findViewById(R.id.editfName);
        fname.setText(t.getFirstName());

        lname=(EditText)findViewById(R.id.editlName);
        lname.setText(t.getLastName());

        phone=(EditText)findViewById(R.id.editTel);
        phone.setText(t.getPhoneNumber());

        addpermision=(CheckBox)findViewById(R.id.addPermisionToStudent);

        update=(Button) findViewById(R.id.updateStudent);



        addpermision.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isStudent){
                    isStudent=true;
                }
                else{
                    isStudent=false;
                    addpermision.setChecked(false);
                }
            }
        });


        update.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "this feature will be coming soon!", Toast.LENGTH_LONG).show();
//                updateStudentInfo(UID,fname.getText().toString(),lname.getText().toString(),phone.getText().toString(),true,isTutor);
                Intent i =new Intent(TutorUpdateInfo.this, TutorHomePage.class);
                startActivity(i);
            }
        });

    }
}