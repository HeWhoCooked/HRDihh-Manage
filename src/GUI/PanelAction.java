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
    public JButton EditBT = new JButton("Edit");
    public JButton LihatBT = new JButton("Lihat");

    public PanelAction() {
        setLayout(new GridBagLayout());
        setBackground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 6, 0, 6); // Memberikan jarak 6px di kiri dan kanan tombol

        // 1. Pasang Tombol Lihat di sebelah kiri (gridx = 0)
        gbc.gridx = 0;
        LihatBT.setBackground(new Color(46, 204, 113)); // Hijau
        LihatBT.setForeground(Color.WHITE);
        LihatBT.setFocusPainted(false);
        LihatBT.setFont(new Font("Roboto", Font.PLAIN, 12));
        add(LihatBT, gbc);

        // 2. Pasang Tombol Edit di sebelah kanan (gridx = 1)
        gbc.gridx = 1;
        EditBT.setBackground(new Color(52, 152, 219)); // Biru
        EditBT.setForeground(Color.WHITE);
        EditBT.setFocusPainted(false);
        EditBT.setFont(new Font("Roboto", Font.PLAIN, 12));
        add(EditBT, gbc);
        
        setLayout(new FlowLayout(FlowLayout.CENTER, 10, 5));
        setBackground(Color.WHITE);

        // Desain tombol Edit (Biru)
        EditBT.setBackground(new Color(52, 152, 219));
        EditBT.setForeground(Color.WHITE);
        EditBT.setFocusPainted(false);

        // Desain tombol Lihat (Hijau)
        LihatBT.setBackground(new Color(46, 204, 113));
        LihatBT.setForeground(Color.WHITE);
        LihatBT.setFocusPainted(false);

        // Masukkan ke panel berdampingan
        add(LihatBT);
        add(EditBT);
    }
}
