/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import Model.RekapAbsensi;
import utility.DataBase;
import java.util.List;

public class AbsenController {

    public static List<RekapAbsensi> rekapPerBulan(String bulan) {
        return DataBase.ambilRekapAbsensiByBulan(bulan);
    }
}
