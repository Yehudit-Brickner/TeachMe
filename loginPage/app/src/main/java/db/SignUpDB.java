package db;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import interfaces.IPerson;

public class SignUpDB {

    final static String COLL_NAME = "users";

    public static String signUpRequest(IPerson person, String password)
    {
        final String[] messRet = {""};
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        mAuth.createUserWithEmailAndPassword(person.getEmail(), password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            if (user == null)
                                return;

                            user.sendEmailVerification()
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                person.setUID(user.getUid());
                                                setPersonData(person);
                                                messRet[0] = "Signed up success. Please Vertify your email.";

                                            }
                                            else
                                                messRet[0] = "Email verification failed - please try again";
                                        }
                                    });
                        } else {
                            messRet[0] = "user creation failed";
                        }
                    }
                });
        mAuth.signOut();
        return messRet[0];
    }

    public static void setPersonData(IPerson person)
    {
        if (person.getUID() == null)
            return;

        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        firestore.collection(COLL_NAME).document(person.getUID()).set(person.getPersonMap());
    }

}
