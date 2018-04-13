package edu.duke.compsci290.dukefoodapp.UserActivities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import edu.duke.compsci290.dukefoodapp.R;
import edu.duke.compsci290.dukefoodapp.model.IDay;
import edu.duke.compsci290.dukefoodapp.model.SampleDayFactory;

public class DayActivity extends AppCompatActivity {
    private TextView dateTextView;
    private RecyclerView rv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // TODO: retrieve user's ID to send to rv adapter (controller that will write to db) -- pass as intent from CalendarActivity
        // TODO: for now, we use a simple string
        String mUserId = "sampleUserId1234";

        setContentView(R.layout.activity_day);
        SampleDayFactory factory = SampleDayFactory.getInstance();
        IDay day = factory.getDay();

        //grab views
        dateTextView = findViewById(R.id.day_textview);
        rv = findViewById(R.id.day_recyclerview);

        // set textview
        dateTextView.setText(day.getDate());

        // set up recycler view
        rv.setAdapter(new DayActivityAdapter(this, day, mUserId));
        rv.setLayoutManager(new LinearLayoutManager(this));
    }
}
