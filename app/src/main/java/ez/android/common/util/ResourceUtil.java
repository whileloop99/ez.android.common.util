package ez.android.common.util;

import android.content.ContentResolver;
import android.content.Context;
import android.content.res.Resources;
import android.media.MediaMetadataRetriever;
import android.net.Uri;

/**
 *
 */
public class ResourceUtil {

    /**
     *
     * @param context
     * @param resourceId
     * @return
     */
    public static long getSoundDuration(Context context, int resourceId) {
        Resources resources = context.getResources();
        Uri uri = new Uri.Builder()
                .scheme(ContentResolver.SCHEME_ANDROID_RESOURCE)
                .authority(resources.getResourcePackageName(resourceId))
                .appendPath(resources.getResourceTypeName(resourceId))
                .appendPath(resources.getResourceEntryName(resourceId))
                .build();

        MediaMetadataRetriever mmr = new MediaMetadataRetriever();
        mmr.setDataSource(context, uri);
        String durationStr = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
        int millSecond = Integer.parseInt(durationStr);
        return millSecond * 3;
    }
}
