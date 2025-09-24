package se.lexicon.flightbooking_api.service;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Component;
import se.lexicon.flightbooking_api.dto.AvailableFlightDTO;
import se.lexicon.flightbooking_api.dto.BookFlightRequestDTO;
import se.lexicon.flightbooking_api.dto.FlightBookingDTO;

import java.util.List;

@Component
public class FlightAssistantToolCalling {
    private final FlightBookingService flightBookingService;


    public FlightAssistantToolCalling(FlightBookingService flightBookingService) {
        this.flightBookingService = flightBookingService;
    }

    //Booking a flight
    @Tool(description = "Book flight for customer")
    public FlightBookingDTO bookFlight(long flightId, BookFlightRequestDTO flightBookingDTO) {
        System.out.println("Book flight Function running!");
        return flightBookingService.bookFlight(flightId, flightBookingDTO);
    }

    //Cancelling a flight

    @Tool(description = "Cancel flight for customer")
    public void cancelFlight(Long flightId, String passengerEmail) {
        flightBookingService.cancelFlight(flightId, passengerEmail);
    }

    //Check bookings by email
    @Tool(description = "Check the booking of customer by email ")
    public List<FlightBookingDTO> findBookingsByEmailAi(String email) {
        return flightBookingService.findBookingsByEmail(email);
    }

    // List of available flights
//    @Tool(description = "Fetch all available flights")
//    public List<AvailableFlightDTO> getAvailableFlights() {
//        System.out.println("Fetch all Flights running");
//        return flightBookingService.findAvailableFlights();
//    }


}
