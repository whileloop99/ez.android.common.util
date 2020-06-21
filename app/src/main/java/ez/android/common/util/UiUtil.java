package ez.android.common.util;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.widget.Button;

import androidx.appcompat.app.AlertDialog;

/**
 *
 */
public class UiUtil {

    /**
     *
     * @param button
     * @param widthInDpi
     * @param heightInDpi
     */
    public static void resizeButtonDrawable(Button button, float widthInDpi, float heightInDpi) {
        Drawable[] drawables = button.getCompoundDrawablesRelative();
        for (Drawable d : drawables) {
            if (d != null) {
                d.setBounds(d.getBounds().left, d.getBounds().top, getSizeFromDps(button.getContext(), widthInDpi), getSizeFromDps(button.getContext(), heightInDpi));
            }
        }
        button.setCompoundDrawablesRelative(drawables[0], drawables[1], drawables[2], drawables[3]);
    }

    /**
     *
     * @param context
     * @param dps
     * @return
     */
    public static int getSizeFromDps(Context context, float dps) {
        final float scale = context.getResources().getDisplayMetrics().density;
        int pixels = (int) (dps * scale + 0.5f);
        return pixels;
    }

    /**
     *
     * @param context
     * @param title
     * @param message
     */
    public static void showPopup(Context context, String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton("OK", (dialog, which) -> {
            dialog.cancel();
        });
        try {
            builder.show();
        } catch(Exception ignored) { }
    }

    /**
     *
     * @param context
     * @param title
     * @param message
     * @param clickCallback
     */
    public static void showPopup(Context context, String title, String message, DialogInterface.OnClickListener clickCallback) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton("OK", (dialog, which) -> {
            dialog.dismiss();
            if(clickCallback != null) {
                clickCallback.onClick(dialog, which);
            }
        });
        try {
            builder.show();
        } catch(Exception ignored) { }
    }

    /**
     *
     * @param context
     * @param title
     * @param message
     * @param clickCallback
     */
    public static void showPopupConfirm(Context context, String title, String message, DialogInterface.OnClickListener clickCallback) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton("OK", (dialog, which) -> {
            dialog.dismiss();
            if(clickCallback != null) {
                clickCallback.onClick(dialog, which);
            }
        });
        builder.setNegativeButton("キャンセル", (dialog, which) -> {
            dialog.dismiss();
            if(clickCallback != null) {
                clickCallback.onClick(dialog, which);
            }
        });
        try {
            builder.show();
        } catch(Exception ignored) { }
    }
}
