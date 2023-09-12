package com.example.mirrormirror;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import java.util.ArrayList;

public class levels_adapter extends BaseAdapter {

    private final static String lvl_key = "com.mouad0.hp.mirrormirror.lvl_key";

    private int levels,width,height;
    private ArrayList<Integer> stars;
    private Context context;
    private Activity activity;


    levels_adapter(Context context, int levels, ArrayList<Integer> stars, Activity activity,int width,int height) {

        this.levels=levels;
        this.context=context;
        this.stars=stars;
        this.activity=activity;
        this.width=width;
        this.height=height;



    }
    @Override
    public int getCount() {
        return levels;
    }

    @Override
    public Object getItem(int i) {
        return levels;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @SuppressLint({"ViewHolder", "ClickableViewAccessibility"})
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        view = LayoutInflater.from(context).
                inflate(R.layout.levels_list, viewGroup, false);


        TextView lvl1=view.findViewById(R.id.numbOFlvl1);
        TextView lvl2=view.findViewById(R.id.numbOFlvl2);
        TextView lvl3=view.findViewById(R.id.numbOFlvl3);

        RelativeLayout layout1=view.findViewById(R.id.relativeLayout1);
        RelativeLayout layout2=view.findViewById(R.id.relativeLayout2);
        RelativeLayout layout3=view.findViewById(R.id.relativeLayout3);


        //SET THE BACKGROUND OF LAYOUT
        layout1.setBackgroundResource(R.drawable.list_levels_background);
        layout2.setBackgroundResource(R.drawable.list_levels_background);
        layout3.setBackgroundResource(R.drawable.list_levels_background);


        //SET THE WIDTH AND HEIGHT OF EACH LAYOUT
        ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) layout1.getLayoutParams();
        params.width = width/3;
        params.height=height/7;
        layout1.setLayoutParams(params);

        ConstraintLayout.LayoutParams params2 = (ConstraintLayout.LayoutParams) layout2.getLayoutParams();
        params2.width = width/3;
        params2.height=height/7;
        layout2.setLayoutParams(params2);

        ConstraintLayout.LayoutParams params3 = (ConstraintLayout.LayoutParams) layout3.getLayoutParams();
        params3.width = width/3;
        params3.height=height/7;
        layout3.setLayoutParams(params3);

        //SET THE HEIGHT OF THE TEXTVIEWS
        RelativeLayout.LayoutParams params7 = (RelativeLayout.LayoutParams) lvl1.getLayoutParams();
        params7.height=height/14;
        lvl1.setLayoutParams(params7);

        RelativeLayout.LayoutParams params8 = (RelativeLayout.LayoutParams) lvl2.getLayoutParams();
        params8.height=height/14;
        lvl2.setLayoutParams(params8);

        RelativeLayout.LayoutParams params9 = (RelativeLayout.LayoutParams) lvl3.getLayoutParams();
        params9.height=height/14;
        lvl3.setLayoutParams(params9);


        if (getCount()>(i+1)*3-3){
            lvl1.setText(String.valueOf((i+1)*3-2)); //SET THE NUMBER OF LVL

            final int a=(i+1)*3-2;
            layout1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    //CHECK IF LVL BEFORE IS DONE
                    if(a-1==0){//CHECK IF FIRST LEVEL
                        Intent intent = new Intent(activity,MainActivity.class);
                        intent.putExtra(lvl_key,String.valueOf(a));
                        context.startActivity(intent);
                    }else if (stars.get(a-2)>0) {
                        Intent intent = new Intent(activity, MainActivity.class);
                        intent.putExtra(lvl_key, String.valueOf(a));
                        context.startActivity(intent);
                    }

                }
            });


            //SET LOCKED LEVEL
            if (a-1!=0&&!(stars.get(a-2)>0)){
                ImageView locked= new ImageView(context);
                locked.setBackgroundResource(R.drawable.locked);
                RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                layout1.addView(locked,layoutParams);

            }


            //STARS
            ImageView star1=view.findViewById(R.id.star1_1);
            ImageView star2=view.findViewById(R.id.star2_1);
            ImageView star3=view.findViewById(R.id.star3_1);

            RelativeLayout.LayoutParams params4 = (RelativeLayout.LayoutParams) star1.getLayoutParams();
            params4.width = width/9-setx();
            params4.height=width/9-sety();
            star1.setLayoutParams(params4);

            RelativeLayout.LayoutParams params5 = (RelativeLayout.LayoutParams) star2.getLayoutParams();
            params5.width = width/9-setx();
            params5.height=width/9-sety();
            star2.setLayoutParams(params5);

            RelativeLayout.LayoutParams params6 = (RelativeLayout.LayoutParams) star3.getLayoutParams();
            params6.width = width/9-setx();
            params6.height=width/9-sety();
            star3.setLayoutParams(params6);

            if (stars.get(a-1)>0){
                star1.setBackgroundResource(R.drawable.star_2);
            }
            if (stars.get(a-1)>1){
                star2.setBackgroundResource(R.drawable.star_2);
            }
            if (stars.get(a-1)>2){
                star3.setBackgroundResource(R.drawable.star_2);
            }


        }
        if (getCount()>(i+1)*3-2){

            lvl2.setText(String.valueOf((i+1)*3-1));  //SET THE NUMBER OF LVL

            final int a=(i+1)*3-1;

            layout2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //CHECK IF LVL BEFORE IS DONE
                    if (stars.get(a-2)>0) {
                        Intent intent = new Intent(activity, MainActivity.class);
                        intent.putExtra(lvl_key, String.valueOf(a));
                        context.startActivity(intent);
                    }
                }
            });

            //SET LOCKED LEVEL
            if (!(stars.get(a-2)>0)){
                ImageView locked= new ImageView(context);
                locked.setBackgroundResource(R.drawable.locked);
                RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                layout2.addView(locked,layoutParams);

            }

            //STARS
            ImageView star1=view.findViewById(R.id.star1_2);
            ImageView star2=view.findViewById(R.id.star2_2);
            ImageView star3=view.findViewById(R.id.star3_2);


            RelativeLayout.LayoutParams params4 = (RelativeLayout.LayoutParams) star1.getLayoutParams();
            params4.width = width/9-setx();
            params4.height=width/9-sety();
            star1.setLayoutParams(params4);

            RelativeLayout.LayoutParams params5 = (RelativeLayout.LayoutParams) star2.getLayoutParams();
            params5.width = width/9-setx();
            params5.height=width/9-sety();
            star2.setLayoutParams(params5);

            RelativeLayout.LayoutParams params6 = (RelativeLayout.LayoutParams) star3.getLayoutParams();
            params6.width = width/9-setx();
            params6.height=width/9-sety();
            star3.setLayoutParams(params6);


            if (stars.get(a-1)>0){
                star1.setBackgroundResource(R.drawable.star_2);
            }
            if (stars.get(a-1)>1){
                star2.setBackgroundResource(R.drawable.star_2);
            }
            if (stars.get(a-1)>2){
                star3.setBackgroundResource(R.drawable.star_2);
            }



        }
        if (getCount()>(i+1)*3-1){

            lvl3.setText(String.valueOf((i+1)*3));  //SET THE NUMBER OF LVL

            final int a=(i+1)*3;

            layout3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    //CHECK IF LVL BEFORE IS DONE
                    if (stars.get(a-2)>0) {
                        Intent intent = new Intent(activity, MainActivity.class);
                        intent.putExtra(lvl_key, String.valueOf(a));
                        context.startActivity(intent);
                    }
                }
            });

            //SET LOCKED LEVEL
            if (!(stars.get(a-2)>0)){
                ImageView locked= new ImageView(context);
                locked.setBackgroundResource(R.drawable.locked);
                RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                layout3.addView(locked,layoutParams);

            }


            //STARS
            ImageView star1=view.findViewById(R.id.star1_3);
            ImageView star2=view.findViewById(R.id.star2_3);
            ImageView star3=view.findViewById(R.id.star3_3);


            RelativeLayout.LayoutParams params4 = (RelativeLayout.LayoutParams) star1.getLayoutParams();
            params4.width = width/9-setx();
            params4.height=width/9-sety();
            star1.setLayoutParams(params4);

            RelativeLayout.LayoutParams params5 = (RelativeLayout.LayoutParams) star2.getLayoutParams();
            params5.width = width/9-setx();
            params5.height=width/9-sety();
            star2.setLayoutParams(params5);

            RelativeLayout.LayoutParams params6 = (RelativeLayout.LayoutParams) star3.getLayoutParams();
            params6.width = width/9-setx();
            params6.height=width/9-sety();
            star3.setLayoutParams(params6);


            if (stars.get(a-1)>0){
                star1.setBackgroundResource(R.drawable.star_2);
            }
            if (stars.get(a-1)>1){
                star2.setBackgroundResource(R.drawable.star_2);
            }
            if (stars.get(a-1)>2){
                star3.setBackgroundResource(R.drawable.star_2);
            }


        }


        if (getCount()>(i+1)*3-3){
            return view;}else {
            view = LayoutInflater.from(context).
                    inflate(R.layout.empty, viewGroup, false);
            return view;
        } //SHOW THE ROW OR NOT
    }


    private int setx(){
        int i;

        i= (int) (((float) 20 *width)/1080);

        return i;
    }

    private int sety(){
        int i;

        i= (int) (((float) 20 *height)/1770);

        return i;
    }
}
