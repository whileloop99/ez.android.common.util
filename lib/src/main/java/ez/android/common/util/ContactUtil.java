package ez.android.common.util;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;
import android.provider.ContactsContract;

/**
 *
 */
public class ContactUtil {

    /**
     *
     * @param context
     * @param number
     * @return
     */
    public static String getContactDisplayNameByNumber(Context context, String number) {
        if(number == null || number.isEmpty()) {
            return null;
        }

        Uri uri = Uri.withAppendedPath(ContactsContract.CommonDataKinds.Phone.CONTENT_FILTER_URI, Uri.encode(number));
        String name = null;

        ContentResolver contentResolver = context.getContentResolver();
        Cursor contactLookup = contentResolver.query(uri, new String[] {BaseColumns._ID,
                ContactsContract.PhoneLookup.DISPLAY_NAME }, null, null, null);

        try {
            if (contactLookup != null && contactLookup.getCount() > 0) {
                contactLookup.moveToNext();
                name = contactLookup.getString(contactLookup.getColumnIndex(ContactsContract.Data.DISPLAY_NAME));
                //String contactId = contactLookup.getString(contactLookup.getColumnIndex(BaseColumns._ID));
            }
        } finally {
            if (contactLookup != null) {
                contactLookup.close();
            }
        }

        return name;
    }

    /**
     *
     * @param context
     * @param data
     * @return
     */
    public static String getPhoneNumberFromResultIntent(Context context, Intent data) {
        String selection = ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=?";
        Uri result = data.getData();
        String id = result.getLastPathSegment();
        String[] arguments = new String[]{id};
        Cursor cursor = context.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, selection, arguments, null);
        int index = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DATA);
        if (cursor.moveToFirst()) {
            String phone = cursor.getString(index);
            cursor.close();
            return phone;
        }
        return null;
    }
}
