package domain;

import java.io.Serializable;

/**
* Represents a parking space.
*/

public class ParkingSpot implements Serializable {

    private int spotNumber;
    private boolean occupied;
    private Vehicle currentVehicle; 
    private int floor;
    private boolean disabledSpot;
    private boolean highDisplacementSpot;
    private boolean motorcycleSpot;

    /**
    * Creates a parking spot with the specified number and floor.
    */

    public ParkingSpot(int spotNumber,int floor) {
        this.spotNumber = spotNumber;
        this.occupied = false;
        this.floor= floor;
        this.disabledSpot = false;
        this.highDisplacementSpot = false;
    }

    /**
    * Returns the parking spot number.
    */

    public int getSpotNumber(){
        return spotNumber;
    }

    /**
 * Returns the floor where the parking spot is located.
 */

    public int getFloor() {
        return floor;
    }

    /**
    * Checks whether the parking spot is currently occupied.
    */

    public boolean isOccupied() {
        return occupied;
    }

    /**
    * Returns the vehicle currently occupying the parking spot.
    */    

    public Vehicle getCurrentVehicle(){
        return currentVehicle;
    }

    /**
    * Assigns a vehicle to this parking spot.
    */

    public void parkVehicle(Vehicle vehicle) {
        currentVehicle = vehicle;
        occupied = true;
    }

    /**
    * Removes the vehicle currently assigned to this parking spot.
    */

    public void removeVehicle() {
        currentVehicle = null;
        occupied = false;
    }

    /**
    * Checks whether this parking spot is designated for disabled vehicles.
    */

    public boolean isDisabledSpot() {
        return disabledSpot;
    }

    /**
    * Sets whether this parking spot is designated for disabled vehicles.
    */

    public void setDisabledSpot(boolean disabledSpot) {
        this.disabledSpot = disabledSpot;
    }

    /**
    * Checks whether this parking spot is designated for high-displacement motorcycles.
    */

    public boolean isHighDisplacementSpot() {
        return highDisplacementSpot;
    }

    /**
    * Checks whether this parking spot is designated for motorcycles.
    */

    public boolean isMotorcycleSpot() {
        return motorcycleSpot;
    }

    /**
    * Sets whether this parking spot is designated for motorcycles.
    */

    public void setMotorcycleSpot(boolean motorcycleSpot) {
        this.motorcycleSpot = motorcycleSpot;
    }

    /**
    * Sets whether this parking spot is designated for high-displacement motorcycles.
    */

    public void setHighDisplacementSpot(boolean highDisplacementSpot) {
        this.highDisplacementSpot = highDisplacementSpot;
    }
}