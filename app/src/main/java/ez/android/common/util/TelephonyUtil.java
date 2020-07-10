package ez.android.common.util;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.telephony.TelephonyManager;

import androidx.annotation.RequiresPermission;

import java.lang.reflect.Method;

import static android.telephony.TelephonyManager.SIM_STATE_ABSENT;

/**
 * Telephony utilities
 */
public class TelephonyUtil {

    /**
     * Start a phone call
     * @param context
     * @param phoneNumber
     */
    @RequiresPermission(Manifest.permission.CALL_PHONE)
    public static void call(Context context, String phoneNumber) {
        Intent call = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phoneNumber));
        call.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(call);
    }

    /**
     * End a phone call
     * @param context
     * @throws Exception
     */
    @SuppressLint("SoonBlockedPrivateApi")
    public static void endCall(Context context) throws Exception {
        TelephonyManager tm = getManager(context);

        Method m1 = tm.getClass().getDeclaredMethod("getITelephony");
        m1.setAccessible(true);
        Object iTelephony = m1.invoke(tm);

//        Method m2 = iTelephony.getClass().getDeclaredMethod("silenceRinger");
        Method m3 = iTelephony.getClass().getDeclaredMethod("endCall");

//        m2.invoke(iTelephony);
        m3.invoke(iTelephony);
    }

    /**
     * Check if SIM is avaiable
     * @param context
     * @return
     */
    public static boolean checkSIMAvailable(Context context) {
        return getManager(context).getSimState() != SIM_STATE_ABSENT;
    }

    /**
     * Get current phone number 1
     * @param context
     * @return
     */
    @RequiresPermission(Manifest.permission.READ_PHONE_STATE)
    public static String getPhoneNumber(Context context) {
        return getManager(context).getLine1Number();
    }

    /**
     * Get phone manager
     * @param context
     * @return
     */
    private static TelephonyManager getManager(Context context) {
        return (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
    }
}
