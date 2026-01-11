package network;

import data_objects.TimeEditCalendarEntry;
import data_objects.TimeEditResponseModel;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;
import tools.jackson.databind.ObjectMapper;
import utilities.CalendarBookingMapper;

import java.util.logging.Logger;

public class TimeEditFetcher {
    private static final Logger logger = Logger.getLogger(TimeEditFetcher.class.getName());

    /**
     * Hämtar bokningar från TimeEdit.
     *
     * @param url en TimeEdit url som slutar med .json
     * @return en array av CalendarEntry-objekt
     */
    public static TimeEditCalendarEntry[] getTimeEditBookings(String url)
    {
        //minimal input validation
        if (!url.endsWith(".json"))
        {
            throw new IllegalArgumentException("Invalid URL");
        }

        //vars
        ObjectMapper mapper = new ObjectMapper();
        String json; //as string due to being wrapped in html
        TimeEditResponseModel timeEditResponse;
        try (Client client = ClientBuilder.newClient())
        {
            WebTarget webTarget = client.target(url);
            json = webTarget.request(MediaType.TEXT_PLAIN)
                            .get(String.class);
            timeEditResponse = mapper.readValue(json, TimeEditResponseModel.class);
            logger.info("Found " + timeEditResponse.getReservations()
                                                   .toArray().length + " reservations.");
        }

        return CalendarBookingMapper.convertResponseModelToCalendarEntry(timeEditResponse);
    }
}
