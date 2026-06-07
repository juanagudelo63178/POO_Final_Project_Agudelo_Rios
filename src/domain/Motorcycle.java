package domain;

import java.time.LocalDateTime;

/**
 * Represents a motorcycle.
 */
public class Motorcycle extends Vehicle {

    private int engineCapacity;

    public Motorcycle(String plate, String brand,LocalDateTime entryTime,int engineCapacity) {
        super(plate, brand, entryTime);
        this.engineCapacity = engineCapacity;
    }

    public int getEngineCapacity() {
        return engineCapacity;
    }
    
    @Override
    public double getHourlyRate(){
        return 1.0;
    }
}