package domain;

/**
 * Represents a parking space.
 */
public class ParkingSpot {

    private int spotNumber;
    private boolean occupied;
    private Vehicle currentVehicle;//no hace falta poner vehiculo, el espacio existe 
    private int floor;
    private boolean disabledSpot;
    private boolean highDisplacementSpot;
    private boolean motorcycleSpot;

    public ParkingSpot(int spotNumber,int floor) {
        this.spotNumber = spotNumber;
        this.occupied = false;
        this.floor= floor;
        this.disabledSpot = false;
        this.highDisplacementSpot = false;
    }

    public int getSpotNumber(){
        return spotNumber;
    }
    public int getFloor() {
        return floor;
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
    public boolean isDisabledSpot() {
        return disabledSpot;
    }

    public void setDisabledSpot(boolean disabledSpot) {
        this.disabledSpot = disabledSpot;
    }

    public boolean isHighDisplacementSpot() {
        return highDisplacementSpot;
    }
    public boolean isMotorcycleSpot() {
        return motorcycleSpot;
    }

    public void setMotorcycleSpot(boolean motorcycleSpot) {
        this.motorcycleSpot = motorcycleSpot;
    }

    public void setHighDisplacementSpot(boolean highDisplacementSpot) {
        this.highDisplacementSpot = highDisplacementSpot;
    }
}