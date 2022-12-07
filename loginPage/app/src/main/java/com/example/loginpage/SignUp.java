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

import db.SignUpDB;
import impl.Person;
import interfaces.IPerson;


public class SignUp extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private  FirebaseFirestore firestore;

    private RadioButton radiobtnS;
    private RadioButton radiobtnT;
    private Button signup;
    private ImageButton gooogle;
    private int type =-1;

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
                if (type!=0) {
                    radiobtnT.setChecked(false);
                    type = 0;
                    String msg="not student to student" +type;
                    Log.d("AUTH_DEBUG", msg);
                }
                else{
                    radiobtnS.setChecked(false);
                    type=-1;
                    String msg="student to not student" +type;
                    Log.d("AUTH_DEBUG", msg);
                }
            }
        });

        radiobtnT.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if (type!=1) {
                    radiobtnS.setChecked(false);
                    type = 1;
                    String msg="not tutor to tutor "+type;
                    Log.d("AUTH_DEBUG", msg);
                }
                else{
                    radiobtnT.setChecked(false);
                    type=-1;
                    String msg="tutor to not tutor"+type;
                    Log.d("AUTH_DEBUG",msg );
                }
            }
        });


        gooogle.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View view) {
                if (type <0) {
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
                System.out.println("type="+type);
                Log.d("AUTH_DEBUG", "pressed signup"+type);
                if (type>-1) {
                    Log.d("AUTH_DEBUG","correct type");
                    if (checkPassword(password, vpassword))
                    {
                        IPerson person = new Person(fname.getText().toString(),
                                                    lname.getText().toString(),
                                                    email.getText().toString(),
                                                    phone.getText().toString());

                        String message = SignUpDB.signUpRequest(person, password.getText().toString());
                        Toast.makeText(getApplicationContext(),message,Toast.LENGTH_LONG).show();
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


}

