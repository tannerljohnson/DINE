package edu.duke.compsci290.dukefoodapp.UserActivities;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import edu.duke.compsci290.dukefoodapp.R;
import edu.duke.compsci290.dukefoodapp.model.Order;

public class OrderActivity extends AppCompatActivity {
    private TextView mStudentImage;
    private TextView mDiningImage;
    private TextView mRecipientImage;
    private TextView mStudentName;
    private TextView mDiningName;
    private TextView mRecipientName;
    private TextView mOrderID;
    private TextView mDinningLocation;
    private TextView mRecipientAddress;
    private Order mOrder;
    private Button mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //this activity is for displaying order details
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        // grab intent
        Intent receivedIntent = this.getIntent();
        mOrder = (Order) receivedIntent.getSerializableExtra("order");

        //set up views
        mOrderID = findViewById(R.id.order_activity_order_id);
        mDiningImage = findViewById(R.id.order_activity_dining_textView);
        mStudentImage = findViewById(R.id.order_activity_student_textView);
        mRecipientImage = findViewById(R.id.order_activity_recipient_textView);
        mStudentName = findViewById(R.id.order_activity_student_name);
        mDiningName = findViewById(R.id.order_activity_dining_name);
        mRecipientName = findViewById(R.id.order_activity_resident_name);
        mDinningLocation = findViewById(R.id.order_activity_dining_address);
        mRecipientAddress = findViewById(R.id.order_activity_resident_address);
        mButton = findViewById(R.id.order_activity_button);

        //assign views to variables
        mOrderID.setText(mOrder.getId());
        mStudentName.setText(mOrder.getStudentId());
        mDiningName.setText(mOrder.getDiningId());
        mRecipientName.setText(mOrder.getRecipientId());
        mDinningLocation.setText("");
        mRecipientAddress.setText("");
        final int status = mOrder.getStatus();
        //arrows need to be dynamically assigned
        mDiningImage.setBackgroundResource(R.drawable.redarrow);
        mStudentImage.setBackgroundResource(R.drawable.yellowarrow);
        mRecipientImage.setBackgroundResource(R.drawable.greenarrow);
        //set button onclick
        mButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if (status == 0){
                    //put toast here
                }
                else{
                    //may or may not work. unsure
                    Uri gmmIntentUri = Uri.parse("google.navigation:q=geo:36.0014,-78.9382"); //address of duke; will need to make unique address
                    Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                    mapIntent.setPackage("com.google.android.apps.maps");
                    if (mapIntent.resolveActivity(getPackageManager()) != null) {
                        startActivity(mapIntent);
                    }
                    else{
                        Log.d("OrderActivity", "map not accessible");
                    }
                }
            }
        });
    }
}
