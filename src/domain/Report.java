package domain;

import java.io.Serializable;
import java.time.Duration;
import java.util.ArrayList;
/**
* Represents a parking lot report.
*/
public class Report implements Serializable {

    private int totalVehicles;
    private double totalRevenue;
    private int totalTickets;
    private ArrayList<Ticket> tickets;
    private ArrayList<Employee> employees;

    /**
    * Creates a report with parking lot statistics and collected data.
    */

    public Report(int totalVehicles, double totalRevenue, int totalTickets, ArrayList<Ticket> tickets, ArrayList<Employee> employees) {
        this.totalVehicles = totalVehicles;
        this.totalRevenue = totalRevenue;
        this.totalTickets = totalTickets;
        this.tickets = tickets;
        this.employees = employees;
    }

    /**
    * Returns the total number of vehicles included in the report.
    */

    public int getTotalVehicles() {
        return totalVehicles;
    }

    /**
    * Returns the total revenue included in the report.
    */

    public double getTotalRevenue() {
        return totalRevenue;
    }

    /**
    * Returns the total number of tickets included in the report.
    */

    public int getTotalTickets() {
        return totalTickets;
    }

    /**
    * Returns the number of paid tickets included in the report.
    */

    public int getPaidTicketsCount() {
        int count = 0;
        for (Ticket ticket : tickets) {
            if (ticket.getPayment() != null) {
                count++;
            }
        }
        return count;
    }

    /**
    * Returns the average revenue generated per ticket.
    */

    public double getAverageRevenuePerTicket() {

        if (tickets.isEmpty()) {
            return 0;
        }

        double totalRevenue = 0;

        for (Ticket ticket : tickets) {
            totalRevenue += ticket.getFee();
        }

        return totalRevenue / tickets.size();
    }

    /**
    * Returns the total revenue generated through a specific payment method.
    */

    public double getRevenueByPaymentMethod(String method) {

        double revenue = 0;

        for (Ticket ticket : tickets) {

            if (ticket.getPayment() != null &&
                ticket.getPayment().getMethod().equalsIgnoreCase(method)) {

                revenue += ticket.getFee();
            }
        }

        return revenue;
    }

    /**
    * Returns the ticket that generated the highest revenue.
    */

    public Ticket getHighestRevenueTicket() {

        Ticket highest = null;

        for (Ticket ticket : tickets) {

            if (highest == null ||
                ticket.getFee() > highest.getFee()) {

                highest = ticket;
            }
        }

        return highest;
    }

    /**
    * Returns the employee who processed the highest number of tickets.
    */

    public Employee getMostActiveEmployee() {

        Employee mostActive = null;

        for (Employee employee : employees) {

            if (mostActive == null ||
                employee.getTicketsProcessed() >
                mostActive.getTicketsProcessed()) {

                mostActive = employee;
            }
        }

        return mostActive;
    }

    /**
    * Generates a summary of the report data.
    */

    public String generateSummary() {

        Employee mostActive = getMostActiveEmployee();

        String summary =
        "===== PARKING REPORT =====\n" +
        "Vehicles currently parked: " + totalVehicles + "\n" +
        "Total tickets: " + totalTickets + "\n" +
        "Paid tickets: " + getPaidTicketsCount() + "\n" +
        "Average revenue per ticket: $" + getAverageRevenuePerTicket() + "\n" +
        "Total revenue: $" + totalRevenue + "\n"+
        "Most common vehicle type: " + getMostCommonVehicleType() + "\n"+
        "Longest stay vehicle: " + getLongestStayInfo() + "\n"+
        "Highest revenue vehicle: " + getHighestRevenueVehicleInfo() + "\n"+
        "Peak hour: " + getPeakHour() + "\n";

        if (mostActive != null) {

            summary += "Most active employee: " +
                    mostActive.getName() +
                    " (" +
                    mostActive.getTicketsProcessed() +
                    " tickets)\n";

            summary += "Employee performance: " +
                    mostActive.getPerformanceLevel() +
                    "\n";

            summary += "Average revenue per ticket: $" +
                    String.format("%.2f",
                    mostActive.getAverageRevenuePerTicket()) +
                    "\n";
        }
        Employee highestRevenue = getHighestRevenueEmployee();

        if (highestRevenue != null) {

            summary += "Highest revenue employee: " +
                    highestRevenue.getName() +
                    " ($" +
                    highestRevenue.getRevenueGenerated() +
                    ")\n";

            summary += "Performance level: " +
                    highestRevenue.getPerformanceLevel() +
                    "\n";
        }

        return summary;
    }

    /**
    * Returns the employee who generated the highest total revenue.
    */

    public Employee getHighestRevenueEmployee() {

        Employee highest = null;

        for (Employee employee : employees) {

            if (highest == null ||
                employee.getRevenueGenerated() >
                highest.getRevenueGenerated()) {

                highest = employee;
            }
        }

        return highest;
    }

    /**
    * Returns the most common type of vehicle included in the report.
    */

    public String getMostCommonVehicleType() {

        int cars = 0;
        int motorcycles = 0;

        for (Ticket ticket : tickets) {

            if (ticket.getVehicle() instanceof Car) {
                cars++;
            } else if (ticket.getVehicle() instanceof Motorcycle) {
                motorcycles++;
            }
        }

        if (cars > motorcycles) {
            return "Car (" + cars + ")";
        } else if (motorcycles > cars) {
            return "Motorcycle (" + motorcycles + ")";
        }

        return "Tie (" + cars + " each)";
    }

    /**
    * Returns the ticket associated with the longest parking stay.
    */

    public Ticket getLongestStayTicket() {

        Ticket longest = null;
        long maxMinutes = -1;

        for (Ticket ticket : tickets) {

            if (ticket.getExitTime() != null) {

                long minutes = Duration.between(
                        ticket.getEntryTime(),
                        ticket.getExitTime()
                ).toMinutes();

                if (minutes > maxMinutes) {
                    maxMinutes = minutes;
                    longest = ticket;
                }
            }
        }

        return longest;
    }

    /**
    * Returns information about the ticket with the longest parking stay.
    */

    public String getLongestStayInfo() {

        Ticket longest = getLongestStayTicket();

        if (longest == null) {
            return "No completed tickets";
        }

        long minutes = Duration.between(
                longest.getEntryTime(),
                longest.getExitTime()
        ).toMinutes();

        return longest.getVehicle().getPlate() +
                " (" + minutes + " minutes)";
    }

    /**
    * Returns the vehicle that generated the highest total revenue.
    */

    public Vehicle getHighestRevenueVehicle() {

        Vehicle highestVehicle = null;
        double highestRevenue = -1;

        for (Ticket ticket : tickets) {

            if (ticket.getFee() > highestRevenue) {
                highestRevenue = ticket.getFee();
                highestVehicle = ticket.getVehicle();
            }
        }

        return highestVehicle;
    }

    /**
    * Returns information about the vehicle that generated the highest total revenue.
    */

    public String getHighestRevenueVehicleInfo() {

        Ticket highestTicket = null;

        for (Ticket ticket : tickets) {

            if (highestTicket == null ||
                ticket.getFee() > highestTicket.getFee()) {

                highestTicket = ticket;
            }
        }

        if (highestTicket == null) {
            return "No tickets available";
        }

        return highestTicket.getVehicle().getPlate()
                + " ($" + highestTicket.getFee() + ")";
    }

    /**
    * Returns the hour with the highest parking activity.
    */

    public String getPeakHour() {

        int[] hours = new int[24];

        for (Ticket ticket : tickets) {

            int hour = ticket.getEntryTime().getHour();
            hours[hour]++;
        }

        int maxHour = 0;

        for (int i = 1; i < 24; i++) {

            if (hours[i] > hours[maxHour]) {
                maxHour = i;
            }
        }

        return String.format("%02d:00 - %02d:59 (%d entries)",
                maxHour,
                maxHour,
                hours[maxHour]);
    }
}