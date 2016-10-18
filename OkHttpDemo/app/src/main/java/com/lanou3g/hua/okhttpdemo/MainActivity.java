package com.lanou3g.hua.okhttpdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

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

    private TextView textView;
    private OkHttpClient okHttpClient;
    private String postUrl="http://appserver.jnwtv.com:8080/jnwtv-client/movie/getmoviedetail";
    private  String getUrl = "http://c.3g.163.com/nc/article/list/T1348648517839/0-20.html";

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
         * 1利用okHttp3发送骑牛头: 验证手机号码归属地
         * apistore 申请appkey
         * 3.自己百度或者按自己的理解试着封装单利
         */


        /**
         * okHttp3
         * 分析:
         * 必要对象
         * 1.okHttpClient:处理网络请求(单利)
         * 2.Request 请求内容的设置
         *  通过Builder类构造
         *  如: 设置请求网址  .url
         *     设置请求参数  .post
         *     最后通过.build 完成构造
         * 3.Re是ponse 网络请求的相应
         *  isSuccessful方法: true和false
         *  response.body.string() 获取请求的数据
         */

        // 1.创建请求对象
        okHttpClient = new OkHttpClient();

        // 异步
        new Thread(new Runnable() {
            @Override
            public void run() {
             //  daAsyncGet();
            }
        }).start();


        // 同步 get 请求
        new Thread(new Runnable() {
            @Override
            public void run() {
               // doSyncGet();
            }
        }).start();



        // 同步get请求




        // 2
//        Request.Builder builder = new  Request.Builder();
//        Request request = builder.url(getUrl).build();




            // 异步post请求
       // doAsyncPost();

    }

    private void doAsyncPost() {
        // post请求:需要请求参数
        FormBody.Builder builder = new FormBody.Builder();
        RequestBody body =
                builder.add(key1,value1).add(key2,value2).add(key3,value3).add(key4,value4).add(key5,value5).build();
        // 利用请求参数设置请求
        Request.Builder rb = new Request.Builder();
        Request request = rb.url(postUrl).post(body).build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d("MainActivity", "response:" + response);
                final  String str = response.body().string();
                textView.post(new Runnable() {
                    @Override
                    public void run() {
                        textView.setText(str);
                    }
                });
            }
        });

    }


    /**
     * 同步请求
     */

    private void doSyncGet() {
        // 2.创建Builer
        Request.Builder builder = new Request.Builder();
        Request request = builder.url(getUrl).build();
        // 3.okHttp客户端利用请求发出一次呼叫
        // excute 同步安排此次任务 会获得到一个响应
     //   Call call = okHttpClient.newCall(request);

        try {
            Response response = okHttpClient.newCall(request).execute();
            if (response.isSuccessful()){
                final String str = response.body().string();
                // 子线程中刷新UI界面
                // 1. 利用handler
                // 2 . post方法
                textView.post(new Runnable() {
                    @Override
                    public void run() {
                        textView.setText(str);
                    }
                });

                Log.d("MainActivity", str);
            }else {
                Log.d("MainActivity", "请求失败");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    //    4.处理结果


    }

    /**
     * 异步请求
     */

    private void daAsyncGet(){
        // 1.初始化客户端
        // 2.利用Builder类构造请求
        Request request = new Request.Builder().url(getUrl).build();
        // 3.
       Call call = okHttpClient.newCall(request);
        // 4.异步请求
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d("MainActivity", "response:" + response);

                    final String str =  response.body().string();
                    textView.post(new Runnable() {
                        @Override
                        public void run() {
                            textView.setText(str);
                        }
                    });
            }
        });

    }


}
