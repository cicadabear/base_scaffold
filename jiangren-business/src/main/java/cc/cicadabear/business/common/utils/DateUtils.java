package cc.cicadabear.business.common.utils;

import org.apache.commons.lang.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author Shengzhao Li
 */
public abstract class DateUtils {

    /**
     * Default time format :  yyyy-MM-dd HH:mm:ss
     */
    public static final String DEFAULT_DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    /**
     * Time format :  yyyy-MM-dd HH:mm
     */
    public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm";
    public static final String TIME_FORMAT = "HH:mm";

    /**
     * Default date format
     */
    public static final String DATE_FORMAT = "yyyy-MM-dd";
    /**
     * Default month format
     */
    public static final String MONTH_FORMAT = "yyyy-MM";
    /**
     * Default day format
     */
    public static final String DAY_FORMAT = "dd";

    public static final Long DAY_MILLISECONDS = 1000l * 60 * 60 * 24;

    public static final Long MINUTE_MILLISECONDS = 1000l * 60;

    //Date pattern,  demo:  2013-09-11
    public static final String DATE_PATTERN = "^[0-9]{4}\\-[0-9]{2}\\-[0-9]{2}$";


    /**
     * Private constructor
     */
    private DateUtils() {
    }


    public static boolean isDate(String dateAsText) {
        return StringUtils.isNotEmpty(dateAsText) && dateAsText.matches(DATE_PATTERN);
    }

    public static Date now() {
        return new Date();
    }

    public static String toDateText(Date date) {
        return toDateText(date, DATE_FORMAT);
    }

    public static String toDateText(Date date, String pattern) {
        if (date == null || pattern == null) {
            return null;
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        return dateFormat.format(date);
    }

    public static Date getDate(String dateText) {
        return getDate(dateText, DATE_FORMAT);
    }


    public static Date getDate(String dateText, String pattern) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        try {
            return dateFormat.parse(dateText);
        } catch (ParseException e) {
            throw new IllegalStateException("Parse date from [" + dateText + "," + pattern + "] failed", e);
        }
    }

    public static String toDateTime(Date date) {
        return toDateText(date, DATE_TIME_FORMAT);
    }


    /**
     * Return current year.
     *
     * @return Current year
     */
    public static int currentYear() {
        return calendar().get(Calendar.YEAR);
    }

    public static Calendar calendar() {
        return Calendar.getInstance();
    }

    public static boolean isToday(Date date) {
        if (date == null) {
            return false;
        }
        String dateAsText = toDateText(date);
        String todayAsText = toDateText(now());
        return dateAsText.equals(todayAsText);
    }

    private static long period(Date start, Date end) {
        if (start == null || end == null) {
            return -1;
        }
//        Date start1 = truncateDate(start);
//        Date end1 = truncateDate(end);

        long periodAsMilliSeconds = end.getTime() - start.getTime();
        return periodAsMilliSeconds;
    }

    /**
     * Get two dates period as days,
     * return -1 if the start or end is null
     *
     * @param start Start date
     * @param end   End date
     * @return Period as days or -1
     */
    public static long periodAsDays(Date start, Date end) {
        if (start == null || end == null) {
            return -1;
        }
        Date start1 = truncateDate(start);
        Date end1 = truncateDate(end);

        long periodAsMilliSeconds = end1.getTime() - start1.getTime();
        return periodAsMilliSeconds / (DAY_MILLISECONDS);
    }

    /**
     * Get two dates period as minutes,
     * return -1 if the start or end is null
     *
     * @param start Start date
     * @param end   End date
     * @return Period as days or -1
     */
    public static long periodAsMinutes(Date start, Date end) {
        return period(start, end) / (MINUTE_MILLISECONDS);
    }

    public static long periodAsSeconds(Date start, Date end) {
        return period(start, end) / 1000;
    }


    public static Date truncateDate(Date date) {
        return org.apache.commons.lang.time.DateUtils.truncate(date, Calendar.DAY_OF_MONTH);
    }


    /**
     * Plus days
     *
     * @param date     Base date
     * @param plusDays Plus days
     * @return New date after plus
     */
    public static Date plusDays(Date date, int plusDays) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, plusDays);
        return calendar.getTime();
    }

    /**
     * Plus minutes
     *
     * @param plusMinutes Plus days
     * @return New date after plus
     */
    public static Date plusMinutes(int plusMinutes) {
        return plusMinutes(new Date(), plusMinutes);
    }

    /**
     * Plus minutes
     *
     * @param date        Base date
     * @param plusMinutes Plus days
     * @return New date after plus
     */
    public static Date plusMinutes(Date date, int plusMinutes) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MINUTE, plusMinutes);
        return calendar.getTime();
    }
}
