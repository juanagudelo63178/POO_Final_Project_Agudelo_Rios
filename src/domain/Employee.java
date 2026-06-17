package domain;

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

    public double getAverageRevenuePerTicket() {

        if (ticketsProcessed == 0) {
            return 0;
        }

        return revenueGenerated / ticketsProcessed;
    }

    public String getPerformanceLevel() {

        if (ticketsProcessed >= 50) {
            return "Excellent";
        }

        if (ticketsProcessed >= 20) {
            return "Good";
        }

        return "Needs Improvement";
    }      
}