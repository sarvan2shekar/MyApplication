package com.testapp.sarvan.myapplication;

import android.app.ListActivity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class BindingActivity extends ListActivity
        implements ServiceConnection {
    private MyService s;
    private ArrayAdapter<String> adapter;
    private List<String> wordList = new ArrayList<>();
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        wordList = new ArrayList<String>();
        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,
                android.R.id.text1, wordList);
        setListAdapter(adapter);
        setContentView(R.layout.content_binding);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent intent = new Intent(this, MyService.class);
        bindService(intent, this, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unbindService(this);
    }

    @Override
    public void onServiceConnected(ComponentName name, IBinder binder) {
        MyService.MyBinder b = (MyService.MyBinder) binder;
        s = b.getService();
        Toast.makeText(BindingActivity.this, "Connected", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {
        s = null;
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.updateList:
                if (s != null) {
                    Toast.makeText(this, "Number of elements" + s.getWordList().size(),
                            Toast.LENGTH_SHORT).show();
                    wordList.clear();
                    wordList.addAll(s.getWordList());
                    System.out.println("wordList: " + wordList);
                    adapter.notifyDataSetChanged();
                }
                break;
            case R.id.triggerServiceUpdate:
                Intent service = new Intent(getApplicationContext(), MyService.class);
                getApplicationContext().startService(service);
                break;
        }
    }
}
