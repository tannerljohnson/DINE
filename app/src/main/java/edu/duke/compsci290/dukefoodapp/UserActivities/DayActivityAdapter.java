package edu.duke.compsci290.dukefoodapp.UserActivities;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
    public IDay mDay;
    public String mUserId;
    public List<Order> mOrders;
    public List<String> mOrderIds;

    public static final String sTAG = "DayActivityAdapter";

    // constructor
    public DayActivityAdapter(final Context context, IDay day, String uId) {
        this.mContext = context;
        this.mDay = day;
        this.mUserId = uId;
        this.mOrderIds = new ArrayList<>();
        this.mOrders = new ArrayList<>();
        mOrders.addAll(day.getOrders());
        for (Order o : mOrders) {
            mOrderIds.add(o.getId());
        }
        Log.d(sTAG, "setting up DayActivityAdapter");
    }

    @Override
    public int getItemCount() {
        return mDay.getOrders().size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = mInflater.inflate(R.layout.activity_day_order_holder, parent, false);

        final ViewHolder orderHolder = new ViewHolder(row);

        // onclick listener for selecting orders
        orderHolder.mStatusWidget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // set quiz not complete if clicked
                int position = orderHolder.getAdapterPosition();
                // true if user is changing from unchecked to checked, else false
                boolean checked = ((CheckBox) view).isChecked();
                manualSetStatus(position, checked);
                Log.d(sTAG, "clicked complete widget for order: " + mOrderIds.get(position));
            }
        });

        return orderHolder;
    }

    // user manually sets order status
    private void manualSetStatus(int position, boolean markingAsComplete) {
        // overrides in progress and incomplete
        if (markingAsComplete) {
            // give Order status the User's id
            mOrders.get(position).setStudentId(mUserId);
        } else {
            // clean slate
            mOrders.get(position).setStudentId(null);
            assert mOrders.get(position).getStudentId() == null;
        }
        Log.d(sTAG, mOrderIds.get(position) + " has student owner: " + mOrders.get(position).getStudentId());
        // Database.writeToDb(mOrders);
        // TODO: invoke database writes when user confirms selected orders
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.mOrderName.setText(mOrderIds.get(position));
        holder.mStatusWidget.setChecked(false);
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mOrderName;
        public CheckBox mStatusWidget;
        public LinearLayout mLinearLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            this.mStatusWidget = itemView.findViewById(R.id.order_status_token_image);
            this.mOrderName = itemView.findViewById(R.id.tv_order_name);
            this.mLinearLayout = itemView.findViewById(R.id.order_holder_linear_layout);
        }
    }


}
