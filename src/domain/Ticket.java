package domain;

import java.time.LocalDateTime;

/**
 * Represents a parking ticket.
 */
public class Ticket {

    private String id;
    private LocalDateTime entryTime;
    private LocalDateTime exitTime;
    private double fee;

    private Vehicle vehicle;
    private ParkingSpot parkingSpot;
    private Employee employee;
    private Payment payment;

    public Ticket(String id,
                  Vehicle vehicle,
                  ParkingSpot parkingSpot,
                  Employee employee) {

        this.id = id;
        this.vehicle = vehicle;
        this.parkingSpot = parkingSpot;
        this.employee = employee;
        this.entryTime = LocalDateTime.now();
    }

    public double getFee() {
        return fee;
    }

    public void calculateFee() {
        fee = 10.0;
    }
}