package com.gis.soundrecorder;

import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnPlay, btnStop, btnRecord;
    private MediaRecorder mediaRecorder;
    private String filePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUp();

    }
    
    private void setUp() {
        
        filePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/recording.3gp";
        setUpView();
        
    }

    private void setUpView() {

        // find
        btnPlay = findViewById(R.id.btn_play);
        btnStop = findViewById(R.id.btn_stop);
        btnRecord = findViewById(R.id.btn_record);
        // setEnable
        btnPlay.setEnabled(false);
        btnStop.setEnabled(false);
        // setOnclickListen
        btnPlay.setOnClickListener(this);
        btnStop.setOnClickListener(this);
        btnRecord.setOnClickListener(this);

    }
    
    private void setUpMediaRecorder() {
        
        mediaRecorder = new MediaRecorder();
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mediaRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
        mediaRecorder.setOutputFile(filePath);
        
    }

    @Override
    public void onClick(View v) {
        
        switch (v.getId()) {
            
            case R.id.btn_record:
                
                startRecord();
                break;

            case R.id.btn_stop:

                stopRecord();
                break;

            case R.id.btn_play:

                playAudio();
                break;

        }
        
    }
    
    private void startRecord() {

        try {

            setUpMediaRecorder();
            mediaRecorder.prepare();
            mediaRecorder.start();
            btnRecord.setEnabled(false);
            btnStop.setEnabled(true);
            Toast.makeText(this, "Recording Started", Toast.LENGTH_LONG).show();
            
        } catch (IOException e) {
            
            e.printStackTrace();
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show();
            
        }

    }

    private void stopRecord() {

        mediaRecorder.stop();
        mediaRecorder.release();
        mediaRecorder = null;
        Toast.makeText(this, "Audio Recorder Stopped", Toast.LENGTH_LONG).show();
        // setUp button state
        btnRecord.setEnabled(true);
        btnStop.setEnabled(false);
        btnPlay.setEnabled(true);

    }

    private void playAudio() {

        MediaPlayer mediaPlayer = new MediaPlayer();
        try {

            mediaPlayer.setDataSource(filePath);
            mediaPlayer.prepare();
            mediaPlayer.start();

        } catch (IOException e) {

            e.printStackTrace();
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show();

        }


    }
    
}
