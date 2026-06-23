package domain;

import java.io.Serializable;

/**
* Provides intelligent recommendations and analysis for parking lot operations.
*/

public class ParkingAI implements Serializable {

        /**
     * Returns the hour with the highest vehicle traffic in the parking lot.
     */

    public String getBusiestHour(ParkingLot parkingLot) {

            if (parkingLot.getTickets().isEmpty()) {
                return "No ticket data available.";
            }

            int[] hours = new int[24];

            for (Ticket ticket : parkingLot.getTickets()) {
                int hour = ticket.getEntryTime().getHour();
                hours[hour]++;
            }

            int busiestHour = 0;

            for (int i = 1; i < 24; i++) {
                if (hours[i] > hours[busiestHour]) {
                    busiestHour = i;
                }
            }

            return "The busiest hour is "
                    + busiestHour
                    + ":00 with "
                    + hours[busiestHour]
                    + " vehicle entries.";
    }

        /**
     * Returns the hour with the lowest vehicle traffic in the parking lot.
     */

    public String getLeastBusyHour(ParkingLot parkingLot) {

        if (parkingLot.getTickets().isEmpty()) {
            return "No ticket data available.";
        }

        int[] hours = new int[24];

        for (Ticket ticket : parkingLot.getTickets()) {
            int hour = ticket.getEntryTime().getHour();
            hours[hour]++;
        }

        int leastBusyHour = -1;

        for (int i = 0; i < 24; i++) {

            if (hours[i] > 0) {

                if (leastBusyHour == -1 ||
                        hours[i] < hours[leastBusyHour]) {

                    leastBusyHour = i;
                }
            }
        }

        return "The least busy hour is "
                + leastBusyHour
                + ":00 with "
                + hours[leastBusyHour]
                + " vehicle entries.";
    }

        /**
     * Returns the most common vehicle type currently registered in the parking lot.
     */

    public String getMostCommonVehicleType(ParkingLot parkingLot) {

        if (parkingLot.getTickets().isEmpty()) {
            return "No ticket data available.";
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

        if (cars > motorcycles) {

            double percentage =
                    (cars * 100.0) / (cars + motorcycles);

            return "Cars are the most common vehicle type with "
                    + String.format("%.2f", percentage)
                    + "% of all visits.";

        } else if (motorcycles > cars) {

            double percentage =
                    (motorcycles * 100.0) / (cars + motorcycles);

            return "Motorcycles are the most common vehicle type with "
                    + String.format("%.2f", percentage)
                    + "% of all visits.";

        } else {

            return "Cars and motorcycles have the same number of visits.";
        }
    }

        /**
     * Recommends a promotion for an employee based on their performance.
     */


    public String recommendPromotion(ParkingLot parkingLot) {

        if (parkingLot.getEmployees().isEmpty()) {
            return "No employee data available.";
        }

        Employee bestEmployee = null;

        int maxTickets = -1;
        double maxRevenue = -1;

        for (Employee employee : parkingLot.getEmployees()) {

            if (employee.getTicketsProcessed() > maxTickets) {

                maxTickets = employee.getTicketsProcessed();
                maxRevenue = employee.getRevenueGenerated();
                bestEmployee = employee;

            } else if (employee.getTicketsProcessed() == maxTickets
                    && employee.getRevenueGenerated() > maxRevenue) {

                maxRevenue = employee.getRevenueGenerated();
                bestEmployee = employee;
            }
        }

        return "Based on current performance, "
                + bestEmployee.getName()
                + " deserves consideration for a promotion. "
                + "This employee processed "
                + bestEmployee.getTicketsProcessed()
                + " tickets and generated $"
                + String.format("%.2f",
                        bestEmployee.getRevenueGenerated())
                + " in revenue.";
    }

        /**
     * Recommends actions to improve employee performance based on parking lot activity and operational data.
     */

    public String recommendPerformanceImprovement(ParkingLot parkingLot) {

        if (parkingLot.getEmployees().isEmpty()) {
            return "No employee data available.";
        }

        Employee employeeToImprove = null;

        int minTickets = Integer.MAX_VALUE;
        double minRevenue = Double.MAX_VALUE;

        for (Employee employee : parkingLot.getEmployees()) {

            if (employee.getTicketsProcessed() < minTickets) {

                minTickets = employee.getTicketsProcessed();
                minRevenue = employee.getRevenueGenerated();
                employeeToImprove = employee;

            } else if (employee.getTicketsProcessed() == minTickets
                    && employee.getRevenueGenerated() < minRevenue) {

                minRevenue = employee.getRevenueGenerated();
                employeeToImprove = employee;
            }
        }

        return "Based on current performance, "
                + employeeToImprove.getName()
                + " may require additional training or performance improvement. "
                + "This employee processed "
                + employeeToImprove.getTicketsProcessed()
                + " tickets and generated $"
                + String.format("%.2f",
                        employeeToImprove.getRevenueGenerated())
                + " in revenue.";
    }

    /**
 * Recommends a loyalty discount strategy for frequent or returning parking lot customers.
 */

    public String recommendLoyaltyDiscount(ParkingLot parkingLot) {

        if (parkingLot.getTickets().isEmpty()) {
            return "No ticket data available.";
        }

        java.util.HashMap<String, Integer> visits =
                new java.util.HashMap<>();

        for (Ticket ticket : parkingLot.getTickets()) {

            String plate = ticket.getVehicle().getPlate();

            if (visits.containsKey(plate)) {
                visits.put(plate, visits.get(plate) + 1);
            } else {
                visits.put(plate, 1);
            }
        }

        String bestCustomer = null;
        int maxVisits = 0;

        for (String plate : visits.keySet()) {

            if (visits.get(plate) > maxVisits) {

                maxVisits = visits.get(plate);
                bestCustomer = plate;
            }
        }

        return "Vehicle "
                + bestCustomer
                + " deserves a loyalty discount. "
                + "It has visited the parking lot "
                + maxVisits
                + " times, making it the most frequent customer.";
    }

    /**
 * Analyzes the usage and occupancy of disabled parking spaces in the parking lot.
 */

    public String analyzeDisabledSpaces(ParkingLot parkingLot) {

        int rejections = parkingLot.getDisabledRejections();

        if (rejections == 0) {
            return "Disabled parking demand has always been satisfied.";
        }

        return "Disabled parking demand exceeded capacity "
                + rejections
                + " times.";
    }

    /**
 * Analyzes the usage and occupancy of parking spaces reserved for high-displacement vehicles.
 */

    public String analyzeHighDisplacementSpaces(ParkingLot parkingLot) {

        int rejections =
                parkingLot.getHighDisplacementRejections();

        if (rejections == 0) {
            return "High-displacement motorcycle demand has always been satisfied.";
        }

        return "High-displacement motorcycle demand exceeded capacity "
                + rejections
                + " times.";
    }

    /**
 * Returns the day with the highest parking lot activity based on vehicle entries.
 */

    public String getBusiestDay(ParkingLot parkingLot) {

        if (parkingLot.getTickets().isEmpty()) {
            return "No ticket data available.";
        }

        int[] days = new int[7];

        for (Ticket ticket : parkingLot.getTickets()) {

            int day =
                    ticket.getEntryTime()
                    .getDayOfWeek()
                    .getValue() - 1;

            days[day]++;
        }

        int busiestDay = 0;

        for (int i = 1; i < days.length; i++) {

            if (days[i] > days[busiestDay]) {
                busiestDay = i;
            }
        }

        String[] dayNames = {
            "Monday",
            "Tuesday",
            "Wednesday",
            "Thursday",
            "Friday",
            "Saturday",
            "Sunday"
        };

        return "The busiest day is "
                + dayNames[busiestDay]
                + " with "
                + days[busiestDay]
                + " vehicle entries.";
    }

    /****
 * Predicts the revenue expected for the next day based on historical parking lot data and usage patterns.
 */

    public String predictTomorrowRevenue(ParkingLot parkingLot) {

        if (parkingLot.getTickets().isEmpty()) {
            return "Not enough data to predict revenue.";
        }

        double totalRevenue = 0;

        for (Ticket ticket : parkingLot.getTickets()) {
            totalRevenue += ticket.getFee();
        }

        double averageRevenue =
                totalRevenue / parkingLot.getTickets().size();

        double estimatedRevenue =
                averageRevenue * parkingLot.getVehicles().size();

        return "Estimated revenue for tomorrow is $"
                + String.format("%.2f", estimatedRevenue)
                + " based on historical averages.";
    }
}
