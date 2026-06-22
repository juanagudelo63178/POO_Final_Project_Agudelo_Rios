package domain;

import java.io.Serializable;
import java.util.ArrayList;

/**
* Represents a floor within the parking lot and manages its parking spaces.
*/

public class ParkingFloor implements Serializable {

    private int floorNumber;
    private ArrayList<ParkingSpot> spots;
    private ParkingSpot[][] spotMatrix;

    /**
    * Creates a parking floor and initializes its parking spaces.
    */
    
    public ParkingFloor(int floorNumber) {

        this.floorNumber = floorNumber;
        this.spots = new ArrayList<>();

        this.spotMatrix = new ParkingSpot[5][5];
    }

    /**
    * Returns the number that identifies the parking floor.
    */
    
    public int getFloorNumber() {
        return floorNumber;
    }

    /**
    * Returns the list of parking spaces available on this floor.
    */

    public ArrayList<ParkingSpot> getSpots() {
        return spots;
    }

    /**
    * Adds a parking space to this floor.
    */

    public void addSpot(ParkingSpot spot) {
        spots.add(spot);
    }

    /**
    * Returns the total number of occupied parking spaces on this floor.
    */

    public int getOccupiedSpots() {

        int occupied = 0;

        for (ParkingSpot spot : spots) {
            if (spot.isOccupied()) {
                occupied++;
            }
        }

        return occupied;
    }

    /**
    * Returns the total number of available parking spaces on this floor.
    */

    public int getAvailableSpots() {

        int count = 0;

        for (int row = 0; row < 5; row++) {
            for (int col = 0; col < 5; col++) {

                if (!spotMatrix[row][col].isOccupied()) {
                    count++;
                }
            }
        }

        return count;
    }

    /**
    * Returns the matrix that represents the parking spaces on this floor.
    */

    public ParkingSpot[][] getSpotMatrix() {
        return spotMatrix;
    }

    /**
    * Assigns a parking space to a specific position in the floor matrix.
    */

    public void setSpot(int row, int column, ParkingSpot spot) {
        spotMatrix[row][column] = spot;
    }

    /**
    * Initializes the parking space matrix for this floor.
    */

    public void initializeMatrix() {

        int index = 0;

        for (int row = 0; row < 5; row++) {

            for (int col = 0; col < 5; col++) {

                if (index < spots.size()) {
                    spotMatrix[row][col] = spots.get(index);
                    index++;
                }
            }
        }
    }

    /**
    * Displays a visual representation of the available parking spaces on this floor.
    */

    public void showAvailableMatrix() {

        System.out.println("\n================================");

        if (floorNumber == 1 || floorNumber == 5) {
            System.out.println("FLOOR " + floorNumber + " - MOTORCYCLE PARKING");
        } else {
            System.out.println("FLOOR " + floorNumber + " - CAR PARKING");
        }

        System.out.println("================================");

        for (int row = 0; row < 5; row++) {

            
            for (int col = 0; col < 5; col++) {

                ParkingSpot spot = spotMatrix[row][col];

                if (spot.isOccupied()) {
                    System.out.print("[---]");
                } else if (spot.isHighDisplacementSpot()) {
                    System.out.print("[AVH]");
                } else if (spot.isDisabledSpot()) {
                    System.out.print("[AVD]");
                } else if (spot.isMotorcycleSpot()) {
                    System.out.print("[AVM]");
                } else {
                    System.out.print("[AVC]");
                }
            }

            System.out.println();

            
            for (int col = 0; col < 5; col++) {
                ParkingSpot spot = spotMatrix[row][col];
                System.out.printf(" %-4d", spot.getSpotNumber());
            }

            System.out.println("\n");
        }

        System.out.println("\nAvailable spots on floor: "
                + getAvailableSpots());
    }

    /**
    * Displays a visual representation of the occupied parking spaces on this floor.
    */

    public void showOccupiedMatrix() {

        System.out.println("\n================================");

        if (floorNumber == 1 || floorNumber == 5) {
            System.out.println("FLOOR " + floorNumber + " - MOTORCYCLE PARKING");
        } else {
            System.out.println("FLOOR " + floorNumber + " - CAR PARKING");
        }

        System.out.println("================================");

        for (int row = 0; row < 5; row++) {

            for (int col = 0; col < 5; col++) {

                ParkingSpot spot = spotMatrix[row][col];

                if (spot.isOccupied()) {

                    Vehicle vehicle = spot.getCurrentVehicle();

                    String type = vehicle instanceof Car ? "C" : "M";

                    System.out.print("[" +
                            vehicle.getPlate() +
                            "-" +
                            type +
                            "]");

                } else {

                    System.out.print("[     ]");
                }
            }

            System.out.println();

            for (int col = 0; col < 5; col++) {
                ParkingSpot spot = spotMatrix[row][col];
                System.out.printf(" %-7d", spot.getSpotNumber());
            }

            System.out.println("\n");
        }
    }   
    
}

