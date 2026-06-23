package domain;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Represents a motorcycle.
 */
public class Motorcycle extends Vehicle implements Serializable {

    private boolean highDisplacement;
    private double HOURLY_RATE = 1.0;//constante.

    /**
    * Creates a motorcycle with its basic information and displacement category.
    */

    public Motorcycle(String plate, String brand,LocalDateTime entryTime,boolean highDisplacement) {
        super(plate, brand, VehicleType.MOTORCYCLE);
        this.highDisplacement = highDisplacement;
    }

    /**
    * Checks whether the motorcycle is classified as high displacement.
    */

    public boolean isHighDisplacement() {
        return highDisplacement;
    } 
    
    @Override

    /**
    * Returns the hourly parking rate for a motorcycle.
    */

    public double getHourlyRate() {

      if(highDisplacement) {
        return 2.5;
      }
        return 1.5;
    }
}