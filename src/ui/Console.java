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
import java.util.ArrayList;
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
            System.out.println("13. Search Parking Spot");
            System.out.println("14. Show Parking Statistics");
            System.out.println("15. Show Floor Details");
            System.out.println("16. Employee Ranking");

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
                    searchEmployee();
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
                case 13:
                    searchParkingSpot();
                    break;
                case 14:
                    showParkingStatistics();
                    break;
                case 15:
                    showFloorDetails();
                    break;
                case 16:
                    showEmployeeRanking();
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

        System.out.println("\n===== AVAILABLE SPOTS =====");

        for (ParkingSpot spot : parkingLot.getParkingSpots()) {

            if (!spot.isOccupied()) {

                System.out.println("---------------------");
                System.out.println("Spot Number: " + spot.getSpotNumber());
                System.out.println("Floor: " + spot.getFloor());

                if (spot.isDisabledSpot()) {
                    System.out.println("Type: Disabled");
                } else if (spot.isHighDisplacementSpot()) {
                    System.out.println("Type: High Displacement Motorcycle");
                } else if (spot.isMotorcycleSpot()) {
                    System.out.println("Type: Motorcycle");
                } else {
                    System.out.println("Type: Regular Car");
                }
            }
        }

        System.out.println("---------------------");
        System.out.println("Total Available Spots: " +
                parkingLot.getAvailableSpots());
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

        System.out.println("\n===== PARKED VEHICLES =====");

        for (Vehicle vehicle : parkingLot.getVehicles()) {

            ParkingSpot spot = parkingLot.findVehicleSpot(vehicle.getPlate());

            System.out.println("---------------------");
            System.out.println("Plate: " + vehicle.getPlate());
            System.out.println("Brand: " + vehicle.getBrand());

            if (vehicle instanceof Car) {
                System.out.println("Type: Car");
            } else {
                System.out.println("Type: Motorcycle");
            }

            System.out.println("Entry Time: " + vehicle.getEntryTime());

            if (spot != null) {
                System.out.println("Spot: " + spot.getSpotNumber());
                System.out.println("Floor: " + spot.getFloor());
            }
        }

        System.out.println("---------------------");
        System.out.println("Total Parked Vehicles: "
                + parkingLot.getVehicles().size());
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

        System.out.println("\n===== OCCUPIED SPOTS =====");

        for (ParkingSpot spot : parkingLot.getParkingSpots()) {

            if (spot.isOccupied()) {

                System.out.println("---------------------");
                System.out.println("Spot Number: " + spot.getSpotNumber());
                System.out.println("Floor: " + spot.getFloor());
                System.out.println("Vehicle: " +
                        spot.getCurrentVehicle().getPlate());

                if (spot.getCurrentVehicle() instanceof Car) {
                    System.out.println("Type: Car");
                } else {
                    System.out.println("Type: Motorcycle");
                }
            }
        }

        System.out.println("---------------------");
        System.out.println("Total Occupied Spots: "
                + parkingLot.getOccupiedSpots());
    }
    private void searchEmployee() {

        System.out.print("Enter employee ID: ");
        String id = scanner.next();

        Employee employee = parkingLot.getEmployeeById(id);

        if (employee == null) {
            System.out.println("Employee not found.");
            return;
        }

        System.out.println("\n===== EMPLOYEE DETAILS =====");
        System.out.println("ID: " + employee.getId());
        System.out.println("Name: " + employee.getName());
        System.out.println("Tickets Processed: " + employee.getTicketsProcessed());
        System.out.println("Revenue Generated: $" + employee.getRevenueGenerated());

        for (Ticket ticket : parkingLot.getTickets()) {

            if (ticket.getEmployee().getId().equalsIgnoreCase(id)) {

                System.out.println("---------------------");
                System.out.println("Ticket: " + ticket.getId());
                System.out.println("Vehicle: " + ticket.getVehicle().getPlate());
                System.out.println("Fee: $" + ticket.getFee());
            }
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

        System.out.println("\n===== ALL TICKETS =====");

        if (parkingLot.getTickets().isEmpty()) {
            System.out.println("No tickets registered.");
            return;
        }

        for (Ticket ticket : parkingLot.getTickets()) {

            System.out.println("---------------------");
            System.out.println("Ticket ID: " + ticket.getId());
            System.out.println("Plate: " + ticket.getVehicle().getPlate());
            System.out.println("Type: " +
                    ticket.getVehicle().getClass().getSimpleName());

            System.out.println("Spot: " +
                    ticket.getParkingSpot().getSpotNumber());

            System.out.println("Employee: " +
                    ticket.getEmployee().getName());

            System.out.println("Entry: " +
                    ticket.getEntryTime());

            if (ticket.getExitTime() != null) {

                System.out.println("Exit: " +
                        ticket.getExitTime());

                System.out.println("Fee: $" +
                        ticket.getFee());

                if (ticket.getPayment() != null) {
                    System.out.println("Payment Method: " +
                            ticket.getPayment().getMethod());
                }
            } else {

                System.out.println("Status: ACTIVE");
            }
        }

        System.out.println("---------------------");
        System.out.println("Total Tickets: "
                + parkingLot.getTickets().size());
    }
    private void searchParkingSpot() {

        System.out.print("Enter floor: ");
        int floor = scanner.nextInt();

        System.out.print("Enter spot number: ");
        int spotNumber = scanner.nextInt();

        ParkingSpot spot =
                parkingLot.findParkingSpot(floor, spotNumber);

        if (spot == null) {
            System.out.println("Parking spot not found.");
            return;
        }

        System.out.println("\n===== PARKING SPOT =====");
        System.out.println("Floor: " + spot.getFloor());
        System.out.println("Spot Number: " + spot.getSpotNumber());

        if (spot.isDisabledSpot()) {
            System.out.println("Type: Disabled");
        } else if (spot.isHighDisplacementSpot()) {
            System.out.println("Type: High Displacement Motorcycle");
        } else if (spot.isMotorcycleSpot()) {
            System.out.println("Type: Motorcycle");
        } else {
            System.out.println("Type: Regular Car");
        }

        System.out.println("Occupied: " + spot.isOccupied());

        if (spot.isOccupied()) {

            Vehicle vehicle = spot.getCurrentVehicle();

            System.out.println("Vehicle Plate: "
                    + vehicle.getPlate());

            System.out.println("Vehicle Brand: "
                    + vehicle.getBrand());

            System.out.println("Vehicle Type: "
                    + vehicle.getClass().getSimpleName());
        }
    }
    private void showParkingStatistics() {

        int cars = 0;
        int motorcycles = 0;

        for (Vehicle vehicle : parkingLot.getVehicles()) {

            if (vehicle instanceof Car) {
                cars++;
            } else if (vehicle instanceof Motorcycle) {
                motorcycles++;
            }
        }

        System.out.println("\n===== PARKING STATISTICS =====");
        System.out.println("Cars Parked: " + cars);
        System.out.println("Motorcycles Parked: " + motorcycles);
        System.out.println("Available Spots: " + parkingLot.getAvailableSpots());
        System.out.println("Occupied Spots: " + parkingLot.getOccupiedSpots());
        System.out.printf("Occupancy: %.2f%%\n",
                parkingLot.predictOccupancy());
    }
    private void showFloorDetails() {

        System.out.print("Enter floor: ");
        int floor = scanner.nextInt();

        int occupied = parkingLot.getOccupiedSpotsByFloor(floor);

        System.out.println("\n===== FLOOR " + floor + " =====");
        System.out.println("Occupied Spots: " + occupied);

        System.out.println("\nVehicles:");

        for (Vehicle vehicle : parkingLot.getVehiclesByFloor(floor)) {

            System.out.println("---------------------");
            System.out.println("Plate: " + vehicle.getPlate());
            System.out.println("Brand: " + vehicle.getBrand());
            System.out.println("Type: "
                    + vehicle.getClass().getSimpleName());
        }
    }
    private void showEmployeeRanking() {

        System.out.println("\n===== EMPLOYEE RANKING =====");

        ArrayList<Employee> employees =
                new ArrayList<>(parkingLot.getEmployees());

        employees.sort((e1, e2) ->
                Integer.compare(
                        e2.getTicketsProcessed(),
                        e1.getTicketsProcessed()));

        int position = 1;

        for (Employee employee : employees) {

            System.out.println("\n#" + position++);
            System.out.println("ID: " + employee.getId());
            System.out.println("Name: " + employee.getName());
            System.out.println("Tickets: "
                    + employee.getTicketsProcessed());
            System.out.println("Revenue: $"
                    + employee.getRevenueGenerated());
        }
    }
}   