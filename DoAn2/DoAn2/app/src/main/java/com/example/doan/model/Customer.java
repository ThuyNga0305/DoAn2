package com.example.doan.model;



public class Customer  {
    private String UserID;
    private String UserName;
    private String UserDiaChi;
    private String UserPass;
    private String UserSDT;
    private String UserMail;
    private String UserNgaySinh;
    private int PhanQuyen;

    public void setUserID(String userID) {
        UserID = userID;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getUserDiaChi() {
        return UserDiaChi;
    }

    public void setUserDiaChi(String userDiaChi) {
        UserDiaChi = userDiaChi;
    }

    public String getUserPass() {
        return UserPass;
    }

    public void setUserPass(String userPass) {
        UserPass = userPass;
    }

    public String getUserSDT() {
        return UserSDT;
    }

    public void setUserSDT(String userSDT) {
        UserSDT = userSDT;
    }

    public String getUserMail() {
        return UserMail;
    }

    public void setUserMail(String userMail) {
        UserMail = userMail;
    }

    public String getUserNgaySinh() {
        return UserNgaySinh;
    }

    public void setUserNgaySinh(String userNgaySinh) {
        UserNgaySinh = userNgaySinh;
    }

    public int getPhanQuyen() {
        return PhanQuyen;
    }

    public void setPhanQuyen(int phanQuyen) {
        PhanQuyen = phanQuyen;
    }

    public Customer() {
    }

    public Customer(String userID, String userName, String userDiaChi, String userPass, String userSDT, String userMail, String userNgaySinh, int phanQuyen) {
        UserID = userID;
        UserName = userName;
        UserDiaChi = userDiaChi;
        UserPass = userPass;
        UserSDT = userSDT;
        UserMail = userMail;
        UserNgaySinh = userNgaySinh;
        PhanQuyen = phanQuyen;
    }

    public String getUserID() {
        return UserID;
    }

    public void getUserID(String maSach) {
    }
}
