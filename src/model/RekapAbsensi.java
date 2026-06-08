package Model;

public class RekapAbsensi {

    private int karyawanId;
    private String namaKaryawan;
    private int totalHadir;
    private int totalTidakHadir;
    private int totalTerlambat;

    public RekapAbsensi() {
    }

    // Getter & Setter
    public int getKaryawanId() {
        return karyawanId;
    }

    public void setKaryawanId(int karyawanId) {
        this.karyawanId = karyawanId;
    }

    public String getNamaKaryawan() {
        return namaKaryawan;
    }

    public void setNamaKaryawan(String namaKaryawan) {
        this.namaKaryawan = namaKaryawan;
    }

    public int getTotalHadir() {
        return totalHadir;
    }

    public void setTotalHadir(int totalHadir) {
        this.totalHadir = totalHadir;
    }

    public int getTotalTidakHadir() {
        return totalTidakHadir;
    }

    public void setTotalTidakHadir(int totalTidakHadir) {
        this.totalTidakHadir = totalTidakHadir;
    }

    public int getTotalTerlambat() {
        return totalTerlambat;
    }

    public void setTotalTerlambat(int totalTerlambat) {
        this.totalTerlambat = totalTerlambat;
    }
}
