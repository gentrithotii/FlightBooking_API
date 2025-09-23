package se.lexicon.flightbooking_api.service;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Component;
import se.lexicon.flightbooking_api.dto.AvailableFlightDTO;
import se.lexicon.flightbooking_api.dto.FlightBookingDTO;

import java.util.List;

@Component
public class FlightAssistantToolCalling {
    private final FlightBookingService flightBookingService;


    public FlightAssistantToolCalling(FlightBookingService flightBookingService) {
        this.flightBookingService = flightBookingService;
    }

    //Booking a flight
//    public FlightBookingDTO
    //Cancelling a flight
    // List of aviable flgihts
    @Tool(description = "Fetch all available flights")
    public List<AvailableFlightDTO> getAvailableFlights() {
        System.out.println("Hello");
        return flightBookingService.findAvailableFlights();
    }

}
