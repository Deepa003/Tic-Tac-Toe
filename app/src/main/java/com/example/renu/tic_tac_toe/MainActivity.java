package com.example.renu.tic_tac_toe;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.content.DialogInterface;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Spinner;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

public class MainActivity extends AppCompatActivity {

   Spinner sp;
    Button play,quit;
   String[] data={"Player 1 vs Player 2","Player 1 vs Computer"};
    AdView mAdView;
    InterstitialAd mInterstitialAd;
    AdRequest adRequest;
    private Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbarFunc();

        mAdView = (AdView) findViewById(R.id.adView);
      //  AdRequest adRequest = new AdRequest.Builder().build();
       // .addTestDevice("4170F6EE1557FE0FC6F1230360ACDA79")
        //adRequest.TestDevice(AdRequest.DEVICE_ID_EMULATOR);
       // mAdView.loadAd(adRequest);


        // ******************** INTERSTITIAl AD **********************

        mInterstitialAd = new InterstitialAd(this);
        // set the ad unit ID
        mInterstitialAd.setAdUnitId(getString(R.string.interstitial_full_screen));

        adRequest = new AdRequest.Builder().addTestDevice("AA26C6DE32BDB7A329A27BC60F42E475").build();

        // Load ads into Interstitial Ads
        mInterstitialAd.loadAd(adRequest);

        mInterstitialAd.setAdListener(new AdListener() {
            public void onAdLoaded() {
                showInterstitial();
            }
        });


        //AdRequest adRequest = new AdRequest.Builder().build();
        adRequest = new AdRequest.Builder().build();
      //  .addTestDevice("AA26C6DE32BDB7A329A27BC60F42E475")
        mAdView.loadAd(adRequest);

        mAdView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                // Code to be executed when an ad finishes loading.
                Log.i("Ads", "onAdLoaded");
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                // Code to be executed when an ad request fails.
                Log.i("Ads", "onAdFailedToLoad");
            }

            @Override
            public void onAdOpened() {
                // Code to be executed when an ad opens an overlay that
                // covers the screen.
                Log.i("Ads", "onAdOpened");
            }

            @Override
            public void onAdLeftApplication() {
                // Code to be executed when the user has left the app.
                Log.i("Ads", "onAdLeftApplication");
            }

            @Override
            public void onAdClosed() {
                // Code to be executed when when the user is about to return
                // to the app after tapping on an ad.
                Log.i("Ads", "onAdClosed");
            }
        });




        Intent i1=new Intent(MainActivity.this,MusicService.class);
        startService(i1);

        play=(Button)findViewById(R.id.bplay);
        quit=(Button)findViewById(R.id.bquit);
        quit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //(MainActivity.this).finishAffinity();
                /*final AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
                alert.setMessage("Would you like to continue??");
                alert.setCancelable(false);
                alert.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        startActivity(getIntent());

                    }
                });


                alert.setNegativeButton("NO",new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int id) {
                        //Intent newIntent = new Intent(MainActivity.this,MainActivity.class);
                        //startActivity(newIntent);
                        //finish();
                        (MainActivity.this).finishAffinity();
                        Intent i1=new Intent(MainActivity.this,MusicService.class);
                        stopService(i1);
                    }
                });


                AlertDialog alertBox = alert.create();
                alertBox.show();
            }*/
                final Dialog dialog = new Dialog(MainActivity.this);
                dialog.setContentView(R.layout.exit_dialog);

                Button btn_ok = (Button) dialog.findViewById(R.id.btn_ok);
                btn_ok.setOnClickListener(new View.OnClickListener() {
                    @SuppressLint("NewApi")
                    @Override
                    public void onClick(View view) {
                        (MainActivity.this).finishAffinity();
                        Intent i1=new Intent(MainActivity.this,MusicService.class);
                        stopService(i1);
                    }
                });

                Button btn_cancel = (Button) dialog.findViewById(R.id.btn_cancel);
                btn_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                dialog.show();
            }
        });

        sp=(Spinner)findViewById(R.id.spinner);
        final ArrayAdapter<String> dataAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,data);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp.setAdapter(dataAdapter);
        play.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {

        String text=sp.getSelectedItem().toString();
        if (text.equals("Player 1 vs Player 2")) {
            Intent i = new Intent(MainActivity.this, Game.class);
            i.setType("2");
            startActivity(i);
        } else if (text.equals("Player 1 vs Computer")) {
            Intent i = new Intent(MainActivity.this, Game.class);
            i.setType("1");
            startActivity(i);
        }

    }
});

    }

    public void toolbarFunc(){
        toolbar = (Toolbar) findViewById(R.id.toolbar_homescreen);
        setSupportActionBar(toolbar);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater blowUp=getMenuInflater();
        blowUp.inflate(R.menu.cool_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.help:
                Intent i=new Intent(this,Help.class);
                startActivity(i);
                break;


            case R.id.score:
                Intent intent=new Intent(this,ScoreView.class);
                startActivity(intent);

                break;

            case R.id.options:
                Intent intent1=new Intent(this,Options.class);
                startActivity(intent1);

                break;
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i2 = new Intent(MainActivity.this, MusicService.class);
        stopService(i2);
        finishAffinity();
    }

    @Override
    public void onPause() {
        if (mAdView != null) {
            mAdView.pause();
        }
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mAdView != null) {
            mAdView.resume();
        }
    }

    @Override
    public void onDestroy() {
        if (mAdView != null) {
            mAdView.destroy();
        }
        super.onDestroy();
    }


    private void showInterstitial() {
        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        }
    }

}
