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

    public void processPayment() {
        System.out.println("Payment processed.");
    }
}
