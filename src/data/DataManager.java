package data;

import domain.ParkingLot;
import java.io.*;

/**
* Manages the storage, retrieval, and persistence of parking system data.
*/

public class DataManager {

    /**
    * Name of the file used to store parking system data.
    */

    private static final String FILE_NAME = "parking.dat";

    /**
    * Saves the current state of the parking lot to persistent storage.
    */

    public void saveData(ParkingLot parkingLot) {

        try (
            ObjectOutputStream out =
                new ObjectOutputStream(
                    new FileOutputStream(FILE_NAME))
        ) {

            out.writeObject(parkingLot);
            System.out.println("Data saved successfully.");

        } catch (IOException e) {

            System.out.println("Error saving data.");
        }
    }

    /**
    * Loads and returns the parking lot data from persistent storage.
    */

    public ParkingLot loadData() {

        try (
            ObjectInputStream in =
                new ObjectInputStream(
                    new FileInputStream(FILE_NAME))
        ) {

            return (ParkingLot) in.readObject();

        } catch (Exception e) {

            return null;
        }
    }
}