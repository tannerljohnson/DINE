package edu.duke.compsci290.dukefoodapp.model;

/**
 * Created by tannerjohnson on 4/3/18.
 */

public class User {

    public String mName;
    public String mType;
    public String mEmail;
    public String mBio;
    public int mPoints;
    public boolean mEligibleForReward;
    public String mPendingOrder;
    public String[] mOrderHistory;

    public User(String name, String type, String email, String bio,
                int points, boolean eligibleForReward, String pendingOrder,
                String[] orderHistory) {
        mName = name;
        mType = type;
        mEmail = email;
        mBio = bio;
        mPoints = points;
        mEligibleForReward = eligibleForReward;
        mPendingOrder = pendingOrder;
        mOrderHistory = orderHistory;
    }

}
