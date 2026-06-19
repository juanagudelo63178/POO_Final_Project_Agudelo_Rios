package domain;

import java.io.Serializable;
import java.util.ArrayList;

public class ParkingFloor implements Serializable {

    private int floorNumber;
    private ArrayList<ParkingSpot> spots;
    private ParkingSpot[][] spotMatrix;

    public ParkingFloor(int floorNumber) {

        this.floorNumber = floorNumber;
        this.spots = new ArrayList<>();

        this.spotMatrix = new ParkingSpot[5][5];
    }

    public int getFloorNumber() {
        return floorNumber;
    }

    public ArrayList<ParkingSpot> getSpots() {
        return spots;
    }

    public void addSpot(ParkingSpot spot) {
        spots.add(spot);
    }
    public int getOccupiedSpots() {

        int occupied = 0;

        for (ParkingSpot spot : spots) {
            if (spot.isOccupied()) {
                occupied++;
            }
        }

        return occupied;
    }
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
    public ParkingSpot[][] getSpotMatrix() {
        return spotMatrix;
    }
    public void setSpot(int row, int column, ParkingSpot spot) {
        spotMatrix[row][column] = spot;
    }
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
        }

        System.out.println("\nAvailable spots on floor: "
                + getAvailableSpots());
    }
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
        }
    }
    
}

