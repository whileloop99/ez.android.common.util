package ez.android.common.util;

import android.util.Log;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

/**
 *
 */
public class NetworkUtil {
    private static String TAG = NetworkUtil.class.getSimpleName();

    /**
     *
     * @return
     */
    public static boolean isOnline() {
        try {
            // Connect to Google DNS to check for connection
            int timeoutMs = 1500;
            Socket socket = new Socket();
            SocketAddress socketAddress = new InetSocketAddress("8.8.8.8", 53);
            socket.connect(socketAddress, timeoutMs);
            socket.close();

            return true;
        } catch (IOException ex) {
            Log.e(TAG, ex.getMessage(), ex);
        }
        return false;
    }
}
