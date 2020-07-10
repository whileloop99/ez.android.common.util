package ez.android.common.util;

import android.app.NotificationChannel;
import android.app.NotificationChannelGroup;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.provider.Settings;

import androidx.annotation.NonNull;

/**
 * Notification utilities
 */
public class NotificationUtil {

    /**
     * Create a notification channel
     * https://developer.android.com/training/notify-user/channels#CreateChannel
     * @param context
     * @param channelName
     * @param channelDescription
     */
    public static void createChannel(@NonNull Context context, @NonNull String channelId, @NonNull CharSequence channelName, String channelDescription) {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(channelId, channelName, importance);
            channel.setDescription(channelDescription);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    /**
     * Delete a notification channel
     * https://developer.android.com/training/notify-user/channels#DeleteChannel
     * @param context
     * @param channelId
     */
    public static void deleteChannel(@NonNull Context context, @NonNull String channelId) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.deleteNotificationChannel(channelId);
        }
    }

    /**
     * Open the notification channel settings
     * https://developer.android.com/training/notify-user/channels#UpdateChannel
     * @param context
     * @param channelId
     */
    public static void openChannelSetting(@NonNull Context context, @NonNull String channelId) {
        openChannelSetting(context, context.getPackageName(), channelId);
    }

    /**
     * Open the notification channel settings
     * https://developer.android.com/training/notify-user/channels#UpdateChannel
     * @param context
     * @param appPackageName
     * @param channelId
     */
    public static void openChannelSetting(@NonNull Context context, @NonNull String appPackageName, @NonNull String channelId) {
        Intent intent = new Intent(Settings.ACTION_CHANNEL_NOTIFICATION_SETTINGS);
        intent.putExtra(Settings.EXTRA_APP_PACKAGE, appPackageName);
        intent.putExtra(Settings.EXTRA_CHANNEL_ID, channelId);
        context.startActivity(intent);
    }

    /**
     * Create a notification channel group
     * https://developer.android.com/training/notify-user/channels#CreateChannelGroup
     * @param context
     * @param groupId
     * @param groupName
     */
    public static void createChannelGroup(@NonNull Context context, @NonNull String groupId, @NonNull String groupName) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannelGroup(new NotificationChannelGroup(groupId, groupName));
        }
    }
}
