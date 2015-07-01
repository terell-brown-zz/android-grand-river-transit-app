package tbrown.com.woodbuffalotransitmockup.util;

import tbrown.com.woodbuffalotransitmockup.Constants;

/**
 * Provides general purpose methods for use by other classes
 */
public class Utilities {

    public static int getSeparatingIndex(String info) {
        return info.indexOf(" ");
    }

   public static int getCurrentSpinnerItem(String serviceId) {
       switch(serviceId) {
           case Constants.WEEKDAYS_ALL:
               return Constants.SPINNER_WEEKDAYS_ALL;
           case Constants.SATURDAY:
               return Constants.SPINNER_SATURDAY;
           case Constants.SUNDAY:
               return Constants.SPINNER_SUNDAY;
           default:
               return Constants.SPINNER_WEEKDAYS_ALL;
       }
   }
}
