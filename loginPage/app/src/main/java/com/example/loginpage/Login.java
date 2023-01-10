package com.example.loginpage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RadioButton;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.FirebaseFirestore;

import androidx.annotation.NonNull;

import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import controller.Login_controller;
import db.PersonDataDB;
import impl.Student;
import impl.Tutor;


public class Login extends AppCompatActivity {

    private FirebaseFirestore firestore;
    private FirebaseAuth mAuth;
    private RadioButton radiobtnS;
    private RadioButton radiobtnT;
    private ImageButton googleLogin;
    private GoogleSignInOptions gso;
    private GoogleSignInClient gsc;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // variables used
        mAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        radiobtnS = (RadioButton) findViewById(R.id.radioButtonS);
        radiobtnS.setChecked(false);
        radiobtnT = (RadioButton) findViewById(R.id.radioButtonT);
        radiobtnT.setChecked(false);
        googleLogin = (ImageButton) (findViewById(R.id.googleBtnLogin));
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id)).requestEmail().build();
        gsc = GoogleSignIn.getClient(Login.this, gso);


        // check which permission is being used
        radiobtnS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Login_controller.StudentChecked(radiobtnS,radiobtnT);
//                if(radiobtnT.isChecked()){
//                    radiobtnT.setChecked(false);
//                    radiobtnS.setChecked(true);
//                }
//                else{
//                    radiobtnS.setChecked(true);
//                }

                Log.d("AUTH_DEBUG", "student clicked");
            }
        });

        radiobtnT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Login_controller.tutorChecked(radiobtnS,radiobtnT);
//                if(radiobtnS.isChecked()){
//                    radiobtnS.setChecked(false);
//                    radiobtnT.setChecked(true);
//                }
//                else{
//                    radiobtnT.setChecked(true);
//                }
                Log.d("AUTH_DEBUG", "tutor clicked");
            }
        });


        // login with google
        googleLogin.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View view) {
//                if ( radiobtnS.isChecked() || radiobtnT.isChecked()) {
//                    Log.d("AUTH_DEBUG", "pressed the google button");
//                    SignIn();
//                } else {
//                    Log.d("AUTH_DEBUG", "pressed the google button");
//                    Toast.makeText(getApplicationContext(), "you need to pick student or tutor", Toast.LENGTH_LONG).show();
//                }

                if (Login_controller.googleClicked(radiobtnS, radiobtnT, gsc, gso)) {
                    SignIn();
                }
                else {
                    Toast.makeText(getApplicationContext(), "you need to pick student or tutor", Toast.LENGTH_LONG).show();
                }

            }
        });
    }

    public void SignIn() {
        Intent signInIntent = gsc.getSignInIntent();
        startActivityForResult(signInIntent, 1000);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultcode, Intent data) {

        Log.d("AUTH_DEBUG","in func onActivityResult");
        super.onActivityResult(requestCode, resultcode, data);

        if (requestCode == 1000) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                Log.d("AUTH_DEBUG", "firebaseAuthWithGoogle:" + account.getId());
                firebaseAuthWithGoogle(account.getIdToken());

            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w("AUTH_DEBUG", "Google sign in failed", e);
            }
        }
    }

    // firebase authentication
    private void firebaseAuthWithGoogle(String idToken) {
        Log.d("AUTH_DEBUG", "in firebase auth with google function!");
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("AUTH_DEBUG", "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                            String UID = user.getUid();
                            // check if the person has a user in the db and is the type matches

                            Log.d("AUTH_DEBUG", "user= "+ UID);
                            Student s = PersonDataDB.getStudentFromDB(UID);
                            Tutor t = PersonDataDB.getTutorFromDB(UID);
                            if (t!=null || s!=null) {
                                if (radiobtnS.isChecked() && s!=null) {
                                    Intent intent = new Intent(Login.this, StudentHomePage.class);
                                    intent.putExtra("uid", UID);
                                    startActivity(intent);
                                } else if (radiobtnT.isChecked() && t!=null) {
                                    Intent intent = new Intent(Login.this, TutorHomePage.class);
                                    intent.putExtra("uid", UID);
                                    startActivity(intent);
                                }
                                else{
                                    Toast.makeText(getApplicationContext(), "you tried logging in with the wrong permission", Toast.LENGTH_SHORT).show();
                                }
                            }
                            else{
                                Intent intent = new Intent(Login.this, SignUp.class);
                                startActivity(intent);
                            }
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("AUTH_DEBUG", "signInWithCredential:failure", task.getException());
                            updateUI(null);

                        }
                    }
                });
    }


    private void updateUI(FirebaseUser user) {
    }


    private void signout() {
        gsc.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete( Task<Void> task) {
                finish();

            }
        });
    }
}

