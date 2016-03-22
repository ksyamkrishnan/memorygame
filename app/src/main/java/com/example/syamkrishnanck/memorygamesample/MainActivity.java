package com.example.syamkrishnanck.memorygamesample;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;


public class MainActivity extends Activity implements View.OnClickListener {

    private CountDownTimer countDownTimer;
    private boolean timerHasStarted = false;
    private Button startB;
    public TextView text;
    private final long startTime = 15 * 1000;
    private final long interval = 1 * 1000;
    List<String> imageList = new ArrayList<>();
    ImageListAdapter imageListAdapter;
    GridView imageGridView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startB = (Button) this.findViewById(R.id.button);
        startB.setOnClickListener(this);
        text = (TextView) this.findViewById(R.id.timer);
        countDownTimer = new MyCountDownTimer(startTime, interval);
        imageListAdapter = new ImageListAdapter(this, imageList);
        imageGridView = (GridView) findViewById(R.id.imageGrid);
        imageGridView.setAdapter(imageListAdapter);
        text.setText(text.getText() + String.valueOf(startTime / 1000));
        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... params) {
                FlickerClient client = ServiceGenerator.createService();
                refreshFeed();
                return null;
            }
        }.execute();
        imageGridView.setVisibility(View.INVISIBLE);
        imageGridView.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onClick(View v) {
        if (!timerHasStarted) {
            countDownTimer.start();
            timerHasStarted = true;
            imageGridView.setVisibility(View.VISIBLE);
            startB.setText("STOP");
        } else {
            new AsyncTask<Void, Void, Void>() {

                @Override
                protected Void doInBackground(Void... params) {
                    FlickerClient client = ServiceGenerator.createService();
                    refreshFeed();
                    return null;
                }
            }.execute();
            imageGridView.setVisibility(View.INVISIBLE);
            countDownTimer.cancel();
            timerHasStarted = false;
            startB.setText("RESTART");
        }
    }

    public class MyCountDownTimer extends CountDownTimer {
        public MyCountDownTimer(long startTime, long interval) {
            super(startTime, interval);
        }

        @Override
        public void onFinish() {
           Intent intent = new Intent(getApplicationContext(), QueryActivity.class);
            intent.putStringArrayListExtra("image", (ArrayList<String>) imageList);
            startActivity(intent);
        }

        @Override
        public void onTick(long millisUntilFinished) {

            text.setText("" + millisUntilFinished / 1000);
        }
    }

    public void refreshFeed() {
        Call<String> call = ServiceGenerator.createService().feeds();
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Response<String> response, Retrofit retrofit) {
                try {
                    String res = response.body();
                    int start = res.indexOf("{");
                    int end = res.lastIndexOf("}");
                    String jsonRes = res.substring(start, end + 1);
                    GsonBuilder gsonbuilder = new GsonBuilder();
                    Gson gson = gsonbuilder.create();
                    List<Feed> feedList = null;
                        JSONObject jsonObj = new JSONObject(jsonRes);
                        Type listType = new TypeToken<List<Feed>>() {
                        }.getType();
                        feedList = gson.fromJson(jsonObj.getString("items"), listType);
                        resetAdapter(AppUtil.getUrlList(feedList));
                }catch (Exception e){
                }
            }
            @Override
            public void onFailure(Throwable t) {
            }
        });
        try {
            Call<String> doCall = call.clone();
            doCall.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void resetAdapter(List<String> urlList) {
        imageList.clear();
        imageList.addAll(urlList);
        imageListAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

}