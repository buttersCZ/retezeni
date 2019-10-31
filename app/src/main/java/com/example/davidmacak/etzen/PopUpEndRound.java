package com.example.davidmacak.etzen;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;

public class PopUpEndRound extends AppCompatActivity {

    NumberPicker picker1;
    NumberPicker picker2;
    NumberPicker picker3;
    NumberPicker picker4;
    NumberPicker picker5;

    int intLevel;
    TextView textViewEndRound;
    String nickname;

    String [] alphabet = { "A", "Á", "B", "C","Č","D", "Ď","E","É","Ě","F","G","H","I", "Í","J","K","L","M","N", "Ň","O","Ó","P","Q","R","Ř","S", "Š","T","Ť","U", "Ú","Ů","V","W","X","Y","Ý","Z","Ž", };


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popupendround);
        textViewEndRound = findViewById(R.id.textViewEndRound);
        setPickers();
        intLevel = getIntent().getIntExtra("intlevelik",1);
        textViewEndRound.setText("Uhádl jsi na " +getIntent().getIntExtra("historySteps",1) +". pokus. Vyplň svou přezdívku");
    }


    private void setPickers(){
        picker1 = findViewById(R.id.skorePicker);
        picker1.setMinValue(0);
        picker1.setMaxValue(40);
        picker1.setDisplayedValues(alphabet);
        picker1.setValue(1);
        picker1.setBackground(getDrawable(R.drawable.picker));
        picker2 = findViewById(R.id.skorePicker1);
        picker2.setMinValue(0);
        picker2.setMaxValue(40);
        picker2.setDisplayedValues(alphabet);
        picker2.setValue(1);
        picker2.setBackground(getDrawable(R.drawable.picker));
        picker3 = findViewById(R.id.skorePicker2);
        picker3.setMinValue(0);
        picker3.setMaxValue(40);
        picker3.setDisplayedValues(alphabet);
        picker3.setValue(1);
        picker3.setBackground(getDrawable(R.drawable.picker));
        picker4 = findViewById(R.id.skorePicker3);
        picker4.setMinValue(0);
        picker4.setMaxValue(40);
        picker4.setDisplayedValues(alphabet);
        picker4.setValue(1);
        picker4.setBackground(getDrawable(R.drawable.picker));
        picker5 = findViewById(R.id.skorePicker4);
        picker5.setMinValue(0);
        picker5.setMaxValue(40);
        picker5.setDisplayedValues(alphabet);
        picker5.setValue(1);
        picker5.setBackground(getDrawable(R.drawable.picker));
    }

    public void nextLevel(View view){
        nickname = alphabet[picker1.getValue()]+alphabet[picker2.getValue()]+alphabet[picker3.getValue()]+alphabet[picker4.getValue()]+alphabet[picker5.getValue()];
        SaveScore saveScore = new SaveScore(intLevel,getIntent().getIntExtra("historySteps",1),nickname);
        Intent intent = new Intent(PopUpEndRound.this,Game.class);
        intent.putExtra("level",intLevel);
        startActivity(intent);
        finish();

    }


    public void returnMenu(View view){
        Intent intent = new Intent(PopUpEndRound.this,MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

}
