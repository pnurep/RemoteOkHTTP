package com.example.gold.remoteokhttp;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //OKHTTP는 네트워크에 접근하기 위해서 새로운 Thread를 생성해서 처리해야한다
        //1. Thread생성방법 - AsyncTask
        new AsyncTask<Void, Void, Void>(){
            @Override
            protected Void doInBackground(Void... params) {
                try {
                    String result = getData("http://daum.net");
                    Log.i("OKHTTP","result = " + result);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }
        }.execute();


        //2. new Thread
        new Thread(){
            @Override
            public void run() {
                try {
                    String result = getData("http://google.com");
                    Log.i("OKHTTP","result = " + result);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    String getData(String url) throws IOException {

        //1. okhttp인스턴스생성
        OkHttpClient client = new OkHttpClient();

        //2.request객체 생성
        Request request = new Request.Builder()
                .url(url)
                .build();

        //3. client인스턴스에 request를 담아 보낸다
        Response response = client.newCall(request).execute(); // ->서버측으로 요청
        return response.body().string();
    }

}
