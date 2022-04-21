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
import static feespaymentsystem.Register.userRegistrationInitiation;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author user
 */
public class StaffResident extends javax.swing.JFrame {
public static String search;
public static Boolean dataSelected = false, forceDelete = false;
private PreparedStatement residentsPST;
private ResultSet residentsResult;
private static int selectedResidentRow;
public static String residentID, residentTelephone, residentEmail, roomID, userRole = "";

DefaultTableModel residentsTable;
        
    /**
     * Creates new form StaffResident
     */
    public StaffResident() {
        initComponents();
        
        //Initiate the reading of residents' details into the table.
        ShowResidents();
        
        CenterResidentsData();
        
        SelectOneRowOnly();

        userRegistrationInitiation = false;
        
        forceDelete = false;
    }
    
    private ArrayList<ResidentsList> StoreResidentList(){
        
        //Create an array list.
        ArrayList<ResidentsList> residentList = new ArrayList<>();
         
        //Take the static method from DatabaseConnection to establish database connection.
        DatabaseConnection.DBConnection();
        
        //Catch the database error.
        if (errorDetection == true) {
            Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, errorDetails);
            System.out.println("Error: " + errorDetails);
        } else {
        
            try {
            
            //SQL to select residents' details.
            String residentsSQL = "SELECT * "
                                + "FROM Users "
                                + "JOIN Rooms ON Users.User_ID = Rooms.User_ID "
                                + "WHERE User_Role = 'Resident' ORDER BY Users.User_ID ASC";
            residentsPST = conn.prepareStatement(residentsSQL);
            residentsResult = residentsPST.executeQuery();
            
            //Check if a row of resident's data is found.
            while (residentsResult.next()) {
                                
                OutstandingFees outstandingFeess = new OutstandingFees();
                outstandingFeess.ResidentOutstandingFees(residentsResult.getString("User_ID"));
                
                //Store the data retrieved into each set method.
                ResidentsList residentsList = new ResidentsList();  
                residentsList.SetResidentID(residentsResult.getString("User_ID"));
                residentsList.SetResidentFirstName(residentsResult.getString("User_FirstName"));
                residentsList.SetResidentLastName(residentsResult.getString("User_LastName"));
                residentsList.SetResidentTelephoneNumber(residentsResult.getString("User_TelephoneNumber"));
                residentsList.SetResidentEmailAddress(residentsResult.getString("User_EmailAddress"));
                residentsList.SetResidentResidentRoomID(residentsResult.getString("Room_ID"));
                residentsList.SetOutstandingFees(String.valueOf(outstandingFees));
                
                //Store the entire data of a resident as one data.
                residentList.add(residentsList);
            }
            
           //Catch SQL execution error. 
            } catch (SQLException ex) {
            System.out.println("Error: " + ex);
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
        
        //Return all the results found.
        return residentList;
    }
    
    private void ShowResidents() {
        
        //Assumptions: Access the list in the StoreResidentList() method.
        ArrayList<ResidentsList> list = StoreResidentList();
        
        //Define a table model.
        residentsTable = (DefaultTableModel)ResidentTab.getModel();
        
        //Define the columns of the table.
        Object[] residentsTableRow = new Object[7];
        
        //Loop all the resident's data.
        //Loop all the residents as well.
        for (int counter=0;counter<list.size();counter++){
            residentsTableRow[0]=list.get(counter).GetResidentID();
            residentsTableRow[1]=list.get(counter).GetResidentFirstName();
            residentsTableRow[2]=list.get(counter).GetResidentLastName();
            residentsTableRow[3]=list.get(counter).GetResidentTelephoneNumber();
            residentsTableRow[4]=list.get(counter).GetResidentEmailAddress();
            residentsTableRow[5]=list.get(counter).GetResidentRoomID();
            residentsTableRow[6]=list.get(counter).GetOustandingFees();
            
            //Add the resident's data as a row in the table.
            residentsTable.addRow(residentsTableRow);
        }   
    }
    
    private void CenterResidentsData() {
                
        //Center the data in the table.
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        ResidentTab.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        ResidentTab.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
        ResidentTab.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
        ResidentTab.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);
        ResidentTab.getColumnModel().getColumn(4).setCellRenderer(centerRenderer);
        ResidentTab.getColumnModel().getColumn(5).setCellRenderer(centerRenderer);
        ResidentTab.getColumnModel().getColumn(6).setCellRenderer(centerRenderer);
        
    }
    
    private void SelectOneRowOnly() {
        //DefaultTableModel residentsTable = (DefaultTableModel)ResidentTab.getModel();
        selectedResidentRow = ResidentTab.getSelectedRow();
        ResidentTab.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }
    
    //Method to take the telephone and email value from selected row.
    private void ResidentData(){
        
        residentsTable = (DefaultTableModel)ResidentTab.getModel();
        selectedResidentRow = ResidentTab.convertRowIndexToModel(ResidentTab.getSelectedRow());

            //Check if a row is selected.
            if (selectedResidentRow != -1) {

                residentID = residentsTable.getValueAt(selectedResidentRow, 0).toString();
                residentTelephone = residentsTable.getValueAt(selectedResidentRow, 3)
                        .toString();
                residentEmail = residentsTable.getValueAt(selectedResidentRow, 4)
                        .toString();
                roomID = residentsTable.getValueAt(selectedResidentRow, 5).toString();

                //Indicator of a row is selected.
                dataSelected = true;

            } else {
                ErrorInputLab.setText("Please select a resident.");
            }
        
    }
    
    //Method to clear selected text.
    private void ClearResidentData(){
        
        residentTelephone = "";
        residentEmail = "";
        
    }
    
    private void SelectData() {
        
        search = ResidentSearchTxt.getText();
        SearchFilter(search);

    }
    
    private void SearchFilter(String searchText) {
        TableRowSorter<DefaultTableModel> rowSorter = new TableRowSorter<>(residentsTable);
        ResidentTab.setRowSorter(rowSorter);
        
        rowSorter.setRowFilter(RowFilter.regexFilter(searchText));
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        StaffResidentSidePnl = new javax.swing.JPanel();
        StaffLogoLab = new javax.swing.JLabel();
        LogOutBtn = new javax.swing.JButton();
        BackBtn = new javax.swing.JButton();
        StaffResidentPnl = new javax.swing.JPanel();
        ExitBtn = new javax.swing.JButton();
        MinimizeBtn = new javax.swing.JButton();
        ResidentSearchSep = new javax.swing.JSeparator();
        ResidentSearchTxt = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        ResidentTab = new javax.swing.JTable();
        DeleteBtn = new javax.swing.JButton();
        RegisterBtn = new javax.swing.JButton();
        ModifyBtn = new javax.swing.JButton();
        ErrorInputLab = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        StaffResidentSidePnl.setBackground(new java.awt.Color(0, 0, 0));
        StaffResidentSidePnl.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        StaffLogoLab.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/icons8-person-96.png"))); // NOI18N
        StaffResidentSidePnl.add(StaffLogoLab, new org.netbeans.lib.awtextra.AbsoluteConstraints(62, 281, -1, -1));

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
        StaffResidentSidePnl.add(LogOutBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 592, 220, 78));

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
        StaffResidentSidePnl.add(BackBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 220, 73));

        getContentPane().add(StaffResidentSidePnl, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 220, 670));

        StaffResidentPnl.setBackground(new java.awt.Color(255, 255, 255));
        StaffResidentPnl.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

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
        StaffResidentPnl.add(ExitBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 0, 90, 40));

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
        StaffResidentPnl.add(MinimizeBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 0, 90, 40));

        ResidentSearchSep.setBackground(new java.awt.Color(0, 0, 0));
        ResidentSearchSep.setForeground(new java.awt.Color(0, 0, 0));
        StaffResidentPnl.add(ResidentSearchSep, new org.netbeans.lib.awtextra.AbsoluteConstraints(45, 80, 360, -1));

        ResidentSearchTxt.setFont(new java.awt.Font("Gill Sans MT", 0, 24)); // NOI18N
        ResidentSearchTxt.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        ResidentSearchTxt.setText("Search");
        ResidentSearchTxt.setBorder(null);
        ResidentSearchTxt.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                ResidentSearchTxtFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                ResidentSearchTxtFocusLost(evt);
            }
        });
        ResidentSearchTxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ResidentSearchTxtActionPerformed(evt);
            }
        });
        ResidentSearchTxt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                ResidentSearchTxtKeyReleased(evt);
            }
        });
        StaffResidentPnl.add(ResidentSearchTxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 40, 220, 30));

        ResidentTab.setFont(new java.awt.Font("Gill Sans MT", 0, 20)); // NOI18N
        ResidentTab.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "User ID", "First Name", "Last Name", "Telephone Number", "Email Address", "Room ID", "Fees"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        ResidentTab.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        ResidentTab.setFocusable(false);
        ResidentTab.setGridColor(new java.awt.Color(255, 255, 255));
        ResidentTab.setRowHeight(50);
        ResidentTab.setRowMargin(0);
        ResidentTab.setSelectionBackground(new java.awt.Color(204, 204, 204));
        ResidentTab.setSelectionForeground(new java.awt.Color(0, 0, 0));
        ResidentTab.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(ResidentTab);
        if (ResidentTab.getColumnModel().getColumnCount() > 0) {
            ResidentTab.getColumnModel().getColumn(0).setPreferredWidth(20);
            ResidentTab.getColumnModel().getColumn(1).setPreferredWidth(5);
            ResidentTab.getColumnModel().getColumn(2).setPreferredWidth(7);
            ResidentTab.getColumnModel().getColumn(5).setPreferredWidth(8);
            ResidentTab.getColumnModel().getColumn(6).setPreferredWidth(5);
        }

        StaffResidentPnl.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 130, 750, 380));

        DeleteBtn.setBackground(new java.awt.Color(0, 0, 0));
        DeleteBtn.setFont(new java.awt.Font("Gill Sans MT", 0, 24)); // NOI18N
        DeleteBtn.setForeground(new java.awt.Color(255, 255, 255));
        DeleteBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/icons8-person-64 (3).png"))); // NOI18N
        DeleteBtn.setText("Delete");
        DeleteBtn.setBorder(null);
        DeleteBtn.setFocusPainted(false);
        DeleteBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DeleteBtnActionPerformed(evt);
            }
        });
        StaffResidentPnl.add(DeleteBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 560, 180, -1));

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
        StaffResidentPnl.add(RegisterBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 20, 180, -1));

        ModifyBtn.setBackground(new java.awt.Color(0, 0, 0));
        ModifyBtn.setFont(new java.awt.Font("Gill Sans MT", 0, 24)); // NOI18N
        ModifyBtn.setForeground(new java.awt.Color(255, 255, 255));
        ModifyBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/icons8-person-64 (2).png"))); // NOI18N
        ModifyBtn.setText("Modify");
        ModifyBtn.setBorder(null);
        ModifyBtn.setFocusPainted(false);
        ModifyBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ModifyBtnActionPerformed(evt);
            }
        });
        StaffResidentPnl.add(ModifyBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 560, 180, -1));

        ErrorInputLab.setFont(new java.awt.Font("Gill Sans MT", 0, 24)); // NOI18N
        ErrorInputLab.setForeground(new java.awt.Color(255, 0, 0));
        ErrorInputLab.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        StaffResidentPnl.add(ErrorInputLab, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 520, 500, 30));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/icons8-user-64.png"))); // NOI18N
        StaffResidentPnl.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 10, -1, -1));

        getContentPane().add(StaffResidentPnl, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 0, 850, 670));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void LogOutBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LogOutBtnActionPerformed
        // TODO add your handling code here:

        ClearResidentData();
        dataSelected = false;
        
        Login loginform = new Login();
        loginform.setVisible(true);
        loginform.setLocationRelativeTo(null);

        this.setVisible(false);
    }//GEN-LAST:event_LogOutBtnActionPerformed

    private void BackBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BackBtnActionPerformed
        // TODO add your handling code here:

        ClearResidentData();
        dataSelected = false;
        
        Staff staffform = new Staff();
        staffform.setVisible(true);
        staffform.setLocationRelativeTo(null);

        this.setVisible(false);

    }//GEN-LAST:event_BackBtnActionPerformed

    private void ExitBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ExitBtnActionPerformed
        // TODO add your handling code here:

        System.exit(0);
    }//GEN-LAST:event_ExitBtnActionPerformed

    private void MinimizeBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MinimizeBtnActionPerformed
        // TODO add your handling code here:

        this.setState(StaffResident.ICONIFIED);

    }//GEN-LAST:event_MinimizeBtnActionPerformed

    private void ResidentSearchTxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ResidentSearchTxtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ResidentSearchTxtActionPerformed

    private void DeleteBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DeleteBtnActionPerformed
        // TODO add your handling code here:
        
        ResidentData();

        if(dataSelected == true) {
                            
            OutstandingFees outstandingFeesForm = new OutstandingFees();
            outstandingFeesForm.ResidentOutstandingFees(residentID);
            
            if (outstandingFees > 0){
                
                forceDelete = true;
                
                Decision decisionForm = new Decision();
                decisionForm.setVisible(true);
                decisionForm.setLocationRelativeTo(null);
                this.setVisible(false);
                
            } else {
                Decision decisionForm = new Decision();
                decisionForm.setVisible(true);
                decisionForm.setLocationRelativeTo(null);
                this.setVisible(false);
            }
        }
        
    }//GEN-LAST:event_DeleteBtnActionPerformed

    private void RegisterBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RegisterBtnActionPerformed
        // TODO add your handling code here:
        
        ClearResidentData();
        dataSelected = false;
        
        Register registerform = new Register();
        registerform.setVisible(true);
        registerform.setLocationRelativeTo(null);
        this.setVisible(false);

    }//GEN-LAST:event_RegisterBtnActionPerformed

    private void ModifyBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ModifyBtnActionPerformed
        // TODO add your handling code here:      
        
        ResidentData();
        
        if(dataSelected == true) {
            Modify modifyform = new Modify();
            modifyform.setVisible(true);
            modifyform.setLocationRelativeTo(null);
            this.setVisible(false);
        }
        
    }//GEN-LAST:event_ModifyBtnActionPerformed

    private void ResidentSearchTxtFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_ResidentSearchTxtFocusGained
        // TODO add your handling code here:
        search = ResidentSearchTxt.getText();
        
        if (search.equals("Search")) {
            ResidentSearchTxt.setText("");
        } else {
            
        }
        
    }//GEN-LAST:event_ResidentSearchTxtFocusGained

    private void ResidentSearchTxtFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_ResidentSearchTxtFocusLost
        // TODO add your handling code here:
        search = ResidentSearchTxt.getText();
        
        if (search.equals("")) {
            ResidentSearchTxt.setText("Search");
        } else {
            
        }
        
    }//GEN-LAST:event_ResidentSearchTxtFocusLost

    private void ResidentSearchTxtKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ResidentSearchTxtKeyReleased
        // TODO add your handling code here:
        
        SelectData();
    }//GEN-LAST:event_ResidentSearchTxtKeyReleased

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
            java.util.logging.Logger.getLogger(StaffResident.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
    //</editor-fold>
    
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new StaffResident().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BackBtn;
    private javax.swing.JButton DeleteBtn;
    private javax.swing.JLabel ErrorInputLab;
    private javax.swing.JButton ExitBtn;
    private javax.swing.JButton LogOutBtn;
    private javax.swing.JButton MinimizeBtn;
    private javax.swing.JButton ModifyBtn;
    private javax.swing.JButton RegisterBtn;
    private javax.swing.JSeparator ResidentSearchSep;
    private javax.swing.JTextField ResidentSearchTxt;
    private javax.swing.JTable ResidentTab;
    private javax.swing.JLabel StaffLogoLab;
    private javax.swing.JPanel StaffResidentPnl;
    private javax.swing.JPanel StaffResidentSidePnl;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
