package com.example.loginpage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class TutorHomePage extends AppCompatActivity {


    GoogleSignInOptions gso;
    GoogleSignInClient gsc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutor_home_page);


        TextView tutorname=(TextView)findViewById(R.id.name);



        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        gsc = GoogleSignIn.getClient(TutorHomePage.this,gso);
        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
        if(acct!=null){
            String personName = acct.getDisplayName();
            String s=tutorname.getText().toString()+ personName;
            tutorname.setText(s);
        }






        Button add=(Button) findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent i =new Intent(TutorHomePage.this, AddClass.class);
                startActivity(i);
            }
        });

        ImageButton profile=(ImageButton) findViewById(R.id.profile);
        profile.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent i =new Intent(TutorHomePage.this, MainActivity9.class);
                startActivity(i);
            }
        });

        Button signout=(Button) findViewById(R.id.signoutbtn);
        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOut();
                Intent i =new Intent(TutorHomePage.this, NewLogin.class);
                startActivity(i);
            }
        });

    }



    private void signOut() {
        FirebaseAuth.getInstance().signOut();
        gsc.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete( Task<Void> task) {
                finish();
            }
        });
    }
}