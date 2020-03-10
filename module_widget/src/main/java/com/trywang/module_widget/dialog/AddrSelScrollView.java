package com.trywang.module_widget.dialog;

import android.content.Context;
import android.util.AttributeSet;

import com.rae.widget.ScrollerNumberPicker;

import java.lang.reflect.Field;

/**
 * TODO 写清楚此类的作用
 *
 * @author Try
 * @date 2019/6/19 10:37
 */
public class AddrSelScrollView extends ScrollerNumberPicker {
    public AddrSelScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public AddrSelScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AddrSelScrollView(Context context) {
        super(context);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int mw = getMeasuredWidth();
        if (mw != 0) {
            try {
                Field f = AddrSelScrollView.class.getSuperclass().getDeclaredField("controlWidth");
                f.setAccessible(true);
                f.set(this, getMeasuredWidth());
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

    }
}
