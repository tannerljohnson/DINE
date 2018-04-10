package edu.duke.compsci290.dukefoodapp.model;

import java.util.List;

/**
 * Created by tannerjohnson on 4/3/18.
 */

public class RecipientUser implements IUser {

    public String mId;
    public String mName;
    public String mType;
    public String mEmail;
    public String mBio;
    public int mPoints;
    public boolean mEligibleForReward;
    public String mPendingOrder;
    // TODO: orderHistory should be type List<Order>
    public List<String> mOrderHistory;

    // empty constructor requires all necessary setters
    public RecipientUser() {}

    public RecipientUser(String id, String name, String type, String email, String bio,
                       int points, boolean eligibleForReward, String pendingOrder,
                       List<String> orderHistory) {
        mId = id;
        mName = name;
        mType = type;
        mEmail = email;
        mBio = bio;
        mPoints = points;
        mEligibleForReward = eligibleForReward;
        mPendingOrder = pendingOrder;
        mOrderHistory = orderHistory;
    }


    // getters and setters -- must be in accordance with Realtime Database data structure
    public String getId() { return this.mId; }

    public void setId(String id) { mId = id; }

    public String getName() {
        return this.mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getType() {
        return this.mType;
    }

    public void setType(String type) {
        mType = type;
    }

    public String getEmail() {
        return this.mEmail;
    }

    public void setEmail(String email) {
        mEmail = email;
    }

    public String getBio() {
        return this.mBio;
    }

    public void setBio(String bio) { mBio = bio; }

    public String getPendingOrder() {
        return this.mPendingOrder;
    }

    public void setPendingOrder(String order) { mPendingOrder = order; }

    public List<String> getOrderHistory() {
        return this.mOrderHistory;
    }

    public void setOrderHistory(List<String> orderHistory) { mOrderHistory = orderHistory; }

    public int getPoints() { return this.mPoints; }

    public void setPoints(int points) { mPoints = points; }

    public boolean getEligibleForReward() { return this.mEligibleForReward; }

    public void setEligibleForReward(boolean eligible) { mEligibleForReward = eligible; }

}
