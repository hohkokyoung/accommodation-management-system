/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package feespaymentsystem;

/**
 *
 * @author user
 */
public class Resident extends javax.swing.JFrame {

    /**
     * Creates new form Resident
     */
    public Resident() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        ResidentSidePnl = new javax.swing.JPanel();
        ResidentLogoLab = new javax.swing.JLabel();
        LogOutBtn = new javax.swing.JButton();
        ResidentPnl = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        ModifyBtn = new javax.swing.JButton();
        ExitBtn = new javax.swing.JButton();
        MinimizeBtn = new javax.swing.JButton();
        HistoryBtn = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        ResidentSidePnl.setBackground(new java.awt.Color(0, 0, 0));
        ResidentSidePnl.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        ResidentLogoLab.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/icons8-user-96 (4).png"))); // NOI18N
        ResidentSidePnl.add(ResidentLogoLab, new org.netbeans.lib.awtextra.AbsoluteConstraints(61, 284, -1, -1));

        LogOutBtn.setBackground(new java.awt.Color(0, 0, 0));
        LogOutBtn.setFont(new java.awt.Font("Gill Sans MT", 0, 24)); // NOI18N
        LogOutBtn.setForeground(new java.awt.Color(255, 255, 255));
        LogOutBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/icons8-exit-64.png"))); // NOI18N
        LogOutBtn.setText("Log Out");
        LogOutBtn.setBorder(null);
        LogOutBtn.setFocusPainted(false);
        LogOutBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LogOutBtnActionPerformed(evt);
            }
        });
        ResidentSidePnl.add(LogOutBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 592, 220, 78));

        getContentPane().add(ResidentSidePnl, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 220, 670));

        ResidentPnl.setBackground(new java.awt.Color(255, 255, 255));
        ResidentPnl.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        ResidentPnl.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 10, -1, -1));
        ResidentPnl.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 10, 30, -1));
        ResidentPnl.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 260, -1, -1));
        ResidentPnl.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(277, 360, -1, -1));

        ModifyBtn.setBackground(new java.awt.Color(0, 0, 0));
        ModifyBtn.setFont(new java.awt.Font("Gill Sans MT", 0, 36)); // NOI18N
        ModifyBtn.setForeground(new java.awt.Color(255, 255, 255));
        ModifyBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/icons8-user-96 (2).png"))); // NOI18N
        ModifyBtn.setText("Modify");
        ModifyBtn.setToolTipText("");
        ModifyBtn.setBorder(null);
        ModifyBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        ModifyBtn.setFocusPainted(false);
        ModifyBtn.setName(""); // NOI18N
        ModifyBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ModifyBtnActionPerformed(evt);
            }
        });
        ResidentPnl.add(ModifyBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 100, 320, 480));

        ExitBtn.setBackground(new java.awt.Color(0, 0, 0));
        ExitBtn.setForeground(new java.awt.Color(255, 255, 255));
        ExitBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/icons8-delete-32 (3).png"))); // NOI18N
        ExitBtn.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        ExitBtn.setFocusPainted(false);
        ExitBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ExitBtnActionPerformed(evt);
            }
        });
        ResidentPnl.add(ExitBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 0, 90, 40));

        MinimizeBtn.setBackground(new java.awt.Color(0, 0, 0));
        MinimizeBtn.setForeground(new java.awt.Color(255, 255, 255));
        MinimizeBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/icons8-horizontal-line-32 (2).png"))); // NOI18N
        MinimizeBtn.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        MinimizeBtn.setFocusPainted(false);
        MinimizeBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MinimizeBtnActionPerformed(evt);
            }
        });
        ResidentPnl.add(MinimizeBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 0, 90, 40));

        HistoryBtn.setBackground(new java.awt.Color(0, 0, 0));
        HistoryBtn.setFont(new java.awt.Font("Gill Sans MT", 0, 36)); // NOI18N
        HistoryBtn.setForeground(new java.awt.Color(255, 255, 255));
        HistoryBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/icons8-money-96.png"))); // NOI18N
        HistoryBtn.setText("History");
        HistoryBtn.setToolTipText("");
        HistoryBtn.setBorder(null);
        HistoryBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        HistoryBtn.setFocusPainted(false);
        HistoryBtn.setName(""); // NOI18N
        HistoryBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                HistoryBtnActionPerformed(evt);
            }
        });
        ResidentPnl.add(HistoryBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 100, 320, 480));

        getContentPane().add(ResidentPnl, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 0, 850, 670));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void LogOutBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LogOutBtnActionPerformed
        // TODO add your handling code here:

        Login loginform = new Login();
        loginform.setVisible(true);
        loginform.setLocationRelativeTo(null);

        this.setVisible(false);
    }//GEN-LAST:event_LogOutBtnActionPerformed

    private void ModifyBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ModifyBtnActionPerformed
        // TODO add your handling code here:

        Modify modifyform = new Modify();
        modifyform.setVisible(true);
        modifyform.setLocationRelativeTo(null);
        this.setVisible(false);

    }//GEN-LAST:event_ModifyBtnActionPerformed

    private void ExitBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ExitBtnActionPerformed
        // TODO add your handling code here:

        System.exit(0);
    }//GEN-LAST:event_ExitBtnActionPerformed

    private void MinimizeBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MinimizeBtnActionPerformed
        // TODO add your handling code here:

        this.setState(Resident.ICONIFIED);
    }//GEN-LAST:event_MinimizeBtnActionPerformed

    private void HistoryBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_HistoryBtnActionPerformed
        // TODO add your handling code here:

        History historyForm = new History();
        historyForm.setVisible(true);
        historyForm.setLocationRelativeTo(null);

        this.setVisible(false);

    }//GEN-LAST:event_HistoryBtnActionPerformed

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
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Resident.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Resident.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Resident.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Resident.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new Resident().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton ExitBtn;
    private javax.swing.JButton HistoryBtn;
    private javax.swing.JButton LogOutBtn;
    private javax.swing.JButton MinimizeBtn;
    private javax.swing.JButton ModifyBtn;
    private javax.swing.JLabel ResidentLogoLab;
    private javax.swing.JPanel ResidentPnl;
    private javax.swing.JPanel ResidentSidePnl;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    // End of variables declaration//GEN-END:variables
}
