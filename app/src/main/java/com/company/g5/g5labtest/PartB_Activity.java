package com.company.g5.g5labtest;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

public class PartB_Activity extends AppCompatActivity implements SensorEventListener {
    Sensor aAccSensor;
    SensorManager aSensorM;
    float accFactor = 6.0f;
    float ballX;
    float ballY;


    public void imgMoving(){
        final int SLEEP = 250;
        final int MAX = 75;
        final int BASE = -MAX/2;
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(!Thread.currentThread().isInterrupted()){
                    try{
                        Thread.sleep(SLEEP);
                        final ImageView blackBall = (ImageView) findViewById(R.id.blackBall);
                        if(blackBall==null){
                            break;
                        }
                        blackBall.post(new Runnable() {
                            @Override
                            public void run() {
                                float diffX = (BASE+(int)(Math.random()*MAX) + blackBall.getX());
                                float diffY = (BASE+(int)(Math.random()*MAX) + blackBall.getY());

                                if(diffX < 0){
                                    blackBall.setX(0);
                                }else if(diffX > findViewById(R.id.layout).getWidth()-blackBall.getWidth()){
                                    blackBall.setX(findViewById(R.id.layout).getWidth()-blackBall.getWidth());
                                }else{
                                    blackBall.setX(diffX);
                                }

                                if(diffY < 0){
                                    blackBall.setY(0);
                                }else if(diffY > findViewById(R.id.layout).getHeight()-blackBall.getHeight()){
                                    blackBall.setY(findViewById(R.id.layout).getHeight()-blackBall.getHeight());
                                }else{
                                    blackBall.setY(diffY);
                                }
                            }
                        });
                    }catch(InterruptedException ex){

                    }
                }

            }
        }).start();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_part_b_);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        imgMoving();
        aSensorM = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        if (aSensorM.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) != null){
            aAccSensor = aSensorM.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        }
        else {
            aSensorM = null;
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        ImageView tarBall = (ImageView) findViewById(R.id.tarBall);
        if(tarBall != null){
            float newX = tarBall.getX() - accFactor * event.values[0];
            float newY = tarBall.getY() + accFactor * event.values[1];
            if(newX < 0){
                newX = 0;
            }else if(newX > findViewById(R.id.layout).getWidth()-tarBall.getWidth()){
                newX = findViewById(R.id.layout).getWidth()-tarBall.getWidth();
            }
            if(newY < 0){
                newY = 0;
            }else if(newY > findViewById(R.id.layout).getHeight()-tarBall.getHeight()){
                newY = findViewById(R.id.layout).getHeight()-tarBall.getHeight();
            }
            tarBall.setX(newX);
            tarBall.setY(newY);
            ballX = newX;
            ballY = newY;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        aSensorM.registerListener(this, aAccSensor, SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    protected void onPause() {
        super.onPause();
        aSensorM.unregisterListener(this);
    }

}
