package edu.duke.compsci290.dukefoodapp.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by tannerjohnson on 4/10/18.
 */


public class DiningUser extends UserParent implements IUser,Serializable {

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
}
