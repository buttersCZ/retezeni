package com.example.davidmacak.etzen;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;

public class SelectLevel extends AppCompatActivity {
    SharedPreferences preferences;
    int levelRound;
    Button levelButton[];
    LinearLayout LinearLayoutLevels;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.selectlevel);
        preferences = getSharedPreferences("level",MODE_PRIVATE);
        levelRound = preferences.getInt("levelRound",1);
        LinearLayoutLevels = findViewById(R.id.LinearLayoutLevels);
        createButtons();


    }

    public void selectLevel(View view){
        Intent intent = new Intent(SelectLevel.this,Game.class);
        intent.putExtra("level",view.getId());
        startActivity(intent);
        overridePendingTransition(R.anim.lefttoright,R.anim.righttoleft);
        finish();

    }
    //Used information about done levels from sharedpreferences and use it to display open levels
    private void createButtons(){
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(10,30,10,10);
        levelButton = new Button[10];
        for(int d=1;d<10;d++){
            levelButton[d] = new Button(this);
            if(d<=levelRound){
                levelButton[d].setBackground(getDrawable(R.drawable.border_go));
                //If id of button is equal to last open level, make on this button animation
                if(d==levelRound){
                    Animation animation = AnimationUtils.loadAnimation(this,R.anim.zoominlevel);
                    levelButton[d].setAnimation(animation);
                }
            }
            else {
                levelButton[d].setBackground(getDrawable(R.drawable.border_level_na));

            }
            levelButton[d].setLayoutParams(layoutParams);
            levelButton[d].setPadding(55,30,55,30);
            levelButton[d].setText("Level "+d);
            levelButton[d].setTextSize(20);
            levelButton[d].setId(d);
            final int finalD = d;
            levelButton[d].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectLevel(levelButton[finalD]);
                }
            });
            LinearLayoutLevels.addView(levelButton[d]);
        }
    }

}
