package ui;
import data.DataManager;
import domain.Car;
import domain.Employee;
import domain.Motorcycle;
import domain.ParkingAI;
import domain.ParkingFloor;
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

    private DataManager dataManager;
    
    /**
    * Creates a console instance for interacting with the parking system.
    */

    public Console() {

        dataManager = new DataManager();

        parkingLot = dataManager.loadData();

        scanner = new Scanner(System.in);

        if (parkingLot == null) {

            parkingLot = new ParkingLot();

            for (int floorNumber = 1; floorNumber <= 5; floorNumber++) {

                ParkingFloor floor = new ParkingFloor(floorNumber);

                parkingLot.addFloor(floor);
            }

            for (ParkingFloor floor : parkingLot.getFloors()) {

                int spotNumber = 1;

                for (int row = 0; row < 5; row++) {

                    for (int col = 0; col < 5; col++) {

                        ParkingSpot spot =
                                new ParkingSpot(spotNumber, floor.getFloorNumber());

                        if (floor.getFloorNumber() == 1
                                || floor.getFloorNumber() == 5) {

                            spot.setMotorcycleSpot(true);

                            if (row + col == 4) {
                                spot.setHighDisplacementSpot(true);
                            }

                        } else {

                            if (row == col) {
                                spot.setDisabledSpot(true);
                            }
                        }

                        floor.addSpot(spot);

                        parkingLot.addParkingSpot(spot);

                        floor.setSpot(row, col, spot);

                        spotNumber++;
                    }
                }
            }

            parkingLot.addEmployee(
                    new Employee("1", "Juan")
            );
        }
       
    }

    /**
    * Starts the execution of the console application.
    */

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
            System.out.println("10. Analitic AI");
            System.out.println("11. Search Ticket");
            System.out.println("12. Show All Tickets");
            System.out.println("13. Search Parking Spot");
            System.out.println("14. Show Parking Statistics");
            System.out.println("15. Show Floor Details");
            System.out.println("16. Employee Ranking");
            System.out.println("17. Modify Vehicle");
            System.out.println("18. Delete Vehicle");
            System.out.println("19. Register Employee");
            System.out.println("20. Fire Employee");
            System.out.println("21. Show All Employees");

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
                    parkingAIMenu();
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
                case 17:
                    modifyVehicle();
                    break;
                case 18:
                    deleteVehicle();
                    break;
                case 19:
                    registerEmployee();
                    break;

                case 20:
                    fireEmployee();
                    break;

                case 21:
                    showEmployees();
                    break;
                case 0:
                    dataManager.saveData(parkingLot);
                    System.out.println("Closing system...");
                    break;

                default:
                    System.out.println("Invalid option");
            }
        }while(option !=0);    
    }

    /**
    * Registers the entry of a vehicle into the parking system.
    */

    private void registerVehicleEntry() {
        System.out.print("Enter plate: ");
        String plate = scanner.next();
        if (!plate.matches("[A-Za-z]{3}[0-9]{3}")) {
            System.out.println(
                "Invalid plate format. Use 3 letters followed by 3 numbers (ABC123)."
            );
            return;
        }
        plate = plate.toUpperCase();
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
        if(vehicleType != 1 && vehicleType != 2){
            System.out.println("Invalid vehicle type.");
            return;
        }
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

        System.out.println("Available Employees:");

        for (Employee employee : parkingLot.getEmployees()) {
            System.out.println(
                employee.getId() + " - " +
                employee.getName()
            );
        }

        System.out.print("Select employee ID: ");
        String employeeId = scanner.next();

        Employee employee =
                parkingLot.getEmployeeById(employeeId);

        if (employee == null) {
            System.out.println("Employee not found.");
            return;
        }

        spot.parkVehicle(vehicle);
        
        Ticket ticket = new Ticket("T" + parkingLot.getNextTicketNumber(),vehicle,spot,employee);

        employee.registerTicket();
        
        parkingLot.registerEntry(ticket);

        System.out.println("Vehicle assigned to spot "+ spot.getSpotNumber()+ " on floor "+ spot.getFloor());


        System.out.println("Vehicle registered successfully.");

    }

    /**
    * Registers the exit of a vehicle from the parking system.
    */

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
                System.out.println("Invalid payment method");
                return;
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

    /**
    * Displays the available parking spots in the parking system.
    */    

    private void showAvailableSpots() {

        System.out.println("\n===== AVAILABLE SPOTS =====");

        for (ParkingFloor floor : parkingLot.getFloors()) {
            floor.showAvailableMatrix();
        }

        System.out.println("\n========== LEGEND ==========");
        System.out.println("AVC = Available Car Spot");
        System.out.println("AVD = Available Disabled Spot");
        System.out.println("AVM = Available Motorcycle Spot");
        System.out.println("AVH = Available High Displacement Spot");
        System.out.println("--- = Occupied Spot");

        System.out.println("\nTotal Available Spots: "
                + parkingLot.getAvailableSpots());
    }

    /**
    * Generates and displays a report with parking system statistics.
    */

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

        System.out.println("Floor 4 occupied spots: " + parkingLot.getOccupiedSpotsByFloor(4));

        System.out.println("Floor 5 occupied spots: " + parkingLot.getOccupiedSpotsByFloor(5));

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
        dataManager.saveData(parkingLot);
    }

    /**
    * Searches for a vehicle in the parking system.
    */

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

    /**
    * Displays the vehicles currently parked in the parking system.
    */

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

            Ticket ticket = parkingLot.findTicketByPlate(vehicle.getPlate());

            if(ticket != null){
                System.out.println(
                    "Entry Time: " + ticket.getEntryTime()
                );
            }

            if (spot != null) {
                System.out.println("Spot: " + spot.getSpotNumber());
                System.out.println("Floor: " + spot.getFloor());
            }
        }

        System.out.println("---------------------");
        System.out.println("Total Parked Vehicles: "
                + parkingLot.getVehicles().size());
    }

    /**
    * Displays the parked vehicles grouped by parking floor.
    */

    private void showVehiclesByFloor() {

        System.out.print("Enter floor (1-5): ");
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

    /**
    * Displays the parking spots that are currently occupied.
    */

    private void showOccupiedSpots() {

        System.out.println("\n===== OCCUPIED SPOTS =====");

        for (ParkingFloor floor : parkingLot.getFloors()) {
            floor.showOccupiedMatrix();
        }

        System.out.println("\n========== LEGEND ==========");
        System.out.println("C = Car");
        System.out.println("M = Motorcycle");
    }

    /**
    * Searches for an employee in the parking system.
    */

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

    /**
    * Searches for a ticket in the parking system.
    */

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

    /**
    * Displays all tickets registered in the parking system.
    */

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

    /**
    * Searches for a parking spot in the parking system.
    */

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

    /**
    * Displays statistical information about the parking system.
    */

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

    /**
    * Displays detailed information about each parking floor.
    */

    private void showFloorDetails() {

        System.out.print("Enter floor: ");
        int floorNumber = scanner.nextInt();

        ParkingFloor selectedFloor = null;

        for (ParkingFloor floor : parkingLot.getFloors()) {
            if (floor.getFloorNumber() == floorNumber) {
                selectedFloor = floor;
                break;
            }
        }

        if (selectedFloor == null) {
            System.out.println("Invalid floor.");
            return;
        }

        System.out.println("\n1. Available Spaces Map");
        System.out.println("2. Occupied Spaces Map");
        System.out.println("3. Both Maps");
        System.out.print("Select: ");

        int option = scanner.nextInt();

        switch (option) {

            case 1:
                selectedFloor.showAvailableMatrix();
                break;

            case 2:
                selectedFloor.showOccupiedMatrix();
                break;

            case 3:
                selectedFloor.showAvailableMatrix();
                selectedFloor.showOccupiedMatrix();
                break;

            default:
                System.out.println("Invalid option.");
        }
    }

    /**
    * Displays a ranking of employees based on their performance or activity.
    */

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

    /**
    * Modifies the information of a vehicle registered in the parking system.
    */

    private void modifyVehicle() {

        System.out.print("Enter plate: ");
        String plate = scanner.next();

        System.out.print("Enter new brand: ");
        String newBrand = scanner.next();

        if (parkingLot.updateVehicleBrand(plate, newBrand)) {
            System.out.println("Vehicle updated successfully.");
        } else {
            System.out.println("Vehicle not found.");
        }
    }

    /**
    * Removes a vehicle from the parking system.
    */

    private void deleteVehicle() {

        System.out.print("Enter plate: ");
        String plate = scanner.next();

        if (parkingLot.removeVehicle(plate)) {
            System.out.println("Vehicle deleted successfully.");
        } else {
            System.out.println(
                "Vehicle not found or is currently parked."
            );
        }
    }

    /**
    * Displays and manages the Parking AI menu options.
    */

    private void parkingAIMenu() {

        System.out.println("\n===== PARKING AI =====");

        System.out.println("1. What is the busiest hour?");
        System.out.println("2. What is the least busy hour?");
        System.out.println("3. What type of vehicle visits the parking lot the most?");
        System.out.println("4. Have we ever run out of disabled parking spaces?");
        System.out.println("5. Have we ever run out of high-displacement motorcycle spaces?");
        System.out.println("6. Which employee deserves a promotion?");
        System.out.println("7. Which employee may require performance improvement?");
        System.out.println("8. Which vehicle deserves a loyalty discount?");
        System.out.println("9. What is the busiest day in our records?");
        System.out.println("10. What is the estimated revenue for tomorrow?");

        System.out.print("Select: ");
        int option = scanner.nextInt();

        ParkingAI ai = new ParkingAI();

        switch (option) {

            case 1:
                System.out.println(ai.getBusiestHour(parkingLot));
                break;

            case 2:
                System.out.println(ai.getLeastBusyHour(parkingLot));
                break;

            case 3:
                System.out.println(ai.getMostCommonVehicleType(parkingLot));
                break;

            case 4:
                System.out.println(ai.analyzeDisabledSpaces(parkingLot));
                break;

            case 5:
                System.out.println(ai.analyzeHighDisplacementSpaces(parkingLot));
                break;

            case 6:
                System.out.println(ai.recommendPromotion(parkingLot));
                break;

            case 7:
                System.out.println(ai.recommendPerformanceImprovement(parkingLot));
                break;

            case 8:
                System.out.println(ai.recommendLoyaltyDiscount(parkingLot));
                break;

            case 9:
                System.out.println(ai.getBusiestDay(parkingLot));
                break;

            case 10:
                System.out.println(ai.predictTomorrowRevenue(parkingLot));
                break;

            default:
                System.out.println("Invalid option.");
        }
    }

    /**
    * Registers a new employee in the parking system.
    */

    private void registerEmployee() {

        System.out.print("Employee ID: ");
        String id = scanner.next();

        if (parkingLot.getEmployeeById(id) != null) {
            System.out.println("Employee already exists.");
            return;
        }

        System.out.print("Employee Name: ");
        String name = scanner.next();

        parkingLot.addEmployee(new Employee(id, name));

        System.out.println("Employee registered successfully.");
    }

    /**
    * Removes an employee from the parking system.
    */

    private void fireEmployee() {

        System.out.print("Employee ID: ");
        String id = scanner.next();

        if (parkingLot.removeEmployee(id)) {

            System.out.println("Employee removed successfully.");

        } else {

            System.out.println("Employee could not be removed.");
        }
    }

    /**
    * Displays all employees registered in the parking system.
    */

    private void showEmployees() {

        System.out.println("\n===== EMPLOYEES =====");

        for (Employee employee : parkingLot.getEmployees()) {

            System.out.println("------------------");
            System.out.println("ID: " + employee.getId());
            System.out.println("Name: " + employee.getName());
            System.out.println("Tickets: "
                    + employee.getTicketsProcessed());
            System.out.println("Revenue: $"
                    + employee.getRevenueGenerated());
        }
    }
}   