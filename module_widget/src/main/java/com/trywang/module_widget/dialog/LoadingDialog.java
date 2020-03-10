package com.trywang.module_widget.dialog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.trywang.module_widget.R;

/**
 * TODO 写清楚此类的作用
 *
 * @author Try
 * @date 2018/10/9 15:13
 */
public class LoadingDialog extends AbsWindowDialog<String> {

    public LoadingDialog(Context context) {
        super(context);
    }

    public LoadingDialog(Context context, int theme) {
        super(context, theme);
    }

    @NonNull
    @Override
    protected View createContextView(ViewGroup viewGroup, String msg) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.dialog_loading,viewGroup,false);
        TextView tv = view.findViewById(R.id.tv);
        tv.setText(msg);
        return view;
    }


}

