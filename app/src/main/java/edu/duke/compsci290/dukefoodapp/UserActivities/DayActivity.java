package edu.duke.compsci290.dukefoodapp.UserActivities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import edu.duke.compsci290.dukefoodapp.R;
import edu.duke.compsci290.dukefoodapp.model.Day;
import edu.duke.compsci290.dukefoodapp.model.IDay;
import edu.duke.compsci290.dukefoodapp.model.SampleDayFactory;

public class DayActivity extends AppCompatActivity {
    private TextView date;
    private ListView orders;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day);
        SampleDayFactory factory = SampleDayFactory.getInstance();
        IDay user = factory.getDay();

        //grab views
        date = findViewById(R.id.day_textview);
        orders = findViewById(R.id.day_listview);

        // set textview
        date.setText(user.getDate());

    }
}
