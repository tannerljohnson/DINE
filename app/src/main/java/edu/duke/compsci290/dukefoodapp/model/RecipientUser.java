package edu.duke.compsci290.dukefoodapp.model;

import java.util.List;

/**
 * Created by tannerjohnson on 4/3/18.
 */


public class RecipientUser extends UserParent {

    private int familySize;


    public RecipientUser() {
        makeSettings();
    }

    public RecipientUser(String id, String name, String type, String email, String phone, String bio,
                       int points, boolean eligibleForReward, List<String> pendingOrders,
                       List<String> orderHistory) {
        makeSettings();
        this.id = id;
        this.name = name;
        this.type = type;
        this.phone = phone;
        this.email = email;
        this.bio = bio;
        this.points = points;
        this.eligibleForReward = eligibleForReward;
        this.pendingOrders = pendingOrders;
        this.orderHistory = orderHistory;
    }

    public void setFamilySize(int famSize) {
        this.familySize = famSize;
    }

    public int getFamilySize() {
        return this.familySize;
    }

}
