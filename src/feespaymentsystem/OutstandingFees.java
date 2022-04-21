/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package feespaymentsystem;

import static feespaymentsystem.DatabaseConnection.conn;
import static feespaymentsystem.DatabaseConnection.errorDetails;
import static feespaymentsystem.DatabaseConnection.errorDetection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.IntStream;

/**
 *
 * @author user
 */
public class OutstandingFees {
    public static String firstDate;
    public static PreparedStatement outstandingDatePST, countBalancePST, lengthCountBalancePST;
    public static ResultSet outstandingDateResult, countBalanceResult, lengthCountBalanceResult;
    public static int outstandingFees, monthPastAmount, totalMonthPastAmount, lengthCountBalance,
                    lengthCountBalanceCounter = 0, lengthCountBalanceSum;
    public static long diffMonth;
    public LocalDate currentDateFormat;
    public static String totalPastAmount, totalPastAmountFormat, totalPastAmountFinalFormat;
    

        public void ResidentOutstandingFees(String userID) {
        
        //Take the static method from DatabaseConnection to establish database connection.
        DatabaseConnection.DBConnection();
        
        //Catch the database error.
        if (errorDetection == true) {
            Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, errorDetails);
            System.out.println("Error: " + errorDetails);
        } else {
            
            try {
                        
            //SQL execution to be executed. (Outstanding Fees Purposes)
            String outstandingDateSQL = "SELECT TOP(1) Payment_Date "
                                    + "FROM Payments "
                                    + "WHERE User_ID = ? "
                                    + "ORDER BY Payment_Date ASC";
            outstandingDatePST = conn.prepareStatement(outstandingDateSQL);
            outstandingDatePST.setString(1, userID);
            outstandingDateResult = outstandingDatePST.executeQuery();
            
            //SQL execution to be executed. (Outstanding Fees Purposes)      
            String countBalanceSQL = "SELECT * FROM Payments WHERE User_ID = ?";
            countBalancePST = conn.prepareStatement(countBalanceSQL);
            countBalancePST.setString(1, userID);
            countBalanceResult = countBalancePST.executeQuery();
            
            //Another prepare statement for the same query to be executed. (Total array length purposes)
            lengthCountBalancePST = conn.prepareStatement(countBalanceSQL);
            lengthCountBalancePST.setString(1, userID);
            lengthCountBalanceResult = lengthCountBalancePST.executeQuery();
            
            //Loop to count the size of the array.
            while (lengthCountBalanceResult.next()){
                lengthCountBalance++;
            }
            
            //Array to store all the months payment.
            int[] lengthCountBalanceArray = new int[lengthCountBalance];
            
            while (countBalanceResult.next()){
              
                //Store the retrieved amount in a variable.
                totalPastAmount = countBalanceResult.getString("Payment_Amount");     
                
                //Format the retrieved amount into a countable format.
                totalPastAmountFormat = totalPastAmount.replace("RM ", ""); 
                totalPastAmountFinalFormat = totalPastAmountFormat.replace(".00", "");
                monthPastAmount = Integer.parseInt(totalPastAmountFinalFormat);
                
                //Change the amounts into months.
                totalMonthPastAmount = monthPastAmount / 200;
                
                //Store the month into an array;
                lengthCountBalanceArray[lengthCountBalanceCounter] = totalMonthPastAmount;
                
                //Increment the counter of the array.
                lengthCountBalanceCounter++;
                
            }
            
            //Count the total months of rent paid.
            lengthCountBalanceSum = IntStream.of(lengthCountBalanceArray).sum();
            
            //Execute the query.
            if (outstandingDateResult.next()) {
                
                //Get the first date the resident paid for his unit.
                firstDate = outstandingDateResult.getString("Payment_Date");
                
                    //Format the date where it ignores the timezone.
                    LocalDate firstDateFormat = LocalDate.parse(firstDate);
                    currentDateFormat = LocalDate.now();
                    
                    //Differentiate the month between two dates.
                    diffMonth = ChronoUnit.MONTHS.between(firstDateFormat, currentDateFormat);

                    diffMonth = diffMonth - lengthCountBalanceSum + 1;

                    //Store the results into a integer variable.
                    outstandingFees = (int) (diffMonth * 200);               
                
            } else {
                outstandingFees = 200;
            }
            
            //Catch SQL execution error. 
            } catch (SQLException ex) {
            System.out.println("Error: " + ex);
            } 
        
        }
        
    }
    
}
