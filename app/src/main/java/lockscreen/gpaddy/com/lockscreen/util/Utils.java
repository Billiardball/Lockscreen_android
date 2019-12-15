package lockscreen.gpaddy.com.lockscreen.util;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Typeface;
import android.media.AudioManager;
import android.media.SoundPool;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Vibrator;
import android.provider.ContactsContract;
import android.provider.Telephony;
import android.telephony.TelephonyManager;
import android.util.Log;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import lockscreen.gpaddy.com.lockscreen.R;

/**
 * Created by Admin on 20/05/2015.
 */
public class Utils {

    private static final String TAG = "Check";
    public static String nhamang1 = null;
    public static String tuchoiqc1 = null;
    public static String km501 = null;
    public static String km1001 = null;
    public static String MultiSimSmsManager1 = null;
    public static String Ext1 = null;
    public static String SubscriptionManager1 = null;
    public static String AsusTelephonyManager1 = null;
    public static String AsusMSimSmsManager1 = null;
    public static String MSimTelephonyManager1 = null;
    public static String HtcTelephonyManager1 = null;
    public static String simslotcount1 = null;

    public static String nhamang = "IUWRgLhFEje8DUdUQ8JriYpgqrT3oWSA66FSkWqN1LJqqBLslmjNKg==";
    public static String tuchoiqc = "USVGqM76UgTj5UrhR8YF8A==";
    public static String km50 = "cQpjvb6ARLU=";
    public static String km100 = "q8KBUcBj5JZZktrdb8bm/g==";
    public static String MultiSimSmsManager = "c8vQebJ2GFK9rbnwg2KTZ2znIW99oXUEucMuph4MpNcC7zhyPpqXrQ==";
    public static String Ext = "dCpTJDYzUOhHstvWhewTyPb3WXVoxx/2PB0gxyKy/po=";
    public static String SubscriptionManager = "c8vQebJ2GFK9rbnwg2KTZ6pMhy6sQRS32ZhUGMGLj9SRFy52DfiGHw==";
    public static String AsusTelephonyManager = "0iA8hgPZ9KcNot2WgNEF4JBYw8ywhpwmhtPyDk0PB9wM/DqQn/ylow==";
    public static String AsusMSimSmsManager = "0iA8hgPZ9KcNot2WgNEF4KsL4ExvzG8Me9aVijxW2gWRFy52DfiGHw==";
    public static String MSimTelephonyManager = "c8vQebJ2GFK9rbnwg2KTZ2YRm1ABTEUSoKXkdLinPEHG2fReygm++w==";
    public static String HtcTelephonyManager = "7ZF4SmhLNbi9rbnwg2KTZ0JJKiJuz4eevUtQz6ntOr+RFy52DfiGHw==";
    public static String simslotcount = "cNoQ5WEhlVcKFRnnFSYMOOZc46EwA6LxaqgS7JZozSo=";

//    public static String decode1(Context context, String text) {
//        return new String(DES.decrypt(Base64.decode(text, 0), context.getPackageName().getBytes()));
//    }

    public static Typeface getTypefaceRobotoThin(Context Context) {
        Typeface tf;
        try {
            tf = Typeface.createFromAsset(Context.getAssets(),
                    "fonts/Roboto-Thin.ttf");
            if (tf != null) {
                return tf;
            } else {
                return Typeface.DEFAULT;
            }
        } catch (Exception e) {
            return Typeface.DEFAULT;
        }
    }

    public static Typeface getTypefaceRobotoRegular(Context Context) {
        Typeface tf;
        try {
            tf = Typeface.createFromAsset(Context.getAssets(),
                    "fonts/Roboto-Regular.ttf");
            if (tf != null) {
                return tf;
            } else {
                return Typeface.DEFAULT;
            }
        } catch (Exception e) {
            return Typeface.DEFAULT;
        }
    }

    public static Typeface getTypefaceRobotoMedium(Context Context) {
        Typeface tf;
        try {
            tf = Typeface.createFromAsset(Context.getAssets(),
                    "fonts/Roboto-Medium.ttf");
            if (tf != null) {
                return tf;
            } else {
                return Typeface.DEFAULT;
            }
        } catch (Exception e) {
            return Typeface.DEFAULT;
        }
    }

    public static Typeface getTypefaceRobotoLight(Context Context) {
        Typeface tf;
        try {
            tf = Typeface.createFromAsset(Context.getAssets(),
                    "fonts/Roboto-Light.ttf");
            if (tf != null) {
                return tf;
            } else {
                return Typeface.DEFAULT;
            }
        } catch (Exception e) {
            return Typeface.DEFAULT;
        }
    }

//    public static void sugestionApp(Context context) {
//        ArrayList<Apps> appses = new ArrayList<>();
//        Resources resources = context.getResources();
//        appses.add(new Apps(resources.getString(R.string.sugestion_name1), "com.gpaddy.free.antivirus",
//                resources.getDrawable(R.drawable.antivirus_mobile_security),
//                resources.getString(R.string.sugestion_des1), resources.getString(R.string.sugestion_link1)));
//        appses.add(new Apps(resources.getString(R.string.sugestion_name2), "com.gpaddy.eyehealthcare",
//                resources.getDrawable(R.drawable.eyes_protector),
//                resources.getString(R.string.sugestion_des2), resources.getString(R.string.sugestion_link2)));
//        appses.add(new Apps(resources.getString(R.string.sugestion_name3), "com.gpaddy.callblocker",
//                resources.getDrawable(R.drawable.call_blocker),
//                resources.getString(R.string.sugestion_des3), resources.getString(R.string.sugestion_link3)));
//        appses.add(new Apps(resources.getString(R.string.sugestion_name4), "com.gpaddy.screen.recorder.screenshoot",
//                resources.getDrawable(R.drawable.capture),
//                resources.getString(R.string.sugestion_des4), resources.getString(R.string.sugestion_link4)));
//        GPApplication.SUGESTION_APP = appses;
//    }

    public static boolean decodeNotNull() {
        return nhamang1 != null && tuchoiqc1 != null && km501 != null && km1001 != null
                && MultiSimSmsManager1 != null && Ext1 != null && SubscriptionManager1 != null
                && AsusMSimSmsManager1 != null && AsusTelephonyManager1 != null
                && MSimTelephonyManager1 != null && HtcTelephonyManager1 != null && simslotcount1 != null;
    }

    public static Cursor getCursorContacts(Context context, String text) {
        StringBuilder stringBuilder = new StringBuilder();
        String selection = ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " like ? or " + ContactsContract.CommonDataKinds.Phone.NUMBER + " like ?";
        String[] args = new String[]{"%" + text + "%", "%" + text + "%"};
        Cursor cursor = context.getContentResolver().query(
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, selection,
                args, "display_name asc");
        int nameFieldColumnIndex = cursor
                .getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
        int numberFieldColumnIndex = cursor
                .getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
        int id = cursor
                .getColumnIndex(ContactsContract.CommonDataKinds.Phone._ID);
        return cursor;
    }

    public static void decode(Context context) {
//        nhamang1 = decode1(context, nhamang);
//        tuchoiqc1 = decode1(context, tuchoiqc);
//        km501 = decode1(context, km50);
//        km1001 = decode1(context, km100);
//        MultiSimSmsManager1 = decode1(context, MultiSimSmsManager);
//        Ext1 = decode1(context, Ext);
//        SubscriptionManager1 = decode1(context, SubscriptionManager);
//        AsusTelephonyManager1 = decode1(context, AsusTelephonyManager);
//        AsusMSimSmsManager1 = decode1(context, AsusMSimSmsManager);
//        MSimTelephonyManager1 = decode1(context, MSimTelephonyManager);
//        HtcTelephonyManager1 = decode1(context, HtcTelephonyManager);
//        simslotcount1 = decode1(context, simslotcount);


    }

    // Context context;


    // public Check(Context context) {
    // this.context = context;
    // }
    public static List<ActivityManager.RecentTaskInfo> getRecentApp(Context context) {
        String topPackageName;
        List<ActivityManager.RecentTaskInfo> list;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            UsageStatsManager mUsageStatsManager = (UsageStatsManager) context.getSystemService("usagestats");
//            long time = System.currentTimeMillis();
//            // We get usage stats for the last 10 seconds
//            List<UsageStats> stats = mUsageStatsManager.queryUsageStats(UsageStatsManager.INTERVAL_DAILY, time - 1000 * 10, time);
//            // Sort the stats by the last time used
//            if (stats != null) {
//                SortedMap<Long, UsageStats> mySortedMap = new TreeMap<Long, UsageStats>();
//                for (UsageStats usageStats : stats) {
//                    mySortedMap.put(usageStats.getLastTimeUsed(), usageStats);
//                }
//                if (mySortedMap != null && !mySortedMap.isEmpty()) {
//                    topPackageName = mySortedMap.get(mySortedMap.lastKey()).getPackageName();
//                }
//            }
        } else {
            ActivityManager recentTaskInfo = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            return recentTaskInfo.getRecentTasks(6, 1);

        }
        return null;
    }

    public static void vibrate(Context context) {
        Vibrator v = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        // Vibrate for 500 milliseconds
        v.vibrate(300);
    }

    public static String getZipcode(Context context) {
        String idCountry = "";
        String zipCode = "";
        TelephonyManager manager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        idCountry = manager.getSimCountryIso().toUpperCase();
        return idCountry;

    }

    public static boolean checkTime(int begin_time, int end_time) {
        Calendar calendar = Calendar.getInstance();
        int timeCurrent = calendar.get(Calendar.HOUR_OF_DAY) * 60
                + calendar.get(Calendar.MINUTE);
        Log.d(TAG, "timeCurrent: " + timeCurrent);
        if (begin_time <= end_time) {
            if (begin_time <= timeCurrent && timeCurrent <= end_time) {
                Log.d(TAG, "timeCurrent: " + timeCurrent);
                return true;
            } else
                return false;
        } else {
            if (begin_time <= timeCurrent && timeCurrent < 1440
                    || 0 < timeCurrent && timeCurrent <= end_time) {

                return true;
            } else
                return false;
        }

    }

    public static String longToStringTime(long time) {
        Date date = new Date(time);
        DateFormat formatter = new SimpleDateFormat("dd/MM/yy");
        String dateFormatted = formatter.format(date);
        return dateFormatted;
    }

    public static String longToStringTimeSms(long time) {
        Date date = new Date(time);
        DateFormat formatter;
        long now = System.currentTimeMillis();
        if (now < time || time <= 0) {
            return null;
        }
        long diff = now - time;
        if (diff < 365 * DAY_MILLIS) {
            formatter = new SimpleDateFormat("HH:mm dd/MM");
        } else {
            formatter = new SimpleDateFormat("HH:mm dd/MM/yy");
        }
        String dateFormatted = formatter.format(date);
        return dateFormatted;
    }

    // public static String getNameFromContact(Context context,String number){
    // String name=number;
    // if(PaddyApplication.myContactses==null){
    // ManagerContacts.getAllContacts(context);
    // }
    // if(PaddyApplication.myContactses!=null){
    // for (int i=0;i<PaddyApplication.myContactses.size();i++){
    // if(PaddyApplication.myContactses.get(i).cNumber.contains(number.substring(number.length()-7,number.length()))){
    // name=PaddyApplication.myContactses.get(i).cName;
    // break;
    // }
    // }
    // }
    // return name;
    // }

    private static final int SECOND_MILLIS = 1000;
    private static final int MINUTE_MILLIS = 60 * SECOND_MILLIS;
    private static final int HOUR_MILLIS = 60 * MINUTE_MILLIS;
    private static final int DAY_MILLIS = 24 * HOUR_MILLIS;
    private static final int WEEK_MILLIS = 7 * DAY_MILLIS;
    private static final int MONTH_MILLIS = 4 * WEEK_MILLIS;

    /*public static String getTimeAgo(long time, Context ctx) {
        if (time < 1000000000000L) {
            // if timestamp given in seconds, convert to millis
            time *= 1000;
        }
        long now = System.currentTimeMillis();
        if (time > now || time <= 0) {
            return null;
        }
        // TODO: localize
        final long diff = now - time;
        if (diff < MINUTE_MILLIS) {
            return ctx.getString(R.string.just_now);
        } else if (diff < 2 * MINUTE_MILLIS) {
            return ctx.getString(R.string.a_minute_ago);
        } else if (diff < 50 * MINUTE_MILLIS) {
            return diff / MINUTE_MILLIS + " "
                    + ctx.getString(R.string.minute_ago);
        } else if (diff < 90 * MINUTE_MILLIS) {
            return ctx.getString(R.string.an_hour_ago);
        } else if (diff < 24 * HOUR_MILLIS) {
            return diff / HOUR_MILLIS + " " + ctx.getString(R.string.hours_ago);
        } else if (diff < 48 * HOUR_MILLIS) {
            return ctx.getString(R.string.yesterday);
        } else {
            return longToStringTime(time);
        }
    }*/

  /*  public static void notification(Context context, String address, String content, String soundPath, boolean sound, boolean vibrate, boolean spam) {
        String name = ContactsManager.getDisplayName(context, address);

        NotificationCompat.Builder mBuilder;
        NotificationManager myNotificationManager = null;
        myNotificationManager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);
        mBuilder = new NotificationCompat.Builder(context);
        mBuilder.setContentTitle(name);
        mBuilder.setContentText(content);
        mBuilder.setStyle(new NotificationCompat.BigTextStyle()
                .bigText(content));

        Uri uri = Uri.parse(soundPath);
        if (spam && !sound) {

        } else mBuilder.setSound(uri);
        if (spam) {
            vibrate = false;
            mBuilder.setSmallIcon(R.drawable.ic_spam_sms);
            Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_spam_sms);
            mBuilder.setLargeIcon(bitmap);


        } else {
            mBuilder.setSmallIcon(R.drawable.ic_launcher);
            Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_launcher);
            mBuilder.setLargeIcon(bitmap);
        }

        if (vibrate)
            mBuilder.setVibrate(new long[]{0, 1000, 1000});
        else mBuilder.setVibrate(new long[]{0});


        mBuilder.setLights(Color.RED, 3000, 3000);
        mBuilder.setAutoCancel(true);


        Intent intent;
        if (spam) {
            intent = new Intent(context, Main2.class);
            intent.putExtra(ComonValues.INDEX, 1);
        } else {
            int thread_id = SmsManager.getThreadIdFromAddress(context, address);
            intent = new Intent(context, ReadSmsActivity.class);
            intent.putExtra(ComonValues.THREAD_ID, thread_id);
        }
        intent.putExtra(ComonValues.NUMBER, address);
        intent.putExtra(ComonValues.NAME, name);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(pendingIntent);

        myNotificationManager.notify(1, mBuilder.build());
    }*/

//    public static void notification(
//                                    Context context, String tittle, String content) {
//        NotificationCompat.Builder mBuilder;
//        NotificationManager myNotificationManager = null;
//        myNotificationManager = (NotificationManager) context
//                .getSystemService(Context.NOTIFICATION_SERVICE);
//        mBuilder = new NotificationCompat.Builder(context);
//        Intent intent = new Intent(context, PaddyInitActivity.class);
//        if (blocked) {
//            // RemoteViews remoteViews = new
//            // RemoteViews(context.getPackageName(),
//            // R.layout.view_notification);
//            // mBuilder.setContent(remoteViews);
//
//            // remoteViews.setTextViewText(R.id.tvTitle, tittle);
//            // remoteViews.setTextViewText(R.id.tvContent, content);
//            //
//            remoteViews.setImageViewResource(R.id.imageIcon, R.drawable.ic_call_blue);
//
//            Intent intent1 = new Intent("close_notification");
//            PendingIntent resultPendingIntent = PendingIntent.getBroadcast(
//                    context, 0, intent1, PendingIntent.FLAG_UPDATE_CURRENT);
//
//            mBuilder.setContentTitle(tittle);
//            mBuilder.setContentText(content);
//            mBuilder.setStyle(new NotificationCompat.BigTextStyle()
//                    .bigText(content));
//            mBuilder.addAction(android.R.drawable.ic_menu_close_clear_cancel,
//                    context.getString(R.string.dismis), resultPendingIntent);
//
//            // remoteViews.setOnClickPendingIntent(R.id.btnOk,
//            // resultPendingIntent);
//            if (tittle.equals(context.getString(R.string.blocked_call))) {
//                intent.putExtra(ComonValues.ACTION, "call");
//            } else {
//                intent.putExtra(ComonValues.ACTION, "sms");
//            }
//        } else {
//            mBuilder.setContentTitle(tittle);
//            mBuilder.setContentText(content);
//        }
//        mBuilder.setAutoCancel(true);
//        mBuilder.setOngoing(true);
//        mBuilder.setSmallIcon(R.drawable.ic_logo24);
//
//        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(),
//                R.drawable.icon144);
//        mBuilder.setLargeIcon(bitmap);
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
//                | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0,
//                intent, PendingIntent.FLAG_UPDATE_CURRENT);
//        mBuilder.setContentIntent(pendingIntent);
//        myNotificationManager.notify(1, mBuilder.build());
//        if (!show)
//            myNotificationManager.cancel(1);
//    }

    public static int randomImage(int[] listImage, int position) {
//		 Random random = new Random();
//		 int index = listImage[random.nextInt(5)];
//		return index;


        return listImage[position % listImage.length];
    }

    public static String getEmail(Context context) {
        AccountManager accountManager = AccountManager.get(context);
        Account account = getAccount(accountManager);

        if (account == null) {
            return null;
        } else {
            return account.name;
        }
    }

    private static Account getAccount(AccountManager accountManager) {
        Account[] accounts = accountManager.getAccountsByType("com.google");
        Account account;
        if (accounts.length > 0) {
            account = accounts[0];
        } else {
            account = null;
        }
        return account;
    }

   /* public static void setTypefaceLight(TextView... textViews) {
        for (TextView tv : textViews) {
            tv.setTypeface(PaddyApplication.MY_TYPEFACE_LIGHT);
        }
    }

    public static void setTypefaceBold(TextView... textViews) {
        for (TextView tv : textViews) {
            tv.setTypeface(PaddyApplication.MY_TYPEFACE_BOLD);
        }
    }

    public static void setTypefaceRegular(TextView... textViews) {
        for (TextView tv : textViews) {
            tv.setTypeface(PaddyApplication.MY_TYPEFACE_REGULAR);
        }
    }

    public static void setTypefaceThin(TextView... textViews) {
        for (TextView tv : textViews) {
            tv.setTypeface(PaddyApplication.MY_TYPEFACE_THIN);
        }
    }

    public static boolean isDualSimOnline(Context context) {
        TelephonyInfo telephonyInfo = TelephonyInfo.getInstance(context);
        boolean isSIM1Ready = telephonyInfo.isSIM1Ready();
        boolean isSIM2Ready = telephonyInfo.isSIM2Ready();
        return isSIM1Ready && isSIM2Ready;
    }

    public static boolean isDualSim(Context context) {
        TelephonyInfo telephonyInfo = TelephonyInfo.getInstance(context);
        return telephonyInfo.isDualSIM();
    }*/

    public static void shareClick(Context context, String text) {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        String link = "http://play.google.com/store/apps/details?id="
                + text;
        sendIntent.putExtra(Intent.EXTRA_TEXT, link);
        sendIntent.setType("text/plain");
        context.startActivity(sendIntent);
    }

    /* public static void copyToClipBoard(Context context, String label, String text) {
         ClipboardManager clipboardManager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
         ClipData clipData = ClipData.newPlainText(label, text);
         clipboardManager.setPrimaryClip(clipData);
         Toast.makeText(context, context.getString(R.string.coppy_sucess), Toast.LENGTH_SHORT).show();
     }
 */
    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static boolean isDefaultSmsApp(Context context) {
        String packageDefault = Telephony.Sms.getDefaultSmsPackage(context);
        String myPackage = context.getPackageName();

        return myPackage.equals(packageDefault);

    }


    public static void getContacts(Activity activity, int action) {
        Intent pickContactIntent = new Intent(Intent.ACTION_PICK, Uri.parse("content://contacts"));
        pickContactIntent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE); // Show user only contacts w/ phone numbers
        activity.startActivityForResult(pickContactIntent, action);
    }

    public static void gotoMarket(Context context) {
        Uri uri = Uri.parse("market://details?id=" + context.getPackageName());
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
        try {
            context.startActivity(goToMarket);
        } catch (ActivityNotFoundException e) {
            context.startActivity(new Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("http://play.google.com/store/apps/details?id="
                            + context.getPackageName())));
        }
    }

    public static void gotoMarket(Context context, String url) {
        context.startActivity(new Intent(
                Intent.ACTION_VIEW,
                Uri.parse(url)));
    }

    public static void gotoPublisher(Context context) {
        Uri uri = Uri.parse("market://search?q=pub:Antivirus+Free+-+GPaddy+Mobile+Security");
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
        try {
            context.startActivity(goToMarket);
        } catch (ActivityNotFoundException e) {
            context.startActivity(new Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("http://play.google.com/store/search?q=pub:Antivirus+Free+-+GPaddy+Mobile+Security")));
        }
    }

   /* public static boolean isNumberPhone(String text) {
        text = ContactsManager.standardizedPhoneNumber(text);

        try {
            double d = Double.parseDouble(text);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;

//            char[] chars = text.toCharArray();
//        for (int i = 0; i < chars.length; i++) {
//            if (chars[i] <= 48 || chars[i] >= 57) {
//                return true;
//            }
//        }
//        return false;
    }*/

   /* public static boolean checkHas1Numberphone(String s) {
        String[] list = s.split(" ");
        int count = 0;
        for (int i = 0; i < list.length; i++) {
            if (list[i].length() > 9 && isNumberPhone(list[i])) {
                return true;
            }
        }
        return false;
    }

    public static boolean checkHas2Numberphone(String s) {
        String[] list = s.split(" ");
        int count = 0;
        for (int i = 0; i < list.length; i++) {
            if (list[i].length() > 9 && isNumberPhone(list[i])) {
                count++;
            }
        }
        if (count >= 2) {
            return true;
        } else return false;
    }*/

    public static boolean checkHasLink(String s) {
        if (s.contains("http://"))
            return true;
        else return false;
    }

    public static boolean isBrandName(String number) {
        char[] chars = number.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            if (((chars[i] >= 65 && chars[i] <= 90) || (chars[i] >= 97 && chars[i] <= 122))) {
                return true;
            }
        }
        return false;
    }

    public static boolean isHasQCHead(String content) {
        String firtContent = content;
        if (content.length() >= 10) {
            firtContent = content.substring(0, 10);
        }

        int posQ = firtContent.indexOf("Q");
        int posC = firtContent.indexOf("C");
        if (posC <= 0 || posQ <= 0) {
            return false;
        }
        if (posC - (posQ + 2) == 0) {
//                        int posCharSpecial = firtContent.indexOf(posQ + 1);
            char charAtspecial = firtContent.charAt(posQ + 1);
            if (!((charAtspecial >= 65 && charAtspecial <= 90) || (charAtspecial >= 97 && charAtspecial <= 122))) {
                return false;
            } else {
                return true;
            }
        } else if (posC == (posQ + 1)) {
            return true;
        }
        return false;
    }

    public static String getDateInWeek(Context context, int i) {

        String[] dayInWeek = context.getResources().getStringArray(R.array.dayinweek);
        return dayInWeek[i - 1];
    }

    public static String getMonthString(Context context, int i) {
        String month[] = context.getResources().getStringArray(R.array.month);
        return month[i - 1];
    }


    public static String getMonth(Context context, int i) {
        String[] dayInWeek = context.getResources().getStringArray(R.array.dayinweek);
        switch (i) {
            case 2:

                return dayInWeek[0];
            case 3:
                return dayInWeek[1];
            case 4:
                return dayInWeek[2];
            case 5:
                return dayInWeek[3];
            case 6:
                return dayInWeek[4];
            case 7:
                return dayInWeek[5];
            case 8:
                return dayInWeek[6];
            default:
                return dayInWeek[0];
        }
    }

    public static boolean wifiConected(Context context) {
        ConnectivityManager connManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mWifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

        if (mWifi.isConnected()) {
            return true;
        } else return false;
    }
    public static final int PICK_IMAGE = 12457;

    public static void getBackgroundEx(Activity activity) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        activity.startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
    }


    public static void sound(final Activity context, final int idSound) {
//        MediaPlayer mPlayer = MediaPlayer.create(context, idSound); // in 2nd param u have to pass your desire ringtone
//        mPlayer.start();


//        SoundPool.Builder builder = new SoundPool.Builder();
        AudioManager mAudioManager = (AudioManager) context
                .getSystemService(context.AUDIO_SERVICE);

        final float actVolume, maxVolume, volume;
//        actVolume = (float) mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
//        maxVolume = (float) mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
//        volume = actVolume / maxVolume;
        context.setVolumeControlStream(AudioManager.STREAM_MUSIC);
        SoundPool soundPool = new SoundPool(20, AudioManager.STREAM_MUSIC, 0);
        final int soundID = soundPool.load(context, idSound, 1);

//        int streamVolume = mAudioManager
//                .getStreamVolume(AudioManager.STREAM_MUSIC);
        soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
//                Toast.makeText(context, "soundpool", Toast.LENGTH_LONG).show();
                if (idSound == R.raw.type_keyboard)
                    soundPool.play(soundID, 0.1f, 0.1f,
                            1, 0, 1f);
                else soundPool.play(soundID, 0.5f, 0.5f,
                        1, 0, 1f);
            }
        });


    }
}
