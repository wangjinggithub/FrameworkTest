package com.trywang.module_widget.dialog;

/**
 * 自定义dialog的回调函数
 *
 * @author Try
 * @date 2017/7/7 11:14
 */

public interface IWindowDialogCallback<T> {
    void onShowLintener(T t);
    void onDismissListener(T t);
    void onCancelListener(T t);
}
