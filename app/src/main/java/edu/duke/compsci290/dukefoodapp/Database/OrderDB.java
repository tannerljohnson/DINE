package edu.duke.compsci290.dukefoodapp.Database;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import edu.duke.compsci290.dukefoodapp.model.Order;

/**
 * Created by tevin on 4/22/2018.
 */

public class OrderDB implements IDatabase {
    private Order mOrder;
    private DatabaseReference mDatabase;

    public void OrderDB(){
        mDatabase  = FirebaseDatabase.getInstance().getReference();
    }

    @Override
    public void writeToDatabase() {
        mDatabase.child("orders").child(mOrder.getId()).setValue(mOrder);
    }

    @Override
    public void readFromDatabase() {
        mOrder.setDiningName(mDatabase.child("orders").child(mOrder.getId()).child("diningName").getKey());
    }

    @Override
    public Object getObject() {
        return null;
    }

    @Override
    public void setObject(Object object) {
        //pass the order object
        this.mOrder = (Order) object;
    }

    public ArrayList<String> getOrdersByDate(String date){
        //use to get the orders by date
        ArrayList<String> Orders= new ArrayList<String>();
        return Orders;
    }
}
