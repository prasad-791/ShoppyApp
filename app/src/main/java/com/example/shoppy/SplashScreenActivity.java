package com.example.shoppy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.google.firebase.auth.FirebaseAuth;

public class SplashScreenActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        getSupportActionBar().hide();
        firebaseAuth = FirebaseAuth.getInstance();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(firebaseAuth.getCurrentUser()!=null && firebaseAuth.getCurrentUser().isEmailVerified()) {
                    Intent mainintent = new Intent(SplashScreenActivity.this,MainActivity.class);
                    startActivity(mainintent);
                    finish();
                }else{
                    Intent intent = new Intent(SplashScreenActivity.this,RegisterActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        },3000);
    }
}