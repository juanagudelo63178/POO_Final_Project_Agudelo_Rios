package domain;

/**
 * Represents a parking space.
 */
public class ParkingSpot {

    private int spotNumber;
    private boolean occupied;
    private Vehicle currentVehicle;//no hace falta poner vehiculo, el espacio existe 
    private int floor;

    public ParkingSpot(int spotNumber,int floor) {
        this.spotNumber = spotNumber;
        this.occupied = false;
        this.floor= floor;
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
}