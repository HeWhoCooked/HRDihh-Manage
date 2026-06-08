/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import Model.Lembur;
import utility.DataBase;
import java.util.List;

public class LemburController {

    public static void tambahLembur(int karyawanId, String tanggal, int jam) {
        DataBase.tambahLembur(karyawanId, tanggal, jam);
    }

    public static List<Lembur> semuaLembur() {
        return DataBase.ambilSemuaLembur();
    }
}
