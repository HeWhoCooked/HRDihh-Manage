/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import Model.Pegawai;
import utility.DataBase;
import java.util.List;

public class GajiController {

    public static List<Pegawai> daftarKaryawanDenganGaji() {
        return DataBase.ambilSemuaKaryawan();
    }

    public static double hitungBonusLembur(int karyawanId) {
        return DataBase.hitungBonusLembur(karyawanId);
    }

    public static double hitungPotonganAbsen(int karyawanId) {
        return DataBase.hitungPotonganAbsen(karyawanId);
    }

    public static double hitungPotonganTerlambat(int karyawanId) {
        return DataBase.hitungPotonganTerlambat(karyawanId);
    }

    public static double hitungPajak(double gajiKotor) {
        return gajiKotor * 0.05;
    }
}
