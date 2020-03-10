package com.trywang.module_widget.scrollview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.HorizontalScrollView;

/**
 * TODO 写清楚此类的作用
 *
 * @author Try
 * @date 2019/5/10 11:11
 */
public class XHorizontalScrollView extends HorizontalScrollView implements HorScrollViewObservable.IScrollChangeListener {

    public XHorizontalScrollView(Context context) {
        super(context);
    }

    public XHorizontalScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public XHorizontalScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void setOnScrollChangeListener(OnScrollChangeListener l) {
        super.setOnScrollChangeListener(l);
    }

    @Override
    public boolean isSmoothScrollingEnabled() {
        return super.isSmoothScrollingEnabled();
    }

    @Override
    public void scrollTo(int x, int y) {
        if (x == getScrollX() && y == getScrollY()) {
            return;
        }

        super.scrollTo(x, y);
    }

    @Override
    public void scrollBy(int x, int y) {
        super.scrollBy(x, y);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
//        Log.i("view","l = " + l + "; t = " + t + "; oldl = " + oldl + ";oldt = " + oldt);
//
//        Log.i("view", "onScrollChanged getScrollX() = "+ getScrollX() );
        HorScrollViewObservable.getInstance().dispose(l,t);
        super.onScrollChanged(l, t, oldl, oldt);
    }

    @Override
    public void onScrollChangeListener2(int l, int t) {
        scrollTo(l,t);
    }


}
