package edu.duke.compsci290.dukefoodapp.UserActivities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Size;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import edu.duke.compsci290.dukefoodapp.R;
import edu.duke.compsci290.dukefoodapp.model.IUser;
import edu.duke.compsci290.dukefoodapp.model.UserParent;

public class CalendarActivity extends AppCompatActivity {
    private String[] mBooks;
    public UserParent user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // retrieve intent info
        //Grab Intent information
        Intent receivedIntent = this.getIntent();
        user = (UserParent)receivedIntent.getSerializableExtra("user");

        setContentView(R.layout.activity_calendar);
        mBooks = new String[]{"1","2","3","4","5","6","7","1","2","3","4","5","6","7","1","2","3","4","5","6","7","1","2","3","4","5","6","7","1","2","3","4","5","6","7"};
        GridView gridView = (GridView)findViewById(R.id.calendar_gridview);
        BooksAdapter booksAdapter = new BooksAdapter(this, mBooks);
        gridView.setAdapter(booksAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Get the GridView selected/clicked item text
                String selectedDate = parent.getItemAtPosition(position).toString();
                Toast.makeText(CalendarActivity.this, "clicked day: " + selectedDate, Toast.LENGTH_SHORT).show();
                // Display the selected/clicked item text and position on TextView
                toDayActivity(user, selectedDate);
            }
        });
    }

    // use this method as an onClick listener for each day
    private void toDayActivity(UserParent user, String selectedDate) {
        Intent intent = new Intent(this, DayActivity.class);
        intent.putExtra("type",user.getType());
        intent.putExtra("user",user);
        intent.putExtra("date", selectedDate);
        startActivity(intent);
    }

    public class BooksAdapter extends BaseAdapter {

        private final Context mContext;
        private final String[] books;

        // 1
        public BooksAdapter(Context context, String[] books) {
            this.mContext = context;
            this.books = books;
        }

        // 2
        @Override
        public int getCount() {
            return books.length;
        }

        // 3
        @Override
        public long getItemId(int position) {
            return 0;
        }

        // 4
        @Override
        public String getItem(int position) {
            return books[position];
        }

        // 5
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TextView dummyTextView = new TextView(mContext);
            dummyTextView.setText(String.valueOf(position));
            dummyTextView.setBackgroundResource(R.drawable.grid_border);
            dummyTextView.setTextSize(20);
            dummyTextView.setGravity(Gravity.CENTER);
            dummyTextView.setHeight(250);
            return dummyTextView;
        }

    }
}
