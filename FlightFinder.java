import java.time.LocalDateTime;

import java.time.ZonedDateTime;

import java.util.Arrays;

import java.util.ArrayList;

import java.util.HashMap;

import java.util.List;

import java.util.Map;

import java.util.NavigableSet;

import java.util.Set;

import java.util.TreeSet;

import java.time.ZoneId;

import java.time.Duration;

import java.time.Instant;

import java.time.LocalDate;

import java.time.LocalTime;

import java.time.temporal.ChronoField;

public class FlightFinder {

private Map<String, List<Flight>> allFlights = new HashMap<>();

public FlightFinder(Map<String, List<Flight>> allFlights) {

 this.allFlights = allFlights;

}

public List<NavigableSet<Flight>> findFlights(int dayOfMonth, int month, int year,

        int preferredDepartureStartHour, int preferredDepartureEndHour,

        String departureCity, String arrivalCity, String finalArrivalCity,

  String departureCityTimeZone, String arrivalCityTimeZone) {

 

 List<NavigableSet<Flight>> result = new ArrayList<>();

       

       // Step 1: Construct ZonedDateTime objects to represent User-specified time interval when flights depart

                  // Your code

       LocalTime startTime = LocalTime.of(preferredDepartureStartHour, 00, 00);

       LocalDate startDate = LocalDate.of(year, month, dayOfMonth);

       LocalDateTime startDepartureTime = LocalDateTime.of(startDate, startTime);

       

       LocalTime endTime = LocalTime.of(preferredDepartureEndHour, 00, 00);

       LocalDate endDate = LocalDate.of(year, month, dayOfMonth);

       LocalDateTime endDepartureTime = LocalDateTime.of(endDate, endTime);

       ZonedDateTime startDepartureTime2 = ZonedDateTime.of(startDepartureTime, ZoneId.of(departureCityTimeZone));

       ZonedDateTime endDepartureTime2 = ZonedDateTime.of(endDepartureTime,ZoneId.of(arrivalCityTimeZone));

       // Step 2: Find departing flights at departureCity

       List<Flight> allDepartingFlights = allFlights.get(departureCity);

       

       NavigableSet<Flight> departingflights = new TreeSet<>();

       

                  // Your code

                  // Tip: Methods like isAfter can be used to find flights in the specified user time interval

       for(int i=0;i<allDepartingFlights.size();i++){

           Flight flight = allDepartingFlights.get(i);

           if ( startDepartureTime.isBefore(flight.getDepartureTime())&& endDepartureTime.isAfter(flight.getDepartureTime()) && flight.getArrivalCity().equalsIgnoreCase(arrivalCity) ){

               // System.out.println(flight.getDepartureTime() + flight.getArrivalCity() + startDepartureTime);

               departingflights.add(flight);

           }

       }

       System.out.println(departingflights);

       result.add(departingflights);

       

       // Step 3: Find connecting flights

       //   Constraint 1: Departing at arrivalCity (e.g., Dubai) and arrive at finalArrivalCity (e.g., Mumbai)

       //   Constraint 2: Should start at least two hours after the arrival time of the first flight in the above navigable set

       List<Flight> allConnectingFlights = allFlights.get(arrivalCity);        

       NavigableSet<Flight> departingflights2 = departingflights;

       NavigableSet<Flight> connectingflights = new TreeSet<>();      

       

                   // Your code  

       for(Flight f: departingflights2){

           // Flight flight1 = departingflights2.pollFirst();

           int arrivalTime = f.getArrivalTime().getHour();

           for(int i=0;i<allConnectingFlights.size();i++){

Flight flight2 = allConnectingFlights.get(i);

               int departureTime = flight2.getDepartureTime().getHour();

               int temp = (departureTime-arrivalTime);

               if(flight2.getArrivalCity().equalsIgnoreCase(finalArrivalCity)&& flight2.getDepartureCity().equalsIgnoreCase(arrivalCity) && temp>=2){

                   connectingflights.add(flight2);

                   // System.out.println(flight2.getDepartureTime() + flight2.getArrivalCity()+ flight2);

               }

           }

       }

       System.out.println(connectingflights);

       

       System.out.println(departingflights);

       result.add(connectingflights);

       System.out.println(result);

       return result;

}

public static void main(String[] args) {

    Flight f1 = new Flight(1, "1", "Emirates", "New York", "Dubai",

  LocalDateTime.of(2017, 12, 20, 9, 0), LocalDateTime.of(2017, 12, 20, 9, 0));

 Flight f2 = new Flight(2, "2", "Delta", "San Francisco", "Paris",

   LocalDateTime.of(2017, 12, 20, 9, 0), LocalDateTime.of(2017, 12, 20, 9, 0));

 Flight f3 = new Flight(3, "3", "Delta", "San Francisco", "Rome",

   LocalDateTime.of(2017, 12, 20, 9, 0), LocalDateTime.of(2017, 12, 20, 9, 0));

 Flight f4 = new Flight(4, "4", "Emirates", "San Francisco", "Dubai",

   LocalDateTime.of(2017, 12, 20, 8, 0), LocalDateTime.of(2017, 12, 20, 8, 0));

 

 Map<String, List<Flight>> allFlights = new HashMap<>();

 

 allFlights.put("New York", Arrays.asList(f1));

 allFlights.put("San Francisco", Arrays.asList(f2, f3, f4));

 

 List<NavigableSet<Flight>> result = new FlightFinder(allFlights).findFlights(20, 12, 2017, 9, 13, "San Francisco", "Dubai", "Mumbai", "America/Los_Angeles", "Asia/Dubai");

}

}