package edu.duke.compsci290.dukefoodapp.model;

import android.location.Location;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by tannerjohnson on 4/5/18.
 */

public class SampleOrderFactory {

    private static SampleOrderFactory mInstance;
    private Order mOrder;

    public static SampleOrderFactory getInstance() {
        if (mInstance == null) {
            mInstance = new SampleOrderFactory();
        }
        return mInstance;
    }

    private SampleOrderFactory() {
        doConstruct();
    }

    // constructs our one instance of SampleUserFactory mInstance
    // makes mOrder
    private void doConstruct() {
        mOrder = new Order();
        mOrder.setId("1oiwh1092he");
        Timestamp today = new Timestamp(System.currentTimeMillis());
        mOrder.setDatePosted(today);
        Timestamp tomorrow = new Timestamp(System.currentTimeMillis());
        tomorrow.setDate(tomorrow.getDay()+1);
        mOrder.setDateDelivered(tomorrow);
        mOrder.setRecipientId("3ulvzYImhjV8eMwCsG6keo6wgTs1");
        mOrder.setStudentId("rqXb2oZizsQLy6mXM2lkCuHU7UH3");
        mOrder.setDiningId("lasndSPROUT12312");
        mOrder.setStatus(1);
        Location pickup = new Location("");//provider name is unnecessary
        pickup.setLatitude(0.0d);//your coords of course
        pickup.setLongitude(0.0d);
        Location dropoff = new Location("");
        dropoff.setLatitude(0.0d);
        dropoff.setLongitude(0.0d);
        mOrder.setPickupLocation(pickup);
        mOrder.setDropoffLocation(dropoff);
    }


    // use this method to return student user object
    public Order getSampleOrder() {
        return mOrder;
    }
}
