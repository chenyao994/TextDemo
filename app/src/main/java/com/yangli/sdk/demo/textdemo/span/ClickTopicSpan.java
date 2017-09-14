package com.yangli.sdk.demo.textdemo.span;

import android.content.Context;
import android.view.View;

import com.yangli.sdk.demo.textdemo.listener.SpanTopicCallBack;
import com.yangli.sdk.demo.textdemo.model.TopicModel;

/**
 * 话题#点击回调
 * Created by guoshuyu on 2017/8/16.
 */

public class ClickTopicSpan extends ClickAtUserSpan {


    private TopicModel topicModel;
    private SpanTopicCallBack spanTopicCallBack;

    public ClickTopicSpan(Context context, TopicModel topicModel, int color, SpanTopicCallBack spanTopicCallBack) {
        super(context, null, color, null);
        this.topicModel = topicModel;
        this.spanTopicCallBack = spanTopicCallBack;
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        if (spanTopicCallBack != null) {
            spanTopicCallBack.onClick(view, topicModel);
        }
    }
}
