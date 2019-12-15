package lockscreen.gpaddy.com.lockscreen.util;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;

import lockscreen.gpaddy.com.lockscreen.service.NotificationServiceCustom;

/**
 * Created by Phí Văn Tuấn on 8/1/2019.
 */

public class NotificationEnabledUtil {
    public static boolean isServicePermission(Context context) {
        try {
            String notificationListenerString = Settings.Secure.getString(context.getContentResolver(), "enabled_notification_listeners");
            if (notificationListenerString == null || !notificationListenerString.contains(context.getPackageName())) {
                return false;
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    public static void startService(Context context) {
        String notificationListenerString = Settings.Secure.getString(context.getContentResolver(), "enabled_notification_listeners");
        if (notificationListenerString != null && notificationListenerString.contains(context.getPackageName()) && !isMyServiceRunning(context, NotificationServiceCustom.class.getName())) {
            Intent intent = new Intent(context.getApplicationContext(), NotificationServiceCustom.class);
            intent.setAction("android.settings.NOTIFICATION_LISTENER_SETTINGS");
            context.getApplicationContext().startService(intent);
        }
    }

    @SuppressLint("WrongConstant")
    public static void requestPermission(Context context) {
        try {
            Intent requestIntent = new Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS");
            requestIntent.addFlags(268435456);
            context.getApplicationContext().startActivity(requestIntent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @SuppressLint("WrongConstant")
    public static boolean isMyServiceRunning(Context context, String serviceName) {
        try {
            for (ActivityManager.RunningServiceInfo service : ((ActivityManager) context.getSystemService("activity")).getRunningServices(Integer.MAX_VALUE)) {
                if (serviceName.equals(service.service.getClassName()) && context.getApplicationContext().getPackageName().equals(service.service.getPackageName())) {
                    return true;
                }
            }
        } catch (Exception e) {
        }
        return false;
    }
}
