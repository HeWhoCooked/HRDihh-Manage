package Model;

public class Lembur {

    private int id;
    private int karyawanId;
    private String karyawanNama;
    private String departemenNama;
    private String tanggal;  // "yyyy-MM-dd"
    private int jamLembur;

    // getter setter
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getKaryawanId() {
        return karyawanId;
    }

    public void setKaryawanId(int karyawanId) {
        this.karyawanId = karyawanId;
    }

    public String getKaryawanNama() {
        return karyawanNama;
    }

    public void setKaryawanNama(String karyawanNama) {
        this.karyawanNama = karyawanNama;
    }

    public String getDepartemenNama() {
        return departemenNama;
    }

    public void setDepartemenNama(String departemenNama) {
        this.departemenNama = departemenNama;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public int getJamLembur() {
        return jamLembur;
    }

    public void setJamLembur(int jamLembur) {
        this.jamLembur = jamLembur;
    }
}
