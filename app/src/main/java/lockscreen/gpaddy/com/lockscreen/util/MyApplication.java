package lockscreen.gpaddy.com.lockscreen.util;

import android.app.Application;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.preference.PreferenceManager;

import lockscreen.gpaddy.com.lockscreen.R;
import lockscreen.gpaddy.com.lockscreen.service.MyService;


/**
 * Created by Admin on 19/09/2015.
 */
public class MyApplication extends Application {
    public static Bitmap blur;
//    public static Bitmap notBlur;

    @Override
    public void onCreate() {
        super.onCreate();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(new Intent(getApplicationContext(), MyService.class));
        } else {
            startService(new Intent(getApplicationContext(), MyService.class));
        }
        //startService(new Intent(this, MyService.class));
//        if (MyApplication.blur == null) {
//            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
//            int idBackground = sharedPreferences.getInt(Values.BACKGROUND_RESOURCE_ID, R.drawable.b1);
//            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), idBackground);
//            blur = Blur.fastblur(this, bitmap, 25);
//
//        }
//        if (notBlur == null) {
//            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
//            int idBackground = sharedPreferences.getInt(Values.BACKGROUND_RESOURCE_ID, R.drawable.b1);
//            notBlur = BitmapFactory.decodeResource(getResources(), idBackground);
//        }

    }


}
