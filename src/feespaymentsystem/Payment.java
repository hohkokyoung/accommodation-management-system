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
import static feespaymentsystem.OutstandingFees.outstandingFees;
import static feespaymentsystem.Register.finalNewID;
import static feespaymentsystem.Register.userRegistrationCheck;
import static java.awt.Color.green;
import static java.awt.Color.red;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingUtilities;

/**
 *
 * @author user
 */
public class Payment extends javax.swing.JFrame {
public static String amount, outstandingFeesField, paymentID, validAmount;
public static String selectedBlock, selectedFloor, unit, previousID, selectedUnit, 
                roomUserID, emptyRoomID, newOutstandingFees, newOutstandingFeesFormat, newAmount,
                newPaymentChange, newPaymentChangeFormat, availableUnit;
public PreparedStatement unitPST, previousIDPST, paymentPST, emptyPaymentPST, 
                payRoomPST, datePaymentPST, availableRoomPST; 
public ResultSet unitResult, previousIDResult, paymentResult, emptyPaymentResult, 
                payRoomResult, datePaymentResult, availableRoomResult;
public String eliminatePaymentID, fakePaymentID, newPaymentID, lastPaymentDate, currentDateFormat;
public static int tempPaymentID, paymentChange;
public LocalDate currentDate;

    /**
     * Creates new form Payment
     */
    public Payment() {
        initComponents();
        
        ResidentPayment();
        
        RetrieveOutstandingFees();
        
        OccupiedUnit();
        
        //Pay button as default button.
        SwingUtilities.getRootPane(PayBtn).setDefaultButton(PayBtn);
    }
    
    private void ResidentPayment() {
        //Take the static method from DatabaseConnection to establish database connection.
        DatabaseConnection.DBConnection();
        
        //Catch the database error.
        if (errorDetection == true) {
            Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, errorDetails);
            ErrorInputLab.setText("Database connection issue.");
            System.out.println("Error: " + errorDetails);
        } else {
            
            selectedUnit = (String) UnitCbo.getSelectedItem();
            
            //Validation purposes.
            validAmount = "^(\\d{1,6}.00)$";
            
            //Store the values of the text fields into variables.
            amount = AmountTxt.getText();
            
            //Check if the amount field is empty.
            if (amount.trim().isEmpty()) {
            ErrorInputLab.setText("Amount is empty.");
            
            //Check if the amount field is invalid.
            } else if (!amount.matches(validAmount)) {
            ErrorInputLab.setText("Amount is invalid.");
            } else {
        
            //Clear Error field if no error.
            ErrorInputLab.setText("");
            
            try {
            
                String paymentSQL = "SELECT User_ID FROM Rooms Where Room_ID = ? ";
                paymentPST = conn.prepareStatement(paymentSQL);
                paymentPST.setString(1, selectedUnit);
                paymentResult = paymentPST.executeQuery();

                if (userRegistrationCheck == true){
                    
                    UserRegistrationList userRegistrationListForm = new UserRegistrationList();
                    userRegistrationListForm.SetUserID(finalNewID);
                    roomUserID = userRegistrationListForm.GetUserID();
                    
                } else {
                
                    while (paymentResult.next()){
                        
                        roomUserID = paymentResult.getString("User_ID");
                        
                    }
                
                }
                
                OutstandingFees outstandingFeesForm = new OutstandingFees();
                outstandingFeesForm.ResidentOutstandingFees(roomUserID);
   
            //Catch SQL execution error.
            } catch (SQLException ex) {
            ErrorInputLab.setText("SQL execution error.");
            System.out.println(ex);
            }          
            }
        }
        
    }
    
    private void OccupiedUnit(){

        //Take the static method from DatabaseConnection to establish database connection.
        DatabaseConnection.DBConnection();
        
        //Store the values of the combo boxes into variables.
        selectedBlock = BlockCbo.getSelectedItem().toString();
        selectedFloor = FloorCbo.getSelectedItem().toString();
        
        //Catch the database error.
        if (errorDetection == true) {
            Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, errorDetails);
            ErrorInputLab.setText("Database connection issue.");
            System.out.println("Error: " + errorDetails);
        } else {
            
            try {
            //Remove all the combo box items whenever the staff chooses a new block or floor.
            UnitCbo.removeAllItems();

            //SQL Statement to be executed. (Occupied Rooms Purposes)
            String roomSQL = "SELECT Room_ID "
                            + "FROM Rooms "
                            + "WHERE Room_Block = ? AND Room_Floor = ? "
                            + "AND User_ID IS NOT NULL ";
            unitPST = conn.prepareStatement(roomSQL);
            unitPST.setString(1, selectedBlock);
            unitPST.setString(2, selectedFloor);
            unitResult = unitPST.executeQuery();
            
            String availableRoomSQL = "SELECT Room_ID "
                                + "FROM Rooms "
                                + "WHERE Room_Block = ? "
                                + "AND Room_Floor = ? "
                                + "AND User_ID IS NULL";
            availableRoomPST = conn.prepareStatement(availableRoomSQL);
            availableRoomPST.setString(1, selectedBlock);
            availableRoomPST.setString(2, selectedFloor);
            availableRoomResult = availableRoomPST.executeQuery();
            
            if (userRegistrationCheck == true){
                
                if (availableRoomResult.next()) {

                    do {            
                        
                        availableUnit = availableRoomResult.getString("Room_ID"); 

                        //Add the available unit into the combo box.
                        UnitCbo.addItem(availableUnit);
                        
                    
                    } while (availableRoomResult.next());
                    //Clear text.
                    ErrorInputLab.setText(""); 
                    
                } else {
                    //Set the font color to red.
                    FeesMoneyLab.setForeground(red);

                    //Set the results into a text field.
                    FeesMoneyLab.setText("0.00");
                    
                    //Display text where there are no occupied room.
                    ErrorInputLab.setText("Please select an available unit.");
                }
            } else {
            
            //Check if there are occupied rooms.
                if (unitResult.next()) {

                    do {            
                        
                        unit = unitResult.getString("Room_ID"); 

                        //Add the available unit into the combo box.
                        UnitCbo.addItem(unit);
                        
                    
                    } while (unitResult.next());
                    //Clear text.
                    ErrorInputLab.setText(""); 
                } else {
                    //Set the font color to red.
                    FeesMoneyLab.setForeground(red);

                    //Set the results into a text field.
                    FeesMoneyLab.setText("0.00");
                    
                    //Display text where there are no occupied room.
                    ErrorInputLab.setText("Please select an occupied unit.");
                }
            
            }
            
        //Catch SQL execution error.
        } catch (SQLException ex) {
            ErrorInputLab.setText("SQL execution error.");
            System.out.println(ex);
        } 
        }
        
    }
    
    private void RetrieveOutstandingFees() {

        if (outstandingFees > 0) {

            //Set the font color to red.
            FeesMoneyLab.setForeground(red);

            //Set the results into a text field.
            FeesMoneyLab.setText(String.valueOf(outstandingFees) + ".00");
            
        } else {

            //Set the font color to green;
            FeesMoneyLab.setForeground(green);

            //Set the results into a text field.
            FeesMoneyLab.setText(String.valueOf(outstandingFees) + ".00");
        
        }
                    
    }

    private void PayRoom() {
        
        //Take the static method from DatabaseConnection to establish database connection.
        DatabaseConnection.DBConnection();
        
        //Retrieve outstanding fees from text field.
        outstandingFeesField = FeesMoneyLab.getText();
        
        //Catch the database error.
        if (errorDetection == true) {
            Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, errorDetails);
            ErrorInputLab.setText("Database connection issue.");
            System.out.println("Error: " + errorDetails);
        } else {
            
            try {

            //SQL Statement to be executed. (Register Purposes)
            String emptyPaymentSQL = "SELECT * "
                                + "FROM Payments "
                                + "ORDER BY Payment_ID DESC";
            emptyPaymentPST = conn.prepareStatement(emptyPaymentSQL);
            emptyPaymentResult = emptyPaymentPST.executeQuery();
            
            //SQL Statement to be executed. (Date Purposes)
            String datePaymentSQL = "SELECT TOP(1) Payment_Date "
                                + "FROM Payments "
                                + "WHERE User_ID = ? "
                                + "ORDER BY Payment_Date DESC";
            datePaymentPST = conn.prepareStatement(datePaymentSQL);
            datePaymentPST.setString(1, roomUserID);
            datePaymentResult = datePaymentPST.executeQuery();
            
            if (datePaymentResult.next()){
                
                currentDate = LocalDate.now();
                
                //Format the date.
                DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                currentDateFormat = currentDate.format(dateFormat);
  
            } else {
                
                //Retrieve the current date.
                currentDate = LocalDate.now();
                
                //Format the date.
                DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                currentDateFormat = currentDate.format(dateFormat);
            }
            
            if (emptyPaymentResult.next()){
                
                //Increment the Payment ID by 1.
                paymentID = emptyPaymentResult.getString("Payment_ID");
                eliminatePaymentID = "P";
                fakePaymentID = paymentID.replaceAll(eliminatePaymentID,"");
                tempPaymentID = Integer.parseInt(fakePaymentID) + 1;
                newPaymentID = String.format("P" + "%07d", tempPaymentID);
                
            } else {
                
                //Set the Payment ID to P0000001 if no payment has ever made.
                newPaymentID = "P0000001";
            }
            
            //Check if there are any occupied unit.
            if (UnitCbo.getItemCount() == 0 && userRegistrationCheck == false){
                
                ErrorInputLab.setText("Please select an occupied unit.");
                
            //Check if the payment is needed.
            } else if("0.00".equals(outstandingFeesField) && userRegistrationCheck == false) {
                
                ErrorInputLab.setText("No payment needed for this unit.");
                
            } else if (UnitCbo.getItemCount() == 0 && userRegistrationCheck == true){
                
                ErrorInputLab.setText("Please select an available unit.");
                
            } else {     

                //Check if the amount inputted by the user is viable.
                if (!amount.matches(validAmount)){
                    
                    ErrorInputLab.setText("Amount is invalid.");
                
                //Check if the amount is lower than the outstanding fees.
                } else if (outstandingFees > Integer.parseInt(amount.replace(".00", ""))){
                    
                    ErrorInputLab.setText("Amount is not enough.");
                   
                } else {
                   
                paymentChange = Integer.parseInt(amount.replace(".00", "")) - outstandingFees;
                newPaymentChange = String.valueOf(paymentChange);
                newPaymentChangeFormat = newPaymentChange.replace(newPaymentChange, newPaymentChange + ".00");
                    
                newOutstandingFees = String.valueOf(outstandingFees);
                newOutstandingFeesFormat = newOutstandingFees.replace(newOutstandingFees, newOutstandingFees + ".00");
                
                newAmount = String.valueOf(outstandingFees);
                
                //Clear the text if everything went smoothly.
                ErrorInputLab.setText("");
                
                //Format the amount.
                if ("200.00".equals(amount)) {
                    
                    amount = "RM " + amount;      
                    newAmount = "RM " + newAmount + ".00";

                } else if (amount.contains(".00")) {
                    
                    amount = "RM " + amount;
                    newAmount = "RM " + newAmount + ".00";

                } else {
                    amount = "RM " + amount;
                    amount = amount.replace(amount, amount + ".00");
                    newAmount = "RM " + newAmount;
                    newAmount = newAmount.replace(newAmount, newAmount + ".00");

                }
                        
                //SQL Statement to be executed. (Occupied Rooms Purposes)
                String payRoomSQL = "INSERT INTO Payments "
                                    + "VALUES (?,?,?,?,?)";
                payRoomPST = conn.prepareStatement(payRoomSQL);
                payRoomPST.setString(1, newPaymentID);
                payRoomPST.setString(2, currentDateFormat);
                payRoomPST.setString(3, newAmount);
                payRoomPST.setString(4, roomUserID);
                payRoomPST.setString(5, selectedUnit);
                
                if (userRegistrationCheck == true){
                    
                    //Execute the insert query. (Users)
                    UserRegistration.ResidentRegistration();

                    //Execute the update query. (Rooms)
                    RoomRegistration.ResidentRoomRegistration();

                }

                //Execute the insert query. (Payments)
                payRoomPST.executeUpdate();
                
                //Direct user to the receipt form.
                Receipt receiptForm = new Receipt();
                receiptForm.setVisible(true);
                receiptForm.setLocationRelativeTo(null);
                this.setVisible(false);
                
                    }
                }
            
            //Catch SQL execution error.
            } catch (SQLException ex) {
                ErrorInputLab.setText("SQL execution error.");
                System.out.println(ex);
            } finally {

                //Close the connection.
                DatabaseConnection.CloseDBConnection();

                if (closeErrorDetection == true) {
                    Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, errorDetails);
                    ErrorInputLab.setText("Database connection cannot be closed.");
                    System.out.println("Error: " + closeErrorDetails);
                }

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

        PaymentSidePnl = new javax.swing.JPanel();
        PaymentLogoLab = new javax.swing.JLabel();
        LogOutBtn = new javax.swing.JButton();
        BackBtn = new javax.swing.JButton();
        PaymentPnl = new javax.swing.JPanel();
        ExitBtn = new javax.swing.JButton();
        MinimizeBtn = new javax.swing.JButton();
        PayBtn = new javax.swing.JButton();
        CancelBtn = new javax.swing.JButton();
        AmountSep = new javax.swing.JSeparator();
        AmountLab = new javax.swing.JLabel();
        FeesSep = new javax.swing.JSeparator();
        FeesLab = new javax.swing.JLabel();
        UnitSep = new javax.swing.JSeparator();
        UnitLab = new javax.swing.JLabel();
        FeesRMLab = new javax.swing.JLabel();
        AmountRmLab = new javax.swing.JLabel();
        FeesMoneyLab = new javax.swing.JLabel();
        ErrorInputLab = new javax.swing.JLabel();
        AmountTxt = new javax.swing.JTextField();
        BlockSep = new javax.swing.JSeparator();
        BlockCbo = new javax.swing.JComboBox<>();
        BlockLab = new javax.swing.JLabel();
        FloorSep = new javax.swing.JSeparator();
        FloorCbo = new javax.swing.JComboBox<>();
        FloorLab = new javax.swing.JLabel();
        UnitCbo = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        PaymentSidePnl.setBackground(new java.awt.Color(0, 0, 0));
        PaymentSidePnl.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        PaymentLogoLab.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/icons8-money-filled-96.png"))); // NOI18N
        PaymentSidePnl.add(PaymentLogoLab, new org.netbeans.lib.awtextra.AbsoluteConstraints(62, 281, -1, -1));

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
        PaymentSidePnl.add(LogOutBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 592, 220, 78));

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
        PaymentSidePnl.add(BackBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 220, 73));

        getContentPane().add(PaymentSidePnl, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 220, 670));

        PaymentPnl.setBackground(new java.awt.Color(255, 255, 255));
        PaymentPnl.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

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
        PaymentPnl.add(ExitBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 0, 90, 40));

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
        PaymentPnl.add(MinimizeBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 0, 90, 40));

        PayBtn.setBackground(new java.awt.Color(0, 0, 0));
        PayBtn.setFont(new java.awt.Font("Gill Sans MT", 0, 24)); // NOI18N
        PayBtn.setForeground(new java.awt.Color(255, 255, 255));
        PayBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/icons8-money-64 (1).png"))); // NOI18N
        PayBtn.setText("Pay");
        PayBtn.setBorder(null);
        PayBtn.setFocusPainted(false);
        PayBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PayBtnActionPerformed(evt);
            }
        });
        PaymentPnl.add(PayBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 550, 180, -1));

        CancelBtn.setBackground(new java.awt.Color(0, 0, 0));
        CancelBtn.setFont(new java.awt.Font("Gill Sans MT", 0, 24)); // NOI18N
        CancelBtn.setForeground(new java.awt.Color(255, 255, 255));
        CancelBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/icons8-money-64 (3).png"))); // NOI18N
        CancelBtn.setText("Cancel");
        CancelBtn.setBorder(null);
        CancelBtn.setFocusPainted(false);
        CancelBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CancelBtnActionPerformed(evt);
            }
        });
        PaymentPnl.add(CancelBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 550, 180, -1));

        AmountSep.setBackground(new java.awt.Color(0, 0, 0));
        AmountSep.setForeground(new java.awt.Color(0, 0, 0));
        PaymentPnl.add(AmountSep, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 360, 240, -1));

        AmountLab.setFont(new java.awt.Font("Gill Sans MT", 0, 24)); // NOI18N
        AmountLab.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        AmountLab.setText("Amount:");
        PaymentPnl.add(AmountLab, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 330, -1, -1));

        FeesSep.setBackground(new java.awt.Color(0, 0, 0));
        FeesSep.setForeground(new java.awt.Color(0, 0, 0));
        PaymentPnl.add(FeesSep, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 290, 240, -1));

        FeesLab.setFont(new java.awt.Font("Gill Sans MT", 0, 24)); // NOI18N
        FeesLab.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        FeesLab.setText("Fees:");
        PaymentPnl.add(FeesLab, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 260, -1, -1));

        UnitSep.setBackground(new java.awt.Color(0, 0, 0));
        UnitSep.setForeground(new java.awt.Color(0, 0, 0));
        PaymentPnl.add(UnitSep, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 220, 170, -1));

        UnitLab.setFont(new java.awt.Font("Gill Sans MT", 0, 24)); // NOI18N
        UnitLab.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        UnitLab.setText("Unit:");
        PaymentPnl.add(UnitLab, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 190, -1, -1));

        FeesRMLab.setFont(new java.awt.Font("Gill Sans MT", 0, 24)); // NOI18N
        FeesRMLab.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        FeesRMLab.setText("RM");
        PaymentPnl.add(FeesRMLab, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 260, 50, 30));

        AmountRmLab.setFont(new java.awt.Font("Gill Sans MT", 0, 24)); // NOI18N
        AmountRmLab.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        AmountRmLab.setText("RM");
        PaymentPnl.add(AmountRmLab, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 330, 50, 30));

        FeesMoneyLab.setFont(new java.awt.Font("Gill Sans MT", 0, 24)); // NOI18N
        FeesMoneyLab.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        FeesMoneyLab.setText("0.00");
        PaymentPnl.add(FeesMoneyLab, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 260, 220, 30));

        ErrorInputLab.setFont(new java.awt.Font("Gill Sans MT", 0, 24)); // NOI18N
        ErrorInputLab.setForeground(new java.awt.Color(255, 0, 0));
        ErrorInputLab.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        PaymentPnl.add(ErrorInputLab, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 510, 360, 30));

        AmountTxt.setFont(new java.awt.Font("Gill Sans MT", 0, 24)); // NOI18N
        AmountTxt.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        AmountTxt.setText("200.00");
        AmountTxt.setBorder(null);
        AmountTxt.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                AmountTxtFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                AmountTxtFocusLost(evt);
            }
        });
        PaymentPnl.add(AmountTxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 330, 220, 30));

        BlockSep.setBackground(new java.awt.Color(0, 0, 0));
        BlockSep.setForeground(new java.awt.Color(0, 0, 0));
        PaymentPnl.add(BlockSep, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 150, 170, -1));

        BlockCbo.setBackground(javax.swing.UIManager.getDefaults().getColor("Button.background"));
        BlockCbo.setFont(new java.awt.Font("Gill Sans MT", 0, 24)); // NOI18N
        BlockCbo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "A", "B" }));
        BlockCbo.setBorder(null);
        BlockCbo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BlockCboActionPerformed(evt);
            }
        });
        PaymentPnl.add(BlockCbo, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 120, 150, 30));

        BlockLab.setFont(new java.awt.Font("Gill Sans MT", 0, 24)); // NOI18N
        BlockLab.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        BlockLab.setText("Block:");
        PaymentPnl.add(BlockLab, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 120, -1, -1));

        FloorSep.setBackground(new java.awt.Color(0, 0, 0));
        FloorSep.setForeground(new java.awt.Color(0, 0, 0));
        PaymentPnl.add(FloorSep, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 150, 170, -1));

        FloorCbo.setFont(new java.awt.Font("Gill Sans MT", 0, 24)); // NOI18N
        FloorCbo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10" }));
        FloorCbo.setBorder(null);
        FloorCbo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FloorCboActionPerformed(evt);
            }
        });
        PaymentPnl.add(FloorCbo, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 120, 150, 30));

        FloorLab.setFont(new java.awt.Font("Gill Sans MT", 0, 24)); // NOI18N
        FloorLab.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        FloorLab.setText("Floor:");
        PaymentPnl.add(FloorLab, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 120, -1, -1));

        UnitCbo.setFont(new java.awt.Font("Gill Sans MT", 0, 24)); // NOI18N
        UnitCbo.setBorder(null);
        UnitCbo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                UnitCboActionPerformed(evt);
            }
        });
        PaymentPnl.add(UnitCbo, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 190, 150, 30));

        getContentPane().add(PaymentPnl, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 0, 850, 670));

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

        if (userRegistrationCheck == true){
            
            Register registerForm = new Register();
            registerForm.setVisible(true);
            registerForm.setLocationRelativeTo(null);
            
            this.setVisible(false);
            
        } else {
            
            Staff staffForm = new Staff();
            staffForm.setVisible(true);
            staffForm.setLocationRelativeTo(null);

            this.setVisible(false);
            
        }

    }//GEN-LAST:event_BackBtnActionPerformed

    private void ExitBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ExitBtnActionPerformed
        // TODO add your handling code here:

        System.exit(0);
    }//GEN-LAST:event_ExitBtnActionPerformed

    private void MinimizeBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MinimizeBtnActionPerformed
        // TODO add your handling code here:

        this.setState(Payment.ICONIFIED);
    }//GEN-LAST:event_MinimizeBtnActionPerformed

    private void PayBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PayBtnActionPerformed
        // TODO add your handling code here:
      
        ResidentPayment();
        
        PayRoom();
        
            
    }//GEN-LAST:event_PayBtnActionPerformed

    private void CancelBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CancelBtnActionPerformed
        // TODO add your handling code here:
        
        if (userRegistrationCheck == true){
            
            Register registerForm = new Register();
            registerForm.setVisible(true);
            registerForm.setLocationRelativeTo(null);
            
            this.setVisible(false);
            
        } else {
            
            Staff staffForm = new Staff();
            staffForm.setVisible(true);
            staffForm.setLocationRelativeTo(null);

            this.setVisible(false);
            
        }
        
    }//GEN-LAST:event_CancelBtnActionPerformed

    private void AmountTxtFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_AmountTxtFocusGained
        // TODO add your handling code here:
        amount = AmountTxt.getText();
        
        if (amount.equals("200.00")) {
            AmountTxt.setText("");
        } else {
            
        }
        
    }//GEN-LAST:event_AmountTxtFocusGained

    private void AmountTxtFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_AmountTxtFocusLost
        // TODO add your handling code here:
        
        amount = AmountTxt.getText();
        
        if (amount.equals("")) {
            AmountTxt.setText("200.00");
        } else {
            
        }
        
    }//GEN-LAST:event_AmountTxtFocusLost

    private void BlockCboActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BlockCboActionPerformed
        // TODO add your handling code here: 
        
        ResidentPayment();
        
        RetrieveOutstandingFees();
        
        OccupiedUnit();
        
        OutstandingFees outstandingFeesForm = new OutstandingFees();
        outstandingFeesForm.ResidentOutstandingFees(roomUserID);
        
        
    }//GEN-LAST:event_BlockCboActionPerformed

    private void FloorCboActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FloorCboActionPerformed
        // TODO add your handling code here:
        
        ResidentPayment();
        
        RetrieveOutstandingFees();
        
        OccupiedUnit();
        
        OutstandingFees outstandingFeesForm = new OutstandingFees();
        outstandingFeesForm.ResidentOutstandingFees(roomUserID);
        
       
        
    }//GEN-LAST:event_FloorCboActionPerformed

    private void UnitCboActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_UnitCboActionPerformed
        // TODO add your handling code here:

        ResidentPayment(); 
        
        OutstandingFees outstandingFeesForm = new OutstandingFees();
        outstandingFeesForm.ResidentOutstandingFees(roomUserID);
        
        RetrieveOutstandingFees();
        
    }//GEN-LAST:event_UnitCboActionPerformed

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
            java.util.logging.Logger.getLogger(Payment.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
    //</editor-fold>
    
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new Payment().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel AmountLab;
    private javax.swing.JLabel AmountRmLab;
    private javax.swing.JSeparator AmountSep;
    private javax.swing.JTextField AmountTxt;
    private javax.swing.JButton BackBtn;
    private javax.swing.JComboBox<String> BlockCbo;
    private javax.swing.JLabel BlockLab;
    private javax.swing.JSeparator BlockSep;
    private javax.swing.JButton CancelBtn;
    private javax.swing.JLabel ErrorInputLab;
    private javax.swing.JButton ExitBtn;
    private javax.swing.JLabel FeesLab;
    private javax.swing.JLabel FeesMoneyLab;
    private javax.swing.JLabel FeesRMLab;
    private javax.swing.JSeparator FeesSep;
    private javax.swing.JComboBox<String> FloorCbo;
    private javax.swing.JLabel FloorLab;
    private javax.swing.JSeparator FloorSep;
    private javax.swing.JButton LogOutBtn;
    private javax.swing.JButton MinimizeBtn;
    private javax.swing.JButton PayBtn;
    private javax.swing.JLabel PaymentLogoLab;
    private javax.swing.JPanel PaymentPnl;
    private javax.swing.JPanel PaymentSidePnl;
    private javax.swing.JComboBox<String> UnitCbo;
    private javax.swing.JLabel UnitLab;
    private javax.swing.JSeparator UnitSep;
    // End of variables declaration//GEN-END:variables
}
