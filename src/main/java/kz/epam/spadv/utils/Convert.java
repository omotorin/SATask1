package kz.epam.spadv.utils;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Created by Oleg_Motorin on 05.11.2015.
 */
public class Convert {
    public static Timestamp toTimestamp(LocalDateTime dateTime){
        if(dateTime!=null){
            return Timestamp.valueOf(dateTime);
        }
        return null;
    }

    public static Timestamp toTimestamp(LocalDate dateTime){
        if(dateTime!=null){
            return Timestamp.valueOf(dateTime.atStartOfDay());
        }
        return null;
    }
}
