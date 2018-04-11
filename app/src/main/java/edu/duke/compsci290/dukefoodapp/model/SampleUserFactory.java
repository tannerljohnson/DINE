package edu.duke.compsci290.dukefoodapp.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tannerjohnson on 4/5/18.
 */

public class SampleUserFactory {

    private static SampleUserFactory mInstance;
    private StudentUser mStudentUser;
    private RecipientUser mRecipientUser;
//    private Order mPendingOrder;

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
    // makes mStudentUser
    private void doConstruct() {
        mStudentUser = new StudentUser();
        mStudentUser.setId("rqXb2oZizsQLy6mXM2lkCuHU7UH3");
        mStudentUser.setName("Benito Smith");
        mStudentUser.setType("student");
        mStudentUser.setEmail("bsmith@gmail.com");
        mStudentUser.setBio("this is my bio.");
        mStudentUser.setPoints(200);
        mStudentUser.setEligibleForReward(false);
//        SampleOrderFactory factory = SampleOrderFactory.getInstance();
//        mPendingOrder = factory.getSampleOrder();
//
//        mStudentUser.setPendingOrder(mPendingOrder);
//        List<Order> history = new ArrayList<>();
//        mStudentUser.setOrderHistory(history);
        mStudentUser.setPendingOrder("oid_0");
        List<String> history = new ArrayList<>();
        history.add("oid_1");
        history.add("oid_2");
        mStudentUser.setOrderHistory(history);

        //will use a builder package to create a new package
//        ArrayList<String> Statistics = new ArrayList<>();
//        Statistics.add("Orders: 2");
//        Statistics.add("Reward Eligibility: False");
//        Statistics.add("Pending Order: Yes");
//        Statistics.add("Points: 200");
//        mStudentUser.setStatistics(Statistics);
        ArrayList<String> Settings = new ArrayList<>();
        Settings.add("My Account");
        Settings.add("My Orders");
        Settings.add("Calendar");
        mStudentUser.setSettings(Settings);
        constructRecipientUser();
    }

    private void constructRecipientUser() {
        mRecipientUser = new RecipientUser();
        // only allows writing for authenticated users
        mRecipientUser.setId("3ulvzYImhjV8eMwCsG6keo6wgTs1");
        mRecipientUser.setName("Testy McTest");
        mRecipientUser.setType("recipient");
        mRecipientUser.setEmail("test@test.com");
        mRecipientUser.setBio("hey y'all, how are ya?");
        mRecipientUser.setPoints(0);
        mRecipientUser.setEligibleForReward(false);
        mRecipientUser.setPendingOrder("oid_0");
        List<String> history = new ArrayList<>();
        history.add("oid_0"); // not realistic to have pending in history, but only for testing.
        mRecipientUser.setOrderHistory(history);
    }

    // use this method to return student user object
    public StudentUser getSampleStudentUser() {
        return mStudentUser;
    }

    // use this method to return recipient user object
    public RecipientUser getSampleRecipientUser() {
        return mRecipientUser;

    }

}
