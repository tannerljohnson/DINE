package edu.duke.compsci290.dukefoodapp.UserActivities;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
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
        mOrder = (Order) receivedIntent.getParcelableExtra("order");
        mType = receivedIntent.getStringExtra("type");
        mStatus = mOrder.getStatus();

        //set up views
        mOrderID = findViewById(R.id.order_activity_order_id);
        mDiningImage = findViewById(R.id.order_activity_dining_textView);
        mStudentImage = findViewById(R.id.order_activity_student_textView);
        mRecipientImage = findViewById(R.id.order_activity_recipient_textView);
        mDynamic = findViewById(R.id.order_activity_dynamic_ll);

        //arrows need to be dynamically assigned
        if(mOrder.getDiningId() != null){
            mDiningImage.setBackgroundResource(R.drawable.redarrow);
            mDiningImage.setText(mOrder.getDiningName());
            mDiningImage.setGravity(Gravity.CENTER);
        }
        if(mOrder.getStudentId() != null){
            mStudentImage.setBackgroundResource(R.drawable.yellowarrow);
            mStudentImage.setText(mOrder.getStudentName());
            mStudentImage.setGravity(Gravity.CENTER);
        }
        if(mOrder.getRecipientId() != null){
            mRecipientImage.setBackgroundResource(R.drawable.greenarrow);
            mRecipientImage.setText(mOrder.getRecipientName());
            mRecipientImage.setGravity(Gravity.CENTER);
        }
        mOrderID.setText(mOrder.getId());


        if(mType.equals("student")){
            //create ui for student
            if (mOrder.getRecipientId() != null) {
                TextView Name = new TextView(this);
                Name.setText("Recipient name: "+mOrder.getRecipientName());
                Name.setGravity(Gravity.CENTER);
                Name.setHeight(50);
                mDynamic.addView(Name);
                TextView Phone = new TextView(this);
                Phone.setText("Recipient Phone Number: "+mOrder.getRecipientPhone());
                Phone.setGravity(Gravity.CENTER);
                Phone.setHeight(50);
                mDynamic.addView(Phone);
                TextView Allergens = new TextView(this);
                Allergens.setText("Allergens: "+mOrder.getAllergens());
                Allergens.setGravity(Gravity.CENTER);
                Allergens.setHeight(50);
                mDynamic.addView(Allergens);
            }
            createStatusView();
        }

        if(mType.equals("recipient")){
            //create ui for recipient
            TextView Name = new TextView(this);
            Name.setText("Student name: "+mOrder.getStudentName());
            Name.setGravity(Gravity.CENTER);
            Name.setHeight(50);
            mDynamic.addView(Name);
            TextView Phone = new TextView(this);
            Phone.setText("Student Phone Number: "+mOrder.getStudentPhone());
            Phone.setGravity(Gravity.CENTER);
            Phone.setHeight(50);
            mDynamic.addView(Phone);
            TextView Allergens = new TextView(this);
            Allergens.setText("Allergens: "+mOrder.getAllergens());
            Allergens.setGravity(Gravity.CENTER);
            Allergens.setHeight(50);
            mDynamic.addView(Allergens);
        }

        if(mType.equals("dining")){
            //create ui for dining
            TextView Name = new TextView(this);
            Name.setText("Recipient name: "+mOrder.getRecipientName());
            Name.setGravity(Gravity.CENTER);
            Name.setHeight(50);
            mDynamic.addView(Name);
            TextView Phone = new TextView(this);
            Phone.setText("Recipient Phone Number: "+mOrder.getRecipientPhone());
            Phone.setGravity(Gravity.CENTER);
            Phone.setHeight(50);
            mDynamic.addView(Phone);
            TextView Name1 = new TextView(this);
            Name1.setText("Student name: "+mOrder.getStudentName());
            Name1.setGravity(Gravity.CENTER);
            Name1.setHeight(50);
            mDynamic.addView(Name1);
            TextView Phone1 = new TextView(this);
            Phone1.setText("Student Phone Number: "+mOrder.getStudentPhone());
            Phone1.setGravity(Gravity.CENTER);
            Phone1.setHeight(50);
            mDynamic.addView(Phone1);
            TextView Allergens = new TextView(this);
            Allergens.setText("Allergens: "+mOrder.getAllergens());
            Allergens.setGravity(Gravity.CENTER);
            Allergens.setHeight(50);
            mDynamic.addView(Allergens);
        }



        //added for testing purposes only
    }

    private void createStatusView() {
        if(mStatus == 0){
            //needs student approval
            //only seen by dining
            TextView Status = new TextView(this);
            Status.setText("Status: Needs Student Distributor");
            Status.setHeight(15);
            mDynamic.addView(Status);
        }
        if(mStatus == 1){
            //needs recipient approval
            // Seen by dining and student
            TextView Status = new TextView(this);
            Status.setText("Status: Needs Recipient");
            Status.setHeight(15);
            mDynamic.addView(Status);
        }
        if(mStatus == 2){
            //needs student confirmation
            //seen by all parties
            if (mType.equals("student")){
                Button confirm = new Button(this);
                confirm.setText("Confirm Availability");
                confirm.setHeight(20);
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
                Status.setText("Status: Waiting for Student Confirmation");
                Status.setHeight(15);
                mDynamic.addView(Status);
            }
        }
        if(mStatus == 3){
            //order ready
            //seen by all parties
            if (mType.equals("student")){
                Button deliver = new Button(this);
                deliver.setText("Go to Address");
                deliver.setHeight(20);
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
                Status.setText("Status: Student has confirmed.");
                Status.setHeight(15);
                mDynamic.addView(Status);
            }
        }
        if(mStatus == 4){
            //order completed
            //seen by all parties
            TextView Status = new TextView(this);
            Status.setText("Status: Order Completed!");
            Status.setHeight(15);
            mDynamic.addView(Status);
        }
    }
}
