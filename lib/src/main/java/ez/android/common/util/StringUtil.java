package ez.android.common.util;

/**
 * String utilities
 */
public class StringUtil {

    /**
     *
     * @param value
     * @return
     */
    public static boolean isNullOrEmpty(String value) {
        return value == null || value.isEmpty();
    }

    /**
     *
     * @param value
     * @return
     */
    public static boolean isNullOrEmptyOrWhitespace(String value) {
        return isNullOrEmpty(value) || value.trim().isEmpty();
    }
}
