package ru.mirea.tsitserid.looper;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import java.util.concurrent.ThreadLocalRandom;

public class MyLooper extends Thread{
    public Handler mHandler;
    private Handler mainHandler;
    int count;
    public MyLooper(Handler mainThreadHandler) {
        mainHandler =mainThreadHandler;
    }
    public void run() {
        Log.d("MyLooper", "run");
        Looper.prepare();
        count = ThreadLocalRandom.current().nextInt(18,40);
        mHandler = new Handler(Looper.myLooper()) {
            public void handleMessage(Message msg) {
                String data = msg.getData().getString("KEY");
                Log.d("MyLooper get message: ", data);
                count = ThreadLocalRandom.current().nextInt(18,40);
                Message message = new Message();
                Bundle bundle = new Bundle();
                bundle.putString("result", String.format("Возраст разработчика %s %d лет ",data , count));
                message.setData(bundle);
                mainHandler.sendMessage(message);
            }
        };
        Looper.loop();
    }
}