package edu.duke.compsci290.dukefoodapp.model;

import com.firebase.ui.auth.data.model.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tannerjohnson on 4/10/18.
 */

public class Statistics implements IStats {

    private String mOrdersDone;
    private String mRewardEligibility;
    private String mPendingOrderBoolean;
    private String mPointTotal;
    private ArrayList<String> mAllStats;
//    don't make this a singleton. need to dynamically make multiple stat objects.
//    private static Statistics mInstance;
//
//    public static Statistics getInstance(IUser user) {
//        if (mInstance == null) {
//            mInstance = new Statistics(user);
//        }
//        return mInstance;
//    }

    public Statistics(IUser user) {
        buildStatistics(user);
    }

    // Statistics.buildStatistics(mStudent); // type: StudentUser implements IUser
    // UserParent and StudentUser extends UserParent
    private void buildStatistics(IUser user) {
        // if user.instanceOf()
        assert user instanceof IUser;
        int totalOrdersToDate = user.getOrderHistory().size();
        mOrdersDone = "Orders: " + Integer.toString(totalOrdersToDate);
        mRewardEligibility = "Reward Eligibility: " + String.valueOf(user.getEligibleForReward());
        boolean pendingOrder = true;
        if (user.getPendingOrder() == null) pendingOrder = false;
        mPendingOrderBoolean = "Pending Order: " + String.valueOf(pendingOrder);
        mPointTotal = "Points: " + Integer.toString(user.getPoints());
        mAllStats = new ArrayList<>();
        mAllStats.add(mOrdersDone);
        mAllStats.add(mPendingOrderBoolean);
        mAllStats.add(mPointTotal);
        mAllStats.add(mRewardEligibility);
    }

    public String getOrdersDone(){
        return this.mOrdersDone;
    }
    public String getRewardEligibility(){
        return this.mRewardEligibility;
    }
    public String getPendingOrderBoolean(){
        return this.mPendingOrderBoolean;
    }
    public String getPointTotal(){
        return this.mPointTotal;
    }
    public ArrayList<String> getAllStats() { return this.mAllStats; }

}
