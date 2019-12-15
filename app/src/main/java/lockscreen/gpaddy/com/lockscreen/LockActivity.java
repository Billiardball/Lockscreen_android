package lockscreen.gpaddy.com.lockscreen;

import android.animation.Animator;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.BounceInterpolator;
import android.view.animation.Interpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Scroller;

import com.romainpiel.shimmer.Shimmer;
import com.romainpiel.shimmer.ShimmerTextView;

import lockscreen.gpaddy.com.lockscreen.fragment.FragmentLock;


public class LockActivity extends Activity implements View.OnTouchListener {
    public static int MSG_LOCK_SUCESS = 0x123;
    public static int UPDATE_TIME = 0x234;
    public Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == MSG_LOCK_SUCESS) {
                lockLayer.unlock();
                finish();
            }
        }
    };
    private ShimmerTextView shimmerTextView;
    private Shimmer shimmer;
    private LockLayer lockLayer;
    private ViewPager viewPager;
    private FragmentLock fragmentLock;
    private FragmentLock fragmentLock2;
    private PagerAdapter adapter;

    private Scroller scroller;
    private ImageView btnCamera;
    private RelativeLayout screen;
    private PullDoorView pullDoorView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        pullDoorView = new PullDoorView(this);
        finish();
//
//
        View v = pullDoorView;
//        setContentView(R.layout.activity_lock);
//        viewPager = (ViewPager) findViewById(R.id.pager);
//        fragmentLock = FragmentLock.newInstance();
//        fragmentLock2 = FragmentLock.newInstance();
//        adapter = new PagerAdapter(getSupportFragmentManager());
//        viewPager.setAdapter(adapter);
        shimmerTextView = (ShimmerTextView) v.findViewById(R.id.shimmer_tv);
//        lockLayer = new LockLayer(this);
//        lockLayer.setLockView(v);
//        lockLayer.lock();
//        PullDoorView.setMainHandler(handler);
//        startShimer();


    }

//    class PagerAdapter extends FragmentPagerAdapter {
//
//        public PagerAdapter(FragmentManager fm) {
//            super(fm);
//        }
//
//        @Override
//        public Fragment getItem(int position) {
//            switch (position) {
//                case 0:
//                    return fragmentLock;
//                case 1:
//                    return fragmentLock2;
//            }
//            return null;
//        }
//
//        @Override
//        public int getCount() {
//            return 2;
//        }
//    }

    private void setView() {
        Interpolator polator = new BounceInterpolator();
        scroller = new Scroller(this, polator);
    }

    public void startBounceAnim(int startY, int dy, int duration) {
        scroller.startScroll(0, startY, 0, dy, duration);
        screen.invalidate();
    }

    private void startShimer() {
        shimmer = new Shimmer();
        shimmer.setRepeatCount(200)
                .setDuration(800)
                .setStartDelay(300)
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
        shimmer.start(shimmerTextView);

    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
//        int action = event.getAction();
//        switch (action) {
//            case MotionEvent.ACTION_DOWN:
//                mLastDownY = (int) event.getY();
//              break;
//            case MotionEvent.ACTION_MOVE:
//                mCurryY = (int) event.getY();
//                System.err.println("ACTION_MOVE=" + mCurryY);
//                mDelY = mCurryY - mLastDownY;
//                // ֻ׼�ϻ���Ч
//                if (mDelY < 0) {
////                    scrollTo(0, -mDelY);
//                screen.scrollTo(mDelY, -mDelY);
//
////                imgBackground.setY(-mDelY);
////                screen.invalidate();
//                }
//                System.err.println("-------------  " + mDelY);
//
//                break;
//            case MotionEvent.ACTION_UP:
//                screen.scrollTo(0, 0);
////                mCurryY = (int) event.getY();
////                mDelY = mCurryY - mLastDownY;
////                if (mDelY < 0) {
////                    if (Math.abs(mDelY) > mScreenHeigh / 2.5) {
////                        // ���ϻ������������Ļ�ߵ�ʱ�� ����������ʧ����
////                        startBounceAnim(screen.getScrollY(), mScreenHeigh, 300);
//////                        mCloseFlag = true;
////                    } else {
////                        // ���ϻ���δ���������Ļ�ߵ�ʱ�� �������µ�������
////                        screen.scrollTo(0,0);
////                        startBounceAnim(screen.getScrollY(), -screen.getScrollY(), 1000);
////
////                    }
////                }
//
//                break;
//        }
        return true;
    }
}
