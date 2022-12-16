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
import com.google.firebase.firestore.FirebaseFirestore;

public class StudentHomePage extends AppCompatActivity {

    private FirebaseFirestore firestore;
    GoogleSignInOptions gso;
    GoogleSignInClient gsc;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_home_page);

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        gsc = GoogleSignIn.getClient(StudentHomePage.this,gso);


        TextView studentName = (TextView) findViewById(R.id.name);
        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
        if(acct!=null){
            String personName = acct.getDisplayName();
            String s=studentName.getText().toString()+personName;
            studentName.setText(s);
        }









//        Intent intent=getIntent();
//        String str = intent.getStringExtra("uid");
//        Log.d("AUTH_DEBUG","uid = "+ str);


//        firestore = FirebaseFirestore.getInstance();
//        Log.d("AUTH_DEBUG",firestore.toString());
//        DocumentReference docRef = firestore.collection("users").document(str);

//        Log.d("AUTH_DEBUG",docRef.toString());
//        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
//            @Override
//            public void onSuccess(DocumentSnapshot documentSnapshot) {
////                Person student = documentSnapshot.toObject(Person.class);
////                Log.d("AUTH_DEBUG",student.getFirstName());
////                studentName.setText(student.getFirstName());
//            }
//        });






//        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                if (task.isSuccessful()){
//                    DocumentSnapshot doc = task.getResult();
//                    if (doc.exists()){
//                        Log.d("AUTH_DEBUG", "doc exsits "+doc.getData().toString());
//
//                    }
//                    else{
//                        Log.d("AUTH_DEBUG", "no doc");
//                    }
//                }
//                else{
//                    Log.d("AUTH_DEBUG", "task unsecsessfull", task.getException());
//                }
//            }
//        });





        ImageButton profile=(ImageButton) findViewById(R.id.profile);
        profile.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent i =new Intent(StudentHomePage.this, StudentUpdateInfo.class);
                startActivity(i);
            }
        });


        Button search=(Button) findViewById(R.id.search);
        search.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent i =new Intent(StudentHomePage.this, Search.class);
                startActivity(i);
            }
        });

        Button upcoming=(Button) findViewById(R.id.upcoming);
        upcoming.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent i =new Intent(StudentHomePage.this, FutureClassesStudent.class);
                startActivity(i);
            }
        });

        Button passed=(Button) findViewById(R.id.passed);
        passed.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent i =new Intent(StudentHomePage.this, PassedClassesStudent.class);
                startActivity(i);
            }
        });



        Button signout=(Button) findViewById(R.id.signoutbtn);
        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOut();
                Intent i =new Intent(StudentHomePage.this, NewLogin.class);
                startActivity(i);
            }
        });
    }

//    private void signOut() {
//        FirebaseAuth.getInstance().signOut();
//        Intent i =new Intent(StudentHomePage.this, NewLogin.class);
//        startActivity(i);
//    }


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