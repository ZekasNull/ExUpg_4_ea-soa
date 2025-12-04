package se.ltu;

import data_objects.TimeEditResponseModel;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;
import tools.jackson.databind.ObjectMapper;

import java.util.ArrayList;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {

    public static void main(String[] args)
    {
        testJSONFetch();
    }

    /**
     * Försöker hämta TimeEdit's JSON och läsa hur många reservationer som hittades.
     * Endast för testning.
     */
    private static void testJSONFetch()
    {
        //setup vars
        String url = "https://cloud.timeedit.net/ltu/web/schedule1/ri106956X27Z0XQ6Z76g5Y35y4006Y34507gQY6Q557645616YQ637.json"; //d0031n + d0023e over lp2, expected reservations: 32
        String url2 = "https://cloud.timeedit.net/ltu/web/schedule1/ri166956X58Z0XQ6Z76g5Y35y4006Y34507gQY6Q567640616YQ637.json"; //24th dec -> 1st jan 2026, expected reservations: 5
        Client client = ClientBuilder.newClient();
        WebTarget webTarget = client.target(url2);

        //get json
        String json = webTarget.request(MediaType.TEXT_PLAIN).get(String.class);
        System.out.println(json); //debug

        //get json v2 - does not work BECAUSE: the response is JSON wrapped in HTML?! ok then, i guess we're processing raw strings
        //TimeEditResponseModel response = webTarget.request(MediaType.APPLICATION_JSON_TYPE).get(TimeEditResponseModel.class);

        //deserialise
        ObjectMapper mapper = new ObjectMapper();
        TimeEditResponseModel response = mapper.readValue(json, TimeEditResponseModel.class);

        //check
        System.out.println("Number of reservations found is "+response.getReservations().toArray().length);
    }
}