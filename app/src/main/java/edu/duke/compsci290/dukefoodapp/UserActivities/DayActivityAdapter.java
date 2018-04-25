package edu.duke.compsci290.dukefoodapp.UserActivities;

import android.content.Context;
import android.os.Parcelable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import edu.duke.compsci290.dukefoodapp.R;
import edu.duke.compsci290.dukefoodapp.model.IDay;
import edu.duke.compsci290.dukefoodapp.model.Order;

/**
 * Created by tannerjohnson on 4/13/18.
 */

public class DayActivityAdapter extends RecyclerView.Adapter<DayActivityAdapter.ViewHolder> {

    public Context mContext;
    public String mUserType;
    public ArrayList<Order> mOrders;
    public static final String sTAG = "DayActivityAdapter";

    // constructor
    public DayActivityAdapter(final Context context, ArrayList<Order> orders, String type) {
        this.mContext = context;
        this.mOrders = orders;
        this.mUserType = type;
        Log.d(sTAG,"# of orders(38): "+Integer.toString(mOrders.size()));
        Log.d(sTAG, "setting up DayActivityAdapter");
    }

    @Override
    public int getItemCount() {
        Log.d(sTAG,"# of orders(44): "+Integer.toString(mOrders.size()));
        return mOrders.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d(sTAG, "onCreateViewHolder");
        LayoutInflater mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = mInflater.inflate(R.layout.activity_day_order_holder, parent, false);
        final ViewHolder orderHolder = new ViewHolder(row);
        return orderHolder;
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Order order = mOrders.get(position);
        holder.mOrderName.setText(order.getDiningName());
        //create colors for layout background
        // onclick listener for selecting orders
        holder.mLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mUserType == "recipient"){
                    if (order.getRecipientId() != null){
                        //toast

                    }
                    else{
                        // open dialogue for the user
                    }
                }
                if(mUserType.equals("student")){
                    if (order.getStudentId() != null){
                        //toast

                    }
                    else{
                        // open dialogue for the user
                    }
                }
            }
        });
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mOrderName;
        public LinearLayout mLinearLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            this.mOrderName = itemView.findViewById(R.id.tv_order_name);
            this.mLinearLayout = itemView.findViewById(R.id.order_holder_linear_layout);
        }
    }


}
