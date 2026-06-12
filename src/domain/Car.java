package domain;

import java.time.LocalDateTime;

/**
 * Represents a car.
 */
public class Car extends Vehicle {

    private boolean disabledVehicle;

    public Car(String plate, String brand,LocalDateTime entryTime,boolean disabledVehicle) {
        super(plate, brand, entryTime);
        this.disabledVehicle = disabledVehicle;
    }
    public boolean isDisabledVehicle() {
        return disabledVehicle;
    }

    @Override
    public double getHourlyRate() {

        if(disabledVehicle) {
            return 1.0;
        }
        return 2.0;
    }
}