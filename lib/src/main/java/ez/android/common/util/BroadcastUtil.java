package ez.android.common.util;

import android.content.Context;
import android.content.Intent;

import androidx.annotation.Nullable;

import java.io.Serializable;
import java.util.HashMap;

/**
 *
 */
public class BroadcastUtil {

    /**
     *
     * @param context
     * @param clazz
     * @param data
     */
    public static void sendBroadcast(Context context, Class clazz, HashMap<String, Serializable> data) {
        Intent intent = new Intent(context, clazz);
        for (String key : data.keySet()) {
            intent.putExtra(key, data.get(key));
        }
        context.sendBroadcast(intent);
    }

    /**
     *
     * @param context
     * @param clazz
     * @param action
     */
    public static void sendBroadcast(Context context, Class clazz, String action) {
        sendBroadcast(context, clazz, action, null);
    }

    /**
     *
     * @param context
     * @param clazz
     * @param action
     * @param data
     */
    public static void sendBroadcast(Context context, Class clazz, String action, @Nullable HashMap<String, Serializable> data) {
        Intent intent = new Intent(context, clazz);
        intent.setAction(action);
        if(data != null) {
            for (String key : data.keySet()) {
                intent.putExtra(key, data.get(key));
            }
        }
        context.sendBroadcast(intent);
    }
}
