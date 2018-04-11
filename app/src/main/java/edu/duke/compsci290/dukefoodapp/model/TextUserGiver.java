package edu.duke.compsci290.dukefoodapp.model;

import java.util.List;

/**
 * Created by tannerjohnson on 4/5/18.
 */

// FOR TESTING ONLY
public class TextUserGiver {

    public static void main(String[] args) throws UserMalformedException {
    	System.out.println("testing...");
        SampleUserFactory factory = SampleUserFactory.getInstance();
        StudentUser studentUser = factory.getSampleStudentUser();

//        Statistics studentUserStats = new Statistics(studentUser);
//        System.out.println(studentUserStats.getOrdersDone());
//        System.out.println(studentUserStats.getPendingOrderBoolean());
//        System.out.println(studentUserStats.getPointTotal());
//        System.out.println(studentUserStats.getRewardEligibility());

        int i=0;
        List<String> allStats = studentUser.getStatistics();
        for(String s : allStats) {
            i++;
            System.out.println("stat #" +Integer.toString(i) + ": " + s);
        }

        System.out.println("student user name: " + studentUser.getName());
        RecipientUser recipientUser = factory.getSampleRecipientUser();
        System.out.println("recipient user name: " + recipientUser.getName());
    }

}
