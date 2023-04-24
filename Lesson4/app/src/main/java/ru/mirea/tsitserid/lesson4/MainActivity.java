package ru.mirea.tsitserid.lesson4;


import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;

import ru.mirea.tsitserid.lesson4.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private final Handler handler = new Handler();
    private MediaPlayer mediaPlayer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.editTextMirea.setText("Мой номер по списку No28");
        binding.buttonMirea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(MainActivity.class.getSimpleName(),"onClickListener");
            }
        });

        final Button playButton = binding.ButtonPlayStop;
        final SeekBar bar = binding.seekBar;
        mediaPlayer = MediaPlayer.create(this, R.raw.backup_dancers);
        bar.setMax(mediaPlayer.getDuration());
        mediaPlayer.start();
        mediaPlayer.pause();
        binding.seekBar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                seekChange(v);
                return false;
            }
        });
        binding.ButtonPlayStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (playButton.getText() == "play") {
                    playButton.setText("stop");
                    try{
                        mediaPlayer.start();
                        startPlayProgressUpdater(bar, playButton);
                    }catch (IllegalStateException e) {
                        mediaPlayer.pause();
                    }
                }else {
                    playButton.setText("play");
                    mediaPlayer.pause();
                }
            }
        });
    }
    private void seekChange(View v){
        if(mediaPlayer.isPlaying()){
            SeekBar sb = (SeekBar)v;
            mediaPlayer.seekTo(sb.getProgress());
        }
    }
    public void startPlayProgressUpdater(SeekBar bar, Button playButton) {
        bar.setProgress(mediaPlayer.getCurrentPosition());

        if (mediaPlayer.isPlaying()) {
            Runnable notification = new Runnable() {
                public void run() {
                    startPlayProgressUpdater(bar, playButton);
                }
            };
            handler.postDelayed(notification,1000);
        }else{
            mediaPlayer.pause();
            playButton.setText("play");
            bar.setProgress(0);
        }
    }
}