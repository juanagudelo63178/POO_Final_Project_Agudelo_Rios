package domain;

import java.time.LocalDateTime;

/**
 * Represents a vehicle entering the parking lot.
 */
public abstract class Vehicle {

    private String plate;
    private String brand;
    private LocalDateTime entryTime;
    private VehicleType type;

    public Vehicle(String plate, String brand, LocalDateTime entryTime, VehicleType type) {
        this.plate = plate;
        this.brand = brand;
        this.entryTime = entryTime;
        this.type = type;

    }

    public String getPlate() {
        return plate;
    }

    public String getBrand() {
        return brand;
    }

    public LocalDateTime getEntryTime() {
        return entryTime;
    }

    public abstract double getHourlyRate();

    public VehicleType getType() {
        return type;
    }   

}