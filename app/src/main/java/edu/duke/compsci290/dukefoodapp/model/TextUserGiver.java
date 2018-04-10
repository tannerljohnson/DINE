package edu.duke.compsci290.dukefoodapp.model;

/**
 * Created by tannerjohnson on 4/5/18.
 */

// FOR TESTING ONLY
public class TextUserGiver {

    public static void main(String[] args) {
    	System.out.println("testing...");
        SampleUserFactory factory = SampleUserFactory.getInstance();
        StudentUser studentUser = factory.getSampleStudentUser();
        System.out.println("student user name: " + studentUser.getName());
        RecipientUser recipientUser = factory.getSampleRecipientUser();
        System.out.println("recipient user name: " + recipientUser.getName());
    }

}
