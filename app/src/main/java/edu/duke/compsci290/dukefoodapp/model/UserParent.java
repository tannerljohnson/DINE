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
    protected ArrayList<String> settings;

    protected String id;

    protected String name;

    protected String type;

    protected String email;

    protected String phone;

    protected String bio;

    protected int points;

    protected boolean eligibleForReward;

    protected List<String> pendingOrders;

    protected List<String> orderHistory;

    protected int familySize;
    // admin (pickup) and recipient (dropoff) must have address

    protected String address;
//    protected byte[] imageByteArray;

    protected void makeSettings() {
        settings = new ArrayList<>();
        settings.add("Home");
        settings.add("My Account");
        settings.add("My Orders");
        settings.add("Calendar");
    }

    // getters and setters -- must be in accordance with Realtime Database data structure
    public String getId() { return this.id; }

    public void setId(String id) { this.id = id; }

    public String getPhone() { return this. phone; }

    public void setPhone(String phone) { this.phone = phone; }

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
        return this.pendingOrders;
    }

    public void setPendingOrders(List<String> order) { pendingOrders = order; }

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

    public ArrayList<String> getSettings() { return this.settings; }

    public void setFamilySize(int size) {
        this.familySize = size;
    }

    public int getFamilySize() {
        return this.familySize;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress() {
        return this.address;
    }


    public void updateOrderHistory(String id){
        if (this.orderHistory == null){
            this.orderHistory = new ArrayList<>();
        }
        this.orderHistory.add(id);
    }
    public void updatePendingOrders(String id){
        if (this.pendingOrders == null){
            this.pendingOrders = new ArrayList<>();
        }
        this.pendingOrders.add(id);
    }

}
