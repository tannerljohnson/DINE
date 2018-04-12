package edu.duke.compsci290.dukefoodapp.model;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by tevin on 4/11/2018.
 */

public class SampleDayFactory {
    private static SampleDayFactory mInstance;
    private Day mday;


    public static SampleDayFactory getInstance() {
        if (mInstance == null) {
            mInstance = new SampleDayFactory();
        }
        return mInstance;
    }

    public void SampleDayFactory(){doConstruct();}

    public void doConstruct(){
        SampleOrderFactory orderFactory =  SampleOrderFactory.getInstance();
        ArrayList<Order> orders = new ArrayList<>();
        orders.add(orderFactory.getSampleOrder());
        mday.setOrders(orders);
        mday.setDate("July 24, 2018");
    }

    //use to get day
    public IDay getDay() {
        return mday;
    }
}
