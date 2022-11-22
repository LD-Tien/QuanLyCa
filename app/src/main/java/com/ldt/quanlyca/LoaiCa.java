package com.ldt.quanlyca;

import java.io.Serializable;

public class LoaiCa implements Serializable {
    private String tenKH, tenThuong, dacTinh, mauSac, urlImage;

    public LoaiCa() {
    }

    public LoaiCa(String tenKH, String tenThuong, String dacTinh, String mauSac, String urlImage) {
        this.tenKH = tenKH;
        this.tenThuong = tenThuong;
        this.dacTinh = dacTinh;
        this.mauSac = mauSac;
        this.urlImage = urlImage;
    }

    public String getTenKH() {
        return tenKH;
    }

    public void setTenKH(String tenKH) {
        this.tenKH = tenKH;
    }

    public String getTenThuong() {
        return tenThuong;
    }

    public void setTenThuong(String tenThuong) {
        this.tenThuong = tenThuong;
    }

    public String getDacTinh() {
        return dacTinh;
    }

    public void setDacTinh(String dacTinh) {
        this.dacTinh = dacTinh;
    }

    public String getMauSac() {
        return mauSac;
    }

    public void setMauSac(String mauSac) {
        this.mauSac = mauSac;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }
}
