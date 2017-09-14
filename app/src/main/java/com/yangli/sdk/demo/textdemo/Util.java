package com.yangli.sdk.demo.textdemo;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

/**
 * Created by Administrator on 2017/9/5.
 */

public class Util implements TipRelativeLayout.AnimationEndCallback {
    private PopupWindow reportVideoPopwindow;

    public void showTips(Activity activity){
        int translateHeight=(int) dip2px(activity,60);
        View parent = ((ViewGroup) activity.findViewById(android.R.id.content)).getChildAt(0);
        View popView = LayoutInflater.from(activity).inflate(R.layout.activity_popupwindow_tips, null);
        int statusBar=getStatusBarHeight(activity);
        reportVideoPopwindow = new PopupWindow(popView, ViewGroup.LayoutParams.MATCH_PARENT,translateHeight*2);
        reportVideoPopwindow.showAtLocation(parent, Gravity.TOP, 0, 0);
        TipRelativeLayout tvTips=(TipRelativeLayout) popView.findViewById(R.id.rl_tips);
        tvTips.setTitleHeight(0);//移动状态栏的高度
        tvTips.setAnimationEnd(this);//设置动画结束监听函数
        tvTips.showTips();//显示提示RelativeLayout,移动动画.
    }

    public int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    private  float dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        float result = dpValue * scale + 0.0f;
        return result;
    }

    @Override
    public void onAnimationEnd() {
        reportVideoPopwindow.dismiss();//动画结束，隐藏popupwindow
    }
}