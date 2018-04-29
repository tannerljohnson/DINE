package edu.duke.compsci290.dukefoodapp.UserActivities;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Size;
import android.view.Gravity;
import android.view.MenuItem;
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

import edu.duke.compsci290.dukefoodapp.Database.OrderDB;
import edu.duke.compsci290.dukefoodapp.R;
import edu.duke.compsci290.dukefoodapp.model.IUser;
import edu.duke.compsci290.dukefoodapp.model.UserParent;

public class CalendarActivity extends AppCompatActivity {
    //This activity allows the user to decide what day to choose an order
    private CalendarView mCalendarView;
    private UserParent user;
    private String Tag = "CalendarActivity";
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // retrieve intent info
        //Grab Intent information
        Intent receivedIntent = this.getIntent();
        user = (UserParent)receivedIntent.getSerializableExtra("user");
        setContentView(R.layout.activity_calendar);

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
                Log.d(Tag,"item selected");
                Intent intent;
                switch (menuItem.getItemId()) {
                    case(R.id.home):
                        Log.d(Tag,"Home");
                        intent = new Intent(CalendarActivity.this, UserActivity.class);
                        intent.putExtra("type",user.getType());
                        intent.putExtra("user",user);
                        startActivity(intent);
                        finish();
                        break;
                    case(R.id.my_orders):
                        if (user.getOrderHistory() == null){
                            CharSequence text = "You Have No Orders!";
                            int duration = Toast.LENGTH_SHORT;
                            Toast toast = Toast.makeText(CalendarActivity.this, text, duration);
                            toast.show();
                            break;
                        }
                        else{
                            intent = new Intent(CalendarActivity.this, MyOrdersActivity.class);
                            intent.putExtra("type",user.getType());
                            intent.putExtra("user",user);
                            startActivity(intent);
                            finish();
                            break;
                        }
                    case(R.id.calendar):
                        intent = new Intent(CalendarActivity.this, CalendarActivity.class);
                        intent.putExtra("type",user.getType());
                        intent.putExtra("user",user);
                        startActivity(intent);
                        finish();
                        break;
                }
                return true;
            }
        });

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
                String date = Integer.toString(i)+ Integer.toString(i1) + Integer.toString(i2);
                Log.d("Tag", date);
                Intent intent = new Intent(CalendarActivity.this, DayActivity.class);
                intent.putExtra("user",user);
                intent.putExtra("date", date);
                startActivity(intent);
            }
        });

    }

}
