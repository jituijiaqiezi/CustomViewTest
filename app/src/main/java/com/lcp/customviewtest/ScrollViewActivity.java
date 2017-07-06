package com.lcp.customviewtest;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ScrollView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

public class ScrollViewActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ScrollView scrollView;
    List<Integer> datas;
    CustomButton btnUp, btnDown;
    Handler handler;
    Runnable upRunnable, downRunnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scroll_view);
        init();
    }

    private void init() {
        scrollView = (ScrollView) findViewById(R.id.scrollView);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        datas = new ArrayList<>();
        for (int i = 0; i < 200; i++) {
            datas.add(i);
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new DataAdapter(datas));
        btnUp = (CustomButton) findViewById(R.id.btn_up);
        btnDown = (CustomButton) findViewById(R.id.btn_down);
        handler = new Handler();
        upRunnable = new Runnable() {
            @Override
            public void run() {
                scrollView.smoothScrollBy(0, 1);
                handler.postDelayed(upRunnable, 30);
            }
        };
        downRunnable = new Runnable() {
            @Override
            public void run() {
                scrollView.smoothScrollBy(0, -1);
                handler.postDelayed(downRunnable, 30);
            }
        };

        btnUp.setOnCustomTouchListener(new CustomButton.OnCustomTouchListener() {
            @Override
            public void onTouch() {
                handler.post(upRunnable);
            }

            @Override
            public void onCancel() {
                handler.removeCallbacks(upRunnable);
            }
        });
        btnDown.setOnCustomTouchListener(new CustomButton.OnCustomTouchListener() {
            @Override
            public void onTouch() {
                handler.post(downRunnable);
            }

            @Override
            public void onCancel() {
                handler.removeCallbacks(downRunnable);
            }
        });
    }

    class DataAdapter extends BaseQuickAdapter<Integer, BaseViewHolder> {
        public DataAdapter(List<Integer> datas) {
            super(R.layout.item_data, datas);
        }

        @Override
        protected void convert(BaseViewHolder helper, Integer item) {
            helper.setText(R.id.text, String.valueOf(item));
        }
    }
}
