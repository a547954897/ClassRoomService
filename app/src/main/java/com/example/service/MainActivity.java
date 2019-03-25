package com.example.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    String TAG = "MainActivity";
    Intent intent;
    IntentFilter intentFilter;
    MyBroadCast myBroadCast;

    Button playM;
    Button pauseM;
    Button stopM;

    class MyBroadCast extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d(TAG, "onReceive:我收到了广播 ");
            sendNotification();


        }
        public void sendNotification(){
            Intent intent = new Intent(MainActivity.this,MainActivity.class);
            PendingIntent pi = PendingIntent.getActivity(MainActivity.this,0,intent,0);
            NotificationManager manager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);

            if (Build.VERSION.SDK_INT >= 26){
                NotificationChannel channel = new NotificationChannel("1","channel1",manager.IMPORTANCE_HIGH);
                manager.createNotificationChannel(channel);
            }

            Notification notification = new NotificationCompat.Builder(MainActivity.this,"1")
                    .setContentTitle("暂停中...")
                    .setContentText("点击返回继续播放")
                    .setContentIntent(pi)
                    .setSound(Settings.System.DEFAULT_ALARM_ALERT_URI)
                    .setDefaults(Notification.DEFAULT_ALL)
                    .setVibrate(new long[]{0,1000,1000,1000})
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setLargeIcon(BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher))
                    .setAutoCancel(true)
                    .build();
            manager.notify(1,notification);
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       // playM = findViewById(R.id.play);
      //  intent = new Intent(this,MyService.class);
        // startService(intent);

        initView();



    }

    public void initView(){
        playM = findViewById(R.id.play_muisc);
        pauseM = findViewById(R.id.pause_muisc);
        stopM = findViewById(R.id.stop_muisc);


        playM.setOnClickListener(this);
        pauseM.setOnClickListener(this);
        stopM.setOnClickListener(this);

        intentFilter = new IntentFilter();
        myBroadCast = new MyBroadCast();
    }

    public void sendBroad(){
        intentFilter.addAction("MainActivity.ACTION");
        registerReceiver(myBroadCast,intentFilter);

        intent = new Intent("MainActivity.ACTION");
        sendBroadcast(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(myBroadCast);
      //  stopService(intent);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.play_muisc:
                Log.d("test11", "onClick: play");
                playMuisic();
                pauseM.setEnabled(true);

                break;
            case R.id.pause_muisc:
               pauseMuisic();
               pauseM.setEnabled(false);
               sendBroad();
                break;
            case R.id.stop_muisc:
               stopMuisic();
                break;

        }
    }

    public void playMuisic(){
        Intent intent = new Intent(this,MyService.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("Key",MyService.Control.PLAY);
        intent.putExtras(bundle);
        startService(intent);
    }
    public void pauseMuisic(){
        Intent intent = new Intent(this,MyService.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("Key",MyService.Control.PAUSE);
        intent.putExtras(bundle);
        startService(intent);
    }
    public void stopMuisic(){
        Intent intent = new Intent(this,MyService.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("Key",MyService.Control.STOP);
        intent.putExtras(bundle);
        startService(intent);
    }

  /*  public void sendNotification(){
        //Intent intent = new Intent()
   //     PendingIntent pi = PendingIntent.getActivity(this,0,intent,0);
        NotificationManager manager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notification = new NotificationCompat.Builder(this,"default")
                .setContentTitle("this is title")
                .setContentText("this is text")
                //.setContentIntent(pi)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher))
                .build();
        manager.notify(1,notification);
    }*/

}
