package com.example.mirrormirror;


import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Point;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Switch;


public class Settings extends AppCompatActivity {

    public final static String SHARED_PREFS="shared_prefs";
    public final static String music_SHAREDPREFS="music_SHAREDPREFS";
    public final static String sound_SHAREDPREFS="sound_SHAREDPREFS";

    public static int opened=0;

    Switch sound_switch, music_switch;
    SharedPreferences sharedPreferences;
    Button back;
    float width,height;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Display display = getWindowManager(). getDefaultDisplay();
        Point size = new Point();
        display. getSize(size);
        width = size. x;
        height = size. y;

        background();
        set_back_button();


        //GET THE PREVIOUS VALUE OF THE SWITCH
        sound_switch =findViewById(R.id.Sound);
        music_switch =findViewById(R.id.music);

        sharedPreferences=getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);

        music_switch.setChecked(sharedPreferences.getBoolean(music_SHAREDPREFS,true));
        sound_switch.setChecked(sharedPreferences.getBoolean(sound_SHAREDPREFS,true));

        music_switch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (music_switch.isChecked()){
                    Start.music.start();
                    Start.music.setLooping(true);
                }else{
                    Start.music.pause();
                }
                Start.musicBoolean= music_switch.isChecked();
                save();
            }
        });
        sound_switch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { save(); }});


    }

    private void set_back_button() {

        // SET THE BACK BUTTON
        back= new Button(this);

        back.setBackgroundResource(R.drawable.back);
        RelativeLayout.LayoutParams layoutParams19 = new RelativeLayout.LayoutParams(setx(75),sety(75));
        addContentView(back,layoutParams19);

        back.setY(sety(50));
        back.setX(setx(50));

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              finish();
            }
        });
    }

    public void save(){

        SharedPreferences sharedPreferences=getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();

        editor.putBoolean(music_SHAREDPREFS, music_switch.isChecked());
        editor.putBoolean(sound_SHAREDPREFS, sound_switch.isChecked());

        editor.apply();

    }

    private void background() {
        //BACKGROUND
        ConstraintLayout background= findViewById(R.id.layout);

        int back_color= Color.parseColor("#D9F5FF");
        background.setBackgroundColor(back_color);

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

        opened=1;

        if (Start.musicBoolean){
            Start.music.start();
            Start.music.setLooping(true);

        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        opened=0;
        app_closed app_closed = new app_closed();
        app_closed.activity_closed();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
