package lockscreen.gpaddy.com.lockscreen.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import lockscreen.gpaddy.com.lockscreen.activity.LockHasPasscode;
import lockscreen.gpaddy.com.lockscreen.activity.LockNoPasscode;
import lockscreen.gpaddy.com.lockscreen.util.Values;

/**
 * Created by Admin on 21/09/2015.
 */
public class BootComlepeReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        if (sharedPreferences.getBoolean(Values.ACTIVATE_LOCK, true)) {
            if (sharedPreferences.getBoolean(Values.ENABLE_PASSCODE, false)) {
                Intent intent1 = new Intent(context, LockHasPasscode.class);
                intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_FROM_BACKGROUND);
                context.startActivity(intent1);
            } else {
                Intent intent1 = new Intent(context, LockNoPasscode.class);
                intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_FROM_BACKGROUND);
                context.startActivity(intent1);
            }
        }

    }
}
