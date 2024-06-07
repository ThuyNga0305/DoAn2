package com.example.doan.model;


public class Categories  {
    private String LoaiID;
    private String NameLoai;
    private String mota;
    private String hinhanh;

    public String getLoaiID() {
        return LoaiID;
    }

    public void setLoaiID(String loaiID) {
        LoaiID = loaiID;
    }

    public String getNameLoai() {
        return NameLoai;
    }

    public void setNameLoai(String nameLoai) {
        NameLoai = nameLoai;
    }

    public String getMota() {
        return mota;
    }

    public void setMota(String mota) {
        this.mota = mota;
    }

    public String getHinhanh() {
        return hinhanh;
    }

    public void setHinhanh(String hinhanh) {
        this.hinhanh = hinhanh;
    }

    public Categories() {
    }

    public Categories(String loaiID, String nameLoai, String mota, String hinhanh) {
        LoaiID = loaiID;
        NameLoai = nameLoai;
        this.mota = mota;
        this.hinhanh = hinhanh;
    }

    @Override
    public String toString() {
        return NameLoai;
    }

    public String getLoaiID(String maSach) {
        return LoaiID;
    }
}

