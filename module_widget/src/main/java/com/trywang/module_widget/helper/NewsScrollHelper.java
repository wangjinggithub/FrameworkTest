package com.trywang.module_widget.helper;

import android.animation.ObjectAnimator;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;

import java.util.List;

/**
 * 新闻滚动帮助类
 *
 * @author Try
 * @date 2018/5/30 16:03
 */
public class NewsScrollHelper {
    private static volatile NewsScrollHelper sInstance;
    private List mList;
    private int count;
    private View mViewTop;
    private View mViewBottom;
    private Thread mThread;
    private IDataChange mIDataChange;

    private NewsScrollHelper() {
    }

    public static NewsScrollHelper getInstance() {
        if (sInstance == null) {
            synchronized (NewsScrollHelper.class) {
                if (sInstance == null) {
                    sInstance = new NewsScrollHelper();
                }
            }
        }
        return sInstance;
    }

    public NewsScrollHelper setData(List list) {
        this.mList = list;
        return sInstance;
    }

    public NewsScrollHelper setViews(View top, View bottom) {
        this.mViewTop = top;
        this.mViewBottom = bottom;
        return sInstance;
    }

    public NewsScrollHelper setIDataChange(IDataChange dataChange) {
        this.mIDataChange = dataChange;
        return sInstance;
    }

    private Handler mHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    if (mList == null || mViewTop == null || mViewBottom == null) {
                        return;
                    }
                    int index = msg.arg1;
                    if (index > mList.size() - 1) {
                        index = 0;
                    }
                    int next = index + 1;
                    if (next > mList.size() - 1) {
                        next = 0;
                    }
                    try {
                        translateAll(mViewTop, mViewBottom, getItem(index), getItem(next));
                    } catch (Exception e) {
//                        Log.e("View","新闻滚动出错");
                        e.printStackTrace();
                    }
                    break;
                default:
                    break;
            }
        }
    };

    public void interrupt() {
        if (mThread != null && mThread.isAlive()) {
            mThread.interrupt();
        }
    }

    public void onDestroy(){
        interrupt();
        if (mList != null) {
            mList.clear();
            mList = null;
        }
        mHandler = null;
        mViewBottom = null;
        mViewTop = null;
        mIDataChange = null;
    }

    public synchronized void startScoll() {
        initThread();
        if (!mThread.isAlive() && mList != null && mList.size() > 0
                && mViewTop != null && mViewBottom != null ) {
            mThread.start();
        }
    }

    public synchronized NewsScrollHelper resetCount(){
        count = 0;
        return sInstance;
    }

    private void translateAll(View top, View bottom, Object topStr, Object bottomStr) {
        if (mIDataChange != null) {
            mIDataChange.onDataChange(topStr, bottomStr);
        }
        translate(bottom);
        translate(top);
    }

    private void translate(View target) {
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(target, "TranslationY", 0, -target.getMeasuredHeight());
        objectAnimator.setDuration(1000);
        objectAnimator.start();
    }

    private synchronized int getCount() {
        return this.mList == null ? 0 : this.mList.size();
    }

    private Object getItem(int index){
        if (mList != null && mList.size() > index) {
            return mList.get(index);
        }
        return null;
    }

    private void initThread() {
        interrupt();
        mThread = new Thread() {
            @Override
            public void run() {
                try {
                    while (true) {
                        if (this.isInterrupted()) {
                            throw new InterruptedException("抛出异常停止");
                        }

                        sleep(5000);
                        Message msg = Message.obtain();
                        msg.arg1 = count % (getCount() == 0 ? count : getCount());
                        msg.what = 1;
                        mHandler.sendMessage(msg);
                        count++;
                    }
                } catch (InterruptedException e) {
                    Log.d("thread", "暂停抛出异常停止");
                } catch (Exception e) {
                    Log.d("thread", "抛出其他异常停止");
                }
            }
        };
    }

    public interface IAdapter {
        <T> void setData(T t);
    }

    public interface IDataChange {
        void onDataChange(Object t1, Object t2);
    }
}
