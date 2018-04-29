package edu.duke.compsci290.dukefoodapp.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.location.Location;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created by tannerjohnson on 4/6/18.
 */
@Entity(tableName = "orders")
public class Order implements Parcelable{

    @PrimaryKey
    @ColumnInfo(name = "id")
    @NonNull
    private String id;
    @Ignore
    private Timestamp datePosted;
    @Ignore
    private Timestamp dateDelivered;
    @ColumnInfo(name = "recipient_id")
    private String recipientId;
    @ColumnInfo(name = "recipient_name")
    private String recipientName;
    @ColumnInfo(name = "recipient_phone")
    private String recipientPhone;
    @ColumnInfo(name = "dining_id")
    private String diningId;
    @ColumnInfo(name = "dining_name")
    private String diningName;
    @ColumnInfo(name = "student_id")
    private String studentId;
    @ColumnInfo(name = "student_name")
    private String studentName;
    @ColumnInfo(name = "student_phone")
    private String studentPhone;
    @ColumnInfo(name = "status")
    private int status; // 0: pending student acceptance, 1: pending student delivery + recipient has accepted, 2:student has reconfirmed, 3: ready, 4: complete
    @ColumnInfo(name = "pickup_location")
    private String pickupLocation;
    @ColumnInfo(name = "dropoff_location")
    private String dropoffLocation;
    @ColumnInfo(name = "allergens")
    private String allergens;
    @ColumnInfo(name = "date")
    private String date;
    @ColumnInfo(name = "pickup_time")
    private String pickupTime;
    @ColumnInfo(name = "delivery_time")
    private String deliveryTime;

    public Order() {}

    public String getId() { return this.id; }
    public Timestamp getDatePosted() { return this.datePosted; }
    public Timestamp getDateDelivered() { return this.dateDelivered; }
    public String getRecipientId() { return this.recipientId; }
    public String getDiningId() { return this.diningId; }
    public String getStudentId() { return this.studentId; }
    public int getStatus() { return this.status; }
    public String getPickupLocation() { return this.pickupLocation; }
    public String getDropoffLocation() { return this.dropoffLocation; }
    public String getAllergens() { return this.allergens; }
    public String getRecipientName() {return this.recipientName;}
    public String getRecipientPhone() {return this.recipientPhone;}
    public String getStudentName() {return studentName;}
    public String getStudentPhone() {return studentPhone;}
    public String getDiningName() {return this.diningName;}
    public String getDate() {return this.date;}
    public String getPickupTime() {return this.pickupTime;}
    public String getDeliveryTime() {return this.deliveryTime;}

    public void setId(String id) { this.id = id; }
    public void setDatePosted(Timestamp datePosted) { this.datePosted = datePosted; }
    public void setDateDelivered(Timestamp dateDelivered) { this.dateDelivered = dateDelivered; }
    public void setRecipientId(String recipientId) { this.recipientId = recipientId; }
    public void setDiningId(String diningId) { this.diningId = diningId; }
    public void setStudentId(String studentId) { this.studentId = studentId; }
    public void setStatus(int status) { this.status = status; }
    public void setPickupLocation(String pickupLocation) { this.pickupLocation = pickupLocation; }
    public void setDropoffLocation(String dropoffLocation) { this.dropoffLocation = dropoffLocation; }
    public void setAllergens(String allergens) { this.allergens = allergens; }
    public void setRecipientName(String recipientName) {this.recipientName = recipientName;}
    public void setRecipientPhone(String recipientPhone) {this.recipientPhone = recipientPhone;}
    public void setStudentName(String studentName) {this.studentName = studentName;}
    public void setStudentPhone(String studentPhone) {this.studentPhone = studentPhone;}
    public void setDiningName(String diningName) {this.diningName = diningName;}
    public void setDate(String date) {this.date = date;}
    public void setPickupTime(String pickupTime) {this.pickupTime = pickupTime;}
    public void setDeliveryTime(String deliveryTime) {this.deliveryTime = deliveryTime;}

    @Override
    public int describeContents() {
        return 0;
    }


    @Override
    public void writeToParcel(Parcel out, int i) {
        out.writeString(id);
        out.writeString(recipientId);
        out.writeString(recipientName);
        out.writeString(recipientPhone);
        out.writeString(diningId);
        out.writeString(diningName);
        out.writeString(studentId);
        out.writeString(studentName);
        out.writeString(studentPhone);
        out.writeInt(status);
        out.writeString(pickupLocation);
        out.writeString(dropoffLocation);
        out.writeString(allergens);
        out.writeString(date);
        out.writeString(pickupTime);
        out.writeString(deliveryTime);
    }

    private Order(Parcel in){
        id = in.readString();
        recipientId = in.readString();
        recipientName = in.readString();
        recipientPhone = in.readString();
        diningId = in.readString();
        diningName = in.readString();
        studentId = in.readString();
        studentName = in.readString();
        studentPhone = in.readString();
        status = in.readInt();
        pickupLocation = in.readString();
        dropoffLocation = in.readString();
        allergens = in.readString();
        date = in.readString();
        pickupTime = in.readString();
        deliveryTime = in.readString();
    }

    public static final Parcelable.Creator<Order> CREATOR
            = new Parcelable.Creator<Order>() {

        // This simply calls our new constructor (typically private) and
        // passes along the unmarshalled `Parcel`, and then returns the new object!
        @Override
        public Order createFromParcel(Parcel in) {
            return new Order(in);
        }

        // We just need to copy this and change the type to match our class.
        @Override
        public Order[] newArray(int size) {
            return new Order[size];
        }
    };
}
