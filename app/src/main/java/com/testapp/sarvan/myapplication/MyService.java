package com.testapp.sarvan.myapplication;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class MyService extends Service {
    IBinder binder = new MyBinder();
    private List<String> resultList = new ArrayList<String>();
    private int counter = 1;

    public MyService() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        System.out.println("onStart called");
        if (intent != null) {
            System.out.println("intent.getflags" + intent.getFlags());
        }
        Toast.makeText(this, "Service Started", Toast.LENGTH_SHORT).show();
        addResultValues();
        System.out.println("resultList: " + resultList);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        System.out.println("onBind called");
        addResultValues();
        System.out.println("resultList: " + resultList);
        return binder;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Toast.makeText(this, "Service destroyed", Toast.LENGTH_SHORT).show();
    }

    public List<String> getWordList() {
        return resultList;
    }

    private void addResultValues() {
        Random random = new Random();
        List<String> input = Arrays.asList("Linux", "Android", "iPhone", "Windows7");
        resultList.add(input.get(random.nextInt(3)) + " " + counter++);
        if (counter == Integer.MAX_VALUE) {
            counter = 0;
        }
    }

    public class MyBinder extends Binder {
        MyService getService() {
            return MyService.this;
        }
    }
}
