/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package feespaymentsystem;

import static feespaymentsystem.DatabaseConnection.conn;
import static feespaymentsystem.DatabaseConnection.errorDetails;
import static feespaymentsystem.DatabaseConnection.errorDetection;
import static feespaymentsystem.UsedTelephoneEmail.lengthUsedInfoEmail;
import static feespaymentsystem.UsedTelephoneEmail.lengthUsedInfoTelephone;
import static feespaymentsystem.UsedTelephoneEmail.registrationUsedInfoResult;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingUtilities;

/**
 *
 * @author user
 */
public class Register extends javax.swing.JFrame {

    public static String firstname, lastname, firstName, lastName, telephone, email, unitID;
    public static String validText, validEmail, validTelephone2_4, validTelephone6_9, 
                validTelephone011, validTelephone010;
    public static String selectedBlock, selectedFloor, unit, usedID, 
                usedTelephone, usedEmail;
    public static String retrievedResidentID, eliminateResidentID, fakeNewID, finalNewID;
    public PreparedStatement unitPST, newIDPST; 
    public ResultSet unitResult, newIDResult;
    public int tempNewID, lengthUsedInfoCounter;
    public static Boolean userRegistrationCheck = false, userRegistrationInitiation = false;
                            
    /**
     * Creates new form Register
     */
    public Register() {
        initComponents();
        
        //AvailableUnit();
        
        //Automatically reset the registraion approval to false. (In case of details modification)
        userRegistrationCheck = false;
        
        userRegistrationInitiation = false;
        
        NewID();

        SwingUtilities.getRootPane(RegisterBtn).setDefaultButton(RegisterBtn);
    }
    
    private void ResidentRegister() {
        
         //Take static the method from DatabaseConnection to establish database connection.
        DatabaseConnection.DBConnection();
        
        //Catch the database error.
        if (errorDetection == true) {
            Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, errorDetails);
            ErrorInputLab.setText("Database connection issue.");
            System.out.println("Error: " + errorDetails);
        } else {
            
            //Validation purposes.
            validText = "[a-zA-Z ]+";
            validEmail = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                    + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
            validTelephone2_4 = "^(01[2-4]-)?(\\d{7})$";
            validTelephone6_9 = "^(01[6-9]-)?(\\d{7})$";
            validTelephone011 = "^(011-)?(\\d{8})$";
            validTelephone010 = "^(010-)?(\\d{7})$";
            
            //Store the values of the text fields into variables.
            firstName = FirstNameTxt.getText();
            lastName = LastNameTxt.getText();
            telephone = TelephoneTxt.getText();
            email = EmailTxt.getText();
            
            //Check if any of the text fields is empty.
            if(firstName.trim().isEmpty() || lastName.trim().isEmpty()||
                telephone.trim().isEmpty() || email.trim().isEmpty()) {
            ErrorInputLab.setText("Please fill all the empty fields.");
            
            //Check if the first name and/or last name is invalid.
            } else if(!(firstName.matches(validText)) && !(lastName.matches(validText))) {
                ErrorInputLab.setText("First and last name is invalid.");
            } else if(!(firstName.matches(validText))) {
                ErrorInputLab.setText("First name is invalid.");
            } else if(!(lastName.matches(validText))) {
                ErrorInputLab.setText("Last name is invalid.");
                
            //Check if the telephone or email is invalid.
            } else if(!(telephone.matches(validTelephone2_4)) && 
                    !(telephone.matches(validTelephone6_9))  && 
                    !(telephone.matches(validTelephone011)) && 
                    !(telephone.matches(validTelephone010))) {
                ErrorInputLab.setText("Telephone is invalid.");
            } else if(!(email.matches(validEmail))) {
                ErrorInputLab.setText("Email is invalid.");
            } else {

                //Clear Error field if no error.
                 ErrorInputLab.setText("");
        
                try {
                      
                    UsedTelephoneEmail.UsedResidentInfo();
   
                    //Execute the usedInfo query.
                    while (registrationUsedInfoResult.next()){

                        usedTelephone = registrationUsedInfoResult.getString("User_TelephoneNumber");
                        usedEmail = registrationUsedInfoResult.getString("User_EmailAddress");
                        
                        //Store the telephone into an array;
                        lengthUsedInfoTelephone[lengthUsedInfoCounter] = usedTelephone;
                        
                        //Store the email into an array;
                        lengthUsedInfoEmail[lengthUsedInfoCounter] = usedEmail;
                        
                        lengthUsedInfoCounter++;
                        
                    }
                    
                    if (Arrays.asList(lengthUsedInfoTelephone).contains(telephone) && Arrays.asList(lengthUsedInfoEmail).contains(email)){

                        ErrorInputLab.setText("Telephone and email already exist.");

                    } else if (Arrays.asList(lengthUsedInfoTelephone).contains(telephone)){

                        ErrorInputLab.setText("Telephone already exists.");

                    } else if (Arrays.asList(lengthUsedInfoEmail).contains(email)){

                        ErrorInputLab.setText("Email already exists.");

                    } else {
                        
                        userRegistrationCheck = true;
                        
                        Payment paymentForm = new Payment();
                        paymentForm.setVisible(true);
                        paymentForm.setLocationRelativeTo(null);
                        this.setVisible(false);
     
                    }
                    
                    //Catch SQL execution error.
                    } catch (SQLException ex) {
                    ErrorInputLab.setText("SQL execution error.");
                    System.out.println(ex);
                }
            }
        }
    }
    
    public void NewID() {
        
        //Take the static method from DatabaseConnection to establish database connection.
        DatabaseConnection.DBConnection();
        
        //Catch the database error.
        if (errorDetection == true) {
            Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, errorDetails);
            ErrorInputLab.setText("Database connection issue.");
            System.out.println("Error: " + errorDetails);
        } else {
            
            try {

                //SQL statement to be executed (Compare ID purposes)
                String newIDSQL = "SELECT TOP(1) User_ID "
                                + "FROM Users "
                                + "WHERE User_Role = 'Resident' "
                                + "ORDER BY User_ID DESC";
                newIDPST = conn.prepareStatement(newIDSQL);
                newIDResult = newIDPST.executeQuery();
       
                if (newIDResult.next()){

                    //Increment the Resident ID by 1.
                    retrievedResidentID = newIDResult.getString("User_ID");
                    eliminateResidentID = "RS";
                    fakeNewID = retrievedResidentID.replaceAll(eliminateResidentID,"");
                    tempNewID = Integer.parseInt(fakeNewID) + 1;
                    finalNewID = String.format("RS" + "%06d", tempNewID);
                    
                } else {
                    
                    finalNewID = "RS000001";  
                    
                }
                
                IDLab.setText(finalNewID);
                
        //Catch SQL execution error.
        } catch (SQLException ex) {
            ErrorInputLab.setText("SQL execution error.");
            System.out.println(ex);
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

        RegisterSidePnl = new javax.swing.JPanel();
        RegisterLogoLab = new javax.swing.JLabel();
        LogOutBtn = new javax.swing.JButton();
        BackBtn = new javax.swing.JButton();
        RegisterPnl = new javax.swing.JPanel();
        ExitBtn = new javax.swing.JButton();
        MinimizeBtn = new javax.swing.JButton();
        RegisterBtn = new javax.swing.JButton();
        CancelBtn = new javax.swing.JButton();
        IDNumberLab = new javax.swing.JLabel();
        IDNumberSep = new javax.swing.JSeparator();
        FirstNameSep = new javax.swing.JSeparator();
        FirstNameTxt = new javax.swing.JTextField();
        FirstNameLab = new javax.swing.JLabel();
        LastNameLab = new javax.swing.JLabel();
        LastNameSep = new javax.swing.JSeparator();
        LastNameTxt = new javax.swing.JTextField();
        TelephoneSep = new javax.swing.JSeparator();
        TelephoneTxt = new javax.swing.JTextField();
        TelephoneLab = new javax.swing.JLabel();
        EmailSep = new javax.swing.JSeparator();
        EmailTxt = new javax.swing.JTextField();
        EmailLab = new javax.swing.JLabel();
        IDLab = new javax.swing.JLabel();
        ErrorInputLab = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        RegisterSidePnl.setBackground(new java.awt.Color(0, 0, 0));
        RegisterSidePnl.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        RegisterLogoLab.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/icons8-user-96.png"))); // NOI18N
        RegisterSidePnl.add(RegisterLogoLab, new org.netbeans.lib.awtextra.AbsoluteConstraints(62, 281, -1, -1));

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
        RegisterSidePnl.add(LogOutBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 592, 220, 78));

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
        RegisterSidePnl.add(BackBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 220, 73));

        getContentPane().add(RegisterSidePnl, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 220, 670));

        RegisterPnl.setBackground(new java.awt.Color(255, 255, 255));
        RegisterPnl.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

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
        RegisterPnl.add(ExitBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 0, 90, 40));

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
        RegisterPnl.add(MinimizeBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 0, 90, 40));

        RegisterBtn.setBackground(new java.awt.Color(0, 0, 0));
        RegisterBtn.setFont(new java.awt.Font("Gill Sans MT", 0, 24)); // NOI18N
        RegisterBtn.setForeground(new java.awt.Color(255, 255, 255));
        RegisterBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/icons8-person-64 (1).png"))); // NOI18N
        RegisterBtn.setText("Register");
        RegisterBtn.setBorder(null);
        RegisterBtn.setFocusPainted(false);
        RegisterBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RegisterBtnActionPerformed(evt);
            }
        });
        RegisterPnl.add(RegisterBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 550, 180, -1));

        CancelBtn.setBackground(new java.awt.Color(0, 0, 0));
        CancelBtn.setFont(new java.awt.Font("Gill Sans MT", 0, 24)); // NOI18N
        CancelBtn.setForeground(new java.awt.Color(255, 255, 255));
        CancelBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/icons8-person-64 (4).png"))); // NOI18N
        CancelBtn.setText("Cancel");
        CancelBtn.setBorder(null);
        CancelBtn.setFocusPainted(false);
        CancelBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CancelBtnActionPerformed(evt);
            }
        });
        RegisterPnl.add(CancelBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 550, 180, -1));

        IDNumberLab.setFont(new java.awt.Font("Gill Sans MT", 0, 24)); // NOI18N
        IDNumberLab.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        IDNumberLab.setText("ID Number:");
        RegisterPnl.add(IDNumberLab, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 190, -1, -1));

        IDNumberSep.setBackground(new java.awt.Color(0, 0, 0));
        IDNumberSep.setForeground(new java.awt.Color(0, 0, 0));
        RegisterPnl.add(IDNumberSep, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 220, 240, -1));

        FirstNameSep.setBackground(new java.awt.Color(0, 0, 0));
        FirstNameSep.setForeground(new java.awt.Color(0, 0, 0));
        RegisterPnl.add(FirstNameSep, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 290, 170, -1));

        FirstNameTxt.setFont(new java.awt.Font("Gill Sans MT", 0, 24)); // NOI18N
        FirstNameTxt.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        FirstNameTxt.setText("Kok Young");
        FirstNameTxt.setBorder(null);
        FirstNameTxt.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                FirstNameTxtFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                FirstNameTxtFocusLost(evt);
            }
        });
        FirstNameTxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FirstNameTxtActionPerformed(evt);
            }
        });
        RegisterPnl.add(FirstNameTxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 260, 150, 30));

        FirstNameLab.setFont(new java.awt.Font("Gill Sans MT", 0, 24)); // NOI18N
        FirstNameLab.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        FirstNameLab.setText("First Name:");
        RegisterPnl.add(FirstNameLab, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 260, -1, -1));

        LastNameLab.setFont(new java.awt.Font("Gill Sans MT", 0, 24)); // NOI18N
        LastNameLab.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        LastNameLab.setText("Last Name:");
        RegisterPnl.add(LastNameLab, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 260, -1, -1));

        LastNameSep.setBackground(new java.awt.Color(0, 0, 0));
        LastNameSep.setForeground(new java.awt.Color(0, 0, 0));
        RegisterPnl.add(LastNameSep, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 290, 170, -1));

        LastNameTxt.setFont(new java.awt.Font("Gill Sans MT", 0, 24)); // NOI18N
        LastNameTxt.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        LastNameTxt.setText("Hoh");
        LastNameTxt.setBorder(null);
        LastNameTxt.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                LastNameTxtFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                LastNameTxtFocusLost(evt);
            }
        });
        LastNameTxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LastNameTxtActionPerformed(evt);
            }
        });
        RegisterPnl.add(LastNameTxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 260, 150, 30));

        TelephoneSep.setBackground(new java.awt.Color(0, 0, 0));
        TelephoneSep.setForeground(new java.awt.Color(0, 0, 0));
        RegisterPnl.add(TelephoneSep, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 360, 240, -1));

        TelephoneTxt.setFont(new java.awt.Font("Gill Sans MT", 0, 24)); // NOI18N
        TelephoneTxt.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        TelephoneTxt.setText("012-1234567");
        TelephoneTxt.setBorder(null);
        TelephoneTxt.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                TelephoneTxtFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                TelephoneTxtFocusLost(evt);
            }
        });
        TelephoneTxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TelephoneTxtActionPerformed(evt);
            }
        });
        RegisterPnl.add(TelephoneTxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 330, 220, 30));

        TelephoneLab.setFont(new java.awt.Font("Gill Sans MT", 0, 24)); // NOI18N
        TelephoneLab.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        TelephoneLab.setText("Telephone:");
        RegisterPnl.add(TelephoneLab, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 330, -1, -1));

        EmailSep.setBackground(new java.awt.Color(0, 0, 0));
        EmailSep.setForeground(new java.awt.Color(0, 0, 0));
        RegisterPnl.add(EmailSep, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 430, 240, -1));

        EmailTxt.setFont(new java.awt.Font("Gill Sans MT", 0, 24)); // NOI18N
        EmailTxt.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        EmailTxt.setText("hoh@hotmail.com");
        EmailTxt.setBorder(null);
        EmailTxt.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                EmailTxtFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                EmailTxtFocusLost(evt);
            }
        });
        EmailTxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EmailTxtActionPerformed(evt);
            }
        });
        RegisterPnl.add(EmailTxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 400, 220, 30));

        EmailLab.setFont(new java.awt.Font("Gill Sans MT", 0, 24)); // NOI18N
        EmailLab.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        EmailLab.setText("Email:");
        RegisterPnl.add(EmailLab, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 400, -1, -1));

        IDLab.setFont(new java.awt.Font("Gill Sans MT", 0, 24)); // NOI18N
        IDLab.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        RegisterPnl.add(IDLab, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 190, 220, 30));

        ErrorInputLab.setFont(new java.awt.Font("Gill Sans MT", 0, 24)); // NOI18N
        ErrorInputLab.setForeground(new java.awt.Color(255, 0, 0));
        ErrorInputLab.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        RegisterPnl.add(ErrorInputLab, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 510, 360, 30));

        getContentPane().add(RegisterPnl, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 0, 850, 670));

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

        StaffResident staffresidentform = new StaffResident();
        staffresidentform.setVisible(true);
        staffresidentform.setLocationRelativeTo(null);
        this.setVisible(false);

    }//GEN-LAST:event_BackBtnActionPerformed

    private void ExitBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ExitBtnActionPerformed
        // TODO add your handling code here:

        System.exit(0);
    }//GEN-LAST:event_ExitBtnActionPerformed

    private void MinimizeBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MinimizeBtnActionPerformed
        // TODO add your handling code here:

        this.setState(Register.ICONIFIED);

    }//GEN-LAST:event_MinimizeBtnActionPerformed

    private void RegisterBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RegisterBtnActionPerformed
        // TODO add your handling code here:
    
        userRegistrationInitiation = true;
        
        ResidentRegister();
        
    }//GEN-LAST:event_RegisterBtnActionPerformed

    private void CancelBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CancelBtnActionPerformed
        // TODO add your handling code here:
        
        StaffResident staffresidentform = new StaffResident();
        staffresidentform.setVisible(true);
        staffresidentform.setLocationRelativeTo(null);
        this.setVisible(false);
    }//GEN-LAST:event_CancelBtnActionPerformed

    private void FirstNameTxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FirstNameTxtActionPerformed
        // TODO add your handling code here:
        
        
    }//GEN-LAST:event_FirstNameTxtActionPerformed

    private void LastNameTxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LastNameTxtActionPerformed
        // TODO add your handling code here:
        
        
    }//GEN-LAST:event_LastNameTxtActionPerformed

    private void TelephoneTxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TelephoneTxtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TelephoneTxtActionPerformed

    private void EmailTxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EmailTxtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_EmailTxtActionPerformed

    private void FirstNameTxtFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_FirstNameTxtFocusGained
        // TODO add your handling code here:
        
        firstName = FirstNameTxt.getText();
        
        if (firstName.equals("Kok Young")) {
            FirstNameTxt.setText("");
        } else {
            
        }
        
        
    }//GEN-LAST:event_FirstNameTxtFocusGained

    private void FirstNameTxtFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_FirstNameTxtFocusLost
        // TODO add your handling code here:
        firstName = FirstNameTxt.getText();
        
        if (firstName.equals("")) {
            FirstNameTxt.setText("Kok Young");
        } else {
            
        }
        
    }//GEN-LAST:event_FirstNameTxtFocusLost

    private void LastNameTxtFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_LastNameTxtFocusGained
        // TODO add your handling code here:
        
        lastName = LastNameTxt.getText();
        
        if (lastName.equals("Hoh")) {
            LastNameTxt.setText("");
        } else {
            
        }
        
    }//GEN-LAST:event_LastNameTxtFocusGained

    private void LastNameTxtFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_LastNameTxtFocusLost
        // TODO add your handling code here:
        
        lastName = LastNameTxt.getText();
        
        if (lastName.equals("")) {
            LastNameTxt.setText("Hoh");
        } else {
            
        }
    }//GEN-LAST:event_LastNameTxtFocusLost

    private void TelephoneTxtFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_TelephoneTxtFocusGained
        // TODO add your handling code here:
        telephone = TelephoneTxt.getText();
        
        if (telephone.equals("012-1234567")) {
            TelephoneTxt.setText("");
        } else {
            
        }
        
    }//GEN-LAST:event_TelephoneTxtFocusGained

    private void TelephoneTxtFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_TelephoneTxtFocusLost
        // TODO add your handling code here:
        
        telephone = TelephoneTxt.getText();
        
        if (telephone.equals("")) {
            TelephoneTxt.setText("012-1234567");
        } else {
            
        }
        
    }//GEN-LAST:event_TelephoneTxtFocusLost

    private void EmailTxtFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_EmailTxtFocusGained
        // TODO add your handling code here:
        email = EmailTxt.getText();
        
        if (email.equals("hoh@hotmail.com")) {
            EmailTxt.setText("");
        } else {
            
        }
        
    
        
    }//GEN-LAST:event_EmailTxtFocusGained

    private void EmailTxtFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_EmailTxtFocusLost
        // TODO add your handling code here:
        
        email = EmailTxt.getText();
        
        if (email.equals("")) {
            EmailTxt.setText("hoh@hotmail.com");
        } else {
            
        }
        
    }//GEN-LAST:event_EmailTxtFocusLost

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
            java.util.logging.Logger.getLogger(Register.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new Register().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BackBtn;
    private javax.swing.JButton CancelBtn;
    private javax.swing.JLabel EmailLab;
    private javax.swing.JSeparator EmailSep;
    private javax.swing.JTextField EmailTxt;
    private javax.swing.JLabel ErrorInputLab;
    private javax.swing.JButton ExitBtn;
    private javax.swing.JLabel FirstNameLab;
    private javax.swing.JSeparator FirstNameSep;
    private javax.swing.JTextField FirstNameTxt;
    private javax.swing.JLabel IDLab;
    private javax.swing.JLabel IDNumberLab;
    private javax.swing.JSeparator IDNumberSep;
    private javax.swing.JLabel LastNameLab;
    private javax.swing.JSeparator LastNameSep;
    private javax.swing.JTextField LastNameTxt;
    private javax.swing.JButton LogOutBtn;
    private javax.swing.JButton MinimizeBtn;
    private javax.swing.JButton RegisterBtn;
    private javax.swing.JLabel RegisterLogoLab;
    private javax.swing.JPanel RegisterPnl;
    private javax.swing.JPanel RegisterSidePnl;
    private javax.swing.JLabel TelephoneLab;
    private javax.swing.JSeparator TelephoneSep;
    private javax.swing.JTextField TelephoneTxt;
    // End of variables declaration//GEN-END:variables
}
