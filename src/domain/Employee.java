package domain;

/**
 * Represents an employee.
 */
public class Employee {

    private String id;
    private String name;
    private int ticketsProcessed;
    private double revenueGenerated;

    public Employee(String id, String name) {
        this.id = id;
        this.name = name;
        this.ticketsProcessed = 0;
        this.revenueGenerated = 0;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void registerTicket() {
        ticketsProcessed++;
    }

    public void addRevenue(double amount) {
        revenueGenerated += amount;
    }

    public int getTicketsProcessed() {
        return ticketsProcessed;
    }

    public double getRevenueGenerated() {
        return revenueGenerated;
    }
}