package edu.duke.compsci290.dukefoodapp.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tannerjohnson on 4/3/18.
 */

public class StudentUser implements IUser {


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



    // always set up mSettings in constructor. used for building UI
    public StudentUser() {
        makeSettings();
    }

    private void makeSettings() {
        mSettings = new ArrayList<>();
        mSettings.add("My Account");
        mSettings.add("My Orders");
        mSettings.add("Calendar");
    }

    public StudentUser(String id, String name, String type, String email, String bio,
                int points, boolean eligibleForReward, String pendingOrder,
                List<String> orderHistory) {
        makeSettings();
//        this.statistics = statistics;
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

//    public void setStatistics(ArrayList<String> stats) { statistics = stats; }

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

//    public void setSettings(ArrayList<String> settings) { mSettings = settings; }

    public ArrayList<String> getSettings() { return this.mSettings; }

}
