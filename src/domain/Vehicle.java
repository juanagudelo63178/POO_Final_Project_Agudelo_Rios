package domain;

import java.time.LocalDateTime;

/**
 * Represents a vehicle entering the parking lot.
 */
public abstract class Vehicle {

    private String plate;
    private String brand;
    private LocalDateTime entryTime;

    public Vehicle(String plate, String brand, LocalDateTime entryTime) {
        this.plate = plate;
        this.brand = brand;
        this.entryTime = entryTime;
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
}