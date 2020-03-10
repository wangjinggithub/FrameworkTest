package com.trywang.module_widget;

import android.os.CountDownTimer;
import android.view.View;
import android.widget.TextView;

/**
 * TODO 写清楚此类的作用
 *
 * @author Try
 * @date 2018/11/1 11:48
 */
public class CountDownSMSTimer {
    public CountDownTimer mCountDownTimer;
    private View mViewContainer;
    private TextView mTvShow;

    private String mTxtCountDown = "%sS后重获";
    private String mTxtEnd = "重新获取";

    private long mTimeTotal;
    private long mTimeInterval;

    public void destory(){
        if (mCountDownTimer != null) {
            mCountDownTimer.cancel();
            mCountDownTimer = null;
        }
        mTvShow = null;
        mViewContainer = null;
    }

    public CountDownSMSTimer() {
    }

    public CountDownSMSTimer(long mTimeTotal, long mTimeInterval) {
        this.mTimeTotal = mTimeTotal;
        this.mTimeInterval = mTimeInterval;
    }

    public CountDownSMSTimer(long mTimeTotal, long mTimeInterval,String mTxtCountDown, String mTxtEnd) {
        this.mTxtCountDown = mTxtCountDown;
        this.mTxtEnd = mTxtEnd;
        this.mTimeTotal = mTimeTotal;
        this.mTimeInterval = mTimeInterval;
    }

    public void withCountDownView(View container, TextView showTv, View.OnClickListener listener){
        this.mViewContainer = container;
        this.mTvShow = showTv;
        mViewContainer.setOnClickListener((v)->{
            mViewContainer.setEnabled(false);
            start();
            if (listener != null) {
                listener.onClick(v);
            }
        });
    }

    private void start(){
        if (mCountDownTimer != null) {
            mCountDownTimer.cancel();
        }
        mCountDownTimer = new CountDownTimer(mTimeTotal + 100, mTimeInterval ) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTvShow.setText(String.format(mTxtCountDown, millisUntilFinished / 1000));
            }

            @Override
            public void onFinish() {
                mTvShow.setText(mTxtEnd);
                mViewContainer.setEnabled(true);
            }
        };
        mCountDownTimer.start();
    }





}
