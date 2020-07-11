package ez.android.common.util;

import android.content.Context;
import android.content.Intent;

/**
 * Activity utilities
 */
public class ActivityUtil {
    /**
     * Start an activity with common flags
     * @param context
     * @param clazz
     */
    public static void startActivity(Context context, Class clazz) {
        Intent intent = new Intent(context, clazz);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        context.startActivity(intent);
    }
}
