package com.tugas_besar.laundry_kita_mobile.Activities;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.tugas_besar.laundry_kita_mobile.R;

public class SplashScreenActivity extends AppCompatActivity {

    //Mengeset waktu lamanya splashscreen
    private static int splashInterval = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_splash_screen);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // TODO Auto-generated method stub
                Intent i = new Intent(SplashScreenActivity.this, SignInActivity.class);
                startActivity(i);

                // Jeda selesai Splash_Screen
                this.finish();
            }

            private void finish() {
            }
        }, splashInterval);
    };
}