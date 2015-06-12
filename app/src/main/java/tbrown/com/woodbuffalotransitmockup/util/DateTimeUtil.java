package tbrown.com.woodbuffalotransitmockup.util;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by tmast_000 on 5/29/2015.
 */
public class DateTimeUtil {
    // This class contains methods that deal with dates and times
    //   to be used by other classes

    private static final String WEEKDAYS_ALL = "'15SPRI-All-Weekday-01'";
    private static final String SATURDAY = "'15SPRI-All-Saturday-01'";
    private static final String SUNDAY = "'15SPRI-All-Sunday-01'";
    private static final String[] SERVICES = {WEEKDAYS_ALL,SATURDAY,SUNDAY};


    public static String getServiceId() {
        // Returns a string representing the service code used for transit
        //   schedules based on the day of the week

        Calendar cal = Calendar.getInstance();

        switch (cal.get(Calendar.DAY_OF_WEEK)) {
            case 1:
                return SUNDAY;
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
                return WEEKDAYS_ALL;
            case 7:
                return SATURDAY;
            default:
                return WEEKDAYS_ALL;
        }
    }

    public static String getCurrentTime() {
        // returns the current time in 24hr hh:mm:ss format
        Calendar cal = Calendar.getInstance();
        Date time = cal.getTime();
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        String formattedDate = format.format(time);
        return formattedDate;
    }

    public static String applyTimeFormat(Date date) {
        // converts times from 24hr hh:mm:ss format to 12hr hh:mm format
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("hh:mm a");
        String formattedDate = format.format(cal.getTime());
        return formattedDate;
    };

    public static String applyTimeFormat(String time) {
        // converts times from 24hr hh:mm:ss format to 12hr hh:mm format
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat currentFormat = new SimpleDateFormat("HH:mm:ss");
        SimpleDateFormat desiredFormat = new SimpleDateFormat("hh:mm a");
        Date mTime;
        String formattedTime;
        try {
            Log.i("MyActivity", time);
            mTime = currentFormat.parse(time);
            formattedTime = desiredFormat.format(mTime);
        } catch (ParseException e) {
            formattedTime = "Gotee";
        }
        return formattedTime;
    };

    public static String setEndTime(int addedTime) {
        // adds specified amount of time in hrs to current time
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.HOUR,addedTime);
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        String formattedDate = format.format(cal.getTime());
        return formattedDate;
    }
}
