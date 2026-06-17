package domain;

import java.time.Duration;
import java.util.ArrayList;

/**
 * Represents a parking lot report.
 */
public class Report {

    private int totalVehicles;
    private double totalRevenue;
    private int totalTickets;
    private ArrayList<Ticket> tickets;
    private ArrayList<Employee> employees;

    public Report(int totalVehicles, double totalRevenue, int totalTickets, ArrayList<Ticket> tickets, ArrayList<Employee> employees) {
        this.totalVehicles = totalVehicles;
        this.totalRevenue = totalRevenue;
        this.totalTickets = totalTickets;
        this.tickets = tickets;
        this.employees = employees;
    }

    public int getTotalVehicles() {
        return totalVehicles;
    }

    public double getTotalRevenue() {
        return totalRevenue;
    }

    public int getTotalTickets() {
        return totalTickets;
    }

    public int getPaidTicketsCount() {
        int count = 0;
        for (Ticket ticket : tickets) {
            if (ticket.getPayment() != null) {
                count++;
            }
        }
        return count;
    }
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
    public double getRevenueByPaymentMethod(PaymentMethod method) {

        double revenue = 0;

        for (Ticket ticket : tickets) {

            if (ticket.getPayment() != null &&
                ticket.getPayment().getMethod() == method) {

                revenue += ticket.getFee();
            }
        }

        return revenue;
    }
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
            summary += "Most active employee: " + mostActive.getName()+ " (" + mostActive.getTicketsProcessed() + " tickets)\n";
        }
        Employee highestRevenue = getHighestRevenueEmployee();

        if (highestRevenue != null) {
            summary += "Highest revenue employee: " + highestRevenue.getName() + " ($" + highestRevenue.getRevenueGenerated() + ")\n";
        }

        return summary;
    }
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