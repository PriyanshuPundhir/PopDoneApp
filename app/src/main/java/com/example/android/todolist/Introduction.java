package com.example.android.todolist;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;
import com.example.android.todolist.category.CategoryActivity;

import java.util.Timer;
import java.util.TimerTask;

public class Introduction extends AppCompatActivity {

    Timer timer;
    LottieAnimationView lottieAnimationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introduction);
        getSupportActionBar().hide();
        timer=new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Intent intent = new Intent(Introduction.this, CategoryActivity.class);
                startActivity(intent);
                finish();
            }
        }, 3000);
        lottieAnimationView = findViewById(R.id.animation);
        lottieAnimationView.animate().translationY(1400).setDuration(1000).setStartDelay(4000);

    }
}