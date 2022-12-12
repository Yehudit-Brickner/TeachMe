package com.example.loginpage;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import impl.Person;
import impl.User;

public class SignUp extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private  FirebaseFirestore firestore;

    private RadioButton radiobtnS;
    private RadioButton radiobtnT;
    private Button signup;
    private ImageButton gooogle;
    private boolean isStudent =false;
    private boolean isTutor =false;

    GoogleSignInOptions gso;
    GoogleSignInClient gsc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();


        radiobtnS=(RadioButton)findViewById(R.id.radioButtonS) ;
        radiobtnT=(RadioButton)findViewById(R.id.radioButtonT) ;
        signup=(Button)findViewById(R.id.signupbtn);

        gooogle=(ImageButton)findViewById(R.id.imageButton);
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        gsc = GoogleSignIn.getClient(SignUp.this,gso);

        EditText fname = (EditText) findViewById(R.id.editfName);
        EditText lname = (EditText) findViewById(R.id.editlName);

        EditText phone = (EditText) findViewById(R.id.editTel);
        EditText email = (EditText) findViewById(R.id.editEmail);
        EditText password = (EditText) findViewById(R.id.editpassword);
        EditText vpassword =(EditText) findViewById(R.id.editpassword1);






        radiobtnS.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if (isStudent) {
                    radiobtnS.setChecked(false);
                    isStudent=false;
//                    type = 0;
//                    String msg="not student to student" +type;
//                    Log.d("AUTH_DEBUG", msg);
                }
                else{
                    isStudent=true;
//                    radiobtnS.setChecked(false);
//                    type=-1;
//                    String msg="student to not student" +type;
//                    Log.d("AUTH_DEBUG", msg);
                }
            }
        });

        radiobtnT.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if (isTutor) {
                    radiobtnT.setChecked(false);
                    isTutor=false;
//                    type = 1;
//                    String msg="not tutor to tutor "+type;
//                    Log.d("AUTH_DEBUG", msg);
                }
                else{
                    isTutor =true;
//                    radiobtnT.setChecked(false);
//                    type=-1;
//                    String msg="tutor to not tutor"+type;
//                    Log.d("AUTH_DEBUG",msg );
                }
            }
        });


        gooogle.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View view) {
                if (isStudent || isTutor) {
                    System.out.println("pressed the google button");
                    Log.d(TAG, "pressed the google button");
                    SignIn();

                }
                else{
                    System.out.println("pressed the google button");
                    Log.d(TAG, "pressed the google button");
                    Toast.makeText(getApplicationContext(),"you need to pick student or tutor",Toast.LENGTH_LONG).show();
                }
            }

        });



        signup.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
//                System.out.println("type="+type);
//                Log.d("AUTH_DEBUG", "pressed signup"+type);

                if (isStudent || isTutor) {
                    Log.d("AUTH_DEBUG", "correct type");
                    if (checkFillIn(fname, lname, phone, email, password, vpassword)) {
                        Log.d("AUTH_DEBUG", "all fields are filled in");
                        if (checkPassword(password, vpassword)) {
                            Log.d("AUTH_DEBUG", "passwords match");
                            mAuth.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                                    // Sign in success, update UI with the signed-in user's information
                                                    Log.d("AUTH_DEBUG", "createUserWithEmail:success");
                                                    FirebaseUser user = mAuth.getCurrentUser();
                                                    updateUI(user);
                                                    user.getUid();
                                                    Person p = new Person(user.getUid().toString(), fname.getText().toString(), lname.getText().toString(),
                                                            email.getText().toString(), phone.getText().toString(),String.valueOf(isStudent),String.valueOf(isTutor));
                                                    firestore.collection("users").add(p);
                                                    Intent i = new Intent(SignUp.this, NewLogin.class);
                                                    startActivity(i);

                                                    //                                            String s= String.valueOf(firestore.collection("users"));
                                                    ////                                            String uID=String.valueOf(firestore.collection("users").get());
                                                    //                                            Log.d("AUTH_DEBUG", "user ID is "+ s);
                                                    //                                            if (type==0){
                                                    //                                                Intent i = new Intent(SignUp.this, StudentHomePage.class);
                                                    //                                                startActivity(i);
                                                    //                                            } else if (type == 1) {
                                                    //                                                Intent i = new Intent(SignUp.this, TutorHomePage.class);
                                                    //                                                startActivity(i);
                                                    //                                            }
                                                }
                                    else {
                                                    // If sign in fails, display a message to the user.
                                                    Log.w("AUTH_DEBUG", "createUserWithEmail:failure", task.getException());
                                                    updateUI(null);
                                                }
                                }
                            });
                        }
                    }
                    else{
                        Toast.makeText(getApplicationContext(),"Make sure you filled everything in",Toast.LENGTH_LONG).show();
                    }
                }
                else{
//                    System.out.println("something went wrong");
                    Toast.makeText(getApplicationContext(),"please pick student or tutor",Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    private void updateUI(FirebaseUser user) {
    }


    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            reload();
        }
    }

    private void reload(){}


    public void SignIn(){

//        Intent signInIntent =gsc.getSignInIntent();
//        startActivityForResult(signInIntent,1000);
//        this.OnActivityResult(signInIntent,1000);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultcode, Intent data){
        super.onActivityResult(requestCode, resultcode, data);
        if (requestCode == 1000){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                task.getResult(ApiException.class);
//                navigateToSecoundActivity();
                finish();
                Intent intent = new Intent(SignUp.this, SignedIn.class);
                startActivity(intent);
            } catch (ApiException e) {
                Toast.makeText(getApplicationContext(),"oops, something went wrong",Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }
        }

    }

//    private void navigateToSecoundActivity() {
//        finish();
//        Intent intent = new Intent(SignUp.this, SignedIn.class);
//        startActivity(intent);
//    }


    public boolean checkPassword(EditText p1, EditText p2){
        if (p1.getText().toString().equals(p2.getText().toString())){
            Log.d("AUTH_DEBUG", "passwords equal");
            return true;
        }
        Log.d("AUTH_DEBUG", "passwords not equal");
        Toast.makeText(getApplicationContext(),"passwords don't match",Toast.LENGTH_LONG).show();
        return false;
    }

    public boolean checkFillIn(EditText fn, EditText ln, EditText ph, EditText e, EditText p1, EditText p2){

        Log.d("AUTH_DEBUG", "first name="+fn.getText().toString()+"....");

        if (fn.getText().toString().length()>0 && ln.getText().toString().length()>0
                && ph.getText().toString().length()>0 && e.getText().toString().length()>0
                && p1.getText().toString().length()>0 && p2.getText().toString().length()>0){
            return true;
        }

        Toast.makeText(getApplicationContext(),"Make sure you filled everything in",Toast.LENGTH_LONG).show();
        return false;

    }



}

