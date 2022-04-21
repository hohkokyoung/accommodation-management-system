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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author user
 */
public class BlockB extends javax.swing.JFrame {
public static String room, allRooms;
private static String displayCounter;
private PreparedStatement displayPST, displayAllPST;
private ResultSet displayResult, displayAllResult;

    /**
     * Creates new form BlockB
     */
    public BlockB() {
        initComponents();
        
        DisplayRooms();
    }

    private void DisplayRooms() {
        
        //Take the static method from DatabaseConnection to establish database connection.
        DatabaseConnection.DBConnection();
        
        //Catch the database error.
        if (errorDetection == true) {
            Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, errorDetails);
            System.out.println("Error: " + errorDetails);
        } else {
       
            try {
                
            //SQL statement to select all rooms.
            String displayAlLSQL = "SELECT COUNT(Room_ID) AS AllRooms "
                                + "FROM Rooms "
                                + "WHERE User_ID IS NULL AND Room_Block = 'B'";
            displayAllPST = conn.prepareStatement(displayAlLSQL);
            displayAllResult = displayAllPST.executeQuery();
            
            //Execute the query;
            while (displayAllResult.next()){
                allRooms = displayAllResult.getString("AllRooms");
                RoomsLeftNumberLab.setText(allRooms);
            }
            
            //Loop to change the input into the SQL statement.
            for(int counter = 1; counter < 11; counter++){
                
                displayCounter = String.valueOf(counter); 
                
                //SQL statement to display rooms in each floor.
                String displaySQL = "SELECT COUNT(Room_ID) AS Room "
                                    + "FROM Rooms "
                                    + "WHERE User_ID IS NULL "
                                    + "AND Room_Block = 'B' "
                                    + "AND Room_Floor = ?";
                displayPST = conn.prepareStatement(displaySQL);
                displayPST.setString(1, displayCounter);
                displayResult = displayPST.executeQuery();
                
                //Execute the query;
                while (displayResult.next()) {
                
                    room = displayResult.getString("Room");
                    
                    //Switch for the result to store in each individual fields.
                    switch (counter) {
                        case 1:
                            FirstFloorNumberLab.setText(room + "/5");
                            break;
                        case 2:
                            SecondFloorNumberLab.setText(room + "/5");
                            break;
                        case 3:
                            ThirdFloorNumberLab.setText(room + "/5");
                            break;
                        case 4:
                            FourthFloorNumberLab.setText(room + "/5");
                            break;
                        case 5:
                            FifthFloorNumberLab.setText(room + "/5");
                            break;
                        case 6:
                            SixthFloorNumberLab.setText(room + "/5");
                            break;
                        case 7:
                            SeventhFloorNumberLab.setText(room + "/5");
                            break;
                        case 8:
                            EighthFloorNumberLab.setText(room + "/5");
                            break;
                        case 9:
                            NinthFloorNumberLab.setText(room + "/5");
                            break;
                        case 10:
                            TenthFloorNumberLab.setText(room + "/5");
                            break;
                        default:
                            break;
                    }
                }
            }
            
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
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        BlockBSidePnl = new javax.swing.JPanel();
        BlockBLogo = new javax.swing.JLabel();
        LogOutBtn = new javax.swing.JButton();
        BackBtn = new javax.swing.JButton();
        BlockBPnl = new javax.swing.JPanel();
        ExitBtn = new javax.swing.JButton();
        MinimizeBtn = new javax.swing.JButton();
        RoomsLeftSep = new javax.swing.JSeparator();
        RoomsLeftLab = new javax.swing.JLabel();
        RoomsLeftNumberLab = new javax.swing.JLabel();
        FirstFloorNumberLab = new javax.swing.JLabel();
        FirstFloorSep = new javax.swing.JSeparator();
        FirstFloorLab = new javax.swing.JLabel();
        SixthFloorLab = new javax.swing.JLabel();
        SixthFloorNumberLab = new javax.swing.JLabel();
        SixthFloorSep = new javax.swing.JSeparator();
        SecondFloorSep = new javax.swing.JSeparator();
        SecondFloorNumberLab = new javax.swing.JLabel();
        SecondFloorLab = new javax.swing.JLabel();
        ThirdFloorSep = new javax.swing.JSeparator();
        ThirdFloorNumberLab = new javax.swing.JLabel();
        ThirdFloorLab = new javax.swing.JLabel();
        FourthFloorSep = new javax.swing.JSeparator();
        FourthFloorNumberLab = new javax.swing.JLabel();
        FourthFloorLab = new javax.swing.JLabel();
        FifthFloorSep = new javax.swing.JSeparator();
        FifthFloorNumberLab = new javax.swing.JLabel();
        FifthFloorLab = new javax.swing.JLabel();
        SeventhFloorLab = new javax.swing.JLabel();
        EighthFloorLab = new javax.swing.JLabel();
        NinthFloorLab = new javax.swing.JLabel();
        TenthFloor = new javax.swing.JLabel();
        TenthFloorSep = new javax.swing.JSeparator();
        TenthFloorNumberLab = new javax.swing.JLabel();
        NinthFloorSep = new javax.swing.JSeparator();
        NinthFloorNumberLab = new javax.swing.JLabel();
        EightFloorSep = new javax.swing.JSeparator();
        EighthFloorNumberLab = new javax.swing.JLabel();
        SeventhFloorSep = new javax.swing.JSeparator();
        SeventhFloorNumberLab = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        BlockBSidePnl.setBackground(new java.awt.Color(0, 0, 0));
        BlockBSidePnl.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        BlockBLogo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/icons8-building-100 (1).png"))); // NOI18N
        BlockBSidePnl.add(BlockBLogo, new org.netbeans.lib.awtextra.AbsoluteConstraints(58, 281, -1, -1));

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
        BlockBSidePnl.add(LogOutBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 592, 220, 78));

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
        BlockBSidePnl.add(BackBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 220, 73));

        getContentPane().add(BlockBSidePnl, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 220, 670));

        BlockBPnl.setBackground(new java.awt.Color(255, 255, 255));
        BlockBPnl.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

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
        BlockBPnl.add(ExitBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 0, 90, 40));

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
        BlockBPnl.add(MinimizeBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 0, 90, 40));

        RoomsLeftSep.setBackground(new java.awt.Color(0, 0, 0));
        RoomsLeftSep.setForeground(new java.awt.Color(0, 0, 0));
        BlockBPnl.add(RoomsLeftSep, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 110, 240, -1));

        RoomsLeftLab.setFont(new java.awt.Font("Gill Sans MT", 0, 24)); // NOI18N
        RoomsLeftLab.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        RoomsLeftLab.setText("Rooms Left:");
        BlockBPnl.add(RoomsLeftLab, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 80, -1, -1));

        RoomsLeftNumberLab.setFont(new java.awt.Font("Gill Sans MT", 0, 24)); // NOI18N
        RoomsLeftNumberLab.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        RoomsLeftNumberLab.setText("0");
        BlockBPnl.add(RoomsLeftNumberLab, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 80, 220, 30));

        FirstFloorNumberLab.setFont(new java.awt.Font("Gill Sans MT", 0, 24)); // NOI18N
        FirstFloorNumberLab.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        FirstFloorNumberLab.setText("/5");
        BlockBPnl.add(FirstFloorNumberLab, new org.netbeans.lib.awtextra.AbsoluteConstraints(269, 210, 120, -1));

        FirstFloorSep.setBackground(new java.awt.Color(0, 0, 0));
        FirstFloorSep.setForeground(new java.awt.Color(0, 0, 0));
        BlockBPnl.add(FirstFloorSep, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 240, 140, -1));

        FirstFloorLab.setFont(new java.awt.Font("Gill Sans MT", 0, 24)); // NOI18N
        FirstFloorLab.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        FirstFloorLab.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/icons8-number-1-64.png"))); // NOI18N
        FirstFloorLab.setText("F");
        BlockBPnl.add(FirstFloorLab, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 190, 80, 70));

        SixthFloorLab.setFont(new java.awt.Font("Gill Sans MT", 0, 24)); // NOI18N
        SixthFloorLab.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        SixthFloorLab.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/icons8-number-6-64.png"))); // NOI18N
        SixthFloorLab.setText("F");
        BlockBPnl.add(SixthFloorLab, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 190, 80, 70));

        SixthFloorNumberLab.setFont(new java.awt.Font("Gill Sans MT", 0, 24)); // NOI18N
        SixthFloorNumberLab.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        SixthFloorNumberLab.setText("/5");
        BlockBPnl.add(SixthFloorNumberLab, new org.netbeans.lib.awtextra.AbsoluteConstraints(579, 210, 120, -1));

        SixthFloorSep.setBackground(new java.awt.Color(0, 0, 0));
        SixthFloorSep.setForeground(new java.awt.Color(0, 0, 0));
        BlockBPnl.add(SixthFloorSep, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 240, 140, -1));

        SecondFloorSep.setBackground(new java.awt.Color(0, 0, 0));
        SecondFloorSep.setForeground(new java.awt.Color(0, 0, 0));
        BlockBPnl.add(SecondFloorSep, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 320, 140, -1));

        SecondFloorNumberLab.setFont(new java.awt.Font("Gill Sans MT", 0, 24)); // NOI18N
        SecondFloorNumberLab.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        SecondFloorNumberLab.setText("/5");
        BlockBPnl.add(SecondFloorNumberLab, new org.netbeans.lib.awtextra.AbsoluteConstraints(269, 290, 120, -1));

        SecondFloorLab.setFont(new java.awt.Font("Gill Sans MT", 0, 24)); // NOI18N
        SecondFloorLab.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        SecondFloorLab.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/icons8-number-2-64.png"))); // NOI18N
        SecondFloorLab.setText("F");
        BlockBPnl.add(SecondFloorLab, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 270, 80, 70));

        ThirdFloorSep.setBackground(new java.awt.Color(0, 0, 0));
        ThirdFloorSep.setForeground(new java.awt.Color(0, 0, 0));
        BlockBPnl.add(ThirdFloorSep, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 400, 140, -1));

        ThirdFloorNumberLab.setFont(new java.awt.Font("Gill Sans MT", 0, 24)); // NOI18N
        ThirdFloorNumberLab.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ThirdFloorNumberLab.setText("/5");
        BlockBPnl.add(ThirdFloorNumberLab, new org.netbeans.lib.awtextra.AbsoluteConstraints(269, 370, 120, -1));

        ThirdFloorLab.setFont(new java.awt.Font("Gill Sans MT", 0, 24)); // NOI18N
        ThirdFloorLab.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ThirdFloorLab.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/icons8-3-64 (1).png"))); // NOI18N
        ThirdFloorLab.setText("F");
        BlockBPnl.add(ThirdFloorLab, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 350, 80, 70));

        FourthFloorSep.setBackground(new java.awt.Color(0, 0, 0));
        FourthFloorSep.setForeground(new java.awt.Color(0, 0, 0));
        BlockBPnl.add(FourthFloorSep, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 480, 140, -1));

        FourthFloorNumberLab.setFont(new java.awt.Font("Gill Sans MT", 0, 24)); // NOI18N
        FourthFloorNumberLab.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        FourthFloorNumberLab.setText("/5");
        BlockBPnl.add(FourthFloorNumberLab, new org.netbeans.lib.awtextra.AbsoluteConstraints(269, 450, 120, -1));

        FourthFloorLab.setFont(new java.awt.Font("Gill Sans MT", 0, 24)); // NOI18N
        FourthFloorLab.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        FourthFloorLab.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/icons8-number-4-64.png"))); // NOI18N
        FourthFloorLab.setText("F");
        BlockBPnl.add(FourthFloorLab, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 430, 80, 70));

        FifthFloorSep.setBackground(new java.awt.Color(0, 0, 0));
        FifthFloorSep.setForeground(new java.awt.Color(0, 0, 0));
        BlockBPnl.add(FifthFloorSep, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 560, 140, -1));

        FifthFloorNumberLab.setFont(new java.awt.Font("Gill Sans MT", 0, 24)); // NOI18N
        FifthFloorNumberLab.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        FifthFloorNumberLab.setText("/5");
        BlockBPnl.add(FifthFloorNumberLab, new org.netbeans.lib.awtextra.AbsoluteConstraints(269, 530, 120, -1));

        FifthFloorLab.setFont(new java.awt.Font("Gill Sans MT", 0, 24)); // NOI18N
        FifthFloorLab.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        FifthFloorLab.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/icons8-5-64 (1).png"))); // NOI18N
        FifthFloorLab.setText("F");
        BlockBPnl.add(FifthFloorLab, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 510, 80, 70));

        SeventhFloorLab.setFont(new java.awt.Font("Gill Sans MT", 0, 24)); // NOI18N
        SeventhFloorLab.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        SeventhFloorLab.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/icons8-7-64 (1).png"))); // NOI18N
        SeventhFloorLab.setText("F");
        BlockBPnl.add(SeventhFloorLab, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 270, 80, 70));

        EighthFloorLab.setFont(new java.awt.Font("Gill Sans MT", 0, 24)); // NOI18N
        EighthFloorLab.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        EighthFloorLab.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/icons8-8-64 (1).png"))); // NOI18N
        EighthFloorLab.setText("F");
        BlockBPnl.add(EighthFloorLab, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 350, 80, 70));

        NinthFloorLab.setFont(new java.awt.Font("Gill Sans MT", 0, 24)); // NOI18N
        NinthFloorLab.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        NinthFloorLab.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/icons8-9-64 (2).png"))); // NOI18N
        NinthFloorLab.setText("F");
        BlockBPnl.add(NinthFloorLab, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 430, 80, 70));

        TenthFloor.setFont(new java.awt.Font("Gill Sans MT", 0, 24)); // NOI18N
        TenthFloor.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        TenthFloor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/icons8-10-filled-64.png"))); // NOI18N
        TenthFloor.setText("F");
        BlockBPnl.add(TenthFloor, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 510, 80, 70));

        TenthFloorSep.setBackground(new java.awt.Color(0, 0, 0));
        TenthFloorSep.setForeground(new java.awt.Color(0, 0, 0));
        BlockBPnl.add(TenthFloorSep, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 560, 140, -1));

        TenthFloorNumberLab.setFont(new java.awt.Font("Gill Sans MT", 0, 24)); // NOI18N
        TenthFloorNumberLab.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        TenthFloorNumberLab.setText("/5");
        BlockBPnl.add(TenthFloorNumberLab, new org.netbeans.lib.awtextra.AbsoluteConstraints(579, 530, 120, -1));

        NinthFloorSep.setBackground(new java.awt.Color(0, 0, 0));
        NinthFloorSep.setForeground(new java.awt.Color(0, 0, 0));
        BlockBPnl.add(NinthFloorSep, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 480, 140, -1));

        NinthFloorNumberLab.setFont(new java.awt.Font("Gill Sans MT", 0, 24)); // NOI18N
        NinthFloorNumberLab.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        NinthFloorNumberLab.setText("/5");
        BlockBPnl.add(NinthFloorNumberLab, new org.netbeans.lib.awtextra.AbsoluteConstraints(579, 450, 120, -1));

        EightFloorSep.setBackground(new java.awt.Color(0, 0, 0));
        EightFloorSep.setForeground(new java.awt.Color(0, 0, 0));
        BlockBPnl.add(EightFloorSep, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 400, 140, -1));

        EighthFloorNumberLab.setFont(new java.awt.Font("Gill Sans MT", 0, 24)); // NOI18N
        EighthFloorNumberLab.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        EighthFloorNumberLab.setText("/5");
        BlockBPnl.add(EighthFloorNumberLab, new org.netbeans.lib.awtextra.AbsoluteConstraints(579, 370, 120, -1));

        SeventhFloorSep.setBackground(new java.awt.Color(0, 0, 0));
        SeventhFloorSep.setForeground(new java.awt.Color(0, 0, 0));
        BlockBPnl.add(SeventhFloorSep, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 320, 140, -1));

        SeventhFloorNumberLab.setFont(new java.awt.Font("Gill Sans MT", 0, 24)); // NOI18N
        SeventhFloorNumberLab.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        SeventhFloorNumberLab.setText("/5");
        BlockBPnl.add(SeventhFloorNumberLab, new org.netbeans.lib.awtextra.AbsoluteConstraints(579, 290, 120, -1));

        getContentPane().add(BlockBPnl, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 0, 850, 670));

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

        Rooms roomsform = new Rooms();
        roomsform.setVisible(true);
        roomsform.setLocationRelativeTo(null);
        this.setVisible(false);
    }//GEN-LAST:event_BackBtnActionPerformed

    private void ExitBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ExitBtnActionPerformed
        // TODO add your handling code here:

        System.exit(0);
    }//GEN-LAST:event_ExitBtnActionPerformed

    private void MinimizeBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MinimizeBtnActionPerformed
        // TODO add your handling code here:

        this.setState(BlockB.ICONIFIED);
    }//GEN-LAST:event_MinimizeBtnActionPerformed

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
            java.util.logging.Logger.getLogger(BlockB.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
    //</editor-fold>
    
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new BlockB().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BackBtn;
    private javax.swing.JLabel BlockBLogo;
    private javax.swing.JPanel BlockBPnl;
    private javax.swing.JPanel BlockBSidePnl;
    private javax.swing.JSeparator EightFloorSep;
    private javax.swing.JLabel EighthFloorLab;
    private javax.swing.JLabel EighthFloorNumberLab;
    private javax.swing.JButton ExitBtn;
    private javax.swing.JLabel FifthFloorLab;
    private javax.swing.JLabel FifthFloorNumberLab;
    private javax.swing.JSeparator FifthFloorSep;
    private javax.swing.JLabel FirstFloorLab;
    private javax.swing.JLabel FirstFloorNumberLab;
    private javax.swing.JSeparator FirstFloorSep;
    private javax.swing.JLabel FourthFloorLab;
    private javax.swing.JLabel FourthFloorNumberLab;
    private javax.swing.JSeparator FourthFloorSep;
    private javax.swing.JButton LogOutBtn;
    private javax.swing.JButton MinimizeBtn;
    private javax.swing.JLabel NinthFloorLab;
    private javax.swing.JLabel NinthFloorNumberLab;
    private javax.swing.JSeparator NinthFloorSep;
    private javax.swing.JLabel RoomsLeftLab;
    private javax.swing.JLabel RoomsLeftNumberLab;
    private javax.swing.JSeparator RoomsLeftSep;
    private javax.swing.JLabel SecondFloorLab;
    private javax.swing.JLabel SecondFloorNumberLab;
    private javax.swing.JSeparator SecondFloorSep;
    private javax.swing.JLabel SeventhFloorLab;
    private javax.swing.JLabel SeventhFloorNumberLab;
    private javax.swing.JSeparator SeventhFloorSep;
    private javax.swing.JLabel SixthFloorLab;
    private javax.swing.JLabel SixthFloorNumberLab;
    private javax.swing.JSeparator SixthFloorSep;
    private javax.swing.JLabel TenthFloor;
    private javax.swing.JLabel TenthFloorNumberLab;
    private javax.swing.JSeparator TenthFloorSep;
    private javax.swing.JLabel ThirdFloorLab;
    private javax.swing.JLabel ThirdFloorNumberLab;
    private javax.swing.JSeparator ThirdFloorSep;
    // End of variables declaration//GEN-END:variables
}
