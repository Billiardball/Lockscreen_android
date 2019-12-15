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
import android.graphics.BitmapFactory;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.BatteryManager;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.accessibility.AccessibilityEventCompat;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import android.support.v7.app.AppCompatActivity;
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
import android.widget.Toast;

import com.romainpiel.shimmer.Shimmer;
import com.romainpiel.shimmer.ShimmerTextView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Calendar;

import lockscreen.gpaddy.com.lockscreen.R;
import lockscreen.gpaddy.com.lockscreen.fragment.FragmentLock;
import lockscreen.gpaddy.com.lockscreen.fragment.FragmentLockPassCode;
import lockscreen.gpaddy.com.lockscreen.util.Blur;
import lockscreen.gpaddy.com.lockscreen.util.MyApplication;
import lockscreen.gpaddy.com.lockscreen.util.Utils;
import lockscreen.gpaddy.com.lockscreen.util.Values;
import lockscreen.gpaddy.com.lockscreen.view.MyViewPager;
import lockscreen.gpaddy.com.lockscreen.view.TextViewRobotoLight;
import lockscreen.gpaddy.com.lockscreen.view.TextViewRobotoThin;


public class LockHasPasscode extends FragmentActivity implements View.OnClickListener {
    private View screen;
    private static RelativeLayout layout = null;
    //    private ImageView imgCamera;
    private int mScreenWidth = 0;
    private WindowManager.LayoutParams wmParams;
    private static WindowManager mWindowManager;
    private ViewPager pager;
    private TextViewRobotoThin tvTime;

    private TextViewRobotoLight btnCancel, tvDate;
    private ShimmerTextView tvShimmer;
    private Shimmer shimmer;
    private View vPassCode, vLock;
    private ImageView imgPass;
    private ImageView btn0, btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9;
    private StringBuilder code;
    private String key;
    private ImageView imgSignal, imgWifi, imgBattery, imgCharging;
    private TextViewRobotoLight tvOperatorName, tvBattery;
    private ImageView imgBackground, imgBackgroundBlur;
    private SharedPreferences sharedPreferences;
    private TextViewRobotoThin tvFormat;
    private Bitmap blur;
    public static boolean booleanisCall = false;
    private int idBackground;
    private BroadcastCall broadcastCall;
    static boolean isCallRegister = false;
    static int IDLE = 0;
    int lastPage;

    private boolean hasPassCode = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (mWindowManager != null) {
            Log.e("..............", "oncreate return");
            return;
        }
        Log.e("..............", "oncreate");
       /* View decorView = getWindow().getDecorView();
// Hide both the navigation bar and the status bar.
// SYSTEM_UI_FLAG_FULLSCREEN is only available on Android 4.1 and higher, but as
// a general rule, you should design your app to hide the status bar whenever you
// hide the navigation bar.
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);*/

        layout = (RelativeLayout) View.inflate(this, R.layout.activity_lock2, null);
        pager = (ViewPager) layout.findViewById(R.id.pager);
        pager.setOffscreenPageLimit(2);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        hasPassCode = sharedPreferences.getBoolean(Values.ENABLE_PASSCODE, true);
        key = sharedPreferences.getString(Values.PASSCODE, "");
        idBackground = sharedPreferences.getInt(Values.BACKGROUND_RESOURCE_ID, R.drawable.b1);

        LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        vPassCode = layoutInflater.inflate(R.layout.fragment_lock_passcode, null);

        vPassCode = layoutInflater.inflate(R.layout.fragment_lock_passcode, null);

        vLock = layoutInflater.inflate(R.layout.fragment_lock, null);
        bindView();
        btnCancel = (TextViewRobotoLight) vPassCode.findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(this);
        setTvShimmer();
        code = new StringBuilder();
//       onBackPressed();
        mWindowAddView();
        pager.setAdapter(new MyPagerAdapter(this));


        pager.post(new Runnable() {
            @Override
            public void run() {
                pager.setCurrentItem(1, false);
            }
        });

        pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            public float thresholdOffset;
            private boolean scrollStarted, checkDirection;

            @Override
            public void onPageScrolled(int position, final float positionOffset, int positionOffsetPixels) {
//                int curentpos = pager.getCurrentItem();
////                Log.e(">.............", +"");
//                if (curentpos == 1) {
//                    if (firstRight) {
//                    Log.e(">.............", "left " + positionOffset);
//                        layout.post(new Runnable() {
//                            @Override
//                            public void run() {
//                                imgBackgroundBlur.setAlpha(1 - positionOffset);
//                                imgBackground.setAlpha(positionOffset);
//                            }
//                        });

//                } else {
//                    Log.e(">.............", "right " +positionOffset);
//                        layout.post(new Runnable() {
//                            @Override
//                            public void run() {
//                                imgBackgroundBlur.setAlpha(positionOffset);
//                                imgBackground.setAlpha(1 - positionOffset);
//                            }
//                        });

//                }


//                Log.e(">.............", position + "       " + positionOffset + "            " + "             " + positionOffsetPixels);


//
//                if(firstRight){
//                    Log.e(">.............", "left");
//                    imgBackgroundBlur.post(new Runnable() {
//                        @Override
//                        public void run() {
//                       imgBackgroundBlur.setAlpha(positionOffset);
//                            imgBackground.setAlpha(1-positionOffset);
//                        }
//                    });
//
//                }
//                else {
//                    imgBackgroundBlur.post(new Runnable() {
//                        @Override
//                        public void run() {
//                            imgBackgroundBlur.setAlpha(1-positionOffset);
//                            imgBackground.setAlpha(positionOffset);
//                        }
//                    });
//
//                }
//                if(position==1) firstRight = true;

//                float prePos = -1;
//                if(prePos>positionOffset){
//                    prePos = positionOffset;
//                }
//                if(prePos+0.01f>positionOffset){
//                    Log.e(">.............", "left");
//                }

//                if (firstRight) {
//                    prePos = positionOffset;
//                    firstRight = false;
//
//                }

//                if (positionOffset < prePos) {
//                    Log.e(">.............", "left");
//                } else Log.e(">.............", "right");


////                int i = 0;
////                i++;
            }

            //
            @Override
            public void onPageSelected(int position) {
//                return;
//                if (position == 0) {
//                    firstRight = false;
//                }
                if (position == 1) {
                    imgBackground.setVisibility(View.VISIBLE);
//                    imgBackgroundBlur.setVisibility(View.GONE);
                    return;
                } else {
                    imgBackground.setVisibility(View.GONE);
//                    imgBackgroundBlur.setVisibility(View.VISIBLE);
                    return;
                }
//                if (position == 0) {
//                    imgBackground.setVisibility(View.GONE);
//                    imgBackgroundBlur.setVisibility(View.VISIBLE);
//                }
//                    try {
//                        imgBackground.post(new Runnable() {
//                            @Override
//                            public void run() {
////                                imgBackground.setImageBitmap(MyApplication.blur);
//                                imgBackground.setVisibility(View.GONE);
//                                imgBackgroundBlur.setVisibility(View.VISIBLE);
//                            }
//                        });
//
//                    } catch (Exception e) {
//
//                    }
////                    imgBackground.setVisibility(View.GONE);
////                    imgBackgroundBlur.setVisibility(View.VISIBLE);
//                } else {
//                    imgBackground.setVisibility(View.VISIBLE);
//                    imgBackgroundBlur.setVisibility(View.GONE);
//                    imgBackground.post(new Runnable() {
//                        @Override
//                        public void run() {
////                            imgBackground.setImageBitmap(MyApplication.blur);
////                            imgBackground.setImageBitmap(MyApplication.notBlur);
//                            imgBackground.setVisibility(View.VISIBLE);
//                            imgBackgroundBlur.setVisibility(View.GONE);
//                        }
//                    });


//                    imgBackground.setVisibility(View.VISIBLE);
//                    imgBackgroundBlur.setVisibility(View.GONE);
//                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
//return;

            }
        });
//        broadcastCall = new BroadcastCall();
//        registerReceiver(broadcastCall, new IntentFilter("android.intent.action.PHONE_STATE"));

        if (!isCallRegister) {
            telephony = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE); //TelephonyManager object

            customPhoneListener = new CustomPhoneStateListener();
            //telephony.
            telephony.listen(customPhoneListener, PhoneStateListener.LISTEN_CALL_STATE);
            Log.e("............", "register");
            IDLE = 0;
            isCallRegister = true;
        }

        setTime();
        telephonyManager = (TelephonyManager) this.getSystemService(TELEPHONY_SERVICE);
        telephonyManager.listen(phoneStateListener, TelephonyManager.PHONE_TYPE_GSM);
        tvOperatorName.setText(telephonyManager.getNetworkOperatorName());
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_BATTERY_CHANGED);
//        filter.addAction(Intent.ACTION_POWER_CONNECTED);
//        filter.addAction(Intent.ACTION_POWER_DISCONNECTED);
        filter.addAction(Intent.ACTION_TIME_TICK);
        filter.addAction(Intent.ACTION_TIMEZONE_CHANGED);
        registerReceiver(broadcast, filter);
    }

    private void bindView() {
        tvShimmer = (ShimmerTextView) vLock.findViewById(R.id.shimmer_tv);
        tvDate = (TextViewRobotoLight) vLock.findViewById(R.id.tvDate);
        tvTime = (TextViewRobotoThin) vLock.findViewById(R.id.tvTime);

        imgPass = (ImageView) vPassCode.findViewById(R.id.imgPass);
        btn0 = (ImageView) vPassCode.findViewById(R.id.btn0);
        btn0.setOnClickListener(this);
        btn1 = (ImageView) vPassCode.findViewById(R.id.btn1);
        btn1.setOnClickListener(this);
        btn2 = (ImageView) vPassCode.findViewById(R.id.btn2);
        btn2.setOnClickListener(this);
        btn3 = (ImageView) vPassCode.findViewById(R.id.btn3);
        btn3.setOnClickListener(this);
        btn4 = (ImageView) vPassCode.findViewById(R.id.btn4);
        btn4.setOnClickListener(this);
        btn5 = (ImageView) vPassCode.findViewById(R.id.btn5);
        btn5.setOnClickListener(this);
        btn6 = (ImageView) vPassCode.findViewById(R.id.btn6);
        btn6.setOnClickListener(this);
        btn7 = (ImageView) vPassCode.findViewById(R.id.btn7);
        btn7.setOnClickListener(this);
        btn8 = (ImageView) vPassCode.findViewById(R.id.btn8);
        btn8.setOnClickListener(this);
        btn9 = (ImageView) vPassCode.findViewById(R.id.btn9);
        btn9.setOnClickListener(this);

        tvFormat = (TextViewRobotoThin) vLock.findViewById(R.id.tvFormat);


        imgSignal = (ImageView) layout.findViewById(R.id.imgSignal);
        imgWifi = (ImageView) layout.findViewById(R.id.imgWifi);
        imgBattery = (ImageView) layout.findViewById(R.id.imgBattery);
        imgCharging = (ImageView) layout.findViewById(R.id.imgCharging);
        tvOperatorName = (TextViewRobotoLight) layout.findViewById(R.id.tvOperatorName);
        tvBattery = (TextViewRobotoLight) layout.findViewById(R.id.tvBattery);
        setStatusBar();


        imgBackground = (ImageView) layout.findViewById(R.id.imgBackground);
        imgBackgroundBlur = (ImageView) layout.findViewById(R.id.imgBackgroundBlur);
//        imgBackground.setImageResource(idBackground);

        boolean isResource = sharedPreferences.getBoolean(Values.BACKGROUND_RESOUECE_BOLEAN, true);
        try {
            if (!isResource) {
//                setWallpaper();
//                Uri uri = Uri.parse(preferences.getString(Values.BACKGROUND_URI, ""));
//                this.background.setImageURI(uri);

                Uri imageUri = Uri.parse(sharedPreferences.getString(Values.BACKGROUND_URI, ""));
                Drawable myDrawable = Drawable.createFromPath(sharedPreferences.getString(Values.BACKGROUND_URI, ""));

                Bitmap bitmap = null;
                bitmap = ((BitmapDrawable) myDrawable).getBitmap();
                imgBackground.setImageBitmap(bitmap);
                if (MyApplication.blur == null) {
                    blur = Blur.fastblur(this, bitmap, 25);
                    MyApplication.blur = blur;
                }
//            imgBackgroundBlur.setImageBitmap(MyApplication.blur);


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
                if (MyApplication.blur == null) {
                    Bitmap bitmap = BitmapFactory.decodeResource(getResources(), idBackground);
                    blur = Blur.fastblur(this, bitmap, 25);
                    MyApplication.blur = blur;

                }
            }
//        imgBackgroundBlur.setImageResource(R.drawable.b12);


            imgBackgroundBlur.setImageBitmap(MyApplication.blur);
        } catch (Exception e) {

        }
//        if (MyApplication.notBlur == null) {
//            MyApplication.notBlur = BitmapFactory.decodeResource(getResources(), idBackground);
//        }

//        imgBackgroundBlur.setImageBitmap(fastblur);


    }

    Broadcast broadcast;
    TelephonyManager telephonyManager;

    public void setStatusBar() {

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
        Log.e("..............", "onDestroy HasPass");
//        if (telephony != null && customPhoneListener != null)
//            telephony.listen(customPhoneListener, PhoneStateListener.LISTEN_NONE);
        super.onDestroy();
//        unregisterReceiver(broadcastCall);
        try {
            unregisterReceiver(broadcast);
        } catch (Exception e) {

        }
//        try {

//        }catch (Exception e){
//
//        }
    }


    private void setImageCode() {
        int size = code.length();
        Utils.sound(this, R.raw.type_keyboard);

        switch (size) {
            case 0:
                imgPass.setImageResource(R.drawable.pas7);
                break;
            case 1:
                imgPass.setImageResource(R.drawable.pas1);
                break;
            case 2:
                imgPass.setImageResource(R.drawable.pas2);
                break;
            case 3:
                imgPass.setImageResource(R.drawable.pas3);
                break;
            case 4:
                imgPass.setImageResource(R.drawable.pas4);
                break;
            case 5:
                imgPass.setImageResource(R.drawable.pas5);
                break;
            case 6:
                imgPass.setImageResource(R.drawable.pas6);
                if (code.toString().equals(key)) {

                    unlock();
                    if (telephony != null && customPhoneListener != null) {
                        telephony.listen(customPhoneListener, PhoneStateListener.LISTEN_NONE);
                        Log.e("................", "unregister");
                        isCallRegister = false;
                    }
                } else {
                    Animation animation = AnimationUtils.loadAnimation(this, R.anim.shake);
                    imgPass.startAnimation(animation);
                    Utils.vibrate(this);
                    code = new StringBuilder();
                    setImageCode();
                }
                break;
        }
    }


    public void unlock() {
        try {
            if (layout != null) {
                mWindowManager.removeView(layout);
                Log.e("...........PASS: ", "mWindowManager.removeView(layout)");
            }
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
                .setStartDelay(500)
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
        wmParams.format = PixelFormat.TRANSLUCENT;
        if(Build.VERSION.SDK_INT <26)
            wmParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_ERROR;
        else
            wmParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
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
        Log.e(".............", "addView");

//        Animation animation = AnimationUtils.loadAnimation(this, R.anim.zoom_in_background);
//        imgBackground.startAnimation(animation);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnCancel:
                if (code.length() > 0) {
                    code.deleteCharAt(code.length() - 1);
                    setImageCode();
                } else
                    pager.post(new Runnable() {
                        @Override
                        public void run() {
                            pager.setCurrentItem(1);
                        }
                    });
                break;

            case R.id.btn0:
                code.append(0);
                setImageCode();
                break;
            case R.id.btn1:
                code.append(1);
                setImageCode();
                break;
            case R.id.btn2:
                code.append(2);
                setImageCode();
                break;
            case R.id.btn3:
                code.append(3);
                setImageCode();
                break;
            case R.id.btn4:
                code.append(4);
                setImageCode();
                break;
            case R.id.btn5:
                code.append(5);
                setImageCode();
                break;
            case R.id.btn6:
                code.append(6);
                setImageCode();
                break;
            case R.id.btn7:
                code.append(7);
                setImageCode();
                break;
            case R.id.btn8:
                code.append(8);
                setImageCode();
                break;
            case R.id.btn9:
                code.append(9);
                setImageCode();

                break;
        }


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


    @Override
    protected void onStop() {
        Log.e("..............", "onStop HasPass");

        super.onStop();
    }

    @Override
    protected void onResume() {
        super.onResume();


    }

    public void setImgSignal(int level) {
        int img[] = new int[]{R.drawable.phone_signal_1,
                R.drawable.phone_signal_2,
                R.drawable.phone_signal_3,
                R.drawable.signal_4,
                R.drawable.phone_signal_4};
        imgSignal.setImageResource(img[level - 1]);
    }

    private static final int EXCELLENT_LEVEL = 75;
    private static final int GOOD_LEVEL = 50;
    private static final int MODERATE_LEVEL = 25;
    private static final int WEAK_LEVEL = 0;

    private void setSignalLevel(int level) {

        int progress = (int) ((((float) level) / 31.0) * 100);

        if (progress > EXCELLENT_LEVEL) {
            // 5 song
            imgSignal.setImageResource(R.drawable.phone_signal_5);
        } else if (progress > GOOD_LEVEL) {
            // 4 song
            imgSignal.setImageResource(R.drawable.phone_signal_4);
        } else if (progress > MODERATE_LEVEL) {
            // 3 song
            imgSignal.setImageResource(R.drawable.phone_signal_3);
        } else if (progress > WEAK_LEVEL) {
            // 2 song
            imgSignal.setImageResource(R.drawable.phone_signal_2);
        } else if (progress <= WEAK_LEVEL) {
            // No operator
            imgSignal.setVisibility(View.GONE);
            tvOperatorName.setText(getString(R.string.paddy_no_signal));
        }

//        Log.i("signalLevel ", "" + progress);
    }

    PhoneStateListener phoneStateListener = new PhoneStateListener() {
        int MAX_SIGNAL_DBM_VALUE = 31;

        public void onSignalStrengthChanged(int asu) {
            // Set trang thai song di dong
            setSignalLevel(asu);

            super.onSignalStrengthChanged(asu);
        }


//        @Override
//        public void onSignalStrengthsChanged(SignalStrength signalStrength) {
//            super.onSignalStrengthsChanged(signalStrength);
//            int signalStrengthValue;
//
//            if (signalStrength.isGsm()) {
//                if (signalStrength.getGsmSignalStrength() != 99)
//                    signalStrengthValue = signalStrength.getGsmSignalStrength() * 2 - 113;
//                else
//                    signalStrengthValue = signalStrength.getGsmSignalStrength();
//            } else {
//                signalStrengthValue = signalStrength.getCdmaDbm();
//            }
//            Toast.makeText(LockHasPasscode.this,signalStrengthValue+" ",Toast.LENGTH_LONG).show();
////            txtSignalStr.setText("Signal Strength : " + signalStrengthValue);
//        }
//
//        private int calculateSignalStrengthInPercent(int signalStrength) {
//            return (int) ((float) signalStrength / MAX_SIGNAL_DBM_VALUE * 100);
//        }
    };
    static CustomPhoneStateListener customPhoneListener;
    static TelephonyManager telephony;

    class BroadcastCall extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
//            if (!isNotFirst)
//                return;
//            if (isNotFirst) {
//                telephony = (TelephonyManager) context.getSystemService(Service.TELEPHONY_SERVICE); //TelephonyManager object
//                customPhoneListener = new CustomPhoneStateListener();
//                telephony.listen(customPhoneListener, PhoneStateListener.LISTEN_CALL_STATE); //Register our listener with TelephonyManager
//
//                Bundle bundle = intent.getExtras();
//                String phoneNr = bundle.getString("incoming_number");
//                isNotFirst = false;
//
//            }
////            Log.v(TAG, "phoneNr: "+phoneNr);
////            mContext=context;

        }
    }

    public static boolean isfirst = true;

    public class CustomPhoneStateListener extends PhoneStateListener {


        private static final String TAG = "CustomPhoneStateListener";


        @Override
        public void onCallStateChanged(int state, String incomingNumber) {
            if (!sharedPreferences.getBoolean(Values.ENABLE_PASSCODE, true)) {
                return;
            }

            switch (state) {
                case TelephonyManager.CALL_STATE_RINGING:
                    Log.e("...........PASS:  ", "CALL_STATE_RINGING");
                    unlock();
                    booleanisCall = true;
                    isfirst = false;
                    break;

                case TelephonyManager.CALL_STATE_OFFHOOK:
                    booleanisCall = true;
                    Log.e("...........PASS:  ", "CALL_STATE_OFFHOOK");
                    break;
                case TelephonyManager.CALL_STATE_IDLE:
                    Log.e("...........PASS:  ", "CALL_STATE_IDLE");
                    IDLE++;
                    if (IDLE <= 1) {
                        IDLE = 1;
                        Log.e("...........PASS:  ", "IDLE <= 1");
                        return;
                    }


                    if (isfirst) {
                        return;
                    }
                    isfirst = false;
                    if (!isfirst) {
                        booleanisCall = false;
                        Intent intent1 = new Intent(LockHasPasscode.this, LockHasPasscode.class);
                        intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_FROM_BACKGROUND);
                        startActivity(intent1);
                        Log.e("...........PASS:  ", "startactivitylock");
                        isfirst = true;
                    }
                    break;

            }
        }
    }

}






