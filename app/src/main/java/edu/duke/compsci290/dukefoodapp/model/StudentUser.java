package edu.duke.compsci290.dukefoodapp.model;

import java.util.List;

/**
 * Created by tannerjohnson on 4/3/18.
 */


public class StudentUser extends UserParent {



    public StudentUser() {
        makeSettings();
    }


    public StudentUser(String id, String name, String type, String email, String phone, String bio,
                int points, boolean eligibleForReward, List<String> pendingOrders,
                List<String> orderHistory) {
        makeSettings();
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.type = type;
        this.email = email;
        this.bio = bio;
        this.points = points;
        this.eligibleForReward = eligibleForReward;
        this.pendingOrders = pendingOrders;
        this.orderHistory = orderHistory;
    }

}
