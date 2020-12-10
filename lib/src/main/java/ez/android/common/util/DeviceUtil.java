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
import android.text.format.Formatter;
import android.util.Log;

import androidx.annotation.RequiresPermission;
import androidx.core.app.ActivityCompat;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import static android.content.Context.WIFI_SERVICE;

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
    @SuppressLint("HardwareIds")
    @RequiresPermission(Manifest.permission.ACCESS_WIFI_STATE)
    public static String getWifiMacAddress(Context context) {
        WifiManager wifi = (WifiManager) context.getApplicationContext().getSystemService(WIFI_SERVICE);
        WifiInfo info = wifi.getConnectionInfo();
        return info.getMacAddress();
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

    /**
     * Get IP address from first non-localhost interface
     * @param useIPv4   true=return ipv4, false=return ipv6
     * @return  address or empty string
     */
    public static String getIPAddress(boolean useIPv4) {
        try {
            List<NetworkInterface> interfaces = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface nInfo : interfaces) {
                List<InetAddress> addresses = Collections.list(nInfo.getInetAddresses());
                for (InetAddress address : addresses) {
                    if (!address.isLoopbackAddress()) {
                        String add = address.getHostAddress();
                        boolean isIPv4 = add.indexOf(':')<0;

                        if (useIPv4) {
                            if (isIPv4)
                                return add;
                        } else {
                            if (!isIPv4) {
                                int idx = add.indexOf('%'); // drop ip6 zone suffix
                                return idx<0 ? add.toUpperCase() : add.substring(0, idx).toUpperCase();
                            }
                        }
                    }
                }
            }
        } catch (Exception ignored) { } // for now eat exceptions
        return "";
    }

    @RequiresPermission(Manifest.permission.ACCESS_WIFI_STATE)
    public static String getIPAddressFromWifiManager(Context context) {
        WifiManager manager = (WifiManager) context.getApplicationContext().getSystemService(WIFI_SERVICE);
        WifiInfo wifiInfo = manager.getConnectionInfo();
        int ipInt = wifiInfo.getIpAddress();
        try {
            return InetAddress.getByAddress(
                    ByteBuffer.allocate(4).order(ByteOrder.LITTLE_ENDIAN).putInt(ipInt).array())
                    .getHostAddress();
        } catch (UnknownHostException ignored) {}
        return null;
    }

    /*
     * Load file content to String
     */
    private static String loadFileAsString(String path) throws java.io.IOException{
        StringBuilder fileData = new StringBuilder(1000);
        BufferedReader reader = new BufferedReader(new FileReader(path));
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
     * @param context Android context
     * @return Android ID
     */
    @SuppressLint("HardwareIds")
    public static String getAndroidId(Context context) {
        return Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
    }

    /**
     * Get device elapsed real time in string
     * @return Boot time string
     */
    public static String getBootTimeString() {
        long ut = SystemClock.elapsedRealtime() / 1000;
        int h = (int) ((ut / 3600));
        int m = (int) ((ut / 60) % 60);
        return h + ":" + m;
    }

    /**
     * Get device information summary
     * @return Device system info
     */
    @SuppressLint("HardwareIds")
    public static String printSystemInfo() {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
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
        sb.append("\nDISPLAY            :").append(Build.DISPLAY);

        sb.append("\n_______ DONUT-4 _______");
        sb.append("\nSDK_INT            :").append(Build.VERSION.SDK_INT);
        sb.append("\nMANUFACTURER       :").append(Build.MANUFACTURER);
        sb.append("\nBOOTLOADER         :").append(Build.BOOTLOADER);
        sb.append("\nCPU_ABI            :").append(Build.CPU_ABI);
        sb.append("\nCPU_ABI2           :").append(Build.CPU_ABI2);
        sb.append("\nHARDWARE           :").append(Build.HARDWARE);
        sb.append("\nUNKNOWN            :").append(Build.UNKNOWN);
        sb.append("\nCODENAME           :").append(Build.VERSION.CODENAME);

        sb.append("\n_______ GINGERBREAD-9 _______");
        sb.append("\nSERIAL             :").append(Build.SERIAL);
        Log.i(TAG, sb.toString());
        return sb.toString();
    }

    /**
     *
     * @param context Android context
     * @return Device unique ID
     */
    @SuppressLint("HardwareIds")
    @RequiresPermission(Manifest.permission.READ_PHONE_STATE)
    public static UUID getDeviceUniqueId(Context context) {
        final TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);

        String tmDevice = "", tmSerial = "", androidId;

        try {
            tmDevice = "" + tm.getDeviceId();
        } catch (Exception ignored) {}

        try {
            tmDevice = "" + tm.getSimSerialNumber();
        } catch (Exception ignored) {}
        androidId = "" + android.provider.Settings.Secure.getString(context.getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);

        return new UUID(androidId.hashCode(), ((long)tmDevice.hashCode() << 32) | tmSerial.hashCode());
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
     * @return DeviceSuperInfo
     */
    @SuppressLint("HardwareIds")
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

        } catch (Exception ignored) { }

        return info;
    }

    /**
     * Get using gmail accounts
     * @param context Android context
     * @return email list
     */
    @RequiresPermission(allOf = {Manifest.permission.GET_ACCOUNTS, Manifest.permission.READ_CONTACTS})
    public static List<String> getGoogleEmailAccounts(Context context) {
        AccountManager manager = AccountManager.get(context);
        Account[] accounts = manager.getAccountsByType("com.google");
        List<String> possibleEmails = new ArrayList<>();

        for (Account account : accounts) {
            possibleEmails.add(account.name);
        }

        return possibleEmails;
    }
}
