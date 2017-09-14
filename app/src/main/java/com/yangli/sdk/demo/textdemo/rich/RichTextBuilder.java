package com.yangli.sdk.demo.textdemo.rich;

import android.content.Context;
import android.graphics.Color;
import android.text.Spannable;
import android.text.method.MovementMethod;
import android.widget.TextView;


import com.yangli.sdk.demo.textdemo.listener.ITextViewShow;
import com.yangli.sdk.demo.textdemo.listener.SpanAtUserCallBack;
import com.yangli.sdk.demo.textdemo.listener.SpanCreateListener;
import com.yangli.sdk.demo.textdemo.listener.SpanTopicCallBack;
import com.yangli.sdk.demo.textdemo.listener.SpanUrlCallBack;
import com.yangli.sdk.demo.textdemo.model.TopicModel;
import com.yangli.sdk.demo.textdemo.model.UserModel;
import com.yangli.sdk.demo.textdemo.span.ClickAtUserSpan;
import com.yangli.sdk.demo.textdemo.span.ClickTopicSpan;
import com.yangli.sdk.demo.textdemo.span.LinkSpan;

import java.util.List;

/**
 * 富文本设置 话题、at某人，链接识别
 * Created by guoshuyu on 2017/8/17.
 */

public class RichTextBuilder {
    private Context context;
    private String content = "";
    private List<UserModel> listUser;
    private List<TopicModel> listTopic;
    private TextView textView;
    private SpanAtUserCallBack spanAtUserCallBack;
    private SpanUrlCallBack spanUrlCallBack;
    private SpanTopicCallBack spanTopicCallBack;
    private SpanCreateListener spanCreateListener;
    private int atColor = Color.BLUE;
    private int topicColor = Color.BLUE;
    private int linkColor = Color.BLUE;
    private boolean needNum = false;
    private boolean needUrl = false;

    public RichTextBuilder(Context context) {
        this.context = context;
    }

    /**
     * 文本内容
     */
    public RichTextBuilder setContent(String content) {
        this.content = content;
        return this;
    }

    /**
     * at某人的list
     */
    public RichTextBuilder setListUser(List<UserModel> listUser) {
        this.listUser = listUser;
        return this;
    }

    /**
     * 话题list
     */
    public RichTextBuilder setListTopic(List<TopicModel> listTopic) {
        this.listTopic = listTopic;
        return this;
    }

    /**
     * 显示文本view
     */
    public RichTextBuilder setTextView(TextView textView) {
        this.textView = textView;
        return this;
    }

    /**
     * at某人显示颜色
     */
    public RichTextBuilder setAtColor(int atColor) {
        this.atColor = atColor;
        return this;
    }

    /**
     * 话题显示颜色
     */
    public RichTextBuilder setTopicColor(int topicColor) {
        this.topicColor = topicColor;
        return this;
    }

    /**
     * 链接显示颜色
     */
    public RichTextBuilder setLinkColor(int linkColor) {
        this.linkColor = linkColor;
        return this;
    }

    /**
     * 是否需要识别电话
     */
    public RichTextBuilder setNeedNum(boolean needNum) {
        this.needNum = needNum;
        return this;
    }

    public RichTextBuilder setNeedUrl(boolean needUrl) {
        this.needUrl = needUrl;
        return this;
    }

    /**
     * at某人点击回调
     */
    public RichTextBuilder setSpanAtUserCallBack(SpanAtUserCallBack spanAtUserCallBack) {
        this.spanAtUserCallBack = spanAtUserCallBack;
        return this;
    }

    /**
     * url点击回调
     */
    public RichTextBuilder setSpanUrlCallBack(SpanUrlCallBack spanUrlCallBack) {
        this.spanUrlCallBack = spanUrlCallBack;
        return this;
    }

    /**
     * 话题点击回调
     */
    public RichTextBuilder setSpanTopicCallBack(SpanTopicCallBack spanTopicCallBack) {
        this.spanTopicCallBack = spanTopicCallBack;
        return this;
    }

    /**
     * 自定义span回调，如果不需要可不设置
     */
    public RichTextBuilder setSpanCreateListener(SpanCreateListener spanCreateListener) {
        this.spanCreateListener = spanCreateListener;
        return this;
    }

    public Spannable buildSpan(ITextViewShow iTextViewShow) {
        if (context == null) {
            throw new IllegalStateException("context could not be null.");
        }

        return TextCommonUtils.getAllSpanText(
                context,
                content,
                listUser,
                listTopic,
                iTextViewShow,
                atColor,
                linkColor,
                topicColor,
                needNum,
                needUrl,
                spanAtUserCallBack,
                spanUrlCallBack,
                spanTopicCallBack);
    }


    public void build() {

        if (context == null) {
            throw new IllegalStateException("context could not be null.");
        }

        if (textView == null) {
            throw new IllegalStateException("textView could not be null.");
        }

        ITextViewShow iTextViewShow = new ITextViewShow() {
            @Override
            public void setText(CharSequence charSequence) {
                textView.setText(charSequence);
            }

            @Override
            public CharSequence getText() {
                return textView.getText();
            }

            @Override
            public void setMovementMethod(MovementMethod movementMethod) {
                textView.setMovementMethod(movementMethod);
            }

            @Override
            public void setAutoLinkMask(int flag) {
                textView.setAutoLinkMask(flag);
            }


            @Override
            public ClickAtUserSpan getCustomClickAtUserSpan(Context context, UserModel userModel, int color, SpanAtUserCallBack spanClickCallBack) {
                if (spanCreateListener != null) {
                    return spanCreateListener.getCustomClickAtUserSpan(context, userModel, color, spanClickCallBack);
                }
                return null;
            }

            @Override
            public ClickTopicSpan getCustomClickTopicSpan(Context context, TopicModel topicModel, int color, SpanTopicCallBack spanTopicCallBack) {
                if (spanCreateListener != null) {
                    return spanCreateListener.getCustomClickTopicSpan(context, topicModel, color, spanTopicCallBack);
                }
                return null;
            }

            @Override
            public LinkSpan getCustomLinkSpan(Context context, String url, int color, SpanUrlCallBack spanUrlCallBack) {
                if (spanCreateListener != null) {
                    return spanCreateListener.getCustomLinkSpan(context, url, color, spanUrlCallBack);
                }
                return null;
            }
        };

        Spannable spannable = TextCommonUtils.getAllSpanText(
                context,
                content,
                listUser,
                listTopic,
                iTextViewShow,
                atColor,
                linkColor,
                topicColor,
                needNum,
                needUrl,
                spanAtUserCallBack,
                spanUrlCallBack,
                spanTopicCallBack);
        textView.setText(spannable);
    }
}
