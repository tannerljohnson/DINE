package edu.duke.compsci290.dukefoodapp.UserActivities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import edu.duke.compsci290.dukefoodapp.R;

//import edu.duke.compsci290.dukefoodapp.model.User;

/**
 * Created by maxwesterkam on 4/4/18.
 */

// name, email, number, type, bio

public class UserPreferencesActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 1) get pre existing information from user object stored in firebase and populate the views
        // 2) update user in firebase based on input
        // 3) store user info in shared preferences for faster use later?

        setContentView(R.layout.activity_userpreferences);
        DatabaseReference mDatabase;
        mDatabase  = FirebaseDatabase.getInstance().getReference();

    }

    @Override
    protected void onStart() {
       super.onStart();
       // get info from shared pref or Firebase and populate
    }

    protected int saveInDB() {

        return 0;
    }

    //public User getUser() {

    //}
}
