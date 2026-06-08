package Model;

public abstract class Pegawai {

    protected int id;
    protected String nama;
    protected int usia;
    protected String jenisKelamin;
    protected Departemen departemen;
    protected Jabatan jabatan;
    protected boolean statusAktif;
    protected int kuotaCuti;
    protected String tipe; // "Tetap" atau "Magang"

    public abstract double hitungGaji();

    // Constructor
    public Pegawai() {
    }

    public Pegawai(String nama, int usia, String jenisKelamin, Departemen departemen, Jabatan jabatan, String tipe) {
        this.nama = nama;
        this.usia = usia;
        this.jenisKelamin = jenisKelamin;
        this.departemen = departemen;
        this.jabatan = jabatan;
        this.tipe = tipe;
        this.statusAktif = true;
        this.kuotaCuti = 12;
    }

    // Getter dan Setter
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

    public int getUsia() {
        return usia;
    }

    public void setUsia(int usia) {
        this.usia = usia;
    }

    public String getJenisKelamin() {
        return jenisKelamin;
    }

    public void setJenisKelamin(String jenisKelamin) {
        this.jenisKelamin = jenisKelamin;
    }

    public Departemen getDepartemen() {
        return departemen;
    }

    public void setDepartemen(Departemen departemen) {
        this.departemen = departemen;
    }

    public Jabatan getJabatan() {
        return jabatan;
    }

    public void setJabatan(Jabatan jabatan) {
        this.jabatan = jabatan;
    }

    public boolean isStatusAktif() {
        return statusAktif;
    }

    public void setStatusAktif(boolean statusAktif) {
        this.statusAktif = statusAktif;
    }

    public int getKuotaCuti() {
        return kuotaCuti;
    }

    public void setKuotaCuti(int kuotaCuti) {
        this.kuotaCuti = kuotaCuti;
    }

    public String getTipe() {
        return tipe;
    }

    public void setTipe(String tipe) {
        this.tipe = tipe;
    }
}
