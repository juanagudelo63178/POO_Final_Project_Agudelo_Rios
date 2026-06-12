package domain;

import java.time.LocalDateTime;

/**
 * Represents a motorcycle.
 */
public class Motorcycle extends Vehicle {

    private boolean highDisplacement;
    private double HOURLY_RATE = 1.0;//constante.

    public Motorcycle(String plate, String brand,LocalDateTime entryTime,boolean highDisplacement) {
        super(plate, brand, entryTime);
        this.highDisplacement = highDisplacement;
    }
    public boolean isHighDisplacement() {
        return highDisplacement;
    } 
    
    @Override
    public double getHourlyRate() {

      if(highDisplacement) {
        return 2.5;
      }
        return 1.5;
    }
}