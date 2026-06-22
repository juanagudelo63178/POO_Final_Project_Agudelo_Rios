package domain;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;

/**
* Represents the parking lot management system.
*/

public class ParkingLot implements Serializable {

    private ArrayList<Vehicle> vehicles;
    private ArrayList<Ticket> tickets;
    private ArrayList<ParkingSpot> parkingSpots;
    private ArrayList<Employee> employees;
    private ArrayList<ParkingFloor> floors;
    private int totalVehiclesRegistered;

    /**
    * Creates a parking lot and initializes its main components and parking floors.
    */

    public ParkingLot() {
        vehicles = new ArrayList<>();
        tickets = new ArrayList<>();
        parkingSpots = new ArrayList<>();
        employees = new ArrayList<>();
        totalVehiclesRegistered = 0;
        floors = new ArrayList<>();

        
    }
    
    /**
    * Registers the entry of a vehicle into the parking lot using a parking ticket.
    */

    public void registerEntry(Ticket ticket) {
        tickets.add(ticket);
        vehicles.add(ticket.getVehicle());
        totalVehiclesRegistered++;
    }

    /**
    * Adds a parking space to the parking lot.
    */

    public void addParkingSpot(ParkingSpot parkingSpot) {

        parkingSpots.add(parkingSpot);

    }

    /**
    * Adds an employee to the parking lot staff.
    */

    public void addEmployee(Employee employee) {
    employees.add(employee);
    }

    /**
    * Adds a vehicle to the parking lot records.
    */
    
    public void addVehicle(Vehicle vehicle) {
    vehicles.add(vehicle);
    }

    /**
    * Processes the exit of a vehicle and records its payment information.
    */

    public boolean registerExit(String plate, String paymentMethod){

        for (Ticket ticket : tickets) {

            if (ticket.getVehicle().getPlate().equalsIgnoreCase(plate) && ticket.getExitTime() == null) {

                ticket.setExitTime(LocalDateTime.now());
                ticket.calculateFee();

                Payment payment = new Payment(ticket.getFee(), paymentMethod);

                if (!payment.processPayment()) {
                    System.out.println("Payment failed.");
                    return false;
                }
                System.out.println(payment.generateReceipt());

                ticket.setPayment(payment);
                ticket.getEmployee().addRevenue(ticket.getFee());
                ticket.getParkingSpot().removeVehicle();
                vehicles.remove(ticket.getVehicle());

                return true;
            }
        }

        return false;
    }

    /**
    * Searches for and returns a vehicle based on its license plate.
    */

    public Vehicle findVehicle(String plate) {
        for(Vehicle vehicle:vehicles){
            if(vehicle.getPlate().equalsIgnoreCase(plate)){
                return vehicle;
            }
        }
        return null;
    }

    /**
    Returns the total number of available parking spaces in the parking lot.
    */

    public int getAvailableSpots() {
        int available=0;
        for(ParkingSpot spot : parkingSpots){
            if(!spot.isOccupied()){
                available++;
            }
        }
        return available;
    }

    /**
    * Finds and returns an available parking space in the parking lot.
    */

    public ParkingSpot getAvailableSpot() {

        for (ParkingSpot spot : parkingSpots) {

            if (!spot.isOccupied()
                    && !spot.isDisabledSpot()
                    && !spot.isHighDisplacementSpot()
                    && !spot.isMotorcycleSpot()) {

                return spot;
            }
        }

        return null;
    }   

    /**
    * Finds and returns an available parking space suitable for the specified vehicle.
    */

    public ParkingSpot getAvailableSpotForVehicle(Vehicle vehicle) {

        for (ParkingSpot spot : parkingSpots) {

            if (spot.isOccupied()) {
                continue;
            }

            if (vehicle instanceof Car) {

                Car car = (Car) vehicle;

                if (car.isDisabledVehicle() && spot.isDisabledSpot()) {
                    return spot;
                }
            }

            if (vehicle instanceof Motorcycle) {

                Motorcycle motorcycle = (Motorcycle) vehicle;

                if (motorcycle.isHighDisplacement()
                        && spot.isHighDisplacementSpot()) {
                    return spot;
                }

                if (!motorcycle.isHighDisplacement()
                        && spot.isMotorcycleSpot()) {
                    return spot;
                }
            }
        }

        return getAvailableSpot();
    }

    /**
    * Returns the total number of vehicles registered in the parking lot.
    */

    public int getTotalVehiclesRegistered() {
        return totalVehiclesRegistered;
    }

    /**
    * Generates a report containing information and statistics about the parking lot.
    */

    public Report generateReport() {
        int totalVehicles = vehicles.size();
        double totalRevenue=0;

        for(Ticket ticket:tickets){
            totalRevenue+=ticket.getFee();
        }
        return new Report(totalVehicles,totalRevenue,tickets.size(),tickets,employees);
    }

    /**
    * Predicts the future occupancy level of the parking lot based on current data.
    */

    public double predictOccupancy() {
        if(parkingSpots.isEmpty()){
            return 0;
        }
        return ((double) getOccupiedSpots() / parkingSpots.size()) * 100;
    }

    /**
    * Returns the total number of tickets processed in the parking lot.
    */

    public int getTotalTickets() {
        return tickets.size();
    }
    
    /**
    * Returns the number of occupied parking spaces on the specified floor.
    */

    public int getOccupiedSpotsByFloor(int floor) {

     int occupied = 0;

     for (ParkingSpot spot : parkingSpots) {

        if (spot.getFloor() == floor && spot.isOccupied()) {
            occupied++;
        }
     }

     return occupied;
    }

    /**
    * Returns the list of vehicles currently registered in the parking lot.
    */

    public ArrayList<Vehicle> getVehicles() {
        return vehicles;
    }

    /**
    * Returns the list of parking spaces available in the parking lot.
    */

    public ArrayList<ParkingSpot> getParkingSpots() {
        return parkingSpots;
    }

    /**
    * Returns the list of employees working in the parking lot.
    */

    public ArrayList<Employee> getEmployees() {
        return employees;
    }

    /**
    * Returns the list of tickets registered in the parking lot.
    */

    public ArrayList<Ticket> getTickets() {
        return tickets;
    }

    /**
    * Finds and returns the parking space occupied by the vehicle with the specified license plate.
    */

    public ParkingSpot findVehicleSpot(String plate) {

        for (ParkingSpot spot : parkingSpots) {

            if (spot.isOccupied()
                    && spot.getCurrentVehicle().getPlate().equalsIgnoreCase(plate)) {

                return spot;
            }
        }

        return null;
    }

    /**
    * Finds and returns the ticket associated with the specified vehicle license plate.
    */

    public Ticket findTicketByPlate(String plate) {

        for (Ticket ticket : tickets) {

            if (ticket.getVehicle().getPlate().equalsIgnoreCase(plate)) {
                return ticket;
            }
        }

        return null;
    }


    
    public Employee getEmployeeById(String id) {

        for (Employee employee : employees) {

            if (employee.getId().equalsIgnoreCase(id)) {
                return employee;
            }
        }

        return null;
    }
    public int getTicketsByEmployee(String employeeId) {

        int count = 0;

        for (Ticket ticket : tickets) {

            if (ticket.getEmployee().getId().equalsIgnoreCase(employeeId)) {
                count++;
            }
        }

        return count;
    }

    public int getOccupiedSpots() {

        int occupied = 0;

        for (ParkingSpot spot : parkingSpots) {

            if (spot.isOccupied()) {
                occupied++;
            }
        }

        return occupied;
    }
    public Ticket findTicketById(String id) {

        for (Ticket ticket : tickets) {

            if (ticket.getId().equalsIgnoreCase(id)) {
                return ticket;
            }
        }

        return null;
    }
    public ParkingSpot findParkingSpot(int floor, int spotNumber) {

        for (ParkingSpot spot : parkingSpots) {

            if (spot.getFloor() == floor &&
                spot.getSpotNumber() == spotNumber) {

                return spot;
            }
        }

        return null;
    }
    public ArrayList<Vehicle> getVehiclesByFloor(int floor) {

        ArrayList<Vehicle> vehiclesOnFloor = new ArrayList<>();

        for (ParkingSpot spot : parkingSpots) {

            if (spot.getFloor() == floor &&
                spot.isOccupied()) {

                vehiclesOnFloor.add(spot.getCurrentVehicle());
            }
        }

        return vehiclesOnFloor;
    }
    public void addFloor(ParkingFloor floor) {
        floors.add(floor);
    }

    public ArrayList<ParkingFloor> getFloors() {
        return floors;
    }
    public boolean updateVehicleBrand(String plate, String newBrand) {

        Vehicle vehicle = findVehicle(plate);

        if (vehicle != null) {
            vehicle.setBrand(newBrand);
            return true;
        }

        return false;
    }
    public boolean removeVehicle(String plate) {

        Vehicle vehicle = findVehicle(plate);

        if (vehicle != null) {

            ParkingSpot spot = findVehicleSpot(plate);

            if (spot != null) {
                return false; // cannot delete parked vehicle
            }

            vehicles.remove(vehicle);
            return true;
        }

        return false;
    }
}