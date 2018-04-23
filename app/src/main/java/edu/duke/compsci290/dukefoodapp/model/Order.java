package edu.duke.compsci290.dukefoodapp.model;

import android.location.Location;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created by tannerjohnson on 4/6/18.
 */

public class Order implements Serializable{

    private String id;
    private Timestamp datePosted;
    private Timestamp dateDelivered;
    private String recipientId;
    private String recipientName;
    private String recipientPhone;
    private String diningId;
    private String diningName;
    private String studentId;
    private String studentName;
    private String studentPhone;
    private int status; // 0: pending student acceptance, 1: pending student delivery + recipient has accepted, 2:student has reconfirmed, 3: ready, 4: complete
    private String pickupLocation;
    private String dropoffLocation;
    private String allergens;

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

}
