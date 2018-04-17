package edu.duke.compsci290.dukefoodapp.UserActivities;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Size;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CalendarView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.data.model.User;

import java.util.Calendar;
import java.util.Date;

import edu.duke.compsci290.dukefoodapp.R;
import edu.duke.compsci290.dukefoodapp.model.IUser;
import edu.duke.compsci290.dukefoodapp.model.UserParent;

public class CalendarActivity extends AppCompatActivity {
    private CalendarView mCalendarView;
    private UserParent user;
    private String Tag = "CalendarActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // retrieve intent info
        //Grab Intent information
        Intent receivedIntent = this.getIntent();
        user = (UserParent)receivedIntent.getSerializableExtra("user");
        setContentView(R.layout.activity_calendar);



        mCalendarView = (CalendarView) findViewById(R.id.calendarView);
        //TODO: access database and set up color of day based on availability of order.
//        setUpColors();
        Date currentTime = Calendar.getInstance().getTime();
        Log.d(Tag,currentTime.toString());
        //TODO: set up the Calendar to have fewer months present
//        mCalendarView.setMinDate();
//        mCalendarView.setMaxDate();
        mCalendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
                /// want "month day, year"
                String date = "i + i1 + i2";
                Log.d("Tag", date);
                Intent intent = new Intent(CalendarActivity.this, DayActivity.class);
                intent.putExtra("user",user);
                intent.putExtra("date", date);
                startActivity(intent);
            }
        });

    }

}
