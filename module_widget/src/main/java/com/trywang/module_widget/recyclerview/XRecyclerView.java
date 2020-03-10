package com.trywang.module_widget.recyclerview;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

/**
 * TODO 写清楚此类的作用
 *
 * @author Try
 * @date 2019-09-02 14:27
 */
public class XRecyclerView extends RecyclerView {
    public XRecyclerView(@NonNull Context context) {
        super(context);
    }

    public XRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public XRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        boolean handled = super.dispatchTouchEvent(ev);
        Log.i("view","dispatchTouchEvent handled = " + handled);
        return handled;
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        boolean handled = super.onTouchEvent(e);
        Log.i("view","onTouchEvent handled = " + handled + "; e.getAction() = " + e.getAction()
        + ";isLayoutFrozen = " + isLayoutFrozen());
        return handled;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent e) {
        boolean handled = super.onInterceptTouchEvent(e);
        Log.i("view","onInterceptTouchEvent handled = " + handled);
        return handled;
    }
}
