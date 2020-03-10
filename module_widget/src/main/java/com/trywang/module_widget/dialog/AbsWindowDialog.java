package com.trywang.module_widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;

import com.trywang.module_widget.R;

/**
 * Dialog的父类
 *
 * @author Try
 * @date 2017/7/6 09:48
 */
public abstract class AbsWindowDialog<T> {
    public static final String TAG = "TAG";
    public static final int THEME_BASE = 1;//带灰色背景的主题
    public static final int THEME_WITH_LIGHT = 2; //不带灰色普通背景的主题
    private Dialog mDialog;
    private RelativeLayout mRootView;
    private IWindowDialogCallback mCallback;
    private IOnDismissListener mDismissCallbackExtra;//
    protected T mData;

    protected Context mContext;
    protected boolean isSystemAlert = false;//默认为系统弹框 6.0以后需要请求权限
    protected boolean mCanceledOnTouchOutside ;//能否取消
    private int mTheme;

    public AbsWindowDialog(Context context) {
        this(context,THEME_BASE);
    }

    public AbsWindowDialog(Context context, int theme) {
        this.mContext = context;
        this.mTheme = theme;

    }

    public void setCallback(IWindowDialogCallback callback) {
        this.mCallback = callback;
    }

    public void setDismissCallbackExtra(IOnDismissListener dismissCallbackExtra) {
        this.mDismissCallbackExtra = dismissCallbackExtra;
    }

    public void setCanceledOnTouchOutside(boolean canceledOnTouchOutside) {
        this.mCanceledOnTouchOutside = canceledOnTouchOutside;
    }

    public boolean isSystemAlert() {
        return isSystemAlert;
    }

    public T getData() {
       return mData;
    }

    public void showOnUI(final T t) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                show(t);
            }
        });
    }

    private void show(T t) {
        dismiss();
        mData = t;
        mDialog = createDialog();
        initView(t);
        mDialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        mRootView.setFitsSystemWindows(true);
        mDialog.setContentView(mRootView);
        mDialog.setCancelable(mCanceledOnTouchOutside);
        mDialog.setCanceledOnTouchOutside(mCanceledOnTouchOutside);
        if (mDialog.getWindow() != null && isSystemAlert) {
            mDialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        }


        setOnShowListener();
        setOnCancelListener();
        setOnDismissListener();
        mDialog.show();

    }

    public void dismiss() {
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
            mDialog = null;
        }
    }

    public void cancel() {
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.cancel();
            mDialog = null;
        }
    }

    protected Dialog createDialog() {
        cancel();
        return new Dialog(mContext,getTheme());
    }

    protected void setSystemAlert(boolean systemAlert) {
        isSystemAlert = systemAlert;
    }

    protected void setTheme(int theme) {
        this.mTheme = theme;
    }

    private int getTheme() {
        if (mTheme == THEME_BASE) {
            return R.style.DialogBaseTheme;
        } else if (mTheme == THEME_WITH_LIGHT) {
            return R.style.DialogWithLightTheme;
        } else {
            return mTheme;
        }
    }

    private void initView(T t) {
        initRootView();
        View view = createContextView(mRootView,t);
        if (view == null) {
            return;
        }
        view.setClickable(true);
        mRootView.removeAllViews();
        RelativeLayout.LayoutParams p = (RelativeLayout.LayoutParams) view.getLayoutParams();
        p.addRule(RelativeLayout.CENTER_IN_PARENT);
        mRootView.addView(view,p);
    }

    private void initRootView() {
        mRootView = (RelativeLayout) LayoutInflater.from(mContext).inflate(R.layout.layout_dialog_base,null);
        mRootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCanceledOnTouchOutside) {
                    cancel();
                }
            }
        });
    }

    private void setOnShowListener() {
        if (mCallback != null) {
            mDialog.setOnShowListener(new DialogInterface.OnShowListener() {
                @Override
                public void onShow(DialogInterface dialog) {
                    mCallback.onShowLintener(mData);
                }
            });
        }
    }
    private void setOnCancelListener() {
        if (mCallback != null) {
            mDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    mCallback.onCancelListener(mData);
                }
            });
        }
    }

    private void setOnDismissListener() {
        mDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                if (mDismissCallbackExtra != null) {
                    mDismissCallbackExtra.onDismiss(mData);
                }
                if (mCallback != null) {
                    mCallback.onDismissListener(mData);
                }
            }
        });

    }

    protected abstract @NonNull
    View createContextView(ViewGroup viewGroup, T t);

    public interface IOnDismissListener<T>{
        void onDismiss(T t);
    }
}
