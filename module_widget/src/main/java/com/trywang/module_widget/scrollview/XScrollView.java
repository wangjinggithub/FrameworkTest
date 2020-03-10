package com.trywang.module_widget.scrollview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ScrollView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * TODO 写清楚此类的作用
 *
 * @author Try
 * @date 2019/6/5 15:57
 */
public class XScrollView extends ScrollView {
    IOnScrollChangeListener mIOnScrollChangeListener;

    public IOnScrollChangeListener getIOnScrollChangeListener() {
        return mIOnScrollChangeListener;
    }

    public void setIOnScrollChangeListener(IOnScrollChangeListener onScrollChangeListener) {
        this.mIOnScrollChangeListener = onScrollChangeListener;
    }

    public XScrollView(@NonNull Context context) {
        super(context);
    }

    public XScrollView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public XScrollView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (mIOnScrollChangeListener != null) {
            mIOnScrollChangeListener.onScrollChange(this,l,t,oldl,oldl);
        }
    }

    public interface IOnScrollChangeListener {
        /**
         * Called when the scroll position of a view changes.
         *
         * @param v The view whose scroll position has changed.
         * @param scrollX Current horizontal scroll origin.
         * @param scrollY Current vertical scroll origin.
         * @param oldScrollX Previous horizontal scroll origin.
         * @param oldScrollY Previous vertical scroll origin.
         */
        void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY);
    }
}
