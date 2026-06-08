/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import Model.Cuti;
import utility.DataBase;
import java.util.List;

public class CutiController {

    public static List<Cuti> semuaCuti() {
        return DataBase.ambilSemuaCuti();
    }

    public static void setujuiCuti(int idCuti) {
    // Ambil data cuti terlebih dahulu
    Cuti cuti = null;
    List<Cuti> semua = DataBase.ambilSemuaCuti();
    for (Cuti c : semua) {
        if (c.getId() == idCuti) {
            cuti = c;
            break;
        }
    }
    if (cuti == null) {
        throw new IllegalArgumentException("Data cuti tidak ditemukan");
    }
    // Hitung jumlah hari cuti
    int hari = DataBase.hitungHariCuti(cuti.getTanggalMulai(), cuti.getTanggalSelesai());
    // Cek kuota karyawan
    int kuota = DataBase.ambilKuotaCuti(cuti.getKaryawanId());
    if (kuota >= hari) {
        // Kurangi kuota
        DataBase.kurangiKuotaCuti(cuti.getKaryawanId(), hari);
        // Update status cuti menjadi Disetujui
        DataBase.perbaruiStatusCuti(idCuti, "Disetujui");
    } else {
        throw new IllegalStateException("Kuota cuti tidak mencukupi (sisa: " + kuota + " hari, butuh: " + hari + " hari)");
    }
}

    public static void tolakCuti(int id) {
        DataBase.perbaruiStatusCuti(id, "Ditolak");
    }

    public static void ajukanCuti(int karyawanId, String mulai, String selesai, String alasan) {
        DataBase.tambahCuti(karyawanId, mulai, selesai, alasan);
    }
}
