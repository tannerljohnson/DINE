package edu.duke.compsci290.dukefoodapp.UserActivities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import edu.duke.compsci290.dukefoodapp.R;
import edu.duke.compsci290.dukefoodapp.model.IDay;
import edu.duke.compsci290.dukefoodapp.model.IUser;
import edu.duke.compsci290.dukefoodapp.model.SampleDayFactory;
import edu.duke.compsci290.dukefoodapp.model.UserParent;

import static edu.duke.compsci290.dukefoodapp.UserActivities.DayActivityAdapter.sTAG;

public class DayActivity extends AppCompatActivity {
    private TextView dateTextView;
    private RecyclerView rv;
    public IUser user;
    public List<String> orderHistory;
    public String date;

    public static final String mTAG = "DAY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Grab Intent information passed from CalendarActivity
        Intent receivedIntent = this.getIntent();
        user = (IUser)receivedIntent.getSerializableExtra("user");
        date = receivedIntent.getStringExtra("date");

        // check some intent data
        Log.d("tag1", user.getName().toString() + " requests data for date: " + date);

        // TODO: retrieve user's ID to send to rv adapter (controller that will write to db) -- pass as intent from CalendarActivity
        // TODO: for now, we use a simple string
//        String mUserId = "sampleUserId1234";
        String mUserId = user.getId();
        Log.d(mTAG, "userId is: " + mUserId + " of type: " + user.getType());

        setContentView(R.layout.activity_day);

        // TODO: QUERY db for Day instead of using Factory ... actually build Day in Adapter instead
        // TODO: just use here for testing, but you really want to send DayActivityAapter(this, date, mUserId) and build Day obj in Adapter
        SampleDayFactory factory = SampleDayFactory.getInstance();
        IDay day = factory.getDay();

        //grab views
        dateTextView = findViewById(R.id.day_textview);
        rv = findViewById(R.id.day_recyclerview);

        // set textview
        dateTextView.setText(day.getDate());

        Log.d(mTAG, "setting up rv in DayActivity...");
        // set up recycler view

        // pass this boolean array to keep track of checked items

        rv.setAdapter(new DayActivityAdapter(this, day, mUserId));
        rv.setLayoutManager(new LinearLayoutManager(this));
    }


    public void submitSelectedOrders(View view) {
        for (int i=0; i<DayActivityAdapter.mCheckedArray.size(); i++) {
            if (DayActivityAdapter.mCheckedArray.get(i) == true) {
                // hit (i.e. something is checked)
                Toast.makeText(DayActivity.this, "sending this order to the database!", Toast.LENGTH_SHORT).show();
                return;
            }
        }
        Toast.makeText(this, "please choose an answer(s)", Toast.LENGTH_SHORT).show();
    }
}
