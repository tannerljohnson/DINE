package edu.duke.compsci290.dukefoodapp.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by tannerjohnson on 4/12/18.
 *
 * Abstract parent class for all User classes with all getter and setter methods
 * For custom settings, @Override makeSettings method in user subclass
 */

public abstract class UserParent implements IUser, Serializable {

    protected ArrayList<String> mSettings;
    protected String id;
    protected String name;
    protected String type;
    protected String email;
    protected String bio;
    protected int points;
    protected boolean eligibleForReward;
    protected List<String> pendingOrder;
    protected List<String> orderHistory;

    protected void makeSettings() {
        mSettings = new ArrayList<>();
        mSettings.add("My Account");
        mSettings.add("My Orders");
        mSettings.add("Calendar");
    }

    // getters and setters -- must be in accordance with Realtime Database data structure
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

    public List<String> getPendingOrders() {
        return this.pendingOrder;
    }

    public void setPendingOrder(List<String> order) { pendingOrder = order; }

    public List<String> getOrderHistory() {
        return this.orderHistory;
    }

    public void setOrderHistory(List<String> orderHistory) { this.orderHistory = orderHistory; }

    public int getPoints() { return this.points; }

    public void setPoints(int points) { this.points = points; }

    public boolean getEligibleForReward() { return this.eligibleForReward; }

    public void setEligibleForReward(boolean eligible) { eligibleForReward = eligible; }

    public ArrayList<String> getStatistics() throws UserMalformedException {
        // ALWAYS make a new Statistics object because our database has writes happening constantly...
        // i.e., don't display old data

        // check if Statistics can be built
        if (this == null) {
            throw new UserMalformedException("Must initialize user before creating statistics!");
        }
        Statistics stats = new Statistics(this);
        return stats.getAllStats();
    }

    public ArrayList<String> getSettings() { return this.mSettings; }
}
