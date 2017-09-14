package com.yangli.sdk.demo.textdemo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;

import static com.yangli.sdk.demo.textdemo.R.id.tv;

public class MainActivity extends AppCompatActivity {
    private HtmlText tv1, tv2, tv3;
    private TextView mTV;
    private baseAdapter mAdapter;
    private ListView mList;
    private EditText editText;
    private ArrayList<String> list = new ArrayList<>();
    Handler handler =new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            finish();
            startActivity(new Intent(MainActivity.this,DialogActivity.class));
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

      //   startActivity(new Intent(this,DialogActivity.class));
        initView();


    }

    private void initView() {

        editText = (EditText)findViewById(R.id.edit_query) ;
        mTV = (TextView)findViewById(R.id.text_viewsss);
        tv1 = (HtmlText) findViewById(tv);
        mList = (ListView) findViewById(R.id.listview);
        tv1.init(this);
        tv1.setMovementMethod(LinkMovementMethod.getInstance());
       // tv1.setHighlightColor(getResources().getColor(R.color.highlighted_text_dark));
        tv1.setOnHtmlTextClick(new HtmlText.OnHtmlTextClick() {
            @Override
            public void onClick(String name, String id, int type) {
                Toast.makeText(MainActivity.this, "ccc", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onupdateDrawState(TextPaint ds, int type) {

            }
        });


        findViewById(R.id.ssssssss).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                new Thread(){
                    @Override
                    public void run() {
                        super.run();
                        try {
                            Thread.sleep(2000);
                            handler.sendMessage(new Message());
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }.start();


                String srere=  editText.getText().toString();
                if (srere!=null)
                {

                    mTV.setText( addList("2323","1","陈瑶",srere) );
                    list.add (addList("2323","1","陈瑶",srere));
                    mList.setAdapter(mAdapter);
                }
            }
        });
        initData();
    }

    private String  addList(String id,String type,String name,String contest)
    {
                return  "<tag><user id=\""+id+"\" type=\""+type+"\">"+name+"</user>"+contest+"</tag>";
    }


    private void initData() {
        String str = "<content>" +
                "<user id=\"13\" type=\"1\">张三</user>" +
                "占位符===占位符===占位符" +
                "<user id=\"14\" type=\"1\">李四</user>" +
                "占位符===占位符===占位符" +
                "<topic id=\"154\" type=\"2\">吃蛇</topic>" +
                "占位符===占位符===占位符" +
                "<user id=\"15\" type=\"1\">王五</user>" +
                "</content>";

        String str2 = "<tag><user id=\"ZbGzrNmj6VhQTywfPkn\" type=\"1\">阿里巴巴</user></tag> <tag><user id=\"axR9u9i4mAlfPF0BvDp\" type=\"1\">google</user></tag> <tag><user id=\"jfZOHFgbma9tp3rrTxm\" type=\"1\">Cocacola</user></tag> 那你就";

        String str3 = "<tag><user id=\"21\" type=\"1\">二十一</user>酱油符===酱油符===酱油符<user id=\"22\" type=\"1\">二十二</user>" +
                "酱油符===酱油符===酱油符<topic id=\"87\" type=\"2\">打水</topic>" +
                "酱油符===酱油符===酱油符<user id=\"23\" type=\"1\">二十三</user></tag>";

        String str4 = "<tag><user id=\"31\" type=\"1\">三十一</user>飞机符===飞机符===飞机符<user id=\"34\" type=\"1\">三十二</user>" +
                "飞机符===飞机符===飞机符<topic id=\"20\" type=\"2\">路过</topic>" +
                "飞机符===飞机符===飞机符<user id=\"33\" type=\"1\">三十三</user></tag>";
        mTV.setText(str2);
        list.add(str);
        list.add(str4);
        list.add(str2);
        list.add(str3);
        list.add(str2);
        list.add(str3);

        mAdapter = new baseAdapter(this, list);
        mList.setAdapter(mAdapter);
        mList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivity.this, "Adapter:" + position, Toast.LENGTH_SHORT).show();
            }
        });
        tv1.setText(str);
    }
}