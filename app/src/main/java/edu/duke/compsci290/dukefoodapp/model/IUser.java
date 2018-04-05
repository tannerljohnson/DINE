package edu.duke.compsci290.dukefoodapp.model;

import java.util.List;

/**
 * Created by tannerjohnson on 4/5/18.
 */

public interface IUser {
    String getUserName();
    String getUserType();
    String getUserEmail();
    String getUserBio();
    String getUserPendingOrder();
    List<String> getUserOrderHistory();
}
