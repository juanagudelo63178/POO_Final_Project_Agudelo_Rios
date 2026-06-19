package domain;

import java.io.Serializable;

public class ParkingAI implements Serializable {

    public String generateAnalysis(ParkingLot parkingLot) {

        double occupancy = parkingLot.predictOccupancy();

        int[] hours = new int[24];

        for (Ticket ticket : parkingLot.getTickets()) {

            int hour = ticket.getEntryTime().getHour();

            hours[hour]++;
        }

        int peakHour = 0;

        for (int i = 1; i < hours.length; i++) {

            if (hours[i] > hours[peakHour]) {
                peakHour = i;
            }
        }

        int cars = 0;
        int motorcycles = 0;

    for (Ticket ticket : parkingLot.getTickets()) {

        if (ticket.getVehicle().getType() == VehicleType.CAR) {
            cars++;
        } else if (ticket.getVehicle().getType() == VehicleType.MOTORCYCLE) {
            motorcycles++;
        }
    }

    String mostCommonVehicle;

    if (cars > motorcycles) {
        mostCommonVehicle = "Car";
    } else if (motorcycles > cars) {
        mostCommonVehicle = "Motorcycle";
    } else {
    mostCommonVehicle = "Tie";
    }

    Employee mostActiveEmployee = null;
    int maxTickets = -1;

    for (Employee employee : parkingLot.getEmployees()) {

        if (employee.getTicketsProcessed() > maxTickets) {

            maxTickets = employee.getTicketsProcessed();
            mostActiveEmployee = employee;
        }
    }

    String activeEmployeeName;

    if (mostActiveEmployee != null) {
        activeEmployeeName = mostActiveEmployee.getName();
    } else {
        activeEmployeeName = "No employees";
    }

    Employee highestRevenueEmployee = null;
    double maxRevenue = -1;

    for (Employee employee : parkingLot.getEmployees()) {

        if (employee.getRevenueGenerated() > maxRevenue) {

            maxRevenue = employee.getRevenueGenerated();
            highestRevenueEmployee = employee;
        }
    }

    String revenueEmployeeName;

    if (highestRevenueEmployee != null) {
        revenueEmployeeName = highestRevenueEmployee.getName();
    } else {
        revenueEmployeeName = "No employees";
    }

    Vehicle topVehicle = null;
    double highestFee = -1;

    for (Ticket ticket : parkingLot.getTickets()) {

        if (ticket.getFee() > highestFee) {

            highestFee = ticket.getFee();
            topVehicle = ticket.getVehicle();
        }
    }

    String topVehiclePlate;

    if (topVehicle != null) {
        topVehiclePlate = topVehicle.getPlate();
    } else {
        topVehiclePlate = "No vehicles";
    }

        int[] floorUsage = new int[6]; // pisos 1 a 5

    for (Ticket ticket : parkingLot.getTickets()) {

        int floor = ticket.getParkingSpot().getFloor();

        if (floor >= 1 && floor <= 5) {
            floorUsage[floor]++;
        }
    }

    int mostUsedFloor = 1;

    for (int i = 2; i <= 5; i++) {

            if (floorUsage[i] > floorUsage[mostUsedFloor]) {
        mostUsedFloor = i;
        }
    }

    String recommendation = "";

    if (occupancy >= 80) {

        recommendation +=
            "Increase parking capacity or speed up exits. ";

    }

    if (peakHour >= 12 && peakHour <= 16) {

        recommendation +=
            "Increase staffing during afternoon peak hours. ";

    }

    if (mostUsedFloor == 1 || mostUsedFloor == 5) {

        recommendation +=
            "Monitor motorcycle floors due to high demand. ";

    } else {

        recommendation +=
            "Monitor car floors due to high demand. ";

    }

    if (recommendation.isEmpty()) {

        recommendation =
            "Parking operation is stable.";
    }

        String analysis =
        "===== AI ANALYSIS =====\n" +
        "Current Occupancy: " +
        String.format("%.2f", occupancy) +
        "%\n" +
        "Peak Hour: " + peakHour + ":00\n"+
        "Most Common Vehicle: " + mostCommonVehicle + "\n" +
        "Most Active Employee: " + activeEmployeeName + "\n"+
        "Highest Revenue Employee: " + revenueEmployeeName + "\n" +
        "Top Revenue Vehicle: " + topVehiclePlate + "\n"+
        "Most Used Floor: " + mostUsedFloor + "\n"+
        "Recommendation: " + recommendation + "\n";
        
        return analysis;
    }
}
