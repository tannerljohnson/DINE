package edu.duke.compsci290.dukefoodapp.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tannerjohnson on 4/5/18.
 */

public class SampleUserFactory {

    private static SampleUserFactory mInstance;
    private StudentUser mUser;

    public static SampleUserFactory getInstance() {
        if (mInstance == null) {
            mInstance = new SampleUserFactory();
        }
        return mInstance;
    }

    private SampleUserFactory() {
        doConstruct();
    }

    // constructs our one instance of SampleUserFactory mInstance
    // makes mUser
    private void doConstruct() {
        mUser = new StudentUser();
        mUser.setUserName("Benito Smith");
        mUser.setUserType("student");
        mUser.setUserEmail("bsmith@gmail.com");
        mUser.setUserBio("this is my bio.");
        mUser.setUserPoints(200);
        mUser.setUserEligibleForReward(false);
        mUser.setUserPendingOrder("o_id_123");
        List<String> history = new ArrayList<>();
        history.add("o_id_1");
        history.add("o_id_2");
        mUser.setUserOrderHistory(history);
    }

    // use this method to return user object
    public StudentUser getSampleUser() {
        return mUser;
    }

}
