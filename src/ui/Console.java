package ui;

import domain.Car;
import domain.Employee;
import domain.ParkingLot;
import domain.ParkingSpot;
import domain.Ticket;
import java.util.Scanner;

public class Console {

    private ParkingLot parkingLot;

    private Scanner scanner;

    private int ticketCounter;

    public Console() {
        parkingLot = new ParkingLot();
        scanner = new Scanner(System.in);
        ticketCounter = 1;
        parkingLot.addParkingSpot(new ParkingSpot(1));
        parkingLot.addParkingSpot(new ParkingSpot(2));
        parkingLot.addParkingSpot(new ParkingSpot(3));
        parkingLot.addParkingSpot(new ParkingSpot(4));
        parkingLot.addParkingSpot(new ParkingSpot(5));
        parkingLot.addEmployee(
        new Employee("EMP001", "Juan" )
);
    }

    public void start() {

        int option;

        do { 
            System.out.println("=================================");
            System.out.println("      PARKING LOT SYSTEM");
            System.out.println("=================================");
            System.out.println("1. Register Vehicle Entry");
            System.out.println("2. Register Vehicle Exit");
            System.out.println("3. Show Available Spots");
            System.out.println("4. Generate Report");
            System.out.println("0. Exit");

            System.out.print("Select an option: ");
            option = scanner.nextInt();

            switch(option) {

                case 1:
                    registerVehicleEntry();
                    break;

                case 2:
                    registerVehicleExit();
                    break;

                case 3:
                    showAvailableSpots();
                    break;

                case 4:
                    generateReport();
                    break;

                case 0:
                    System.out.println("Closing system...");
                    break;

                default:
                    System.out.println("Invalid option");
            }
        }while(option !=0);    
    }

    private void registerVehicleEntry() {
        System.out.print("Enter plate: ");

        String plate = scanner.next();

        Car car = new Car(plate,"toyota", java.time.LocalDateTime.now(), 4);

        ParkingSpot spot = parkingLot.getAvailableSpot();

        if (spot == null) {
            System.out.println("No available parking spots.");
            return;
        }

        Employee employee = new Employee("EMP001","JUAN");

        spot.parkVehicle(car);
        
        Ticket ticket = new Ticket("T" + ticketCounter, car, spot, employee);
        
        parkingLot.registerEntry(ticket);

        ticketCounter++;

        System.out.println("Vehicle registered successfully.");

    }

    private void registerVehicleExit() {
        System.out.print("Enter plate: ");

        String plate = scanner.next();

        parkingLot.registerExit(plate);

        System.out.println("Vehicle exit registered.");
    }

    private void showAvailableSpots() {

      int available = parkingLot.getAvailableSpots();

      System.out.println("Available spots: " + available);

    }

    private void generateReport() {

      System.out.println("Report selected");

    }
}   