package controller;

import android.util.Log;
import android.widget.RadioButton;

import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

public class LoginController {


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

    public static boolean googleClicked(RadioButton radiobtnS, RadioButton radiobtnT){
        if ( radiobtnS.isChecked() || radiobtnT.isChecked()) {
            Log.d("AUTH_DEBUG", "pressed the google button");
            return true;
        } else {
            Log.d("AUTH_DEBUG", "pressed the google button");
            return false;
        }
    }






}
