package edu.duke.compsci290.dukefoodapp.UserActivities;

import android.annotation.SuppressLint;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.duke.compsci290.dukefoodapp.R;
import edu.duke.compsci290.dukefoodapp.Room.OrderDb;
import edu.duke.compsci290.dukefoodapp.model.IUser;
import edu.duke.compsci290.dukefoodapp.model.Order;
import edu.duke.compsci290.dukefoodapp.model.SampleOrderFactory;

public class MyOrdersActivity extends AppCompatActivity {
    //This activity is for the user to look at their current orders, and the progress of the order
    //maybe save the specific users order in SQLite, and update the orders the user already has.
    //helps with the no connection problems
    private static final String TAG = "MyOrdersActivity";
    public RecyclerView rv;
    public IUser user;
    public List<String> pendingOrderIds;
    private boolean wifiConnected;
    private String userId;

    public List<Order> mPendingOrders;

    private OrderDb mDatabase;
    private DatabaseReference mFirebaseReference;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_orders);

//        // set up local db
//        if (mDatabase == null) {
//            mDatabase = Room.databaseBuilder(getApplicationContext(),
//                    OrderDb.class, "orders").build();
//        }

        //Grab Intent information
        Intent receivedIntent = this.getIntent();
        user = (IUser)receivedIntent.getSerializableExtra("user");
        pendingOrderIds = user.getPendingOrders();

        // check some intent data
        Log.d(TAG, user.getName().toString());


        //set up recycler view
        rv = findViewById(R.id.my_orders_recycler_view);
        rv.setAdapter(new OrderAdapter(this, pendingOrderIds));
        rv.setLayoutManager(new LinearLayoutManager(this));

//        getUserOrdersFromFirebase();
    }




    // NOT USED
    private void roomStoreOrders() {
        if (mDatabase == null) {
            mDatabase = Room.databaseBuilder(getApplicationContext(),
                    OrderDb.class, "orders").build();
        }
        List<Order> oldPendingOrders = mDatabase.orderDao().findPendingStudentOrdersById(user.getId());
        for (Order oldOrder : oldPendingOrders) {
            mDatabase.orderDao().delete(oldOrder);
        }

        if (mPendingOrders != null) { // store if we have pending orders
            for (Order o : mPendingOrders) {
                mDatabase.orderDao().insertOrder(o);
            }
        }
    }


    // get pending Order objects from Firebase before storing locally
    public void getUserOrdersFromFirebase() {
        mFirebaseReference = FirebaseDatabase.getInstance().getReference();
        Query query = mFirebaseReference.child("orders").orderByChild("studentId").equalTo(user.getId()); // ONLY IF STUDENT
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Log.d(TAG, "snapshot does exist");
                    mPendingOrders = new ArrayList<>();
                    for (DataSnapshot issue : dataSnapshot.getChildren()) {
                        Map<String, Object> map = (HashMap<String, Object>)issue.getValue();
                        String orderId = map.get("id").toString();
                        Log.d(TAG, "user's next pending orderId is: " +orderId);
                        mPendingOrders.add((Order) issue.getValue(Order.class));
                    }
                } else { // does not exist
                    Log.d(TAG, "snapshot does NOT exist");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(TAG, "onCancelled", databaseError.toException());
            }
        });
    }



    public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {
        private Context mContext;
        private List<String> pendingOrders;


        public class ViewHolder extends RecyclerView.ViewHolder {
            public TextView dining;
            public TextView student;
            public TextView recipient;
            public LinearLayout orderLL;
            public TextView orderid;

            public ViewHolder(View itemView) {
                super(itemView);
                this.dining = itemView.findViewById(R.id.dining_textView);
                this.student = itemView.findViewById(R.id.student_textView);
                this.recipient = itemView.findViewById(R.id.recipient_textView);
                this.orderLL = itemView.findViewById(R.id.order_image_linear_layout);
                this.orderid = itemView.findViewById(R.id.order_layout_order_id);
            }
        }

        public OrderAdapter(Context context, List<String> pendingOrders) {
            this.mContext = context;
            this.pendingOrders = pendingOrders;
        }



        @Override
        public int getItemCount(){
            return pendingOrders.size();
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
            //create inflater and viewholder
            LayoutInflater mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = mInflater.inflate(R.layout.order_layout, parent, false);
            Log.d("tag1", row.toString());
            final ViewHolder orderHolder = new ViewHolder(row);
            return orderHolder;
        }

        private Order grabOrder( int pos) {
            //code below for testing purposes only
            //TODO: remove pos when done with testing phase
            SampleOrderFactory orderFactory = SampleOrderFactory.getInstance();
//            if (pos == 0){
                Order order = orderFactory.getSampleOrder();
                return order;
//            }
//            else{
//                Order order = orderFactory.getIncompleteOrder();
//            return order;
//            }

            //In reality, this should use the clicked orderid, and grab info from database to populate an Order object
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, final int position) {
            // doesnt work
            holder.dining.setBackgroundResource(R.drawable.redarrow);
            holder.dining.setText("Dining");
            holder.dining.setGravity(Gravity.CENTER);
            holder.student.setBackgroundResource(R.drawable.yellowarrow);
            holder.student.setText("name");
            holder.student.setGravity(Gravity.CENTER);
            holder.recipient.setBackgroundResource(R.drawable.greenarrow);
            holder.recipient.setText("Recipient");
            holder.recipient.setGravity(Gravity.CENTER);
            holder.orderid.setText(pendingOrders.get(position));
            holder.orderLL.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(MyOrdersActivity.this,OrderActivity.class);
                    Order order = grabOrder(position);
                    intent.putExtra("order",order);
                    intent.putExtra("type",user.getType());
                    startActivity(intent);
                }
            });

        }


    }
}
