package com.trywang.module_widget.dialog;

import android.content.Context;

import com.rae.widget.dialog.impl.SlideDialog;

import java.util.List;

/**
 * TODO 写清楚此类的作用
 *
 * @author Try
 * @date 2019-12-04 14:27
 */
public class TxtListSelectionDialog extends SlideDialog {
    List<ITxtItem> mListData;
    public TxtListSelectionDialog(Context context) {
        super(context);
    }

    public TxtListSelectionDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    public TxtListSelectionDialog(Context context, List<ITxtItem> listData) {
        super(context);
        this.mListData = listData;
    }

    public interface ITxtItem{
        String getId();
        String getTxtItem();
    }
}
