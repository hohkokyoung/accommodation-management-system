/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package feespaymentsystem;

import static feespaymentsystem.DatabaseConnection.closeErrorDetails;
import static feespaymentsystem.DatabaseConnection.closeErrorDetection;
import static feespaymentsystem.DatabaseConnection.errorDetails;
import static feespaymentsystem.DisplayReceipt.receiptResult;
import static feespaymentsystem.Payment.amount;
import static feespaymentsystem.Payment.newOutstandingFeesFormat;
import static feespaymentsystem.Payment.newPaymentChangeFormat;
import static feespaymentsystem.Register.userRegistrationCheck;
import static feespaymentsystem.Register.userRegistrationInitiation;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingUtilities;

/**
 *
 * @author user
 */
public class Receipt extends javax.swing.JFrame {
    private String paymentID, unit, userID, paymentDate;
    
    /**
     * Creates new form Receipt
     */
    public Receipt() {
        initComponents();
        
        ResidentReceipt();
        
        //Done button as default button.
        SwingUtilities.getRootPane(DoneBtn).setDefaultButton(DoneBtn);
        
    }
    
    public void PrintReceipt(){
        
        PrinterJob printJob = PrinterJob.getPrinterJob();
        
        printJob.setPrintable(new Printable(){
           public int print(Graphics graphic, PageFormat pageFormat, int pageNum){
               
               if(pageNum>0){
                   return Printable.NO_SUCH_PAGE;
               }
               
               Graphics2D graphic2D = (Graphics2D)graphic;
               graphic2D.translate(pageFormat.getImageableX(), pageFormat.getImageableY());
               graphic2D.scale(0.50,0.50);
               
               DoneBtn.setVisible(false);
               PrintBtn.setVisible(false);

               ReceiptPnl.paint(graphic2D);
               return Printable.PAGE_EXISTS;
               
           } 
        });
        
        Boolean printVerification = printJob.printDialog();
        if(printVerification){
            try{
                printJob.print();
                
                if (userRegistrationInitiation == true){
                    
                    ResidentInformation residentInfoForm = new ResidentInformation();
                    residentInfoForm.setVisible(true);
                    residentInfoForm.setLocationRelativeTo(null);

                    this.setVisible(false);
                } else {
                    
                    Payment paymentForm = new Payment();
                    paymentForm.setVisible(true);
                    paymentForm.setLocationRelativeTo(null);

                    this.setVisible(false);
                    
                } 
                
            } catch (PrinterException ex){
                System.out.println(ex);
                
            }
        }
        
    }

    //Display all the details of the resident's receipt.
    private void ResidentReceipt(){
        
        //Take the static method from DisplayReceipt.
        DisplayReceipt.LatestPayment();
        
        try {
            
            //Execute the receipt query.
            while (receiptResult.next()){
                
                paymentID = receiptResult.getString("Payment_ID");
                unit = receiptResult.getString("Room_ID");
                userID = receiptResult.getString("User_ID");
                paymentDate = receiptResult.getString("Payment_Date");
                
            }
            
            //Set all the results found into text fields.
            PaymentIDDetailsLab.setText(paymentID);
            UnitDetailsLab.setText(unit);
            IDNumberDetailsLab.setText(userID);
            DateDetails.setText(paymentDate);
            FeesMoneyLab.setText(newOutstandingFeesFormat);
            AmountMoneyLab.setText(amount.replace("RM", ""));
            ChangeMoneyLab.setText(String.valueOf(newPaymentChangeFormat));
            
            
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
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        ReceiptSidePnl = new javax.swing.JPanel();
        ReceiptLogoLab = new javax.swing.JLabel();
        ReceiptPnl = new javax.swing.JPanel();
        UnitSep = new javax.swing.JSeparator();
        UnitLab = new javax.swing.JLabel();
        IDNumberSep = new javax.swing.JSeparator();
        IDNumberLab = new javax.swing.JLabel();
        FeesLab = new javax.swing.JLabel();
        AmountLab = new javax.swing.JLabel();
        ChangeLab = new javax.swing.JLabel();
        FeesMoneyLab = new javax.swing.JLabel();
        AmountMoneyLab = new javax.swing.JLabel();
        ChangeMoneyLab = new javax.swing.JLabel();
        UnitDetailsLab = new javax.swing.JLabel();
        IDNumberDetailsLab = new javax.swing.JLabel();
        DoneBtn = new javax.swing.JButton();
        FeesSep = new javax.swing.JSeparator();
        FeesRMLab = new javax.swing.JLabel();
        AmountSep = new javax.swing.JSeparator();
        AmountRMLab = new javax.swing.JLabel();
        ChangeSep = new javax.swing.JSeparator();
        ChangeRMLab = new javax.swing.JLabel();
        DateLab = new javax.swing.JLabel();
        DateSep = new javax.swing.JSeparator();
        PaymentIDDetailsLab = new javax.swing.JLabel();
        PaymentIDLab = new javax.swing.JLabel();
        DateDetails = new javax.swing.JLabel();
        PaymentIDSep = new javax.swing.JSeparator();
        PrintBtn = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        ReceiptSidePnl.setBackground(new java.awt.Color(0, 0, 0));
        ReceiptSidePnl.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        ReceiptLogoLab.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/icons8-purchase-order-96.png"))); // NOI18N
        ReceiptSidePnl.add(ReceiptLogoLab, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 270, -1, -1));

        getContentPane().add(ReceiptSidePnl, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 220, 650));

        ReceiptPnl.setBackground(new java.awt.Color(255, 255, 255));
        ReceiptPnl.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        UnitSep.setBackground(new java.awt.Color(0, 0, 0));
        UnitSep.setForeground(new java.awt.Color(0, 0, 0));
        ReceiptPnl.add(UnitSep, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 160, 240, -1));

        UnitLab.setFont(new java.awt.Font("Gill Sans MT", 0, 24)); // NOI18N
        UnitLab.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        UnitLab.setText("Unit:");
        ReceiptPnl.add(UnitLab, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 130, -1, -1));

        IDNumberSep.setBackground(new java.awt.Color(0, 0, 0));
        IDNumberSep.setForeground(new java.awt.Color(0, 0, 0));
        ReceiptPnl.add(IDNumberSep, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 230, 240, -1));

        IDNumberLab.setFont(new java.awt.Font("Gill Sans MT", 0, 24)); // NOI18N
        IDNumberLab.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        IDNumberLab.setText("ID Number:");
        ReceiptPnl.add(IDNumberLab, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 200, -1, -1));

        FeesLab.setFont(new java.awt.Font("Gill Sans MT", 0, 24)); // NOI18N
        FeesLab.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        FeesLab.setText("Fees:");
        ReceiptPnl.add(FeesLab, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 340, -1, -1));

        AmountLab.setFont(new java.awt.Font("Gill Sans MT", 0, 24)); // NOI18N
        AmountLab.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        AmountLab.setText("Amount:");
        ReceiptPnl.add(AmountLab, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 410, -1, -1));

        ChangeLab.setFont(new java.awt.Font("Gill Sans MT", 0, 24)); // NOI18N
        ChangeLab.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ChangeLab.setText("Change:");
        ReceiptPnl.add(ChangeLab, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 480, -1, -1));

        FeesMoneyLab.setFont(new java.awt.Font("Gill Sans MT", 0, 24)); // NOI18N
        FeesMoneyLab.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        FeesMoneyLab.setText("0.00");
        ReceiptPnl.add(FeesMoneyLab, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 340, 220, 30));

        AmountMoneyLab.setFont(new java.awt.Font("Gill Sans MT", 0, 24)); // NOI18N
        AmountMoneyLab.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        AmountMoneyLab.setText("0.00");
        ReceiptPnl.add(AmountMoneyLab, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 410, 220, 30));

        ChangeMoneyLab.setFont(new java.awt.Font("Gill Sans MT", 0, 24)); // NOI18N
        ChangeMoneyLab.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ChangeMoneyLab.setText("0.00");
        ReceiptPnl.add(ChangeMoneyLab, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 480, 220, 30));

        UnitDetailsLab.setFont(new java.awt.Font("Gill Sans MT", 0, 24)); // NOI18N
        UnitDetailsLab.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ReceiptPnl.add(UnitDetailsLab, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 130, 220, 30));

        IDNumberDetailsLab.setFont(new java.awt.Font("Gill Sans MT", 0, 24)); // NOI18N
        IDNumberDetailsLab.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        IDNumberDetailsLab.setText("ID");
        ReceiptPnl.add(IDNumberDetailsLab, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 200, 220, 30));

        DoneBtn.setBackground(new java.awt.Color(0, 0, 0));
        DoneBtn.setFont(new java.awt.Font("Gill Sans MT", 0, 24)); // NOI18N
        DoneBtn.setForeground(new java.awt.Color(255, 255, 255));
        DoneBtn.setText("Done");
        DoneBtn.setBorder(null);
        DoneBtn.setFocusPainted(false);
        DoneBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DoneBtnActionPerformed(evt);
            }
        });
        ReceiptPnl.add(DoneBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 560, 180, 50));

        FeesSep.setBackground(new java.awt.Color(0, 0, 0));
        FeesSep.setForeground(new java.awt.Color(0, 0, 0));
        ReceiptPnl.add(FeesSep, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 370, 240, -1));

        FeesRMLab.setFont(new java.awt.Font("Gill Sans MT", 0, 24)); // NOI18N
        FeesRMLab.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        FeesRMLab.setText("RM");
        ReceiptPnl.add(FeesRMLab, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 340, 50, 30));

        AmountSep.setBackground(new java.awt.Color(0, 0, 0));
        AmountSep.setForeground(new java.awt.Color(0, 0, 0));
        ReceiptPnl.add(AmountSep, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 440, 240, -1));

        AmountRMLab.setFont(new java.awt.Font("Gill Sans MT", 0, 24)); // NOI18N
        AmountRMLab.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        AmountRMLab.setText("RM");
        ReceiptPnl.add(AmountRMLab, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 410, 50, 30));

        ChangeSep.setBackground(new java.awt.Color(0, 0, 0));
        ChangeSep.setForeground(new java.awt.Color(0, 0, 0));
        ReceiptPnl.add(ChangeSep, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 510, 240, -1));

        ChangeRMLab.setFont(new java.awt.Font("Gill Sans MT", 0, 24)); // NOI18N
        ChangeRMLab.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ChangeRMLab.setText("RM");
        ReceiptPnl.add(ChangeRMLab, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 480, 50, 30));

        DateLab.setFont(new java.awt.Font("Gill Sans MT", 0, 24)); // NOI18N
        DateLab.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        DateLab.setText("Date:");
        ReceiptPnl.add(DateLab, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 270, -1, -1));

        DateSep.setBackground(new java.awt.Color(0, 0, 0));
        DateSep.setForeground(new java.awt.Color(0, 0, 0));
        ReceiptPnl.add(DateSep, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 300, 240, -1));

        PaymentIDDetailsLab.setFont(new java.awt.Font("Gill Sans MT", 0, 24)); // NOI18N
        PaymentIDDetailsLab.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ReceiptPnl.add(PaymentIDDetailsLab, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 60, 220, 30));

        PaymentIDLab.setFont(new java.awt.Font("Gill Sans MT", 0, 24)); // NOI18N
        PaymentIDLab.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        PaymentIDLab.setText("Payment ID:");
        ReceiptPnl.add(PaymentIDLab, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 60, -1, -1));

        DateDetails.setFont(new java.awt.Font("Gill Sans MT", 0, 24)); // NOI18N
        DateDetails.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ReceiptPnl.add(DateDetails, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 270, 220, 30));

        PaymentIDSep.setBackground(new java.awt.Color(0, 0, 0));
        PaymentIDSep.setForeground(new java.awt.Color(0, 0, 0));
        ReceiptPnl.add(PaymentIDSep, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 90, 240, -1));

        PrintBtn.setBackground(new java.awt.Color(0, 0, 0));
        PrintBtn.setFont(new java.awt.Font("Gill Sans MT", 0, 24)); // NOI18N
        PrintBtn.setForeground(new java.awt.Color(255, 255, 255));
        PrintBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/icons8-print-64.png"))); // NOI18N
        PrintBtn.setBorder(null);
        PrintBtn.setFocusPainted(false);
        PrintBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PrintBtnActionPerformed(evt);
            }
        });
        ReceiptPnl.add(PrintBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 20, 80, 70));

        getContentPane().add(ReceiptPnl, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 0, 640, 650));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void DoneBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DoneBtnActionPerformed
        // TODO add your handling code here:

        if (userRegistrationCheck == true) {
                                
            ResidentInformation residentInformationForm = new ResidentInformation();
            residentInformationForm.setVisible(true);
            residentInformationForm.setLocationRelativeTo(null);
            this.setVisible(false);
                                   
            userRegistrationCheck = false;

        } else {

            Payment paymentform = new Payment();
            paymentform.setVisible(true);
            paymentform.setLocationRelativeTo(null);

            this.setVisible(false);
            
        }

    }//GEN-LAST:event_DoneBtnActionPerformed

    private void PrintBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PrintBtnActionPerformed
        // TODO add your handling code here:

        PrintReceipt();

    }//GEN-LAST:event_PrintBtnActionPerformed

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
            java.util.logging.Logger.getLogger(Receipt.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new Receipt().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel AmountLab;
    private javax.swing.JLabel AmountMoneyLab;
    private javax.swing.JLabel AmountRMLab;
    private javax.swing.JSeparator AmountSep;
    private javax.swing.JLabel ChangeLab;
    private javax.swing.JLabel ChangeMoneyLab;
    private javax.swing.JLabel ChangeRMLab;
    private javax.swing.JSeparator ChangeSep;
    private javax.swing.JLabel DateDetails;
    private javax.swing.JLabel DateLab;
    private javax.swing.JSeparator DateSep;
    private javax.swing.JButton DoneBtn;
    private javax.swing.JLabel FeesLab;
    private javax.swing.JLabel FeesMoneyLab;
    private javax.swing.JLabel FeesRMLab;
    private javax.swing.JSeparator FeesSep;
    private javax.swing.JLabel IDNumberDetailsLab;
    private javax.swing.JLabel IDNumberLab;
    private javax.swing.JSeparator IDNumberSep;
    private javax.swing.JLabel PaymentIDDetailsLab;
    private javax.swing.JLabel PaymentIDLab;
    private javax.swing.JSeparator PaymentIDSep;
    private javax.swing.JButton PrintBtn;
    private javax.swing.JLabel ReceiptLogoLab;
    private javax.swing.JPanel ReceiptPnl;
    private javax.swing.JPanel ReceiptSidePnl;
    private javax.swing.JLabel UnitDetailsLab;
    private javax.swing.JLabel UnitLab;
    private javax.swing.JSeparator UnitSep;
    // End of variables declaration//GEN-END:variables
}
