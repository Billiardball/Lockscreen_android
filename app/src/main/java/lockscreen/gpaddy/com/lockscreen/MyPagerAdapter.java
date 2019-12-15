package lockscreen.gpaddy.com.lockscreen;

import android.app.Activity;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import lockscreen.gpaddy.com.lockscreen.activity.LockHasPasscode;
import lockscreen.gpaddy.com.lockscreen.view.TextViewRobotoLight;

/**
 * Created by Admin on 14/09/2015.
 */
public class MyPagerAdapter extends PagerAdapter implements View.OnClickListener {
    Activity context;
    LockHasPasscode lock2;
    View v, v1;
    private TextViewRobotoLight btnCancel;

    public MyPagerAdapter(Activity context) {
        this.context = context;
        lock2 = (LockHasPasscode) context;
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
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        v = layoutInflater.inflate(R.layout.fragment_lock_passcode, null);
        v1 = layoutInflater.inflate(R.layout.fragment_lock, null);
        btnCancel = (TextViewRobotoLight) v.findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(this);


        ((ViewPager) collection).addView(v1, 0);
        ((ViewPager) collection).addView(v, 1);
        switch (position) {
            case 0:
                return v;
            case 1:
                return v1;
        }
        return v1;
    }

    public int getItemPosition(Object obj) {
        return obj == v ? 0 : obj == v1 ? 1 : -1;
    }

    @Override
    public void destroyItem(ViewGroup collection, int position, Object view) {
        ((ViewPager) collection).removeView((View) view);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnCancel:
                lock2.unlock();
                break;
        }
    }
}
