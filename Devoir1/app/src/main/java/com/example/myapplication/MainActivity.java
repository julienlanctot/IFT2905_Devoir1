package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {


    private int tries = 1;
    private int maxTries = 5;
    private boolean started = false;
    private boolean isYellow = false;
    private long startime;

    private Timer timer = new Timer();
    public static MainActivity activity = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        activity = this;

        Button btn = (Button) findViewById(R.id.button3);
        btn.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                MainActivity.activity.clickHandler();
            }
        });
    }

    /**
     * Method used when user presses the button
     */
    public void clickHandler()
    {
        Button btn = (Button) findViewById(R.id.button3);
        if(this.started)
        {
            if(this.isYellow)
            {
                long elaspeTime = System.currentTimeMillis() - this.startime;
                btn.setText(String.valueOf(elaspeTime) + "ms");
                this.startime = System.currentTimeMillis();
                btn.setBackgroundColor(getResources().getColor(R.color.green));
            }
            else
            {
                btn.setText("Trop vite!");
                btn.setBackgroundColor(getResources().getColor(R.color.red));
                this.timer.cancel();
                this.start();
            }
        }
        else
        {
            this.start();
        }
    }

    public void start()
    {
        this.started = true;
        int delay = (int) (new Random().nextDouble() * 7000) + 3000;
        Button btn = (Button) findViewById(R.id.button3);
        btn.setText(getResources().getText(R.string.default_msg));
        this.timer.schedule(new TimerTask() {
            @Override
            public void run() {
                MainActivity.activity.isYellow = true;
                btn.setBackgroundColor(getResources().getColor(R.color.yellow));
                btn.setText(String.valueOf(delay));
                MainActivity.activity.startime = System.currentTimeMillis();
            }
        },delay);
    }
}