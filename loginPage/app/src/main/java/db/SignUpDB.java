package db;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Map;

import interfaces.IPerson;

public class SignUpDB  {

    final static String COLL_NAME = "users";
    final static FirebaseAuth mAuth = FirebaseAuth.getInstance();

    public static String signUpRequest(IPerson person, String password) {
//        if (!isGoodPersonData(person))
//            return "There are some missing info, please reinsert and try again.";

        final String[] messRet = {""};

        mAuth.createUserWithEmailAndPassword(person.getEmail(), password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            if (user == null)
                            {
                                messRet[0] = "try again";
                                return;
                            }

                            user.sendEmailVerification()
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                person.setUID(user.getUid());
//                                                setPersonData(person);
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
        return messRet[0];
    }

//    public static void setPersonData(IPerson person)
//    {
//        if (person.getUID() == null || person.getUID().isEmpty())
//            return;
//
//        CollectionReference usersCollection = FirebaseFirestore.getInstance().collection(COLL_NAME);
//        usersCollection.document(person.getUID()).set(person.getPersonMap());
//    }
//
//    public static void setPersonData(IPerson person, boolean is_tutor, boolean is_student)
//    {
//        if (person.getUID() == null || person.getUID().isEmpty())
//            return;
//
//        CollectionReference usersCollection = FirebaseFirestore.getInstance().collection(COLL_NAME);
//        Map<String, Object> map = person.getPersonMap();
//        map.put("is_tutor", is_tutor);
//        map.put("is_student", is_student);
//        usersCollection.document(person.getUID()).set(map);
//    }
//
//    public static boolean isGoodPersonData(IPerson person)
//    {
//        if (person == null)
//            return true;
//
//        return (!person.getFirstName().isEmpty() && !person.getLastName().isEmpty()
//                && !person.getEmail().isEmpty() && !person.getPhoneNumber().isEmpty());
//    }

}
