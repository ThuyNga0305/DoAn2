package com.example.doan.model;

import java.io.Serializable;

public class   Order implements Serializable {
    private String DHID;
    private String UserID;
    private String StoreID;
    private String DHTrangThai;
    private String DHThoiGian;

    public String getDHID() {
        return DHID;
    }

    public void setDHID(String DHID) {
        this.DHID = DHID;
    }

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }

    public String getStoreID() {
        return StoreID;
    }

    public void setStoreID(String storeID) {
        StoreID = storeID;
    }

    public String getDHTrangThai() {
        return DHTrangThai;
    }

    public void setDHTrangThai(String dHTrangThai) {
        this.DHTrangThai = dHTrangThai;
    }

    public String getDHThoiGian() {
        return DHThoiGian;
    }

    public void setDHThoiGian(String dHThoiGian) {
        this.DHThoiGian = dHThoiGian;
    }

    public Order() {
    }

    public Order(String DHID, String userID, String storeID, String dHTrangThai, String dHThoiGian) {
        this.DHID = DHID;
        UserID = userID;
        StoreID = storeID;
        this.DHTrangThai = dHTrangThai;
        this.DHThoiGian = dHThoiGian;
    }
}
