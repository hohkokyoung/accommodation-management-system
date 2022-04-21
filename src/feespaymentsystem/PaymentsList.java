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
public class PaymentsList {
    
    private String paymentID, paymentDate, paymentAmount;

    public void SetPaymentID(String paymentID) {
        this.paymentID = paymentID;
    }
    
    public void SetPaymentDate(String paymentDate) {
        this.paymentDate = paymentDate;
    }
    
    public void SetPaymentAmount(String paymentAmount) {
        this.paymentAmount = paymentAmount;
    }
    
    public String GetPaymentID() {
        return paymentID;
    }
    
    public String GetPaymentDate() {
        return paymentDate;
    }
    
    public String GetPaymentAmount() {
        return paymentAmount;
    }
    
}
