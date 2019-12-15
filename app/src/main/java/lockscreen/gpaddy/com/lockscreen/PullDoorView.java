package lockscreen.gpaddy.com.lockscreen;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.BounceInterpolator;
import android.view.animation.Interpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Scroller;

public class PullDoorView extends RelativeLayout {

    private Context mContext;

    private Scroller mScroller;

    private int mScreenWidth = 0;

    private int mScreenHeigh = 0;

    private int mLastDownY = 0;

    private int mCurryY;

    private int mDelY;
    private SoundPool soundPool;
    private int musicId;
    private boolean mCloseFlag = false;
    private static Handler mainHandler = null;
    private ImageView mImgView;
    private ImageView mImgCamera;

    private RelativeLayout statusbar;
    private LayoutInflater inflater;

    private RelativeLayout lockScreen;

    public PullDoorView(Context context) {
        super(context);
        mContext = context;
        setupView();
    }

    public PullDoorView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        setupView();
    }

    private void setupView() {

        inflater = (LayoutInflater) LayoutInflater.from(mContext);

        statusbar = (RelativeLayout) inflater.inflate(R.layout.statusbar, null);


        lockScreen = (RelativeLayout) inflater.inflate(R.layout.fragment_lock, null);


        Interpolator polator = new BounceInterpolator();
        mScroller = new Scroller(mContext, polator);


        WindowManager wm = (WindowManager) (mContext.getSystemService(Context.WINDOW_SERVICE));
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        mScreenHeigh = dm.heightPixels;
        mScreenWidth = dm.widthPixels;


        // this.setBackgroundColor(Color.argb(0, 0, 0, 0));
        this.setBackgroundColor(getResources().getColor(android.R.color.transparent));
        mImgView = new ImageView(mContext);
        mImgView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        mImgView.setScaleType(ImageView.ScaleType.FIT_XY);
        mImgView.setImageResource(R.drawable.b3);
        addView(mImgView);

        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.addRule(ALIGN_PARENT_BOTTOM, 1);
        params.addRule(ALIGN_PARENT_RIGHT, 1);
        mImgCamera = new ImageView(mContext);
        addView(mImgCamera, params);
//        mImgCamera.setOnTouchListener(this);
        mImgCamera.setImageResource(R.drawable.camera_btn);
        addView(lockScreen);
        addView(statusbar);

//        soundPool = new SoundPool(10, AudioManager.STREAM_SYSTEM, 5);
//        musicId = soundPool.load(mContext, R.raw.music, 1);
    }


    public void setBgImage(int id) {
        mImgView.setImageResource(id);
    }


    public void setBgImage(Drawable drawable) {
        mImgView.setImageDrawable(drawable);
    }

    public void startBounceAnim(int startY, int dy, int duration) {
        mScroller.startScroll(0, startY, 0, dy, duration);
        invalidate();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
//                if (event.getX() >= mScreenWidth - 100 && event.getY() >= mScreenHeigh - 100) {
                    mLastDownY = (int) event.getY();
                    System.err.println("ACTION_DOWN=" + mLastDownY);
                    return true;
//                } else return false;
            case MotionEvent.ACTION_MOVE:
                mCurryY = (int) event.getY();
                System.err.println("ACTION_MOVE=" + mCurryY);
                mDelY = mCurryY - mLastDownY;
                lockScreen.setY(mDelY);
                postInvalidate();


//                if (mDelY < 0) {
//                    scrollTo(0, -mDelY);
                    postInvalidate();
//                }
                System.err.println("-------------  " + mDelY);

                break;
            case MotionEvent.ACTION_UP:
                mCurryY = (int) event.getY();
                mDelY = mCurryY - mLastDownY;
                if (mDelY < 0) {
                    if (Math.abs(mDelY) > mScreenHeigh / 2.5) {

                        startBounceAnim(this.getScrollY(), mScreenHeigh, 300);
                        mCloseFlag = true;
                    } else {

                        startBounceAnim(this.getScrollY(), -this.getScrollY(), 1000);

                    }
                }

                break;
        }
        return super.onTouchEvent(event);
    }

    @Override
    public void computeScroll() {

        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            Log.i("scroller", "getCurrX()= " + mScroller.getCurrX() + "     getCurrY()=" + mScroller.getCurrY() + "  getFinalY() =  " + mScroller.getFinalY());

            postInvalidate();
        } else {
            if (mCloseFlag) {
//                soundPool.play(musicId, 1, 1, 0, 0, 1);
                mainHandler.obtainMessage(LockActivity.MSG_LOCK_SUCESS).sendToTarget();
                this.setVisibility(View.GONE);
            }
        }
    }

    public static void setMainHandler(Handler handler) {
        mainHandler = handler;
    }

//    @Override
//    public boolean onTouch(View v, MotionEvent event) {
//        if (v == mImgCamera) {
//            int action = event.getAction();
//            switch (action) {
//                case MotionEvent.ACTION_DOWN:
//                    mLastDownY = (int) event.getY();
//                    System.err.println("ACTION_DOWN=" + mLastDownY);
//                    return true;
//                case MotionEvent.ACTION_MOVE:
//                    mCurryY = (int) event.getY();
//                    System.err.println("ACTION_MOVE=" + mCurryY);
//                    mDelY = mCurryY - mLastDownY;
//
//                    if (mDelY < 0) {
//                        scrollTo(0, -mDelY);
//                    }
//                    System.err.println("-------------  " + mDelY);
//
//                    break;
//                case MotionEvent.ACTION_UP:
//                    mCurryY = (int) event.getY();
//                    mDelY = mCurryY - mLastDownY;
//                    if (mDelY < 0) {
//                        if (Math.abs(mDelY) > mScreenHeigh / 2.5) {
//
//                            startBounceAnim(this.getScrollY(), mScreenHeigh, 300);
//                            mCloseFlag = true;
//                        } else {
//
//                            startBounceAnim(this.getScrollY(), -this.getScrollY(), 1000);
//
//                        }
//                    }
//
//                    break;
//            }
//            return super.onTouchEvent(event);
//        }
//        return false;
//    }
}
