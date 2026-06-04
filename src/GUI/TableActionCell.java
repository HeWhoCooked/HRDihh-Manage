/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUI;

import java.awt.Color;
import java.awt.Component;
import javax.swing.*;
import javax.swing.table.*;

/**
 *
 * @author stevedownes
 */
public class TableActionCell extends AbstractCellEditor implements TableCellRenderer, TableCellEditor {
    private final PanelAction renderPanel = new PanelAction();
    private final PanelAction editPanel = new PanelAction();

    public TableActionCell(JTable table) {
        
        // LOGIKA TOMBOL LIHAT
        editPanel.btnLihat.addActionListener(e -> {
            fireEditingStopped();
            int row = table.getSelectedRow();
            String nama = table.getValueAt(row, 1).toString();
            String dept = table.getValueAt(row, 2).toString();
            String posisi = table.getValueAt(row, 3).toString();
            
            JOptionPane.showMessageDialog(table, "Nama: " + nama + "\nDept: " + dept + "\nPosisi: " + posisi, "Detail", JOptionPane.INFORMATION_MESSAGE);
        });

        // LOGIKA TOMBOL EDIT
        editPanel.btnEdit.addActionListener(e -> {
            fireEditingStopped();
            int row = table.getSelectedRow();
            String nama = table.getValueAt(row, 1).toString();
            String namaBaru = JOptionPane.showInputDialog(table, "Ubah Nama Karyawan:", nama);
            if (namaBaru != null && !namaBaru.trim().isEmpty()) {
                table.setValueAt(namaBaru, row, 1);
            }
        });

        // LOGIKA TOMBOL HAPUS (DENGAN POP-UP KONFIRMASI)
        editPanel.btnHapus.addActionListener(e -> {
            fireEditingStopped();
            int row = table.getSelectedRow();
            
            // Mengambil nama karyawan untuk memperjelas pop-up
            String namaKaryawan = (table.getValueAt(row, 1) != null) ? table.getValueAt(row, 1).toString() : "Karyawan ini";
            
            // Pop-up konfirmasi sesuai permintaan Anda
            int jawab = JOptionPane.showConfirmDialog(
                table, 
                "Apakah Anda yakin ingin menghapus karyawan " + namaKaryawan + "?", 
                "Konfirmasi Hapus", 
                JOptionPane.YES_NO_OPTION, 
                JOptionPane.WARNING_MESSAGE
            );
            
            if (jawab == JOptionPane.YES_OPTION) {
                DefaultTableModel model = (DefaultTableModel) table.getModel();
                model.removeRow(row);
                
                // Menyusun ulang nomor urut setelah baris dihapus
                for (int i = 0; i < model.getRowCount(); i++) {
                    model.setValueAt(i + 1, i, 0);
                }
            }
        });
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        renderPanel.setBackground(isSelected ? table.getSelectionBackground() : Color.WHITE);
        return renderPanel;
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        editPanel.setBackground(table.getSelectionBackground());
        return editPanel;
    }

    @Override
    public Object getCellEditorValue() {
        return "";
    }
}
