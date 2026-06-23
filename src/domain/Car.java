package domain;

import java.io.Serializable;
import java.time.LocalDateTime;
/**
 * Represents a car.
 */
public class Car extends Vehicle implements Serializable {

    private boolean disabledVehicle;

    /**
    * Creates a car with its basic information and accessibility status.
    */
    public Car(String plate, String brand,LocalDateTime entryTime,boolean disabledVehicle) {
        super(plate, brand, VehicleType.CAR);
        this.disabledVehicle = disabledVehicle;
    }

    /**
    * Checks whether the car is assigned as a disabled vehicle.
    */

    public boolean isDisabledVehicle() {
        return disabledVehicle;
    }

    @Override
 
    /**
     * Returns the hourly parking rate for a car.
    */

    public double getHourlyRate() {

        if(disabledVehicle) {
            return 1.0;
        }
        return 2.0;
    }
}