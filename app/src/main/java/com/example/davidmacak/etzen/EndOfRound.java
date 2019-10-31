package com.example.davidmacak.etzen;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class EndOfRound extends  AppCompatActivity   {
    ImageView imageViewStars;
    Context context;

    //Not used class
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.endround);
        context = this;
        imageViewStars = findViewById(R.id.imageViewStar);
        Animation animation = AnimationUtils.loadAnimation(context,R.anim.lefttoright);
        imageViewStars.setAnimation(animation);


    }
}
