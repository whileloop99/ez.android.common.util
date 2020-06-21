package ez.android.common.util;

import org.joda.time.Period;

import java.util.Calendar;

public class DateTimeUtil {
    /**
     * Check if time in range
     * @param timeToCheck 例　10：00
     * @param timeRange 例　9：30 〜 10：30
     * @return
     */
    public static boolean isTimeIntersect(String timeToCheck, String timeRange, String timeSeparator, String rangeSeparator) {
        int time = Integer.parseInt(timeToCheck.replace(timeSeparator,""));
        String[] timeRanges = timeRange.split(rangeSeparator);
        int timeRangeStart = Integer.parseInt(timeRanges[0].replace(timeSeparator,""));
        int timeRangeEnd = Integer.parseInt(timeRanges[1].replace(timeSeparator,""));

        return timeRangeStart < timeRangeEnd && time >= timeRangeStart && time <= timeRangeEnd
                || timeRangeStart > timeRangeEnd && (time >= timeRangeStart && time <= 2400 || time <= timeRangeEnd);
    }

    /**
     * Check if time in range
     * @param timeToCheck
     * @param timeRange
     * @return
     */
    public static boolean isTimeIntersect(String timeToCheck, String timeRange) {
        return isTimeIntersect(timeToCheck, timeRange, "：", " 〜 ");
    }

    /**
     * Get next time with given period
     * @param purchaseTime
     * @param period
     * @return
     */
    public static long getNextPeriodTime(long purchaseTime, String period) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(purchaseTime);
        int days = Period.parse(period).toStandardDays().getDays();
        calendar.add(Calendar.DAY_OF_MONTH, days);
        return calendar.getTimeInMillis();
    }
}
