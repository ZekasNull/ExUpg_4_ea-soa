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

