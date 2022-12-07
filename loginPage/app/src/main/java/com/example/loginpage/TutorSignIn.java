package com.example.loginpage;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.material.button.MaterialButton;

public class TutorSignIn extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutor_sign_in);

        TextView username= (TextView) findViewById(R.id.email);
        TextView password= (TextView) findViewById(R.id.password);

        MaterialButton loginbtn=(MaterialButton) findViewById(R.id.loginbtn);

        //admin and admin
        loginbtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(username.getText().toString().equals("admint") &&(password.getText().toString().equals("admint"))){
                    Intent i =new Intent(TutorSignIn.this, TutorHomePage.class);
                    startActivity(i);
                }
                else{
                    Toast.makeText(TutorSignIn.this,"LOGIN FAILED",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}