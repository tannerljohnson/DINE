package edu.duke.compsci290.dukefoodapp.UserActivities;

import android.content.Intent;
import android.nfc.Tag;
import android.os.AsyncTask;
import android.os.Parcelable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import edu.duke.compsci290.dukefoodapp.Database.IOnDatabaseRead;
import edu.duke.compsci290.dukefoodapp.Database.OrderDB;
import edu.duke.compsci290.dukefoodapp.R;
import edu.duke.compsci290.dukefoodapp.model.IDay;
import edu.duke.compsci290.dukefoodapp.model.IUser;
import edu.duke.compsci290.dukefoodapp.model.Order;
import edu.duke.compsci290.dukefoodapp.model.SampleDayFactory;
import edu.duke.compsci290.dukefoodapp.model.UserParent;

import static edu.duke.compsci290.dukefoodapp.UserActivities.DayActivityAdapter.sTAG;

public class DayActivity extends AppCompatActivity {
    //this activity is used for the user to sign up for items on the specified day
    private TextView dateTextView;
    private RecyclerView rv;
    public UserParent user;
    public ArrayList<Order> orderHistory;
    public String date;
    private LinearLayout dynamicLL;
    private OrderDB oDB;
    public static final String mTAG = "DAY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day);
        //Grab Intent information passed from CalendarActivity
        final Intent receivedIntent = this.getIntent();
        user = (UserParent) receivedIntent.getSerializableExtra("user");
        date = receivedIntent.getStringExtra("date");
        Log.d(mTAG,date);


        //grab views
        dateTextView = findViewById(R.id.day_textview);
        dateTextView.setText(date);
        oDB = OrderDB.getInstance();
        oDB.setCustomEventListener(new IOnDatabaseRead() {
            @Override
            public void onEvent() {
                orderHistory = oDB.getOrdersByDate();
                Log.d(mTAG,"# of orders: " + Integer.toString(orderHistory.size()));
                rv = findViewById(R.id.day_recyclerview);
                rv.setAdapter(new DayActivityAdapter(DayActivity.this,orderHistory,user.getType()));
                rv.setLayoutManager(new LinearLayoutManager(DayActivity.this));
            }
        });
        oDB.setOrdersByDate(date);





        //TODO: Make the Dialogues for each user type
        dynamicLL = findViewById(R.id.day_activity_dynamic_ll);
        Button button = new Button(this);
        if(user.getType().equals("admin")){
            button.setText("New Order");
            button.setTextSize(5);
            button.setGravity(Gravity.RIGHT);
            button.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    // creates a dialogue
                    final Order order = new Order();
                    AlertDialog.Builder mBuilder = new AlertDialog.Builder(DayActivity.this);
                    View mView = getLayoutInflater().inflate(R.layout.create_order,null);
                    Button submit = mView.findViewById(R.id.button_create_order);
                    final EditText diningName = mView.findViewById(R.id.create_order_input_location);
                    final EditText allergens = mView.findViewById(R.id.create_order_input_allergens);
                    final EditText pickup = mView.findViewById(R.id.create_order_input_pickuptime);
                    mBuilder.setView(mView);
                    final AlertDialog dialog = mBuilder.create();
                    dialog.show();
                    submit.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            // submits the info
                            order.setDiningName(diningName.getText().toString());
                            order.setAllergens(allergens.getText().toString());
                            order.setPickupTime(pickup.getText().toString());
                            order.setDate(date);
                            //create unique order id
                            String id = UUID.randomUUID().toString();
                            order.setId(id);
                            oDB.setObject(order);
                            oDB.writeToDatabase();
                            //toast order sumbitted
                            Log.d(mTAG,"order submitted");
                            dialog.dismiss();
                            Intent intent = new Intent(DayActivity.this, DayActivity.class);
                            intent.putExtra("user" ,user);
                            intent.putExtra("date", date);
                            startActivity(intent);
                            finish();
                        }
                    });
                }
            });
            dynamicLL.addView(button);
        }




}



}
