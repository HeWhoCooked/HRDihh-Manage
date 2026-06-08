package utility;

import Model.*;
import java.sql.*;
import java.util.*;

public class DataBase {

    private static Connection koneksi;
    private static final String URL = "jdbc:mysql://localhost:3306/";
    private static final String USER = "root";
    private static final String PASS = "";
    private static final String DB_NAME = "db_hrdih";

    public static void sambung() {
        try {
            if (koneksi == null || koneksi.isClosed()) {
                Class.forName("com.mysql.cj.jdbc.Driver");
                // Buat database jika belum ada
                Connection conn = DriverManager.getConnection(URL, USER, PASS);
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SHOW DATABASES LIKE '" + DB_NAME + "'");
                if (!rs.next()) {
                    stmt.executeUpdate("CREATE DATABASE " + DB_NAME);
                    System.out.println("[INFO] Database " + DB_NAME + " berhasil dibuat.");
                }
                rs.close();
                stmt.close();
                conn.close();
                // Koneksi ke database
                koneksi = DriverManager.getConnection(URL + DB_NAME + "?useSSL=false&serverTimezone=UTC", USER, PASS);
                buatTabel();
                isiDataAwal();
                inisialisasiRekapAbsensi();
                inisialisasiDataCuti();
                System.out.println("[INFO] Koneksi database berhasil.");
            }
        } catch (Exception e) {
            System.err.println("[ERROR] Gagal koneksi database: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void buatTabel() throws SQLException {
        String[] sqls = {
            "CREATE TABLE IF NOT EXISTS departemen (id INT PRIMARY KEY AUTO_INCREMENT, nama VARCHAR(100) NOT NULL)",
            "CREATE TABLE IF NOT EXISTS jabatan (id INT PRIMARY KEY AUTO_INCREMENT, nama VARCHAR(100) NOT NULL, gaji_pokok DECIMAL(15,2), tunjangan DECIMAL(15,2))",
            "CREATE TABLE IF NOT EXISTS karyawan (id INT PRIMARY KEY AUTO_INCREMENT, nama VARCHAR(100) NOT NULL, usia INT, jenis_kelamin ENUM('L','P'), departemen_id INT, jabatan_id INT, tipe ENUM('Tetap','Magang') NOT NULL, status_aktif BOOLEAN, kuota_cuti INT, gaji_pokok DECIMAL(15,2), tunjangan DECIMAL(15,2), uang_harian DECIMAL(15,2), hari_kerja INT, FOREIGN KEY (departemen_id) REFERENCES departemen(id), FOREIGN KEY (jabatan_id) REFERENCES jabatan(id))",
            "CREATE TABLE IF NOT EXISTS histori_jabatan (id INT PRIMARY KEY AUTO_INCREMENT, karyawan_id INT, departemen_id INT, jabatan_id INT, tanggal_mulai VARCHAR(20), tanggal_selesai VARCHAR(20), FOREIGN KEY (karyawan_id) REFERENCES karyawan(id))",
            "CREATE TABLE IF NOT EXISTS cuti (id INT PRIMARY KEY AUTO_INCREMENT, karyawan_id INT, tanggal_mulai VARCHAR(20), tanggal_selesai VARCHAR(20), status ENUM('Pending','Disetujui','Ditolak'), alasan TEXT, FOREIGN KEY (karyawan_id) REFERENCES karyawan(id))",
            "CREATE TABLE IF NOT EXISTS lembur (id INT PRIMARY KEY AUTO_INCREMENT, karyawan_id INT, tanggal VARCHAR(20), jam_lembur INT, FOREIGN KEY (karyawan_id) REFERENCES karyawan(id))",
            "CREATE TABLE IF NOT EXISTS absensi (id INT PRIMARY KEY AUTO_INCREMENT, karyawan_id INT, tanggal VARCHAR(20), hadir BOOLEAN, terlambat BOOLEAN, FOREIGN KEY (karyawan_id) REFERENCES karyawan(id))",
            "CREATE TABLE IF NOT EXISTS pengguna (id INT PRIMARY KEY AUTO_INCREMENT, username VARCHAR(50) UNIQUE, password VARCHAR(255), role VARCHAR(20))"
        };
        try (Statement stmt = koneksi.createStatement()) {
            for (String sql : sqls) {
                stmt.execute(sql);
            }
            System.out.println("[INFO] Tabel berhasil dibuat/dicek.");
        }
    }

    private static void isiDataAwal() {
        try {
            // Cek apakah data sudah ada
            PreparedStatement psCheck = koneksi.prepareStatement("SELECT COUNT(*) FROM pengguna");
            ResultSet rs = psCheck.executeQuery();
            rs.next();
            int count = rs.getInt(1);
            if (count > 0) {
                System.out.println("[INFO] Data awal sudah ada, skip insert.");
                return;
            }
            System.out.println("[INFO] Mengisi data awal...");

            // Insert departemen
            koneksi.createStatement().execute("INSERT INTO departemen (nama) VALUES ('HRD'), ('IT'), ('Keuangan'), ('Pemasaran')");
            // Insert jabatan
            koneksi.createStatement().execute("INSERT INTO jabatan (nama, gaji_pokok, tunjangan) VALUES "
                    + "('Staff HRD', 5000000, 500000), ('Programmer', 7000000, 1000000), "
                    + "('Akuntan', 6000000, 800000), ('Marketing', 5500000, 700000)");
            // Insert user HRD
            koneksi.createStatement().execute("INSERT INTO pengguna (username, password, role) VALUES "
                    + "('Jaw', '123456', 'HRD'), ('Bintang Syahlevi', 'sayakanlawan', 'HRD'), ('Julian', 'juljul', 'HRD')");
            // Insert karyawan
            insertKaryawanAwal();
            System.out.println("[INFO] Data awal berhasil dimasukkan.");
        } catch (SQLException e) {
            System.err.println("[ERROR] Gagal mengisi data awal: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void insertKaryawanAwal() throws SQLException {
        String[][] data = {
            {"KAISYA ALMAIDJA", "20", "P", "1", "1", "Tetap", "5000000", "500000"},
            {"RAFFA HAFIZH HAUZAAN", "21", "L", "2", "2", "Tetap", "7000000", "1000000"},
            {"ABDE RIOS SATRIANY", "22", "L", "3", "3", "Tetap", "6000000", "800000"},
            {"KAYLA DWI SEPTIANI", "20", "P", "4", "4", "Tetap", "5500000", "700000"},
            {"AN'AMATUS SYAFIRA AULIA AZAHRA", "21", "P", "1", "1", "Tetap", "5000000", "500000"},
            {"AHMAD MUSAFIQ", "22", "L", "2", "2", "Tetap", "7000000", "1000000"},
            {"RIKA ALFIYANI", "21", "P", "3", "3", "Tetap", "6000000", "800000"},
            {"LUCKYTA RIZQIA JUBAEDI", "20", "P", "4", "4", "Tetap", "5500000", "700000"},
            {"AMELIA MARLIANA", "21", "P", "1", "1", "Tetap", "5000000", "500000"},
            {"ANTHONIO OMPUSUNGGU", "23", "L", "2", "2", "Tetap", "7000000", "1000000"},
            {"AZKA BARRA HAIDAR", "20", "L", "3", "3", "Tetap", "6000000", "800000"},
            {"FAHMI MUBAROQ", "22", "L", "4", "4", "Tetap", "5500000", "700000"},
            {"FIRMANSYAH ADE IRAWAN", "21", "L", "1", "1", "Tetap", "5000000", "500000"},
            {"JAW SYANITO RIPTANTO PUTRA", "22", "L", "1", "1", "Tetap", "5000000", "500000"},
            {"MUHAMMAD BINTANG SYAHLEVI", "21", "L", "1", "1", "Tetap", "5000000", "500000"},
            {"MUHAMMAD ZAHRAN SYAUQI AUFA", "20", "L", "2", "2", "Tetap", "7000000", "1000000"},
            {"RIZKY YOGA SALASA", "22", "L", "3", "3", "Tetap", "6000000", "800000"},
            {"VINCENT SEBASTIAN HO", "21", "L", "4", "4", "Tetap", "5500000", "700000"},
            {"YUWAN RANU PRATAMA", "20", "L", "1", "1", "Tetap", "5000000", "500000"},
            {"FADHIL AZHAR PUTRA", "22", "L", "2", "2", "Tetap", "7000000", "1000000"},
            {"JULIAN MANASYE NASYOK", "23", "L", "1", "1", "Tetap", "5000000", "500000"},
            {"FABIAN ANANDA TAUFIK", "21", "L", "3", "3", "Tetap", "6000000", "800000"},
            {"GIBRAN FERDIANSYAH", "20", "L", "4", "4", "Tetap", "5500000", "700000"},
            {"MUHAMMAD NIZAR ISHAMUDDIN ALHANNAN", "22", "L", "1", "1", "Tetap", "5000000", "500000"},
            {"RIZAL HERUDIN", "21", "L", "2", "2", "Tetap", "7000000", "1000000"}
        };
        String sql = "INSERT INTO karyawan (nama, usia, jenis_kelamin, departemen_id, jabatan_id, tipe, status_aktif, kuota_cuti, gaji_pokok, tunjangan) VALUES (?,?,?,?,?,?,1,12,?,?)";
        try (PreparedStatement ps = koneksi.prepareStatement(sql)) {
            for (String[] d : data) {
                ps.setString(1, d[0]);
                ps.setInt(2, Integer.parseInt(d[1]));
                ps.setString(3, d[2]);
                ps.setInt(4, Integer.parseInt(d[3]));
                ps.setInt(5, Integer.parseInt(d[4]));
                ps.setString(6, d[5]);
                ps.setDouble(7, Double.parseDouble(d[6]));
                ps.setDouble(8, Double.parseDouble(d[7]));
                ps.executeUpdate();
            }
        }
    }

    public static void inisialisasiRekapAbsensi() {
        try {
            // Cek apakah tabel sudah ada
            DatabaseMetaData meta = koneksi.getMetaData();
            ResultSet rs = meta.getTables(null, null, "rekap_absensi_bulanan", null);
            if (!rs.next()) {
                // Buat tabel
                String createTable = "CREATE TABLE rekap_absensi_bulanan ("
                        + "id INT PRIMARY KEY AUTO_INCREMENT, "
                        + "karyawan_id INT NOT NULL, "
                        + "bulan VARCHAR(7) NOT NULL, "
                        + "total_hadir INT DEFAULT 0, "
                        + "total_tidak_hadir INT DEFAULT 0, "
                        + "total_terlambat INT DEFAULT 0, "
                        + "FOREIGN KEY (karyawan_id) REFERENCES karyawan(id) ON DELETE CASCADE, "
                        + "UNIQUE KEY (karyawan_id, bulan))";
                koneksi.createStatement().execute(createTable);

                // Isi data contoh untuk bulan sebelumnya
                java.time.LocalDate now = java.time.LocalDate.now();
                java.time.LocalDate bulanLalu = now.minusMonths(1);
                String bulan = bulanLalu.format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM"));

                // Ambil semua karyawan
                String ambilKaryawan = "SELECT id FROM karyawan";
                Statement stmt = koneksi.createStatement();
                ResultSet rsKaryawan = stmt.executeQuery(ambilKaryawan);

                // Generate data contoh (random)
                java.util.Random rand = new java.util.Random();
                String insertSql = "INSERT INTO rekap_absensi_bulanan (karyawan_id, bulan, total_hadir, total_tidak_hadir, total_terlambat) VALUES (?, ?, ?, ?, ?)";
                PreparedStatement ps = koneksi.prepareStatement(insertSql);
                int totalHariKerja = 22;
                while (rsKaryawan.next()) {
                    int id = rsKaryawan.getInt("id");
                    int hadir = 18 + rand.nextInt(5);
                    int tidakHadir = totalHariKerja - hadir;
                    int terlambat = rand.nextInt(4);
                    ps.setInt(1, id);
                    ps.setString(2, bulan);
                    ps.setInt(3, hadir);
                    ps.setInt(4, tidakHadir);
                    ps.setInt(5, terlambat);
                    ps.executeUpdate();
                }
                ps.close();
                stmt.close();
                System.out.println("[INFO] Tabel rekap_absensi_bulanan dibuat dan diisi data contoh untuk bulan " + bulan);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void inisialisasiDataCuti() {
        try {
            // Cek apakah sudah ada cuti dengan status 'Pending'
            PreparedStatement psCek = koneksi.prepareStatement("SELECT COUNT(*) FROM cuti WHERE status = 'Pending'");
            ResultSet rs = psCek.executeQuery();
            rs.next();
            int pendingCount = rs.getInt(1);
            if (pendingCount == 0) {
                // Hapus semua cuti yang mungkin ada (opsional, agar tidak bentrok)
                // koneksi.createStatement().execute("DELETE FROM cuti");

                // Insert contoh cuti
                String[] contoh = {
                    "INSERT INTO cuti (karyawan_id, tanggal_mulai, tanggal_selesai, status, alasan) VALUES (1, '2025-06-10', '2025-06-12', 'Pending', 'Sakit')",
                    "INSERT INTO cuti (karyawan_id, tanggal_mulai, tanggal_selesai, status, alasan) VALUES (2, '2025-06-15', '2025-06-17', 'Pending', 'Acara keluarga')",
                    "INSERT INTO cuti (karyawan_id, tanggal_mulai, tanggal_selesai, status, alasan) VALUES (3, '2025-06-20', '2025-06-22', 'Pending', 'Liburan')",
                    "INSERT INTO cuti (karyawan_id, tanggal_mulai, tanggal_selesai, status, alasan) VALUES (4, '2025-06-05', '2025-06-07', 'Pending', 'Sakit gigi')",
                    "INSERT INTO cuti (karyawan_id, tanggal_mulai, tanggal_selesai, status, alasan) VALUES (5, '2025-06-25', '2025-06-26', 'Pending', 'Urusan pribadi')"
                };
                for (String sql : contoh) {
                    koneksi.createStatement().execute(sql);
                }
                System.out.println("[INFO] Data contoh cuti (Pending) berhasil ditambahkan.");
            } else {
                System.out.println("[INFO] Data cuti sudah ada (" + pendingCount + " pending).");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // ========== METHOD UNTUK AUTENTIKASI DAN CRUD ==========
    public static boolean autentikasiHRD(String username, String password) {
        String sql = "SELECT * FROM pengguna WHERE username=? AND password=? AND role='HRD'";
        try (PreparedStatement ps = koneksi.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // ========== GET ALL KARYAWAN ==========
    public static List<Pegawai> ambilSemuaKaryawan() {
        List<Pegawai> daftar = new ArrayList<>();
        String sql = "SELECT k.*, d.nama as dept_nama, j.nama as jab_nama, j.gaji_pokok as jab_gaji, j.tunjangan as jab_tunjangan "
                + "FROM karyawan k JOIN departemen d ON k.departemen_id=d.id "
                + "JOIN jabatan j ON k.jabatan_id=j.id";
        try (Statement stmt = koneksi.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Pegawai p;
                if ("Tetap".equals(rs.getString("tipe"))) {
                    KaryawanTetap kt = new KaryawanTetap();
                    kt.setGajiPokok(rs.getDouble("gaji_pokok"));
                    kt.setTunjangan(rs.getDouble("tunjangan"));
                    p = kt;
                } else {
                    Magang m = new Magang();
                    m.setUangHarian(rs.getDouble("uang_harian"));
                    m.setHariKerja(rs.getInt("hari_kerja"));
                    p = m;
                }
                p.setId(rs.getInt("id"));
                p.setNama(rs.getString("nama"));
                p.setUsia(rs.getInt("usia"));
                p.setJenisKelamin(rs.getString("jenis_kelamin"));
                p.setStatusAktif(rs.getBoolean("status_aktif"));
                p.setKuotaCuti(rs.getInt("kuota_cuti"));
                p.setTipe(rs.getString("tipe"));
                p.setDepartemen(new Departemen(rs.getInt("departemen_id"), rs.getString("dept_nama")));
                p.setJabatan(new Jabatan(rs.getInt("jabatan_id"), rs.getString("jab_nama"), rs.getDouble("jab_gaji"), rs.getDouble("jab_tunjangan")));
                daftar.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return daftar;
    }

    // ========== GET KARYAWAN BY ID ==========
    public static Pegawai ambilKaryawanById(int id) {
        String sql = "SELECT k.*, d.nama as dept_nama, j.nama as jab_nama, j.gaji_pokok as jab_gaji, j.tunjangan as jab_tunjangan "
                + "FROM karyawan k JOIN departemen d ON k.departemen_id=d.id "
                + "JOIN jabatan j ON k.jabatan_id=j.id WHERE k.id=?";
        try (PreparedStatement ps = koneksi.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Pegawai p;
                if ("Tetap".equals(rs.getString("tipe"))) {
                    KaryawanTetap kt = new KaryawanTetap();
                    kt.setGajiPokok(rs.getDouble("gaji_pokok"));
                    kt.setTunjangan(rs.getDouble("tunjangan"));
                    p = kt;
                } else {
                    Magang m = new Magang();
                    m.setUangHarian(rs.getDouble("uang_harian"));
                    m.setHariKerja(rs.getInt("hari_kerja"));
                    p = m;
                }
                p.setId(rs.getInt("id"));
                p.setNama(rs.getString("nama"));
                p.setUsia(rs.getInt("usia"));
                p.setJenisKelamin(rs.getString("jenis_kelamin"));
                p.setStatusAktif(rs.getBoolean("status_aktif"));
                p.setKuotaCuti(rs.getInt("kuota_cuti"));
                p.setTipe(rs.getString("tipe"));
                p.setDepartemen(new Departemen(rs.getInt("departemen_id"), rs.getString("dept_nama")));
                p.setJabatan(new Jabatan(rs.getInt("jabatan_id"), rs.getString("jab_nama"), rs.getDouble("jab_gaji"), rs.getDouble("jab_tunjangan")));
                return p;
            }
        } catch (SQLException e) {
        }
        return null;
    }

    // ========== TAMBAH KARYAWAN ==========
    public static void tambahKaryawan(Pegawai p) {
        String sql = "INSERT INTO karyawan (nama, usia, jenis_kelamin, departemen_id, jabatan_id, tipe, status_aktif, kuota_cuti, gaji_pokok, tunjangan, uang_harian, hari_kerja) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
        try (PreparedStatement ps = koneksi.prepareStatement(sql)) {
            ps.setString(1, p.getNama());
            ps.setInt(2, p.getUsia());
            ps.setString(3, p.getJenisKelamin());
            ps.setInt(4, p.getDepartemen().getId());
            ps.setInt(5, p.getJabatan().getId());
            ps.setString(6, p.getTipe());
            ps.setBoolean(7, p.isStatusAktif());
            ps.setInt(8, p.getKuotaCuti());
            if (p instanceof KaryawanTetap) {
                ps.setDouble(9, ((KaryawanTetap) p).getGajiPokok());
                ps.setDouble(10, ((KaryawanTetap) p).getTunjangan());
                ps.setNull(11, Types.DOUBLE);
                ps.setNull(12, Types.INTEGER);
            } else {
                ps.setNull(9, Types.DOUBLE);
                ps.setNull(10, Types.DOUBLE);
                ps.setDouble(11, ((Magang) p).getUangHarian());
                ps.setInt(12, ((Magang) p).getHariKerja());
            }
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // ========== PERBARUI KARYAWAN ==========
    public static void perbaruiKaryawan(Pegawai p) {
        String sql = "UPDATE karyawan SET nama=?, usia=?, jenis_kelamin=?, departemen_id=?, jabatan_id=?, tipe=?, status_aktif=?, kuota_cuti=?, gaji_pokok=?, tunjangan=?, uang_harian=?, hari_kerja=? WHERE id=?";
        try (PreparedStatement ps = koneksi.prepareStatement(sql)) {
            ps.setString(1, p.getNama());
            ps.setInt(2, p.getUsia());
            ps.setString(3, p.getJenisKelamin());
            ps.setInt(4, p.getDepartemen().getId());
            ps.setInt(5, p.getJabatan().getId());
            ps.setString(6, p.getTipe());
            ps.setBoolean(7, p.isStatusAktif());
            ps.setInt(8, p.getKuotaCuti());
            if (p instanceof KaryawanTetap) {
                ps.setDouble(9, ((KaryawanTetap) p).getGajiPokok());
                ps.setDouble(10, ((KaryawanTetap) p).getTunjangan());
                ps.setNull(11, Types.DOUBLE);
                ps.setNull(12, Types.INTEGER);
            } else {
                ps.setNull(9, Types.DOUBLE);
                ps.setNull(10, Types.DOUBLE);
                ps.setDouble(11, ((Magang) p).getUangHarian());
                ps.setInt(12, ((Magang) p).getHariKerja());
            }
            ps.setInt(13, p.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // ========== HAPUS KARYAWAN ==========
    public static void hapusKaryawan(int id) {
        String sql = "DELETE FROM karyawan WHERE id=?";
        try (PreparedStatement ps = koneksi.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
        }
    }

    // ========== DEPARTEMEN & JABATAN ==========
    public static List<Departemen> ambilSemuaDepartemen() {
        List<Departemen> list = new ArrayList<>();
        try (Statement stmt = koneksi.createStatement(); ResultSet rs = stmt.executeQuery("SELECT * FROM departemen")) {
            while (rs.next()) {
                list.add(new Departemen(rs.getInt("id"), rs.getString("nama")));
            }
        } catch (SQLException e) {
        }
        return list;
    }

    public static List<Jabatan> ambilSemuaJabatan() {
        List<Jabatan> list = new ArrayList<>();
        try (Statement stmt = koneksi.createStatement(); ResultSet rs = stmt.executeQuery("SELECT * FROM jabatan")) {
            while (rs.next()) {
                list.add(new Jabatan(rs.getInt("id"), rs.getString("nama"), rs.getDouble("gaji_pokok"), rs.getDouble("tunjangan")));
            }
        } catch (SQLException e) {
        }
        return list;
    }

    // ========== ABSENSI ==========
    // ========== REKAP ABSENSI BULANAN ==========
    public static List<Model.RekapAbsensi> ambilRekapAbsensiByBulan(String bulan) {
        List<Model.RekapAbsensi> list = new ArrayList<>();
        String sql = "SELECT r.*, k.nama FROM rekap_absensi_bulanan r "
                + "JOIN karyawan k ON r.karyawan_id = k.id "
                + "WHERE r.bulan = ? ORDER BY k.nama";
        try (PreparedStatement ps = koneksi.prepareStatement(sql)) {
            ps.setString(1, bulan);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Model.RekapAbsensi ra = new Model.RekapAbsensi();
                ra.setKaryawanId(rs.getInt("karyawan_id"));
                ra.setNamaKaryawan(rs.getString("nama"));
                ra.setTotalHadir(rs.getInt("total_hadir"));
                ra.setTotalTidakHadir(rs.getInt("total_tidak_hadir"));
                ra.setTotalTerlambat(rs.getInt("total_terlambat"));
                list.add(ra);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // ========== HISTORI JABATAN ==========
    public static List<String> ambilHistoriJabatan(int karyawanId) {
        List<String> histori = new ArrayList<>();
        String sql = "SELECT d.nama as dept, j.nama as jab, hj.tanggal_mulai, hj.tanggal_selesai FROM histori_jabatan hj JOIN departemen d ON hj.departemen_id=d.id JOIN jabatan j ON hj.jabatan_id=j.id WHERE hj.karyawan_id=? ORDER BY hj.tanggal_mulai DESC";
        try (PreparedStatement ps = koneksi.prepareStatement(sql)) {
            ps.setInt(1, karyawanId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String entry = rs.getString("dept") + " - " + rs.getString("jab") + " (" + rs.getString("tanggal_mulai") + " s/d " + (rs.getString("tanggal_selesai") == null ? "sekarang" : rs.getString("tanggal_selesai")) + ")";
                histori.add(entry);
            }
        } catch (SQLException e) {
        }
        return histori;
    }

    public static void simpanHistoriJabatan(int karyawanId, int deptId, int jabId, String mulai, String selesai) {
        String sql = "INSERT INTO histori_jabatan (karyawan_id, departemen_id, jabatan_id, tanggal_mulai, tanggal_selesai) VALUES (?,?,?,?,?)";
        try (PreparedStatement ps = koneksi.prepareStatement(sql)) {
            ps.setInt(1, karyawanId);
            ps.setInt(2, deptId);
            ps.setInt(3, jabId);
            ps.setString(4, mulai);
            if (selesai != null) {
                ps.setString(5, selesai);
            } else {
                ps.setNull(5, Types.VARCHAR);
            }
            ps.executeUpdate();
        } catch (SQLException e) {
        }
    }

    // ========== GAJI ==========
    public static double hitungBonusLembur(int karyawanId) {
        String sql = "SELECT SUM(jam_lembur) as total FROM lembur WHERE karyawan_id=?";
        try (PreparedStatement ps = koneksi.prepareStatement(sql)) {
            ps.setInt(1, karyawanId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("total") * 20000;
            }
        } catch (SQLException e) {
        }
        return 0;
    }

    public static double hitungPotonganAbsen(int karyawanId) {
        String sql = "SELECT total_tidak_hadir FROM rekap_absensi_bulanan WHERE karyawan_id=? AND bulan = DATE_FORMAT(CURDATE(), '%Y-%m')";
        try (PreparedStatement ps = koneksi.prepareStatement(sql)) {
            ps.setInt(1, karyawanId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("total_tidak_hadir") * 100000;
            }
        } catch (SQLException e) {
        }
        return 0;
    }

    public static double hitungPotonganTerlambat(int karyawanId) {
        String sql = "SELECT total_terlambat FROM rekap_absensi_bulanan WHERE karyawan_id=? AND bulan = DATE_FORMAT(CURDATE(), '%Y-%m')";
        try (PreparedStatement ps = koneksi.prepareStatement(sql)) {
            ps.setInt(1, karyawanId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("total_terlambat") * 50000;
            }
        } catch (SQLException e) {
        }
        return 0;
    }

    // ========== CUTI ==========
    public static List<Cuti> ambilSemuaCuti() {
        List<Cuti> list = new ArrayList<>();
        String sql = "SELECT c.*, k.nama as karyawan_nama FROM cuti c JOIN karyawan k ON c.karyawan_id=k.id ORDER BY c.tanggal_mulai DESC";
        try (Statement stmt = koneksi.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Cuti c = new Cuti();
                c.setId(rs.getInt("id"));
                c.setKaryawanId(rs.getInt("karyawan_id"));
                c.setKaryawanNama(rs.getString("karyawan_nama"));
                c.setTanggalMulai(rs.getString("tanggal_mulai"));
                c.setTanggalSelesai(rs.getString("tanggal_selesai"));
                c.setStatus(rs.getString("status"));
                c.setAlasan(rs.getString("alasan"));
                list.add(c);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static void tambahCuti(int karyawanId, String mulai, String selesai, String alasan) {
        String sql = "INSERT INTO cuti (karyawan_id, tanggal_mulai, tanggal_selesai, status, alasan) VALUES (?,?,?, 'Pending', ?)";
        try (PreparedStatement ps = koneksi.prepareStatement(sql)) {
            ps.setInt(1, karyawanId);
            ps.setString(2, mulai);
            ps.setString(3, selesai);
            ps.setString(4, alasan);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void hapusCuti(int idCuti) {
        String sql = "DELETE FROM cuti WHERE id=?";
        try (PreparedStatement ps = koneksi.prepareStatement(sql)) {
            ps.setInt(1, idCuti);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void perbaruiStatusCuti(int idCuti, String status) {
        String sql = "UPDATE cuti SET status=? WHERE id=?";
        try (PreparedStatement ps = koneksi.prepareStatement(sql)) {
            ps.setString(1, status);
            ps.setInt(2, idCuti);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // ========== KUOTA CUTI ==========
    public static int ambilKuotaCuti(int karyawanId) {
        String sql = "SELECT kuota_cuti FROM karyawan WHERE id = ?";
        try (PreparedStatement ps = koneksi.prepareStatement(sql)) {
            ps.setInt(1, karyawanId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("kuota_cuti");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static void kurangiKuotaCuti(int karyawanId, int jumlah) {
        String sql = "UPDATE karyawan SET kuota_cuti = kuota_cuti - ? WHERE id = ? AND kuota_cuti >= ?";
        try (PreparedStatement ps = koneksi.prepareStatement(sql)) {
            ps.setInt(1, jumlah);
            ps.setInt(2, karyawanId);
            ps.setInt(3, jumlah);
            int affected = ps.executeUpdate();
            if (affected == 0) {
                throw new SQLException("Kuota cuti tidak mencukupi");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

// Hitung jumlah hari cuti (selisih tanggal)
    public static int hitungHariCuti(String tanggalMulai, String tanggalSelesai) {
        // asumsi format yyyy-MM-dd
        try {
            java.time.LocalDate mulai = java.time.LocalDate.parse(tanggalMulai);
            java.time.LocalDate selesai = java.time.LocalDate.parse(tanggalSelesai);
            return (int) java.time.temporal.ChronoUnit.DAYS.between(mulai, selesai) + 1;
        } catch (Exception e) {
            return 1; // default 1 hari jika error
        }
    }

    // ========== LEMBUR ==========
    public static void tambahLembur(int karyawanId, String tanggal, int jam) {
        String sql = "INSERT INTO lembur (karyawan_id, tanggal, jam_lembur) VALUES (?,?,?)";
        try (PreparedStatement ps = koneksi.prepareStatement(sql)) {
            ps.setInt(1, karyawanId);
            ps.setString(2, tanggal);
            ps.setInt(3, jam);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<Lembur> ambilSemuaLembur() {
        List<Lembur> list = new ArrayList<>();
        String sql = "SELECT l.*, k.nama as karyawan_nama FROM lembur l JOIN karyawan k ON l.karyawan_id=k.id ORDER BY l.tanggal DESC";
        try (Statement stmt = koneksi.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Lembur l = new Lembur();
                l.setId(rs.getInt("id"));
                l.setKaryawanId(rs.getInt("karyawan_id"));
                l.setKaryawanNama(rs.getString("karyawan_nama"));
                l.setTanggal(rs.getString("tanggal"));
                l.setJamLembur(rs.getInt("jam_lembur"));
                list.add(l);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
