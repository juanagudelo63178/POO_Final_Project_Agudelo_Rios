package domain;

import java.time.LocalDateTime;

/**
 * Represents a motorcycle.
 */
public class Motorcycle extends Vehicle {

    private int engineCapacity;
    private double HOURLY_RATE = 1.0;//constante.

    public Motorcycle(String plate, String brand,LocalDateTime entryTime,int engineCapacity) {
        super(plate, brand, entryTime);
        this.engineCapacity = engineCapacity;
    }

    public int getEngineCapacity() { //darle mas logica al atributo
        return engineCapacity;
    }
    
    @Override
    public double getHourlyRate(){
        return this.HOURLY_RATE;
    }
}