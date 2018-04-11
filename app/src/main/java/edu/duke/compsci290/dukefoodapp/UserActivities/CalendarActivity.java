package edu.duke.compsci290.dukefoodapp.UserActivities;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Size;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import edu.duke.compsci290.dukefoodapp.R;

public class CalendarActivity extends AppCompatActivity {
    private String[] mBooks;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        mBooks = new String[]{"1","2","3","4","5","6","7","1","2","3","4","5","6","7","1","2","3","4","5","6","7","1","2","3","4","5","6","7","1","2","3","4","5","6","7"};
        GridView gridView = (GridView)findViewById(R.id.calendar_gridview);
        BooksAdapter booksAdapter = new BooksAdapter(this, mBooks);
        gridView.setAdapter(booksAdapter);
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
        public Object getItem(int position) {
            return null;
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
