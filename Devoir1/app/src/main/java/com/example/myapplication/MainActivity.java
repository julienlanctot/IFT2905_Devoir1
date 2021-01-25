package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    Button btn;
    TextView text;
    TextView text2;
    int tries = 1;
    int maxTries = 5;
    int compteur = 1;
    boolean started = false;
    boolean isYellow = false;
    boolean isGreen = false;
    long startime;
    long total = 0;
    ArrayList<Long> test = new ArrayList<Long>();

    Handler timer = new Handler();


    public static MainActivity activity = null;

    Runnable chronoR = new Runnable() {
        @Override
        public void run() {
            while(true)
            {

                try
                {
                    Thread.sleep(1);
                } catch (InterruptedException ex)
                {

                }

                if(isYellow)
                {
                    text2.setText(System.currentTimeMillis()-startime + "ms");
                }
            }

        }
    };
    Thread chronoT = new Thread(chronoR);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        activity = this;

        btn = findViewById(R.id.button3);
        text = findViewById(R.id.textView3);
        text2 = findViewById(R.id.textView4);

        btn.setOnClickListener(b1_listener);

        chronoT.start();
    }

    /**
     * Method used when user presses the button
     */




    View.OnClickListener b1_listener = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
        if(started)
        {
            btn.setText("Veuillez cliquer quand le bouton sera jaune");
            if(isYellow)
            {
                long elapseTime = System.currentTimeMillis() - startime;
                test.add(elapseTime);
                total = total + elapseTime;
              //  btn.setText(String.valueOf(elapseTime) + "ms");
                startime = System.currentTimeMillis();
                btn.setBackgroundColor(getResources().getColor(R.color.green));
                compteur++;
                btn.setText("");
                //text2.setText("succes ! vous avez clique en" +  elapseTime + "ms");

                isYellow = false;
                timer.removeCallbacks(myRunnable);
                if(compteur < 6){
                timer.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        text2.setText("");
                        start();
                    }
                }, 1500);}
                else{
                    compteur = 1;
                    AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
                    alertDialog.setTitle("5 essais complétés");
                    alertDialog.setMessage("Moyenne du temps de réaction " + total/5 + " ms");
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                    total = 0;
                                }
                            });
                    alertDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialogInterface) {
                            start();
                        }
                    });
                    alertDialog.show();
                    timer.removeCallbacks(myRunnable);
                    text2.setText("");
                    start();


                }
            }

            else
            {
                btn.setText("Trop vite!");
                btn.setBackgroundColor(getResources().getColor(R.color.red));
                btn.setClickable(false);
                timer.removeCallbacks(myRunnable);
                timer.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        start();
                    }
                }, 1500);
            }
        }
        else
        {
            start();
        }
    }
    };
    public void start()
    {
        timer = new Handler();
        btn.setClickable(true);
        text.setVisibility(View.VISIBLE);
        text2.setVisibility(View.VISIBLE);
        text.setText("Essai " + compteur + " de 5");
        started = true;
        int delay = (int) (new Random().nextDouble() * 7000) + 3000;
        Button btn = (Button) findViewById(R.id.button3);
        btn.setText(getResources().getText(R.string.ingame_msg));
        btn.setBackgroundColor(getResources().getColor(R.color.gray));
        timer.postDelayed(myRunnable, delay);
    }


    Runnable myRunnable = new Runnable() {
        @Override
        public void run() {
            isYellow = true;
            btn.setBackgroundColor(getResources().getColor(R.color.yellow));
            MainActivity.activity.startime = System.currentTimeMillis();
            }
    };
}