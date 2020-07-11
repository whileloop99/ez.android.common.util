package ez.android.common.util;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaMetadataRetriever;
import android.media.SoundPool;
import android.net.Uri;

/**
 * Sound utilities
 */
public class SoundUtil {

    private static SoundPool pool = null;
    private static int id = -1;

    /**
     *
     * @param context
     * @param resourceId
     */
    public static void siren(Context context, int resourceId, float volume, int loop) {
        if (null == pool) {
//            pool = new SoundPool(5, AudioManager.STREAM_ALARM, 0);
            pool = new SoundPool.Builder()
                    .setMaxStreams(10)
                    .setAudioAttributes(new AudioAttributes.Builder()
                            .setLegacyStreamType(AudioManager.STREAM_ALARM)
                            .build())
                    .build();
        }
        if (-1 == id) {
            id = pool.load(context.getApplicationContext(), resourceId, 1);
            pool.setOnLoadCompleteListener((soundPool, sampleId, status) -> {
                loudest(context, AudioManager.STREAM_ALARM);
                pool.play(id, volume, volume, 1, loop, 1.0f);
            });
        }
        loudest(context, AudioManager.STREAM_ALARM);
        pool.play(id, volume, volume, 1, loop, 1.0f);
    }

    /**
     *
     * @param context
     * @param stream
     */
    public static void loudest(Context context, int stream) {
        AudioManager manager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        int loudest = manager.getStreamMaxVolume(stream);
        manager.setStreamVolume(stream, loudest, 0);
    }

    /**
     * Get sound duration
     * @param context
     * @param rawName
     * @return
     */
    public static long getDuration(Context context, String rawName) {
        Uri uri = Uri.parse("android.resource://"+context.getPackageName()+"/raw/" + rawName);
        MediaMetadataRetriever mmr = new MediaMetadataRetriever();
        mmr.setDataSource(context,uri);
        String durationStr = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
        int millSecond = Integer.parseInt(durationStr);
        return millSecond;
    }
}
