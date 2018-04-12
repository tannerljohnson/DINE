package edu.duke.compsci290.dukefoodapp.UserActivities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import edu.duke.compsci290.dukefoodapp.R;
import edu.duke.compsci290.dukefoodapp.model.IUser;

public class MyOrdersActivity extends AppCompatActivity {
    public RecyclerView rv;
    public IUser user;
    public List<String> orderHistory;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_orders);

        //Grab Intent information
        Intent receivedIntent = this.getIntent();
        user = (IUser)receivedIntent.getSerializableExtra("user");
        orderHistory = user.getOrderHistory();

        // check some intent data
        Log.d("tag1", user.getName().toString());


        //set up recycler view
        rv = findViewById(R.id.my_orders_recycler_view);
        rv.setAdapter(new OrderAdapter(this, orderHistory));
        rv.setLayoutManager(new LinearLayoutManager(this));


    }

    public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {
        private Context mContext;
        private List<String> orderhistory;


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

        public OrderAdapter(Context context, List<String> orderhistory) {
            this.mContext = context;
            this.orderhistory = orderhistory;
        }



        @Override
        public int getItemCount(){
            return orderhistory.size();
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
            holder.orderid.setText(orderhistory.get(position));

        }


    }
}
