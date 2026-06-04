package domain;

/**
 * Represents a parking lot report.
 */
public class Report {

    private int totalVehicles;
    private double totalRevenue;

    public Report(int totalVehicles, double totalRevenue) {
        this.totalVehicles = totalVehicles;
        this.totalRevenue = totalRevenue;
    }

    public int getTotalVehicles() {
        return totalVehicles;
    }

    public double getTotalRevenue() {
        return totalRevenue;
    }

    public String generateSummary() {
        return "Vehicles: " + totalVehicles +
               ", Revenue: " + totalRevenue;
    }
}