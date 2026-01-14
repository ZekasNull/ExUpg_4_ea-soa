package data_objects;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

import java.util.Map;

/**
 * Format för ett CalendarEntry. Hanteras även av transfer-applikationen internt.
 */
public class TimeEditCalendarEntry {
    private String id;
    private String startdate;
    private String starttime;
    private String enddate;
    private String endtime;
    private Map<String, String> detailedInformation;

    private BooleanProperty vald = new SimpleBooleanProperty(false);

    public TimeEditCalendarEntry()
    {

    }

    public TimeEditCalendarEntry(String id, String startdate, String starttime, String enddate, String endtime, Map<String, String> columns)
    {
        this.id = id;
        this.startdate = startdate;
        this.starttime = starttime;
        this.enddate = enddate;
        this.endtime = endtime;
        this.detailedInformation = columns;
    }
    //region getters&setters
    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getStartdate()
    {
        return startdate;
    }

    public void setStartdate(String startdate)
    {
        this.startdate = startdate;
    }

    public String getStarttime()
    {
        return starttime;
    }

    public void setStarttime(String starttime)
    {
        this.starttime = starttime;
    }

    public String getEnddate()
    {
        return enddate;
    }

    public void setEnddate(String enddate)
    {
        this.enddate = enddate;
    }

    public String getEndtime()
    {
        return endtime;
    }

    public void setEndtime(String endtime)
    {
        this.endtime = endtime;
    }

    public Map<String, String> getDetailedInformation()
    {
        return detailedInformation;
    }

    public void setDetailedInformation(Map<String, String> detailedInformation)
    {
        this.detailedInformation = detailedInformation;
    }

    public BooleanProperty getVald() {
        return vald;
    }

    public boolean isVald() {
        return vald.get();
    }

    public void setVald(boolean selected) {
        this.vald.set(selected);
    }

    //endregion


    @Override
    public String toString() {
        return "CalendarEntry\n" +
                "{\n" +
                "id='" + id + "',\n" +
                "startdate='" + startdate + "',\n" +
                "starttime='" + starttime + "',\n" +
                "enddate='" + enddate + "',\n" +
                "endtime='" + endtime + "',\n" +
                "detailedInformation=" + detailedInformation +
                "\n}";
    }
}
