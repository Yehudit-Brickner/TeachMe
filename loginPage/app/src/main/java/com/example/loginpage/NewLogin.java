package com.example.loginpage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;

import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class NewLogin extends AppCompatActivity {

    public FirebaseFirestore firestore;
    private FirebaseAuth mAuth;


    private RadioButton radiobtnS;
    private RadioButton radiobtnT;
    private Button login;
    private Button forgotpassword;
    private Button signup;


    private int type=-1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_login);


        mAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();


        radiobtnS=(RadioButton)findViewById(R.id.radioButtonS) ;
        radiobtnT=(RadioButton)findViewById(R.id.radioButtonT) ;
        login=(Button)findViewById(R.id.loginbtn);
        forgotpassword=(Button)findViewById(R.id.forgotpassbtn);
        signup=(Button)findViewById(R.id.signupbtn);


        TextView email= (TextView) findViewById(R.id.email);
        TextView password= (TextView) findViewById(R.id.password);

        radiobtnS.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if (type!=0) {
                    radiobtnT.setChecked(false);
                    type = 0;
                    Log.d("AUTH_DEBUG", "not student to student");
                }
                else{
                    radiobtnS.setChecked(false);
                    type=-1;
                    Log.d("AUTH_DEBUG", "student to not student");
                }
            }
        });

        radiobtnT.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if (type!=1) {
                    radiobtnS.setChecked(false);
                    type = 1;
                    Log.d("AUTH_DEBUG", "not tutor to tutor");
                }
                else{
                    radiobtnT.setChecked(false);
                    type=-1;
                    Log.d("AUTH_DEBUG", "tutor to not tutor");
                }
            }
        });

        login.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Log.d("AUTH_DEBUG", "pressed on login");
                if (type>=0) {
                    mAuth.signInWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {

                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
                                        Log.d("AUTH_DEBUG", "signInWithEmail:success");
                                        FirebaseUser user = mAuth.getCurrentUser();
                                        if (!user.isEmailVerified())
                                        {
                                            Toast.makeText(getApplicationContext(), "Please vertify your email.", Toast.LENGTH_SHORT).show();
                                            mAuth.signOut();
                                            return;
                                        }
                                        updateUI(user);
                                        if (type==0){
                                            Intent i = new Intent(NewLogin.this, StudentHomePage.class);
                                            startActivity(i);
                                        }
                                        else{
                                            Intent i = new Intent(NewLogin.this, TutorHomePage.class);
                                            startActivity(i);
                                        }

                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Log.w("AUTH_DEBUG", "signInWithEmail:failure", task.getException());
                                        Toast.makeText(getApplicationContext(), "Authentication failed.", Toast.LENGTH_SHORT).show();
                                        updateUI(null);
                                    }
                                }

                            });
                }
                else{
                    Toast.makeText(getApplicationContext(), "you need to choose student or tutor", Toast.LENGTH_SHORT).show();
                }

//                System.out.println("type="+type);
//                if (type==0) {
//                    System.out.println(type);
//                    Task<QuerySnapshot> u = firestore.collection("users").get(Source.valueOf(username.getText().toString()));
//
//
//                    if (username.getText().toString().equals("admins") && password.getText().toString().equals("admins")) {
//                        Intent i = new Intent(NewLogin.this, StudentHomePage.class);
//                        startActivity(i);
//                    }
//                }
//                else if( type==1){
//                    if (username.getText().toString().equals("admint") && password.getText().toString().equals("admint")) {
//                        Intent i = new Intent(NewLogin.this, TutorHomePage.class);
//                        startActivity(i);
//                    }
//                }
//                else{
//                    System.out.println("something went wrong");
//                }
            }
        });


        forgotpassword.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Log.d("AUTH_DEBUG", "pressed on fotgotPassword");
                Intent i = new Intent(NewLogin.this, ForgotPassword.class);
                startActivity(i);
            }
        });

        signup.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Log.d("AUTH_DEBUG", "pressed on signup");
                Intent i = new Intent(NewLogin.this, SignUp.class);
                startActivity(i);
            }
        });


    }

    private void updateUI(FirebaseUser user) {
    }
}