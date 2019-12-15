package lockscreen.gpaddy.com.lockscreen.activity;

import android.animation.Animator;
import android.app.Activity;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.BatteryManager;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.telephony.PhoneStateListener;
import android.telephony.SignalStrength;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.romainpiel.shimmer.Shimmer;
import com.romainpiel.shimmer.ShimmerTextView;

import java.util.ArrayList;
import java.util.Calendar;

import lockscreen.gpaddy.com.lockscreen.R;
import lockscreen.gpaddy.com.lockscreen.util.Utils;
import lockscreen.gpaddy.com.lockscreen.util.Values;
import lockscreen.gpaddy.com.lockscreen.view.TextViewRobotoLight;
import lockscreen.gpaddy.com.lockscreen.view.TextViewRobotoThin;


public class LockNoPasscode extends FragmentActivity implements View.OnClickListener {
    private View screen;
    private static RelativeLayout layout = null;
    //    private ImageView imgCamera;
    private int mScreenWidth = 0;
    private WindowManager.LayoutParams wmParams;
    private static WindowManager mWindowManager = null;
    private ViewPager pager;
    private TextViewRobotoThin tvTime;
    private ImageView imgBackground;

    private TextViewRobotoLight tvDate;
    private ShimmerTextView tvShimmer;
    private Shimmer shimmer;
    private View vPassCode, vLock;
    private ImageView imgSignal, imgWifi, imgBattery, imgCharging;
    private TextViewRobotoLight tvOperatorName, tvBattery;
    private SharedPreferences sharedPreferences;
    private TextViewRobotoThin tvFormat;
    private static boolean isCall = false;
    public static boolean booleanisCall = false;

    private boolean hasPassCode = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        hasPassCode = sharedPreferences.getBoolean(Values.ENABLE_PASSCODE, false);
        super.onCreate(savedInstanceState);
        if (mWindowManager != null) {
            Log.e("..............", "oncreate return");
            return;
        }
        layout = (RelativeLayout) View.inflate(this, R.layout.activity_lock2, null);
        pager = (ViewPager) layout.findViewById(R.id.pager);
//        pager.setOffscreenPageLimit(2);
        pager.setAdapter(new MyPagerAdapter(this));
        pager.post(new Runnable() {
            @Override
            public void run() {
                pager.setCurrentItem(1, false);
            }
        });

        LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        vPassCode = layoutInflater.inflate(R.layout.fragment_lock_passcode, null);

        vPassCode = layoutInflater.inflate(R.layout.view_null, null);

        vLock = layoutInflater.inflate(R.layout.fragment_lock, null);
        bindView();
        setTvShimmer();
        mWindowAddView();


        pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            boolean start = false;

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                Log.e(".............", positionOffset + " " + position);
//                if(start){
//                    if (positionOffset < 0.5f) {
//                    unlock();
//                }
//                }
//                if(positionOffset>0.5f){
//                    if(positionOffset<)
//                }
//                if (position==1&&positionOffset > 0.5f) {
//                    unlock();
//                }
            }

            @Override
            public void onPageSelected(int position) {
                Log.e(".............", position + "");
                if (position == 0) {
                    unlock();
                    if (telephony != null && customPhoneListener != null)
                        telephony.listen(customPhoneListener, PhoneStateListener.LISTEN_NONE);
                }
//                if (position == 0) {
//                    start = false;
//                } else start = true;
//}
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        telephony = (TelephonyManager) getSystemService(Service.TELEPHONY_SERVICE); //TelephonyManager object
        customPhoneListener = new CustomPhoneStateListener();
        telephony.listen(customPhoneListener, PhoneStateListener.LISTEN_CALL_STATE);
//        broadcastCall = new BroadcastCall();
//        registerReceiver(broadcastCall, new IntentFilter("android.intent.action.PHONE_STATE"));
        setTime();
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_BATTERY_CHANGED);
        filter.addAction(Intent.ACTION_TIME_TICK);
        filter.addAction(Intent.ACTION_TIMEZONE_CHANGED);
        registerReceiver(broadcast, filter);
//        screenOff = new ScreenOff();
//        registerReceiver(screenOff, new IntentFilter(Intent.ACTION_SCREEN_OFF));
    }

    //    ScreenOff screenOff;
//
    private void bindView() {
        tvShimmer = (ShimmerTextView) vLock.findViewById(R.id.shimmer_tv);
        tvDate = (TextViewRobotoLight) vLock.findViewById(R.id.tvDate);
        tvTime = (TextViewRobotoThin) vLock.findViewById(R.id.tvTime);


        imgSignal = (ImageView) layout.findViewById(R.id.imgSignal);
        imgWifi = (ImageView) layout.findViewById(R.id.imgWifi);
        imgBattery = (ImageView) layout.findViewById(R.id.imgBattery);
        imgCharging = (ImageView) layout.findViewById(R.id.imgCharging);
        tvOperatorName = (TextViewRobotoLight) layout.findViewById(R.id.tvOperatorName);
        tvBattery = (TextViewRobotoLight) layout.findViewById(R.id.tvBattery);
        setStatusBar();
        imgBackground = (ImageView) layout.findViewById(R.id.imgBackground);
        boolean isResource = sharedPreferences.getBoolean(Values.BACKGROUND_RESOUECE_BOLEAN, true);
        int idBackground = sharedPreferences.getInt(Values.BACKGROUND_RESOURCE_ID, 0);
        if (!isResource) {
//                setWallpaper();
//                Uri uri = Uri.parse(preferences.getString(Values.BACKGROUND_URI, ""));
//                this.background.setImageURI(uri);

            Uri imageUri = Uri.parse(sharedPreferences.getString(Values.BACKGROUND_URI, ""));

            Drawable myDrawable = Drawable.createFromPath(sharedPreferences.getString(Values.BACKGROUND_URI, ""));

            Bitmap bitmap = null;
            bitmap = ((BitmapDrawable) myDrawable).getBitmap();
            imgBackground.setImageBitmap(bitmap);


//                Uri selectedImageURI = Uri.parse(preferences.getString(Values.BACKGROUND_URI, ""));
//                InputStream input = getContentResolver().openInputStream(selectedImageURI);
//                this.background.setImageBitmap(BitmapFactory.decodeStream(input));//
            // this.background.setImageURI(Uri.parse(preferences.getString(Values.BACKGROUND_URI, "")));

        } else {
            if (idBackground == 0) {
                imgBackground.setImageResource(R.drawable.b2);
            } else {
                imgBackground.setImageResource(idBackground);
            }
        }
        tvFormat = (TextViewRobotoThin) vLock.findViewById(R.id.tvFormat);


    }

    Broadcast broadcast;
    TelephonyManager telephonyManager;

    public void setStatusBar() {
        telephonyManager = (TelephonyManager) this.getSystemService(TELEPHONY_SERVICE);
        telephonyManager.listen(phoneStateListener, TelephonyManager.PHONE_TYPE_GSM);
        tvOperatorName.setText(telephonyManager.getNetworkOperatorName());
        broadcast = new Broadcast();
        if (Utils.wifiConected(this)) {
            imgWifi.setVisibility(View.VISIBLE);
        } else {
            imgWifi.setVisibility(View.GONE);
        }

    }

    public void setTime() {
        boolean isTime12 = sharedPreferences.getBoolean(Values.TIME_FORMAT, false);
        Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        int date = c.get(Calendar.DATE);
        int dateWeek = c.get(Calendar.DAY_OF_WEEK);
        int month = c.get(Calendar.MONTH) + 1;
        String minuteString = minute + "";
        if (minuteString.length() == 1) {
            minuteString = "0" + minuteString;
        }

        if (isTime12) {
            int hour12 = hour % 12;
            tvFormat.setVisibility(View.VISIBLE);
            if (hour > 12) {
                tvFormat.setText("PM");
                tvTime.setText(hour12 + ":" + minuteString);
            } else {
                tvFormat.setText("AM");
                tvTime.setText(hour12 + ":" + minuteString);
            }
        } else {
            tvFormat.setVisibility(View.GONE);
            tvTime.setText(hour + ":" + minuteString);
        }


        tvDate.setText(Utils.getDateInWeek(this, dateWeek) + ", " + Utils.getMonthString(this, month) + " " + date);
//        Animation animation = AnimationUtils.loadAnimation(this, R.anim.abc_slide_in_bottom);
//        tvTime.startAnimation(animation);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        try {

            unregisterReceiver(broadcast);

//            unregisterReceiver(broadcastCall);

        } catch (Exception e) {

        }


//        unregisterReceiver(screenOff);
    }

    public void unlock() {
        try {
            if (layout != null)
                mWindowManager.removeView(layout);
            if (sharedPreferences.getBoolean(Values.ENABLE_VIBRATE, true))
                Utils.vibrate(this);
            if (sharedPreferences.getBoolean(Values.ENABLE_SOUND, true))
                Utils.sound(this, R.raw.unlock);

        } catch (Exception e) {

        } finally {
            mWindowManager = null;
            finish();
        }
    }

    private void setTvShimmer() {
        shimmer = new Shimmer();
        shimmer.setRepeatCount(200)
                .setDuration(2000)
                .setStartDelay(1000)
                .setDirection(Shimmer.ANIMATION_DIRECTION_LTR)
                .setAnimatorListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {

                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                });
        tvShimmer.setText(sharedPreferences.getString(Values.UNLOCK_TEXT, getString(R.string.slidetounlock)));
        tvShimmer.setTypeface(Utils.getTypefaceRobotoRegular(this));
        shimmer.start(tvShimmer);

    }


    private void mWindowAddView() {
        mWindowManager = (WindowManager) getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
        wmParams = new WindowManager.LayoutParams();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)

        {
            wmParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;

        }

        else {

            wmParams.type = WindowManager.LayoutParams.TYPE_PHONE;

        }
        wmParams.format = PixelFormat.RGBA_8888;
        //wmParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_PANEL;
//        wmParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_ERROR;
        //wmParams.flags = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        wmParams.flags = WindowManager.LayoutParams.FLAG_FULLSCREEN | WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN;
        wmParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        wmParams.height = WindowManager.LayoutParams.MATCH_PARENT;
        int uiOptions = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                | View.SYSTEM_UI_FLAG_IMMERSIVE;
        layout.setSystemUiVisibility(uiOptions);
        mWindowManager.addView(layout, wmParams);
//        Animation animation = AnimationUtils.loadAnimation(this, R.anim.zoom_in_background);
//        imgBackground.startAnimation(animation);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

        }
    }

//    private BroadcastCall broadcastCall;

    @Override
    protected void onResume() {
        super.onResume();


    }

    @Override
    protected void onStop() {
        super.onStop();

    }


    public class MyPagerAdapter extends PagerAdapter {
        Activity context;
//        LockHasPasscode lock2;


        public MyPagerAdapter(Activity context) {
            this.context = context;
//            lock2 = (LockHasPasscode) context;
        }


        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup collection, int position) {


            ((ViewPager) collection).addView(vLock, 0);
            ((ViewPager) collection).addView(vPassCode, 1);
            switch (position) {
                case 0:
                    return vPassCode;
                case 1:
                    return vLock;
            }
            return vLock;
        }

        public int getItemPosition(Object obj) {
            return obj == vPassCode ? 0 : obj == vLock ? 1 : -1;
        }

        @Override
        public void destroyItem(ViewGroup collection, int position, Object view) {
            ((ViewPager) collection).removeView((View) view);
        }


    }

    public void charging(boolean charging, int intExtra) {
        if (charging) {
            if (intExtra <= 10) {
                imgBattery.setImageResource(R.drawable.battery_10_green);
            } else if (intExtra > 10 && intExtra <= 20) {
                imgBattery.setImageResource(R.drawable.battery_20_green);
            } else if (intExtra > 20 && intExtra <= 35) {
                imgBattery.setImageResource(R.drawable.battery_35_green);
            } else if (intExtra > 35 && intExtra <= 50) {
                imgBattery.setImageResource(R.drawable.battery_50_green);
            } else if (intExtra > 50 && intExtra <= 75) {
                imgBattery.setImageResource(R.drawable.battery_75_green);
            } else if (intExtra > 75 && intExtra <= 90) {
                imgBattery.setImageResource(R.drawable.battery_90_green);
            } else if (intExtra > 90) {
                imgBattery.setImageResource(R.drawable.battery_full_green);
            }
        } else {
            if (intExtra <= 10) {
                imgBattery.setImageResource(R.drawable.battery_10);
            } else if (intExtra > 10 && intExtra <= 20) {
                imgBattery.setImageResource(R.drawable.battery_20);
            } else if (intExtra > 20 && intExtra <= 35) {
                imgBattery.setImageResource(R.drawable.battery_35);
            } else if (intExtra > 35 && intExtra <= 50) {
                imgBattery.setImageResource(R.drawable.battery_50);
            } else if (intExtra > 50 && intExtra <= 75) {
                imgBattery.setImageResource(R.drawable.battery_75);
            } else if (intExtra > 75 && intExtra <= 90) {
                imgBattery.setImageResource(R.drawable.battery_90);
            } else if (intExtra > 90) {
                imgBattery.setImageResource(R.drawable.battery_full);
            }
        }
        try {
            mWindowManager.updateViewLayout(layout, wmParams);
        } catch (Exception e) {

        }
        layout.postInvalidate();
    }

    class Broadcast extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {

            String action = intent.getAction();
            if (action.equals(Intent.ACTION_BATTERY_CHANGED)) {
                int intExtra = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);
                int plugin = intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, 0);
                tvBattery.setText(intExtra + "%");
                if (plugin != 0) {
                    imgCharging.setVisibility(View.VISIBLE);
                    charging(true, intExtra);
                } else {
                    imgCharging.setVisibility(View.GONE);
                    charging(false, intExtra);
                }
                return;

            } else if (action.equals(Intent.ACTION_TIME_CHANGED) || action.equals(Intent.ACTION_TIME_TICK) || action.equals(Intent.ACTION_TIMEZONE_CHANGED)) {
                setTime();
                return;
            }


//            case Intent.ACTION_POWER_DISCONNECTED:
//
//            break;
//            case Intent.ACTION_TIME_CHANGED:
//
//            break;


        }
    }

    public void setImgSignal(int level) {
        int img[] = new int[]{R.drawable.phone_signal_1,
                R.drawable.phone_signal_2,
                R.drawable.phone_signal_3,
                R.drawable.signal_4,
                R.drawable.phone_signal_4};
        imgSignal.setImageResource(img[level - 1]);
    }

    PhoneStateListener phoneStateListener = new PhoneStateListener() {
        int MAX_SIGNAL_DBM_VALUE = 31;

        @Override
        public void onSignalStrengthsChanged(SignalStrength signalStrength) {
            super.onSignalStrengthsChanged(signalStrength);
            if (signalStrength.isGsm()) {
                setImgSignal(calculateSignalStrengthInPercent(signalStrength.getGsmSignalStrength()));
            }
        }

        private int calculateSignalStrengthInPercent(int signalStrength) {
            return (int) ((float) signalStrength / MAX_SIGNAL_DBM_VALUE * 100);
        }
    };
    public static boolean isNotFirst = true;
    TelephonyManager telephony;
    CustomPhoneStateListener customPhoneListener;

    public class BroadcastCall extends BroadcastReceiver {


        @Override
        public void onReceive(Context context, Intent intent) {
            if (!isNotFirst)
                return;
            if (isNotFirst) {
//                telephony = (TelephonyManager) context.getSystemService(Service.TELEPHONY_SERVICE); //TelephonyManager object
//                customPhoneListener = new CustomPhoneStateListener();
//                telephony.listen(customPhoneListener, PhoneStateListener.LISTEN_CALL_STATE); //Register our listener with TelephonyManager

                Bundle bundle = intent.getExtras();
                String phoneNr = bundle.getString("incoming_number");
                isNotFirst = false;

            }
//            Log.v(TAG, "phoneNr: "+phoneNr);
//            mContext=context;

        }
    }

    int i = 0;
    public static boolean isfirst = true;

    public class CustomPhoneStateListener extends PhoneStateListener {


        private static final String TAG = "CustomPhoneStateListener";


        @Override
        public void onCallStateChanged(int state, String incomingNumber) {
            if (sharedPreferences.getBoolean(Values.ENABLE_PASSCODE,false)) {
                return;
            }
            i++;

            if (incomingNumber != null && incomingNumber.length() > 0)
//                incoming_nr=incomingNumber;

                switch (state) {
                    case TelephonyManager.CALL_STATE_RINGING:
                        Log.e("...........NO:  ","CALL_STATE_RINGING");
                        booleanisCall = true;
                        unlock();
                        isfirst = false;
 /*                       try {

                        if (layout.isShown())
                            mWindowManager.removeView(layout);
                       }catch (Exception e){
//
                       }
//                        isNotFirst = false;*/
//                    prev_state=state;
                        break;
                    case TelephonyManager.CALL_STATE_OFFHOOK:
                        Log.e("...........NO:  ", "CALL_STATE_OFFHOOK");
                        booleanisCall = true;
//                        try {
//                        if (!layout.isShown())
//
//                            mWindowManager.addView(layout, wmParams);
//                        }catch (Exception e){
//
//                        }
//                    prev_state=state;
                        break;
                    case TelephonyManager.CALL_STATE_IDLE:
                        Log.e("...........NO:  ", "CALL_STATE_IDLE ");
                        if (isfirst) {
                            return;
                        }
                        isfirst = false;
                        if (!isfirst) {
                            Log.e("...........NO:  ", "CALL_STATE_IDLE startactivity ");
                            booleanisCall = false;
                            Intent intent1 = new Intent(LockNoPasscode.this, LockNoPasscode.class);
                            intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_FROM_BACKGROUND);
                            startActivity(intent1);
                            isfirst = true;
                        }
                        if (telephony != null && customPhoneListener != null)
                            telephony.listen(customPhoneListener, PhoneStateListener.LISTEN_NONE);
//                        if (telephony != null && customPhoneListener != null)
//                            telephony.listen(customPhoneListener, PhoneStateListener.LISTEN_NONE);
//                        if (!layout.isShown()) {
//                            int uiOptions = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//                                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//                                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
//                                    | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
//                                    | View.SYSTEM_UI_FLAG_IMMERSIVE;
//                            layout.setSystemUiVisibility(uiOptions);
//                            mWindowManager.addView(layout, wmParams);
//                        }

//                    if((prev_state==TelephonyManager.CALL_STATE_OFFHOOK)){
//                        prev_state=state;
//                        //Answered Call which is ended
//                    }
//                    if((prev_state==TelephonyManager.CALL_STATE_RINGING)){
//                        prev_state=state;
//                        //Rejected or Missed call
//                    }
                        break;

                }
        }
    }
}






