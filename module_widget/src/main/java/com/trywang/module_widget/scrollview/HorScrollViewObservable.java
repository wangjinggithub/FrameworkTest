package com.trywang.module_widget.scrollview;

import android.graphics.Point;

import java.util.ArrayList;
import java.util.List;

/**
 * TODO 写清楚此类的作用
 *
 * @author Try
 * @date 2019/5/10 11:51
 */
public class HorScrollViewObservable {
    private static HorScrollViewObservable sInstance ;
    private Point mPoint ;

    private HorScrollViewObservable(){
        mPoint = new Point();
    }

    public Point getPoint() {
        return mPoint;
    }

    public void setPoint(int x, int y){
        mPoint.x = x;
        mPoint.y = y;
    }

    public static HorScrollViewObservable getInstance(){
        if (sInstance == null) {
            synchronized (HorScrollViewObservable.class) {
                if (sInstance == null) {
                    sInstance = new HorScrollViewObservable();
                }
            }
        }
        return sInstance;
    }

    List<IScrollChangeListener> subscribe = new ArrayList<>();

    public void addSubscribe(IScrollChangeListener scrollChangeListener){
        if (!subscribe.contains(scrollChangeListener)) {
            subscribe.add(scrollChangeListener);
        }
    }

    public void removeSubscribe(IScrollChangeListener scrollChangeListener){
        if (subscribe.contains(scrollChangeListener)) {
            subscribe.remove(scrollChangeListener);
        }
    }

    public void dispose(int l ,int t){
        setPoint(l,t);
        for (int i = 0; i < subscribe.size(); i++) {
            IScrollChangeListener listener = subscribe.get(i);
            if (listener != null) {
                listener.onScrollChangeListener2(l,t);
            }
        }
    }

    public interface IScrollChangeListener{
        void onScrollChangeListener2(int l, int t);
    }
}
