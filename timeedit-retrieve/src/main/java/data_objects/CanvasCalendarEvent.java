package data_objects;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;

public class CanvasCalendarEvent {
    // context_code: (krävs) Entiteten (användare/grupp) som kalenderhändelsen knyts till, exempelvis "user_123" eller "course_456"
    // Title: Tekniskt valfri, men orsakar bad request om den inte finns
    // start_at och end_at: Tekniskt valfri men skapar ett "spökevent" som inte syns om båda är null

    //obligatoriska
    private String context_code;           // Entitetskod, typ "user_123" eller "course_456"
    private String title;
    private ZonedDateTime start_at;
    private ZonedDateTime end_at;

    //Valfria
    private String description;
    private String location_name;          // lokalnamn/zoomrum
    private String location_address;
    private String time_zone_edited;       // exempelvis "Europe/Stockholm"
    private Boolean all_day;               // (osäker på hur det interagerar med start_at och end_at, exclusive or?)
    private String child_event_data;       // för återkommande calendar_events
    private Boolean duplicate;             // osäker på hur den används
    private String rrule;                  // "recurrence rule (iCalendar format)" - osäker på om vi behöver den, men inkluderar ändå

    // Getters and setters
    public String getContext_code() { return context_code; }
    public void setContext_code(String context_code) { this.context_code = context_code; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public ZonedDateTime getStart_at() { return start_at; }
    public void setStart_at(ZonedDateTime start_at) { this.start_at = start_at; }

    public ZonedDateTime getEnd_at() { return end_at; }
    public void setEnd_at(ZonedDateTime end_at) { this.end_at = end_at; }

    public String getLocation_name() { return location_name; }
    public void setLocation_name(String location_name) { this.location_name = location_name; }

    public String getLocation_address() { return location_address; }
    public void setLocation_address(String location_address) { this.location_address = location_address; }

    public String getTime_zone_edited() { return time_zone_edited; }
    public void setTime_zone_edited(String time_zone_edited) { this.time_zone_edited = time_zone_edited; }

    public Boolean getAll_day() { return all_day; }
    public void setAll_day(Boolean all_day) { this.all_day = all_day; }

    public String getChild_event_data() { return child_event_data; }
    public void setChild_event_data(String child_event_data) { this.child_event_data = child_event_data; }

    public Boolean getDuplicate() { return duplicate; }
    public void setDuplicate(Boolean duplicate) { this.duplicate = duplicate; }

    public String getRrule() { return rrule; }
    public void setRrule(String rrule) { this.rrule = rrule; }
}
