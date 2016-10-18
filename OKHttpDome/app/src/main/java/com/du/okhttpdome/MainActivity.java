package com.du.okhttpdome;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    /**
     * 网络编程
     * HttpURLConnection
     * 运行在线程中 handler传回主线程
     * 扩展Volley分装了线程池和handler 适用于轻量级频繁的网络请求,应用于展示类,购物类
     * OKHttp:可同步可异步,氪上传体积大的数据, 应用:和服务器产生体积大的数据交互
     * <p/>
     * 同步:请求数据主线程会一直等到有数据传回才继续
     * 异步:发出网络请求在子线程中异步执行,回调机制:数据请求成功通过回调方式传回
     */
    private TextView textView;
    private OkHttpClient client;
    private String theUrl = "http://c.3g.163.com/nc/article/list/T1348648517839/0-20.html";
    private String PostUrl = "http://appserver.jnwtv.com:8080/jnwtv-client/movie/getmoviedetail";
    String key1 = "account";
    String value1 = "26690576370";
    String key2 = "episodeNo";
    String value2 = "1";
    String key3 = "mId";
    String value3 = "1193";
    String key4 = "token";
    String value4 = "2016101715493688672507925614387226690576370";
    String key5 = "piId";
    String value5 = "10037";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView) findViewById(R.id.main_tv);
        /**
         * OkHttp3
         * 分析:
         * 必要的对象
         * 1.OkHttpClient:处理网络请求(单利)
         *
         * 2.Request 请求内容的设置 通过Builder类构造 如:设置请求网址 .url 设置请求参数 .post 最后通过.build完成构造
         * 3.Response 网络请求的响应 isSuccessful方法:true和false
         * response.body.String()获取请求数据
         */
        //1.初始化处理网络请求
        client = new OkHttpClient();
        //同步get请求
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                douSyncGet();
//
//            }
//        }).start();
        //异步get请求
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                doAsyncGet();
//            }
//        }).start();
        //异步post请求
        doAsyncPost();
    }

    /**
     * 异步Post请求
     */
    private void doAsyncPost() {
        //post请求:需要请求参数
        //利用FormBuilder构造 请求参数
        FormBody.Builder builder = new FormBody.Builder();
        RequestBody body = builder.add(key1, value1).add(key2, value2).add(key3, value3).add(key4, value4).add(key5, value5).build();
        //利用请求参数 设置 请求
        Request.Builder rb = new Request.Builder();
        Request request = rb.url(PostUrl).post(body).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d("MainActivity", "response:" + response.body().string());
            }
        });

    }

    private void douSyncGet() {
        //2.
        Request.Builder builder = new Request.Builder();
        Request request = builder.url(theUrl).build();
        //3.okHttp客服端利用 请求 发出一次呼叫
        //execute同步安排此次任务, 会获得到一个响应
//        Call call = client.newCall(request);
//        Response re = call.execute();
        try {
            Response response = client.newCall(request).execute();
            //4.处理结果
            if (response.isSuccessful()) {
                final String str = response.body().string();
                //子线程中刷新UI界面
                //1.利用Handler
                //2.post方法
                textView.post(new Runnable() {
                    @Override
                    public void run() {
                        textView.setText(str);

                    }
                });
            } else {
                Log.d("MainActivity", "请求失败");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 异步get请求
     */
    private void doAsyncGet() {
        //1.初始化客户端
        //2.利用Builder类构造请求
        Request request = new Request.Builder().url(theUrl).build();
        //3.
        Call call = client.newCall(request);
        //4.异步请求
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //响应的信息
//                String str = response.message();
                final String str = response.body().string();
                textView.post(new Runnable() {
                    @Override
                    public void run() {
//                        textView.setText(str);
                        Log.d("MainActivity", str);
                    }
                });
            }
        });
    }


}
