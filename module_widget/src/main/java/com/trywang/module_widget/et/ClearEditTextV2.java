package com.trywang.module_widget.et;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.appcompat.widget.AppCompatEditText;

/**
 * 简单的带清除功能的et
 *
 * @author Try
 * @date 2018/9/28 10:52
 */
public class ClearEditTextV2 extends AppCompatEditText implements View.OnClickListener {
    private View mDeleteView;

    public void withDeleteView(View deleteView) {
        if (deleteView == null) {
            return;
        }
        mDeleteView = deleteView;
        deleteView.setOnClickListener(this);
        onTextChanged(getText(),0,0,length());
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
    public ClearEditTextV2(Context context) {
        super(context);
    }

    public ClearEditTextV2(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ClearEditTextV2(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

}
