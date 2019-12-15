package lockscreen.gpaddy.com.lockscreen.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.support.v7.app.ActionBar;
//import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.ironsource.mobilcore.CallbackResponse;
import com.ironsource.mobilcore.MobileCore;

import lockscreen.gpaddy.com.lockscreen.R;

import lockscreen.gpaddy.com.lockscreen.util.NotificationEnabledUtil;
import lockscreen.gpaddy.com.lockscreen.util.Utils;
import lockscreen.gpaddy.com.lockscreen.util.Values;


public class SettingActivity extends AppCompatActivity implements View.OnClickListener {
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private RelativeLayout menuActivate, menuReview, menuShare, menuRate, menuLauncher, menuPass, menuChangePass,
            menuTimeFormat, menuWallpaper, menuUnlockText, menuSound, menuVibrate, menuDisableLock, menuTimeOut;
    private CheckBox cbActivate, cbPassCode, cbSound, cbVibrate;
    private ActionBar actionBar;
    public static final int KEY_CHANGE_PASS = 1243;
    public static final int KEY_DISABLE_PASS = 1213;
    public static final int KEY_NEW_PASS = 1276;
    private Toolbar toolbar;
    private boolean askedForOverlayPermission = false;
    //admob
    private AdView adView;
    private AdRequest adRequest;
    private int OVERLAY_PERMISSION_CODE = 200;
    private InterstitialAd interstitialAd;
    private AdRequest adRequestFull;

    private void requestNewInterstitial() {
        adRequestFull = new AdRequest.Builder()
                .addTestDevice("5DE009358208E67E37FD2A7F7661044A")
                .build();

        interstitialAd.loadAd(adRequestFull);
    }

    private void showFullAds() {
        if (interstitialAd != null && interstitialAd.isLoaded()) {
            interstitialAd.show();
        }
    }

    public void addOverlay() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!Settings.canDrawOverlays(this)) {
                askedForOverlayPermission = true;
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + getApplicationContext().getPackageName()));
                startActivityForResult(intent, OVERLAY_PERMISSION_CODE);
            } else {
                init();
            }
        } else {
            init();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //init advertising
        //MobileCore.init(this, "4FJA7P5PNXXR1PVGK0LY7NLHSL62D", MobileCore.LOG_TYPE.DEBUG, MobileCore.AD_UNITS.STICKEEZ, MobileCore.AD_UNITS.INTERSTITIAL);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = sharedPreferences.edit();
        setContentView(R.layout.activity_setting);
        addOverlay();
        //admob

    }

    @Override
    protected void onResume() {
        super.onResume();
        permissionNotify();
//        if (MobileCore.isStickeeReady()) {
//            MobileCore.showStickee(this);
//        }
    }

    private void permissionNotify() {
        if (Build.VERSION.SDK_INT < 23 || NotificationEnabledUtil.isServicePermission(this)) {
            init();
        } else {
            NotificationEnabledUtil.requestPermission(this);
        }
    }

    private void init() {
        /*adView = (AdView) findViewById(R.id.adView);
        adRequest = new AdRequest.Builder().addTestDevice("5DE009358208E67E37FD2A7F7661044A").build();
        adView.loadAd(adRequest);*/
        NotificationEnabledUtil.startService(this);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
//        try {
//            registerReceiver(ScreenOn.newInstance(), new IntentFilter(Intent.ACTION_SCREEN_OFF));
//        }catch (Exception e){
//
//        }

//        getActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.actionbar)));
        /*menuActivate = (RelativeLayout) findViewById(R.id.menuActivate);
        menuActivate.setOnClickListener(this);
        menuReview = (RelativeLayout) findViewById(R.id.menuReview);
        menuReview.setOnClickListener(this);
        menuShare = (RelativeLayout) findViewById(R.id.menuShare);
        menuShare.setOnClickListener(this);
        menuRate = (RelativeLayout) findViewById(R.id.menuRate);
        menuRate.setOnClickListener(this);
        menuLauncher = (RelativeLayout) findViewById(R.id.menuLauncher);
        menuLauncher.setOnClickListener(this);*/
        menuPass = (RelativeLayout) findViewById(R.id.menuPass);
        menuPass.setOnClickListener(this);
        /*menuChangePass = (RelativeLayout) findViewById(R.id.menuChangePass);
        menuChangePass.setOnClickListener(this);*/
        menuTimeFormat = (RelativeLayout) findViewById(R.id.menuTimeFormat);
        menuTimeFormat.setOnClickListener(this);
        menuWallpaper = (RelativeLayout) findViewById(R.id.menuWallpaper);
        menuWallpaper.setOnClickListener(this);
        menuUnlockText = (RelativeLayout) findViewById(R.id.menuUnlockText);
        menuUnlockText.setOnClickListener(this);
        menuSound = (RelativeLayout) findViewById(R.id.menuSound);
        menuSound.setOnClickListener(this);
        menuVibrate = (RelativeLayout) findViewById(R.id.menuVibrate);
        menuVibrate.setOnClickListener(this);

        /*menuDisableLock = (RelativeLayout) findViewById(R.id.menuDisableLock);
        menuDisableLock.setOnClickListener(this);*/

        menuTimeOut = (RelativeLayout) findViewById(R.id.menuTimeout);
        menuTimeOut.setOnClickListener(this);


//        cbActivate = (CheckBox) findViewById(R.id.cbActivate);
        cbPassCode = (CheckBox) findViewById(R.id.cbPassCode);
        cbSound = (CheckBox) findViewById(R.id.cbSound);
        cbVibrate = (CheckBox) findViewById(R.id.cbVibrate);

        cbSound.setChecked(sharedPreferences.getBoolean(Values.ENABLE_SOUND, true));
        cbVibrate.setChecked(sharedPreferences.getBoolean(Values.ENABLE_VIBRATE, true));


        boolean passCode = sharedPreferences.getBoolean(Values.ENABLE_PASSCODE, false);
        cbPassCode.setChecked(passCode);

        /*if (passCode) {
            menuChangePass.setVisibility(View.VISIBLE);
        } else menuChangePass.setVisibility(View.GONE);*/
        //cbActivate.setChecked(sharedPreferences.getBoolean(Values.ACTIVATE_LOCK, true));

        /*admob
        MobileAds.initialize(getApplicationContext(), getResources().getString(R.string.app_id));
        interstitialAd = new InterstitialAd(this);
        interstitialAd.setAdUnitId(getResources().getString(R.string.ad_unit_full));
        requestNewInterstitial();

        interstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                super.onAdClosed();
                requestNewInterstitial();
            }
        });
        */
    }

    @Override
    public void onBackPressed() {
//        if (MobileCore.isInterstitialReady()) {
//            MobileCore.showInterstitial(this, new CallbackResponse() {
//                @Override
//                public void onConfirmation(TYPE type) {
//                    finish();
//                }
//            });
//        } else {
//            finish();
//            ;
//        }
        //showFullAds();
        finish();
    }

    private void changeUnlockText() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_change_unlock_text);
        final EditText editText = (EditText) dialog.findViewById(R.id.edtUnlock);
        editText.setText(sharedPreferences.getString(Values.UNLOCK_TEXT, getString(R.string.slidetounlock)));
        Button ok = (Button) dialog.findViewById(R.id.btnOk);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = editText.getText().toString();
                if (text.length() > 0) {
                    editor.putString(Values.UNLOCK_TEXT, text);
                    editor.commit();
                    dialog.dismiss();
                }
            }
        });
        Button cancel = (Button) dialog.findViewById(R.id.btnCancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void changeTimeFormat() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        CharSequence format[] = new CharSequence[]{getString(R.string.time_format_12), getString(R.string.time_format_24)};
        boolean time12 = sharedPreferences.getBoolean(Values.TIME_FORMAT, false);
        int pos = time12 ? 0 : 1;

        builder.setSingleChoiceItems(format, pos, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                editor.putBoolean(Values.TIME_FORMAT, which == 0 ? true : false);
                editor.commit();
            }
        });
        builder.show();
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
//            case R.id.menuActivate:
//                boolean oldStateActivate = cbActivate.isChecked();
//                cbActivate.setChecked(!oldStateActivate);
//                if (oldStateActivate) {
//                    unregisterReceiver(ScreenOn.newInstance());
//                } else {
//                    try {
//                        registerReceiver(ScreenOn.newInstance(), new IntentFilter(Intent.ACTION_SCREEN_OFF));
//                    } catch (Exception e) {
//
//                    }
//                }
//                editor.putBoolean(Values.ACTIVATE_LOCK, !oldStateActivate);
//                editor.commit();
//                break;
//            case R.id.menuDisableLock:
//                Intent intent = new Intent();
//                intent.setAction("android.app.action.SET_NEW_PASSWORD");
//                startActivity(intent);
//                break;
//            case R.id.menuReview:
//                boolean hasPassCode = sharedPreferences.getBoolean(Values.ENABLE_PASSCODE, false);
//                if (hasPassCode) {
//                    startActivity(new Intent(this, LockHasPasscode.class));
//                } else {
//                    startActivity(new Intent(this, LockNoPasscode.class));
//                }
//
//
//                break;
//            case R.id.menuShare:
//                Utils.shareClick(this, this.getPackageName());
//                break;
//            case R.id.menuRate:
//                Utils.gotoMarket(this);
//
//                break;
//            case R.id.menuLauncher:
//                String linkLauncher = "https://play.google.com/store/apps/details?id=com.iphone.launcher.prank";
//                Utils.gotoMarket(this, linkLauncher);
//                break;

            case R.id.menuTimeout:
                final CharSequence[] time_out = getResources().getStringArray(R.array.time_out);
                final long[] timeLong = {0L, 5000L, 10000L, 20000L, 30000L, 60000L, 120000L};
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                int which = 1;
                long time = sharedPreferences.getLong(Values.TIME_OUT, 10000L);
                for (int i = 0; i < timeLong.length; i++) {
                    if (timeLong[i] == time) {
                        which = i;
                        break;
                    }
                }
                builder.setSingleChoiceItems(time_out, which, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        editor.putLong(Values.TIME_OUT, timeLong[which]);
                        editor.commit();
                        dialog.dismiss();
                    }
                });

                builder.show();
                break;
            case R.id.menuPass:
                boolean oldStatePass = cbPassCode.isChecked();

                if (oldStatePass) {

                    startActivityForResult(new Intent(this, DisablePass.class), KEY_DISABLE_PASS);

                } else {
                    startActivityForResult(new Intent(this, ChangePassCodeActivity.class), KEY_CHANGE_PASS);

                }

                break;

            /*case R.id.menuChangePass:
                startActivityForResult(new Intent(this, DisablePass.class), KEY_NEW_PASS);
                break;*/

            case R.id.menuTimeFormat:
                changeTimeFormat();
                break;
            case R.id.menuWallpaper:
                startActivity(new Intent(this, PaddyGetBackgroundActivity.class));

                break;
            case R.id.menuUnlockText:
                changeUnlockText();
                break;
            case R.id.menuSound:
                boolean sound = sharedPreferences.getBoolean(Values.ENABLE_SOUND, true);
                cbSound.setChecked(!sound);
                editor.putBoolean(Values.ENABLE_SOUND, !sound);
                editor.commit();
                break;
            case R.id.menuVibrate:
                boolean vibrate = sharedPreferences.getBoolean(Values.ENABLE_VIBRATE, true);
                cbVibrate.setChecked(!vibrate);
                editor.putBoolean(Values.ENABLE_VIBRATE, !vibrate);
                editor.commit();
                break;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == OVERLAY_PERMISSION_CODE) {
            askedForOverlayPermission = false;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (Settings.canDrawOverlays(this)) {
                    init();
                } else {
                    addOverlay();
                }
            } else {
                init();
            }
        }
        if (requestCode == KEY_CHANGE_PASS) {
            if (resultCode == Activity.RESULT_OK) {
                String code = data.getStringExtra("result");
                editor.putString(Values.PASSCODE, code);
                editor.commit();
                //menuChangePass.setVisibility(View.VISIBLE);
                cbPassCode.setChecked(true);
                editor.putBoolean(Values.ENABLE_PASSCODE, true);
                editor.commit();

            }
        } else if (requestCode == KEY_DISABLE_PASS) {
            if (resultCode == Activity.RESULT_OK) {
                //menuChangePass.setVisibility(View.GONE);
                cbPassCode.setChecked(false);
                editor.putBoolean(Values.ENABLE_PASSCODE, false);
                editor.commit();
            }
        } else if (requestCode == KEY_NEW_PASS) {
            if (resultCode == Activity.RESULT_OK) {
                startActivityForResult(new Intent(this, ChangePassCodeActivity.class), KEY_CHANGE_PASS);
            }
        }
    }
}
