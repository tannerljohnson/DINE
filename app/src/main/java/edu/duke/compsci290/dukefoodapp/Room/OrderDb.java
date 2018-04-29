package edu.duke.compsci290.dukefoodapp.Room;


import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import edu.duke.compsci290.dukefoodapp.model.Order;
import edu.duke.compsci290.dukefoodapp.model.UserParent;

/**
 * Created by tannerjohnson on 4/26/18.
 */


@Database(entities = {Order.class}, version = 1)
public abstract class OrderDb extends RoomDatabase {
    public abstract OrderDao orderDao();
}
