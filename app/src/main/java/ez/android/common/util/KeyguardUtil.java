package ez.android.common.util;

import android.app.Activity;
import android.app.KeyguardManager;
import android.content.Context;
import android.os.Build;

import androidx.annotation.RequiresApi;

/**
 * Keyguard utilities
 */
public class KeyguardUtil {

    /**
     * Request unlock the device
     * @param activity
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static void requestDismissKeyguard(Activity activity) {
        KeyguardManager keyguardManager = (KeyguardManager) activity.getSystemService(Context.KEYGUARD_SERVICE);
        if(keyguardManager.isKeyguardLocked()) {
            keyguardManager.requestDismissKeyguard(activity, null);
        }
    }
}
