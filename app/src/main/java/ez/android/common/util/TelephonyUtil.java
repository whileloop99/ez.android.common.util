package ez.android.common.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.telephony.TelephonyManager;

import java.lang.reflect.Method;

import static android.content.Context.TELEPHONY_SERVICE;
import static android.telephony.TelephonyManager.SIM_STATE_ABSENT;

/**
 *
 */
public class TelephonyUtil {

    /**
     *
     * @param context
     * @param phoneNumber
     */
    @SuppressLint("MissingPermission")
    public static void call(Context context, String phoneNumber) {
        Intent call = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phoneNumber));
        call.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(call);
    }

    /**
     *
     * @param context
     * @throws Exception
     */
    @SuppressLint("SoonBlockedPrivateApi")
    public static void endCall(Context context) throws Exception {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(TELEPHONY_SERVICE);

        Method m1 = tm.getClass().getDeclaredMethod("getITelephony");
        m1.setAccessible(true);
        Object iTelephony = m1.invoke(tm);

//        Method m2 = iTelephony.getClass().getDeclaredMethod("silenceRinger");
        Method m3 = iTelephony.getClass().getDeclaredMethod("endCall");

//        m2.invoke(iTelephony);
        m3.invoke(iTelephony);
    }

    /**
     *
     * @param context
     * @return
     */
    public static boolean checkSIMAvailable(Context context) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(TELEPHONY_SERVICE);
        return tm.getSimState() != SIM_STATE_ABSENT;
    }
}
