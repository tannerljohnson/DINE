package edu.duke.compsci290.dukefoodapp.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by tannerjohnson on 4/10/18.
 */

public class DiningUser implements IUser,Serializable {

    private ArrayList<String> mSettings;
    private String id;
    private String name;
    private String type;
    private String email;
    private String bio;
    private int points;
    private boolean eligibleForReward;
    private String pendingOrder;
    private List<String> orderHistory;

    public DiningUser() {
        makeSettings();
    }

    public DiningUser(String id, String name, String type, String email, String bio,
                         int points, boolean eligibleForReward, String pendingOrder,
                         List<String> orderHistory) {
        makeSettings();
        this.id = id;
        this.name = name;
        this.type = type;
        this.email = email;
        this.bio = bio;
        this.points = points;
        this.eligibleForReward = eligibleForReward;
        this.pendingOrder = pendingOrder;
        this.orderHistory = orderHistory;
    }

    private void makeSettings() {
        mSettings = new ArrayList<>();
        mSettings.add("My Account");
        mSettings.add("My Orders");
        mSettings.add("Calendar");
    }

    // don't think we even need a full constructor

    public String getId() { return this.id; }

    public void setId(String id) { this.id = id; }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBio() {
        return this.bio;
    }

    public void setBio(String bio) { this.bio = bio; }

    public String getPendingOrder() {
        return this.pendingOrder;
    }

    public void setPendingOrder(String order) { pendingOrder = order; }

    public List<String> getOrderHistory() {
        return this.orderHistory;
    }

    public void setOrderHistory(List<String> orderHistory) { this.orderHistory = orderHistory; }

    public int getPoints() { return this.points; }

    public void setPoints(int points) { this.points = points; }

    public boolean getEligibleForReward() { return this.eligibleForReward; }

    public void setEligibleForReward(boolean eligible) { eligibleForReward = eligible; }

    public ArrayList<String> getStatistics() throws UserMalformedException {
        // check if Statistics can be built
        if (this == null) {
            throw new UserMalformedException("Must initialize user before creating statistics!");
        }
        Statistics stats = new Statistics(this);
        return stats.getAllStats();
    }

    public ArrayList<String> getSettings() { return this.mSettings; }

}
