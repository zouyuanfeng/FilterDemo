package com.itzyf.filterdemo;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private FilterAdapter adapter;
    private List<LabelBean> data;
    private TextView textView;
    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        textView = (TextView) findViewById(R.id.text);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        initData();
        recyclerView.setAdapter(adapter = new FilterAdapter(this, data));
        GridLayoutManager layoutManager = new GridLayoutManager(this, 3);
        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int i) {
                if (data.get(i).getType() == LabelBean.HEADER)
                    return 3;//如果为header，把三格全占满
                else return 1;
            }
        });
        recyclerView.setLayoutManager(layoutManager);
        adapter.setOnCheckListener(item -> {
            Map<String, LabelBean> checkLabel = adapter.getCheckLabel();
            StringBuilder text = new StringBuilder();
            for (Map.Entry<String, LabelBean> label : checkLabel.entrySet()) {
                text.append(label.getKey()).append(":").append(label.getValue().getLabel()).append("\n");
            }
            textView.setText(text.toString());
        });
    }

    private void initData() {
        data = new ArrayList<>();
        String groupName = "";
        for (int i = 0; i < 50; i++) {
            if (i % 10 == 0) {
                groupName = "标题" + (i / 10 + 1);
                data.add(new LabelBean(groupName, LabelBean.HEADER));
            } else
                data.add(new LabelBean("内容" + i, groupName));
        }
    }

    public void onReset(View view) {
        adapter.clearCheck();
    }

    public void onComplete(View view) {
        drawerLayout.closeDrawers();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.actiong_filter) {
            if (drawerLayout.isDrawerOpen(Gravity.END))
                drawerLayout.closeDrawers();
            else
                drawerLayout.openDrawer(Gravity.END);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
