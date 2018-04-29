package edu.duke.compsci290.dukefoodapp.UserActivities;

import android.content.Context;
import android.content.Intent;
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

import java.util.ArrayList;
import java.util.List;

import edu.duke.compsci290.dukefoodapp.Database.UserDB;
import edu.duke.compsci290.dukefoodapp.R;
import edu.duke.compsci290.dukefoodapp.model.SampleUserFactory;
import edu.duke.compsci290.dukefoodapp.model.UserMalformedException;
import edu.duke.compsci290.dukefoodapp.model.UserParent;

public class UserActivity extends AppCompatActivity {
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
    private UserDB uDB;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        // get id from sign in activity intent
        Intent receivedIntent = this.getIntent();
        user= (UserParent) receivedIntent.getSerializableExtra("user");
        if (user != null) {
            Log.d(TAG, "received user name: " + user.getName());
        }


        setContentView(R.layout.activity_user);

        //set navigation
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        mToggle = new ActionBarDrawerToggle(this,mDrawerLayout,R.string.open,R.string.close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //setup clickables to navigation view

        NavigationView nv = (NavigationView)findViewById(R.id.navigation_view);
        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                Log.d(TAG,"item selected");
                Intent intent;
                switch (menuItem.getItemId()) {
                    case(R.id.home):
                        Log.d(TAG,"Home");
                        intent = new Intent(UserActivity.this, UserActivity.class);
                        intent.putExtra("type",user.getType());
                        intent.putExtra("user",user);
                        startActivity(intent);
                        finish();
                        break;
                    case(R.id.my_orders):
                        if (user.getOrderHistory() == null){
                            CharSequence text = "You Have No Orders!";
                            int duration = Toast.LENGTH_SHORT;
                            Toast toast = Toast.makeText(UserActivity.this, text, duration);
                            toast.show();
                            break;
                        }
                        else{
                            intent = new Intent(UserActivity.this, MyOrdersActivity.class);
                            intent.putExtra("type",user.getType());
                            intent.putExtra("user",user);
                            startActivity(intent);
                            finish();
                            break;
                        }
                    case(R.id.calendar):
                        intent = new Intent(UserActivity.this, CalendarActivity.class);
                        intent.putExtra("type",user.getType());
                        intent.putExtra("user",user);
                        startActivity(intent);
                        finish();
                        break;
                }
                return true;
            }
        });


        //initialize views
        mLogo = findViewById(R.id.userlogo);
        mUsertype = findViewById(R.id.usertype);
        mUsername = findViewById(R.id.username);
        mUserimage = findViewById(R.id.userimage);


        //assign values to views
        mUsertype.setText(user.getType());
        mUsername.setText(user.getName());

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

        // Set up statistics ListView and Adapter
        ListView listView = findViewById(R.id.statisticslistview);
        MyArrayAdapter adapter = new MyArrayAdapter(this, android.R.layout.simple_list_item_1,mStatistics);
        listView.setAdapter(adapter);

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
