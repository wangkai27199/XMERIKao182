package com.example.dell.xmerikao182;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Toast;

import com.aspsine.irecyclerview.IRecyclerView;
import com.example.dell.xmerikao182.bean.InfoBean;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends Activity implements MyAdapter.onItemClickListener {

    private IRecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private List<InfoBean.ResultBean.DataBean> list = new ArrayList<InfoBean.ResultBean.DataBean>();
    private String url = "http://v.juhe.cn/toutiao/index?type=&key=f9a6248a6287cfefad0ac46d6581ee6b";
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0) {
                String result = (String) msg.obj;
                System.out.println("result = " + result);
                Gson gson = new Gson();
                InfoBean infoBean = gson.fromJson(result, InfoBean.class);
                List<InfoBean.ResultBean.DataBean> data = infoBean.result.data;
                System.out.println("data = " + data.size());
                list.addAll(data);
                if (list != null && list.size() > 0) {
                    MyAdapter adapter = new MyAdapter(list, MainActivity.this);

                    recyclerView.setIAdapter(adapter);
                    adapter.setOnItemClickListener(MainActivity.this);
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getData();

        recyclerView = (IRecyclerView) findViewById(R.id.recyclerView);

        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

    }

    private void getData() {
        new Thread() {
            @Override
            public void run() {
                super.run();
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder().url(url).build();
                try {
                    Response response = client.newCall(request).execute();
                    //System.out.println("response = " + response.body().string());
                    String str = response.body().string();
                    Message msg = Message.obtain();
                    msg.obj = str;
                    msg.what = 0;
                    handler.sendMessage(msg);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    @Override
    public void onItemClickListener(View view, int position) {
        Toast.makeText(this, list.get(position).title, Toast.LENGTH_SHORT).show();
    }
}
