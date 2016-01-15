package com.myluco.simpletodo;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class SplashActivity extends AppCompatActivity {
    /** Duration of wait **/
    private final int SPLASH_DISPLAY_LENGTH = 500;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.activity_splash);

       Thread timerThread = new Thread() {
           public void run() {
               try{
                   sleep(SPLASH_DISPLAY_LENGTH);
                   Log.v("Splash", "will start main activity");

               }catch (InterruptedException e) {
                   e.printStackTrace();
               }finally{
                    Log.v("Splash", "finally....");
                   Intent intent = new Intent( SplashActivity.this, TodoActivity.class);
                   startActivity(intent);
               }
           }
       };
        timerThread.start();
    }
    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        finish();
    }

}
