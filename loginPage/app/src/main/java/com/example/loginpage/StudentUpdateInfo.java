package com.example.loginpage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import db.PersonDataDB;
import impl.Student;

public class StudentUpdateInfo extends AppCompatActivity {


    private FirebaseAuth mAuth;
    private FirebaseFirestore firestore;
    private EditText fname;
    private EditText lname;
    private EditText phone;
    private CheckBox addpermision;
    private Button update;
    private String UID;
    private boolean isTutor=false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_update_info);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        firestore = FirebaseFirestore.getInstance();
        UID = user.getUid();
        Student s= PersonDataDB.getStudentFromDB(UID);
        fname=(EditText)findViewById(R.id.editfName);
        fname.setText(s.getFirstName());
        lname=(EditText)findViewById(R.id.editlName);
        lname.setText(s.getLastName());
        phone=(EditText)findViewById(R.id.editTel);
        phone.setText(s.getPhoneNumber());
        addpermision=(CheckBox)findViewById(R.id.addPermisionToStudent);
        update=(Button) findViewById(R.id.updateStudent);



        addpermision.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isTutor){
                    isTutor=true;
                }
                else{
                    isTutor=false;
                    addpermision.setChecked(false);
                }
            }
        });



        update.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
//                updateStudentInfo(UID,fname.getText().toString(),lname.getText().toString(),phone.getText().toString(),true,isTutor);
                Intent i =new Intent(StudentUpdateInfo.this, StudentHomePage.class);
                startActivity(i);
            }
        });


    }
}