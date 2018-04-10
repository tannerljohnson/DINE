package edu.duke.compsci290.dukefoodapp.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tannerjohnson on 4/3/18.
 */

public class StudentUser implements IUser {

    public String mName;
    public String mType;
    public String mEmail;
    public String mBio;
    public int mPoints;
    public boolean mEligibleForReward;
    public String mPendingOrder;
    // TODO: mOrderHistory should be type List<Order>
    public List<String> mOrderHistory;
    public ArrayList<String> mStatistics;
    public ArrayList<String> mSettings;

    // empty constructor requires all necessary setters
    public StudentUser() {
    }

    public StudentUser(String name, String type, String email, String bio,
                int points, boolean eligibleForReward, String pendingOrder,
                List<String> orderHistory, ArrayList<String> statistics) {
        mName = name;
        mType = type;
        mEmail = email;
        mBio = bio;
        mPoints = points;
        mEligibleForReward = eligibleForReward;
        mPendingOrder = pendingOrder;
        mOrderHistory = orderHistory;
        mStatistics = statistics;
    }


    // getters and setters -- must be in accordance with Realtime Database data structure
    public String getUserName() {
        return this.mName;
    }

    public void setUserName(String name) {
        mName = name;
    }

    public String getUserType() {
        return this.mType;
    }

    public void setUserType(String type) {
        mType = type;
    }

    public String getUserEmail() {
        return this.mEmail;
    }

    public void setUserEmail(String email) {
        mEmail = email;
    }

    public String getUserBio() {
        return this.mBio;
    }

    public void setUserBio(String bio) { mBio = bio; }

    public String getUserPendingOrder() {
        return this.mPendingOrder;
    }

    public void setUserPendingOrder(String order) { mPendingOrder = order; }

    public List<String> getUserOrderHistory() {
        return this.mOrderHistory;
    }

    public void setUserOrderHistory(List<String> orderHistory) { mOrderHistory = orderHistory; }

    public int getUserPoints() { return this.mPoints; }

    public void setUserPoints(int points) { mPoints = points; }

    public boolean getUserEligibleForReward() { return this.mEligibleForReward; }

    public void setUserEligibleForReward(boolean eligible) { mEligibleForReward = eligible; }

    public void setStatistics(ArrayList<String> stats) { mStatistics = stats; }

    public ArrayList<String> getStatistics() { return this.mStatistics; }

    public void setSettings(ArrayList<String> settings) { mSettings = settings; }

    public ArrayList<String> getSettings() { return this.mSettings; }

}
