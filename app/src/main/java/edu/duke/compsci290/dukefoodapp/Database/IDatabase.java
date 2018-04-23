package edu.duke.compsci290.dukefoodapp.Database;

/**
 * Created by tevin on 4/22/2018.
 */

public interface IDatabase {
    void writeToDatabase();
    void readFromDatabase();
    void setObject(Object object);
    Object getObject();
}
