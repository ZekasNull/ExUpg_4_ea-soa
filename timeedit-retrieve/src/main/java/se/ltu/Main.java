package se.ltu;

import data_objects.TimeEditCalendarEntry;
import data_objects.CanvasCalendarEntry;
import data_objects.TimeEditResponseModel;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.Form;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import tools.jackson.databind.ObjectMapper;
import utilities.CalendarBookingMapper;
import utilities.TimeEditFetcher;

import java.time.*;
import java.time.format.DateTimeFormatter;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {

    public static void main(String[] args)
    {
//        testCalendarEventPOST();
        testJSONFetchAndConversion2();
    }

    private static void testCalendarEventPOST()
    {
        //targets
        String TargetURL = "https://canvas.ltu.se/api/v1/calendar_events";
        Client client = ClientBuilder.newClient();
        WebTarget webTarget = client.target(TargetURL);
        System.out.println(webTarget.getUri()); //debug - url faulty?

        //skapa nytt calendarevent
        CanvasCalendarEntry event = new CanvasCalendarEntry();
        event.setContext_code("group_187819"); //vår canvasgrupp för uppgiften
        event.setTitle("Test-POST till Canvas");
        ZonedDateTime startDateTime = ZonedDateTime.of(LocalDate.of(2026, 1, 23), LocalTime.of(13, 0), ZoneId.systemDefault());
        ZonedDateTime endDateTime = ZonedDateTime.of(LocalDate.of(2026, 1, 23), LocalTime.of(14, 0), ZoneId.systemDefault());
        event.setStart_at(startDateTime);
        event.setEnd_at(endDateTime);

        System.out.println(event.getStart_at()
                                .format(DateTimeFormatter.ISO_INSTANT));
        //transform to form
        Form form = new Form();
        form.param("calendar_event[context_code]", event.getContext_code());
        form.param("calendar_event[title]", event.getTitle());
        form.param("calendar_event[start_at]", event.getStart_at()
                                                    .format(DateTimeFormatter.ISO_INSTANT));
        form.param("calendar_event[end_at]", event.getEnd_at()
                                                  .format(DateTimeFormatter.ISO_INSTANT));

        //attempt post
        Response response = webTarget.request(MediaType.APPLICATION_JSON_TYPE)
                                     .header("Authorization", "Bearer 3755~hnFnnRrcJBXuf7GLtNJx4hQ2N6NFThyTwP62tXJzLvFBZextheahYZ7JxmXBeVGc")
                                     .post(Entity.form(form));
        System.out.println("Felkod: " + response.getStatus() + ", meddelande: " + response.getStatusInfo());
        //System.out.println(response.readEntity(String.class));
    }

    /**
     * Försöker hämta TimeEdit's JSON och läsa hur många reservationer som hittades.
     * Endast för testning.
     */
    private static void testJSONFetchAndConversion()
    {
        //setup vars
        String url = "https://cloud.timeedit.net/ltu/web/schedule1/ri106956X27Z0XQ6Z76g5Y35y4006Y34507gQY6Q557645616YQ637.json"; //d0031n + d0023e over lp2, expected reservations: 32
        String url2 = "https://cloud.timeedit.net/ltu/web/schedule1/ri166956X58Z0XQ6Z76g5Y35y4006Y34507gQY6Q567640616YQ637.json"; //24th dec -> 1st jan 2026, expected reservations: 5
        Client client = ClientBuilder.newClient();
        WebTarget webTarget = client.target(url);

        //get json
        String json = webTarget.request(MediaType.TEXT_PLAIN)
                               .get(String.class);
        //System.out.println(json); //debug

        //get json v2 - does not work BECAUSE: the response is JSON wrapped in HTML?! ok then, i guess we're processing raw strings
        //TimeEditResponseModel response = webTarget.request(MediaType.APPLICATION_JSON_TYPE).get(TimeEditResponseModel.class);

        //deserialise
        ObjectMapper mapper = new ObjectMapper();
        TimeEditResponseModel response = mapper.readValue(json, TimeEditResponseModel.class);

        //check
        System.out.println("Number of reservations found is " + response.getReservations()
                                                                        .toArray().length);

        TimeEditCalendarEntry[] testConversion = CalendarBookingMapper.convertResponseToCalendarEntry(response);

        for (TimeEditCalendarEntry c : testConversion)
        {
            System.out.println(c.toString());
        }
    }

    private static void testJSONFetchAndConversion2()
    {
        String url = "https://cloud.timeedit.net/ltu/web/schedule1/ri106956X27Z0XQ6Z76g5Y35y4006Y34507gQY6Q557645616YQ637.json";

        TimeEditCalendarEntry[] testFetch = TimeEditFetcher.getTimeEditBookings(url);

        for (TimeEditCalendarEntry c : testFetch)
        {
            System.out.println(c.toString());
        }
    }

}