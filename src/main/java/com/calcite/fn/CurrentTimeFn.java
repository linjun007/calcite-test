package com.calcite.fn;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

public class CurrentTimeFn {
    public Timestamp eval() {
        return new Timestamp(System.currentTimeMillis());
    }

    public static long add(Timestamp timestamp, Integer day) {
        return 1;
    }

    public CurrentTimeFn(java.lang.Long cc, int day) {

    }

    public CurrentTimeFn() {

    }

    public static Long add(java.lang.Long timestamp, int day) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(timestamp);
        cal.add(Calendar.DAY_OF_YEAR, day);
        return cal.getTime().getTime();
    }
}
