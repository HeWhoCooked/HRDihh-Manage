package GUI;

/**
 *
 * @author stevedownes
 */
public class ManageKaryawan extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(ManageKaryawan.class.getName());

    public ManageKaryawan() {
        initComponents();
        this.setExtendedState(javax.swing.JFrame.MAXIMIZED_BOTH);
        SematkanTombolTabel();
        
        javax.swing.table.JTableHeader header = DataTbl.getTableHeader();
        
        header.setDefaultRenderer(new javax.swing.table.DefaultTableCellRenderer() {
            @Override
            public java.awt.Component getTableCellRendererComponent(javax.swing.JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                
                setBackground(new java.awt.Color(0, 0, 0));
                setForeground(java.awt.Color.WHITE);
                setFont(new java.awt.Font("Roboto", java.awt.Font.BOLD, 14));
                setHorizontalAlignment(javax.swing.SwingConstants.CENTER); 
                
                return this;
            }
        });
        
        header.setPreferredSize(new java.awt.Dimension(header.getPreferredSize().width, 40));
    }
    
    private void SematkanTombolTabel(){
        TableActionCell actionCell = new TableActionCell(DataTbl);
        
        DataTbl.getColumnModel().getColumn(4).setCellRenderer(actionCell);
        DataTbl.getColumnModel().getColumn(4).setCellEditor(actionCell);
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlHeader = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        BackKwnBT = new javax.swing.JButton();
        pnlTempatTabel = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        DataTbl = new javax.swing.JTable();
        pnlAtasTabel = new javax.swing.JPanel();
        TambahKwnBT = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        pnlHeader.setBackground(new java.awt.Color(255, 255, 255));
        pnlHeader.setBorder(javax.swing.BorderFactory.createEmptyBorder(15, 25, 15, 25));
        pnlHeader.setPreferredSize(new java.awt.Dimension(881, 120));
        pnlHeader.setLayout(new javax.swing.BoxLayout(pnlHeader, javax.swing.BoxLayout.LINE_AXIS));

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Gambar/NamaLogo.jpeg"))); // NOI18N
        pnlHeader.add(jLabel1);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        pnlHeader.add(jPanel1);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        pnlHeader.add(jPanel2);

        BackKwnBT.setBackground(new java.awt.Color(255, 255, 255));
        BackKwnBT.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Gambar/ExitLogo.png"))); // NOI18N
        BackKwnBT.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 0));
        BackKwnBT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BackKwnBTActionPerformed(evt);
            }
        });
        pnlHeader.add(BackKwnBT);

        getContentPane().add(pnlHeader, java.awt.BorderLayout.PAGE_START);

        pnlTempatTabel.setBackground(new java.awt.Color(240, 240, 240));
        pnlTempatTabel.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10));
        pnlTempatTabel.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        pnlTempatTabel.setLayout(new java.awt.BorderLayout());

        DataTbl.setBackground(new java.awt.Color(255, 255, 255));
        DataTbl.setFont(new java.awt.Font("Roboto", 0, 16)); // NOI18N
        DataTbl.setForeground(new java.awt.Color(44, 62, 80));
        DataTbl.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "No", "Nama", "Departemen", "Posisi", "Aksi"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        DataTbl.setGridColor(new java.awt.Color(230, 230, 230));
        DataTbl.setRowHeight(45);
        DataTbl.setShowGrid(true);
        jScrollPane1.setViewportView(DataTbl);
        if (DataTbl.getColumnModel().getColumnCount() > 0) {
            DataTbl.getColumnModel().getColumn(0).setMinWidth(50);
            DataTbl.getColumnModel().getColumn(0).setPreferredWidth(50);
            DataTbl.getColumnModel().getColumn(0).setMaxWidth(50);
            DataTbl.getColumnModel().getColumn(4).setMinWidth(200);
            DataTbl.getColumnModel().getColumn(4).setPreferredWidth(200);
            DataTbl.getColumnModel().getColumn(4).setMaxWidth(200);
        }

        pnlTempatTabel.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        pnlAtasTabel.setBackground(new java.awt.Color(240, 240, 240));
        pnlAtasTabel.setLayout(new java.awt.BorderLayout());

        TambahKwnBT.setBackground(new java.awt.Color(0, 0, 0));
        TambahKwnBT.setFont(new java.awt.Font("Roboto", 1, 24)); // NOI18N
        TambahKwnBT.setForeground(new java.awt.Color(255, 255, 255));
        TambahKwnBT.setText("+");
        TambahKwnBT.setBorder(null);
        TambahKwnBT.setPreferredSize(new java.awt.Dimension(60, 29));
        pnlAtasTabel.add(TambahKwnBT, java.awt.BorderLayout.CENTER);

        pnlTempatTabel.add(pnlAtasTabel, java.awt.BorderLayout.LINE_END);

        getContentPane().add(pnlTempatTabel, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BackKwnBTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BackKwnBTActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_BackKwnBTActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new ManageKaryawan().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BackKwnBT;
    private javax.swing.JTable DataTbl;
    private javax.swing.JButton TambahKwnBT;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel pnlAtasTabel;
    private javax.swing.JPanel pnlHeader;
    private javax.swing.JPanel pnlTempatTabel;
    // End of variables declaration//GEN-END:variables
}
