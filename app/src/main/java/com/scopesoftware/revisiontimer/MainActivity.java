package com.scopesoftware.revisiontimer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.Random;


public class MainActivity extends Activity {
    TextView studyTimeTitleText;
    TextView breakTimeTitleText;
    TextView studyTimeText;
    TextView breakTimeText;

    SeekBar studyTimeBar;
    SeekBar breakTimeBar;

    Button studyButton;
    int studyTime;
    int breakTime;

    Random ra;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ra = new Random();

        studyTimeBar = (SeekBar) findViewById(R.id.studyTimeSeekBar);
        breakTimeBar = (SeekBar) findViewById(R.id.breakTimeSeekBar);
        studyTimeTitleText = (TextView) findViewById(R.id.studyTitleTextView);
        breakTimeTitleText = (TextView) findViewById(R.id.breakTitleTextView);
        studyTimeText = (TextView) findViewById(R.id.studyTimeTextView);
        breakTimeText = (TextView) findViewById(R.id.breakTimeTextView);
        studyButton = (Button) findViewById(R.id.startButton);

        studyTimeBar.setMax(115);
        breakTimeBar.setMax(55);

        studyTime = 5;
        breakTime = 5;

        studyTimeBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                i = 5 + (i/5)*5;
                studyTimeText.setText("" + i + "m");
                studyTime = i;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        breakTimeBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                i = 5 + (i/5)*5;
                breakTimeText.setText("" + i + "m");
                breakTime = i;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        studyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             startStudying(studyTime, breakTime);
            }
        });

    }

    public void startStudying(int studyTime, int breakTime){
        Intent intent  = new Intent(this, StudyingActivity.class);
        intent.putExtra("studyTime", studyTime);
        intent.putExtra("breakTime", breakTime);
        startActivity(intent);
    }


}
