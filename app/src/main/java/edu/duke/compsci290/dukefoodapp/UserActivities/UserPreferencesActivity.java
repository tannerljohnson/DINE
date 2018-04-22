package edu.duke.compsci290.dukefoodapp.UserActivities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.firebase.ui.auth.data.model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import edu.duke.compsci290.dukefoodapp.R;
import edu.duke.compsci290.dukefoodapp.model.IUser;

//import edu.duke.compsci290.dukefoodapp.model.User;

/**
 * Created by maxwesterkam on 4/4/18.
 */

// name, email, number, type, bio

public class UserPreferencesActivity extends AppCompatActivity {

    private static final String TAG = "UserPreferencesActivity";
    public User mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 1) get pre existing information from user object stored in firebase and populate the views
        // 2) update user in firebase based on input
        // 3) store user info in shared preferences for faster use later?

        // get intent sent from Sign in
        Intent receivedIntent = this.getIntent();
        String uId = (String)receivedIntent.getSerializableExtra("id");
        Log.d(TAG, "received id: " + uId);

        setContentView(R.layout.activity_userpreferences);
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mUser = dataSnapshot.getValue(User.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });



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
