package data_objects;

import java.util.List;

/**
 * Representerar det deserialiserade svaret från TimeEdit
 */
public class TimeEditResponseModel {
    private List<String> columnheaders;
    private Info info;
    private List<Reservation> reservations;

    //region getters&setters
    public List<String> getColumnheaders()
    {
        return columnheaders;
    }

    public void setColumnheaders(List<String> columnheaders)
    {
        this.columnheaders = columnheaders;
    }

    public Info getInfo()
    {
        return info;
    }

    public void setInfo(Info info)
    {
        this.info = info;
    }

    public List<Reservation> getReservations()
    {
        return reservations;
    }

    public void setReservations(List<Reservation> reservations)
    {
        this.reservations = reservations;
    }
    //endregion
}

/**
 * Info-entiteten från TimeEdit. Kommer förmodligen inte användas?
 */
class Info { //helt osäker på vad den här är relevant för, men behåller utifall att
    private int reservationlimit;
    private int reservationcount;

    //region getters&setters
    public int getReservationlimit()
    {
        return reservationlimit;
    }

    public void setReservationlimit(int reservationlimit)
    {
        this.reservationlimit = reservationlimit;
    }

    public int getReservationcount()
    {
        return reservationcount;
    }

    public void setReservationcount(int reservationcount)
    {
        this.reservationcount = reservationcount;
    }
    //endregion
}

/**
 * Representerar en individuell bokning
 */
class Reservation {
    private String id;
    private String startdate;
    private String starttime;
    private String enddate;
    private String endtime;
    private List<String> columns;

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

    public List<String> getColumns()
    {
        return columns;
    }

    public void setColumns(List<String> columns)
    {
        this.columns = columns;
    }
    //endregion
}