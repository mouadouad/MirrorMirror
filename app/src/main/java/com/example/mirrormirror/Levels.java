package com.example.mirrormirror;


import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;


public class Levels extends AppCompatActivity {

    public final static String SHARED_PREFS="shared_prefs";
    public final static String stars_sharedPREFS ="STARS";

    public static int opened=0;
    public static Boolean back_clicked=false;

    int levels=30;
    ArrayList<Integer> stars=new ArrayList<>();
    ListView listView;
    int width,height;
    Activity activity=Levels.this;
    TextView NumberOfStars;
    Button back;
    InterstitialAd mInterstitialAd;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Display display = getWindowManager(). getDefaultDisplay();
        Point size = new Point();
        display. getSize(size);
        width = size. x;
        height = size. y;

        //BACKGROUND
        background();

        // SET THE BACK BUTTON
        back_button();

        //MAKE THE NUMBER OF STARS TEXTVIEW
        set_Textview();

        // GET THE STARS ARRAY
        SharedPreferences sharedPreferences=getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString(stars_sharedPREFS, "");
        assert json != null;
        if (!json.isEmpty()){
            Type type = new TypeToken<ArrayList<Integer>>() {}.getType();
            stars = gson.fromJson(json, type);
        }

        //MAKE THE STARS 0 IN THE FIRST TIME
        if (stars.isEmpty()){
            for (int i=0;i<levels;i++){
                stars.add(0);
            }
            save();
        }

        //APPEND THE NUMBER OF STARS
        int lenght = stars.toArray().length;
        int numberofstars=0;
        for (int a = 0; a< lenght; a++){
            numberofstars=numberofstars+stars.get(a);
        }
        NumberOfStars.append(" : "+numberofstars);


        //MAKE THE LISTVIEW
        set_listview();

        //ADD INTERSTITIAL
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());

    }

    public void set_Textview(){
        //MAKE THE NUMBER OF STARS TEXTVIEW
        NumberOfStars = new TextView(this );
        RelativeLayout.LayoutParams layoutParams1 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,sety(250));
        addContentView(NumberOfStars,layoutParams1);
        int color= Color.parseColor("#F8C822");
        NumberOfStars.setTextColor(color);
        NumberOfStars.append(getString(R.string.number_of_stars));
        NumberOfStars.setTextSize(sety(20));
        NumberOfStars.setGravity(Gravity.CENTER);

    }

    public void set_listview(){
        //MAKE THE LISTVIEW
        listView = new ListView(this );
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,sety(1770-250)-getStatusBarHeight());
        addContentView(listView,layoutParams);
        listView.setY(sety(250));

        final levels_adapter adapter=new levels_adapter(this,levels,stars,activity,width,height);
        listView.setAdapter(adapter);
        listView.setDivider(null);
        listView.setDividerHeight(0);
    }

    public void back_button(){
        // SET THE BACK BUTTON
        back= new Button(this);

        back.setBackgroundResource(R.drawable.back);
        RelativeLayout.LayoutParams layoutParams1 = new RelativeLayout.LayoutParams(setx(75),sety( 75));
        addContentView(back,layoutParams1);

        back.setY(sety(50));
        back.setX(setx(50));

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent backIntent = new Intent(Levels.this,Start.class);
                startActivity(backIntent);
                back_clicked=true;
                mInterstitialAd.show();
            }
        });
    }

    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    private void background() {
        //BACKGROUND
        RelativeLayout background=new RelativeLayout(this);
        RelativeLayout.LayoutParams backparams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        int back_color= Color.parseColor("#2258B0");
        background.setBackgroundColor(back_color);
        addContentView(background,backparams);
    }

    public void save(){

        SharedPreferences sharedPreferences=getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();

        Gson gson = new Gson();
        String json = gson.toJson(stars);
        editor.putString(stars_sharedPREFS,json);


        editor.apply();

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
        Intent backIntent = new Intent(Levels.this,Start.class);
        startActivity(backIntent);
        back_clicked=true;
        mInterstitialAd.show();
    }
}
