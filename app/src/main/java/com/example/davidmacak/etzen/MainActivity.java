package com.example.davidmacak.etzen;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TableRow;

public class MainActivity extends AppCompatActivity {
    Context context;
    ImageButton imageButtonGO;
    Animation animationZoom;
    LinearLayout linearLayoutMain;
    TableRow tableRowHead;
    Button buttonExit;
    Button buttonScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        loadItems();
        setListeners();

    }
    //Load buttons in menu, etc..
    private void loadItems(){
        imageButtonGO = findViewById(R.id.imageButtonGO);
        animationZoom = AnimationUtils.loadAnimation(context,R.anim.zoomin);
        imageButtonGO.setAnimation(animationZoom);
        linearLayoutMain = findViewById(R.id.LinearLayoutMain);
        tableRowHead = findViewById(R.id.TableRowHead);
        buttonExit = findViewById(R.id.buttonExit);
        buttonScore = findViewById(R.id.buttonScore);
    }
    //
    private void setListeners(){

        imageButtonGO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,SelectLevel.class);
                startActivity(intent);
                overridePendingTransition(R.anim.lefttoright,R.anim.righttoleft);

            }
        });

        buttonExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        buttonScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,Score.class);
                startActivity(intent);
            }
        });

    }



    @Override
    public void finish() {
        super.finish();
    }
}
