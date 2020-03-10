package com.trywang.module_widget.listview;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.TypedValue;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.header.StoreHouseHeader;

/**
 * 下拉刷新
 * Created by shenminjie on 2017/8/2.
 * mail:shenminjie@100bei.com
 */

public class CommonRefreshLayout extends PtrClassicFrameLayout {

    public CommonRefreshLayout(Context context) {
        super(context);
        initView();
    }

    public CommonRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public CommonRefreshLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView();
    }

    /**
     * 初始化view
     */
    private void initView() {
        final StoreHouseHeader header = new StoreHouseHeader(getContext());
        header.initWithString("ITREE");
        header.setPadding(0, dp2px(15), 0, dp2px(15));
        header.setTextColor(Color.parseColor("#303030"));
        setHeaderView(header);
        addPtrUIHandler(header);
    }

    private int dp2px(int value) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value, getResources().getDisplayMetrics());
    }
}
