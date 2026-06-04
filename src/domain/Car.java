package domain;

import java.time.LocalDateTime;

/**
 * Represents a car.
 */
public class Car extends Vehicle {

    private int numberOfDoors;

    public Car(String plate, String brand,
               LocalDateTime entryTime,
               int numberOfDoors) {
        super(plate, brand, entryTime);
        this.numberOfDoors = numberOfDoors;
    }

    public int getNumberOfDoors() {
        return numberOfDoors;
    }
    @Override
    public double getHourlyRate(){
        return 2.0;
    }
}