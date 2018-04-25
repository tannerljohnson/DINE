package edu.duke.compsci290.dukefoodapp.UserActivities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import edu.duke.compsci290.dukefoodapp.R;
import edu.duke.compsci290.dukefoodapp.model.DiningUser;
import edu.duke.compsci290.dukefoodapp.model.RecipientUser;
import edu.duke.compsci290.dukefoodapp.model.StudentUser;
import edu.duke.compsci290.dukefoodapp.model.UserParent;

//import edu.duke.compsci290.dukefoodapp.model.User;

/**
 * Created by maxwesterkam on 4/4/18.
 */

// name, email, number, type, bio

public class UserPreferencesActivity extends AppCompatActivity {
    private static final String TAG = "UserPreferencesActivity";
    private String[] mTypes;
    private DatabaseReference mDatabase;
    private Button mSubmitButton;
    private TextView mUserEmailTextView;
    private EditText mNameText;
    private EditText mPhoneText;
    private EditText mBioText;
    private String mUserEmail;
    private Spinner mSpinner;
    private String uId;
    private String mUserName;
    private String mUserBio;
    private String mUserPhone;
    private String mFamilySize;
    private String userType;

    private TextView mFamilySizeTextView;
    private EditText mFamilySizeEditText;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 1) get pre existing information from user object stored in firebase and populate the views
        // 2) update user in firebase based on input
        // 3) store user info in shared preferences for faster use later?

        // get intent sent from Sign in
        Intent receivedIntent = this.getIntent();
        uId = (String)receivedIntent.getSerializableExtra("id");
        Log.d(TAG, "received id: " + uId);
        mUserEmail = receivedIntent.getStringExtra("email");
        Log.d(TAG, "received email: " + mUserEmail);

        setContentView(R.layout.activity_userpreferences);

        // set up family size invisible fields
        mFamilySizeTextView = findViewById(R.id.userFamilySizePrompt);
        mFamilySizeEditText = findViewById(R.id.userFamilySize);
        mFamilySizeTextView.setVisibility(View.GONE);
        mFamilySizeEditText.setVisibility(View.GONE);


        //set up spinner
        mTypes = this.getResources().getStringArray(R.array.user_types);
        mSpinner = findViewById(R.id.userTypeSpinner);
        ArrayAdapter<String> mSpinnerTypeAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, mTypes);
        mSpinnerTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(mSpinnerTypeAdapter);

        // set on item click listener for spinner
        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                String selectedItem = parent.getItemAtPosition(position).toString();
                if(selectedItem.equals("recipient")) {
                    mFamilySizeTextView.setVisibility(View.VISIBLE);
                    mFamilySizeEditText.setVisibility(View.VISIBLE);
                } else {
                    mFamilySizeTextView.setVisibility(View.GONE);
                    mFamilySizeEditText.setVisibility(View.GONE);
                }
            } // to close the onItemSelected
            public void onNothingSelected(AdapterView<?> parent)
            {
                // not applicable
            }
        });

        // set up email
        mUserEmailTextView = findViewById(R.id.userEmail);
        mUserEmailTextView.setText(mUserEmail);

        // set up input text fields
        mNameText = findViewById(R.id.userName);
        mPhoneText = findViewById(R.id.userPhone);
        mBioText = findViewById(R.id.userBio);

        // set up submit button
        mSubmitButton = findViewById(R.id.userPrefsSubmitButton);
        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d(TAG, "clicked submit");
                submitUserPreferences();
            }
        });

        // set up database
        mDatabase  = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("users").setValue(uId,null);

    }

    @Override
    protected void onStart() {
       super.onStart();
       // get info from shared pref or Firebase and populate
    }

    // make sure everything is filled out
    // capture user input
    // create user object
    // pass User as intent to UserActivity
    private void submitUserPreferences() {
        if (validateForm()) {
            // create User object
            UserParent newUser = null;
            userType = mSpinner.getSelectedItem().toString();
            if (userType.equals("student")) {
                newUser = new StudentUser();
                newUser.setType("student");
            } else if (userType.equals("recipient")) {
                newUser = new RecipientUser();
                newUser.setType("recipient");
                newUser.setFamilySize(Integer.parseInt(mFamilySize));
            } else  {
                newUser = new DiningUser();
                newUser.setType("admin");
            }

            newUser.setId(uId);
            newUser.setName(mUserName);
            newUser.setBio(mUserBio);
            newUser.setPhone(mUserPhone);
            newUser.setOrderHistory(null);
            newUser.setPendingOrders(null);
            newUser.setEligibleForReward(false);
            newUser.setPoints(0);
            Toast.makeText(this, "Creating User and sending as intent", Toast.LENGTH_SHORT).show();
            writeToFirebase(newUser);
            Intent intent = new Intent(UserPreferencesActivity.this, UserActivity.class);
            intent.putExtra("user", newUser);
            startActivity(intent);
        }
    }

    private void writeToFirebase(UserParent newUser) {
        mDatabase.child("users").child(newUser.getId()).setValue(newUser);
    }

    private boolean validateForm() {
        boolean valid = true;

        mUserName = mNameText.getText().toString();
        if (TextUtils.isEmpty(mUserName)) {
            mNameText.setError("Required.");
            valid = false;
        } else {
            mNameText.setError(null);
        }

        userType = mSpinner.getSelectedItem().toString();
        if (userType.equals("recipient")) {
            mFamilySize = mFamilySizeEditText.getText().toString();
//            int familySizeInt = Integer.parseInt(mFamilySize);
            if (TextUtils.isEmpty(mFamilySize)) {
                mPhoneText.setError("Required. Enter a number from 1 - 8");
                Toast.makeText(this, "Enter a family size from 1 - 8", Toast.LENGTH_SHORT).show();
                valid = false;
            } else {
                try {
                    int familySizeInt = Integer.parseInt(mFamilySize);
                    if (familySizeInt < 1 || familySizeInt > 8) {
                        mPhoneText.setError("Enter a number from 1 - 8");
                        Toast.makeText(this, "Enter a family size from 1 - 8", Toast.LENGTH_SHORT).show();
                        valid = false;
                    } else {
                        mPhoneText.setError(null);
                    }
                } catch (NumberFormatException e) {
                    mPhoneText.setError("Enter a number from 1 - 8");
                    Toast.makeText(this, "Enter a family size from 1 - 8", Toast.LENGTH_SHORT).show();
                    valid = false;
                    e.printStackTrace();
                }
            }
        }

        mUserPhone = mPhoneText.getText().toString();
        if (TextUtils.isEmpty(mUserPhone)) {
            mPhoneText.setError("Required.");
            valid = false;
        } else {
            mPhoneText.setError(null);
        }

        mUserBio = mBioText.getText().toString();
        if (TextUtils.isEmpty(mUserBio)) {
            mBioText.setError("Required.");
            valid = false;
        } else {
            mBioText.setError(null);
        }

        return valid;
    }

    protected int saveInDB() {

        return 0;
    }

    //public User getUser() {

    //}
}
