package lockscreen.gpaddy.com.lockscreen.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.TextView;

import lockscreen.gpaddy.com.lockscreen.util.Utils;

/**
 * Created by Admin on 23/08/2015.
 */
public class TextViewRobotoLight extends TextView {
    public TextViewRobotoLight(Context context) {
        super(context);
        setTypeface(Utils.getTypefaceRobotoLight(context));

    }

    public TextViewRobotoLight(Context context, AttributeSet attrs) {
        super(context, attrs);
        setTypeface(Utils.getTypefaceRobotoLight(context));
       

    }

    public TextViewRobotoLight(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setTypeface(Utils.getTypefaceRobotoLight(context));


    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public TextViewRobotoLight(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        setTypeface(Utils.getTypefaceRobotoLight(context));


    }


}
