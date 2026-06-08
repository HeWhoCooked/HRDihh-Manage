/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

import controller.KaryawanController;
import Model.Pegawai;
import java.awt.Color;
import java.awt.Component;
import javax.swing.*;
import javax.swing.table.*;

/**
 *
 * @author stevedownes
 */
public class TableActionCell extends AbstractCellEditor implements TableCellRenderer, TableCellEditor {

    private final PanelAction panel = new PanelAction(); // satu panel untuk render dan edit
    private JTable table;
    private ManageKaryawan parentFrame;

    public TableActionCell(JTable table, ManageKaryawan parent) {
        this.table = table;
        this.parentFrame = parent;
        setupActions();
    }

    private void setupActions() {
        panel.btnLihat.addActionListener(e -> {
            fireEditingStopped();
            int row = table.getSelectedRow();
            if (row != -1) {
                String nama = table.getValueAt(row, 1).toString();
                Pegawai p = cariPegawaiByNama(nama);
                if (p != null) {
                    new InfoLengkap(p.getId()).setVisible(true);
                    
                }
            }
        });

        panel.btnEdit.addActionListener(e -> {
            fireEditingStopped();
            int row = table.getSelectedRow();
            if (row != -1) {
                String nama = table.getValueAt(row, 1).toString();
                Pegawai p = cariPegawaiByNama(nama);
                if (p != null) {
                    new EditData(p.getId()).setVisible(true);
                }
            }
        });

        panel.btnHapus.addActionListener(e -> {
            fireEditingStopped();
            int row = table.getSelectedRow();
            if (row != -1) {
                String nama = table.getValueAt(row, 1).toString();
                Pegawai p = cariPegawaiByNama(nama);
                if (p != null) {
                    int confirm = JOptionPane.showConfirmDialog(table, "Hapus karyawan " + nama + "?", "Konfirmasi", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                    if (confirm == JOptionPane.YES_OPTION) {
                        KaryawanController.hapusKaryawan(p.getId());
                        parentFrame.muatDataKaryawan(); // refresh tabel
                    }
                }
            }
        });
    }

    private Pegawai cariPegawaiByNama(String nama) {
        return KaryawanController.daftarKaryawan().stream()
                .filter(k -> k.getNama().equals(nama))
                .findFirst()
                .orElse(null);
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        panel.setBackground(isSelected ? table.getSelectionBackground() : Color.WHITE);
        return panel;
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        panel.setBackground(table.getSelectionBackground());
        return panel;
    }

    @Override
    public Object getCellEditorValue() {
        return "";
    }
}
