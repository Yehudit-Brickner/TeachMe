package com.example.loginpage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.FirebaseFirestore;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;

import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import db.PersonDataDB;
import impl.Person;
import impl.Student;
import impl.Tutor;


public class NewLogin extends AppCompatActivity {

    public FirebaseFirestore firestore;
    private FirebaseAuth mAuth;
    private RadioButton radiobtnS;
    private RadioButton radiobtnT;
    private ImageButton googleLogin;
    GoogleSignInOptions gso;
    GoogleSignInClient gsc;

//    private int type = -1;
    private boolean isStudent;
    private boolean isTutor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_login);

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
        gsc = GoogleSignIn.getClient(NewLogin.this, gso);
        isStudent=false;
        isTutor=false;


        // check which permission is being used
        radiobtnS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isStudent) {
                    radiobtnT.setChecked(false);
                    radiobtnS.setChecked(true);
                    isStudent=true;
                } else {
                    radiobtnS.setChecked(false);
                    isStudent=false;
                }
                Log.d("AUTH_DEBUG", "student clicked");
            }
        });

        radiobtnT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isTutor) {
                    radiobtnS.setChecked(false);
                    radiobtnT.setChecked(true);
                    isTutor=true;
                } else {
                    radiobtnT.setChecked(false);
                    isTutor = false;
                }
                Log.d("AUTH_DEBUG", "tutor clicked");
            }
        });


        // login with google
        googleLogin.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View view) {
                if (/*isStudent*/ radiobtnS.isChecked() || /*isTutor*/radiobtnT.isChecked()) {
                    System.out.println("pressed the google button");
                    Log.d("AUTH_DEBUG", "pressed the google button");
                    Log.d("AUTH_DEBUG", "isStudent="+isStudent+" istutor="+isTutor);
                    SignIn();
                } else {
                    System.out.println("pressed the google button");
                    Log.d("AUTH_DEBUG", "pressed the google button");
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
                                if (/*isStudent*/radiobtnS.isChecked() && s!=null) {
                                    Intent intent = new Intent(NewLogin.this, StudentHomePage.class);
                                    intent.putExtra("uid", UID);
                                    startActivity(intent);
                                } else if (/*isTutor*/radiobtnT.isChecked() && t!=null) {
                                    Intent intent = new Intent(NewLogin.this, TutorHomePage.class);
                                    intent.putExtra("uid", UID);
                                    startActivity(intent);
                                }
                                else{
                                    Toast.makeText(getApplicationContext(), "you tried logging in with the wrong permission", Toast.LENGTH_SHORT).show();
                                }
                            }
                            else{
                                Intent intent = new Intent(NewLogin.this, SignUp.class);
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








    {
//        login.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View view) {
//                Log.d("AUTH_DEBUG", "pressed on login");
//                if (type>=0) {
//                    mAuth.signInWithEmailAndPassword(email.getText().toString(), password.getText().toString())
//                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                                @Override
//                                public void onComplete(@NonNull Task<AuthResult> task) {
//
//                                    if (task.isSuccessful()) {
//                                        // Sign in success, update UI with the signed-in user's information
//                                        Log.d("AUTH_DEBUG", "signInWithEmail:success");
//                                        FirebaseUser user = mAuth.getCurrentUser();
//                                        updateUI(user);
//                                        String UID = user.getUid();
//
//                                        Log.d("AUTH_DEBUG", "type = "+type);
//                                        if (type == 0){ // and user is a student
//
//                                            Intent i = new Intent(NewLogin.this, StudentHomePage.class);
//                                            i.putExtra("uid",UID);
//                                            startActivity(i);
//                                        }
//                                        else if (type == 1){ // and user is a tutor
//                                            Intent i = new Intent(NewLogin.this, TutorHomePage.class);
//                                            startActivity(i);
//                                        }
//                                        else{
//                                            Log.w("AUTH_DEBUG", "mix matched permissions", task.getException());
//                                            Toast.makeText(getApplicationContext(), "you tryed signing im as "+""+"\nbut you dont have that permision", Toast.LENGTH_SHORT).show();
//                                        }
//
//                                    }
//                                    else {
//                                        // If sign in fails, display a message to the user.
//                                        Log.w("AUTH_DEBUG", "signInWithEmail:failure", task.getException());
//                                        Toast.makeText(getApplicationContext(), "Authentication failed\n please check your email and password", Toast.LENGTH_SHORT).show();
//                                        updateUI(null);
//                                    }
//                                }
//
//                            });
//                }
//                else{
//                    Toast.makeText(getApplicationContext(), "you need to choose student or tutor", Toast.LENGTH_SHORT).show();
//                }
//
//            }
//        });
//

//    }
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

