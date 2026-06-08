package Model;

public class Jabatan {

    private int id;
    private String nama;
    private double gajiPokok;
    private double tunjangan;

    public Jabatan() {
    }

    public Jabatan(int id, String nama, double gajiPokok, double tunjangan) {
        this.id = id;
        this.nama = nama;
        this.gajiPokok = gajiPokok;
        this.tunjangan = tunjangan;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public double getGajiPokok() {
        return gajiPokok;
    }

    public void setGajiPokok(double gajiPokok) {
        this.gajiPokok = gajiPokok;
    }

    public double getTunjangan() {
        return tunjangan;
    }

    public void setTunjangan(double tunjangan) {
        this.tunjangan = tunjangan;
    }
}
