package com.yangli.sdk.demo.textdemo;

import android.content.Context;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * ================================================
 * 作    者：yanliqichun Emial:yanglqc@80pm.com
 * 版    本：1.0
 * 创建日期：2017/7/10
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class baseAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<String> list;

    public baseAdapter(Context context, ArrayList<String> list) {
        this.context = context;
        this.list = list;
    }


    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_text, null);

            holder.tv = (HtmlText) convertView.findViewById(R.id.tv);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        String str = list.get(position);

        //使用重写的LocalLinkMovementMethod防止和item点击事件冲突
        holder.tv.setMovementMethod(LinkMovementMethod.getInstance());

        //设置点击时无背景效果
      //  holder.tv.setHighlightColor(context.getResources().getColor(R.color.colorAccent));
        holder.tv.init(context);
        holder.tv.setText(str);

        holder.tv.setOnHtmlTextClick(new HtmlText.OnHtmlTextClick() {
            @Override
            public void onClick(String name, String id, int types) {
                Toast.makeText(context, "name:" + name + "id:" + id+" type "+types, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onupdateDrawState(TextPaint ds, int type) {
                if (type == 1) {
                    ds.setColor(context.getResources().getColor(R.color.colorPrimary));
                }
            }
        });

        holder.tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             //   Toast.makeText(context, "click", Toast.LENGTH_SHORT).show();
            }
        });

        return convertView;
    }

    class ViewHolder {
        HtmlText tv;
    }

}
