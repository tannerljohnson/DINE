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
        ArrayList<String> Statistics = new ArrayList<>();
        Statistics.add("Orders: 2");
        Statistics.add("Reward Eligibility: False");
        Statistics.add("Pending Order: Yes");
        Statistics.add("Points: 200");
        mUser.setStatistics(Statistics);
        ArrayList<String> Settings = new ArrayList<>();
        Settings.add("My Account");
        Settings.add("My Orders");
        Settings.add("Calendar");
        mUser.setSettings(Settings);
    }


    // use this method to return user object
    public StudentUser getSampleUser() {
        return mUser;
    }

}
