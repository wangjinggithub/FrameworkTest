package com.trywang.module_widget.dialog;

import android.content.Context;

import com.rae.widget.dialog.impl.ProvinceSelectionDialog;
import com.rae.widget.dialog.model.LocationProvinceInfoBean;
import com.trywang.module_widget.R;

import java.util.List;

/**
 * 地址选择器
 *
 * @author Try
 * @date 2019/6/19 10:36
 */
public class AddrSelDialog extends ProvinceSelectionDialog {
    public AddrSelDialog(Context context, List<LocationProvinceInfoBean> data) {
        super(context, data);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.widget_dialog_select_city_v2;
    }
}
