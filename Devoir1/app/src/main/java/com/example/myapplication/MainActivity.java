package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    Button b1;
    Handler timer;
    TextView t1;

    boolean is_invisible;
    boolean is_bright;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        is_invisible = true;
        is_bright = true;
        b1 = findViewById(R.id.button1);
        t1 = findViewById(R.id.textView1);

        timer = new Handler();
        b1.setOnClickListener(b1_listener);


    }



    Runnable timer_listener = new Runnable() {
        @Override
        public void run() {
            if(is_bright){
                b1.setBackgroundColor(Color.BLUE);
                is_bright = false;
            }else{
                b1.setBackgroundColor(Color.RED);
                is_bright = true;
            }

            timer.postDelayed(timer_listener, 2000);
        }
    };

    View.OnClickListener b1_listener = new View.OnClickListener(){
        @Override
        public void onClick(View v) {

            if(is_invisible){
                t1.setVisibility(View.VISIBLE);
                b1.setBackgroundColor(Color.RED);
                is_invisible = false;
            }
            timer_listener.run();
        }
    };



}