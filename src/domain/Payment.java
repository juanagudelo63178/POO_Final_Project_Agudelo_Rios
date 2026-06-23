package domain;

import java.io.Serializable;

/**
 * Represents a payment.
 */

public class Payment implements Serializable {

    private double amount;
    private String method;

    /**
    * Creates a payment with the specified amount and payment method.
    */

    public Payment(double amount, String method) {

        this.amount = amount;
        this.method = method;
        
    }
    
    /**
    * Returns the payment amount.
    */

    public double getAmount() {
        return amount;
    }

    /**
    * Returns the payment method.
    */

    public String getMethod() {
        return method;
    }

    /**
    * Processes the payment transaction.
    */

    public boolean processPayment() {

        if (!isValid()) {
            return false;
        }

        System.out.println("Payment processed.");
        return true;
    }

    /**
    * Checks whether the payment information is valid.
    */

    public boolean isValid() {
        return amount >= 0;
    }

    /**
    * Generates a receipt for the completed payment.
    */

    public String generateReceipt() {

        return "===== PAYMENT RECEIPT =====\n" +
                "Method: " + method + "\n" +
                "Amount: $" + amount;
    }
}
