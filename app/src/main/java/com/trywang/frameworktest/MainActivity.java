package com.trywang.frameworktest;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.tbruyelle.rxpermissions2.RxPermissions;
import com.trywang.module_base.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {
    @BindView(R.id.tv_one)
    TextView mTvOne;
    final RxPermissions rxPermissions = new RxPermissions(this);
    String[] perGroup = new String[]{
            Manifest.permission.SEND_SMS,//发送短信
            Manifest.permission.READ_SMS,//读短信
            Manifest.permission.ACCESS_COARSE_LOCATION,//获取错略位置
            Manifest.permission.ACCESS_FINE_LOCATION,//获取精确位置
            Manifest.permission.CAMERA,//拍照权限
            Manifest.permission.READ_CONTACTS,//读取联系人
            Manifest.permission.WRITE_CONTACTS,//写联系人
            Manifest.permission.WRITE_EXTERNAL_STORAGE,//写入外部存储  写照片，文件、数据存储
            Manifest.permission.READ_EXTERNAL_STORAGE,//读取外部存储  读取照片，文件
            Manifest.permission.READ_PHONE_STATE,//获取imei
    };
    boolean isJumpSettingReturn;
    @Override
    protected int getContextView() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView(@Nullable Bundle savedInstanceState) {
    }

    @Override
    protected void initDataSub(@Nullable Bundle savedInstanceState) {
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestP(perGroup);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isJumpSettingReturn && getPreListDenied(perGroup).size() > 0) {
            showDialog();
        } else {
            isJumpSettingReturn = false;
        }
    }

    private void requestP(String[] perGroup){
        List<String> perList = getPreListDenied(perGroup);

        if (perList.size() <= 0) {
            return;
        }
        String[] pers = perList.toArray(new String[perList.size()]);
        ActivityCompat.requestPermissions(this,pers ,1001);
    }

    /**
     * 判断是否有权限被拒绝
     * @param perGroup 列表长度 > 0  则表示有权限被拒绝
     * @return
     */
    private List<String> getPreListDenied(String[] perGroup){
        List<String> list = new ArrayList<>();
        for (int i = 0; i < perGroup.length; i++) {
            if (ContextCompat.checkSelfPermission(this,perGroup[i])
                    == PackageManager.PERMISSION_DENIED) {
                //没有权限
                list.add(perGroup[i]);
            }
        }
        return list;
    }

    @OnClick(R.id.tv_one)
    public void onClickOne(){
        String imei = PerUtils.getIMEI(this);
        mTvOne.setText("imei = "+imei);
//        getAppList();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        List<String> listDenied = new ArrayList<>();
        StringBuffer sbD = new StringBuffer("拒绝：");
        StringBuffer sbG = new StringBuffer("同意：");
        for (int i = 0; i < grantResults.length; i++) {
            if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                //拒绝：
                sbD.append(permissions[i]).append("\n");
                listDenied.add(permissions[i]);
            } else {
                //同意
                sbG.append(permissions[i]).append("\n");
            }
        }
        mTvOne.setText(sbG.append(sbD.toString()).toString());

        if (listDenied.size() > 0) {
            showDialog();
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void showDialog(){
        AlertDialog ad = new AlertDialog.Builder(this)
                .setTitle("权限未通过")
                .setMessage("有部分权限尚未同意，需要前往设置页面同意")
                .setCancelable(false)
                .setPositiveButton("前往同意", (dialogInterface, i) -> {
                    jumpSetting();
                })
        .create();
        ad.show();
    }

    private void jumpSetting(){
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.fromParts("package", this.getPackageName(), null));
        this.startActivity(intent);
        isJumpSettingReturn = true;
    }


    private void getAppList() {
        PackageManager pm = getPackageManager();
        // Return a List of all packages that are installed on the device.
        List<PackageInfo> packages = pm.getInstalledPackages(0);
        for (PackageInfo packageInfo : packages) {
            // 判断系统/非系统应用
            if ((packageInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0) // 非系统应用
            {
                String appName = packageInfo.applicationInfo.loadLabel(getPackageManager()).toString();
                String packName = packageInfo.packageName;
                System.out.println("App名字：" +appName+ "； 包名=" + packName);
                ;
            } else {
                // 系统应用
            }
        }
    }
}
