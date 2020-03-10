package com.trywang.module_widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

/**
 * TODO 写清楚此类的作用
 *
 * @author Try
 * @date 2018/12/7 16:27
 */
public class XViewPager extends ViewPager {
    private boolean isAllowDrag = true;

    public boolean isAllowDrag() {
        return isAllowDrag;
    }

    public void setAllowDrag(boolean allowDrag) {
        isAllowDrag = allowDrag;
    }

    public XViewPager(@NonNull Context context) {
        super(context);
    }

    public XViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return isAllowDrag && super.onInterceptTouchEvent(ev) ;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return isAllowDrag && super.onTouchEvent(ev);
    }
}
