package lockscreen.gpaddy.com.lockscreen.view;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;

import lockscreen.gpaddy.com.lockscreen.fragment.FragmentLock;
import lockscreen.gpaddy.com.lockscreen.fragment.FragmentLockPassCode;

/**
 * Created by Admin on 14/09/2015.
 */
public class MyViewPager extends ViewPager {
    PagerAdapter mPagerAdapter;

    public MyViewPager(Context context) {
        super(context);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (mPagerAdapter != null) {
            super.setAdapter(mPagerAdapter);

        }
    }

    @Override
    public void setAdapter(PagerAdapter adapter) {
    }

    public void storeAdapter(PagerAdapter pagerAdapter) {
        mPagerAdapter = pagerAdapter;
    }


}
