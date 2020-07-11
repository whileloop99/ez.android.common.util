package ez.android.common.util;

import android.Manifest;
import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;
import android.util.Patterns;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresPermission;

import java.util.regex.Pattern;

/**
 * Account utilities
 */
public class AccountUtil {

    /**
     * Get current primary email account
     * @param context
     * @return
     */
    @Nullable
    @RequiresPermission(Manifest.permission.GET_ACCOUNTS)
    public static String getPrimaryEmail(@NonNull Context context) {
        Pattern emailPattern = Patterns.EMAIL_ADDRESS;
        Account[] accounts = AccountManager.get(context).getAccounts();
        for (Account account : accounts) {
            if (emailPattern.matcher(account.name).matches()) {
                return account.name;
            }
        }
        return null;
    }
}
