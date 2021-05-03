package com.daniyal.todo_mvvm.utilities;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class DateUtils {

    public static Date getDate(long time) {

        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        String date_ = formatter.format(new Date(time));

        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        try {
            Date date = format.parse(date_);
            return date;
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;

    }

    public static String getHour(long time) {

        SimpleDateFormat formatter = new SimpleDateFormat("hh:mm a");
        return formatter.format(new Date(time));
    }
}
