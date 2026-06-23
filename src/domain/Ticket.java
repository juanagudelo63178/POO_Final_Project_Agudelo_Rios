package domain;

import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDateTime;

/**
* Represents a parking ticket.
*/

public class Ticket implements Serializable {

    private String id;
    private LocalDateTime entryTime;
    private LocalDateTime exitTime;
    private double fee;

    private Vehicle vehicle;
    private ParkingSpot parkingSpot;
    private Employee employee;
    private Payment payment;

    /**
    * Creates a ticket associated with a vehicle, parking spot, and employee.
    */

    public Ticket(String id,Vehicle vehicle,ParkingSpot parkingSpot,Employee employee) {

        this.id = id;
        this.vehicle = vehicle;
        this.parkingSpot = parkingSpot;
        this.employee = employee;
        this.entryTime = LocalDateTime.now();
    }

    /**
    * Returns the parking fee associated with this ticket.
    */

    public double getFee() {
        return fee;
    }

    /**
    * Returns the unique identifier of this ticket.
    */

    public String getId(){
        return id;
    }

    /**
    * Returns the vehicle associated with this ticket.
    */

    public Vehicle getVehicle(){
        return vehicle;
    }

    /**
    * Returns the parking spot associated with this ticket.
    */

    public ParkingSpot getParkingSpot(){
        return parkingSpot;
    }

    /**
    * Returns the payment associated with this ticket.
    */

    public Payment getPayment(){
        return  payment;
    }

    /**
    * Returns the employee associated with this ticket.
    */

    public Employee getEmployee() {
        return employee;
    }

    /**
    * Assigns a payment to this ticket.
    */

    public void setPayment(Payment payment){
        this.payment = payment;
    }

    /**
    * Sets the exit time associated with this ticket.
    */

    public void setExitTime(LocalDateTime exitTime){
        this.exitTime = exitTime;
    }
    
    /**
    * Returns the total parking duration associated with this ticket.
    */

    public long getDuration() {

        if (exitTime == null) {
            return 0;
        }

        long minutes = Duration.between(entryTime, exitTime).toMinutes();

        return (long) Math.ceil(minutes / 60.0);
    }

    /**
    * Calculates the parking fee based on the parking duration.
    */

    public void calculateFee() {
        long hours = getDuration();
        if(hours==0){
            fee=0;
        }else{
            fee=hours*vehicle.getHourlyRate();
        }

    }

    /**
    * Returns the exit time associated with this ticket.
    */

    public LocalDateTime getExitTime() {
       return exitTime;
    }

    /**
    * Returns the entry time associated with this ticket.
    */

    public LocalDateTime getEntryTime() {
        return entryTime;
    }
    
}