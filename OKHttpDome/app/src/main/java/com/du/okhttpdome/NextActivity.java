package com.du.okhttpdome;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by dllo on 16/10/18.
 */
public class NextActivity extends AppCompatActivity {
    private String Url = "http://apis.baidu.com/3023/imei/applemobile?imei=359310067992586";//号码归属地
    private String getUrl = "http://c.3g.163.com/nc/article/list/T1348648517839/0-20.html";
    private String postUrl = "http://appserver.jnwtv.com:8080/jnwtv-client/movie/getmoviedetail";
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
    private OkHttpClient okHttpClient;
    private TextView tv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_next);
        tv = (TextView) findViewById(R.id.next_tv);
        //初始化
        okHttpClient = new OkHttpClient();
        //同步get请求
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                douSyncGet();
//
//            }
//        }).start();
        //异步请求
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                doAsyncTaskGet();
//
//            }
//        }).start();
        //post请求
        new Thread(new Runnable() {
            @Override
            public void run() {
                doAsyncTaskPost();

            }
        }).start();

    }








    public void getGet(){
        Request.Builder builder = new Request.Builder();
        Request request = builder.url(getUrl).build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String str = response.body().string();
                tv.post(new Runnable() {
                    @Override
                    public void run() {
                        Log.d("NextActivity", str);
                    }
                });
            }
        });


    }


































    /**
     *
     */
    private void doAsyncTaskPost() {
        //post请求需要参数 所以要用FormBody.builder构造请求参数
        FormBody.Builder builder = new FormBody.Builder();
        FormBody body = builder.add(key1, value1).add(key2, value2).add(key3, value3).add(key4, value4).add(key5, value5).build();
        //利用请求参数 设置 请求
        Request.Builder rb = new Request.Builder();
        Request request = rb.url(postUrl).post(body).build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
//                Log.d("MainActivity", "response:" + response.body().string());
                tv.setText(response.body().string());
            }
        });

    }

    /**
     * 异步get请求
     */
    private void doAsyncTaskGet() {
        Request.Builder builder = new Request.Builder();
        Request request = builder.url(getUrl).build();
        okhttp3.Call call = okHttpClient.newCall(request);
        //异步请求        (回传)队列
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }


            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //响应信息(单个实体的响应状态的描述信息)
//                String str = response.message();
//                Log.d("xxx", str);
                final String str = response.body().string();
                tv.post(new Runnable() {
                    @Override
                    public void run() {
                        tv.setText(str);
                    }
                });
            }
        });


    }

    /**
     * 同步get请求
     */
    private void douSyncGet() {
        //请求构造器
        Request.Builder builder = new Request.Builder();
        //构建网站
        Request request = builder.url(getUrl).build();
        try {
            //客服端发送一次请求
            Response response = okHttpClient.newCall(request).execute();
            //判断请求是否响应
            if (response.isSuccessful()) {
                final String string = response.body().string();
                tv.post(new Runnable() {
                    @Override
                    public void run() {
                        tv.setText(string);
                    }
                });
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
