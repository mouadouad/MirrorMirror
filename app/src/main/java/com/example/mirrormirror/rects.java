package com.example.mirrormirror;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.media.MediaPlayer;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.Objects;
import static android.graphics.Rect.intersects;


public class rects extends View {

    public final static String stars_sharedPREFS ="STARS";
    public final static String sound_SHAREDPREFS="sound_SHAREDPREFS";


    SharedPreferences sharedPreferences;
    ArrayList<Integer> stars=new ArrayList<>();
    int numb_of_lvl;
    int first_top, first_left, top_check, left_check, bottom_check,right_check;
    Handler handler;
    Runnable runnable;
    boolean still_traveling=true,intersect=false,won=false;
    ArrayList<int[]> mirror_place=new ArrayList<>(),variables=new ArrayList<>();
    double cos,sin,tan;
    int angle_helper,lastpos;
    int TOP,LEFT,BOTTOM,RIGHT;
    int counter;
    Context context;
    int hypotenuse;
    FrameLayout dim;
    TextView text;
    ImageView star1,star2,star3;
    float width,height;
    boolean sound;



    public rects(Context context) {
        super(context);
        handler=  new Handler();

        int back_color= Color.parseColor("#1F1C1C");
        setBackgroundColor(back_color);



    }

    public void get_stars(ImageView star1, ImageView star2, ImageView star3){
        this.star1=star1;
        this.star2=star2;
        this.star3=star3;

    }

    public void get_launch(int lastposition, ArrayList<int[]> mirror_places, float Yaxe, float Xaxe, float top0, ArrayList<Integer> stars, int numb_of_lvl, SharedPreferences sharedPreferences, Context context, FrameLayout dim, TextView text,float width,float height){

        // GET VARIABLES FROM MAINACTIVITY
        this.stars=stars;
        this.numb_of_lvl=numb_of_lvl;
        this.sharedPreferences=sharedPreferences;
        this.context=context;
        this.dim=dim;
        this.text=text;
        this.width=width;
        this.height=height;
        sound= sharedPreferences.getBoolean(sound_SHAREDPREFS,true);
        lastpos=lastposition;
        mirror_place=mirror_places;
        // CALCULATE HYPOTENUSE
        this.hypotenuse= (int) Math.sqrt(width*width+height*height);


        cos=Math.cos(Math.toRadians(lastposition));
        sin=Math.sin(Math.toRadians(lastposition));
        tan=Math.tan(Math.toRadians(lastposition));



        //SET THE FIRST RECT
        first_left=(int)(Xaxe+sin*(Yaxe-top0)-cos*15);
        first_top=(int) (Yaxe-cos*(Yaxe-top0)-sin*15);


        if (lastpos!=0) {
            first_left = getX_rotated(first_left, first_top, lastpos);
            first_top = getY_rotated((Xaxe + sin * (Yaxe - top0) - cos * 15), first_top, lastpos);
        }

        int[] first_rect_postition;
        first_rect_postition = new int[4];

        first_rect_postition[0]= first_left;
        first_rect_postition[1]= first_top ;
        first_rect_postition[2]= first_top +10;
        first_rect_postition[3]=lastpos;

        variables.add(first_rect_postition);

        counter=1;
        repeat();


    }


    @SuppressLint("NewApi")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


        @SuppressLint("DrawAllocation") final Paint color=new Paint();
        @SuppressLint("DrawAllocation") final Rect mirror=new Rect();
        @SuppressLint("DrawAllocation") final Rect checkLeft=new Rect();


        //IF BUMP INTO MIRROR
        if (intersect){

            MediaPlayer bump = MediaPlayer.create(context, R.raw.bump_sound);
            if (sound) {
                bump.start();
            }

            counter++;


            //GET LAST NORMAL COORDINATES
            double last_left,last_top;


            last_left = getX_normal(variables.get(counter - 2)[0]+15, variables.get(counter - 2)[1]+15, lastpos);
            last_top = getY_normal(variables.get(counter - 2)[0]+15, variables.get(counter - 2)[1]+15, lastpos);



            //SET NEW ANGLE
            lastpos=(20/angle_helper)*180-lastpos;
            cos=Math.cos(Math.toRadians(lastpos));
            sin=Math.sin(Math.toRadians(lastpos));
            tan=Math.tan(Math.toRadians(lastpos));


            // ADD THE NEW COORDINATES
            final  int[] rect_positions;
            rect_positions = new int[4];

            rect_positions[0]=getX_rotated(last_left,last_top,lastpos)-15;
            rect_positions[1]=getY_rotated(last_left,last_top,lastpos)-10;
            rect_positions[2]=getY_rotated(last_left,last_top,lastpos);
            rect_positions[3]=lastpos;


            variables.add(rect_positions);
            intersect=false;

            repeat();

        }

        //DRAW RECTS
        for (int forloop=0;forloop<counter;forloop++){

            //DRAW RECTS

            LEFT= variables.get(forloop)[0];
            TOP= variables.get(forloop)[1] ;
            RIGHT=LEFT+30;
            BOTTOM=variables.get(forloop)[2];

            @SuppressLint("DrawAllocation") final Rect rect= new Rect();
            @SuppressLint("DrawAllocation") final Rect header= new Rect();

            canvas.save();
            canvas.rotate(variables.get(forloop)[3]);

            rect.set(seth(LEFT),seth(TOP), seth(RIGHT), seth(BOTTOM));

            //DRAW HEADER
            canvas.save();
            canvas.rotate(-45);

            int bottom_header = getY_rotated(getX_normal(LEFT, TOP, variables.get(forloop)[3]), getY_normal(LEFT, TOP, variables.get(forloop)[3]), -45.05 + variables.get(forloop)[3]) + 21;
            int right_header = getX_rotated(getX_normal(LEFT, TOP, variables.get(forloop)[3]), getY_normal(LEFT, TOP, variables.get(forloop)[3]), -45.05 + variables.get(forloop)[3]) + 21;
            int top_header = getY_rotated(getX_normal(LEFT, TOP, variables.get(forloop)[3]), getY_normal(LEFT, TOP, variables.get(forloop)[3]), -45.05 + variables.get(forloop)[3]);
            int left_header = getX_rotated(getX_normal(LEFT, TOP, variables.get(forloop)[3]), getY_normal(LEFT, TOP, variables.get(forloop)[3]), -45.05 + variables.get(forloop)[3]);

            header.set(seth(left_header),seth( top_header), seth(right_header),seth(bottom_header));

            int beam_color= Color.parseColor("#C6EEE1");
            @SuppressLint("DrawAllocation")  final  Paint Bcolor=new Paint();
            Bcolor.setColor(beam_color);
            Bcolor.setStyle(Paint.Style.FILL);

            canvas.drawRect(header,Bcolor );
            canvas.restore();
            canvas.drawRect(rect, Bcolor);
            canvas.restore();


        }


        // GET CURRENT POSITION OF RECT
        int theleft,thetop;
        theleft=getX_rotated(getX_normal(LEFT,TOP,variables.get(counter-1)[3]),getY_normal(LEFT,TOP,variables.get(counter-1)[3]),-45.05+variables.get(counter-1)[3])+21;
        thetop=getY_rotated(getX_normal(LEFT,TOP,variables.get(counter-1)[3]),getY_normal(LEFT,TOP,variables.get(counter-1)[3]),-45.05+variables.get(counter-1)[3]);

        // SET THE CHECKING RECTS
        int check_left,check_top,check_right,check_bottom;

        check_left=getX_normal(theleft,thetop,lastpos-45.05);
        check_right=getX_normal(theleft,thetop,lastpos-45.05)+1;
        check_top=getY_normal(theleft,thetop,lastpos-45.05)+1;
        check_bottom=getY_normal(theleft,thetop,lastpos-45.05);

        checkLeft.set(seth(check_left),seth(check_top),seth(check_right),seth(check_bottom));

        //  MIRRORS
        for (int i = 1; i< Objects.requireNonNull(mirror_place.toArray()).length; i++) {

            //GET MIRRORS
            left_check =mirror_place.get(i)[0];
            top_check =mirror_place.get(i)[1];
            bottom_check =mirror_place.get(i)[2];
            right_check =mirror_place.get(i)[3];


            //SET MIRRORS
            mirror.set(seth(left_check), seth(top_check), seth(right_check), seth(bottom_check));


            //IF THE MIRROR IS THE GOAL
            if (i==1) {
                int GREEN= Color.parseColor("#90C418");
                color.setColor(GREEN);
                color.setStyle(Paint.Style.FILL);

                if (intersects(checkLeft,mirror)){
                    win();
                    won=true;
                }

                }else{
                int BLUE= Color.parseColor("#3CABE0");
                color.setColor(BLUE);
                color.setStyle(Paint.Style.FILL);
            }

            canvas.drawRect(mirror, color);


            //INTERSECTION
            if (intersects(checkLeft,mirror)&&i!=1){

                angle_helper=Math.abs(bottom_check-top_check);  //TO KNOW WHAT ANGLE
                intersect = true;

            }

        }

    }

    public void repeat(){

        while (still_traveling&&!intersect&&!won){

            still_traveling=false;

            runnable=new Runnable() {
                @Override
                public void run() {


                    // IF THE RECT IS GOING AWAY OR IS STUCK
                    if (variables.get(counter-1)[2]-variables.get(counter-1)[1]> hypotenuse||counter>25){
                        lost();
                    }else {
                        variables.get(counter - 1)[1] = variables.get(counter - 1)[1] - 8;

                        still_traveling = true;
                        repeat();
                        invalidate();


                    }


                }
            };handler.postDelayed(runnable,1);


        }


    }

    public int getX_rotated(double left,double top,double angle){

        double sin,tan;

        sin=Math.sin(Math.toRadians(angle));
        tan=Math.tan(Math.toRadians(angle));


        return (int) - (-sin*(top-left/-tan));

    }

    public int getY_rotated(double left,double top,double angle){


        double cos,sin,tan;

        cos=Math.cos(Math.toRadians(angle));
        sin=Math.sin(Math.toRadians(angle));
        tan=Math.tan(Math.toRadians(angle));

        return (int) (left/-sin+cos*(top-left/-tan));

    }

    public int getX_normal(double left,double top,double angle){

        double cos,tan;

        cos=Math.cos(Math.toRadians(angle));
        tan=Math.tan(Math.toRadians(angle));



        return (int) (cos * (left -tan * top));


    }

    public int getY_normal(double left,double top,double angle){

        double cos,sin,tan;

        cos=Math.cos(Math.toRadians(angle));
        sin=Math.sin(Math.toRadians(angle));
        tan=Math.tan(Math.toRadians(angle));



        return (int)  (sin * (left  - tan * top) + top / cos);


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

    public void win(){

        //3 STARS
        if (counter-1<=mirror_place.get(0)[0]){
            stars.set(numb_of_lvl-1,3);
        }

        //2 STARS
        if (stars.get(numb_of_lvl-1)<3) {

            if (counter - 1 > mirror_place.get(0)[0] && counter - 1 <= mirror_place.get(0)[1]){
                stars.set(numb_of_lvl-1,2);
            }
        }

        //1 STAR
        if (stars.get(numb_of_lvl-1)<2) {

            if (counter - 1 > mirror_place.get(0)[1]) {
                stars.set(numb_of_lvl - 1, 1);
            }
        }

        //MAKE THE SCREEN DIM
        dim.setVisibility(VISIBLE);

        //SHOW THE TEXTVIEW
        text.setText(R.string.lvl_completed);


        //SHOW THE STARS
        star1.setVisibility(VISIBLE);
        star2.setVisibility(VISIBLE);
        star3.setVisibility(VISIBLE);

        //ANIMATE STARS
        final MediaPlayer shine=MediaPlayer.create(context,R.raw.star_sound);

        Handler handler2=new Handler();
        Runnable runnable2;

        runnable2=new Runnable() {
            @Override
            public void run() {

                if (sound) {
                    shine.start();
                }

            }
        };handler2.postDelayed(runnable2,500);

        runnable2=new Runnable() {
            @Override
            public void run() {
                star1.setBackgroundResource(R.drawable.star_2);
                if (counter-1<=mirror_place.get(0)[1]) {
                    if (sound) {
                        shine.start();
                    }
                }

            }
        };handler2.postDelayed(runnable2,1500);

        Handler handler3=new Handler();
        Runnable runnable3;

        runnable3=new Runnable() {
            @Override
            public void run() {
                if (counter-1<=mirror_place.get(0)[1]){
                    star2.setBackgroundResource(R.drawable.star_2);
                    if (counter-1<=mirror_place.get(0)[0]) {
                        if (sound) {
                            shine.start();
                        }
                    }

                }
            }
        };handler3.postDelayed(runnable3,2500);

        Handler handler4=new Handler();
        Runnable runnable4;

        runnable4=new Runnable() {
            @Override
            public void run() {
                if (counter-1<=mirror_place.get(0)[0]){
                    star3.setBackgroundResource(R.drawable.star_2);
                }
            }
        };handler4.postDelayed(runnable4,3500);



        //GO TO LEVELS WHEN FINISHED
        Handler handler1=new Handler();
        Runnable runnable1;

        runnable1=new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(context,Levels.class);
                context.startActivity(i);
            }
        };handler1.postDelayed(runnable1,4500);



        save();
    }

    public void lost(){

        //MAKE THE SCREEN DIM
        dim.setVisibility(VISIBLE);

        //SHOW THE TEXTVIEW
        text.setText(R.string.lvl_failed);
        text.setTextColor(Color.RED);


        // REFRESH WHEN FINISHED
        Handler handler1=new Handler();
        Runnable runnable1;

        runnable1=new Runnable() {
            @Override
            public void run() {
                ((MainActivity)context).recreate();

            }
        };handler1.postDelayed(runnable1,2000);
    }

    public void save(){

        SharedPreferences.Editor editor=sharedPreferences.edit();

        Gson gson = new Gson();
        String json = gson.toJson(stars);
        editor.putString(stars_sharedPREFS,json);

        editor.apply();

    }

    public void pause(){

        won=true;
    }

    public void resume(){
        won=false;
        //SEE IF STARTED OR NOT
       if(counter>=1){ repeat();}

    }



}
