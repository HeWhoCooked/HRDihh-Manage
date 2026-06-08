package Model;

public class Magang extends Pegawai {

    private double uangHarian;
    private int hariKerja;

    public Magang() {
        super();
        this.tipe = "Magang";
    }

    @Override
    public double hitungGaji() {
        return uangHarian * hariKerja;
    }

    public double getUangHarian() {
        return uangHarian;
    }

    public void setUangHarian(double uangHarian) {
        this.uangHarian = uangHarian;
    }

    public int getHariKerja() {
        return hariKerja;
    }

    public void setHariKerja(int hariKerja) {
        this.hariKerja = hariKerja;
    }
}
