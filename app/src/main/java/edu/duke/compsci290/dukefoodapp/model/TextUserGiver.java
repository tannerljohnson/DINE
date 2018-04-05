package edu.duke.compsci290.dukefoodapp.model;

/**
 * Created by tannerjohnson on 4/5/18.
 */

public class TextUserGiver {

    public static void main(String[] args) {
    	System.out.println("testing...");
        SampleUserFactory factory = SampleUserFactory.getInstance();
        StudentUser user = factory.getSampleUser();
        System.out.println("user name: " + user.getUserName());
    }

}
