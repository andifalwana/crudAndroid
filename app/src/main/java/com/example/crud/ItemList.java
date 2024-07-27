package com.example.crud;

public class ItemList {
    private  String id, judul, penulis, sampul;
    public  ItemList(){

    }

    public ItemList(String judul, String penulis, String sampul) {
        this.judul = judul;
        this.penulis = penulis;
        this.sampul = sampul;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getPenulis() {
        return penulis;
    }

    public void setPenulis(String penulis) {
        this.penulis = penulis;
    }

    public String getSampul() {
        return sampul;
    }

    public void setSampul(String sampul) {
        this.sampul = sampul;
    }
}
