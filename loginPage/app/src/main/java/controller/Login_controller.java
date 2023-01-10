package controller;

import android.content.Intent;
import android.util.Log;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import model.Login_model;

public class Login_controller {


    public static void StudentChecked(RadioButton radiobtnS, RadioButton radiobtnT){
        if(radiobtnT.isChecked()){
            radiobtnT.setChecked(false);
            radiobtnS.setChecked(true);
        }
        else{
            radiobtnS.setChecked(true);
        }
    }

    public static void tutorChecked(RadioButton radiobtnS, RadioButton radiobtnT){
        if(radiobtnS.isChecked()){
            radiobtnS.setChecked(false);
            radiobtnT.setChecked(true);
        }
        else{
            radiobtnT.setChecked(true);
        }
    }

    public static boolean googleClicked(RadioButton radiobtnS, RadioButton radiobtnT, GoogleSignInClient gsc, GoogleSignInOptions gso){
        if ( radiobtnS.isChecked() || radiobtnT.isChecked()) {
            Log.d("AUTH_DEBUG", "pressed the google button");
            return true;
        } else {
            Log.d("AUTH_DEBUG", "pressed the google button");
            return false;
        }
    }






}
