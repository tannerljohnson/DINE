package edu.duke.compsci290.dukefoodapp.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tannerjohnson on 4/3/18.
 */

public class StudentUser implements IUser {


    public ArrayList<String> statistics;
    public ArrayList<String> mSettings;

    public String id;
    public String name;
    public String type;
    public String email;
    public String bio;
    public int points;
    public boolean eligibleForReward;
    public String pendingOrder;
    public List<String> orderHistory;


    // empty constructor requires all necessary setters
    public StudentUser() {
    }

    public StudentUser(String id, String name, String type, String email, String bio,
                int points, boolean eligibleForReward, String pendingOrder,
                List<String> orderHistory, ArrayList<String> statistics) {

        this.statistics = statistics;
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

    public void setStatistics(ArrayList<String> stats) { statistics = stats; }

    public ArrayList<String> getStatistics() { return this.statistics; }

    public void setSettings(ArrayList<String> settings) { mSettings = settings; }

    public ArrayList<String> getSettings() { return this.mSettings; }

}
