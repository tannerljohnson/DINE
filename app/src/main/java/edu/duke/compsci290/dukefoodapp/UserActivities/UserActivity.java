package edu.duke.compsci290.dukefoodapp.UserActivities;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.data.model.User;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

import edu.duke.compsci290.dukefoodapp.Database.UserDB;
import edu.duke.compsci290.dukefoodapp.R;
import edu.duke.compsci290.dukefoodapp.login.ChooserActivity;
import edu.duke.compsci290.dukefoodapp.login.GoogleSignInActivity;
import edu.duke.compsci290.dukefoodapp.model.DiningUser;
import edu.duke.compsci290.dukefoodapp.model.RecipientUser;
import edu.duke.compsci290.dukefoodapp.model.SampleUserFactory;
import edu.duke.compsci290.dukefoodapp.model.StudentUser;
import edu.duke.compsci290.dukefoodapp.model.UserMalformedException;
import edu.duke.compsci290.dukefoodapp.model.UserParent;

public class UserActivity extends AppCompatActivity {
    private static final String TAG = "UserActivity";
    //create variables
    private ImageView mLogo;
    private TextView mUsertype;
    private TextView mUsername;
    private ImageView mUserimage;
    private Button mRefresh;
    private ArrayList<String> mStatistics;
    private ArrayList<String> mSettings;
    private UserParent user;
    private byte[] mImageByteArray;
    private Bitmap mImageBitmap;
    private FirebaseStorage mStorage;

    // for sign out
    private FirebaseAuth mAuth;
    private GoogleSignInClient mGoogleSignInClient;



    private UserDB uDB;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        // get id from sign in activity intent
        Intent receivedIntent = this.getIntent();

        // check intent for log out
        user = (UserParent) receivedIntent.getSerializableExtra("user");
        if (user != null) {
            Log.d(TAG, "received user name: " + user.getName());
        }
        mStorage = FirebaseStorage.getInstance();

//        mImageByteArray = user.getImageByteArray();
        // convert byte array back to bitmap
//        mImageBitmap = BitmapFactory.decodeByteArray(mImageByteArray, 0, mImageByteArray.length);

        setContentView(R.layout.activity_user);

        //set navigation
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //setup clickables to navigation view

        NavigationView nv = (NavigationView) findViewById(R.id.navigation_view);
        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                Log.d(TAG, "item selected");
                Intent intent;
                switch (menuItem.getItemId()) {
                    case (R.id.home):
                        Log.d(TAG, "Home");
                        intent = new Intent(UserActivity.this, UserActivity.class);
                        intent.putExtra("type", user.getType());
                        intent.putExtra("user", user);
                        startActivity(intent);
                        finish();
                        break;
                    case (R.id.logout):
                        Log.d(TAG, "Log Out");
                        signOut();
                        intent = new Intent(UserActivity.this, ChooserActivity.class);
                        startActivity(intent);
                        finish();
                        break;
                    case (R.id.my_orders):
                        if (user.getOrderHistory() == null) {
                            CharSequence text = "You Have No Orders!";
                            int duration = Toast.LENGTH_SHORT;
                            Toast toast = Toast.makeText(UserActivity.this, text, duration);
                            toast.show();
                            break;
                        } else {
                            intent = new Intent(UserActivity.this, MyOrdersActivity.class);
                            intent.putExtra("type", user.getType());
                            intent.putExtra("user", user);
                            startActivity(intent);
                            finish();
                            break;
                        }
                    case (R.id.calendar):
                        intent = new Intent(UserActivity.this, CalendarActivity.class);
                        intent.putExtra("type", user.getType());
                        intent.putExtra("user", user);
                        startActivity(intent);
                        finish();
                        break;
                }
                return true;
            }
        });


        //initialize views
        mLogo = findViewById(R.id.userlogo);
//        mUsertype = findViewById(R.id.usertype);
        mUsername = findViewById(R.id.username);
        mUserimage = findViewById(R.id.userimage);


        queryAndSetPicture();

        mUsername.setText("Welcome " + user.getName() + "!");

        //generate statistics if not generated
        try {
            mStatistics = user.getStatistics();
        } catch (UserMalformedException e) {
            e.printStackTrace();
            mStatistics = new ArrayList<String>();
            mStatistics.add("No Statistics");
        }
        //generate settings if not generated
        if (user.getSettings() == null) {
            mSettings = new ArrayList<String>();
            mSettings.add("No Settings");
        } else {
            mSettings = user.getSettings();
        }

        // Set up statistics ListView and Adapter
        ListView listView = findViewById(R.id.statisticslistview);
        MyArrayAdapter adapter = new MyArrayAdapter(this, android.R.layout.simple_list_item_1, mStatistics);
        listView.setAdapter(adapter);

        mRefresh = findViewById(R.id.userActivityRefresh);
        mRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserActivity.this, GoogleSignInActivity.class);
                startActivity(intent);
            }
        });
    }


    // query cloud storage for user's picture
    private void queryAndSetPicture() {
        StorageReference storageRef = mStorage.getReference().child(user.getId());
        final long ONE_MEGABYTE = 1024 * 1024;
        storageRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                // Data for "images/island.jpg" is returns, use this as needed
                mImageByteArray = bytes;
                mImageBitmap = BitmapFactory.decodeByteArray(mImageByteArray, 0, mImageByteArray.length);
                mUserimage.setImageBitmap(mImageBitmap);
                mUserimage.setBackgroundColor(80000000);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
//                mUserimage.setBackgroundResource(#80000000);
            }
        });

    }

    private void signOut() {
        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        mAuth = FirebaseAuth.getInstance();
        // Firebase sign out
        mAuth.signOut();

        // Google sign out
        mGoogleSignInClient.signOut();
    }


    //ArrayAdapter used for settings and statistics
    public static class MyArrayAdapter extends ArrayAdapter<String> {

        private Context mContext;
        private List<String> mstatistics;

        public MyArrayAdapter(Context context, int resource, ArrayList<String> statistics) {
            super(context, resource,statistics);
            mContext = context;
            mstatistics = statistics;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = convertView;
            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(android.R.layout.simple_list_item_1, null);
            }
            ((TextView) view.findViewById(android.R.id.text1)).setText(mstatistics.get(position));
            return view;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
