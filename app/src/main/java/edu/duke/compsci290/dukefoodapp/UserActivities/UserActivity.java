package edu.duke.compsci290.dukefoodapp.UserActivities;

import android.content.Context;
import android.content.Intent;
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
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.firebase.ui.auth.data.model.User;

import java.util.ArrayList;
import java.util.List;

import edu.duke.compsci290.dukefoodapp.R;
import edu.duke.compsci290.dukefoodapp.model.SampleUserFactory;
import edu.duke.compsci290.dukefoodapp.model.UserMalformedException;
import edu.duke.compsci290.dukefoodapp.model.UserParent;

public class UserActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private static final String TAG = "UserActivity";
    //create variables
    private ImageView mLogo;
    private TextView mUsertype;
    private TextView mUsername;
    private ImageView mUserimage;
    private ListView mStatisticsView;
    private Button mMyOrders;
    private Button mCalendar;
    private ArrayList<String> mStatistics;
    private ArrayList<String> mSettings;
    private UserParent user;

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
        SampleUserFactory factory = SampleUserFactory.getInstance();
        user = factory.getSampleStudentUser();


        //initialize views
        mLogo = findViewById(R.id.userlogo);
        mUsertype = findViewById(R.id.usertype);
        mUsername = findViewById(R.id.username);
        mUserimage = findViewById(R.id.userimage);
        mStatisticsView = findViewById(R.id.statisticslistview);
        mMyOrders = findViewById(R.id.myorders);
        mCalendar = findViewById(R.id.calendar);

        //assign values to views
        mUsertype.setText(user.getType());
        mUsername.setText(user.getName());

        //make statistics and settings array
        try {
            mStatistics = user.getStatistics();
        } catch (UserMalformedException e) {
            e.printStackTrace();
        }
        mSettings = user.getSettings();

        //set up spinner
        Spinner mSpinner = (Spinner) findViewById(R.id.userpagesetting);
        ArrayAdapter<String> mSettingAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, mSettings);
        mSettingAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(mSettingAdapter);

        // Set up ListView and Adapter
        ListView listView = findViewById(R.id.statisticslistview);
        MyArrayAdapter adapter = new MyArrayAdapter(this, android.R.layout.simple_list_item_1,mStatistics);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);

        //TODO:Make these work with the spinner and remove buttons
        //set onclick for calendar
        Button calendar = findViewById(R.id.calendar);
        calendar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d("tag1:", "clicked calendar");
                toCalendar();
            }
        });

        //set onclick for MyOrders
        Button myOrders = findViewById(R.id.myorders);
        myOrders.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d("tag1:", "clicked calendar");
                toMyOrders(user);
            }
        });


    }

    private void toMyOrders(UserParent user) {
        Intent intent = new Intent(this, MyOrdersActivity.class);
        intent.putExtra("type",user.getType());
        intent.putExtra("user",user);
        startActivity(intent);
    }

    public void toCalendar(){
        Intent intent = new Intent(this, CalendarActivity.class);
        intent.putExtra("type",user.getType());
        intent.putExtra("user",user);
        startActivity(intent);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }
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
