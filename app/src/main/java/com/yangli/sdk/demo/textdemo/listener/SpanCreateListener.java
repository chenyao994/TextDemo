package com.yangli.sdk.demo.textdemo.listener;

import android.content.Context;

import com.yangli.sdk.demo.textdemo.model.TopicModel;
import com.yangli.sdk.demo.textdemo.model.UserModel;
import com.yangli.sdk.demo.textdemo.span.ClickAtUserSpan;
import com.yangli.sdk.demo.textdemo.span.ClickTopicSpan;
import com.yangli.sdk.demo.textdemo.span.LinkSpan;

/**
 * Created by guoshuyu on 2017/8/31.
 */

public interface SpanCreateListener {

    ClickAtUserSpan getCustomClickAtUserSpan(Context context, UserModel userModel, int color, SpanAtUserCallBack spanClickCallBack);

    ClickTopicSpan getCustomClickTopicSpan(Context context, TopicModel topicModel, int color, SpanTopicCallBack spanTopicCallBack);

    LinkSpan getCustomLinkSpan(Context context, String url, int color, SpanUrlCallBack spanUrlCallBack);
}
