package domain;

import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * Represents the parking lot management system.
 */
public class ParkingLot {

    private ArrayList<Vehicle> vehicles;
    private ArrayList<Ticket> tickets;
    private ArrayList<ParkingSpot> parkingSpots;
    private ArrayList<Employee> employees;
    private int totalVehiclesRegistered;

    public ParkingLot() {
        vehicles = new ArrayList<>();
        tickets = new ArrayList<>();
        parkingSpots = new ArrayList<>();
        employees = new ArrayList<>();
        totalVehiclesRegistered = 0;
    }

    public void registerEntry(Ticket ticket) {
        tickets.add(ticket);
        vehicles.add(ticket.getVehicle());
        totalVehiclesRegistered++;
    }

    public void addParkingSpot(ParkingSpot parkingSpot) {
    parkingSpots.add(parkingSpot);
    }

    public void addEmployee(Employee employee) {
    employees.add(employee);
    }

    public void addVehicle(Vehicle vehicle) {
    vehicles.add(vehicle);
    }

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

    public Vehicle findVehicle(String plate) {
        for(Vehicle vehicle:vehicles){
            if(vehicle.getPlate().equalsIgnoreCase(plate)){
                return vehicle;
            }
        }
        return null;
    }

    public int getAvailableSpots() {
        int available=0;
        for(ParkingSpot spot : parkingSpots){
            if(!spot.isOccupied()){
                available++;
            }
        }
        return available;
    }

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

    public int getTotalVehiclesRegistered() {
        return totalVehiclesRegistered;
    }

    public Report generateReport() {
        int totalVehicles = vehicles.size();
        double totalRevenue=0;

        for(Ticket ticket:tickets){
            totalRevenue+=ticket.getFee();
        }
        return new Report(totalVehicles,totalRevenue,tickets.size(),tickets,employees);
    }

    public double predictOccupancy() {
        if(parkingSpots.isEmpty()){
            return 0;
        }
        return ((double) getOccupiedSpots() / parkingSpots.size()) * 100;
    }

    public int getTotalTickets() {
        return tickets.size();
    }
    
    public int getOccupiedSpotsByFloor(int floor) {

     int occupied = 0;

     for (ParkingSpot spot : parkingSpots) {

        if (spot.getFloor() == floor && spot.isOccupied()) {
            occupied++;
        }
     }

     return occupied;
    }

    public ArrayList<Vehicle> getVehicles() {
        return vehicles;
    }
    public ArrayList<ParkingSpot> getParkingSpots() {
        return parkingSpots;
    }
    public ArrayList<Employee> getEmployees() {
        return employees;
    }
    public ArrayList<Ticket> getTickets() {
        return tickets;
    }

    public ParkingSpot findVehicleSpot(String plate) {

        for (ParkingSpot spot : parkingSpots) {

            if (spot.isOccupied()
                    && spot.getCurrentVehicle().getPlate().equalsIgnoreCase(plate)) {

                return spot;
            }
        }

        return null;
    }
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
}