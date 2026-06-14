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

    public ParkingLot() {
        vehicles = new ArrayList<>();
        tickets = new ArrayList<>();
        parkingSpots = new ArrayList<>();
        employees = new ArrayList<>();
    }

    public void registerEntry(Ticket ticket) {
        tickets.add(ticket);
        vehicles.add(ticket.getVehicle());
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

    public void registerExit(String plate) {

        for(Ticket ticket:tickets){
            if(ticket.getVehicle().getPlate().equalsIgnoreCase(plate)){
                ticket.setExitTime(LocalDateTime.now());
                ticket.calculateFee();
                Payment payment = new Payment(ticket.getFee(),"cash");
                payment.processPayment();
                ticket.setPayment(payment);
                ticket.getParkingSpot().removeVehicle();
                break;
            }
        }
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

    public ParkingSpot getAvailableSpot(){
        for(ParkingSpot spot : parkingSpots){
            if(!spot.isOccupied()){
                return spot;
            }
        }
        return null;
    }

    public Report generateReport() {
        int totalVehicles = vehicles.size();
        double totalRevenue=0;

        for(Ticket ticket:tickets){
            totalRevenue+=ticket.getFee();
        }
        return new Report(totalVehicles, totalRevenue);
    }

    public double predictOccupancy() {
        if(parkingSpots.isEmpty()){
            return 0;
        }
        return ((double) vehicles.size()/parkingSpots.size())*100; 
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
    public ParkingSpot findVehicleSpot(String plate) {

        for (ParkingSpot spot : parkingSpots) {

            if (spot.isOccupied()
                    && spot.getCurrentVehicle().getPlate().equalsIgnoreCase(plate)) {

                return spot;
            }
        }

        return null;
    }
}