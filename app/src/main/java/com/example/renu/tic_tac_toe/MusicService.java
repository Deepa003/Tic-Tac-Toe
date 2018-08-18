package com.example.renu.tic_tac_toe;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by renu on 12/08/16.
 */
public class MusicService extends Service implements MediaPlayer.OnCompletionListener {
    MediaPlayer mPlayer;

    @Override
    public void onCompletion(MediaPlayer mp) {
        mPlayer.release();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mPlayer=MediaPlayer.create(this,R.raw.song);

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if(!mPlayer.isLooping()){
            mPlayer.start();
            return START_STICKY;
        }

        return super.onStartCommand(intent, flags, startId);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
       if(mPlayer.isPlaying()){
           mPlayer.stop();

       }
    }

}
