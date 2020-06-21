package ez.android.common.util;

import android.content.Context;
import android.content.Intent;

/**
 *
 */
public class ActivityUtil {
    /**
     *
     * @param context
     * @param clazz
     */
    public static void startActivity(Context context, Class clazz) {
        Intent intent = new Intent(context, clazz);
        context.startActivity(intent);
    }
}
