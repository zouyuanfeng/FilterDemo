package com.itzyf.filterdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private FilterAdapter adapter;
    private List<LabelBean> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        initData();
        recyclerView.setAdapter(adapter = new FilterAdapter(this, data));
        GridLayoutManager layoutManager = new GridLayoutManager(this, 3);
        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int i) {
                if (data.get(i).getType() == LabelBean.HEADER)
                    return 3;
                else return 1;
            }
        });
        recyclerView.setLayoutManager(layoutManager);
        adapter.setOnCheckListener(item -> Toast.makeText(MainActivity.this, item.getLabel(), Toast.LENGTH_SHORT).show());
    }

    private void initData() {
        data = new ArrayList<>();
        String groupName = "";
        for (int i = 0; i < 50; i++) {
            if (i % 10 == 0) {
                groupName = "我是标题" + i;
                data.add(new LabelBean(groupName, LabelBean.HEADER));
            } else
                data.add(new LabelBean("我是内容" + i, groupName));
        }
    }
}
