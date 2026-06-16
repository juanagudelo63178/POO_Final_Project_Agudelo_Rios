package domain;

/**
 * Represents a payment.
 */
public class Payment {

    private double amount;
    private String method;

    public Payment(double amount, String method) {
        this.amount = amount;
        this.method = method;
        
    }
    

    public double getAmount() {
        return amount;
    }

    public String getMethod() {
        return method;
    }

    public boolean processPayment() {

        if (!isValid()) {
            return false;
        }

        System.out.println("Payment processed.");
        return true;
    }

    public boolean isValid() {
        return amount >= 0;
    }

    public String generateReceipt() {

        return "===== PAYMENT RECEIPT =====\n" +
                "Method: " + method + "\n" +
                "Amount: $" + amount;
    }
}
