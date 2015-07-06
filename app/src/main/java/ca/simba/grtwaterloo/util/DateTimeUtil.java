package ca.simba.grtwaterloo.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import ca.simba.grtwaterloo.Constants;

/**
 * Provides methods that deal with dates and times to be used by other classes
 */
public class DateTimeUtil {

    // Constants
    private static final String WEEKDAYS_ALL = Constants.WEEKDAYS_ALL;
    private static final String SATURDAY = Constants.SATURDAY;
    private static final String SUNDAY = Constants.SUNDAY;

    public static String getServiceId() {
        // Returns a string representing the service code used for transit schedules based on the day of the week

        switch (Calendar.getInstance().get(Calendar.DAY_OF_WEEK)) { // gets current day of week
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
        Date time = Calendar.getInstance().getTime();
        SimpleDateFormat aFormat = new SimpleDateFormat("HH:mm:ss");
        String formattedDate = aFormat.format(time);
        return formattedDate;
    }

    public static String apply12HrTimeFormat(String time) {
        // converts times from 24hr hh:mm:ss format to 12hr hh:mm a format
        SimpleDateFormat currentFormat = new SimpleDateFormat("HH:mm:ss");
        SimpleDateFormat desiredFormat = new SimpleDateFormat("hh:mm a");
        Date mTime;
        String formattedTime;
        try {
            mTime = currentFormat.parse(time);
            formattedTime = desiredFormat.format(mTime);
        } catch (ParseException e) {
            formattedTime = "0:00 AM";
        }
        return formattedTime;
    };

    public static String setEndTime(int addedTime) {
        // calculates end of time interval by adding specified amount of time in hrs to current time
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.HOUR,addedTime);
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        String formattedDate = format.format(cal.getTime());
        return formattedDate;
    }
}
