package com.trywang.module_widget.dialog;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;

import com.rae.widget.ScrollerNumberPicker;
import com.rae.widget.dialog.impl.SlideDialog;
import com.rae.widget.dialog.model.LocationCityAreaInfoBean;
import com.rae.widget.dialog.model.LocationCityInfoBean;
import com.rae.widget.dialog.model.LocationProvinceInfoBean;
import com.trywang.module_widget.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 地址选择器
 *
 * @author Try
 * @date 2019/6/19 10:36
 */
public class AddrSelDialogV3 extends SlideDialog {
    private final List<LocationProvinceInfoBean> mProvinceDataList;

    private ScrollerNumberPicker mProvinceView;
    private ScrollerNumberPicker mCityView;
    private ProvinceSelectionDismissListener mListener;

    public interface ProvinceSelectionDismissListener {

        /**
         * 选择成功
         *
         * @param provinceName
         * @param cityName
         * @param provinceId
         * @param cityId
         */
        void onDismiss(String provinceName, String cityName, String provinceId, String cityId);
    }

    public void setOnDismissListener(ProvinceSelectionDismissListener onDismissListener) {
        mListener = onDismissListener;
    }

    public AddrSelDialogV3(Context context, List<LocationProvinceInfoBean> data) {
        super(context);
        mProvinceDataList = data;

        setContentView(getLayoutId());
        mProvinceView = findViewById(R.id.snp_province);
        mCityView = findViewById(R.id.snp_city);


        // 关闭
        findViewById(R.id.tv_province_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        // 确定
        findViewById(R.id.tv_province_sure).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
                notifyDismiss();

            }
        });

        // 省份选择
        mProvinceView.setOnSelectListener(new ScrollerNumberPicker.OnSelectListener() {
            @Override
            public void endSelect(int index, String text) {
                List<LocationCityInfoBean> cityList = mProvinceDataList.get(index).getCity(); // 城市列表
                ArrayList<String> cityData = toCityArrayString(cityList);

                mCityView.setData(cityData);
                mCityView.setDefault(0);
            }

            @Override
            public void selecting(int id, String text) {

            }
        });


        // 城市选择
        mCityView.setOnSelectListener(new ScrollerNumberPicker.OnSelectListener() {
            @Override
            public void endSelect(int index, String text) {
            }

            @Override
            public void selecting(int id, String text) {

            }
        });

        load();
    }

    protected int getLayoutId() {
        return R.layout.widget_dialog_select_city_v3;
    }



    private void notifyDismiss() {
        if (mListener != null) {
            mListener.onDismiss(
                    mProvinceView.getSelectedText(),
                    mCityView.getSelectedText(),
                    mProvinceDataList.get(mProvinceView.getSelected()).getId(),
                    mProvinceDataList.get(mProvinceView.getSelected()).getCity().get(mCityView.getSelected()).getId()
            );
        }
    }


    /**
     * 第一次加载数据
     */
    private void load() {
        LocationProvinceInfoBean defaultProvince = mProvinceDataList.get(0);
        List<LocationCityInfoBean> defaultCityInfoBeenList = defaultProvince.getCity();
        List<LocationCityAreaInfoBean> defaultAreaInfoBeenList = defaultProvince.getCity().get(0).getArea();
        mProvinceView.setData(toProvinceArrayString(mProvinceDataList));
        mProvinceView.setDefault(0);

        mCityView.setData(toCityArrayString(defaultCityInfoBeenList));
        mCityView.setDefault(0);

    }

    private ArrayList<String> toProvinceArrayString(List<LocationProvinceInfoBean> data) {
        ArrayList<String> result = new ArrayList<>();
        for (LocationProvinceInfoBean item : data) {
            result.add(item.getName());
        }
        return result;
    }

    private ArrayList<String> toCityArrayString(List<LocationCityInfoBean> data) {
        ArrayList<String> result = new ArrayList<>();
        for (LocationCityInfoBean item : data) {
            result.add(item.getName());
        }
        return result;
    }

    private ArrayList<String> toAreasArrayString(List<LocationCityAreaInfoBean> data) {
        ArrayList<String> result = new ArrayList<>();
        for (LocationCityAreaInfoBean item : data) {
            result.add(item.getName());
        }
        return result;
    }


    /**
     * 重新加载数据，三级联动
     *
     * @param provinceName 省份
     * @param cityName     城市
     */
    public void invalidate(String provinceName, String cityName) {
        if (TextUtils.isEmpty(provinceName) || TextUtils.isEmpty(cityName)) {
            return;
        }

        ArrayList<String> provinceData = new ArrayList<>();
        ArrayList<String> cityData = new ArrayList<>();

        int defaultProvinceIndex = 0;
        int defaultCityIndex = 0;

        for (int index = 0; index < mProvinceDataList.size(); index++) {
            LocationProvinceInfoBean province = mProvinceDataList.get(index);
            provinceData.add(province.getName());
            if (provinceName != null && TextUtils.equals(provinceName, province.getName())) {
                defaultProvinceIndex = index;

                // 加载城市
                if (!TextUtils.isEmpty(cityName)) {
                    for (int cityIndex = 0; cityIndex < province.getCity().size(); cityIndex++) {
                        LocationCityInfoBean city = province.getCity().get(cityIndex);
                        cityData.add(city.getName());
                        if (TextUtils.equals(cityName, city.getName())) {
                            defaultCityIndex = cityIndex;
                        }
                    }
                }
            }
        }


        mProvinceView.setData(provinceData);
        mCityView.setData(cityData);

        try {
            mProvinceView.setDefault(defaultProvinceIndex);
            mCityView.setDefault(defaultCityIndex);
        } catch (Exception e) {
            mProvinceView.setDefault(0);
            mCityView.setDefault(0);
        }

    }

    /**
     * 自动设置数据，并回调
     */
    public void auto(String provinceName, String cityName) {
        invalidate(provinceName, cityName);
        dismiss();
        notifyDismiss();
    }
}
