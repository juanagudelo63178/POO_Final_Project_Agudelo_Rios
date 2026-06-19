package data;

import domain.ParkingLot;
import java.io.*;

public class DataManager {

    private static final String FILE_NAME = "parking.dat";

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