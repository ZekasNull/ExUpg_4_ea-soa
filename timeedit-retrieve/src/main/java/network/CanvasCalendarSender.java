package network;

import data_objects.CanvasCalendarEntry;
import data_objects.TimeEditCalendarEntry;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.Form;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import utilities.CalendarBookingMapper;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class CanvasCalendarSender {
    private static final String API_TOKEN = "";//TODO ladda från proper config
    private static final String CALENDARENDPOINTURL = "https://canvas.ltu.se/api/v1/calendar_events";

    /**
     * Försöker skicka en kalenderhändelse av TimeEdit-format till Canvas genom att först konvertera händelsen till Canvasformat.
     *
     * @param event Kalenderhändelsen som ska skickas
     * @return true om händelsen skickades
     */
    public static boolean sendTimeEditCalendarEntryToCanvas(TimeEditCalendarEntry event)
    {
        CanvasCalendarEntry booking = CalendarBookingMapper.convertTimeEditCalendarToCanvasCalendar(event);
        return SendCanvasCalendarEntryToCanvas(booking);
    }

    /**
     * Försöker skicka en kalenderhändelse av Canvas-format till Canvas.
     *
     * @param event Kalenderhändelsen som ska skickas
     * @return true om händelsen skickades
     */
    public static boolean SendCanvasCalendarEntryToCanvas(CanvasCalendarEntry event)
    {
        //vars
        Form form = new Form();
        Response response;

        //validate
        if (API_TOKEN.isEmpty())
            throw new IllegalStateException("CanvasCalendarSender must have API-token to function.");
        if (event.getContext_code()
                 .isEmpty())
            throw new IllegalArgumentException("Context code cannot be empty"); //validering för att garantera att målkalender finns

        //prepare form
        form.param("calendar_event[context_code]", event.getContext_code());
        form.param("calendar_event[title]", event.getTitle());
        form.param("calendar_event[start_at]", event.getStart_at()
                                                    .format(DateTimeFormatter.ISO_INSTANT));
        form.param("calendar_event[end_at]", event.getEnd_at()
                                                  .format(DateTimeFormatter.ISO_INSTANT));
        form.param("calendar_event[description]", event.getDescription());
        form.param("calendar_event[location_address]", event.getLocation_address());

        try (Client client = ClientBuilder.newClient())
        {
            WebTarget webTarget = client.target(CALENDARENDPOINTURL);
            response = webTarget.request(MediaType.APPLICATION_JSON_TYPE)
                                .header("Authorization", "Bearer " + API_TOKEN)
                                .post(Entity.form(form));
        }

        return response.getStatus() == 201; //förväntat resultat är 201 Created
    }

    public static void main(String[] args)
    {
        CanvasCalendarEntry event = new CanvasCalendarEntry();
        event.setContext_code("group_187819"); //vår canvasgrupp för uppgiften
        event.setTitle("Test-POST till Canvas");
        ZonedDateTime startDateTime = ZonedDateTime.of(LocalDate.of(2026, 1, 23), LocalTime.of(13, 0), ZoneId.systemDefault());
        ZonedDateTime endDateTime = ZonedDateTime.of(LocalDate.of(2026, 1, 23), LocalTime.of(14, 0), ZoneId.systemDefault());
        event.setStart_at(startDateTime);
        event.setEnd_at(endDateTime);

        CanvasCalendarSender.SendCanvasCalendarEntryToCanvas(event);
    }
}
