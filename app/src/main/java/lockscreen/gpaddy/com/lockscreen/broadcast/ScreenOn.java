package lockscreen.gpaddy.com.lockscreen.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import lockscreen.gpaddy.com.lockscreen.activity.LockHasPasscode;
import lockscreen.gpaddy.com.lockscreen.activity.LockNoPasscode;
import lockscreen.gpaddy.com.lockscreen.util.Values;

/**
 * Created by Admin on 19/09/2015.
 */
public class ScreenOn extends BroadcastReceiver {
    public static ScreenOn screenOn;

    public static ScreenOn newInstance() {

        if (screenOn == null) {
            screenOn = new ScreenOn();
        }
        return screenOn;

    }

    @Override
    public void onReceive(Context context, Intent intent) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if (intent.getAction().equalsIgnoreCase("android.intent.action.SCREEN_OFF") || intent.getAction().equalsIgnoreCase("android.intent.action.SCREEN_ON")) {
            editor.putLong(Values.TIME_LAST_OFF, System.currentTimeMillis());
            editor.commit();
//        } else {
            if (System.currentTimeMillis() - sharedPreferences.getLong(Values.TIME_LAST_OFF, -1) <= sharedPreferences.getLong(Values.TIME_OUT, -1)) {
                return;
            } else {
                if (sharedPreferences.getBoolean(Values.ENABLE_PASSCODE, false)) {
                    if (!LockHasPasscode.booleanisCall) {
                        Intent intent1 = new Intent(context, LockHasPasscode.class);
                        intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_FROM_BACKGROUND);
                        context.startActivity(intent1);
                    }

                } else {
                    if (!LockNoPasscode.booleanisCall) {
                        Intent intent1 = new Intent(context, LockNoPasscode.class);
                        intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_FROM_BACKGROUND);
                        context.startActivity(intent1);
                        Log.e("CustomPhoneStateListene", "screenon");
                    }
                }
            }

        }

    }
}

