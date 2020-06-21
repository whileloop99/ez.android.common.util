package ez.android.common.util;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

public class AlarmUtil {
    private static SoundPool pool = null;
    private static int id = -1;
    public static void siren(Context context, int resourceId) {
        if (null == pool) {
            pool = new SoundPool(5, AudioManager.STREAM_ALARM, 0);
        }
        if (-1 == id) {
            id = pool.load(context.getApplicationContext(), resourceId, 1);
            pool.setOnLoadCompleteListener((soundPool, sampleId, status) -> {
                loudest(context, AudioManager.STREAM_ALARM);
                pool.play(id, 1.0f, 1.0f, 1, 3, 1.0f);
            });
        }
        loudest(context, AudioManager.STREAM_ALARM);
        pool.play(id, 1.0f, 1.0f, 1, 3, 1.0f);
    }

    public static void loudest(Context context, int stream) {
        AudioManager manager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        int loudest = manager.getStreamMaxVolume(stream);
        manager.setStreamVolume(stream, loudest, 0);
    }
}
