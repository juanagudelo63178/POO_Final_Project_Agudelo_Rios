package domain;

import java.io.Serializable;

/**
 * Represents a vehicle entering the parking lot.
 */
public abstract class Vehicle implements Serializable {

    private String plate;
    private String brand;
    private VehicleType type;

    /**
    * Creates a vehicle with the specified plate, brand, entry time, and type.
    */

    public Vehicle(String plate, String brand,VehicleType type) {
        this.plate = plate;
        this.brand = brand;
        this.type = type;

    }

    /**
    * Returns the license plate of this vehicle.
    */

    public String getPlate() {
        return plate;
    }

    /**
    * Returns the brand of this vehicle.
    */

    public String getBrand() {
        return brand;
    }

    /**
    * Returns the entry time of this vehicle.
    */


    /**
    * Returns the hourly rate applied to this type of vehicle.
    */
    
    public abstract double getHourlyRate();

    /**
    * Returns the type of this vehicle.
    */

    public VehicleType getType() {
        return type;
    }   

    /**
    * Sets the brand of this vehicle.
    */

    public void setBrand(String brand) {
        this.brand = brand;
    }
}