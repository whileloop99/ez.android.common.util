package ez.android.common.util;

import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

/**
 *
 */
public class FileUtil {

    /**
     *
     * @param file
     * @param bytes
     */
    public static void toFile(File file, byte[] bytes) {
        try (OutputStream outputStream = new FileOutputStream(file)) {
            outputStream.write(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param fileUrl
     * @param bytes
     */
    public static void toFile(String fileUrl, byte[] bytes) {
        try (OutputStream outputStream = new FileOutputStream(fileUrl)) {
            outputStream.write(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param context
     * @param subFolderName
     * @return
     */
    public static File getSubMediaFolder(Context context, String subFolderName) {
        File folder = new File(context.getFilesDir(), subFolderName);

        if (folder.exists() || (!folder.exists() && folder.mkdirs())) {
            return folder;
        }
        // Fallback to Storage card
        folder = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + subFolderName);
        if (folder.exists() || (!folder.exists() && folder.mkdirs())) {
            return folder;
        }
        return null;
    }
}
