package com.trywang.module_widget.et;

import android.content.Context;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;

import com.trywang.module_widget.R;
import com.trywang.module_widget.R2;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 密码的输入框
 *
 * @author Try
 * @date 2017/11/23 16:37
 */
public class PwdEditText extends LinearLayout implements ClearEditText.ITextChangeWatcher,
        View.OnFocusChangeListener{
    private static final InputFilter[] NO_FILTERS = new InputFilter[0];
    @BindView(R2.id.cet_pwd)
    ClearEditText mEtPwd;
    @BindView(R2.id.iv_see_pwd)
    ImageView mIvSeePwd;
    @BindView(R2.id.fl_see_pwd)
    FrameLayout mFlSeePwd;

    List<ClearEditText.ITextChangeWatcher> mChangeWatcherList = new ArrayList<>();
    Context mContext;
    String mHint = "请输入";

    public PwdEditText(Context context) {
        this(context,null);
    }

    public PwdEditText(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public PwdEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    public Editable getText() {
        return mEtPwd.getText();
    }

    public void setMaxLength(int maxLength) {
        if (maxLength >= 0) {
            mEtPwd.setFilters(new InputFilter[] { new InputFilter.LengthFilter(maxLength) });
        } else {
            mEtPwd.setFilters(NO_FILTERS);
        }
    }

    public void addTextChangeWatcher(ClearEditText.ITextChangeWatcher watcher) {
        if (watcher != null) {
            mChangeWatcherList.add(watcher);
        }
    }

    public void setHintText(String hint) {
        this.mHint = hint;
        if (mEtPwd != null) {
            mEtPwd.setHint(mHint);
        }
    }

    protected @NonNull
    int getContextRes() {
        return R.layout.widget_pwd_et;
    }

    protected View getContextView() {
        return inflate(mContext,getContextRes() ,this);
    }

    private void init(Context context) {
        mContext = context;
        getContextView();
        ButterKnife.bind(this);

        mEtPwd.setTextChangeWatcher(this);
        mEtPwd.setOnFocusChangeListener(this);
    }




    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mEtPwd.setHint(mHint + "");
    }

    @Override
    public void beforeChange(CharSequence s, int start, int count, int after) {
        for (int i = 0; i < mChangeWatcherList.size(); i++) {
            mChangeWatcherList.get(i).beforeChange(s,start,count,after);
        }
    }

    @Override
    public void whenChange(CharSequence s, int start, int count, int after) {
        for (int i = 0; i < mChangeWatcherList.size(); i++) {
            mChangeWatcherList.get(i).whenChange(s,start,count,after);
        }
    }

    @Override
    public void afterChange(Editable s) {
        String result = TextUtils.isEmpty(s.toString()) ? "" : s.toString();
        if (result.length() > 0) {
            mFlSeePwd.setVisibility(View.VISIBLE);
        } else {
            mFlSeePwd.setVisibility(View.GONE);
        }

        if (result.contains(" ")) {
            result = result.replace(" ", "");
            mEtPwd.setText(result);
            mEtPwd.setSelection(result.length());
        }

        for (int i = 0; i < mChangeWatcherList.size(); i++) {
            mChangeWatcherList.get(i).afterChange(s);
        }
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        mEtPwd.onFocusChange(v, hasFocus);
        if (hasFocus && mEtPwd.length() > 0) {
            mFlSeePwd.setVisibility(View.VISIBLE);
        } else {
            mFlSeePwd.setVisibility(View.GONE);
        }
    }

    @OnClick(R2.id.fl_see_pwd)
    public void onClickSeePwd(View view) {
        //查看密码
        boolean isSel;
        if (view.getTag() == null) {
            isSel = false;
        } else {
            isSel = (boolean) view.getTag();
        }
        isSel = !isSel;
        view.setTag(isSel);

        if (isSel) {
            mEtPwd.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            mIvSeePwd.setImageResource(R.mipmap.icon_eye_sel);
        } else {
            mEtPwd.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            mIvSeePwd.setImageResource(R.mipmap.icon_eye_unsel);
        }
        mEtPwd.setSelection(mEtPwd.length());
        mEtPwd.requestFocus();
    }
}
