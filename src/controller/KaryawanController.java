/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import Model.*;
import utility.DataBase;
import java.util.List;

public class KaryawanController {

    public static List<Pegawai> daftarKaryawan() {
        return DataBase.ambilSemuaKaryawan();
    }

    public static Pegawai cariKaryawan(int id) {
        return DataBase.ambilKaryawanById(id);
    }

    public static void tambahKaryawan(Pegawai p) {
        DataBase.tambahKaryawan(p);
    }

    public static void perbaruiKaryawan(Pegawai p) {
        DataBase.perbaruiKaryawan(p);
    }

    public static void hapusKaryawan(int id) {
        DataBase.hapusKaryawan(id);
    }

    public static List<String> historiJabatan(int id) {
        return DataBase.ambilHistoriJabatan(id);
    }

    public static List<Departemen> daftarDepartemen() {
        return DataBase.ambilSemuaDepartemen();
    }

    public static List<Jabatan> daftarJabatan() {
        return DataBase.ambilSemuaJabatan();
    }

    public static void simpanHistoriJabatan(int karyawanId, int deptId, int jabId, String mulai, String selesai) {
        DataBase.simpanHistoriJabatan(karyawanId, deptId, jabId, mulai, selesai);
    }
}
