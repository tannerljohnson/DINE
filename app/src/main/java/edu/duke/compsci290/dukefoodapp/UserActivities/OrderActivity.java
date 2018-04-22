package edu.duke.compsci290.dukefoodapp.UserActivities;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import edu.duke.compsci290.dukefoodapp.R;
import edu.duke.compsci290.dukefoodapp.model.Order;

public class OrderActivity extends AppCompatActivity {
    private TextView mStudentImage;
    private TextView mDiningImage;
    private TextView mRecipientImage;
    private TextView mOrderID;
    private LinearLayout mDynamic;
    private Order mOrder;
    private int mStatus;
    private String mType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //this activity is for displaying order details
        //this activity is Dynamically built based on the type of user
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        // grab intent
        Intent receivedIntent = this.getIntent();
        mOrder = (Order) receivedIntent.getSerializableExtra("order");
        mType = receivedIntent.getStringExtra("type");
        mStatus = mOrder.getStatus();

        //set up views
        mOrderID = findViewById(R.id.order_activity_order_id);
        mDiningImage = findViewById(R.id.order_activity_dining_textView);
        mStudentImage = findViewById(R.id.order_activity_student_textView);
        mRecipientImage = findViewById(R.id.order_activity_recipient_textView);
        mDynamic = findViewById(R.id.order_activity_dynamic_ll);

        //arrows need to be dynamically assigned
        if(mOrder.getDiningId() != null){mDiningImage.setBackgroundResource(R.drawable.redarrow);}
        if(mOrder.getStudentId() != null){mStudentImage.setBackgroundResource(R.drawable.yellowarrow);}
        if(mOrder.getRecipientId() != null){mRecipientImage.setBackgroundResource(R.drawable.greenarrow);}
        mOrderID.setText(mOrder.getId());


        if(mType.equals("student")){
            //create ui for student
            if (mOrder.getRecipientId() != null) {
                TextView Name = new TextView(this);
                Name.setText(mOrder.getRecipientName());
                mDynamic.addView(Name);
                TextView Phone = new TextView(this);
                Phone.setText(mOrder.getRecipientPhone());
                mDynamic.addView(Phone);
            }
            createStatusView();
        }

        if(mType.equals("recipient")){
            //create ui for recipient
            TextView Name = new TextView(this);
            Name.setText(mOrder.getStudentName());
            mDynamic.addView(Name);
            TextView Phone = new TextView(this);
            Phone.setText(mOrder.getStudentPhone());
            mDynamic.addView(Phone);
        }

        if(mType.equals("dining")){
            //create ui for dining
            TextView Name = new TextView(this);
            Name.setText(mOrder.getRecipientName());
            mDynamic.addView(Name);
            TextView Phone = new TextView(this);
            Phone.setText(mOrder.getRecipientPhone());
            mDynamic.addView(Phone);
            TextView Name1 = new TextView(this);
            Name1.setText(mOrder.getStudentName());
            mDynamic.addView(Name1);
            TextView Phone1 = new TextView(this);
            Phone1.setText(mOrder.getStudentPhone());
            mDynamic.addView(Phone1);
        }



        //added for testing purposes only
    }

    private void createStatusView() {
        if(mStatus == 0){
            //needs student approval
            //only seen by dining
            TextView Status = new TextView(this);
            Status.setText("Needs Student Distributor");
            mDynamic.addView(Status);
        }
        if(mStatus == 1){
            //needs recipient approval
            // Seen by dining and student
            TextView Status = new TextView(this);
            Status.setText("Needs Recipient");
            mDynamic.addView(Status);
        }
        if(mStatus == 2){
            //needs student confirmation
            //seen by all parties
            if (mType.equals("student")){
                Button confirm = new Button(this);
                confirm.setText("Confirm Availability");
                mDynamic.addView(confirm);
                confirm.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View view) {
                        //update the status in the dbase
                        //recreate UI
                    }
                });
            }
            else{
                TextView Status = new TextView(this);
                Status.setText("Waiting for Student Confirmation");
                mDynamic.addView(Status);
            }
        }
        if(mStatus == 3){
            //order ready
            //seen by all parties
            if (mType.equals("student")){
                Button deliver = new Button(this);
                deliver.setText("Go to Address");
                mDynamic.addView(deliver);
                deliver.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View view) {
                    Uri gmmIntentUri = Uri.parse("google.navigation:q="+ mOrder.getDropoffLocation() +"&avoid=tf");
                    //will need the address from the order, q can be address
                    // conneted by +s (101+Main+Street,Durham+NC)
                    Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                    mapIntent.setPackage("com.google.android.apps.maps");
                    if (mapIntent.resolveActivity(getPackageManager()) != null) {
                        startActivity(mapIntent);
                    }
                    else{
                        Log.d("OrderActivity", "map not accessible");
                    }
                    }
                });
            }
            else{
                TextView Status = new TextView(this);
                Status.setText("Student has confirmed.");
                mDynamic.addView(Status);
            }
        }
        if(mStatus == 4){
            //order completed
            //seen by all parties
            TextView Status = new TextView(this);
            Status.setText("Order Completed!");
            mDynamic.addView(Status);
        }
    }
}
