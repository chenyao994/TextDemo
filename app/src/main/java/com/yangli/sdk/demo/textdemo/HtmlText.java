package com.yangli.sdk.demo.textdemo;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.Html;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.widget.TextView;

import org.xml.sax.XMLReader;
import org.xmlpull.v1.XmlPullParser;

import java.io.ByteArrayInputStream;

/**
 * ================================================
 * 作    者：yanliqichun Emial:yanglqc@80pm.com
 * 版    本：1.0
 * 创建日期：2017/7/7
 * 描    述：
 * 修订历史：
 * ================================================
 */

public class HtmlText extends TextView {

    private Context context;

    private String mData;


    //接口回调
    private OnHtmlTextClick mOnHtmlTextClick;
    //初始化接口回调
    public void setOnHtmlTextClick(OnHtmlTextClick mOnHtmlTextClick) {
        this.mOnHtmlTextClick = mOnHtmlTextClick;
    }


    public void init(Context context) {
        this.context = context;
    }

    public HtmlText(Context context) {
        super(context);
    }

    public HtmlText(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public HtmlText(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    public void setText(CharSequence text, BufferType type) {
        //得到String字符
        if (text == null) {
            mData = "";
        } else {
            mData = text.toString();
        }
        //用Html中重写的TagHandler解析，以Spanned格式输出
        Spanned spanned = Html.fromHtml(mData, null, new MyTagHandler());
        super.setText(spanned, type);
    }




    //实现Html.TagHandler接口
    class MyTagHandler implements Html.TagHandler {
        private int startIndex = 0;
        private int stopIndex = 0;
        private String mId = "";
        private int mType = -1;
        private String mName = "";

        @Override
        //重写handleTag解析
        public void handleTag(boolean opening, String tag, Editable output, XMLReader xmlReader) {

            if (tag.equalsIgnoreCase("user")) {
                //如果是开头
                if (opening) {
                    //记录当前下表
                    startIndex = output.length();
                } else {
                    //结尾开始对数据进行处理
                    endGame(output);
                }
            } else if (tag.equalsIgnoreCase("topic")) {
                if (opening) {
                    startIndex = output.length();
                } else {
                    endGame(output);
                }
            }

        }

        public void endGame(Editable output) {
            //获取到结束位置的下表
            stopIndex = output.length();

            //使用pull解析，获取标签内的属性
            XmlPull(output);
            //根据不同的类型添加不同的前缀
            if (mType == 1) {
                output.insert(startIndex, "@");
                //因为多添加了一个符号所以截止位置需要+1
                stopIndex += 1;
                //startIndex+1是因为获取name的时候为了不把前缀的符号包括进去所以下标 前移一位
                output.setSpan(new GameSpan(output.subSequence(startIndex + 1, stopIndex).toString(),
                        mId, mType), startIndex, stopIndex, Spanned.SPAN_MARK_MARK);
                output.insert(stopIndex, " ");
            } else if (mType == 2) {
                output.insert(startIndex, "#");
                stopIndex += 1;
                output.insert(stopIndex, "#");
                //startIndex+1是因为获取name的时候为了不把前缀的符号包括进去所以下标 前移一位
                output.setSpan(new GameSpan(output.subSequence(startIndex + 1, stopIndex).toString() ,
                        mId, mType), startIndex, stopIndex+1, Spanned.SPAN_MARK_MARK);
                output.insert(stopIndex+1, " ");
            }
            Log.e("yl", "endGame: " + output.toString());


            //在结尾处添加空格





        }


        private class GameSpan extends ClickableSpan {
            private String mName;
            private int mType;
            private String mId;

            public GameSpan(String mName, String mId, int mType) {
                this.mName = mName;
                this.mId = mId;
                this.mType = mType;
            }

            @Override
            //重写点击事件
            public void onClick(View v) {
                //设置点击事件
                if (mOnHtmlTextClick != null) {
                    mOnHtmlTextClick.onClick(mName, mId, mType);
                }

            }

            @Override
            //对截取出来的字段设置字体颜色
            public void updateDrawState(TextPaint ds) {


                if (mType == 1) {
                    ds.setColor(getResources().getColor(R.color.colorAccent));
                } else {
                    ds.setColor(getResources().getColor(R.color.colorPrimary));
                }
                //设置文字接口回调
                if (mOnHtmlTextClick != null) {
                    mOnHtmlTextClick.onupdateDrawState(ds, mType);
                }
            }
        }

        //使用XML中的Pull解析，小巧轻便，解析速度快，简单易用
        private void XmlPull(Editable output) {
            try {
                //字节数组输入流
                ByteArrayInputStream mInputStream;
                if (mData != null && !"".equals(mData)) {
                    //创建字节数组输入流
                    mInputStream = new ByteArrayInputStream(mData.getBytes());
                } else {
                    return;
                }
                //创建xmlPull解析器
                XmlPullParser parser = Xml.newPullParser();
                ///初始化xmlPull解析器，内容，格式
                parser.setInput(mInputStream, "utf-8");
                //读取文件的类型
                int type = parser.getEventType();
                //无限判断文件类型进行读取
                while (type != XmlPullParser.END_DOCUMENT) {
                    switch (type) {
                        //开始标签
                        case XmlPullParser.START_TAG:
                            //当内容里面包含其中一个标签的时候进行解析处理
                            if ("user".equals(parser.getName()) || "topic".equals(parser.getName())) {
                                //必须要先获取属性 如果先获取值 则无法获取到属性
                                //获取id属性
                                String id = parser.getAttributeValue(null, "id");
                                //获取type属性
                                String strType = parser.getAttributeValue(null, "type");

                                //获取user或topic的值
                                mName = parser.nextText();
                                //如果当前解析的值是当前截取的字段，获取当前解析的属性
                                if (mName.equals(output.subSequence(startIndex, stopIndex).toString())) {
                                    //属性赋值用于点击事件判断
                                    mId = id;
                                    try {
                                        mType = Integer.valueOf(strType);
                                    } catch (Exception e) {
                                        mType = 0;
                                    }
                                    type = XmlPullParser.END_DOCUMENT;
                                }
                            }
                            break;
                        //结束标签
                        case XmlPullParser.END_TAG:
                            //                            Log.e("yl", "XmlPull: 结束标签解析");
                            break;
                    }
                    //继续往下读取标签类型
                    type = parser.next();
                }
            } catch (Exception e) {

            }
        }

    }

    //接口回调
    public interface OnHtmlTextClick {
        //点击事件回调
        void onClick(String name, String id, int type);
        //文字颜色回调 包括前缀符号
        void onupdateDrawState(TextPaint ds, int type);

    }



}
