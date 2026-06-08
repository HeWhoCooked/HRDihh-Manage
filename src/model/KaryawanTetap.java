package Model;

public class KaryawanTetap extends Pegawai {

    private double gajiPokok;
    private double tunjangan;

    public KaryawanTetap() {
        super();
        this.tipe = "Tetap";
    }

    @Override
    public double hitungGaji() {
        return gajiPokok + tunjangan;
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
