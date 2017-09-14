package com.yangli.sdk.demo.textdemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.yangli.sdk.demo.textdemo.model.TopicModel;

import java.util.ArrayList;
import java.util.List;
public class TopicListActivity extends AppCompatActivity {
    ListView userList;
    public final static String DATA = "data";
    private List<TopicModel> data = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic_list);
        userList =(ListView)findViewById(R.id.user_list);


        for (int i = 0; i < 50; i++) {
            TopicModel topicModel = new TopicModel();
            topicModel.setTopicName("测试话题" + i);
            topicModel.setTopicId(i * 30 + "");
            data.add(topicModel);
        }

        ArrayAdapter adapter = new ArrayAdapter<TopicModel>(this, R.layout.user_list_item, data);
        userList.setAdapter(adapter);
        userList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.putExtra(DATA, data.get(position));
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        });
    }

}
