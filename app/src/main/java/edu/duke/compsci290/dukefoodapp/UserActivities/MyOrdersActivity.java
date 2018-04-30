package edu.duke.compsci290.dukefoodapp.UserActivities;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import java.util.List;
import edu.duke.compsci290.dukefoodapp.Database.IOnDatabaseRead;
import edu.duke.compsci290.dukefoodapp.Database.OrderDB;
import edu.duke.compsci290.dukefoodapp.R;
import edu.duke.compsci290.dukefoodapp.model.Order;
import edu.duke.compsci290.dukefoodapp.model.UserParent;

public class MyOrdersActivity extends AppCompatActivity {
    //This activity is for the user to look at their current orders, and the progress of the order
    //maybe save the specific users order in SQLite, and update the orders the user already has.
    //helps with the no connection problems
    public RecyclerView rv;

    public UserParent user;
    public List<String> orderHistory;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private final String TAG = "My Orders Activity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_orders);

        //set navigation
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        mToggle = new ActionBarDrawerToggle(MyOrdersActivity.this,mDrawerLayout,R.string.opena,R.string.closea);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //setup clickables to navigation view

        NavigationView nv = (NavigationView)findViewById(R.id.navigation_view);
        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                Log.d(TAG,"item selected");
                Intent intent;
                switch (menuItem.getItemId()) {
                    case(R.id.home):
                        Log.d(TAG,"Home");
                        intent = new Intent(MyOrdersActivity.this, UserActivity.class);
                        intent.putExtra("type",user.getType());
                        intent.putExtra("user",user);
                        startActivity(intent);
                        finish();
                        break;
                    case(R.id.my_orders):
                        if (user.getOrderHistory() == null){
                            CharSequence text = "You Have No Orders!";
                            int duration = Toast.LENGTH_SHORT;
                            Toast toast = Toast.makeText(MyOrdersActivity.this, text, duration);
                            toast.show();
                            break;
                        }
                        else{
                            intent = new Intent(MyOrdersActivity.this, MyOrdersActivity.class);
                            intent.putExtra("type",user.getType());
                            intent.putExtra("user",user);
                            startActivity(intent);
                            finish();
                            break;
                        }
                    case(R.id.calendar):
                        intent = new Intent(MyOrdersActivity.this, CalendarActivity.class);
                        intent.putExtra("type",user.getType());
                        intent.putExtra("user",user);
                        startActivity(intent);
                        finish();
                        break;
                }
                return true;
            }
        });

        //Grab Intent information
        Intent receivedIntent = this.getIntent();
        user = (UserParent) receivedIntent.getSerializableExtra("user");
        orderHistory = user.getOrderHistory();

        // check some intent data
        Log.d(TAG, user.getName().toString());


        //set up recycler view
        rv = findViewById(R.id.my_orders_recycler_view);
        rv.setAdapter(new OrderAdapter(this, orderHistory));
        rv.setLayoutManager(new LinearLayoutManager(this));

//        getUserOrdersFromFirebase();
    }




    public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {
        private Context mContext;
        private List<String> orderhistory;
        private OrderDB oDB;
        private Order mOrder;


        public class ViewHolder extends RecyclerView.ViewHolder {
            public TextView orderid;

            public ViewHolder(View itemView) {
                super(itemView);
                this.orderid = itemView.findViewById(R.id.order_layout_order_id);
            }
        }

        public OrderAdapter(Context context, List<String> orderhistory) {
            this.mContext = context;
            this.orderhistory = orderhistory;
            this.oDB = OrderDB.getInstance();
            this.mOrder = new Order();
        }



        @Override
        public int getItemCount(){
            return orderHistory.size();
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



        @Override
        public void onBindViewHolder(final ViewHolder holder, final int position) {

            oDB.readFromDatabase(orderhistory.get(position));
            oDB.setCustomEventListener(new IOnDatabaseRead() {
                @Override
                public void onEvent() {
                    mOrder = (Order) oDB.getObject();
                    holder.orderid.setText(mOrder.getDiningName());
                    holder.orderid.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            //TODO: create different dialogues for each person
                            if (user.getType().equals("admin")){
                                adminDialogue();
                            }
                            if (user.getType().equals("student")){
                                studentDialogue();
                            }
                            if (user.getType().equals("recipient")){
                                recipientDialogue();
                            }
                        }
                    });
                }
            });


        }

        private void recipientDialogue() {
            AlertDialog.Builder mBuilder = new AlertDialog.Builder(MyOrdersActivity.this);
            View mView = getLayoutInflater().inflate(R.layout.recipient_my_order,null);
            TextView diningName = mView.findViewById(R.id.dining_name);
            diningName.setText("Dining Name: " + mOrder.getDiningName());
            TextView studentName = mView.findViewById(R.id.student_name);
            studentName.setText("Student Name: " + mOrder.getStudentName());
            TextView studentPhone = mView.findViewById(R.id.student_phone);
            studentPhone.setText("Student Phone: " + mOrder.getStudentPhone());
            TextView deliveryTime = mView.findViewById(R.id.delivery_time);
            deliveryTime.setText("Delivery Time: "+mOrder.getDeliveryTime());
            Button confirmDelivery = mView.findViewById(R.id.confirm_delivery);
            confirmDelivery.setText("Confirm Delivery");
            mBuilder.setView(mView);
            final AlertDialog dialog = mBuilder.create();
            dialog.show();
            confirmDelivery.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mOrder.setStatus(4);
                    oDB.setObject(mOrder);
                    oDB.writeToDatabase();
                    dialog.dismiss();
                }
            });

        }

        private void studentDialogue() {
            AlertDialog.Builder mBuilder = new AlertDialog.Builder(MyOrdersActivity.this);
            final View mView = getLayoutInflater().inflate(R.layout.student_my_order,null);
            TextView diningName = mView.findViewById(R.id.dining_name);
            diningName.setText("Dining Name: " + mOrder.getDiningName());
            TextView recipientName = mView.findViewById(R.id.recipient_name);
            recipientName.setText("Recipient Name: " + mOrder.getRecipientName());
            TextView recipientPhone = mView.findViewById(R.id.recipient_phone);
            recipientPhone.setText("Recipient Phone: " + mOrder.getRecipientPhone());
            TextView pickupTime = mView.findViewById(R.id.pickup_time);
            pickupTime.setText("Pickup Time: "+mOrder.getPickupTime());
            TextView deliveryTime = mView.findViewById(R.id.delivery_time);
            deliveryTime.setText("Delivery Time: "+mOrder.getDeliveryTime());
            Button pickupOrder = mView.findViewById(R.id.pickup_order);
            Button dropoffOrder = mView.findViewById(R.id.dropoff_order);
            Button streetview = mView.findViewById(R.id.streetview);
            pickupOrder.setText("Pickup Order");
            dropoffOrder.setText("Dropoff Order");
            streetview.setText("Street View");
            mBuilder.setView(mView);
            final AlertDialog dialog = mBuilder.create();
            dialog.show();
            streetview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mOrder.getDropoffLocation() != null){
                        Intent intent = new Intent(MyOrdersActivity.this,StreetViewActivity.class);
                        intent.putExtra("address",mOrder.getDropoffLocation());
                        startActivity(intent);
                    }
                    else{
                        CharSequence text = "Order Not Ready!";
                        int duration = Toast.LENGTH_SHORT;
                        Toast toast = Toast.makeText(MyOrdersActivity.this, text, duration);
                        toast.show();
                    }
                }
            });
            if(mOrder.getStatus() != 4){
                pickupOrder.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(mOrder.getRecipientName() != null){
                            mOrder.setStatus(2);
                            oDB.setObject(mOrder);
                            oDB.writeToDatabase();
                            String address = mOrder.getPickupLocation().replace(" ","+");
                            Uri gmmIntentUri = Uri.parse("google.navigation:q="+ address +"&avoid=tf");
                            Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                            mapIntent.setPackage("com.google.android.apps.maps");
                            if (mapIntent.resolveActivity(getPackageManager()) != null) {
                                startActivity(mapIntent);
                            }
                            else{
                                Log.d(TAG, "map not accessible");
                            }
                        }
                        else{
                            CharSequence text = "Order Not Ready!";
                            int duration = Toast.LENGTH_SHORT;
                            Toast toast = Toast.makeText(MyOrdersActivity.this, text, duration);
                            toast.show();
                        }
                        dialog.dismiss();
                    }
                });
                dropoffOrder.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(mOrder.getRecipientName() != null){
                            mOrder.setStatus(3);
                            oDB.setObject(mOrder);
                            oDB.writeToDatabase();
                            String address = mOrder.getDropoffLocation().replace(" ","+");
                            Uri gmmIntentUri = Uri.parse("google.navigation:q="+ address +"&avoid=tf");
                            Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                            mapIntent.setPackage("com.google.android.apps.maps");
                            if (mapIntent.resolveActivity(getPackageManager()) != null) {
                                startActivity(mapIntent);
                            }
                            else{
                                Log.d(TAG, "map not accessible");
                            }
                        }
                        else{
                            CharSequence text = "Order Not Ready!";
                            int duration = Toast.LENGTH_SHORT;
                            Toast toast = Toast.makeText(MyOrdersActivity.this, text, duration);
                            toast.show();
                        }
                        dialog.dismiss();
                    }
                });
            }


        }

        private void adminDialogue() {
            AlertDialog.Builder mBuilder = new AlertDialog.Builder(MyOrdersActivity.this);
            View mView = getLayoutInflater().inflate(R.layout.admin_my_order,null);
            TextView studentName = mView.findViewById(R.id.student_name);
            studentName.setText("Student Name: " + mOrder.getStudentName());
            TextView studentPhone = mView.findViewById(R.id.student_phone);
            studentPhone.setText("Student Phone: " + mOrder.getStudentPhone());
            TextView recipientName = mView.findViewById(R.id.recipient_name);
            recipientName.setText("Recipient Name: " + mOrder.getRecipientName());
            TextView recipientPhone = mView.findViewById(R.id.recipient_phone);
            recipientPhone.setText("Recipient Phone: " + mOrder.getRecipientPhone());
            TextView pickupTime = mView.findViewById(R.id.pickup_time);
            pickupTime.setText("Pickup Time: "+mOrder.getPickupTime());
            TextView deliveryTime = mView.findViewById(R.id.delivery_time);
            deliveryTime.setText("Delivery Time: "+mOrder.getDeliveryTime());
            TextView status = mView.findViewById(R.id.status);
            status.setText("Status: " + cypherStatus(mOrder.getStatus()));
            mBuilder.setView(mView);
            final AlertDialog dialog = mBuilder.create();
            dialog.show();
        }

        private String cypherStatus(int status) {
            String ans = "";
            switch (status){
                case(0):
                    ans = "Pending Student acceptance";
                    break;
                case(1):
                    ans = "Pending Recipient acceptance";
                    break;
                case(2):
                    ans = "Order Started";
                    break;
                case(3):
                    ans = "Student on the Way";
                    break;
                case(4):
                    ans = "Order Complete";
                    break;
            }
            return ans;
        }


    }
}
