package edu.duke.compsci290.dukefoodapp.UserActivities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
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

import com.firebase.ui.auth.data.model.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

import edu.duke.compsci290.dukefoodapp.Database.UserDB;
import edu.duke.compsci290.dukefoodapp.R;
import edu.duke.compsci290.dukefoodapp.model.SampleUserFactory;
import edu.duke.compsci290.dukefoodapp.model.UserMalformedException;
import edu.duke.compsci290.dukefoodapp.model.UserParent;

public class UserActivity extends AppCompatActivity{
    private static final String TAG = "UserActivity";
    //create variables
    private ImageView mLogo;
    private TextView mUsertype;
    private TextView mUsername;
    private ImageView mUserimage;
    private Button mSetting;
    private ArrayList<String> mStatistics;
    private ArrayList<String> mSettings;
    private UserParent user;
    private byte[] mImageByteArray;
    private Bitmap mImageBitmap;
    private FirebaseStorage mStorage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // get id from sign in activity intent
        Intent receivedIntent = this.getIntent();
        user= (UserParent) receivedIntent.getSerializableExtra("user");
        if (user != null) {
            Log.d(TAG, "received user name: " + user.getName());
        }
        mStorage = FirebaseStorage.getInstance();

//        mImageByteArray = user.getImageByteArray();
        // convert byte array back to bitmap
//        mImageBitmap = BitmapFactory.decodeByteArray(mImageByteArray, 0, mImageByteArray.length);


        setContentView(R.layout.activity_user);

        //initialize views
        mLogo = findViewById(R.id.userlogo);
        mUsertype = findViewById(R.id.usertype);
        mUsername = findViewById(R.id.username);
        mUserimage = findViewById(R.id.userimage);


        queryAndSetPicture();



        //assign values to views
        mUsertype.setText(user.getType());
        // I don't like how this looks^ -tlj
        mUsertype.setVisibility(View.GONE);
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
        if(user.getSettings() == null){
            mSettings = new ArrayList<String>();
            mSettings.add("No Settings");
        }
        else{
            mSettings = user.getSettings();
        }

        //TODO: create dialog instead of spinner (looks better)
        mSetting = findViewById(R.id.user_activity_settings);
        mSetting.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // creates a dialogue
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(UserActivity.this);
                View mView = getLayoutInflater().inflate(R.layout.user_settings,null);
                final ListView lv = mView.findViewById(R.id.user_settings_list_view);
                MyArrayAdapter adapter = new MyArrayAdapter(UserActivity.this, android.R.layout.simple_list_item_1,mSettings);
                lv.setAdapter(adapter);
                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {

                        Object o = lv.getItemAtPosition(position);
                        String activity;
                        String prefix =  "edu.duke.compsci290.dukefoodapp.UserActivities.";
                        if (o.equals("My Account")){
                            activity = prefix +"UserActivity";
                            Log.d(TAG,activity);
                        }
                        else{
                            activity = prefix + o.toString().replace(" ","") + "Activity";
                            Log.d(TAG,activity);
                        }

                        try {
                            Class<?> c = Class.forName(activity);
                            if (!o.equals("My Orders") | user.getOrderHistory() != null){
                                Intent intent = new Intent(UserActivity.this, c);
                                intent.putExtra("type",user.getType());
                                intent.putExtra("user",user);
                                startActivity(intent);
                            }
                        } catch (ClassNotFoundException ignored) {
                        }
                    }
                });
                mBuilder.setView(mView);
                AlertDialog dialog = mBuilder.create();
                dialog.show();
            }
        });



        // Set up statistics ListView and Adapter
        ListView listView = findViewById(R.id.statisticslistview);
        MyArrayAdapter adapter = new MyArrayAdapter(this, android.R.layout.simple_list_item_1,mStatistics);
        listView.setAdapter(adapter);

        //dynamically create button for testing purposes
        LinearLayout ll = findViewById(R.id.user_activity_ll);
        Button dbTest = new Button(this);
        dbTest.setText("Database Test");
        ll.addView(dbTest);
        dbTest.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                UserDB db = UserDB.getInstance();
                user.setName("Tevin");
                db.setObject(user);
                db.writeToDatabase();
                db.readFromDatabase();
                user = (UserParent) db.getObject();
                Log.d(TAG, user.getName());
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
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
//                mUserimage.setVisibility(View.INVISIBLE);
            }
        });
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
}
