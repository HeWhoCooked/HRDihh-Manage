/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUI;

import javax.swing.*;
import java.awt.*;

/**
 *
 * @author stevedownes
 */
public class PanelAction extends JPanel {
    public JButton btnEdit = new JButton("Edit");
    public JButton btnLihat = new JButton("Lihat");
    public JButton btnHapus = new JButton("Hapus");

    public PanelAction() {
        setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        setBackground(Color.WHITE);

        // Desain tombol Lihat (Hijau)
        btnLihat.setBackground(new Color(46, 204, 113));
        btnLihat.setForeground(Color.WHITE);
        btnLihat.setFocusPainted(false);

        // Desain tombol Edit (Biru)
        btnEdit.setBackground(new Color(52, 152, 219));
        btnEdit.setForeground(Color.WHITE);
        btnEdit.setFocusPainted(false);

        // Desain tombol Hapus (Merah)
        btnHapus.setBackground(new Color(231, 76, 60));
        btnHapus.setForeground(Color.WHITE);
        btnHapus.setFocusPainted(false);

        add(btnLihat);
        add(btnEdit);
        add(btnHapus);
    }
}
