/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUI;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;

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
        
        // Desain Tombol Rincian (Warna Teal / Biru Toska Modern)
        btnRincian.setBackground(new java.awt.Color(22, 160, 133));
        btnRincian.setForeground(java.awt.Color.WHITE);
        btnRincian.setFont(new java.awt.Font("Roboto", java.awt.Font.BOLD, 12));
        btnRincian.setFocusPainted(false);
        
        // Memasukkan tombol ke dalam panel agar posisinya pas di tengah sel
        java.awt.GridBagConstraints gbc = new java.awt.GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new java.awt.Insets(2, 2, 2, 2);
        panel.add(btnRincian, gbc);
        panel.setBackground(java.awt.Color.WHITE);

        // LOGIKA KETIKA TOMBOL RINCIAN DIKLIK
        btnRincian.addActionListener(e -> {
            fireEditingStopped(); // Matikan mode edit sel aktif
            int row = table.getSelectedRow();
            
            // Mengambil data dari baris tabel yang diklik
            String nama = (table.getValueAt(row, 1) != null) ? table.getValueAt(row, 1).toString() : "-";
            String dept = (table.getValueAt(row, 2) != null) ? table.getValueAt(row, 2).toString() : "-";
            String totalGaji = (table.getValueAt(row, 3) != null) ? table.getValueAt(row, 3).toString() : "0";
            
            // Membuat kalkulasi simulasi rincian slip gaji sederhana
            String slipMessage = "====================================\n"
                               + "          SLIP RINCIAN GAJI KARYAWAN        \n"
                               + "====================================\n\n"
                               + "Nama Karyawan  : " + nama + "\n"
                               + "Departemen         : " + dept + "\n"
                               + "------------------------------------\n"
                               + "Gaji Pokok             : Rp " + totalGaji + "\n"
                               + "Tunjangan Jabatan  : Rp 0\n"
                               + "Potongan Absensi  : Rp 0\n"
                               + "------------------------------------\n"
                               + "TOTAL TERIMA       : Rp " + totalGaji + "\n\n"
                               + "====================================";
            
            // Tampilkan pop up rincian
            JOptionPane.showMessageDialog(table, slipMessage, "Detail Gaji - " + nama, JOptionPane.INFORMATION_MESSAGE);
        });
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
