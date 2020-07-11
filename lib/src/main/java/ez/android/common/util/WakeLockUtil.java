package ez.android.common.util;

import android.content.Context;
import android.os.PowerManager;

/**
 * WakeLock utilities
 */
public class WakeLockUtil {
    private static String TAG = "WakeLockUtil:DEFAULT";

    /**
     * Quickly acquire a wake lock with default lock tag
     * @param context
     */
    public static synchronized PowerManager.WakeLock acquire(Context context) {
        return acquire(context, TAG);
    }

    /**
     * Acquire a wake lock
     * @param context
     * @param tag
     * @return
     */
    public static synchronized PowerManager.WakeLock acquire(Context context, String tag) {
        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        PowerManager.WakeLock mLock = pm.newWakeLock(
//                PowerManager.SCREEN_BRIGHT_WAKE_LOCK
//                        | PowerManager.FULL_WAKE_LOCK
//                        |
                        PowerManager.PARTIAL_WAKE_LOCK
                        | PowerManager.ACQUIRE_CAUSES_WAKEUP
                , tag);
        mLock.setReferenceCounted(true);
//        if(!mLock.isHeld()) {
            mLock.acquire();
//        }
        return mLock;
    }
}
