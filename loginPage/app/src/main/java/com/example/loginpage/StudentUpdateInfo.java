package com.example.loginpage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

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
    private FirebaseUser user;
    private EditText fname;
    private EditText lname;
    private EditText phone;
    private CheckBox addpermision;
    private Button update;
    private String UID;
    private Student s;
    private boolean isTutor=false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_update_info);

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        firestore = FirebaseFirestore.getInstance();
        UID = user.getUid();
        s= PersonDataDB.getStudentFromDB(UID);

        fname=(EditText)findViewById(R.id.editfName);
        fname.setText(s.getFirstName());

        lname=(EditText)findViewById(R.id.editlName);
        lname.setText(s.getLastName());

        phone=(EditText)findViewById(R.id.editTel);
        phone.setText(s.getPhoneNumber());

        addpermision=(CheckBox)findViewById(R.id.addPermisionToStudent);
        if(PersonDataDB.getTutorFromDB(UID)!=null){
            addpermision.setChecked(true);
            isTutor=true;
        }
        update=(Button) findViewById(R.id.updateStudent);



        addpermision.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isTutor) {
                    if (addpermision.isChecked()) {
                        addpermision.setChecked(false);
                    } else {
                        addpermision.setChecked(true);
                    }
                }
                else{
                    addpermision.setChecked(true);
                }
            }
        });



        update.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
//                Toast.makeText(getApplicationContext(), "this feature will be coming soon!", Toast.LENGTH_LONG).show();
                PersonDataDB.updatePersonData(UID,fname.getText().toString(),lname.getText().toString(),s.getEmail(),phone.getText().toString(),true,isTutor);
                Intent i =new Intent(StudentUpdateInfo.this, StudentHomePage.class);
                startActivity(i);
            }
        });


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
                Intent i =new Intent(StudentUpdateInfo.this, StudentHomePage.class);
                startActivity(i);
        }
        return true;
    }
}