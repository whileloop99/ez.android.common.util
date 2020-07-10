package ez.android.common.util;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.annotation.NonNull;

/**
 * Service utilities
 */
public class ServiceUtil {

    /**
     * Check if target service is running
     * @param context
     * @param serviceClass
     * @return
     */
    public static boolean isRunning(@NonNull Context context, @NonNull Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Start a foreground service
     * @param context
     * @param serviceClass
     */
    public static void startForeground(@NonNull Context context, @NonNull Class<?> serviceClass) {
        Intent intent = new Intent(context, serviceClass);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(intent);
        } else {
            context.startService(intent);
        }
    }

    /**
     * Stop a service
     * @param context
     */
    public static void stop(@NonNull Context context, @NonNull Class<?> serviceClass) {
        Intent intent = new Intent(context, serviceClass);
        context.stopService(intent);
    }
}
