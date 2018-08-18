package com.example.renu.tic_tac_toe;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;

public class Options extends AppCompatActivity {

    SeekBar seekBar;
    AudioManager audioManager;
    ImageButton button;
    boolean bm=true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);
        seekBar=(SeekBar)findViewById(R.id.seekBar);
        button=(ImageButton)findViewById(R.id.imageButton);
        audioManager=(AudioManager)getSystemService(Context.AUDIO_SERVICE);
        seekBar.setMax(audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC));
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(bm==true) {
                    bm=false;
                    Intent i2 = new Intent(Options.this, MusicService.class);
                    stopService(i2);
                }
                else
                    if(bm==false){
                        bm=true;
                        Intent i1=new Intent(Options.this,MusicService.class);
                        startService(i1);

                    }
            }
        });

    }


}
