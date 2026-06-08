package view;

import javax.swing.*;
import java.awt.*;

public class PanelAction extends JPanel {
    public JButton btnEdit = new JButton("Edit");
    public JButton btnLihat = new JButton("Lihat");
    public JButton btnHapus = new JButton("Hapus");

    public PanelAction() {
        setLayout(new GridLayout(1, 3, 5, 5)); 
        setBackground(Color.WHITE);
        setOpaque(true);

        // Ukuran tombol seragam
        Dimension btnSize = new Dimension(80, 30);
        btnLihat.setPreferredSize(btnSize);
        btnEdit.setPreferredSize(btnSize);
        btnHapus.setPreferredSize(btnSize);

        btnLihat.setBackground(new Color(46, 204, 113));
        btnLihat.setForeground(Color.WHITE);
        btnLihat.setFocusPainted(false);

        btnEdit.setBackground(new Color(52, 152, 219));
        btnEdit.setForeground(Color.WHITE);
        btnEdit.setFocusPainted(false);

        btnHapus.setBackground(new Color(231, 76, 60));
        btnHapus.setForeground(Color.WHITE);
        btnHapus.setFocusPainted(false);

        add(btnLihat);
        add(btnEdit);
        add(btnHapus);
    }
}