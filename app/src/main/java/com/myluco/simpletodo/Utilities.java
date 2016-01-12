package com.myluco.simpletodo;

import android.content.res.Resources;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by lcc on 1/6/16.
 */
public class Utilities {

    private static  String NOT_FINISHED = TodoActivity.getAppContext().getString(R.string.notFinished_label);
    //private static  String NOT_FINISHED = "Not Finished";
    private static SimpleDateFormat dateFormatter =new SimpleDateFormat("MM-dd-yyyy", Locale.US); ;
    public static String NotFinished() {
        return NOT_FINISHED;
    }
    public static String dateStringFromLong(long date) {
        String value = dateFormatter.format(date);
        return value;
    }
    public static long longFromDateString(String dateString) {
         Date date = null;
            try {
                date = dateFormatter.parse(dateString);
            } catch (ParseException e) {
                date = null;
                Log.e("Utilities-longfromDate", "date input wrong - " +dateString );
            }
        long longDate;
        if (date != null) {
            longDate = date.getTime();
        } else {
            longDate = 0;

        }
        return longDate;
    }
}
