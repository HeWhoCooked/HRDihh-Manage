/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

import controller.KaryawanController;
import Model.Pegawai;
import Model.KaryawanTetap;
import Model.Magang;
import utility.DataBase;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.*;
import java.awt.*;

/**
 *
 * @author stevedownes
 */
public class GajiTableAction extends javax.swing.AbstractCellEditor implements javax.swing.table.TableCellRenderer, javax.swing.table.TableCellEditor {

    private final JPanel panel = new JPanel(new java.awt.GridBagLayout());
    private final JButton btnRincian = new JButton("Rincian Gaji");
    private final JTable table;

    public GajiTableAction(JTable table) {
        this.table = table;

        btnRincian.setBackground(new java.awt.Color(22, 160, 133));
        btnRincian.setForeground(java.awt.Color.WHITE);
        btnRincian.setFont(new java.awt.Font("Roboto", java.awt.Font.BOLD, 12));
        btnRincian.setFocusPainted(false);

        java.awt.GridBagConstraints gbc = new java.awt.GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new java.awt.Insets(2, 2, 2, 2);
        panel.add(btnRincian, gbc);
        panel.setBackground(java.awt.Color.WHITE);

        btnRincian.addActionListener(e -> {
            fireEditingStopped();
            int row = table.getSelectedRow();
            if (row == -1) {
                return;
            }

            String nama = (table.getValueAt(row, 1) != null) ? table.getValueAt(row, 1).toString() : "-";
            String dept = (table.getValueAt(row, 2) != null) ? table.getValueAt(row, 2).toString() : "-";

            Pegawai p = KaryawanController.daftarKaryawan().stream()
                    .filter(k -> k.getNama().equals(nama))
                    .findFirst()
                    .orElse(null);
            if (p == null) {
                JOptionPane.showMessageDialog(table, "Data karyawan tidak ditemukan", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Data dari database (sudah termasuk potongan absen & terlambat)
            double bonusLembur = DataBase.hitungBonusLembur(p.getId());
            double potonganAbsen = DataBase.hitungPotonganAbsen(p.getId());
            double potonganTerlambat = DataBase.hitungPotonganTerlambat(p.getId());
            double gajiPokok = (p instanceof KaryawanTetap) ? ((KaryawanTetap) p).getGajiPokok() : 0;
            double tunjangan = (p instanceof KaryawanTetap) ? ((KaryawanTetap) p).getTunjangan() : 0;
            double gajiKotor = p.hitungGaji() + bonusLembur;
            double totalPotongan = potonganAbsen + potonganTerlambat;
            double pajak = (gajiKotor - totalPotongan) * 0.05;
            double gajiBersih = gajiKotor - totalPotongan - pajak;

            JPanel rincianPanel = new JPanel(new GridLayout(0, 2, 10, 10));
            rincianPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

            rincianPanel.add(new JLabel("Nama Karyawan:"));
            rincianPanel.add(new JLabel(p.getNama()));
            rincianPanel.add(new JLabel("Departemen:"));
            rincianPanel.add(new JLabel(dept));

            if (p instanceof KaryawanTetap) {
                rincianPanel.add(new JLabel("Gaji Pokok:"));
                rincianPanel.add(new JLabel("Rp " + formatRupiah(gajiPokok)));
                rincianPanel.add(new JLabel("Tunjangan:"));
                rincianPanel.add(new JLabel("Rp " + formatRupiah(tunjangan)));
            } else if (p instanceof Magang) {
                Magang m = (Magang) p;
                rincianPanel.add(new JLabel("Uang Harian:"));
                rincianPanel.add(new JLabel("Rp " + formatRupiah(m.getUangHarian())));
                rincianPanel.add(new JLabel("Hari Kerja:"));
                rincianPanel.add(new JLabel(String.valueOf(m.getHariKerja())));
            }

            rincianPanel.add(new JLabel("Bonus Lembur:"));
            rincianPanel.add(new JLabel("Rp " + formatRupiah(bonusLembur)));
            rincianPanel.add(new JLabel("Potongan Absen:"));
            rincianPanel.add(new JLabel("Rp " + formatRupiah(potonganAbsen)));
            rincianPanel.add(new JLabel("Potongan Terlambat:"));
            rincianPanel.add(new JLabel("Rp " + formatRupiah(potonganTerlambat)));
            rincianPanel.add(new JLabel("Pajak (5%):"));
            rincianPanel.add(new JLabel("Rp " + formatRupiah(pajak)));
            rincianPanel.add(new JLabel("Gaji Bersih:"));
            rincianPanel.add(new JLabel("Rp " + formatRupiah(gajiBersih)));

            JOptionPane.showMessageDialog(table, rincianPanel, "Rincian Gaji - " + p.getNama(), JOptionPane.INFORMATION_MESSAGE);
        });
    }

    private String formatRupiah(double angka) {
        return String.format("%,.0f", angka).replace(",", ".");
    }

    @Override

    public java.awt.Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        if (isSelected) {
            panel.setBackground(table.getSelectionBackground());
        } else {
            panel.setBackground(java.awt.Color.WHITE);
        }
        return panel;
    }

    @Override
    public java.awt.Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        panel.setBackground(table.getSelectionBackground());
        return panel;
    }

    @Override
    public Object getCellEditorValue() {
        return "";
    }
}
