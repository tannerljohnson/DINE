package edu.duke.compsci290.dukefoodapp.model;

import com.firebase.ui.auth.data.model.User;

/**
 * Created by tannerjohnson on 4/10/18.
 */

public class Statistics implements IStats {

    private String mOrdersDone;
    private String mRewardEligibility;
    private String mPendingOrderBoolean;
    private String mPointTotal;

    private static Statistics mInstance;

    public static Statistics getInstance(IUser user) {
        if (mInstance == null) {
            mInstance = new Statistics(user);
        }
        return mInstance;
    }

    private Statistics(IUser user) {
        buildStatistics(user);
    }

    // Statistics.buildStatistics(mStudent); // type: StudentUser implements IUser
    // UserParent and StudentUser extends UserParent
    public void buildStatistics(IUser user) {
        // if user.instanceOf()
        assert user instanceof IUser;
        int totalOrdersToDate = user.getOrderHistory().size();
        mOrdersDone = "Orders: " + Integer.toString(totalOrdersToDate);
        mRewardEligibility = "Reward Eligibility: " + String.valueOf(user.getEligibleForReward());
        boolean pendingOrder = true;
        if (user.getPendingOrder() == null) pendingOrder = false;
        mPendingOrderBoolean = "Pending Order: " + String.valueOf(pendingOrder);
        mPointTotal = "Points: " + Integer.toString(user.getPoints());
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

}
