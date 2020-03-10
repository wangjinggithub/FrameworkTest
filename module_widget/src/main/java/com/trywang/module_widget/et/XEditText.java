package com.trywang.module_widget.et;

import android.content.Context;
import android.text.InputType;
import android.util.AttributeSet;
import android.view.View;

import androidx.appcompat.widget.AppCompatEditText;

/**
 * 带清除和展示密码功能的edittext
 *
 * @author Try
 * @date 2018/10/29 13:53
 */
public class XEditText extends AppCompatEditText implements View.OnClickListener{

    private View mDeleteView;
    private View mShowEyesView;

    public void withDeleteView(View deleteView) {
        if (deleteView == null) {
            return;
        }
        mDeleteView = deleteView;
        deleteView.setOnClickListener(this);
        onTextChanged(getText(),0,0,length());
    }

    public void withShowEyesView(View showEyesView,boolean isSel){
        if (showEyesView == null) {
            return;
        }
        mShowEyesView = showEyesView;
        mShowEyesView.setOnClickListener(mShowEyesListener);
        mShowEyesView.setTag(isSel);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (mDeleteView != null)
            mDeleteView.setOnClickListener(null);
        mDeleteView = null;
    }

    @Override
    protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter);
        if (mDeleteView != null) {
            mDeleteView.setVisibility(text.length() > 0 ? View.VISIBLE : View.INVISIBLE);
        }
    }

    @Override
    public void onClick(View v) {
        setText("");
        setSelection(0);
    }
    public XEditText(Context context) {
        super(context);
    }

    public XEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public XEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private View.OnClickListener mShowEyesListener = view -> {
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
            setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
        } else {
            setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        }
        mShowEyesView.setSelected(isSel);
        setSelection(length());
        requestFocus();
    };



}
