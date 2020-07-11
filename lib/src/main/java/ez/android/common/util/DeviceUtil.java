package ez.android.common.util;

import android.Manifest;
import android.accounts.Account;
import android.accounts.AccountManager;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.SystemClock;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Log;

import androidx.annotation.RequiresPermission;
import androidx.core.app.ActivityCompat;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.NetworkInterface;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Android device utilities
 */
public class DeviceUtil {
    /**
     * Logging tag
     */
    private static final String TAG = DeviceUtil.class.getSimpleName();

    /**
     * Ethernet Mac Address
     */
    private static final String ETH0_MAC_ADDRESS = "/sys/class/net/eth0/address";

    /**
     * Get device WIFI's MAC address
     *
     * <uses-permission android:name="android.permission.INTERNET" />
     *
     * @return MAC address
     */
    public static String getWifiMacAddress() {
        try {
            List<NetworkInterface> all = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface nif : all) {
                if (!nif.getName().equalsIgnoreCase("wlan0")) continue;

                byte[] macBytes = nif.getHardwareAddress();
                if (macBytes == null) {
                    return "";
                }

                StringBuilder res = new StringBuilder();
                for (byte b : macBytes) {
                    res.append(String.format("%02X:", b));
                }

                if (res.length() > 0) {
                    res.deleteCharAt(res.length() - 1);
                }
                return res.toString();
            }
        } catch (Exception ignored) { }
        return "02:00:00:00:00:00";
    }

    /**
     * Get device WIFI's MAC address
     * Not work after android 6 breaking change
     *
     * <uses-permission android:name="android.permission.ACCESS_WIFI_STATE"/>
     *
     * @return MAC address
     */
    @SuppressLint("MissingPermission")
    public static String getWifiMacAddress(Context context) {
        WifiManager wifi = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = wifi.getConnectionInfo();
        String mac = info.getMacAddress();
        return mac;
    }

    /**
     * Get device ethernet MAC address
     *
     * @return MAC address
     */
    public static String getEthernetMacAddress() {
        try {
            return loadFileAsString(ETH0_MAC_ADDRESS)
                    .toUpperCase().substring(0, 17);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /*
     * Load file content to String
     */
    private static String loadFileAsString(String filePath) throws java.io.IOException{
        StringBuffer fileData = new StringBuffer(1000);
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        char[] buf = new char[1024];
        int numRead=0;
        while((numRead=reader.read(buf)) != -1){
            String readData = String.valueOf(buf, 0, numRead);
            fileData.append(readData);
        }
        reader.close();
        return fileData.toString();
    }

    /**
     * Get Android ID
     *
     * Please note that this ID will change when the device is factory reset.
     * Not recommend to use as device unique ID
     * @param context
     * @return
     */
    public static String getAndroidId(Context context) {
        return Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
    }

    /**
     * Get device elapsed real time in string
     * @return
     */
    public static String getBootTimeString() {
        long ut = SystemClock.elapsedRealtime() / 1000;
        int h = (int) ((ut / 3600));
        int m = (int) ((ut / 60) % 60);
        return h + ":" + m;
    }

    /**
     * Get device information summary
     * @return
     */
    public static String printSystemInfo() {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = dateFormat.format(date);
        StringBuilder sb = new StringBuilder();
        sb.append("_______  INFO  ").append(time).append(" ______________");
        sb.append("\nID                 :").append(Build.ID);
        sb.append("\nBRAND              :").append(Build.BRAND);
        sb.append("\nMODEL              :").append(Build.MODEL);
        sb.append("\nRELEASE            :").append(Build.VERSION.RELEASE);
        sb.append("\nSDK                :").append(Build.VERSION.SDK);

        sb.append("\n_______ OTHER _______");
        sb.append("\nBOARD              :").append(Build.BOARD);
        sb.append("\nPRODUCT            :").append(Build.PRODUCT);
        sb.append("\nDEVICE             :").append(Build.DEVICE);
        sb.append("\nFINGERPRINT        :").append(Build.FINGERPRINT);
        sb.append("\nHOST               :").append(Build.HOST);
        sb.append("\nTAGS               :").append(Build.TAGS);
        sb.append("\nTYPE               :").append(Build.TYPE);
        sb.append("\nTIME               :").append(Build.TIME);
        sb.append("\nINCREMENTAL        :").append(Build.VERSION.INCREMENTAL);

        sb.append("\n_______ CUPCAKE-3 _______");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.CUPCAKE) {
            sb.append("\nDISPLAY            :").append(Build.DISPLAY);
        }

        sb.append("\n_______ DONUT-4 _______");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.DONUT) {
            sb.append("\nSDK_INT            :").append(Build.VERSION.SDK_INT);
            sb.append("\nMANUFACTURER       :").append(Build.MANUFACTURER);
            sb.append("\nBOOTLOADER         :").append(Build.BOOTLOADER);
            sb.append("\nCPU_ABI            :").append(Build.CPU_ABI);
            sb.append("\nCPU_ABI2           :").append(Build.CPU_ABI2);
            sb.append("\nHARDWARE           :").append(Build.HARDWARE);
            sb.append("\nUNKNOWN            :").append(Build.UNKNOWN);
            sb.append("\nCODENAME           :").append(Build.VERSION.CODENAME);
        }

        sb.append("\n_______ GINGERBREAD-9 _______");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
            sb.append("\nSERIAL             :").append(Build.SERIAL);
        }
        Log.i(TAG, sb.toString());
        return sb.toString();
    }

    /**
     *
     * @param context
     * @return
     */
    @RequiresPermission(Manifest.permission.READ_PHONE_STATE)
    public static UUID getDeviceUniqueId(Context context) {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            return null;
        }

        final TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);

        String tmDevice = "", tmSerial = "", androidId;

        try {
            tmDevice = "" + tm.getDeviceId();
        } catch (Exception ignored) {}

        try {
            tmDevice = "" + tm.getSimSerialNumber();
        } catch (Exception ignored) {}
        androidId = "" + android.provider.Settings.Secure.getString(context.getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);

        UUID deviceUuid = new UUID(androidId.hashCode(), ((long)tmDevice.hashCode() << 32) | tmSerial.hashCode());
        return deviceUuid;
    }

    public static class Info {
        String osVersion;
        String osApiLevel;
        String device;
        String modelAndProduct;
        String release;
        String brand;
        String display;
        String cpuAbi;
        String cpuAbi2;
        String unknown;
        String hardware;
        String buildId;
        String manufacturer;
        String serial;
        String user;
        String host;
    }

    /**
     *
     * @return
     */
    public static Info getDeviceSuperInfo() {
        Info info = new Info();
        try {

            info.osVersion = System.getProperty("os.version") + " (" + android.os.Build.VERSION.INCREMENTAL + ")";
            info.osApiLevel = android.os.Build.VERSION.SDK_INT + "";
            info.device = android.os.Build.DEVICE;
            info.modelAndProduct = android.os.Build.MODEL + " ("+ android.os.Build.PRODUCT + ")";
            info.release = android.os.Build.VERSION.RELEASE;
            info.brand = android.os.Build.BRAND;
            info.display = android.os.Build.DISPLAY;
            info.cpuAbi = android.os.Build.CPU_ABI;
            info.cpuAbi2 = android.os.Build.CPU_ABI2;
            info.unknown = android.os.Build.UNKNOWN;
            info.hardware = android.os.Build.HARDWARE;
            info.buildId = android.os.Build.ID;
            info.manufacturer = android.os.Build.MANUFACTURER;
            info.serial = android.os.Build.SERIAL;
            info.user = android.os.Build.USER;
            info.host = android.os.Build.HOST;

        } catch (Exception e) {
        }

        return info;
    }

    /**
     * Get using gmail accounts
     * @param context
     * @return
     */
    @SuppressLint("MissingPermission")
    public static List<String> getGoogleEmailAccounts(Context context) {
        AccountManager manager = AccountManager.get(context);
        Account[] accounts = manager.getAccountsByType("com.google");
        List<String> possibleEmails = new ArrayList<>();

        for (Account account : accounts) {
            // TODO: Check possibleEmail against an email regex or treat
            // account.name as an email address only for certain account.type values.
            possibleEmails.add(account.name);
        }

        return possibleEmails;
    }
}
