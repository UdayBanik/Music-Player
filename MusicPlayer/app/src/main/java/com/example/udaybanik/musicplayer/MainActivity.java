package com.example.udaybanik.musicplayer;

import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.TimeUnit;
import java.util.logging.Handler;

public class MainActivity extends AppCompatActivity {

    private Button b1,b2,b3,b4;
    private MediaPlayer mediaPlayer;
    private double startTime = 0;
    private double finalTime = 0;

    private android.os.Handler handler = new android.os.Handler();
    private int forwardTime = 5000;
    private int backwardTime = 5000;
    private SeekBar seekBar;
    private TextView tv1,tv2,tv3;

    private static int oneTimeOnly = 0;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        b1 = (Button) findViewById(R.id.buttonforward);
        b2 = (Button) findViewById(R.id.buttonrewind);
        b3 = (Button) findViewById(R.id.buttonplay);
        b4 = (Button) findViewById(R.id.buttonpause);

        tv1 = (TextView) findViewById(R.id.textView2);
        tv2 = (TextView) findViewById(R.id.textView3);
        tv3 = (TextView) findViewById(R.id.textView4);
        tv2.setText("ok.mp3");

        mediaPlayer = MediaPlayer.create(this,R.raw.ok);
        seekBar = (SeekBar) findViewById(R.id.seekBar);
        seekBar.setClickable(false);
        b4.setEnabled(false);

        b3.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(getApplicationContext(), "ok.mp3", Toast.LENGTH_SHORT).show();

                mediaPlayer.start();
                finalTime = mediaPlayer.getDuration();
                startTime = mediaPlayer.getCurrentPosition();

                if (oneTimeOnly == 0) {
                    seekBar.setMax((int) finalTime);
                    oneTimeOnly = 1;

                }


                tv3.setText(String.format("%d min %d sec",
                        TimeUnit.MILLISECONDS.toMinutes((long) finalTime),
                        TimeUnit.MILLISECONDS.toSeconds((long) finalTime) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long) finalTime))));


                tv1.setText(String.format("%d min %d sec",
                        TimeUnit.MILLISECONDS.toMinutes((long) startTime),
                        TimeUnit.MILLISECONDS.toSeconds((long) startTime) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long) startTime))));


                seekBar.setProgress((int) startTime);
                handler.postDelayed(UpdateSongTime, 100);
                b4.setEnabled(true);
                b3.setEnabled(false);


            }


        }));

        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view ) {
                Toast.makeText(getApplicationContext(), "Pausing ringtone", Toast.LENGTH_LONG).show();
                mediaPlayer.pause();
                b4.setEnabled(false);
                b3.setEnabled(true);
            }
        });

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int temp = (int) startTime;

                if (temp + forwardTime <= finalTime) {
                    startTime += forwardTime;
                    mediaPlayer.seekTo((int) startTime);
                    Toast.makeText(getApplicationContext(), "You have jump forward 5 seconds", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Can't jump forward 5 seconds", Toast.LENGTH_SHORT).show();
                }
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int temp = (int) startTime;

                if (temp - backwardTime > 0) {
                    startTime -= backwardTime;
                    mediaPlayer.seekTo((int) startTime);
                    Toast.makeText(getApplicationContext(), "You have jump backward 5 seconds", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Can't jump backward 5 seconds", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }





    private Runnable UpdateSongTime = new Runnable() {
        @Override
        public void run() {
            startTime = mediaPlayer.getCurrentPosition();
            tv1.setText(String.format("%d min %d sec",
                    TimeUnit.MILLISECONDS.toMinutes((long) startTime),
                    TimeUnit.MILLISECONDS.toSeconds((long) startTime) -
                            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long) startTime))
            ));

            seekBar.setProgress((int) startTime);
            handler.postDelayed(this, 100);
        }
    };
}










