package utility;

import data_objects.CanvasCalendarEntry;
import data_objects.TimeEditCalendarEntry;
import data_objects.Reservation;
import data_objects.TimeEditResponseModel;
import network.TimeEditFetcher;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class CalendarBookingMapper {
    private static final ArrayList<String> skippedKeyValues = new ArrayList<>(List.of("Aktivitet", "Plats, Lokal"));

    public static TimeEditCalendarEntry[] convertResponseModelToCalendarEntry(TimeEditResponseModel input)
    {
        ArrayList<String> headers = new ArrayList<>(input.getColumnheaders());
        ArrayList<TimeEditCalendarEntry> outputList = new ArrayList<>();

        for (Reservation r : input.getReservations())
        {
            Map<String, String> map = IntStream.range(0, headers.size())
                    .boxed()
                    .collect(Collectors.toMap(headers::get, i -> r.getColumns().get(i)));

            outputList.add(new TimeEditCalendarEntry(r.getId(), r.getStartdate(), r.getStarttime(), r.getEnddate(), r.getEndtime(), map));
        }
        return outputList.toArray(new TimeEditCalendarEntry[0]);
    }

    /**
     * Tar en kalenderhändelse av TimeEdits format och konverterar till Canvas format.
     *
     * @param input
     * @return
     */
    public static CanvasCalendarEntry convertTimeEditCalendarToCanvasCalendar(TimeEditCalendarEntry input)
    {
        CanvasCalendarEntry entry = new CanvasCalendarEntry();
        StringBuilder descriptionBlob = new StringBuilder();
        Map<String, String> details = input.getDetailedInformation();
        ArrayList<String> skippedKeys = new ArrayList<>();

        //prep datetimes
        LocalDateTime startDateTime = LocalDateTime.parse(input.getStartdate() + "T" + input.getStarttime() + ":00");
        LocalDateTime endDateTime = LocalDateTime.parse(input.getEnddate() + "T" + input.getEndtime() + ":00");

        ZonedDateTime startdatetime = ZonedDateTime.of(startDateTime, ZoneId.systemDefault());
        ZonedDateTime enddatetime = ZonedDateTime.of(endDateTime, ZoneId.systemDefault());

        //fill in new object values
        entry.setStart_at(startdatetime);
        entry.setEnd_at(enddatetime);
        entry.setTitle(details.get("Aktivitet"));
        entry.setLocation_name(details.get("Plats, Lokal"));


        for (String key : details.keySet())
        {
            if (skippedKeyValues.contains(key)) continue; //skip used value
            if (details.get(key)
                       .isEmpty()) continue;

            descriptionBlob.append(key)
                           .append(": ")
                           .append(details.get(key))
                           .append("\n");

        }
        entry.setDescription(descriptionBlob.toString());

        return entry;
    }

    public static void main(String[] args)
    {
        //Hämtar exakt en kalenderhändelse och försöker konvertera den, som ett test.
        TimeEditCalendarEntry[] test = TimeEditFetcher.getTimeEditBookings("https://cloud.timeedit.net/ltu/web/schedule1/ri156956X00Z5XQ6Z76g5Y35y4006Y34507gQY6Q557645696YQ637.json");

        CanvasCalendarEntry test2 = CalendarBookingMapper.convertTimeEditCalendarToCanvasCalendar(test[0]);

        System.out.println(test2);

    }
}
