package ui;
import data.DataManager;
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

    private DataManager dataManager;

    public Console() {
        parkingLot = new ParkingLot();
        scanner = new Scanner(System.in);
        ticketCounter = 1;
        dataManager = new DataManager();
        ParkingSpot spot1 = new ParkingSpot(1, 1);
        spot1.setDisabledSpot(true);

        ParkingSpot spot2 = new ParkingSpot(2, 1);

        ParkingSpot spot3 = new ParkingSpot(3, 2);
        spot3.setHighDisplacementSpot(true);

        ParkingSpot spot4 = new ParkingSpot(4, 2);

        ParkingSpot spot5 = new ParkingSpot(5, 3);
        spot5.setMotorcycleSpot(true);

        parkingLot.addParkingSpot(spot1);
        parkingLot.addParkingSpot(spot2);
        parkingLot.addParkingSpot(spot3);
        parkingLot.addParkingSpot(spot4);
        parkingLot.addParkingSpot(spot5);
        parkingLot.addEmployee(new Employee("EMP001", "Juan" ));
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
            System.out.println("6. Show Vehicles Parked");
            System.out.println("7. Show Vehicles By Floor");
            System.out.println("8. Show Occupied Spots");
            System.out.println("9. Show Employees");
            System.out.println("10. Predict Occupancy");
            System.out.println("11. Search Ticket");
            System.out.println("12. Show All Tickets");
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
                case 6:
                    showParkedVehicles();
                    break;
                case 7:
                    showVehiclesByFloor();
                    break;
                case 8:
                    showOccupiedSpots();
                    break;
                case 9:
                    showEmployees();
                    break;
                case 10:
                    predictOccupancy();
                    break;
                case 11:
                    searchTicket();
                    break;
                case 12:
                    showAllTickets();
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
        if (parkingLot.findVehicle(plate) != null) {
            System.out.println("Vehicle already registered.");
            return;
        }
        System.out.print("Enter brand: ");
        String brand = scanner.next();
        System.out.println("Vehicle type:");
        System.out.println("1. Car");
        System.out.println("2. Motorcycle");
        System.out.print("Select: ");

        int vehicleType = scanner.nextInt();

        Vehicle vehicle;
        if(vehicleType == 1) {

            System.out.println("Is this a disabled vehicle?");
            System.out.println("1. Yes");
            System.out.println("2. No");
            System.out.print("Select: ");

            int disabledOption = scanner.nextInt();
            boolean disabled = (disabledOption == 1);

            vehicle = new Car(
                    plate,
                    brand,
                    java.time.LocalDateTime.now(),
                    disabled
            );
        } else {

            System.out.println("Is it a high displacement motorcycle?");
            System.out.println("1. Yes");
            System.out.println("2. No");
            System.out.print("Select: ");

            int displacementOption = scanner.nextInt();
            boolean highDisplacement = (displacementOption == 1);

            vehicle = new Motorcycle(
            plate,
            brand,
            java.time.LocalDateTime.now(),
            highDisplacement
        );
        }
        ParkingSpot spot = parkingLot.getAvailableSpotForVehicle(vehicle);

        if (spot == null) {
            System.out.println("No available parking spots.");
            return;
        }

        Employee employee = parkingLot.getEmployeeById("EMP001");

        spot.parkVehicle(vehicle);
        
        Ticket ticket = new Ticket("T" + ticketCounter, vehicle, spot, employee);

        employee.registerTicket();
        
        parkingLot.registerEntry(ticket);

        System.out.println("Vehicle assigned to spot "+ spot.getSpotNumber()+ " on floor "+ spot.getFloor());

        ticketCounter++;

        System.out.println("Vehicle registered successfully.");

    }

    private void registerVehicleExit() {
        System.out.print("Enter plate: ");

        String plate = scanner.next();
        System.out.println("Payment Method:");
        System.out.println("1. Cash");
        System.out.println("2. Credit Card");
        System.out.println("3. Debit Card");
        System.out.print("Select: ");

        int paymentOption = scanner.nextInt();
        String paymentMethod;

        switch (paymentOption) {
            case 1:
                paymentMethod = "Cash";
                break;
            case 2:
                paymentMethod = "Credit Card";
                break;
            case 3:
                paymentMethod = "Debit Card";
                break;
            default:
                paymentMethod = "Cash";
        }

        boolean success = parkingLot.registerExit(plate, paymentMethod);

        if (success) {

            Ticket ticket = parkingLot.findTicketByPlate(plate);

            System.out.println("===== EXIT SUMMARY =====");
            System.out.println("Ticket: " + ticket.getId());
            System.out.println("Plate: " + ticket.getVehicle().getPlate());
            System.out.println("Hours: " + ticket.getDuration());
            System.out.println("Fee: $" + ticket.getFee());
            System.out.println("Employee: " + ticket.getEmployee().getName());

            if (ticket.getPayment() != null) {
                System.out.println("Payment Method: " +
                        ticket.getPayment().getMethod());
            }

        } else {
            System.out.println("Active ticket not found.");
        }
    }

    private void showAvailableSpots() {

      int available = parkingLot.getAvailableSpots();

      System.out.println("Available spots: " + available);

    }

    private void generateReport() {

        Report report = parkingLot.generateReport();

        System.out.println(report.generateSummary());

        System.out.println("Total vehicles registered: " + parkingLot.getTotalVehiclesRegistered());

        System.out.println("Revenue (Cash): $" + report.getRevenueByPaymentMethod("Cash"));

        System.out.println("Revenue (Credit Card): $" + report.getRevenueByPaymentMethod("Credit Card"));

        System.out.println("Revenue (Debit Card): $" + report.getRevenueByPaymentMethod("Debit Card"));

        System.out.println("Floor 1 occupied spots: "    + parkingLot.getOccupiedSpotsByFloor(1));

        System.out.println("Floor 2 occupied spots: " + parkingLot.getOccupiedSpotsByFloor(2));

        System.out.println("Floor 3 occupied spots: " + parkingLot.getOccupiedSpotsByFloor(3));

        System.out.println("Available spots: " + parkingLot.getAvailableSpots());

        System.out.println("Occupied spots: " + parkingLot.getOccupiedSpots());

        System.out.println("Occupancy rate: "+ parkingLot.predictOccupancy() + "%");

        Ticket highestTicket = report.getHighestRevenueTicket();

        if (highestTicket != null) {

            System.out.println("Highest Revenue Ticket:");

            System.out.println(
                highestTicket.getId() +
                " - " +
                highestTicket.getVehicle().getPlate() +
                " - $" +
                highestTicket.getFee()
            );
        }
        dataManager.saveData();
    }
    private void searchVehicle() {

        System.out.print("Enter plate: ");
        String plate = scanner.next();

        Vehicle vehicle = parkingLot.findVehicle(plate);

        if(vehicle != null) {
            System.out.println("===== VEHICLE INFORMATION =====");
            System.out.println("Plate: " + vehicle.getPlate());
            System.out.println("Brand: " + vehicle.getBrand());
            System.out.println("Type: " + vehicle.getClass().getSimpleName());
            
            ParkingSpot spot = parkingLot.findVehicleSpot(plate);

            if (spot != null) {
            System.out.println("Spot: " + spot.getSpotNumber());
            System.out.println("Floor: " + spot.getFloor());
            }
            if (vehicle instanceof Car) {

                Car car = (Car) vehicle;

                System.out.println("Disabled Vehicle: " +
                (car.isDisabledVehicle() ? "Yes" : "No"));

            } else if (vehicle instanceof Motorcycle) {

                Motorcycle motorcycle = (Motorcycle) vehicle;

                System.out.println("High Displacement: " +
                (motorcycle.isHighDisplacement() ? "Yes" : "No"));
            }
        } 
        else {
            System.out.println("Vehicle not found.");
        }
    }
    private void showParkedVehicles() {

        System.out.println("===== PARKED VEHICLES =====");

        if (parkingLot.getVehicles().isEmpty()) {
            System.out.println("No vehicles parked.");
            return;
        }

        for (Vehicle vehicle : parkingLot.getVehicles()) {

            System.out.println(
                vehicle.getPlate() + " - " +
                vehicle.getBrand() + " - " +
                vehicle.getClass().getSimpleName()
            );
        }
    }
    private void showVehiclesByFloor() {

        System.out.print("Enter floor (1-3): ");
        int floor = scanner.nextInt();

        System.out.println("===== FLOOR " + floor + " =====");

        boolean found = false;

        for (ParkingSpot spot : parkingLot.getParkingSpots()) {

            if (spot.getFloor() == floor && spot.isOccupied()) {

                Vehicle vehicle = spot.getCurrentVehicle();

                System.out.println(
                    vehicle.getPlate() + " - " +
                    vehicle.getBrand() + " - " +
                    vehicle.getClass().getSimpleName()
                );

                found = true;
            }
        }

        if (!found) {
            System.out.println("No vehicles parked on this floor.");
        }
    }
    private void showOccupiedSpots() {

        System.out.println("===== OCCUPIED SPOTS =====");

        boolean found = false;

        for (ParkingSpot spot : parkingLot.getParkingSpots()) {

            if (spot.isOccupied()) {

                System.out.println(
                    "Spot " + spot.getSpotNumber() +
                    " - Floor " + spot.getFloor() +
                    " - " + spot.getCurrentVehicle().getPlate()
                );

                found = true;
            }
        }

        if (!found) {
            System.out.println("No occupied spots.");
        }
    }
    private void showEmployees() {

        System.out.println("===== EMPLOYEES =====");

        for (Employee employee : parkingLot.getEmployees()) {

            System.out.println(employee.getId() + " - " +employee.getName() +" - Tickets: " +parkingLot.getTicketsByEmployee(employee.getId()));
        }
    }
    private void predictOccupancy() {

        System.out.println("===== OCCUPANCY PREDICTION =====");

        double occupancy = parkingLot.predictOccupancy();

        System.out.println("Current occupancy: " + occupancy + "%");

        if (occupancy >= 80) {
            System.out.println("Warning: Parking lot almost full.");
        } else if (occupancy >= 50) {
            System.out.println("Moderate occupancy.");
        } else {
            System.out.println("Low occupancy.");
        }
    }
    private void searchTicket() {

        System.out.print("Enter ticket ID: ");
        String id = scanner.next();

        Ticket ticket = parkingLot.findTicketById(id);

        if (ticket != null) {

            System.out.println("===== TICKET INFORMATION =====");
            System.out.println("Ticket: " + ticket.getId());
            System.out.println("Plate: " + ticket.getVehicle().getPlate());
            System.out.println("Entry Time: " + ticket.getEntryTime());
            System.out.println("Exit Time: " +(ticket.getExitTime() == null
            ? "Vehicle still parked": ticket.getExitTime()));

            System.out.println("Hours: " + ticket.getDuration());
            System.out.println("Fee: $" + ticket.getFee());
            System.out.println("Employee: " + ticket.getEmployee().getName());

            if (ticket.getPayment() != null) {
                System.out.println("Payment Method: " + ticket.getPayment().getMethod());
            }

        } else {
            System.out.println("Ticket not found.");
        }
    }
    private void showAllTickets() {

        System.out.println("===== ALL TICKETS =====");

        if (parkingLot.getTickets().isEmpty()) {
            System.out.println("No tickets registered.");
            return;
        }

        for (Ticket ticket : parkingLot.getTickets()) {

            System.out.println(
                "Ticket: " + ticket.getId() +
                " | Plate: " + ticket.getVehicle().getPlate() +
                " | Fee: $" + ticket.getFee() +
                " | Employee: " + ticket.getEmployee().getName()
            );

            if (ticket.getPayment() != null) {

                System.out.println(
                    "Payment: " +
                    ticket.getPayment().getMethod()
                );
            } 
            System.out.println("--------------------------------");   
        }
    }
}   