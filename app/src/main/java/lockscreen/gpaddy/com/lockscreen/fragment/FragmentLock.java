package lockscreen.gpaddy.com.lockscreen.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import lockscreen.gpaddy.com.lockscreen.R;

/**
 * Created by $ on 9/10/2015.
 */
public class FragmentLock extends Fragment {

    public static FragmentLock newInstance() {
        FragmentLock f = new FragmentLock();
        return f;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_lock, container, false);
        


        return v;
    }
}
