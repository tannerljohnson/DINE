package edu.duke.compsci290.dukefoodapp.model;

import java.util.ArrayList;

/**
 * Created by tannerjohnson on 4/10/18.
 */

public class Statistics implements IStats {

    private String mOrdersDone;
    private String mRewardEligibility;
    private String mPendingOrderBoolean;
    private String mPointTotal;
    private ArrayList<String> mAllStats;

    public Statistics(IUser user) {
        buildStatistics(user);
    }

    private void buildStatistics(IUser user) {
        // if user.instanceOf()
        assert user instanceof IUser;
        assert user instanceof UserParent;
        System.out.println("sample user is instance of IUser and UserParent");
        int totalOrdersToDate = user.getOrderHistory().size();
        mOrdersDone = "Orders: " + Integer.toString(totalOrdersToDate);
        mRewardEligibility = "Reward Eligibility: " + String.valueOf(user.getEligibleForReward());
        boolean pendingOrder = true;
        if (user.getPendingOrders() == null) pendingOrder = false;
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
