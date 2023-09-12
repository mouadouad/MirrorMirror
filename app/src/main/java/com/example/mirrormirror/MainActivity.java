package com.example.mirrormirror;


import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public final static String lvl_key = "com.mouad0.hp.mirrormirror.lvl_key";
    public final static String SHARED_PREFS="shared_prefs";
    public final static String stars_sharedPREFS ="STARS";


    ArrayList<Integer> stars=new ArrayList<>();
    float width,height,x_canon_place,y_canon_place;
    Button left,right,canon,low_canon,launch,again,back;
    int lastPosition=0;
    rects rect;
    mirrors mirror;
    ArrayList<int[]> mirror_places=new ArrayList<>();
    float canon_height=232,canon_width=84;
    TextView text;
    FrameLayout dim;
    boolean first_turn=true;

    public static int opened=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        rect=new rects(this);
        mirror= new mirrors(this);

        Display display = getWindowManager(). getDefaultDisplay();
        Point size = new Point();
        display. getSize(size);
        width = size. x;
        height = size. y;

        x_canon_place=20;
        y_canon_place=1770-732;

        //GET WHICH LVL
        final Intent intent=getIntent();
        final String NumbOfLvl= intent.getStringExtra(lvl_key);

        //GO TO THE LVL
        switch (Integer.parseInt(NumbOfLvl)){

            case 1: lvl_1(); break;
            case 2: lvl_2(); break;
            case 3: lvl_3(); break;
            case 4: lvl_4(); break;
            case 5: lvl_5(); break;
            case 6: lvl_6(); break;
            case 7: lvl_7(); break;
            case 8: lvl_8(); break;
            case 9: lvl_9(); break;
            case 10: lvl_10(); break;
            case 11: lvl_11(); break;
            case 12: lvl_12(); break;
            case 13: lvl_13(); break;
            case 14: lvl_14(); break;
            case 15: lvl_15(); break;
            case 16: lvl_16(); break;
            case 17: lvl_17(); break;
            case 18: lvl_18(); break;





        }

        //GET STARS
        SharedPreferences sharedPreferences=getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString(stars_sharedPREFS, "");
        assert json != null;
        if (!json.isEmpty()){
            Type type = new TypeToken<ArrayList<Integer>>() {}.getType();
            stars = gson.fromJson(json, type);
        }


        //CALL MIRROR ACTIVITY TO DRAW MIRRORS
        mirror.launched(mirror_places,width,height);
        setContentView(mirror);


        place_canon();
        place_controllers();
        place_launch_button();
        onclick_buttons();


    }

    public void onclick_buttons(){

        launch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launch();
            }
        });

        left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { turn_left(); }});

        right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { turn_right(); }});

        right.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                turn_right();turn_right();turn_right();turn_right();turn_right();turn_right();
                return false;
            }
        });

        left.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                turn_left();turn_left();turn_left();turn_left();turn_left();turn_left();
                return false;
            }
        });


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent backIntent = new Intent(MainActivity.this,Levels.class);
                startActivity(backIntent);
            }
        });
    }

    public void turn_left(){

        final RotateAnimation rotateAnim = new RotateAnimation(lastPosition, lastPosition-2,
                RotateAnimation.ABSOLUTE, seth(x_canon_place+canon_width/2),
                RotateAnimation.ABSOLUTE,seth(y_canon_place+canon_height));
        rotateAnim.setDuration(50);
        rotateAnim.setFillAfter(true);
        canon.setAnimation(rotateAnim);
        rotateAnim.start();

        lastPosition-=2;

        if(lastPosition<-180){
            lastPosition+=360;
        }

        if (first_turn){
            lastPosition-=1;
            first_turn=false;
        }


        Log.d("angle", String.valueOf(lastPosition));
        //REFRESH THE VIEW
         final ImageView ntg= new ImageView(this);
         RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(0, 0);
         addContentView(ntg,layoutParams);


    }

    public void turn_right(){

        final RotateAnimation rotateAnim = new RotateAnimation(lastPosition, lastPosition+2,
                RotateAnimation.ABSOLUTE, seth(x_canon_place+canon_width/2),
                RotateAnimation.ABSOLUTE,seth(y_canon_place+canon_height));
        rotateAnim.setDuration(50);
        rotateAnim.setFillAfter(true);
        canon.setAnimation(rotateAnim);
        rotateAnim.start();


        lastPosition+=2;

        if (lastPosition>180){
            lastPosition-=360;
        }

        if (first_turn){
            lastPosition+=1;
            first_turn=false;
        }

        Log.d("angle", String.valueOf(lastPosition));

        //REFRESH THE VIEW
        final ImageView ntg= new ImageView(this);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(0, 0);
        addContentView(ntg,layoutParams);

    }

    public void place_canon(){
         canon = new Button(this);
         low_canon = new Button(this);

        canon.setBackgroundResource(R.drawable.canon1);
         RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(seth(canon_width),seth(canon_height));
        addContentView(canon,layoutParams);

        low_canon.setBackgroundResource(R.drawable.canon2);
        RelativeLayout.LayoutParams layoutParams1 = new RelativeLayout.LayoutParams(seth(130),seth(100));
        addContentView(low_canon,layoutParams1);

        final RotateAnimation rotateAnim = new RotateAnimation(lastPosition, lastPosition,
                RotateAnimation.ABSOLUTE, seth(x_canon_place+canon_width/2),
                RotateAnimation.ABSOLUTE,seth(y_canon_place+canon_height));
        rotateAnim.setFillAfter(true);
        canon.setAnimation(rotateAnim);
        rotateAnim.start();


        canon.setX(seth(x_canon_place));
        canon.setY(seth(y_canon_place));
        low_canon.setY(seth(y_canon_place+182));
        low_canon.setX(seth(x_canon_place-20));

    }

    public void place_controllers(){

         left = new Button(this);
         right = new Button(this);
         again= new Button(this);
         back= new Button(this);


        left.setBackgroundResource(R.drawable.left);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(setx(163),sety( 75));
        addContentView(left,layoutParams);

        right.setBackgroundResource(R.drawable.right);
        RelativeLayout.LayoutParams layoutParams1 = new RelativeLayout.LayoutParams(setx(163),sety( 75));
        addContentView(right,layoutParams1);

        again.setBackgroundResource(R.drawable.replay);
        RelativeLayout.LayoutParams layoutParams18 = new RelativeLayout.LayoutParams(setx(75), sety(75));
        addContentView(again,layoutParams18);

        back.setBackgroundResource(R.drawable.back_w);
        RelativeLayout.LayoutParams layoutParams19 = new RelativeLayout.LayoutParams(setx(75),sety( 75));
        addContentView(back,layoutParams19);

        left.setY(sety(1770-200));
        left.setX(setx(352));

        right.setX(setx(565));
        right.setY(sety(1770-200));

        again.setY(sety(50));
        again.setX(setx(200));

        back.setY(sety(50));
        back.setX(setx(50));


    }

    public void place_launch_button(){

        launch = new Button(this);

        launch.setBackgroundResource(R.drawable.launch);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(setx(200), sety(100));
        addContentView(launch,layoutParams);

        launch.setX(setx(1080-250));
        launch.setY(sety(1770-205));


    }

    public void launch(){


        setContentView(rect); //GIVE THE VIEW TO RECTS ACTIVITY
        place_canon();
        place_controllers();
        place_launch_button();

        //MAKE THE SCREEN DIM
        make_screen_dim();

        //MAKE THE TEXTVIEW
        make_textView();

        //MAKE STARS
        make_stars();

        //GET THE LVL NUMBER
        Intent intent=getIntent();
        int numb_of_lvl= Integer.parseInt(intent.getStringExtra(lvl_key));

        // SHARED PREFS VARIABLE
        SharedPreferences sharedPreferences=getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);

        //LAUNCH AND SEND DATA
        rect.get_launch(lastPosition,mirror_places,y_canon_place+canon_height, x_canon_place+canon_width/2,y_canon_place-10,stars,
                numb_of_lvl,sharedPreferences,this,dim,text,width,height);


        onclick_listeners();


    }

    private void onclick_listeners() {
        //AGAIN BUTTON
        again.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //finish();
                //startActivity(getIntent());
                MainActivity.this.overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
                recreate();
                //   Intent intent =((Activity)context).getIntent();
                //  ((Activity)context).finish();
                //   context1.startActivity(intent);
                //  ((Activity)context).overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);

               // Intent i = new Intent(context,MainActivity.class);
               // i.putExtra(lvl_key,String.valueOf(numb_of_lvl));
               // context.startActivity(i);
               // ((MainActivity)context).overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
            }
        });

        //BACK BUTTON
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, Levels.class);
                startActivity(i);
            }
        });
    }

    private void make_screen_dim() {
        Resources res = getResources();
        Drawable shape = ResourcesCompat.getDrawable(res, R.drawable.shape, null);
        dim=new FrameLayout(this);
        dim.setForeground(shape);
        RelativeLayout.LayoutParams layoutParams20 = new RelativeLayout.LayoutParams((int)(width), (int) height);
        addContentView(dim,layoutParams20);
        dim.getForeground().setAlpha(200);
        dim.setVisibility(View.GONE);
    }

    private void make_textView() {
        RelativeLayout layout= new RelativeLayout(this);
        RelativeLayout.LayoutParams params= new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        addContentView(layout,params);

        text = new TextView(this);
        RelativeLayout.LayoutParams layoutParams21 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams21.addRule(RelativeLayout.CENTER_HORIZONTAL);
        layout.addView(text,layoutParams21);
        text.setTextSize(36);
        text.setTextColor(Color.WHITE);
        text.setY(sety(200));
    }

    private void make_stars() {
        //MAKE THE STARS
        ImageView star1=new ImageView(this);
        star1.setBackgroundResource(R.drawable.star);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams( setx(250), sety(250));
        addContentView(star1,layoutParams);
        star1.setY(sety(700));
        star1.setX( setx(165));
        star1.setVisibility(View.GONE);

        ImageView star2=new ImageView(this);
        star2.setBackgroundResource(R.drawable.star);
        RelativeLayout.LayoutParams layoutParams1 = new RelativeLayout.LayoutParams( setx(250), sety(250));
        addContentView(star2,layoutParams1);
        star2.setY(sety(700));
        star2.setX( setx(415));
        star2.setVisibility(View.GONE);

        ImageView star3=new ImageView(this);
        star3.setBackgroundResource(R.drawable.star);
        RelativeLayout.LayoutParams layoutParams2 = new RelativeLayout.LayoutParams( setx(250),sety( 250));
        addContentView(star3,layoutParams2);
        star3.setY(sety(700));
        star3.setX( setx(665));
        star3.setVisibility(View.GONE);
        //SEND STARS IMAGEVIEWS
        rect.get_stars(star1,star2,star3);
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

    public int seth(float h){
        float hypotenuse= (int) Math.sqrt(width*width+height*height);
        if ((height/width)>(1.64)){
            double modified_height=1.64*width;
            hypotenuse= (int) Math.sqrt(width*width+modified_height*modified_height);

        }else if ((height/width)<(1.64)){
            double modified_width=height/1.64;
            hypotenuse= (int) Math.sqrt(modified_width*modified_width+height*height);
        }
        int i;

        i= (int) ((h*hypotenuse)/2074);

        return i;
    }

    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    boolean stopped=false; //SEE IF ACTIVITY STOPPED TO STOP THE BEAM

    @Override
    public void onResume() {
        super.onResume();

        opened=1;
        if(stopped){ //SEE IF BEAM STOPPED TO RESUME IT
            rect.resume();  }


        if (Start.musicBoolean){
            Start.music.start();
            Start.music.setLooping(true);

        }

    }

    @Override
    protected void onStop() {
        super.onStop();


            opened = 0;
            app_closed app_closed = new app_closed();
            app_closed.activity_closed();

            // STOP BEAM FROM MOVING
            rect.pause();
            stopped = true;


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent backIntent = new Intent(MainActivity.this,Levels.class);
        startActivity(backIntent);
    }

    public void lvl_1(){

        int[] stars,goal,first,second,third,fourth,fifth;


        stars = new int[2];
        goal = new int[4];
        first = new int[4];
        second = new int[4];
        third = new int[4];
        fourth = new int[4];
        fifth = new int[4];

        //TO KNOW STARS
        stars[0]=3;
        stars[1]=5;

        //TH GOAL MIRROR
        goal[0]=1080-20;
        goal[1]=0;
        goal[2]=300;
        goal[3]=1080;

        //0:LEFT  1:TOP  2:BOTTOM  3:RIGHT

        // NOOOOTE!!! TOP<BOTTOM


        first[0]=0;
        first[1]=0;
        first[2]=20;
        first[3]=1080-20;

        second[0]= 0;
        second[1]=1500 ;
        second[2]=1520;
        second[3]= 1080;

        third[0]=500;
        third[1]=20;
        third[2]=1020;
        third[3]= 520;

        fourth[0]= 1080-20;
        fourth[1]=300;
        fourth[2]= 600;
        fourth[3]= 1080;

        fifth[0]= 1080-20;
        fifth[1]=650;
        fifth[2]=1100;
        fifth[3]= 1080;

        mirror_places.add(stars);
        mirror_places.add(goal);
        mirror_places.add(first);
        mirror_places.add(second);
        mirror_places.add(third);
        mirror_places.add(fourth);
        mirror_places.add(fifth);

    }

    public void lvl_2(){

        int[] stars,goal,first,second,third,fourth,fifth;

        stars = new int[2];
        goal = new int[4];
        first = new int[4];
        second = new int[4];
        third = new int[4];
        fourth = new int[4];
        fifth = new int[4];

        //TO KNOW STARS
        stars[0]=1;
        stars[1]=4;

        //TH GOAL MIRROR
        goal[0]=1080-20;
        goal[1]= 1770-getStatusBarHeight()-320;
        goal[2]= 1770-getStatusBarHeight()-20;
        goal[3]= 1080;

        //0:LEFT  1:TOP  2:BOTTOM  3:RIGHT

        // NOOOOTE!!! TOP<BOTTOM


        first[0]=0;
        first[1]=0;
        first[2]=20;
        first[3]= 1080;

        second[0]= 0;
        second[1]=100-getStatusBarHeight() ;
        second[2]=800-getStatusBarHeight();
        second[3]= 20;

        third[0]= 1080-20;
        third[1]=700-getStatusBarHeight();
        third[2]=1000-getStatusBarHeight();
        third[3]= 1080;

        fourth[0]= 800;
        fourth[1]=1000-getStatusBarHeight();
        fourth[2]= 1770-getStatusBarHeight();
        fourth[3]= 820;

        fifth[0]= 820;
        fifth[1]= 1770-getStatusBarHeight()-20;
        fifth[2]= 1770-getStatusBarHeight();
        fifth[3]= 1080;

        mirror_places.add(stars);
        mirror_places.add(goal);
        mirror_places.add(first);
        mirror_places.add(second);
        mirror_places.add(third);
        mirror_places.add(fourth);
        mirror_places.add(fifth);

    }

    public void lvl_3(){

        int[] stars,goal,first,second,third,fourth;

        stars = new int[2];
        goal = new int[4];
        first = new int[4];
        second = new int[4];
        third = new int[4];
        fourth = new int[4];


        //TO KNOW STARS
        //stars[0]=0;
        stars[1]=1;

        //TH GOAL MIRROR
        goal[0]=1080-200;
        goal[1]= 0;
        goal[2]= 20;
        goal[3]= 1080;

        //0:LEFT  1:TOP  2:BOTTOM  3:RIGHT

        // NOOOOTE!!! TOP<BOTTOM


        first[0]=1080-400;
        first[1]=0;
        first[2]=20;
        first[3]= 1080-200;

        second[0]= 1080-400-20;
        second[1]=0 ;
        second[2]=450;
        second[3]= 1080-400;

        third[0]= 1080-20;
        third[1]=100;
        third[2]=600;
        third[3]= 1080;

        fourth[0]= 1080-300;
        fourth[1]=600;
        fourth[2]= 600+20;
        fourth[3]= 1080;

        mirror_places.add(stars);
        mirror_places.add(goal);
        mirror_places.add(first);
        mirror_places.add(second);
        mirror_places.add(third);
        mirror_places.add(fourth);


    }

    public void lvl_4(){

        int[] stars,goal,first;


        stars = new int[2];
        goal = new int[4];
        first = new int[4];


        //TO KNOW STARS
        stars[0]=1;
        stars[1]=1;

        //TH GOAL MIRROR
        goal[0]=800;
        goal[1]=1200-20;
        goal[2]=1200;
        goal[3]=1000;

        //0:LEFT  1:TOP  2:BOTTOM  3:RIGHT

        // NOOOOTE!!! TOP<BOTTOM


        first[0]=400;
        first[1]=0;
        first[2]=20;
        first[3]=600;



        mirror_places.add(stars);
        mirror_places.add(goal);
        mirror_places.add(first);


    }

    public void lvl_5(){

        int[] stars,goal,first,second,third,fourth;

        stars = new int[2];
        goal = new int[4];
        first = new int[4];
        second = new int[4];
        third = new int[4];
        fourth = new int[4];


        //TO KNOW STARS
        stars[0]=1;
        stars[1]=3;

        //TH GOAL MIRROR
        goal[0]=1080-500;
        goal[1]= 1200;
        goal[2]= 1200+20;
        goal[3]= 1080;

        //0:LEFT  1:TOP  2:BOTTOM  3:RIGHT

        // NOOOOTE!!! TOP<BOTTOM


        first[0]=0;
        first[1]=0;
        first[2]=20;
        first[3]= 1080;

        second[0]= 1080-500-20;
        second[1]=900 ;
        second[2]=1200+20;
        second[3]= 1080-500;

        third[0]= 1080-500;
        third[1]=900;
        third[2]=900+20;
        third[3]= 1080-500+100;

        fourth[0]= 1080-200;
        fourth[1]=900;
        fourth[2]= 900+20;
        fourth[3]= 1080;



        mirror_places.add(stars);
        mirror_places.add(goal);
        mirror_places.add(first);
        mirror_places.add(second);
        mirror_places.add(third);
        mirror_places.add(fourth);


    }

    public void lvl_6(){

        int[] stars,goal,first,second,third,fourth,fifth,sixth,seventh;

        stars = new int[2];
        goal = new int[4];
        first = new int[4];
        second = new int[4];
        third = new int[4];
        fourth = new int[4];
        fifth = new int[4];
        sixth = new int[4];
        seventh = new int[4];



        //TO KNOW STARS
        stars[0]=2;
        stars[1]=4;

        //TH GOAL MIRROR
        goal[0]=200;
        goal[1]= 0;
        goal[2]= 20;
        goal[3]= 550;

        //0:LEFT  1:TOP  2:BOTTOM  3:RIGHT

        // NOOOOTE!!! TOP<BOTTOM


        first[0]=0;
        first[1]=0;
        first[2]=20;
        first[3]= 200;

        second[0]= 550;
        second[1]=0 ;
        second[2]=20;
        second[3]= 1080;

        third[0]= 0;
        third[1]=20;
        third[2]=400;
        third[3]= 20;

        fourth[0]= 1080-20;
        fourth[1]=20;
        fourth[2]= 400;
        fourth[3]= 1080;

        fifth[0]= 0;
        fifth[1]=400 ;
        fifth[2]=400+20;
        fifth[3]= 350;

        sixth[0]= 500;
        sixth[1]=400;
        sixth[2]=400+20;
        sixth[3]= 1080;

        seventh[0]= 0;
        seventh[1]=1400;
        seventh[2]= 1400+20;
        seventh[3]= 1080;

        mirror_places.add(stars);
        mirror_places.add(goal);
        mirror_places.add(first);
        mirror_places.add(second);
        mirror_places.add(third);
        mirror_places.add(fourth);
        mirror_places.add(fifth);
        mirror_places.add(sixth);
        mirror_places.add(seventh);


    }

    public void lvl_7(){

        int[] stars,goal;


        stars = new int[2];
        goal = new int[4];



        //TO KNOW STARS
        stars[0]=1;
        stars[1]=1;

        //TH GOAL MIRROR
        goal[0]=1020;
        goal[1]=0;
        goal[2]=60;
        goal[3]=1080;

        //0:LEFT  1:TOP  2:BOTTOM  3:RIGHT

        // NOOOOTE!!! TOP<BOTTOM




        mirror_places.add(stars);
        mirror_places.add(goal);



    }

    public void lvl_8(){

        int[] stars,goal,first,second,third;

        stars = new int[2];
        goal = new int[4];
        first = new int[4];
        second = new int[4];
        third = new int[4];


        //TO KNOW STARS
        stars[0]=2;
        stars[1]=2;

        //TH GOAL MIRROR
        goal[0]=600+20;
        goal[1]= 600;
        goal[2]= 600+200;
        goal[3]= 600+20+20;

        //0:LEFT  1:TOP  2:BOTTOM  3:RIGHT

        // NOOOOTE!!! TOP<BOTTOM


        first[0]=500;
        first[1]=0;
        first[2]=20;
        first[3]= 1080;

        second[0]= 1080-20;
        second[1]=20 ;
        second[2]=320;
        second[3]= 1080;

        third[0]= 600;
        third[1]=600;
        third[2]=600+200;
        third[3]= 600+20;



        mirror_places.add(stars);
        mirror_places.add(goal);
        mirror_places.add(first);
        mirror_places.add(second);
        mirror_places.add(third);


    }

    public void lvl_9(){

        int[] stars,goal,first,second,third;

        stars = new int[2];
        goal = new int[4];
        first = new int[4];
        second = new int[4];
        third = new int[4];


        //TO KNOW STARS
        stars[0]=2;
        stars[1]=2;

        //TH GOAL MIRROR
        goal[0]=700;
        goal[1]= 150;
        goal[2]= 150+200;
        goal[3]= 720;

        //0:LEFT  1:TOP  2:BOTTOM  3:RIGHT

        // NOOOOTE!!! TOP<BOTTOM


        first[0]=0;
        first[1]=450;
        first[2]=650;
        first[3]= 20;

        second[0]= 200;
        second[1]=950 ;
        second[2]=950+20;
        second[3]= 800;

        third[0]= 1080-20;
        third[1]=850;
        third[2]=850+200;
        third[3]= 1080;


        mirror_places.add(stars);
        mirror_places.add(goal);
        mirror_places.add(first);
        mirror_places.add(second);
        mirror_places.add(third);



    }

    public void lvl_10(){

        int[] stars,goal,first,second,third,fourth,fifth,sixth,seventh;

        stars = new int[2];
        goal = new int[4];
        first = new int[4];
        second = new int[4];
        third = new int[4];
        fourth =new int[4];
        fifth = new int[4];
        sixth = new int[4];
        seventh = new int[4];

        x_canon_place=400;
        y_canon_place=800;


        //TO KNOW STARS
        stars[0]=4;
        stars[1]=6;

        //TH GOAL MIRROR
        goal[0]=500+20;
        goal[1]= 1200-30;
        goal[2]= 1500-50;
        goal[3]= 500+20+20;

        //0:LEFT  1:TOP  2:BOTTOM  3:RIGHT

        // NOOOOTE!!! TOP<BOTTOM


        first[0]=0;
        first[1]=0;
        first[2]=20;
        first[3]= 1080;

        second[0]= 0;
        second[1]=20 ;
        second[2]=800;
        second[3]= 20;

        third[0]= 1080-20;
        third[1]=20;
        third[2]=1770-20-getStatusBarHeight();
        third[3]= 1080;

        fourth[0]= 350;
        fourth[1]=1770-20-getStatusBarHeight() ;
        fourth[2]=1770-getStatusBarHeight();
        fourth[3]= 1080;

        fifth[0]= 500;
        fifth[1]=1200-50;
        fifth[2]=1500-50;
        fifth[3]= 500+20;

        sixth[0]= 500+20;
        sixth[1]=1200-50 ;
        sixth[2]=1200-30;
        sixth[3]= 700;

        seventh[0]= 800;
        seventh[1]=1250-50 ;
        seventh[2]=1500-50;
        seventh[3]= 800+20;

        mirror_places.add(stars);
        mirror_places.add(goal);
        mirror_places.add(first);
        mirror_places.add(second);
        mirror_places.add(third);
        mirror_places.add(fourth);
        mirror_places.add(fifth);
        mirror_places.add(sixth);
        mirror_places.add(seventh);


    }

    public void lvl_11(){

        int[] stars,goal,first,second,third,fourth;

        stars = new int[2];
        goal = new int[4];
        first = new int[4];
        second = new int[4];
        third = new int[4];
        fourth = new int[4];

        x_canon_place=550;
        y_canon_place=800;

        //TO KNOW STARS
        stars[0]=2;
        stars[1]=3;

        //TH GOAL MIRROR
        goal[0]=20;
        goal[1]= 300;
        goal[2]= 600;
        goal[3]= 20+20;

        //0:LEFT  1:TOP  2:BOTTOM  3:RIGHT

        // NOOOOTE!!! TOP<BOTTOM


        first[0]=0;
        first[1]=0;
        first[2]=1770-getStatusBarHeight();
        first[3]= 20;

        second[0]= 20;
        second[1]=300-20 ;
        second[2]=300;
        second[3]= 200-20;

        third[0]= 20;
        third[1]=0;
        third[2]=20;
        third[3]= 1080;

        fourth[0]= 20;
        fourth[1]=600;
        fourth[2]=600+20;
        fourth[3]= 400;


        mirror_places.add(stars);
        mirror_places.add(goal);
        mirror_places.add(first);
        mirror_places.add(second);
        mirror_places.add(third);
        mirror_places.add(fourth);



    }

    public void lvl_12(){

        int[] stars,goal,first,second,third,fourth,fifth;

        stars = new int[2];
        goal = new int[4];
        first = new int[4];
        second = new int[4];
        third = new int[4];
        fourth =new int[4];
        fifth = new int[4];


        x_canon_place=500;
        y_canon_place=1000;


        //TO KNOW STARS
        stars[0]=2;
        stars[1]=3;

        //TH GOAL MIRROR
        goal[0]=900+20+20;
        goal[1]= 450-20;
        goal[2]= 450;
        goal[3]= 1080;

        //0:LEFT  1:TOP  2:BOTTOM  3:RIGHT

        // NOOOOTE!!! TOP<BOTTOM


        first[0]=0;
        first[1]=0;
        first[2]=20;
        first[3]= 1080;

        second[0]= 0;
        second[1]=20 ;
        second[2]=1770-20-getStatusBarHeight();
        second[3]= 20;

        third[0]=800;
        third[1]=300-50;
        third[2]=550;
        third[3]= 800+20;

        fourth[0]= 900;
        fourth[1]=450 ;
        fourth[2]=550;
        fourth[3]= 900+40;

        fifth[0]= 880;
        fifth[1]=650;
        fifth[2]=650+20;
        fifth[3]= 1080;


        mirror_places.add(stars);
        mirror_places.add(goal);
        mirror_places.add(first);
        mirror_places.add(second);
        mirror_places.add(third);
        mirror_places.add(fourth);
        mirror_places.add(fifth);


    }

    public void lvl_13(){

        int[] stars,goal,first,second,third,fourth,fifth;

        stars = new int[2];
        goal = new int[4];
        first = new int[4];
        second = new int[4];
        third = new int[4];
        fourth =new int[4];
        fifth = new int[4];


        x_canon_place=500;
        y_canon_place=700;


        //TO KNOW STARS
        stars[0]=5;
        stars[1]=7;

        //TH GOAL MIRROR
        goal[0]=0;
        goal[1]= 250+20-50;
        goal[2]= 250+20+200 ;
        goal[3]= 20;

        //0:LEFT  1:TOP  2:BOTTOM  3:RIGHT

        // NOOOOTE!!! TOP<BOTTOM


        first[0]=100;
        first[1]=0;
        first[2]=20;
        first[3]= 1080;

        second[0]= 1080-20;
        second[1]=20 ;
        second[2]=1770-getStatusBarHeight();
        second[3]= 1080;

        third[0]=100;
        third[1]=20;
        third[2]=300+20;
        third[3]= 100+20;

        fourth[0]= 0;
        fourth[1]=350+20+100 ;
        fourth[2]=350+20+100+20;
        fourth[3]= 300+20;

        fifth[0]= 300+20;
        fifth[1]=250+20-80;
        fifth[2]=350+20+100+20;
        fifth[3]= 300+20+20;


        mirror_places.add(stars);
        mirror_places.add(goal);
        mirror_places.add(first);
        mirror_places.add(second);
        mirror_places.add(third);
        mirror_places.add(fourth);
        mirror_places.add(fifth);


    }

    public void lvl_14(){

        int[] stars,goal,first,second,third,fourth;

        stars = new int[2];
        goal = new int[4];
        first = new int[4];
        second = new int[4];
        third = new int[4];
        fourth = new int[4];

        x_canon_place=550;
        y_canon_place=1770-getStatusBarHeight()-550;

        //TO KNOW STARS
        stars[0]=3;
        stars[1]=7;

        //TH GOAL MIRROR
        goal[0]=550;
        goal[1]= 1770-getStatusBarHeight()-20;
        goal[2]= 1770-getStatusBarHeight();
        goal[3]= 700;

        //0:LEFT  1:TOP  2:BOTTOM  3:RIGHT

        // NOOOOTE!!! TOP<BOTTOM


        first[0]=0;
        first[1]=0;
        first[2]=20;
        first[3]= 1080-20;

        second[0]= 1080-20;
        second[1]=0 ;
        second[2]=1770-getStatusBarHeight()-200;
        second[3]= 1080;

        third[0]= 0;
        third[1]=20;
        third[2]=1770-getStatusBarHeight()-200;
        third[3]= 20;

        fourth[0]= 0;
        fourth[1]=1770-getStatusBarHeight()-200;
        fourth[2]=1770-getStatusBarHeight()-200+20;
        fourth[3]= 700;


        mirror_places.add(stars);
        mirror_places.add(goal);
        mirror_places.add(first);
        mirror_places.add(second);
        mirror_places.add(third);
        mirror_places.add(fourth);





    }

    public void lvl_15(){

        int[] stars,goal,first,second,third,fourth,fifth,sixth,seventh,eight;

        stars = new int[2];
        goal = new int[4];
        first = new int[4];
        second = new int[4];
        third = new int[4];
        fourth =new int[4];
        fifth = new int[4];
        sixth = new int[4];
        seventh = new int[4];
        eight= new int[4];

        x_canon_place=500;
        y_canon_place=400;


        //TO KNOW STARS
        stars[0]=6;
        stars[1]=7;

        //TH GOAL MIRROR
        goal[0]=1080-20;
        goal[1]= 150;
        goal[2]= 300;
        goal[3]= 1080;

        //0:LEFT  1:TOP  2:BOTTOM  3:RIGHT

        // NOOOOTE!!! TOP<BOTTOM


        first[0]=0;
        first[1]=0;
        first[2]=20;
        first[3]= 1080;

        second[0]= 1080-137;
        second[1]=20 ;
        second[2]=120;
        second[3]= 1080-137+20;

        third[0]= 800;
        third[1]=20;
        third[2]=100;
        third[3]= 800+20;

        fourth[0]= 400;
        fourth[1]=250;
        fourth[2]=250+20;
        fourth[3]= 800+20;

        fifth[0]= 800;
        fifth[1]=250+20;
        fifth[2]=460;
        fifth[3]= 800+20;

        sixth[0]= 1080-100;
        sixth[1]=450-20;
        sixth[2]=450;
        sixth[3]= 1080;

        seventh[0]= 0;
        seventh[1]=1770-getStatusBarHeight()-20;
        seventh[2]=1770-getStatusBarHeight();
        seventh[3]= 1080;

        eight[0]= 0;
        eight[1]=20;
        eight[2]=1000;
        eight[3]= 20;

        mirror_places.add(stars);
        mirror_places.add(goal);
        mirror_places.add(first);
        mirror_places.add(second);
        mirror_places.add(third);
        mirror_places.add(fourth);
        mirror_places.add(fifth);
        mirror_places.add(sixth);
        mirror_places.add(seventh);
        mirror_places.add(eight);



    }

    public void lvl_16(){

        int[] stars,goal,first,second,third,fourth,fifth,sixth,seventh;

        stars = new int[2];
        goal = new int[4];
        first = new int[4];
        second = new int[4];
        third = new int[4];
        fourth =new int[4];
        fifth = new int[4];
        sixth = new int[4];
        seventh = new int[4];

        x_canon_place=100;
        y_canon_place=850;


        //TO KNOW STARS
        stars[0]=3;
        stars[1]=5;

        //TH GOAL MIRROR
        goal[0]=200;
        goal[1]= 1400+20;
        goal[2]= 1400+20+20;
        goal[3]= 450;

        //0:LEFT  1:TOP  2:BOTTOM  3:RIGHT

        // NOOOOTE!!! TOP<BOTTOM


        first[0]=0;
        first[1]=0;
        first[2]=20;
        first[3]= 1080;

        second[0]= 1080-20;
        second[1]=20 ;
        second[2]=1770-getStatusBarHeight();
        second[3]= 1080;

        third[0]=0;
        third[1]=1770-20-getStatusBarHeight();
        third[2]=1770-getStatusBarHeight();
        third[3]= 1080-20;

        fourth[0]= 0;
        fourth[1]=800 ;
        fourth[2]=800+20;
        fourth[3]= 200;

        fifth[0]= 0;
        fifth[1]=1400;
        fifth[2]=1400+20;
        fifth[3]= 300;

        sixth[0]= 450;
        sixth[1]=900+20;
        sixth[2]=1400+20;
        sixth[3]= 450+20;

        seventh[0]=280;
        seventh[1]=1300;
        seventh[2]=1300+40;
        seventh[3]= 450;

        mirror_places.add(stars);
        mirror_places.add(goal);
        mirror_places.add(first);
        mirror_places.add(second);
        mirror_places.add(third);
        mirror_places.add(fourth);
        mirror_places.add(fifth);
        mirror_places.add(sixth);
        mirror_places.add(seventh);



    }

    public void lvl_17(){

        int[] stars,goal,first,second,third,fourth;

        stars = new int[2];
        goal = new int[4];
        first = new int[4];
        second = new int[4];
        third = new int[4];
        fourth =new int[4];


        x_canon_place=100;
        y_canon_place=50;


        //TO KNOW STARS
        stars[0]=4;
        stars[1]=7;

        //TH GOAL MIRROR
        goal[0]=1000;
        goal[1]= 750;
        goal[2]= 750+20;
        goal[3]= 1080-20;

        //0:LEFT  1:TOP  2:BOTTOM  3:RIGHT

        // NOOOOTE!!! TOP<BOTTOM


        first[0]=0;
        first[1]=0;
        first[2]=20;
        first[3]= 1080;

        second[0]= 1080-20;
        second[1]=20 ;
        second[2]=1770-20-getStatusBarHeight();
        second[3]= 1080;

        third[0]=0;
        third[1]=550;
        third[2]=550+20;
        third[3]= 700;

        fourth[0]= 400;
        fourth[1]=20 ;
        fourth[2]=390;
        fourth[3]= 400+20;


        mirror_places.add(stars);
        mirror_places.add(goal);
        mirror_places.add(first);
        mirror_places.add(second);
        mirror_places.add(third);
        mirror_places.add(fourth);




    }

    public void lvl_18(){

        int[] stars,goal,first,second,third,fourth,fifth,sixth,seventh,eight;

        stars = new int[2];
        goal = new int[4];
        first = new int[4];
        second = new int[4];
        third = new int[4];
        fourth =new int[4];
        fifth = new int[4];
        sixth = new int[4];
        seventh = new int[4];
        eight=new int[4];

        x_canon_place=400;
        y_canon_place=700;


        //TO KNOW STARS
        stars[0]=9;
        stars[1]=11;

        //TH GOAL MIRROR
        goal[0]=0;
        goal[1]= 20;
        goal[2]= 160;
        goal[3]= 20;

        //0:LEFT  1:TOP  2:BOTTOM  3:RIGHT

        // NOOOOTE!!! TOP<BOTTOM


        first[0]=0;
        first[1]=0;
        first[2]=20;
        first[3]= 1080;

        second[0]= 1080-20;
        second[1]=20 ;
        second[2]=1770-getStatusBarHeight();
        second[3]= 1080;

        third[0]= 0;
        third[1]=1770-20-getStatusBarHeight();
        third[2]=1770-getStatusBarHeight();
        third[3]= 1080;

        fourth[0]= 0;
        fourth[1]=400;
        fourth[2]=1770-20-getStatusBarHeight();
        fourth[3]= 20;

        fifth[0]= 0;
        fifth[1]=400-20;
        fifth[2]=400;
        fifth[3]= 400;

        sixth[0]= 400-20;
        sixth[1]=400-200 ;
        sixth[2]=400-20;
        sixth[3]= 400;

        seventh[0]= 500;
        seventh[1]=20 ;
        seventh[2]=400;
        seventh[3]= 500+20;

        eight[0]= 400;
        eight[1]=600 ;
        eight[2]=600+20;
        eight[3]= 700;

        mirror_places.add(stars);
        mirror_places.add(goal);
        mirror_places.add(first);
        mirror_places.add(second);
        mirror_places.add(third);
        mirror_places.add(fourth);
        mirror_places.add(fifth);
        mirror_places.add(sixth);
        mirror_places.add(seventh);
        mirror_places.add(eight);




    }

}
