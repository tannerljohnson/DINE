package edu.duke.compsci290.dukefoodapp.Room;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import edu.duke.compsci290.dukefoodapp.model.Order;
import edu.duke.compsci290.dukefoodapp.model.UserParent;

/**
 * Created by tannerjohnson on 4/26/18.
 */
@Dao
public interface OrderDao {

    @Query("SELECT * FROM orders")
    List<Order> getAll();

    @Query("SELECT * FROM orders WHERE student_id LIKE :queryId")
    List<Order> findPendingStudentOrdersById(String queryId);

    @Query("SELECT * FROM orders WHERE recipient_id LIKE :queryId")
    List<Order> findPendingRecipientOrdersById(String queryId);

    @Query("SELECT * FROM orders WHERE dining_id LIKE :queryId")
    List<Order> findPendingDiningOrdersById(String queryId);

    @Insert
    void insertOrder(Order order);

    @Delete
    void delete(Order order);


}
