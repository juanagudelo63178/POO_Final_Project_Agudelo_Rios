package domain;

/**
 * Represents a parking space.
 */
public class ParkingSpot {

    private int spotNumber;
    private boolean occupied;
    private Vehicle currentVehicle;

    public ParkingSpot(int spotNumber) {
        this.spotNumber = spotNumber;
        this.occupied = false;
    }

    public int getSpotNumber(){
        return spotNumber;
    }

    public boolean isOccupied() {
        return occupied;
    }

    public Vehicle getCurrentVehicle(){
        return currentVehicle;
    }

    public void parkVehicle(Vehicle vehicle) {
        currentVehicle = vehicle;
        occupied = true;
    }

    public void removeVehicle() {
        currentVehicle = null;
        occupied = false;
    }
}