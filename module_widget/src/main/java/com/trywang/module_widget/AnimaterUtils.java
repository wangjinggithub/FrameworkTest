package com.trywang.module_widget;

import android.animation.ObjectAnimator;
import android.view.View;

/**
 * TODO 写清楚此类的作用
 *
 * @author Try
 * @date 2020-02-25 14:02
 */
public class AnimaterUtils {
    public static void shake(View view){
        ObjectAnimator oa = ObjectAnimator.ofFloat(view,"TranslationX",-10,0,10,0,-10,0,10,0);
        oa.setRepeatCount(3);
        oa.setDuration(200);
        oa.start();
    }
}
