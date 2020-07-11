package ez.android.common.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;

import androidx.annotation.RequiresPermission;

/**
 * Vibrate utilities
 */
public class VibrateUtil {

    /**
     *
     * @param context
     * @param durationInMs
     */
    @RequiresPermission(android.Manifest.permission.VIBRATE)
    public static void vibrateOneShot(Context context, long durationInMs) {
        Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator.vibrate(VibrationEffect.createOneShot(durationInMs, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            //deprecated in API 26
            vibrator.vibrate(durationInMs);
        }
    }
}
