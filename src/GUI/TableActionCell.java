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
        
        // LOGIKA TOMBOL "LIHAT"
        editPanel.LihatBT.addActionListener(e -> {
            fireEditingStopped(); // Hentikan mode edit sel tabel
            int row = table.getSelectedRow();
            
            // Mengambil data dari baris yang diklik
            String nama = table.getValueAt(row, 1).toString();
            String dept = table.getValueAt(row, 2).toString();
            String posisi = table.getValueAt(row, 3).toString();
            
            // Menampilkan Detail Data via Pop-up
            String detailMessage = "=== DETAIL DATA KARYAWAN ===\n\n"
                    + "Nama : " + nama + "\n"
                    + "Departemen : " + dept + "\n"
                    + "Jabatan : " + posisi;
            
            JOptionPane.showMessageDialog(table, detailMessage, "Lihat Data", JOptionPane.INFORMATION_MESSAGE);
        });

        // LOGIKA TOMBOL "EDIT"
        editPanel.EditBT.addActionListener(e -> {
            fireEditingStopped();
            int row = table.getSelectedRow();
            String nama = table.getValueAt(row, 1).toString();
        });
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        if (isSelected) {
            renderPanel.setBackground(table.getSelectionBackground());
        } else {
            renderPanel.setBackground(Color.WHITE);
        }
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
