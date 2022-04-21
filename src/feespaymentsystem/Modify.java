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
import static feespaymentsystem.Login.userRole;
import static feespaymentsystem.StaffResident.dataSelected;
import static feespaymentsystem.StaffResident.residentEmail;
import static feespaymentsystem.StaffResident.residentID;
import static feespaymentsystem.StaffResident.residentTelephone;
import static feespaymentsystem.UsedTelephoneEmail.lengthUsedInfoEmail;
import static feespaymentsystem.UsedTelephoneEmail.lengthUsedInfoTelephone;
import static feespaymentsystem.UsedTelephoneEmail.usedInfoResult;
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
public class Modify extends javax.swing.JFrame {
public static String telephone, email, currentPassword,
            residentTelephoneNumber, residentEmailAddress, usedTelephone, usedEmail;
public static String validEmail, validTelephone2_4, validTelephone6_9, validTelephone011, 
            validTelephone010, validPassword;
public int lengthUsedInfoCounter;
public static char[] oldPassword, newPassword;
private PreparedStatement updateResidentPST, residentPST, comparePasswordPST;
private ResultSet residentResult, comparePasswordResult;
public Boolean modifyVerification = false, passwordChangeCheck = false;
    /**
     * Creates new form Modify
     */
    public Modify() {
        initComponents();
        
        //Modify button as default button.
        SwingUtilities.getRootPane(ModifyBtn).setDefaultButton(ModifyBtn);
        
        UserRole(userRole);
    
        SetResidentText();
        
        SetPasswordText();
        
    }
    
    //Method to show/hide text fields based on user's role.
    private void UserRole(String userRole) {
   
        if("Staff".equals(userRole.trim())){
            OldPasswordLab.setVisible(false);
            NewPasswordLab.setVisible(false);
            OldPasswordTxt.setVisible(false);
            NewPasswordTxt.setVisible(false);
            OldPasswordSep.setVisible(false);
            NewPasswordSep.setVisible(false);
        } else {
            OldPasswordLab.setVisible(true);
            NewPasswordLab.setVisible(true);
            OldPasswordTxt.setVisible(true);
            NewPasswordTxt.setVisible(true);
            OldPasswordSep.setVisible(true);
            NewPasswordSep.setVisible(true);
        }
    }
    
    //Method to back previous form based on user's role.
    private void UserRoleBack(String userRole) {
        
        if("Staff".equals(userRole.trim())){    
            StaffResident staffresidentform = new StaffResident();
            staffresidentform.setVisible(true);
            staffresidentform.setLocationRelativeTo(null);
            this.setVisible(false);
        } else {
            Resident residentForm = new Resident();
            residentForm.setVisible(true);
            residentForm.setLocationRelativeTo(null);
            this.setVisible(false);      
        }
        
    }
 
    private void ResidentModify(String telephone, String email, 
        String oldPassword, String newPassword){
        
        //Take the static method from DatabaseConnection to establish database connection.
        DatabaseConnection.DBConnection();
        
        //Catch the database error.
        if (errorDetection == true) {
            Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, errorDetails);
            ErrorInputLab.setText("Database connection issue.");
            System.out.println("Error: " + errorDetails);
        } else {
       
            //Validation purposes.
            validEmail = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
		+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
            validTelephone2_4 = "^(01[2-4]-)?(\\d{7})$";
            validTelephone6_9 = "^(01[6-9]-)?(\\d{7})$";
            validTelephone011 = "^(011-)?(\\d{8})$";
            validTelephone010 = "^(010-)?(\\d{7})$";
            validPassword = "[a-zA-Z0-9]+";
            
            //Store the values of the text fields into variables.
            telephone = TelephoneTxt.getText();
            email = EmailTxt.getText();
            oldPassword = String.valueOf(OldPasswordTxt.getPassword());
            newPassword = String.valueOf(NewPasswordTxt.getPassword());
            
            //Check if the telephone and/or email field is empty.
            if(telephone.trim().isEmpty() || email.trim().isEmpty()) {
                
                ErrorInputLab.setText("Telephone and Email is empty.");
                
            } else if(telephone.trim().isEmpty()) {
                
                ErrorInputLab.setText("Telephone is empty.");
                
            } else if(email.trim().isEmpty()) {
                
                ErrorInputLab.setText("Email is empty.");
            
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
                    while (usedInfoResult.next()){

                        usedTelephone = usedInfoResult.getString("User_TelephoneNumber");
                        usedEmail = usedInfoResult.getString("User_EmailAddress");
                        
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
                        
                        if ("Staff".equals(userRole.trim())) {

                            ModifyResidentList modifyResidentList = new ModifyResidentList();
                            modifyResidentList.SetTelephone(telephone);
                            modifyResidentList.SetEmail(email);

                            String updateResidentSQL = "UPDATE Users "
                                                    + "SET User_TelephoneNumber = ?, "
                                                    + "User_EmailAddress = ? "
                                                    + "WHERE User_ID = ? ";
                            updateResidentPST = conn.prepareStatement(updateResidentSQL);
                            updateResidentPST.setString(1, modifyResidentList.GetTelephone());
                            updateResidentPST.setString(2, modifyResidentList.GetEmail());
                            updateResidentPST.setString(3, residentID);
                            updateResidentPST.executeUpdate();

                            modifyVerification = true;

                    } else if ("Resident".equals(userRole.trim())) {
                                                                   
                        String comparePasswordSQL = "SELECT * "
                        + "FROM Users"
                        + " WHERE User_ID = ?";
                        comparePasswordPST = conn.prepareStatement(comparePasswordSQL);
                        comparePasswordPST.setString(1, userID);
                        comparePasswordResult = comparePasswordPST.executeQuery();

                        while (comparePasswordResult.next()){
                            currentPassword = comparePasswordResult.getString("User_Password");
                        }

                        if (oldPassword.trim().isEmpty() && newPassword.trim().isEmpty()){

                            ModifyResidentList modifyResidentList = new ModifyResidentList();
                            modifyResidentList.SetTelephone(telephone);
                            modifyResidentList.SetEmail(email);

                            String updateResidentSQL = "UPDATE Users "
                                                    + "SET User_TelephoneNumber = ?, "
                                                    + "User_EmailAddress = ? "
                                                    + "WHERE User_ID = ? ";
                            updateResidentPST = conn.prepareStatement(updateResidentSQL);
                            updateResidentPST.setString(1, modifyResidentList.GetTelephone());
                            updateResidentPST.setString(2, modifyResidentList.GetEmail());
                            updateResidentPST.setString(3, userID);
                            updateResidentPST.executeUpdate();
                            
                            modifyVerification = true;

                        } else if(oldPassword.trim().isEmpty() || newPassword.trim().isEmpty()) {

                            ErrorInputLab.setText("Complete password fields to change password.");
                            modifyVerification = false;
                        
                        //Check if the password is invalid;
                        } else if (!(oldPassword.matches(validPassword)) && 
                                !(newPassword.matches(validPassword))) {
                            
                            ErrorInputLab.setText("Password is invalid.");

                        } else if(oldPassword.equals(newPassword)) {

                            ErrorInputLab.setText("Old and new password cannot be the same.");

                        } else if (newPassword.length() >= 13){
                            
                            ErrorInputLab.setText("Maximum password length is 12.");
                            
                        } else if (newPassword.length() < 4){
                            
                            ErrorInputLab.setText("Minimum password length is 4.");
                            
                        } else if (!(BCrypt.checkpw(oldPassword, currentPassword))){
                            
                            ErrorInputLab.setText("Old password is incorrect.");
                            
                        } else {
                            
                            ModifyResidentList modifyResidentList = new ModifyResidentList();
                            modifyResidentList.SetTelephone(telephone);
                            modifyResidentList.SetEmail(email);
                            modifyResidentList.SetNewPassword(newPassword);

                            String updateResidentSQL = "UPDATE Users "
                                                    + "SET User_TelephoneNumber = ?, "
                                                    + "User_EmailAddress = ?, "
                                                    + "User_Password = ? "
                                                    + "WHERE User_ID = ? ";
                            updateResidentPST = conn.prepareStatement(updateResidentSQL);
                            updateResidentPST.setString(1, modifyResidentList.GetTelephone());
                            updateResidentPST.setString(2, modifyResidentList.GetEmail());
                            updateResidentPST.setString(3, modifyResidentList.GetNewPassword());
                            updateResidentPST.setString(4, userID);
                            updateResidentPST.executeUpdate();

                            modifyVerification = true;

                        }
                    }
                }
            
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
    }
            

    //Method to set the text from the row selected from the previous form.
    private void SetResidentText() {
        
        if("Staff".equals(userRole.trim())){
            TelephoneTxt.setText(residentTelephone);
            EmailTxt.setText(residentEmail);
        } else if ("Resident".equals(userRole.trim())) {
            
            //Take the static method from DatabaseConnection to establish database connection.
            DatabaseConnection.DBConnection();
        
            //Catch the database error.
            if (errorDetection == true) {
                Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, errorDetails);
                ErrorInputLab.setText("Database connection issue.");
                System.out.println("Error: " + errorDetails);
            } else {
            
            try {

                String residentSQL = "SELECT User_TelephoneNumber, User_EmailAddress "
                                    + "FROM Users "
                                    + "WHERE User_ID = ?";
                residentPST = conn.prepareStatement(residentSQL);
                residentPST.setString(1, userID);
                residentResult = residentPST.executeQuery();  
                
                while (residentResult.next()){
                    
                    residentTelephoneNumber = residentResult.getString("User_TelephoneNumber");
                    residentEmailAddress = residentResult.getString("User_EmailAddress");
                    
                    TelephoneTxt.setText(residentTelephoneNumber);
                    EmailTxt.setText(residentEmailAddress);
                }
            
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
    }
    
    private void SetPasswordText(){
        
        OldPasswordTxt.setText("");
        NewPasswordTxt.setText("");
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        ModifySidePnl = new javax.swing.JPanel();
        ModifyLogoLab = new javax.swing.JLabel();
        LogOutBtn = new javax.swing.JButton();
        BackBtn = new javax.swing.JButton();
        ModifyPnl = new javax.swing.JPanel();
        ExitBtn = new javax.swing.JButton();
        MinimizeBtn = new javax.swing.JButton();
        ModifyBtn = new javax.swing.JButton();
        CancelBtn = new javax.swing.JButton();
        EmailSep = new javax.swing.JSeparator();
        EmailLab = new javax.swing.JLabel();
        EmailTxt = new javax.swing.JTextField();
        TelephoneSep = new javax.swing.JSeparator();
        TelephoneTxt = new javax.swing.JTextField();
        TelephoneLab = new javax.swing.JLabel();
        ErrorInputLab = new javax.swing.JLabel();
        OldPasswordLab = new javax.swing.JLabel();
        OldPasswordSep = new javax.swing.JSeparator();
        NewPasswordSep = new javax.swing.JSeparator();
        NewPasswordLab = new javax.swing.JLabel();
        NewPasswordTxt = new javax.swing.JPasswordField();
        OldPasswordTxt = new javax.swing.JPasswordField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        ModifySidePnl.setBackground(new java.awt.Color(0, 0, 0));
        ModifySidePnl.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        ModifyLogoLab.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/icons8-user-96 (2).png"))); // NOI18N
        ModifySidePnl.add(ModifyLogoLab, new org.netbeans.lib.awtextra.AbsoluteConstraints(62, 281, -1, -1));

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
        ModifySidePnl.add(LogOutBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 592, 220, 78));

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
        ModifySidePnl.add(BackBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 220, 73));

        getContentPane().add(ModifySidePnl, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 220, 670));

        ModifyPnl.setBackground(new java.awt.Color(255, 255, 255));
        ModifyPnl.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

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
        ModifyPnl.add(ExitBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 0, 90, 40));

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
        ModifyPnl.add(MinimizeBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 0, 90, 40));

        ModifyBtn.setBackground(new java.awt.Color(0, 0, 0));
        ModifyBtn.setFont(new java.awt.Font("Gill Sans MT", 0, 24)); // NOI18N
        ModifyBtn.setForeground(new java.awt.Color(255, 255, 255));
        ModifyBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/icons8-user-64 (2).png"))); // NOI18N
        ModifyBtn.setText("Modify");
        ModifyBtn.setBorder(null);
        ModifyBtn.setFocusPainted(false);
        ModifyBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ModifyBtnActionPerformed(evt);
            }
        });
        ModifyPnl.add(ModifyBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 550, 180, -1));

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
        ModifyPnl.add(CancelBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 550, 180, -1));

        EmailSep.setBackground(new java.awt.Color(0, 0, 0));
        EmailSep.setForeground(new java.awt.Color(0, 0, 0));
        ModifyPnl.add(EmailSep, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 290, 240, -1));

        EmailLab.setFont(new java.awt.Font("Gill Sans MT", 0, 24)); // NOI18N
        EmailLab.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        EmailLab.setText("Email:");
        ModifyPnl.add(EmailLab, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 260, -1, -1));

        EmailTxt.setFont(new java.awt.Font("Gill Sans MT", 0, 24)); // NOI18N
        EmailTxt.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        EmailTxt.setBorder(null);
        EmailTxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EmailTxtActionPerformed(evt);
            }
        });
        ModifyPnl.add(EmailTxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 260, 220, 30));

        TelephoneSep.setBackground(new java.awt.Color(0, 0, 0));
        TelephoneSep.setForeground(new java.awt.Color(0, 0, 0));
        ModifyPnl.add(TelephoneSep, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 220, 240, -1));

        TelephoneTxt.setFont(new java.awt.Font("Gill Sans MT", 0, 24)); // NOI18N
        TelephoneTxt.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        TelephoneTxt.setBorder(null);
        TelephoneTxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TelephoneTxtActionPerformed(evt);
            }
        });
        ModifyPnl.add(TelephoneTxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 190, 220, 30));

        TelephoneLab.setFont(new java.awt.Font("Gill Sans MT", 0, 24)); // NOI18N
        TelephoneLab.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        TelephoneLab.setText("Telephone:");
        ModifyPnl.add(TelephoneLab, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 190, -1, -1));

        ErrorInputLab.setFont(new java.awt.Font("Gill Sans MT", 0, 24)); // NOI18N
        ErrorInputLab.setForeground(new java.awt.Color(255, 0, 0));
        ErrorInputLab.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ModifyPnl.add(ErrorInputLab, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 510, 480, 30));

        OldPasswordLab.setFont(new java.awt.Font("Gill Sans MT", 0, 24)); // NOI18N
        OldPasswordLab.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        OldPasswordLab.setText("Old Password:");
        ModifyPnl.add(OldPasswordLab, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 330, -1, -1));

        OldPasswordSep.setBackground(new java.awt.Color(0, 0, 0));
        OldPasswordSep.setForeground(new java.awt.Color(0, 0, 0));
        ModifyPnl.add(OldPasswordSep, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 360, 240, -1));

        NewPasswordSep.setBackground(new java.awt.Color(0, 0, 0));
        NewPasswordSep.setForeground(new java.awt.Color(0, 0, 0));
        ModifyPnl.add(NewPasswordSep, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 430, 240, -1));

        NewPasswordLab.setFont(new java.awt.Font("Gill Sans MT", 0, 24)); // NOI18N
        NewPasswordLab.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        NewPasswordLab.setText("New Password:");
        ModifyPnl.add(NewPasswordLab, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 400, -1, -1));

        NewPasswordTxt.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        NewPasswordTxt.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        NewPasswordTxt.setText("Password");
        NewPasswordTxt.setBorder(null);
        NewPasswordTxt.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                NewPasswordTxtFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                NewPasswordTxtFocusLost(evt);
            }
        });
        ModifyPnl.add(NewPasswordTxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 400, 220, 30));

        OldPasswordTxt.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        OldPasswordTxt.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        OldPasswordTxt.setText("Password");
        OldPasswordTxt.setBorder(null);
        OldPasswordTxt.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                OldPasswordTxtFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                OldPasswordTxtFocusLost(evt);
            }
        });
        ModifyPnl.add(OldPasswordTxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 330, 220, 30));

        getContentPane().add(ModifyPnl, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 0, 850, 670));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void LogOutBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LogOutBtnActionPerformed
        // TODO add your handling code here:

        dataSelected = false;
        Login loginform = new Login();
        loginform.setVisible(true);
        loginform.setLocationRelativeTo(null);

        this.setVisible(false);
    }//GEN-LAST:event_LogOutBtnActionPerformed

    private void BackBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BackBtnActionPerformed
        // TODO add your handling code here:
        dataSelected = false;
        UserRoleBack(userRole);
    }//GEN-LAST:event_BackBtnActionPerformed

    private void ExitBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ExitBtnActionPerformed
        // TODO add your handling code here:

        System.exit(0);
    }//GEN-LAST:event_ExitBtnActionPerformed

    private void MinimizeBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MinimizeBtnActionPerformed
        // TODO add your handling code here:

        this.setState(Modify.ICONIFIED);
        
    }//GEN-LAST:event_MinimizeBtnActionPerformed

    private void ModifyBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ModifyBtnActionPerformed
        // TODO add your handling code here:
        
        telephone = TelephoneTxt.getText();
        email = EmailTxt.getText();
        oldPassword = OldPasswordTxt.getPassword();
        newPassword = NewPasswordTxt.getPassword();

        ResidentModify(telephone, email, String.valueOf(oldPassword), String.valueOf(newPassword));

        if (modifyVerification == true && "Staff".equals(userRole.trim())){
            dataSelected = false;
            StaffResident staffresidentform = new StaffResident();
            staffresidentform.setVisible(true);
            staffresidentform.setLocationRelativeTo(null);
            this.setVisible(false);
        } else if (modifyVerification == true && "Resident".equals(userRole.trim())){
            Resident residentform = new Resident();
            residentform.setVisible(true);
            residentform.setLocationRelativeTo(null);
            this.setVisible(false);
        }
        
    }//GEN-LAST:event_ModifyBtnActionPerformed

    private void CancelBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CancelBtnActionPerformed
        // TODO add your handling code here:
        
        dataSelected = false;
        UserRoleBack(userRole);
        
    }//GEN-LAST:event_CancelBtnActionPerformed

    private void EmailTxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EmailTxtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_EmailTxtActionPerformed

    private void TelephoneTxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TelephoneTxtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TelephoneTxtActionPerformed

    private void NewPasswordTxtFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_NewPasswordTxtFocusGained
        // TODO add your handling code here:

    }//GEN-LAST:event_NewPasswordTxtFocusGained

    private void NewPasswordTxtFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_NewPasswordTxtFocusLost
        // TODO add your handling code here:

    }//GEN-LAST:event_NewPasswordTxtFocusLost

    private void OldPasswordTxtFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_OldPasswordTxtFocusGained
        // TODO add your handling code here:

    }//GEN-LAST:event_OldPasswordTxtFocusGained

    private void OldPasswordTxtFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_OldPasswordTxtFocusLost
        // TODO add your handling code here:
        
    }//GEN-LAST:event_OldPasswordTxtFocusLost

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
            java.util.logging.Logger.getLogger(Modify.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
    //</editor-fold>
    
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new Modify().setVisible(true);
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
    private javax.swing.JButton LogOutBtn;
    private javax.swing.JButton MinimizeBtn;
    private javax.swing.JButton ModifyBtn;
    private javax.swing.JLabel ModifyLogoLab;
    private javax.swing.JPanel ModifyPnl;
    private javax.swing.JPanel ModifySidePnl;
    private javax.swing.JLabel NewPasswordLab;
    private javax.swing.JSeparator NewPasswordSep;
    private javax.swing.JPasswordField NewPasswordTxt;
    private javax.swing.JLabel OldPasswordLab;
    private javax.swing.JSeparator OldPasswordSep;
    private javax.swing.JPasswordField OldPasswordTxt;
    private javax.swing.JLabel TelephoneLab;
    private javax.swing.JSeparator TelephoneSep;
    private javax.swing.JTextField TelephoneTxt;
    // End of variables declaration//GEN-END:variables
}
