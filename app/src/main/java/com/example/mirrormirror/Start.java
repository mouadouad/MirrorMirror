package com.example.mirrormirror;


import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Point;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

public class Start extends AppCompatActivity {

    public final static String SHARED_PREFS="shared_prefs";
    public final static String music_SHAREDPREFS="music_SHAREDPREFS";
    public static int opened=0;
    public static MediaPlayer music;
    public static boolean  musicBoolean;
    int width , height;
    Button start,setting;
    ImageView icon;
    InterstitialAd mInterstitialAd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Display display = getWindowManager(). getDefaultDisplay();
        Point size = new Point();
        display. getSize(size);
        width = size. x;
        height = size. y;

        //LAYOUT
        background();
        make_layout();
        onclick_buttons();


        //ANIMATE THE SPLASH SCREEN
        Animation from_bottom= AnimationUtils.loadAnimation(this,R.anim.from_bottom);
        Animation from_top= AnimationUtils.loadAnimation(this,R.anim.from_top);

        start.setAnimation(from_bottom);
        setting.setAnimation(from_top);
        icon.setAnimation(from_top);


        //MUSIC
        SharedPreferences sharedPreferences=getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);
        musicBoolean =sharedPreferences.getBoolean(music_SHAREDPREFS,true);

        //SEE IF COMING FROM SETTING OR FROM LEVELS
        if (!Levels.back_clicked) {
            music = MediaPlayer.create(this, R.raw.mirror_music2);
        }

        //ADD INTERSTITIAL
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());

        banner();

    }

    private void background() {
        //BACKGROUND
        RelativeLayout background=new RelativeLayout(this);
        RelativeLayout.LayoutParams backparams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        int back_color= Color.parseColor("#D9F5FF");
        background.setBackgroundColor(back_color);
        addContentView(background,backparams);
    }

    public void make_layout(){
        icon=new ImageView(this);
        start= new Button(this);
        setting= new Button(this);


        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(setx(300),sety(100));
        addContentView(start,layoutParams);

        RelativeLayout.LayoutParams layoutParams1 = new RelativeLayout.LayoutParams(setx(300),sety(100));
        addContentView(setting,layoutParams1);

        RelativeLayout.LayoutParams layoutParams2 = new RelativeLayout.LayoutParams(setx(800),sety(800));
        addContentView(icon,layoutParams2);

        start.setBackgroundResource(R.drawable.start);
        setting.setBackgroundResource(R.drawable.settings);
        icon.setBackgroundResource(R.drawable.mirror_icone);

        start.setX(setx(390));
        start.setY(sety(1100));

        setting.setX(setx(700));
        setting.setY(sety(50));

        icon.setX(setx(140));
        icon.setY(sety(200));
    }

    public void onclick_buttons(){
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Start.this, Levels.class);
                startActivity(intent);
                mInterstitialAd.show();

            }
        });

        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Start.this,Settings.class);
                startActivity(intent);
            }
        });
    }

    public void banner(){
        AdView adView = new AdView(this);
        adView.setAdSize(AdSize.BANNER);
        adView.setAdUnitId("ca-app-pub-3922358669029120/2831354657");

        RelativeLayout layout=new RelativeLayout(this);
        RelativeLayout.LayoutParams layoutParams1 = new RelativeLayout.LayoutParams((int) width, (int) height-getStatusBarHeight());
        addContentView(layout,layoutParams1);


        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
        layout.addView(adView,layoutParams);

        MobileAds.initialize(this,"ca-app-pub-3922358669029120~3985187056");
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);


    }

    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }


    public int setx(float x){
        int i;

        i= (int) ((x*width)/1080);

        return i;
    }

    public int sety(float x){
        int i;

        i= (int) ((x*height)/1770);

        return i;
    }

    @Override
    public void onResume() {
        super.onResume();

        if (musicBoolean){
            music.start();
            music.setLooping(true);
        }
        opened=1;
    }

    @Override
    protected void onStop() {
        super.onStop();
        opened=0;
        app_closed app_closed = new app_closed();
        app_closed.activity_closed();
    }

}
