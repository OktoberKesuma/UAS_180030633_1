package com.aa183.oktober;


import java.util.Date;

public class film {

    private int idfilm;
    private String judul;
    private Date tanggal;
    private String gambar;
    private String sutradara;
    private String sinopsis;

    public film(int idfilm, String judul, Date tanggal, String gambar, String sutradara, String sinopsis) {
        this.idfilm = idfilm;
        this.judul = judul;
        this.tanggal = tanggal;
        this.gambar = gambar;
        this.sutradara = sutradara;
        this.sinopsis = sinopsis;
    }

    public int getIdfilm() {
        return idfilm;
    }

    public void setIdfilm(int idfilm) {
        this.idfilm = idfilm;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public Date getTanggal() {
        return tanggal;
    }

    public void setTanggal(Date tanggal) {
        this.tanggal = tanggal;
    }

    public String getGambar() {
        return gambar;
    }

    public void setGambar(String gambar) {
        this.gambar = gambar;
    }

    public String getSutradara() {
        return sutradara;
    }

    public void setSutradara(String sutradara) {
        this.sutradara = sutradara;
    }

    public String getSinopsis() {
        return sinopsis;
    }

    public void setSinopsis(String sinopsis) {
        this.sinopsis = sinopsis;
    }
}
