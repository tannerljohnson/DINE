package edu.duke.compsci290.dukefoodapp.model;

import java.util.ArrayList;

/**
 * Created by tevin on 4/11/2018.
 */

public class Day implements IDay {
    public String mDate;
    public ArrayList<Order> mOrders;

    private void Day(){}

    private void Day(ArrayList orders, String date){
        this.mDate = date;
        this.mOrders = orders;
    }


    @Override
    public String getDate() {
        return this.mDate;
    }

    @Override
    public ArrayList<Order> getOrders() {
        return this.mOrders;
    }

    @Override
    public void setOrders(ArrayList<Order> orders) {
        this.mOrders = orders;
    }

    @Override
    public void setDate(String date) {
        this.mDate = date;
    }

}
