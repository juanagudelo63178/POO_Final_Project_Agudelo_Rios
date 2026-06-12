package ui;

import domain.Car;
import domain.Employee;
import domain.Motorcycle;
import domain.ParkingLot;
import domain.ParkingSpot;
import domain.Report;
import domain.Ticket;
import domain.Vehicle;
import java.util.Scanner;

public class Console {

    private ParkingLot parkingLot;

    private Scanner scanner;

    private int ticketCounter;

    public Console() {
        parkingLot = new ParkingLot();
        scanner = new Scanner(System.in);
        ticketCounter = 1;
        parkingLot.addParkingSpot(new ParkingSpot(1, 1));
        parkingLot.addParkingSpot(new ParkingSpot(2, 1));
        parkingLot.addParkingSpot(new ParkingSpot(3, 2));
        parkingLot.addParkingSpot(new ParkingSpot(4, 2));
        parkingLot.addParkingSpot(new ParkingSpot(5, 3));
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
            System.out.println("5. Search Vehicle");
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
                case 5:
                    searchVehicle();
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
        System.out.println("Vehicle type:");
        System.out.println("1. Car");
        System.out.println("2. Motorcycle");
        System.out.print("Select: ");

        int vehicleType = scanner.nextInt();

        Vehicle vehicle;
        if(vehicleType == 1) {

            System.out.print("Disabled vehicle? (true/false): ");
            boolean disabled = scanner.nextBoolean();

            vehicle = new Car(
                    plate,
                    "Toyota",
                    java.time.LocalDateTime.now(),
                    disabled
            );
        } else {

            System.out.print("High displacement motorcycle? (true/false): ");
            boolean highDisplacement = scanner.nextBoolean();

            vehicle = new Motorcycle(
            plate,
            "Yamaha",
            java.time.LocalDateTime.now(),
            highDisplacement
        );
        }
        ParkingSpot spot = parkingLot.getAvailableSpot();

        if (spot == null) {
            System.out.println("No available parking spots.");
            return;
        }

        Employee employee = new Employee("EMP001","JUAN");

        spot.parkVehicle(vehicle);
        
        Ticket ticket = new Ticket("T" + ticketCounter, vehicle, spot, employee);
        
        parkingLot.registerEntry(ticket);

        System.out.println("Vehicle assigned to spot "+ spot.getSpotNumber()+ " on floor "+ spot.getFloor());

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

        Report report = parkingLot.generateReport();
        
        System.out.println("===== PARKING REPORT =====");

        System.out.println("Total vehicles: " + report.getTotalVehicles());

        System.out.println("Total revenue: " + report.getTotalRevenue());

        System.out.println("Total tickets: " + parkingLot.getTotalTickets());

        System.out.println("Floor 1 occupied spots: "    + parkingLot.getOccupiedSpotsByFloor(1));

        System.out.println("Floor 2 occupied spots: " + parkingLot.getOccupiedSpotsByFloor(2));

        System.out.println("Floor 3 occupied spots: " + parkingLot.getOccupiedSpotsByFloor(3));

        System.out.println("Available spots: " + parkingLot.getAvailableSpots());
    }
    private void searchVehicle() {

        System.out.print("Enter plate: ");
        String plate = scanner.next();

        Vehicle vehicle = parkingLot.findVehicle(plate);

        if(vehicle != null) {
            System.out.println("Vehicle found.");
        } else {
            System.out.println("Vehicle not found.");
        }
    }
}   