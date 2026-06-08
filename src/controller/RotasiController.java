/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import Model.*;

public class RotasiController {

    public static void rotasiJabatan(int karyawanId, int deptBaruId, int jabBaruId, String tanggalMulai) {
        Pegawai p = KaryawanController.cariKaryawan(karyawanId);
        if (p != null) {
            // Simpan histori jabatan lama (sampai tanggal mulai rotasi)
            KaryawanController.simpanHistoriJabatan(karyawanId, p.getDepartemen().getId(), p.getJabatan().getId(),
                    p.getDepartemen() != null ? "2020-01-01" : null, tanggalMulai); // tanggal mulai asal bisa diatur
            // Update karyawan
            Departemen deptBaru = KaryawanController.daftarDepartemen().stream().filter(d -> d.getId() == deptBaruId).findFirst().orElse(null);
            Jabatan jabBaru = KaryawanController.daftarJabatan().stream().filter(j -> j.getId() == jabBaruId).findFirst().orElse(null);
            p.setDepartemen(deptBaru);
            p.setJabatan(jabBaru);
            if (p instanceof KaryawanTetap) {
                ((KaryawanTetap) p).setGajiPokok(jabBaru.getGajiPokok());
                ((KaryawanTetap) p).setTunjangan(jabBaru.getTunjangan());
            }
            KaryawanController.perbaruiKaryawan(p);
            // Simpan histori baru
            KaryawanController.simpanHistoriJabatan(karyawanId, deptBaruId, jabBaruId, tanggalMulai, null);
        }
    }
}
