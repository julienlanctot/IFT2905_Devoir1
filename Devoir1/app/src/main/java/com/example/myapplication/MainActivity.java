package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    Button btn;
    TextView text;
    TextView text2;
    int counter = 1;
    boolean started = false;
    boolean isYellow = false;
    long startime;
    long total = 0;

    /**
     * Method that allows the application to run
     */
    Handler timer = new Handler();
    Runnable myRunnable = new Runnable() {
        @Override
        public void run() {
            isYellow = true;
            btn.setBackgroundColor(getResources().getColor(R.color.yellow));
            startime = System.currentTimeMillis();
        }
    };

    /**
     * Method that allows the chronometer to start as the application is running
     */
    Runnable chronoR = new Runnable() {
        @Override
        public void run() {
            while(true)
            {
                try
                {
                    Thread.sleep(1);
                } catch (InterruptedException ex) {}
                if(isYellow)
                    text2.setText(System.currentTimeMillis()-startime + "ms");
            }

        }
    };
    Thread chronoT = new Thread(chronoR);

    /**
     * Method that intialize the application activity
     * @param savedInstanceState Save the instances of the application activity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn = findViewById(R.id.button);
        text = findViewById(R.id.textView1);
        text2 = findViewById(R.id.textView2);

        btn.setOnClickListener(b1_listener);

        chronoT.start();
    }

    /**
     * Method that controls the interface of the application such as the buttons and the dialogs
     * when the user is running the application
     * @param v Graphical user interface
     */
    View.OnClickListener b1_listener = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            if(started)
            {
                btn.setText("Veuillez cliquer quand le bouton sera jaune");
                if(isYellow)
                {
                    long elapseTime = System.currentTimeMillis() - startime;
                    total += elapseTime;
                    btn.setBackgroundColor(getResources().getColor(R.color.green));
                    counter++;
                    btn.setText(getResources().getString(R.string.success));

                    isYellow = false;
                    timer.removeCallbacks(myRunnable);
                    if(counter < 6){
                        timer.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                text2.setText("");
                                start();
                            }
                        }, 1500);
                    }
                    else
                    {
                        counter = 1;
                        AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
                        alertDialog.setTitle("5 essais complétés");
                        alertDialog.setMessage("Moyenne du temps de réaction " + total/5 + " ms");
                        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                        alertDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                            @Override
                            public void onDismiss(DialogInterface dialogInterface) {
                               reset();
                            }
                        });
                        alertDialog.show();
                        timer.removeCallbacks(myRunnable);
                        text2.setText("");
                    }
                }
                else
                {
                    btn.setText(getResources().getString(R.string.too_fast));
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

    /**
     * Method used when launching a new try
     */
    public void start()
    {
        timer = new Handler();
        btn.setClickable(true);
        text.setVisibility(View.VISIBLE);
        text2.setVisibility(View.VISIBLE);
        text.setText("Essai " + counter + " de 5");
        started = true;
        int delay = (int) (new Random().nextDouble() * 7000) + 3000;
        btn.setText(getResources().getText(R.string.ingame_msg));
        btn.setBackgroundColor(getResources().getColor(R.color.gray));
        timer.postDelayed(myRunnable, delay);
    }

    /**
     * Method to reset the reaction test
     */
    public void reset()
    {
        total = 0;
        started = false;
        text.setVisibility(View.INVISIBLE);
        text2.setVisibility(View.INVISIBLE);
        btn.setText(getResources().getString(R.string.default_msg));
        btn.setBackgroundColor(getResources().getColor(R.color.gray));
    }



}