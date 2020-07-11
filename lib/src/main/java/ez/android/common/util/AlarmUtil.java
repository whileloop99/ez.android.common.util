package ez.android.common.util;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

/**
 * Alarm utilities
 * https://proandroiddev.com/android-alarmmanager-as-deep-as-possible-909bd5b64792
 *
 * To preserve battery life, Android has changed the policy on handling repeating alarm since Android 5.1 (API 22).
 * It delays the alarm at least 5 seconds into the future and limit the repeat interval to at least 60 seconds.
 */
public class AlarmUtil {
    /**
     * Trigger an alarm immediately
     * @param context
     * @param pendingIntent
     */
    public static void setImmediately(Context context, PendingIntent pendingIntent) {
        setExact(context, pendingIntent, 0);
    }

    /**
     * Trigger an alarm immediately
     * @param context
     * @param clazz
     */
    public static void setImmediately(Context context, Class<?> clazz) {
        Intent i = new Intent(context, clazz);
        PendingIntent pi = PendingIntent.getBroadcast(context, 0, i, PendingIntent.FLAG_UPDATE_CURRENT);
        setExact(context, pi, 0);
    }

    /**
     * Trigger an alarm exactly
     * @param context
     * @param pendingIntent
     * @param nextTrigger
     */
    public static void setExact(Context context, PendingIntent pendingIntent, long nextTrigger) {
        AlarmManager manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        manager.setAlarmClock(new AlarmManager.AlarmClockInfo(nextTrigger, pendingIntent), pendingIntent);
    }

    /**
     * Cancel an alarm
     * @param context
     * @param pendingIntent
     */
    public static void cancel(Context context, PendingIntent pendingIntent) {
        AlarmManager manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        manager.cancel(pendingIntent);
    }
}
