package domain;

import java.util.ArrayList;

/**
 * Represents the parking lot management system.
 */
public class ParkingLot {

    private ArrayList<Vehicle> vehicles;
    private ArrayList<Ticket> tickets;
    private ArrayList<ParkingSpot> parkingSpots;
    private ArrayList<Employee> employees;

    public ParkingLot() {
        vehicles = new ArrayList<>();
        tickets = new ArrayList<>();
        parkingSpots = new ArrayList<>();
        employees = new ArrayList<>();
    }

    public void registerEntry(Ticket ticket) {
        tickets.add(ticket);
    }

    public void registerExit(String plate) {

    }

    public Vehicle findVehicle(String plate) {
        return null;
    }

    public int getAvailableSpots() {
        return 0;
    }

    public Report generateReport() {
        return null;
    }

    public double predictOccupancy() {
        return 0;
    }

    public int getTotalTickets() {
        return tickets.size();
    }
}