package com.example.loginpage;

import static db.SignUpDB.setPersonData;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.FirebaseFirestore;

import impl.Person;
import interfaces.IPerson;

public class SignUp extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseFirestore firestore;
    private RadioButton radiobtnS;
    private RadioButton radiobtnT;
    private ImageButton googleSignup;
    private boolean isStudent =false;
    private boolean isTutor =false;
    private EditText fname;
    private EditText lname;
    private EditText phone;

    GoogleSignInOptions gso;
    GoogleSignInClient gsc;
    GoogleSignInAccount acct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        radiobtnS=(RadioButton)findViewById(R.id.radioButtonS) ;
        radiobtnT=(RadioButton)findViewById(R.id.radioButtonT) ;
        googleSignup=(ImageButton)findViewById(R.id.imageButton);


        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id)).requestEmail().build();
        gsc = GoogleSignIn.getClient(SignUp.this,gso);
        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);

        fname = (EditText) findViewById(R.id.editfName);
        lname = (EditText) findViewById(R.id.editlName);
        phone = (EditText) findViewById(R.id.editTel);


        radiobtnS.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if (isStudent) {
                    radiobtnS.setChecked(false);
                    isStudent=false;
                }
                else{
                    isStudent=true;
                }
            }
        });

        radiobtnT.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if (isTutor) {
                    radiobtnT.setChecked(false);
                    isTutor=false;
                }
                else{
                    isTutor =true;
                }
            }
        });


        googleSignup.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View view) {
                if (isStudent || isTutor) {
                    System.out.println("pressed the google button");
                    Log.d("AUTH_DEBUG", "pressed the google button");
                    FirebaseUser user = mAuth.getCurrentUser();
                    updateUI(user);
                    String UID=user.getUid();
                    IPerson p = new Person(UID,fname.getText().toString(),
                            lname.getText().toString(),
                            acct.getEmail(),phone.getText().toString());
                    setPersonData(p,isTutor,isStudent);
                    startActivity(new Intent(SignUp.this, Login.class));
                }
                else{
                    System.out.println("pressed the google button");
                    Log.d("AUTH_DEBUG", "pressed the google button");
                    Toast.makeText(getApplicationContext(),"you need to pick student or tutor",Toast.LENGTH_LONG).show();
                }
            }

        });


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
        Intent signInIntent =gsc.getSignInIntent();
        startActivityForResult(signInIntent,1000);
    }


    // [START auth_with_google]
    private void firebaseAuthWithGoogle(String idToken) {
        Log.d("AUTH_DEBUG", "in firebase auth with google function!" );
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
                            String UID=user.getUid();
                            IPerson p = new Person(UID,fname.getText().toString(),
                                    lname.getText().toString(),
                                    acct.getEmail(),phone.getText().toString());
                            setPersonData(p,isTutor,isStudent);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("AUTH_DEBUG", "signInWithCredential:failure", task.getException());
                            updateUI(null);
                        }
                    }
                });
    }




    @Override
    protected void onActivityResult(int requestCode, int resultcode, Intent data){
        super.onActivityResult(requestCode, resultcode, data);
        if (requestCode == 1000){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                Log.d("AUTH_DEBUG", "firebaseAuthWithGoogle:" + account.getId());
                Log.d("AUTH_DEBUG", "firebaseAuthWithGoogle:" + account.getId()+" "+account.getIdToken());
                firebaseAuthWithGoogle(account.getIdToken());
                signout();
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w("AUTH_DEBUG", "Google sign in failed", e);
            }
        }
    }



    public boolean checkFillIn(EditText fn, EditText ln, EditText ph){

        Log.d("AUTH_DEBUG", "first name="+fn.getText().toString()+"....");

        if (fn.getText().toString().length()>0 && ln.getText().toString().length()>0
                && ph.getText().toString().length()>0 ){
            return true;
        }

        Toast.makeText(getApplicationContext(),"Make sure you filled everything in",Toast.LENGTH_LONG).show();
        return false;

    }


    private void signout() {
        gsc.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete( Task<Void> task) {
                finish();
                startActivity(new Intent(SignUp.this, Login.class));
            }
        });
    }

    private void updateUI(FirebaseUser user) {
    }
}

