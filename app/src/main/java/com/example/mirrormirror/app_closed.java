package com.example.mirrormirror;

import android.os.Handler;


class app_closed {

    void activity_closed(){

        android.os.Handler handler2=new Handler();
        Runnable runnable2;


        runnable2=new Runnable() {
            @Override
            public void run() {


                if (Settings.opened==0&&Start.opened==0&&Levels.opened==0&&MainActivity.opened==0){

                    Start.music.pause();

                }
                Settings.opened=0;
                Start.opened=0;
                Levels.opened=0;
                MainActivity.opened=0;


            }
        };handler2.postDelayed(runnable2,30);



    }





}
