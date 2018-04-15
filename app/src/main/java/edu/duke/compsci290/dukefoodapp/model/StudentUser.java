package edu.duke.compsci290.dukefoodapp.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by tannerjohnson on 4/3/18.
 */


public class StudentUser extends UserParent {



    public StudentUser() {
        makeSettings();
    }


    public StudentUser(String id, String name, String type, String email, String bio,
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

}
