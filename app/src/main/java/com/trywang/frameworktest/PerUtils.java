package com.trywang.frameworktest;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.telephony.TelephonyManager;

import androidx.core.content.ContextCompat;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * TODO 写清楚此类的作用
 *
 * @author Try
 * @date 2020-03-10 14:34
 */
public class PerUtils {
    public static final String[] PERMISSIONS = new String[]{
            Manifest.permission.SEND_SMS,//发送短信
            Manifest.permission.READ_SMS,//读短信
            Manifest.permission.ACCESS_COARSE_LOCATION,//获取错略位置
            Manifest.permission.ACCESS_FINE_LOCATION,//获取精确位置
            Manifest.permission.CAMERA,//拍照权限
            Manifest.permission.READ_CONTACTS,//读取联系人
            Manifest.permission.WRITE_CONTACTS,//写联系人
            Manifest.permission.WRITE_EXTERNAL_STORAGE,//写入外部存储  写照片，文件、数据存储
            Manifest.permission.READ_EXTERNAL_STORAGE,//读取外部存储  读取照片，文件
    };

    /**
     * 检查权限
     *
     * @param context
     * @param permissions
     * @return true:已全部有权限   false:部分权限没有
     */
    public static boolean checkPer(Context context, String[] permissions) {
        return checkPermission(context, permissions).size() <= 0;
    }

    /**
     * 检查权限
     *
     * @param context
     * @return true:已全部有权限   false:部分权限没有
     */
    public static boolean checkPermission(Context context) {
        return checkPermission(context, PERMISSIONS).size() <= 0;
    }

    /**
     * 检查权限
     *
     * @param context
     * @param perGroup 权限组
     * @return list长度  <= 0 表示权限都通过
     */
    public static List<String> checkPermission(Context context, String[] perGroup) {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < perGroup.length; i++) {
            if (ContextCompat.checkSelfPermission(context, perGroup[i])
                    == PackageManager.PERMISSION_DENIED) {
                //没有权限
                list.add(perGroup[i]);
            }
        }
        return list;
    }

    public static String getIMEI(Context context) {
        String imei = "";
        try {
            TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                imei = tm.getDeviceId();
            } else {
                Method method = tm.getClass().getMethod("getImei");
                imei = (String) method.invoke(tm);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return imei;
    }
}
