package com.example.mirrormirror;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;
import java.util.ArrayList;
import java.util.Objects;

public class mirrors extends View {

    int top,left,bottom,right;
    ArrayList<int[]> mirror_place=new ArrayList<>();
    float width,height;

    public mirrors(Context context) {
        super(context);

        int back_color= Color.parseColor("#1F1C1C");
        setBackgroundColor(back_color);

    }

    public void launched(ArrayList<int[]> mirror_places,float width,float height){

        mirror_place=mirror_places;
        this.width=width;
        this.height= height;



    }

    @SuppressLint("NewApi")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        for (int i = 1; i< Objects.requireNonNull(mirror_place.toArray()).length; i++) {

           @SuppressLint("DrawAllocation") final Paint color=new Paint();
           @SuppressLint("DrawAllocation") final Rect mirror=new Rect();


           //GET MIRRORS
            left=mirror_place.get(i)[0];
            top=mirror_place.get(i)[1];
            bottom=mirror_place.get(i)[2];
            right=mirror_place.get(i)[3];

            //SET MIRRORS
            mirror.set(seth(left), seth(top), seth(right), seth(bottom));

            if (i==1) { //IF TH MIRROR IS TH GOAL

                int GREEN= Color.parseColor("#90C418");
                color.setColor(GREEN);
                color.setStyle(Paint.Style.FILL);

            }else{
                int BLUE= Color.parseColor("#3CABE0");
                color.setColor(BLUE);
                color.setStyle(Paint.Style.FILL);
            }

            canvas.drawRect(mirror, color);


        }
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


}
