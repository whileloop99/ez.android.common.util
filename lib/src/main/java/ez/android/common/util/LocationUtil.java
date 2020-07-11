package ez.android.common.util;

import android.location.Location;

import java.util.Locale;

/**
 *
 */
public class LocationUtil {

    /**
     * グーグルマップの場所
     * @param location
     * @return
     */
    public static String getExtraDataString(Location location) {
        double lat = location.getLatitude();
        double lon = location.getLongitude();
        int accuracy = (int) location.getAccuracy();
        int altitude = (int) location.getAltitude();
        int bearing = (int) location.getBearing();
        int speed = (int) (location.getSpeed() * 60.0 * 60.0 / 1000.0);
        String messageTemplate = "緯度：%.5f、 経度：%.5f、正確さ：~%dm、高度：^%dm、ベアリング：%ddeg、速度：%dkm/h";
        return  String.format(Locale.JAPAN, messageTemplate, lat, lon, accuracy, altitude, bearing, speed);
    }

    public static String getGoogleMapUrl(Location location) {
        double lat = location.getLatitude();
        double lon = location.getLongitude();
        String messageTemplate = "http://maps.google.com/?q=%.5f,%.5f";
        return  String.format(Locale.JAPAN, messageTemplate, lat, lon);
    }
}
