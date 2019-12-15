package lockscreen.gpaddy.com.lockscreen.service;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Phí Văn Tuấn on 8/1/2019.
 */

@SuppressLint("OverrideAbstract")
@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
public class NotificationServiceCustom extends NotificationListenerService {
    private static ArrayList<String> listAppDock;
    public static NotificationServiceCustom myService;
    private static long timeGetDock;
    private final IBinder binder = new ServiceBinder();
    private boolean isBound = false;

    public class ServiceBinder extends Binder {
        NotificationServiceCustom getService() {
            return NotificationServiceCustom.this;
        }
    }

    public IBinder onBind(Intent intent) {
        this.isBound = true;
        if ("android.service.notification.NotificationListenerService".equals(intent.getAction())) {
            return super.onBind(intent);
        }
        return this.binder;
    }

    public void onCreate() {
        super.onCreate();
        myService = this;
    }

    @SuppressLint("WrongConstant")
    public int onStartCommand(Intent intent, int flags, int startid) {
        myService = this;
        return 1;
    }

    public void onDestroy() {
        myService = null;
        super.onDestroy();
    }

    public boolean isBound() {
        return this.isBound;
    }

    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        super.onNotificationPosted(sbn);
    }

    private static ArrayList<String> getListNotify() {
        ArrayList<String> listApp = new ArrayList();
        try {
            if (myService != null) {
                for (StatusBarNotification sbn : myService.getActiveNotifications()) {
                    String packageName = sbn.getPackageName();
                    if (packageName.equals("com.android.phone") || packageName.equals("com.android.server.telecom")) {
                        packageName = "com.android.contacts";
                    }
                    if (!listApp.contains(packageName)) {
                        listApp.add(packageName);
                    }
                }
            }
        } catch (Exception e) {
        }
        return listApp;
    }


}
