package edu.duke.compsci290.dukefoodapp.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tannerjohnson on 4/5/18.
 */

public interface IUser {
    String getId();
    String getName();
    String getType();
    String getEmail();
    String getBio();
    String getPendingOrder();
    List<String> getOrderHistory();
    boolean getEligibleForReward();
    int getPoints();
    ArrayList<String> getStatistics() throws UserMalformedException;
    ArrayList<String> getSettings();
}
