package com.scopesoftware.revisiontimer;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;

import android.os.Vibrator;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;


public class StudyingActivity extends Activity {
    TextView titleText;
    TextView timerText;
    TextView subTitleText;
    Button pauseButton;

    int studyTime, breakTime;
    int currentStudyTime, currentBreakTime;

    CountDownTimer cdt;

    boolean paused = false;
    long[] pattern = {0, 400, 500, 100, 200, 100, 0};
    Random ra = new Random();

    String[] breakMessages = {"Try doing some exercise, it will relieve stress.",
            "Have some healthy snacks to keep up your energy.",
            "Try some different methods of studying.",
            "Switch subjects if you find yourself bored.",
            "Study the most important information first.",
            "Try studying at different times of day.",
            "Teach someone what you're studying, this helps with consolidation.",
            "Notes aren't everything, make sure you practice with past exams.",
            "Remove yourself from distractions like phones and computers.",
            "Keep your study space well lit.",
            "Keep a notebook to record distracting thoughts for later.",
            "Consolidate information into short, detailed notes.",
            "Eat a good breakfast before studying.",
            "Review information the next day.",
            "Stay hydrated, preferably with water.",
            "Apply the information to something you're interested in."};



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_studying);

        Intent intent = getIntent();
        intent.getExtras();
        studyTime = 60 * intent.getIntExtra("studyTime", 5);
        breakTime = 60 * intent.getIntExtra("breakTime", 5);

        titleText = (TextView) findViewById(R.id.titleTextView);
        timerText = (TextView) findViewById(R.id.timeTextView);
        subTitleText = (TextView) findViewById(R.id.subTitleTextView);
        pauseButton = (Button) findViewById(R.id.pauseButton);

        currentStudyTime = studyTime;
        currentBreakTime = breakTime;

        pauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                paused = !paused;
                if (paused){
                    cdt.cancel();
                    pauseButton.setText("Start");
                }
                else{
                    pauseButton.setText("Pause");
                    studyState();
                }
            }
        });

        studyState();

    }

    public void studyState() {
        Vibrator vb = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        if(currentStudyTime == studyTime) {
            vb.vibrate(pattern, -1);
        }
        pauseButton.setVisibility(View.VISIBLE);
        titleText.setText("Next Break");
        subTitleText.setText("");
        currentBreakTime = breakTime;
        cdt = new CountDownTimer(currentStudyTime * 1000, 1000) {
            @Override
            public void onTick(long l) {
                String minutes = "" + currentStudyTime/60;
                String seconds = "" + currentStudyTime%60;
                if(seconds.length() < 2) seconds = "0" + seconds;

                timerText.setText(minutes + ":" + seconds);
                currentStudyTime -= 1;
            }

            @Override
            public void onFinish() {
                breakState();
            }
        }.start();
    }

    public void breakState(){
        Vibrator vb = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        vb.vibrate(pattern, -1);
        pauseButton.setVisibility(View.INVISIBLE);
        titleText.setText("Enjoy your break!");
        subTitleText.setText(breakMessages[ra.nextInt(breakMessages.length)]);
        currentStudyTime = studyTime;
        cdt = new CountDownTimer(currentBreakTime * 1000, 1000) {
            @Override
            public void onTick(long l) {
                String minutes = "" + currentBreakTime/60;
                String seconds = "" + currentBreakTime%60;
                if(seconds.length() < 2) seconds = "0" + seconds;

                timerText.setText(minutes + ":" + seconds);
                currentBreakTime -= 1;
            }

            @Override
            public void onFinish() {
                studyState();
            }
        }.start();
    }
}
