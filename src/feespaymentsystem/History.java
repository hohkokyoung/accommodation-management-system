/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package feespaymentsystem;

import static feespaymentsystem.DatabaseConnection.closeErrorDetails;
import static feespaymentsystem.DatabaseConnection.closeErrorDetection;
import static feespaymentsystem.DatabaseConnection.conn;
import static feespaymentsystem.DatabaseConnection.errorDetails;
import static feespaymentsystem.DatabaseConnection.errorDetection;
import static feespaymentsystem.Login.userID;
import static feespaymentsystem.OutstandingFees.outstandingFees;
import static java.awt.Color.green;
import static java.awt.Color.red;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author user
 */
public class History extends javax.swing.JFrame {
public static String search, lastDate;
public static PreparedStatement paymentsPST, selectPST;
public static ResultSet paymentsResult, selectResult;

DefaultTableModel historyTable;

    /**
     * Creates new form History
     */
    public History() {
        initComponents();
        
        ShowPayments();
        CenterPaymentsData();
        
        OutstandingFees outstandingFeesForm = new OutstandingFees();
        outstandingFeesForm.ResidentOutstandingFees(userID);
        
        RetrieveOutstandingFees();
    }
    
    private void SelectData() {
        
        //Take the static method from DatabaseConnection to establish database connection.
        DatabaseConnection.DBConnection();
        
        //Catch the database error.
        if (errorDetection == true) {
            Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, errorDetails);
            System.out.println("Error: " + errorDetails);
        } else {
            
            try {
            
             //SQL Statement to be executed. (Search Purposes)
             String selectSQL = "SELECT * "
                            + "FROM Payments "
                            + "WHERE User_ID = ? ";
             selectPST = conn.prepareStatement(selectSQL);
             selectPST.setString(1, userID);
             selectResult = selectPST.executeQuery();
             
             search = HistorySearchTxt.getText();
             
             SearchFilter(search);
             
             //Catch SQL execution error.
        } catch (SQLException ex) {
            System.out.println(ex);
        } finally {
            
            //Close the connection.
            DatabaseConnection.CloseDBConnection();
            
            if (closeErrorDetection == true) {
                Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, errorDetails);
                System.out.println("Error: " + closeErrorDetails);
            }
        }
            
        }
        
    }
    
    private void SearchFilter(String sqlQuery) {
        
        historyTable = (DefaultTableModel)HistoryTab.getModel();
        
        TableRowSorter<DefaultTableModel> rowSorter = new TableRowSorter<>(historyTable);
        HistoryTab.setRowSorter(rowSorter);
        
        rowSorter.setRowFilter(RowFilter.regexFilter(sqlQuery));
        
    }

    private ArrayList<PaymentsList> StorePaymentList(){
        
        //Create an array list.
        ArrayList<PaymentsList> paymentList = new ArrayList<>();
    
        //Take the static method from DatabaseConnection to establish database connection.
        DatabaseConnection.DBConnection();
        
        //Catch the database error.
        if (errorDetection == true) {
            Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, errorDetails);
            System.out.println("Error: " + errorDetails);
        } else {
            
            try {
            
            //SQL to select payment's details.
            String paymentsSQL = "SELECT Payment_ID, Payment_Date, Payment_Amount "
                                + "FROM Payments "
                                + "WHERE User_ID = ?";
            paymentsPST = conn.prepareStatement(paymentsSQL);
            paymentsPST.setString(1, userID);
            paymentsResult = paymentsPST.executeQuery();
            
            //Check if a row of payment's data is found.
            while (paymentsResult.next()) {
                
                //Store the data retrieved into each set method.
                PaymentsList paymentsList = new PaymentsList();  
                paymentsList.SetPaymentID(paymentsResult.getString("Payment_ID"));
                paymentsList.SetPaymentDate(paymentsResult.getString("Payment_Date"));
                paymentsList.SetPaymentAmount(paymentsResult.getString("Payment_Amount"));
                
                //Store the entire data of a payment as one data.
                paymentList.add(paymentsList);
            }
            
           //Catch SQL execution error. 
            } catch (SQLException ex) {
            System.out.println("Error: " + ex);
            }
            
        }
        
        //Close the method.
        return paymentList;
    }
    
    private void ShowPayments() {
        
        //Assumptions: Access the list in the StorePaymentList() method.
        ArrayList<PaymentsList> list = StorePaymentList();
        
        //Define a table model.
        DefaultTableModel paymentsTable = (DefaultTableModel)HistoryTab.getModel();
        
        //Define the columns of the table.
        Object[] paymentsTableRow = new Object[3];
        
        //Loop all the payment's data.
        //Loop all the payments as well.
        for (int counter=0;counter<list.size();counter++){
            paymentsTableRow[0]=list.get(counter).GetPaymentID();
            paymentsTableRow[1]=list.get(counter).GetPaymentDate();
            paymentsTableRow[2]=list.get(counter).GetPaymentAmount();
            
            //Add the payment's data as a row in the table.
            paymentsTable.addRow(paymentsTableRow);

        }   
        
    }
    
    private void CenterPaymentsData() {
        
        //Center the data in the table.
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        HistoryTab.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        HistoryTab.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
        HistoryTab.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
        
    }
    
    private void RetrieveOutstandingFees() {
        if (outstandingFees > 0) {
            
        //Set the font color to red.
        OutstandingFeesMoneyLab.setForeground(red);
        
        //Set the results into a text field.
        OutstandingFeesMoneyLab.setText(String.valueOf(outstandingFees) + ".00");
        } else {
            
        //Set the font color to green;
        OutstandingFeesMoneyLab.setForeground(green);
            
        //Set the results into a text field.
        OutstandingFeesMoneyLab.setText(String.valueOf(outstandingFees) + ".00");
        }
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        HistorySidePnl = new javax.swing.JPanel();
        HistoryLogoLab = new javax.swing.JLabel();
        LogOutBtn = new javax.swing.JButton();
        BackBtn = new javax.swing.JButton();
        HistoryPnl = new javax.swing.JPanel();
        ExitBtn = new javax.swing.JButton();
        MinimizeBtn = new javax.swing.JButton();
        HistorySearchSep = new javax.swing.JSeparator();
        HistorySearchTxt = new javax.swing.JTextField();
        OutstandingFeesLab = new javax.swing.JLabel();
        OutstandingFeesSep = new javax.swing.JSeparator();
        OutstandingFeesRMLab = new javax.swing.JLabel();
        OutstandingFeesMoneyLab = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        HistoryTab = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        HistorySidePnl.setBackground(new java.awt.Color(0, 0, 0));
        HistorySidePnl.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        HistoryLogoLab.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/icons8-money-96.png"))); // NOI18N
        HistorySidePnl.add(HistoryLogoLab, new org.netbeans.lib.awtextra.AbsoluteConstraints(62, 281, -1, -1));

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
        HistorySidePnl.add(LogOutBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 592, 220, 78));

        BackBtn.setBackground(new java.awt.Color(0, 0, 0));
        BackBtn.setForeground(new java.awt.Color(255, 255, 255));
        BackBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/icons8-left-64.png"))); // NOI18N
        BackBtn.setBorder(null);
        BackBtn.setFocusPainted(false);
        BackBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BackBtnActionPerformed(evt);
            }
        });
        HistorySidePnl.add(BackBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 220, 73));

        getContentPane().add(HistorySidePnl, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 220, 670));

        HistoryPnl.setBackground(new java.awt.Color(255, 255, 255));
        HistoryPnl.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

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
        HistoryPnl.add(ExitBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 0, 90, 40));

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
        HistoryPnl.add(MinimizeBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 0, 90, 40));

        HistorySearchSep.setBackground(new java.awt.Color(0, 0, 0));
        HistorySearchSep.setForeground(new java.awt.Color(0, 0, 0));
        HistoryPnl.add(HistorySearchSep, new org.netbeans.lib.awtextra.AbsoluteConstraints(45, 80, 360, -1));

        HistorySearchTxt.setFont(new java.awt.Font("Gill Sans MT", 0, 24)); // NOI18N
        HistorySearchTxt.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        HistorySearchTxt.setText("Search");
        HistorySearchTxt.setBorder(null);
        HistorySearchTxt.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                HistorySearchTxtFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                HistorySearchTxtFocusLost(evt);
            }
        });
        HistorySearchTxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                HistorySearchTxtActionPerformed(evt);
            }
        });
        HistorySearchTxt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                HistorySearchTxtKeyReleased(evt);
            }
        });
        HistoryPnl.add(HistorySearchTxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 40, 220, 30));

        OutstandingFeesLab.setFont(new java.awt.Font("Gill Sans MT", 0, 24)); // NOI18N
        OutstandingFeesLab.setText("Outstanding Fees:");
        HistoryPnl.add(OutstandingFeesLab, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 560, -1, -1));

        OutstandingFeesSep.setBackground(new java.awt.Color(0, 0, 0));
        OutstandingFeesSep.setForeground(new java.awt.Color(0, 0, 0));
        HistoryPnl.add(OutstandingFeesSep, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 590, 240, -1));

        OutstandingFeesRMLab.setFont(new java.awt.Font("Gill Sans MT", 0, 24)); // NOI18N
        OutstandingFeesRMLab.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        OutstandingFeesRMLab.setText("RM");
        HistoryPnl.add(OutstandingFeesRMLab, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 560, 50, 30));

        OutstandingFeesMoneyLab.setFont(new java.awt.Font("Gill Sans MT", 0, 24)); // NOI18N
        OutstandingFeesMoneyLab.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        OutstandingFeesMoneyLab.setText("0.00");
        HistoryPnl.add(OutstandingFeesMoneyLab, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 560, 220, 30));

        HistoryTab.setFont(new java.awt.Font("Gill Sans MT", 0, 20)); // NOI18N
        HistoryTab.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Payment ID", "Payment Date", "Payment Amount"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        HistoryTab.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        HistoryTab.setFocusable(false);
        HistoryTab.setGridColor(new java.awt.Color(255, 255, 255));
        HistoryTab.setRowHeight(50);
        HistoryTab.setSelectionBackground(new java.awt.Color(204, 204, 204));
        HistoryTab.setSelectionForeground(new java.awt.Color(0, 0, 0));
        HistoryTab.getTableHeader().setReorderingAllowed(false);
        HistoryTab.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                HistoryTabMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(HistoryTab);

        HistoryPnl.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 140, 710, 360));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/icons8-money-64 (5).png"))); // NOI18N
        HistoryPnl.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 10, -1, -1));

        getContentPane().add(HistoryPnl, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 0, 850, 670));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void LogOutBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LogOutBtnActionPerformed
        // TODO add your handling code here:

        Login loginform = new Login();
        loginform.setVisible(true);
        loginform.setLocationRelativeTo(null);

        this.setVisible(false);
    }//GEN-LAST:event_LogOutBtnActionPerformed

    private void BackBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BackBtnActionPerformed
        // TODO add your handling code here:

        Resident residentform = new Resident();
        residentform.setVisible(true);
        residentform.setLocationRelativeTo(null);
        this.setVisible(false);
    }//GEN-LAST:event_BackBtnActionPerformed

    private void ExitBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ExitBtnActionPerformed
        // TODO add your handling code here:

        System.exit(0);
    }//GEN-LAST:event_ExitBtnActionPerformed

    private void MinimizeBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MinimizeBtnActionPerformed
        // TODO add your handling code here:

        this.setState(History.ICONIFIED);
    }//GEN-LAST:event_MinimizeBtnActionPerformed

    private void HistorySearchTxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_HistorySearchTxtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_HistorySearchTxtActionPerformed

    private void HistorySearchTxtFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_HistorySearchTxtFocusGained
        // TODO add your handling code here:
        
        search = HistorySearchTxt.getText();
        
        if (search.equals("Search")) {
            HistorySearchTxt.setText("");
        } else {
            
        }
    }//GEN-LAST:event_HistorySearchTxtFocusGained

    private void HistorySearchTxtFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_HistorySearchTxtFocusLost
        // TODO add your handling code here:
        
        search = HistorySearchTxt.getText();
        
        if (search.equals("")) {
            HistorySearchTxt.setText("Search");
        } else {
            
        }
    }//GEN-LAST:event_HistorySearchTxtFocusLost

    private void HistoryTabMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_HistoryTabMouseClicked
        // TODO add your handling code here:

    }//GEN-LAST:event_HistoryTabMouseClicked

    private void HistorySearchTxtKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_HistorySearchTxtKeyReleased
        // TODO add your handling code here:
        
        SelectData();
        
    }//GEN-LAST:event_HistorySearchTxtKeyReleased

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
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(History.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
    //</editor-fold>
    
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new History().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BackBtn;
    private javax.swing.JButton ExitBtn;
    private javax.swing.JLabel HistoryLogoLab;
    private javax.swing.JPanel HistoryPnl;
    private javax.swing.JSeparator HistorySearchSep;
    private javax.swing.JTextField HistorySearchTxt;
    private javax.swing.JPanel HistorySidePnl;
    private javax.swing.JTable HistoryTab;
    private javax.swing.JButton LogOutBtn;
    private javax.swing.JButton MinimizeBtn;
    private javax.swing.JLabel OutstandingFeesLab;
    private javax.swing.JLabel OutstandingFeesMoneyLab;
    private javax.swing.JLabel OutstandingFeesRMLab;
    private javax.swing.JSeparator OutstandingFeesSep;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
