package com.testapp.sarvan.myapplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private TextView tv1, tv2, tv3;
    private BroadcastReceiver br = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                String url = bundle.getString(MyIntentService.FILEPATH);
                int resultCode = bundle.getInt(MyIntentService.RESULT);
                if (resultCode == RESULT_OK) {
                    Toast.makeText(MainActivity.this,
                            "Download complete. Download URI: " + url,
                            Toast.LENGTH_LONG).show();
                    tv3.setText("Download done");
                } else {
                    Toast.makeText(MainActivity.this, "Download failed",
                            Toast.LENGTH_LONG).show();
                    tv3.setText("Download failed");
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv1 = (TextView) findViewById(R.id.tv1);
        tv2 = (TextView) findViewById(R.id.tv2);
        tv3 = (TextView) findViewById(R.id.tv3);
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(br, new IntentFilter(
                MyIntentService.NOTIFICATION));
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(br);
    }

    public void download(View view) {

        Intent intent = new Intent(this, MyIntentService.class);
        // add infos for the service which file to download and where to store
        intent.putExtra(MyIntentService.FILENAME, "index.html");
        intent.putExtra(MyIntentService.URL,
                "https://developer.android.com/reference/android/app/Service.html");
        startService(intent);
        tv3.setText("Service started");
    }

    public void startService(View view) {
        startService(new Intent(getBaseContext(), MyService.class));
    }

    // Method to stop the service
    public void stopService(View view) {
        stopService(new Intent(getBaseContext(), MyService.class));
    }

    public void bindService(View v) {
        Intent intent = new Intent(this, BindingActivity.class);
        startActivity(intent);
    }

    public void unbindService(View v) {
    }
}
