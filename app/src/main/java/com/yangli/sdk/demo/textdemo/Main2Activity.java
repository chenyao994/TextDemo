package com.yangli.sdk.demo.textdemo;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.yangli.sdk.demo.textdemo.listener.OnEditTextUtilJumpListener;
import com.yangli.sdk.demo.textdemo.listener.SpanAtUserCallBack;
import com.yangli.sdk.demo.textdemo.listener.SpanTopicCallBack;
import com.yangli.sdk.demo.textdemo.listener.SpanUrlCallBack;
import com.yangli.sdk.demo.textdemo.model.TopicModel;
import com.yangli.sdk.demo.textdemo.model.UserModel;
import com.yangli.sdk.demo.textdemo.rich.EmojiLayout;
import com.yangli.sdk.demo.textdemo.rich.JumpUtil;
import com.yangli.sdk.demo.textdemo.rich.RichEditBuilder;
import com.yangli.sdk.demo.textdemo.rich.RichEditText;
import com.yangli.sdk.demo.textdemo.rich.RichTextView;
import com.yangli.sdk.demo.textdemo.rich.SmileUtils;

import java.util.ArrayList;
import java.util.List;

public class Main2Activity extends AppCompatActivity implements View.OnClickListener{
    public final static int REQUEST_USER_CODE_INPUT = 1111;
    public final static int REQUEST_USER_CODE_CLICK = 2222;
    public final static int REQUEST_TOPIC_CODE_INPUT = 3333;
    public final static int REQUEST_TOPIC_CODE_CLICK = 4444;
    String insertContent = "这是测试文本#话题话题#哟 www.baidu.com " +
            " 来@某个人  @22222 @kkk " +
            " 好的,来几个表情[e2][e4][e55]，最后来一个电话 13245685478";
        TextView textView;
        RichTextView richTextView;
    RichEditText richEditText;
    String content = "这是测试#话题ssss话题#文本哟 www.baidu.com " +
            "\n来@某个人  @22222 @kkk " +
            "\n好的,来几个表情[e2][e4][e55]，最后来一个电话 13245685478";
    List<TopicModel> topicModels = new ArrayList<>();
    EmojiLayout emojiLayout;
    List<UserModel> nameList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        richEditText = (RichEditText)findViewById(R.id.emoji_edit_text);
        emojiLayout =(EmojiLayout)findViewById(R.id.emojiLayout);
        textView= (TextView)findViewById(R.id.text_viewssss);
        textView.setText(getClickableSpan());
        textView.setMovementMethod(LinkMovementMethod.getInstance());
        richTextView =(RichTextView)findViewById(R.id.richtext_view);
        richTextView.setAtColor(Color.BLUE);
        richTextView.setTopicColor(Color.BLUE);
        richTextView.setLinkColor(Color.BLUE);
        richTextView.setNeedNumberShow(true);
        richTextView.setNeedUrlShow(true);
        findViewById(R.id.emoji_show_bottom).setOnClickListener(this);
        findViewById(R.id.emoji_show_at).setOnClickListener(this);
        findViewById(R.id.insert_text_btn).setOnClickListener(this);
        findViewById(R.id.emoji_show_topic).setOnClickListener(this);


        SpanAtUserCallBack spanAtUserCallBack = new SpanAtUserCallBack() {
            @Override
            public void onClick(View view, UserModel userModel1) {
                Toast.makeText(view.getContext(), userModel1.getUser_name() + " 被点击了"+userModel1.getUser_id(), Toast.LENGTH_SHORT).show();
                if (view instanceof TextView) {
                    ((TextView) view).setHighlightColor(Color.TRANSPARENT);
                }
            }
        };

        SpanTopicCallBack spanTopicCallBack = new SpanTopicCallBack() {
            @Override
            public void onClick(View view, TopicModel topicModel) {
                Toast.makeText(view.getContext(), topicModel.getTopicName() + " 被点击了", Toast.LENGTH_SHORT).show();
                if (view instanceof TextView) {
                    ((TextView) view).setHighlightColor(Color.TRANSPARENT);
                }
            }
        };
        SpanUrlCallBack spanUrlCallBack = new SpanUrlCallBack() {
            @Override
            public void phone(View view, String phone) {
                Toast.makeText(view.getContext(), phone + " 被点击了", Toast.LENGTH_SHORT).show();
                if (view instanceof TextView) {
                    ((TextView) view).setHighlightColor(Color.TRANSPARENT);
                }
            }

            @Override
            public void url(View view, String url) {
                Toast.makeText(view.getContext(), url + " 被点击了", Toast.LENGTH_SHORT).show();
                if (view instanceof TextView) {
                    ((TextView) view).setHighlightColor(Color.TRANSPARENT);
                }
            }
        };

        initData();
        initEmoji();
        initView();
        richTextView.setSpanAtUserCallBackListener(spanAtUserCallBack);
       richTextView.setSpanTopicCallBackListener(spanTopicCallBack);
     richTextView.setSpanUrlCallBackListener(spanUrlCallBack);
        //所有配置完成后才设置text
     richTextView.setRichText(content, nameList, topicModels);
        //    richTextView.setRichText(content);
       // richTextView.setText(content);
    }

    private void initView() {
        emojiLayout.setEditTextSmile(richEditText);
        RichEditBuilder richEditBuilder = new RichEditBuilder();
        richEditBuilder.setEditText(richEditText)
                .setTopicModels(topicModels)
                .setUserModels(nameList)
                .setColorAtUser("#3F51B5")
                .setColorTopic("#3F51B5")
                .setEditTextAtUtilJumpListener(new OnEditTextUtilJumpListener() {
                    @Override
                    public void notifyAt() {
                        JumpUtil.goToUserList(Main2Activity.this, Main2Activity.REQUEST_USER_CODE_INPUT);
                    }

                    @Override
                    public void notifyTopic() {
                        JumpUtil.goToTopicList(Main2Activity.this, Main2Activity.REQUEST_TOPIC_CODE_INPUT);
                    }
                })
                .builder();


    }


    /**
     * 处理自己的表情
     */
    private void initEmoji() {
        List<Integer> data = new ArrayList<>();
        List<String> strings = new ArrayList<>();
        for (int i = 1; i < 64; i++) {
            int resId = getResources().getIdentifier("e" + i, "drawable", getPackageName());
            data.add(resId);
            strings.add("[e" + i + "]");
        }
        /**初始化为自己的**/
        SmileUtils.addPatternAll(SmileUtils.getEmoticons(), strings, data);
    }
    private void initData() {
        nameList.clear();
        topicModels.clear();

        UserModel userModel = new UserModel();
        userModel.setUser_name("22222");
        userModel.setUser_id("用户ID 312312");
        nameList.add(userModel);
        userModel = new UserModel();
        userModel.setUser_name("kkk");
        userModel.setUser_id("用户ID 1212");
        nameList.add(userModel);

        TopicModel topicModel = new TopicModel();
        topicModel.setTopicId("话题id 34324");
        topicModel.setTopicName("话题ssss话题");
        topicModels.add(topicModel);

    }





    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.emoji_show_bottom:
                emojiLayout.hideKeyboard();
                if (emojiLayout.getVisibility() == View.VISIBLE) {
                    emojiLayout.setVisibility(View.GONE);
                } else {
                    emojiLayout.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.emoji_show_at:
                JumpUtil.goToUserList(Main2Activity.this, Main2Activity.REQUEST_USER_CODE_CLICK);
                break;
            case R.id.insert_text_btn:

                Toast.makeText(Main2Activity.this,richEditText.getText().toString(),Toast.LENGTH_LONG).show();
                initData();
                richEditText.resolveInsertText(Main2Activity.this, insertContent, nameList, topicModels);
                break;

            case R.id.emoji_show_topic:
                JumpUtil.goToTopicList(Main2Activity.this, Main2Activity.REQUEST_TOPIC_CODE_CLICK);
                break;

        }
    }





    class Clickable extends ClickableSpan implements  View.OnClickListener{
        private final  View.OnClickListener mlister;

        Clickable(View.OnClickListener mlister) {
            this.mlister = mlister;
        }

        @Override
        public void onClick(View view) {
                mlister.onClick(view);
        }
       @Override
        public void updateDrawState(TextPaint ds) {
            super.updateDrawState(ds);
            ds.setColor(ds.linkColor);
            ds.setUnderlineText(false);
        }
    }

    private SpannableString getClickableSpan()
    {
        View.OnClickListener l =  new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Toast.makeText(Main2Activity.this,"点击了",Toast.LENGTH_LONG).show();
            }
        };
        SpannableString spannableString = new SpannableString(textView.getText().toString());
        int start = 16;
        int end  = spannableString.length();
        spannableString.setSpan(new Clickable(l),start,end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannableString;
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_USER_CODE_CLICK:
                    richEditText.resolveAtResult((UserModel) data.getSerializableExtra(UserListActivity.DATA));
                    break;
                case REQUEST_USER_CODE_INPUT:
                    richEditText.resolveAtResultByEnterAt((UserModel) data.getSerializableExtra(UserListActivity.DATA));
                    break;

                case REQUEST_TOPIC_CODE_INPUT:
                    richEditText.resolveTopicResultByEnter((TopicModel) data.getSerializableExtra(TopicListActivity.DATA));
                    break;
                case REQUEST_TOPIC_CODE_CLICK:
                    richEditText.resolveTopicResult((TopicModel) data.getSerializableExtra(TopicListActivity.DATA));
                    break;
            }
        }

    }

}
