package ez.android.common.util;

import android.content.Context;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import static android.content.Context.SENSOR_SERVICE;

/**
 * Sensor utilities
 */
public class SensorUtil {
    /**
     * Register
     * @param context
     * @param sensorType
     * @param delay
     * @param listener
     */
    public static void register(Context context, int sensorType, int delay, SensorEventListener listener) {
        SensorManager sensorManager = getManager(context);
        sensorManager.registerListener(
                listener,
                sensorManager.getDefaultSensor(sensorType), delay);
    }

    /**
     * Unregister
     * @param context
     * @param listener
     */
    public static void unregister(Context context, SensorEventListener listener) {
        SensorManager sensorManager = getManager(context);
        sensorManager.unregisterListener(listener);
    }

    private static SensorManager getManager(Context context) {
        SensorManager sensorManager = (SensorManager) context.getSystemService(SENSOR_SERVICE);
        return sensorManager;
    }
}
