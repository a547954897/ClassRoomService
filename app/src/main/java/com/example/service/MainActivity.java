package com.example.service;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Intent intent;

    Button playM;
    Button pauseM;
    Button stopM;
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
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
      //  stopService(intent);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.play_muisc:
                Log.d("test11", "onClick: play");
                playMuisic();
                break;
            case R.id.pause_muisc:
               pauseMuisic();
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
}
