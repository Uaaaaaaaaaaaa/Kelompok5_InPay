
package com.example.aplikasiloginmodern;


public class Panti {

    private String id;
    private String namaPanti;
    private String alamatPanti;
    private String deskripsiPanti;
    private String fotoUrl; // kalau kamu pakai foto, boleh dihapus kalau tidak dipakai
    private int jumlahAnak;

    // WAJIB untuk Firebase
    public Panti() {
    }

    public Panti(String id, String namaPanti, String alamatPanti, String deskripsiPanti, String fotoUrl, int jumlahAnak) {
        this.id = id;
        this.namaPanti = namaPanti;
        this.alamatPanti = alamatPanti;
        this.deskripsiPanti = deskripsiPanti;
        this.fotoUrl = fotoUrl;
        this.jumlahAnak = jumlahAnak;
    }

    // Constructor tanpa ID (untuk tambah baru)
    public Panti(String namaPanti, String alamatPanti, String deskripsiPanti, String fotoUrl, int jumlahAnak) {
        this.namaPanti = namaPanti;
        this.alamatPanti = alamatPanti;
        this.deskripsiPanti = deskripsiPanti;
        this.fotoUrl = fotoUrl;
        this.jumlahAnak = jumlahAnak;
    }

    // =========================
    // Getter & Setter Firebase
    // =========================

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNamaPanti() {
        return namaPanti;
    }

    public void setNamaPanti(String namaPanti) {
        this.namaPanti = namaPanti;
    }

    public String getAlamatPanti() {
        return alamatPanti;
    }

    public void setAlamatPanti(String alamatPanti) {
        this.alamatPanti = alamatPanti;
    }

    public String getDeskripsiPanti() {
        return deskripsiPanti;
    }

    public void setDeskripsiPanti(String deskripsiPanti) {
        this.deskripsiPanti = deskripsiPanti;
    }

    public String getFotoUrl() {
        return fotoUrl;
    }

    public void setFotoUrl(String fotoUrl) {
        this.fotoUrl = fotoUrl;
    }

    public int getJumlahAnak() {
        return jumlahAnak;
    }

    public void setJumlahAnak(int jumlahAnak) {
        this.jumlahAnak = jumlahAnak;
    }
}
