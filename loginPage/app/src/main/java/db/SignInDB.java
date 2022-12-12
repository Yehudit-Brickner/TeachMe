package db;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import interfaces.IPerson;

public class SignInDB
{
    final static String COLL_NAME = "users";

    public static IPerson signInRequest(String email, String password)
    {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        IPerson person;
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information

                            FirebaseUser user = mAuth.getCurrentUser();
                            String uID = user.getUid();
                            

                        } else {
                            // If sign in fails, display a message to the user.

                        }
                    }
                });
        return null;
    }
}
