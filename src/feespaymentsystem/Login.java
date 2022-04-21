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
import javax.swing.SwingUtilities;

/**
 *
 * @author user
 * 
 * 
 */
   
public class Login extends javax.swing.JFrame {
    
//Variables declaration.
public static String userRole, userUsername, userPassword, userID;
public static String username;
public static char[] password;
public static String textField;
public static String validUsername, validPassword;
private PreparedStatement loginPST;
private ResultSet loginResult;

    /**
     * Creates new form Login
     */
    public Login() {
        initComponents();

        UsernameTxt.requestFocus();

        
        //Login Button by default.
        SwingUtilities.getRootPane(LoginBtn).setDefaultButton(LoginBtn);
        
    }
    
    //Create a method for login.
    private void UserLogin(String username, String password){
        
        //Take the static method from DatabaseConnection to establish database connection.
        DatabaseConnection.DBConnection();
        
        //Catch the database error.
        if (errorDetection == true) {
            Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, errorDetails);
            ErrorUsernamePasswordLab.setText("Database connection issue.");
            System.out.println("Error: " + errorDetails);
        } else {
            
            //Validation purposes.
            validUsername = "[A-Z0-9]+";
            validPassword = "[a-zA-Z0-9]+";
            
            //Store the values of the text fields into variables.
            username = UsernameTxt.getText();
            password = String.valueOf(PasswordTxt.getPassword());
        
        //Check if the username and/or password fields is empty.
        if (username.trim().isEmpty() && password.trim().isEmpty()){
            ErrorUsernamePasswordLab.setText("Username & Password is empty.");
        } else if (username.trim().isEmpty()) {
            ErrorUsernamePasswordLab.setText("Username is empty.");
        } else if (password.trim().isEmpty()) {
            ErrorUsernamePasswordLab.setText("Password is empty.");
            
        //Check if the username and/or password is invalid.
        } else if (!username.matches(validUsername) 
                && !password.matches(validPassword)){
            ErrorUsernamePasswordLab.setText("Username & Password is invalid.");
        } else if (!username.matches(validUsername)) {
            ErrorUsernamePasswordLab.setText("Username is invalid.");
        } else if (!password.matches(validPassword)) {
            ErrorUsernamePasswordLab.setText("Password is invalid.");
        } else {
            
            //Clear Error field if no error.
            ErrorUsernamePasswordLab.setText("");
            
        try {
            
             //SQL Statement to be executed. (Login Purposes)
            String loginSQL = "SELECT * "
                            + "FROM Users "
                            + "WHERE User_ID = ?";
            loginPST = conn.prepareStatement(loginSQL);
            loginPST.setString(1, username);
            //loginPST.setString(2, password);
            loginResult = loginPST.executeQuery();  
                  
            //Check if a row of result is found.
             if (loginResult.next()){
                 
                //Case sensitive validation.
                userUsername = loginResult.getString("User_ID");
                userPassword = loginResult.getString("User_Password");       
                
                //Check the user's role.
                userRole = loginResult.getString("User_Role");
                
                //Modification purposes.
                userID = loginResult.getString("User_ID");
                
                //Validate case sensitive of user and password.
                if (userUsername.equals(username) && (BCrypt.checkpw(password, userPassword))
                //Check if the user's role is Staff.
                        && "Staff".equals(userRole.trim())){
                    
                    //Close this form.
                    this.setVisible(false);
                    
                    //Open the Staff form.
                    Staff staffForm = new Staff();
                    staffForm.setVisible(true);
                    staffForm.setLocationRelativeTo(null);
                              
                } else if (userUsername.equals(username) 
                        && (BCrypt.checkpw(password, userPassword)) 
                        
                //Check if the user's role is Resident.
                        && "Resident".equals(userRole.trim())) {

                    //Close this form.
                    this.setVisible(false);
                    
                    //Open the Resident form.
                    Resident residentForm = new Resident();
                    residentForm.setVisible(true);
                    residentForm.setLocationRelativeTo(null);
            
                            
                } else {
                    
                    //Show message of wrong username & password.
                    ErrorUsernamePasswordLab.setText("Wrong Username & Password.");
                }
            } else {
                 
                 //Show message of wrong username & password.
                ErrorUsernamePasswordLab.setText("Wrong Username & Password.");
          }      
            
        //Catch SQL execution error.
        } catch (SQLException ex) {
            ErrorUsernamePasswordLab.setText("SQL execution error.");
            System.out.println(ex);
        } finally {
            
            //Close the connection.
            DatabaseConnection.CloseDBConnection();
            
            if (closeErrorDetection == true) {
                Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, errorDetails);
                ErrorUsernamePasswordLab.setText("Database connection cannot be closed.");
                System.out.println("Error: " + closeErrorDetails);
            }
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

        LoginBarPnl = new javax.swing.JPanel();
        LogInLogoLab = new javax.swing.JLabel();
        LoginPnl = new javax.swing.JPanel();
        LogInLab = new javax.swing.JLabel();
        UsernameSep = new javax.swing.JSeparator();
        PasswordSep = new javax.swing.JSeparator();
        LoginBtn = new javax.swing.JButton();
        PasswordLab = new javax.swing.JLabel();
        UsernameLab = new javax.swing.JLabel();
        ExitBtn = new javax.swing.JButton();
        MinimizeBtn = new javax.swing.JButton();
        PasswordTxt = new javax.swing.JPasswordField();
        UsernameTxt = new javax.swing.JTextField();
        ErrorUsernamePasswordLab = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        LoginBarPnl.setBackground(new java.awt.Color(0, 0, 0));
        LoginBarPnl.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        LogInLogoLab.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/icons8-administrator-male-96 (4).png"))); // NOI18N
        LoginBarPnl.add(LogInLogoLab, new org.netbeans.lib.awtextra.AbsoluteConstraints(61, 284, -1, -1));

        getContentPane().add(LoginBarPnl, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 220, 670));

        LoginPnl.setBackground(new java.awt.Color(255, 255, 255));
        LoginPnl.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                UsernameTxtFocusLost(evt);
            }
        });
        LoginPnl.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        LogInLab.setFont(new java.awt.Font("Gill Sans MT", 0, 60)); // NOI18N
        LogInLab.setText("LOG IN");
        LoginPnl.add(LogInLab, new org.netbeans.lib.awtextra.AbsoluteConstraints(319, 132, -1, -1));

        UsernameSep.setBackground(new java.awt.Color(0, 0, 0));
        UsernameSep.setForeground(new java.awt.Color(0, 0, 0));
        LoginPnl.add(UsernameSep, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 310, 290, -1));

        PasswordSep.setBackground(new java.awt.Color(0, 0, 0));
        PasswordSep.setForeground(new java.awt.Color(0, 0, 0));
        LoginPnl.add(PasswordSep, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 400, 290, -1));

        LoginBtn.setBackground(new java.awt.Color(0, 0, 0));
        LoginBtn.setFont(new java.awt.Font("Gill Sans MT", 0, 24)); // NOI18N
        LoginBtn.setForeground(new java.awt.Color(255, 255, 255));
        LoginBtn.setText("Login");
        LoginBtn.setBorder(null);
        LoginBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        LoginBtn.setFocusPainted(false);
        LoginBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LoginBtnActionPerformed(evt);
            }
        });
        LoginPnl.add(LoginBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 440, 200, 50));

        PasswordLab.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/icons8-lock-36.png"))); // NOI18N
        LoginPnl.add(PasswordLab, new org.netbeans.lib.awtextra.AbsoluteConstraints(277, 360, -1, -1));

        UsernameLab.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/icons8-administrator-male-48 (2).png"))); // NOI18N
        LoginPnl.add(UsernameLab, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 260, -1, -1));

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
        LoginPnl.add(ExitBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 0, 90, 40));

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
        LoginPnl.add(MinimizeBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 0, 90, 40));

        PasswordTxt.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        PasswordTxt.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        PasswordTxt.setText("Password");
        PasswordTxt.setBorder(null);
        PasswordTxt.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                PasswordTxtFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                PasswordTxtFocusLost(evt);
            }
        });
        LoginPnl.add(PasswordTxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 360, 220, 30));

        UsernameTxt.setFont(new java.awt.Font("Gill Sans MT", 0, 24)); // NOI18N
        UsernameTxt.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        UsernameTxt.setText("Username");
        UsernameTxt.setBorder(null);
        UsernameTxt.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                UsernameTxtFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                UsernameTxtFocusLost(evt);
            }
        });
        LoginPnl.add(UsernameTxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 270, 220, 30));

        ErrorUsernamePasswordLab.setFont(new java.awt.Font("Gill Sans MT", 0, 24)); // NOI18N
        ErrorUsernamePasswordLab.setForeground(new java.awt.Color(255, 0, 0));
        ErrorUsernamePasswordLab.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        LoginPnl.add(ErrorUsernamePasswordLab, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 520, 360, 30));

        getContentPane().add(LoginPnl, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 0, 850, 670));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    
    private void LoginBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LoginBtnActionPerformed

        username = UsernameTxt.getText();
        password = PasswordTxt.getPassword();
        UserLogin(username, String.valueOf(password));

    }//GEN-LAST:event_LoginBtnActionPerformed

    private void ExitBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ExitBtnActionPerformed
        // TODO add your handling code here:

        System.exit(0);
    }//GEN-LAST:event_ExitBtnActionPerformed

    private void MinimizeBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MinimizeBtnActionPerformed
        // TODO add your handling code here:
        
        this.setState(Login.ICONIFIED);
    }//GEN-LAST:event_MinimizeBtnActionPerformed

    private void UsernameTxtFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_UsernameTxtFocusLost
        // TODO add your handling code here:

        username = UsernameTxt.getText();
        
        if (username.equals("")) {
            UsernameTxt.setText("Username");
        } else {
            
        }

    }//GEN-LAST:event_UsernameTxtFocusLost

    private void UsernameTxtFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_UsernameTxtFocusGained
        // TODO add your handling code here:
        
        username = UsernameTxt.getText();
        
        if (username.equals("Username")) {
            UsernameTxt.setText("");
        } else {
            
        }
       
    }//GEN-LAST:event_UsernameTxtFocusGained

    private void PasswordTxtFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_PasswordTxtFocusGained
        // TODO add your handling code here:
        password = PasswordTxt.getPassword();
        
        if (String.valueOf(password).equals("Password")) {
            PasswordTxt.setText("");
        }
        
    }//GEN-LAST:event_PasswordTxtFocusGained

    private void PasswordTxtFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_PasswordTxtFocusLost
        // TODO add your handling code here:
       
        password = PasswordTxt.getPassword();

        if (String.valueOf(password).equals("")) {
            PasswordTxt.setText("Password");
        }
        
    }//GEN-LAST:event_PasswordTxtFocusLost
 
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
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
    //</editor-fold>
    
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new Login().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel ErrorUsernamePasswordLab;
    private javax.swing.JButton ExitBtn;
    private javax.swing.JLabel LogInLab;
    private javax.swing.JLabel LogInLogoLab;
    private javax.swing.JPanel LoginBarPnl;
    private javax.swing.JButton LoginBtn;
    private javax.swing.JPanel LoginPnl;
    private javax.swing.JButton MinimizeBtn;
    private javax.swing.JLabel PasswordLab;
    private javax.swing.JSeparator PasswordSep;
    private javax.swing.JPasswordField PasswordTxt;
    private javax.swing.JLabel UsernameLab;
    private javax.swing.JSeparator UsernameSep;
    private javax.swing.JTextField UsernameTxt;
    // End of variables declaration//GEN-END:variables
}
