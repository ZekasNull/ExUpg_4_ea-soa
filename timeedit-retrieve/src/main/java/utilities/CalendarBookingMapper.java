package utilities;

import data_objects.CalendarEntry;
import data_objects.Reservation;
import data_objects.TimeEditResponseModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class CalendarBookingMapper {

    public static CalendarEntry[] convertResponseToCalendarEntry(TimeEditResponseModel input)
    {
        ArrayList<String> headers = new ArrayList<>(input.getColumnheaders());
        ArrayList<CalendarEntry> outputList = new ArrayList<>();

        for (Reservation r : input.getReservations())
        {
            Map<String, String> map = IntStream.range(0, headers.size())
                    .boxed()
                    .collect(Collectors.toMap(headers::get, i -> r.getColumns().get(i)));

            outputList.add(new CalendarEntry(r.getId(), r.getStartdate(), r.getStarttime(), r.getEnddate(), r.getEndtime(), map));
        }
        return outputList.toArray(new CalendarEntry[0]);
    }
}
