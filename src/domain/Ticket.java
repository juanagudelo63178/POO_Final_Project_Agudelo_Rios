package domain;

import java.time.Duration;
import java.time.LocalDateTime;

/**
 * Represents a parking ticket.
 */

//mejorar la logica de solo registrar una vez el vehiculo.
public class Ticket {

    private String id;
    private LocalDateTime entryTime;
    private LocalDateTime exitTime;
    private double fee;

    private Vehicle vehicle;
    private ParkingSpot parkingSpot;
    private Employee employee;
    private Payment payment;

    public Ticket(String id,Vehicle vehicle,ParkingSpot parkingSpot,Employee employee) {

        this.id = id;
        this.vehicle = vehicle;
        this.parkingSpot = parkingSpot;
        this.employee = employee;
        this.entryTime = LocalDateTime.now();
    }

    public double getFee() {
        return fee;
    }

    public String getId(){
        return id;
    }

    public Vehicle getVehicle(){
        return vehicle;
    }

    public ParkingSpot getParkingSpot(){
        return parkingSpot;
    }

    public Payment getPayment(){
        return  payment;
    }

    public void setPayment(Payment payment){
        this.payment = payment;
    }

    public void setExitTime(LocalDateTime exitTime){
        this.exitTime = exitTime;
    }
    
    public long getDuration(){
        if (exitTime == null){
            return 0;
        }
        return Duration.between(entryTime,exitTime).toHours();
    }

    public void calculateFee() {
        long hours = getDuration();
        if(hours==0){
            fee=0;
        }else{
            fee=hours*vehicle.getHourlyRate();
        }

    }
    
}