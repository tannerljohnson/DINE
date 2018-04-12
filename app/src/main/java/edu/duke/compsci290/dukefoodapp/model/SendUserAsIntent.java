package edu.duke.compsci290.dukefoodapp.model;

import android.content.Context;
import android.content.Intent;

import java.util.ArrayList;
import java.util.List;

import edu.duke.compsci290.dukefoodapp.UserActivities.MyOrdersActivity;

/**
 * Created by tevin on 4/12/2018.
 */

public class SendUserAsIntent {
    public Intent mIntent;
    public String mString;
    public IUser mUser;
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

    public SendUserAsIntent(){};

    public SendUserAsIntent(Context context, IUser user, Class mclass){
        this.mIntent = new Intent(context, mclass);
        this.mUser = user;
        getUserVariables(user);

        if(type.equals("student")){
            StudentUser studentuser = new StudentUser(id, name, type, email, bio,
            points, eligibleForReward, pendingOrder, orderHistory);
            mIntent.putExtra("type",this.type);
            mIntent.putExtra("user",studentuser);
        }

        if(type.equals("recipient")){
            RecipientUser recipientuser = new RecipientUser(id, name, type, email, bio,
                    points, eligibleForReward, pendingOrder, orderHistory);
            mIntent.putExtra("type",this.type);
            mIntent.putExtra("user",recipientuser);
        }

        if(type.equals("dining")){
            DiningUser dininguser = new DiningUser(id, name, type, email, bio,
                    points, eligibleForReward, pendingOrder, orderHistory);
            mIntent.putExtra("type",this.type);
            mIntent.putExtra("user",dininguser);
        }
    };

    public void getUserVariables(IUser user){
        this.mSettings = user.getSettings();
        this. id = user.getId();
        this.name = user.getName();
        this.type = user.getType();
        this.email = user.getEmail();
        this.bio = user.getBio();
        this.points = user.getPoints();
        this.eligibleForReward = user.getEligibleForReward();
        this.points = user.getPoints();
        this.pendingOrder = user.getPendingOrder();
        this.orderHistory = user.getOrderHistory();
    }

    public Intent getIntent(){return this.mIntent;}
}
