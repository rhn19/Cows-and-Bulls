package com.heliumfarticle.koertyre;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        Thread thread = new Thread(){
            public void run(){
                try{
                    sleep(4000);
                }
                catch (Exception e){
                    e.printStackTrace();
                }
                finally {
                    Intent intent = new Intent(SplashScreen.this,MenuActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        };

        thread.start();
    }
}
