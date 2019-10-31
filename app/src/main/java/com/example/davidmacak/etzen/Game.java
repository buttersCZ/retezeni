package com.example.davidmacak.etzen;

import android.animation.Animator;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.BounceInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.ScrollView;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.Objects;

public class Game extends AppCompatActivity {

    ImageView imageViewStars;
    ImageView imageViewArrowDown;
    ImageView imageViewStar1;
    ImageView imageViewStar2;
    ImageView imageViewStar3;
    ImageView imageViewStar4;
    ImageView imageViewStar5;
    TextView[] textViewTextEnter;
    TextView[] textViewTextExit;
    TextView[] textViewTextHistory;
    TextView textViewCountChange;
    TextView textViewZpet;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    SharedPreferences preferences2;
    SharedPreferences.Editor editor2;
    int intLevel;
    String stringWordEnter;
    String stringWordExit;
    String stringWordHistory;
    String[] stringStepsHistory = new String[100];
    String[] stringStepsHistoryPreferences = new String[100];
    String stringLastWord;
    String pismeno;
    String [] alphabet = { "A", "Á", "B", "C","Č","D", "Ď","E","É","Ě","F","G","H","I", "Í","J","K","L","M","N", "Ň","O","Ó","P","Q","R","Ř","S", "Š","T","Ť","U", "Ú","Ů","V","W","X","Y","Ý","Z","Ž", };
    LinearLayout LinearLayoutOkClick;
    LinearLayout LinearLayoutEnterWord;
    LinearLayout LinearLayoutExitWord;
    LinearLayout LinearLayoutHistory;
    LinearLayout LinearLayoutHistoryOther;
    LinearLayout linearLayoutEnd;
    LinearLayout linearLayoutMain;
    FrameLayout.LayoutParams lparams2 = new FrameLayout.LayoutParams(165,150);
    FrameLayout FrameLayoutStars;
    FrameLayout[] frameLayoutEnter;
    FrameLayout[] frameLayoutExit;
    FrameLayout[] frameLayoutHistory;
    ScrollView ScrollViewHistory;
    TableRow tableRowControl;
    NumberPicker picker1;
    NumberPicker picker2;
    NumberPicker picker3;
    ImageButton imageButtonOK;
    ImageButton imageButtonBack;
    int idPicker;
    boolean booleanWordExist=true;
    int intHistorySteps = 0;
    public SQLiteDatabase mDataBase;
    public static String DB_NAME = "slovnik.sqlite";
    private static String DB_PATH = "";
    Cursor cursor;
    Context context;
    boolean endHandler = true;
    int intLevelGetIntent = 1;
    StringBuilder sb = new StringBuilder();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game);
        loadItems();
        context = this;
        intLevelGetIntent = getIntent().getIntExtra("level",1);
        getIntent().removeExtra("level");
        loadPreferences();
        loadLevel();
        loadPickers();
        loadWordEnter();
        loadWordExit();
        loadListeners();
        Db db = new Db(getApplicationContext());
        db.createDatabase();


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(Game.this,SelectLevel.class);
        startActivity(intent);
        finish();
    }

    private void loadWordEnter() {
        textViewTextEnter = new TextView[stringWordEnter.length()];
        frameLayoutEnter = new FrameLayout[stringWordEnter.length()];
        FrameLayout.LayoutParams lparams;
        lparams= new FrameLayout.LayoutParams(165, 150);
        lparams.gravity = Gravity.CENTER;
        LinearLayout.LayoutParams txtParams = new LinearLayout.LayoutParams(165, 150);
        for (int i = 0; i < stringWordEnter.length(); i++) {
            textViewTextEnter[i] = new TextView(this);
            textViewTextEnter[i].setLayoutParams(txtParams);
            textViewTextEnter[i].setTextColor(Color.WHITE);
            textViewTextEnter[i].setTextSize(30);
            textViewTextEnter[i].setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
            frameLayoutEnter[i] = new FrameLayout(this);
            frameLayoutEnter[i].setBackground(getDrawable(R.drawable.border_enter));
            frameLayoutEnter[i].setLayoutParams(lparams);
            final int finalI = i;
            frameLayoutEnter[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    changeLetter(finalI);
                }
            });
            textViewTextEnter[i].setText(stringWordEnter.substring(i, i + 1));
            LinearLayoutEnterWord.addView(frameLayoutEnter[i]);
            frameLayoutEnter[i].addView(textViewTextEnter[i]);

        }
    }

    private void loadWordExit() {
        textViewTextExit = new TextView[stringWordExit.length()];
        frameLayoutExit = new FrameLayout[stringWordExit.length()];
        FrameLayout.LayoutParams lparams = new FrameLayout.LayoutParams(150, 120);
        lparams.gravity = Gravity.CENTER;
        LinearLayout.LayoutParams txtParams = new LinearLayout.LayoutParams(150, 120);
        for (int i = 0; i < stringWordExit.length(); i++) {
            textViewTextExit[i] = new TextView(this);
            textViewTextExit[i].setLayoutParams(txtParams);
            textViewTextExit[i].setTextColor(Color.BLACK);
            textViewTextExit[i].setTextSize(30);
            textViewTextExit[i].setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
            frameLayoutExit[i] = new FrameLayout(this);
            frameLayoutExit[i].setBackground(getDrawable(R.drawable.border_exit));
            frameLayoutExit[i].setLayoutParams(lparams);
            textViewTextExit[i].setText(stringWordExit.substring(i, i + 1));
            LinearLayoutExitWord.addView(frameLayoutExit[i]);
            frameLayoutExit[i].addView(textViewTextExit[i]);

        }


    }

    private void loadWordHistory() {
        if(ScrollViewHistory.getVisibility()==View.GONE){
            ScrollViewHistory.setVisibility(View.VISIBLE);
        }
        LinearLayout.LayoutParams txtParams = new LinearLayout.LayoutParams(75, 50);
        LinearLayout.LayoutParams txtParams2 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, 50);
        textViewTextHistory = new TextView[stringWordExit.length()];
        frameLayoutHistory = new FrameLayout[stringWordExit.length()];
        textViewCountChange = new TextView(this);
        textViewCountChange.setLayoutParams(txtParams2);
        textViewCountChange.setTextColor(Color.WHITE);
        textViewCountChange.setTextSize(11);
        textViewCountChange.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
        textViewCountChange.setText(intHistorySteps+". ");
        textViewCountChange.setTextSize(11);
        textViewCountChange.setTypeface(null, Typeface.BOLD);
        FrameLayout.LayoutParams lparams = new FrameLayout.LayoutParams(75, 50);
        lparams.gravity = Gravity.CENTER;
        LinearLayout.LayoutParams layoutHistoryParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutHistoryParams.gravity = Gravity.CENTER;
        LinearLayoutHistoryOther = new LinearLayout(this);
        LinearLayoutHistoryOther.setOrientation(LinearLayout.HORIZONTAL);
        LinearLayoutHistoryOther.setLayoutParams(layoutHistoryParams);
        LinearLayoutHistoryOther.addView(textViewCountChange);
        for (int i = 0; i < stringWordHistory.length(); i++) {
            System.out.println("Co je tady za sracku? = "+stringWordHistory.substring(i, i + 1));
            textViewTextHistory[i] = new TextView(this);
            textViewTextHistory[i].setLayoutParams(txtParams);
            textViewTextHistory[i].setTextColor(Color.BLACK);
            textViewTextHistory[i].setTextSize(11);
            textViewTextHistory[i].setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
            frameLayoutHistory[i] = new FrameLayout(this);
            frameLayoutHistory[i].setBackground(getDrawable(R.drawable.border_exit));
            frameLayoutHistory[i].setLayoutParams(lparams);
            textViewTextHistory[i].setText(stringWordHistory.substring(i, i + 1));
            frameLayoutHistory[i].addView(textViewTextHistory[i]);
            LinearLayoutHistoryOther.addView(frameLayoutHistory[i]);
        }
        LinearLayoutHistory.addView(LinearLayoutHistoryOther,1);

    }

    private void changeLetter(int id){
        System.out.println("Jeden level = "+intLevel+" a druhy level = "+intLevelGetIntent);
        if(intLevel!=intLevelGetIntent){
            Toast.makeText(context,"Toto kolo jsi jiz jednou dohral",Toast.LENGTH_SHORT).show();
            return;
        }
        if(endHandler){
            idPicker = id;
            lparams2.gravity = Gravity.CENTER;
            switch (stringWordEnter.length()){
                case 3:
                    switch (id){
                        case 0:
                            LinearLayoutEnterWord.removeAllViews();
                            picker1.setValue(Arrays.asList(alphabet).indexOf(textViewTextEnter[id].getText()));
                            picker1.setLayoutParams(lparams2);
                            LinearLayoutEnterWord.addView(picker1);
                            LinearLayoutEnterWord.addView(frameLayoutEnter[1]);
                            LinearLayoutEnterWord.addView(frameLayoutEnter[2]);

                            break;
                        case 1:
                            LinearLayoutEnterWord.removeAllViews();
                            picker2.setValue(Arrays.asList(alphabet).indexOf(textViewTextEnter[id].getText()));
                            picker2.setLayoutParams(lparams2);
                            LinearLayoutEnterWord.addView(frameLayoutEnter[0]);
                            LinearLayoutEnterWord.addView(picker2);
                            LinearLayoutEnterWord.addView(frameLayoutEnter[2]);
                            break;
                        case 2:
                            LinearLayoutEnterWord.removeAllViews();
                            picker3.setValue(Arrays.asList(alphabet).indexOf(textViewTextEnter[id].getText()));
                            picker3.setLayoutParams(lparams2);
                            LinearLayoutEnterWord.addView(frameLayoutEnter[0]);
                            LinearLayoutEnterWord.addView(frameLayoutEnter[1]);
                            LinearLayoutEnterWord.addView(picker3);
                            break;
                    }
                    break;
                case 4:
                    switch (id){
                        case 0:
                            LinearLayoutEnterWord.removeAllViews();
                            picker1.setValue(Arrays.asList(alphabet).indexOf(textViewTextEnter[id].getText()));
                            picker1.setLayoutParams(lparams2);
                            LinearLayoutEnterWord.addView(picker1);
                            LinearLayoutEnterWord.addView(frameLayoutEnter[1]);
                            LinearLayoutEnterWord.addView(frameLayoutEnter[2]);
                            LinearLayoutEnterWord.addView(frameLayoutEnter[3]);

                            break;
                        case 1:
                            LinearLayoutEnterWord.removeAllViews();
                            picker2.setValue(Arrays.asList(alphabet).indexOf(textViewTextEnter[id].getText()));
                            picker2.setLayoutParams(lparams2);
                            LinearLayoutEnterWord.addView(frameLayoutEnter[0]);
                            LinearLayoutEnterWord.addView(picker2);
                            LinearLayoutEnterWord.addView(frameLayoutEnter[2]);
                            LinearLayoutEnterWord.addView(frameLayoutEnter[3]);
                            break;
                        case 2:
                            LinearLayoutEnterWord.removeAllViews();
                            picker3.setValue(Arrays.asList(alphabet).indexOf(textViewTextEnter[id].getText()));
                            picker3.setLayoutParams(lparams2);
                            LinearLayoutEnterWord.addView(frameLayoutEnter[0]);
                            LinearLayoutEnterWord.addView(frameLayoutEnter[1]);
                            LinearLayoutEnterWord.addView(picker3);
                            LinearLayoutEnterWord.addView(frameLayoutEnter[3]);
                            break;
                        case 3:
                            LinearLayoutEnterWord.removeAllViews();
                            picker3.setValue(Arrays.asList(alphabet).indexOf(textViewTextEnter[id].getText()));
                            picker3.setLayoutParams(lparams2);
                            LinearLayoutEnterWord.addView(frameLayoutEnter[0]);
                            LinearLayoutEnterWord.addView(frameLayoutEnter[1]);
                            LinearLayoutEnterWord.addView(frameLayoutEnter[2]);
                            LinearLayoutEnterWord.addView(picker3);
                            break;
                    }
                    break;
            }

        }

    }

    private void loadPickers(){
        picker1 = new NumberPicker(this);
        picker1.setMinValue(0);
        picker1.setMaxValue(40);
        picker1.setDisplayedValues(alphabet);
        picker1.setValue(3);
        picker1.setBackground(getDrawable(R.drawable.picker));
        picker2 = new NumberPicker(this);
        picker2.setBackground(getDrawable(R.drawable.picker));
        picker2.setMinValue(0);
        picker2.setMaxValue(40);
        picker2.setDisplayedValues(alphabet);
        picker3 = new NumberPicker(this);
        picker3.setBackground(getDrawable(R.drawable.picker));
        picker3.setMinValue(0);
        picker3.setMaxValue(40);
        picker3.setDisplayedValues(alphabet);
    }

    private void loadPreferences(){
        preferences = getSharedPreferences("level", MODE_PRIVATE);
        editor = preferences.edit();
        intLevel = preferences.getInt("levelRound", 1);
        stringLastWord = preferences.getString("lastWord","");
        intHistorySteps = preferences.getInt("historyCounts",0);
        preferences2 = getSharedPreferences(String.valueOf(intLevelGetIntent),MODE_PRIVATE);
        editor2 = preferences2.edit();


        if(intLevel!=intLevelGetIntent){

            for(int d=0;d<stringStepsHistoryPreferences.length;d++){
                stringStepsHistory[d] = stringStepsHistoryPreferences[d];
            }
        }


    }

    private void loadLevel(){
        int id = intLevelGetIntent;
        if(intLevel!= intLevelGetIntent && intLevelGetIntent!=0){
            id = intLevelGetIntent;
        }
        else if (intLevelGetIntent==0) {
            id = intLevel;
        }

        switch (id) {
            case 1:
                stringWordEnter = "LUM";
                stringWordExit = "RUM";
                stringWordHistory = "CIT";
                break;
            case 2:
                stringWordEnter = "LUSK";
                stringWordExit = "RADA";
                stringWordHistory = "POLE";
                break;
            case 3:
                stringWordEnter = "ROHY";
                stringWordExit = "KOZA";
                stringWordHistory = "POLE";
                break;
            case 4:
                stringWordEnter = "PRUTY";
                stringWordExit = "RUNDA";
                stringWordHistory = "POLE";
                break;
            case 5:
                stringWordEnter = "LEPA";
                stringWordExit = "KOZA";
                stringWordHistory = "POLE";
                break;
            case 6:
                stringWordEnter = "ROHY";
                stringWordExit = "KOZA";
                stringWordHistory = "POLE";
                break;
            case 7:
                stringWordEnter = "ROHY";
                stringWordExit = "KOZA";
                stringWordHistory = "POLE";
                break;
            case 8:
                stringWordEnter = "ROHY";
                stringWordExit = "KOZA";
                stringWordHistory = "POLE";
                break;
            case 9:
                stringWordEnter = "ROHY";
                stringWordExit = "KOZA";
                stringWordHistory = "POLE";
                break;
            case 10:
                stringWordEnter = "ROHY";
                stringWordExit = "KOZA";
                stringWordHistory = "POLE";
                break;
            case 11:
                stringWordEnter = "ROHY";
                stringWordExit = "KOZA";
                stringWordHistory = "POLE";
                break;
            case 12:
                stringWordEnter = "ROHY";
                stringWordExit = "KOZA";
                stringWordHistory = "POLE";
                break;
            case 13:
                stringWordEnter = "ROHY";
                stringWordExit = "KOZA";
                stringWordHistory = "POLE";
                break;
            case 14:
                stringWordEnter = "ROHY";
                stringWordExit = "KOZA";
                stringWordHistory = "POLE";
                break;
            case 15:
                stringWordEnter = "ROHY";
                stringWordExit = "KOZA";
                stringWordHistory = "POLE";
                break;
            case 16:
                stringWordEnter = "ROHY";
                stringWordExit = "KOZA";
                stringWordHistory = "POLE";
                break;

        }
        if(!stringLastWord.equals("") && intLevel==intLevelGetIntent){
            stringWordEnter = stringLastWord;

        }
        loadHistorySteps();
        if(!stringLastWord.equals("")){
            for(int d=0;d<intHistorySteps;d++){
                stringWordHistory = stringStepsHistory[d];
                int docasne = intHistorySteps;
                intHistorySteps=d+1;
                loadWordHistory();
                intHistorySteps = docasne;
            }
        }
        else {
            ScrollViewHistory.setVisibility(View.GONE);
        }
    }

    private void loadListeners(){


        imageButtonOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(intLevel!=intLevelGetIntent){
                    Toast.makeText(context,"Toto kolo jsi jiz jednou dohral",Toast.LENGTH_SHORT).show();
                    return;
                }

                String stringWordAsk="";
                stringWordHistory = stringWordEnter;
                for (int w = 0; w < stringWordEnter.length(); w++) {
                    if (w == idPicker && idPicker>-1 && endHandler) {
                        endHandler = false;
                        switch (stringWordEnter.length()){
                            case 3:
                                switch (idPicker) {
                                    case 0:
                                        pismeno = Arrays.asList(alphabet).get(picker1.getValue());
                                        stringWordAsk = pismeno.concat(stringWordEnter.substring(1,2).concat(stringWordEnter.substring(2,3)));
                                        checkWord(stringWordAsk);
                                        break;
                                    case 1:
                                        pismeno = Arrays.asList(alphabet).get(picker2.getValue());
                                        stringWordAsk = stringWordEnter.substring(0,1).concat(pismeno).concat(stringWordEnter.substring(2,3));
                                        checkWord(stringWordAsk);
                                        break;
                                    case 2:
                                        pismeno = Arrays.asList(alphabet).get(picker3.getValue());
                                        stringWordAsk = stringWordEnter.substring(0,1).concat(stringWordEnter.substring(1,2)).concat(pismeno);
                                        checkWord(stringWordAsk);
                                        break;
                                }
                                break;
                            case 4:
                                switch (idPicker) {
                                    case 0:
                                        pismeno = Arrays.asList(alphabet).get(picker1.getValue());
                                        stringWordAsk = pismeno.concat(stringWordEnter.substring(1,2).concat(stringWordEnter.substring(2,3)).concat(stringWordEnter.substring(3,4)));
                                        checkWord(stringWordAsk);
                                        break;
                                    case 1:
                                        pismeno = Arrays.asList(alphabet).get(picker2.getValue());
                                        stringWordAsk = stringWordEnter.substring(0,1).concat(pismeno).concat(stringWordEnter.substring(2,3)).concat(stringWordEnter.substring(3,4));
                                        checkWord(stringWordAsk);
                                        break;
                                    case 2:
                                        pismeno = Arrays.asList(alphabet).get(picker3.getValue());
                                        stringWordAsk = stringWordEnter.substring(0,1).concat(stringWordEnter.substring(1,2)).concat(pismeno).concat(stringWordEnter.substring(3,4));
                                        checkWord(stringWordAsk);
                                        break;
                                    case 3:
                                        pismeno = Arrays.asList(alphabet).get(picker3.getValue());
                                        stringWordAsk = stringWordEnter.substring(0,1).concat(stringWordEnter.substring(1,2)).concat(stringWordEnter.substring(2,3)).concat(pismeno);
                                        checkWord(stringWordAsk);
                                        break;
                                }
                                break;
                        }

                        if(booleanWordExist){
                            MediaPlayer mp = MediaPlayer.create(context, R.raw.okword);
                            mp.start();
                            System.out.println("Tady to jeste jede");

                            stringWordEnter = stringWordAsk;
                            stringWordHistory = stringWordAsk;
                            stringLastWord = stringWordAsk;
                            stringStepsHistory[intHistorySteps]= stringWordAsk;
                            sb = new StringBuilder();
                            for(int d=0;d<=intHistorySteps;d++){
                                sb.append(stringStepsHistory[d]).append(",");

                            }
                            intHistorySteps++;
                            editor.putString("historyWords",sb.toString());
                            editor.putInt("historyCounts",intHistorySteps);
                            editor.putString("lastWord",stringLastWord);
                            editor.commit();
                            loadWordHistory();
                            LinearLayoutEnterWord.removeAllViews();
                            loadWordEnter();
                            frameLayoutEnter[idPicker].setBackground(getDrawable(R.drawable.border_pl));
                            final Handler handler = new Handler();
                            final Runnable r = new Runnable() {
                                public void run() {
                                    frameLayoutEnter[idPicker].setBackground(getDrawable(R.drawable.border_enter));
                                    endHandler= true;
                                    idPicker=-1;
                                }
                            };
                            System.out.println("1");

                            handler.postDelayed(r, 500);
                            if(stringWordEnter.equals(stringWordExit)){
                                System.out.println("2");

                                FrameLayoutStars.setVisibility(View.VISIBLE);
                                Animation animation = AnimationUtils.loadAnimation(context,R.anim.bounce);
                                // imageViewStar1.setAnimation(animation);
                                DisplayMetrics dm = new DisplayMetrics();
                                getWindowManager().getDefaultDisplay().getMetrics(dm);
                                imageViewStar1.animate().setInterpolator( new BounceInterpolator());
                                imageViewStar1.animate().setStartDelay(10);
                                imageViewStar1.animate().setDuration(7000);
                                imageViewStar1.animate().translationY(dm.heightPixels);
                                imageViewStar1.animate().start();
                                //  imageViewStar1.setAnimation(animation);
                                imageViewStar2.animate().setInterpolator( new BounceInterpolator());
                                imageViewStar2.animate().setStartDelay(30);
                                imageViewStar2.animate().setDuration(6000);
                                imageViewStar2.animate().translationY(dm.heightPixels);
                                imageViewStar2.animate().start();

                                imageViewStar3.animate().setInterpolator( new BounceInterpolator());
                                imageViewStar3.animate().setStartDelay(5);
                                imageViewStar3.animate().setDuration(8000);
                                imageViewStar3.animate().translationY(dm.heightPixels);
                                imageViewStar3.animate().start();

                                imageViewStar4.animate().setInterpolator( new BounceInterpolator());
                                imageViewStar4.animate().setStartDelay(15);
                                imageViewStar4.animate().setDuration(7000);
                                imageViewStar4.animate().translationY(dm.heightPixels);
                                imageViewStar5.animate().start();

                                imageViewStar5.animate().setInterpolator( new BounceInterpolator());
                                imageViewStar5.animate().setStartDelay(20);
                                imageViewStar5.animate().setDuration(7000);
                                imageViewStar5.animate().translationY(dm.heightPixels);
                                imageViewStar5.animate().start();


                                final Handler handler2 = new Handler();
                                final Runnable r2 = new Runnable() {
                                    public void run() {

                                        Intent intent = new Intent(Game.this,PopUpEndRound.class);
                                        intent.putExtra("intlevelik",intLevel);
                                        intent.putExtra("historySteps",intHistorySteps);

                                        if(intLevel!=intLevelGetIntent){

                                        }
                                        else {
                                            intLevel++;
                                        }

                                        editor.putInt("levelRound",intLevel);
                                        editor.remove("lastWord");
                                        editor.remove("historyCounts");
                                        editor.remove("historyWords");
                                        editor.commit();

                                        editor2.putInt("historyCounts",intHistorySteps);
                                        editor2.putString("lastWord",stringLastWord);
                                        editor2.putString("historyWords",sb.toString());
                                        editor2.commit();
                                        startActivity(intent);
                                        finish();

                                    }
                                };

                                handler2.postDelayed(r2, 8000);

                            }
                        }
                        else {
                            MediaPlayer mp = MediaPlayer.create(context, R.raw.errorsound);
                            mp.start();
                            LinearLayoutEnterWord.removeAllViews();
                            loadWordEnter();
                            frameLayoutEnter[idPicker].setBackground(getDrawable(R.drawable.border_red));
                            Animation animation = AnimationUtils.loadAnimation(context,R.anim.shake);
                            frameLayoutEnter[idPicker].setAnimation(animation);
                            final Handler handler = new Handler();
                            final Runnable r = new Runnable() {
                                public void run() {
                                    frameLayoutEnter[idPicker].setBackground(getDrawable(R.drawable.border_enter));
                                    endHandler= true;
                                    idPicker=-1;
                                    //handler.postDelayed(this, 3000);
                                }
                            };

                            handler.postDelayed(r, 500);
                        }

                    }
                    else if(idPicker<0){
                        Toast.makeText(context,"Vyber písmeno!",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        LinearLayoutOkClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(intLevel!=intLevelGetIntent){
                    Toast.makeText(context,"Toto kolo jsi jiz jednou dohral",Toast.LENGTH_SHORT).show();
                    return;
                }

                String stringWordAsk="";
                stringWordHistory = stringWordEnter;
                for (int w = 0; w < stringWordEnter.length(); w++) {
                    if (w == idPicker && idPicker>-1 && endHandler) {
                        endHandler = false;
                        switch (stringWordEnter.length()){
                            case 3:
                                switch (idPicker) {
                                    case 0:
                                        pismeno = Arrays.asList(alphabet).get(picker1.getValue());
                                        stringWordAsk = pismeno.concat(stringWordEnter.substring(1,2).concat(stringWordEnter.substring(2,3)));
                                        checkWord(stringWordAsk);
                                        break;
                                    case 1:
                                        pismeno = Arrays.asList(alphabet).get(picker2.getValue());
                                        stringWordAsk = stringWordEnter.substring(0,1).concat(pismeno).concat(stringWordEnter.substring(2,3));
                                        checkWord(stringWordAsk);
                                        break;
                                    case 2:
                                        pismeno = Arrays.asList(alphabet).get(picker3.getValue());
                                        stringWordAsk = stringWordEnter.substring(0,1).concat(stringWordEnter.substring(1,2)).concat(pismeno);
                                        checkWord(stringWordAsk);
                                        break;
                                }
                                break;
                            case 4:
                                switch (idPicker) {
                                    case 0:
                                        pismeno = Arrays.asList(alphabet).get(picker1.getValue());
                                        stringWordAsk = pismeno.concat(stringWordEnter.substring(1,2).concat(stringWordEnter.substring(2,3)).concat(stringWordEnter.substring(3,4)));
                                        checkWord(stringWordAsk);
                                        break;
                                    case 1:
                                        pismeno = Arrays.asList(alphabet).get(picker2.getValue());
                                        stringWordAsk = stringWordEnter.substring(0,1).concat(pismeno).concat(stringWordEnter.substring(2,3)).concat(stringWordEnter.substring(3,4));
                                        checkWord(stringWordAsk);
                                        break;
                                    case 2:
                                        pismeno = Arrays.asList(alphabet).get(picker3.getValue());
                                        stringWordAsk = stringWordEnter.substring(0,1).concat(stringWordEnter.substring(1,2)).concat(pismeno).concat(stringWordEnter.substring(3,4));
                                        checkWord(stringWordAsk);
                                        break;
                                    case 3:
                                        pismeno = Arrays.asList(alphabet).get(picker3.getValue());
                                        stringWordAsk = stringWordEnter.substring(0,1).concat(stringWordEnter.substring(1,2)).concat(stringWordEnter.substring(2,3)).concat(pismeno);
                                        checkWord(stringWordAsk);
                                        break;
                                }
                                break;
                        }

                        if(booleanWordExist){
                            MediaPlayer mp = MediaPlayer.create(context, R.raw.okword);
                            mp.start();
                            stringWordEnter = stringWordAsk;
                            stringWordHistory = stringWordEnter;
                            stringLastWord = stringWordAsk;
                            stringStepsHistory[intHistorySteps]= stringWordHistory;
                            sb = new StringBuilder();
                            for(int d=0;d<=intHistorySteps;d++){
                                sb.append(stringStepsHistory[d]).append(",");

                            }
                            intHistorySteps++;
                            editor.putString("historyWords",sb.toString());
                            editor.putInt("historyCounts",intHistorySteps);
                            editor.putString("lastWord",stringLastWord);
                            editor.commit();
                            loadWordHistory();
                            LinearLayoutEnterWord.removeAllViews();
                            loadWordEnter();
                            frameLayoutEnter[idPicker].setBackground(getDrawable(R.drawable.border_pl));
                            final Handler handler = new Handler();
                            final Runnable r = new Runnable() {
                                public void run() {
                                    frameLayoutEnter[idPicker].setBackground(getDrawable(R.drawable.border_enter));
                                    endHandler= true;
                                    idPicker=-1;
                                }
                            };
                            handler.postDelayed(r, 500);

                            if(stringWordEnter.equals(stringWordExit)){

                                FrameLayoutStars.setVisibility(View.VISIBLE);
                                Animation animation = AnimationUtils.loadAnimation(context,R.anim.bounce);
                                // imageViewStar1.setAnimation(animation);
                                DisplayMetrics dm = new DisplayMetrics();
                                getWindowManager().getDefaultDisplay().getMetrics(dm);
                                imageViewStar1.animate().setInterpolator( new BounceInterpolator());
                                imageViewStar1.animate().setStartDelay(10);
                                imageViewStar1.animate().setDuration(7000);
                                imageViewStar1.animate().translationY(dm.heightPixels);
                                imageViewStar1.animate().start();
                                //  imageViewStar1.setAnimation(animation);
                                imageViewStar2.animate().setInterpolator( new BounceInterpolator());
                                imageViewStar2.animate().setStartDelay(30);
                                imageViewStar2.animate().setDuration(6000);
                                imageViewStar2.animate().translationY(dm.heightPixels);
                                imageViewStar2.animate().start();

                                imageViewStar3.animate().setInterpolator( new BounceInterpolator());
                                imageViewStar3.animate().setStartDelay(5);
                                imageViewStar3.animate().setDuration(8000);
                                imageViewStar3.animate().translationY(dm.heightPixels);
                                imageViewStar3.animate().start();

                                imageViewStar4.animate().setInterpolator( new BounceInterpolator());
                                imageViewStar4.animate().setStartDelay(15);
                                imageViewStar4.animate().setDuration(7000);
                                imageViewStar4.animate().translationY(dm.heightPixels);
                                imageViewStar5.animate().start();

                                imageViewStar5.animate().setInterpolator( new BounceInterpolator());
                                imageViewStar5.animate().setStartDelay(20);
                                imageViewStar5.animate().setDuration(7000);
                                imageViewStar5.animate().translationY(dm.heightPixels);
                                imageViewStar5.animate().start();


                                final Handler handler2 = new Handler();
                                final Runnable r2 = new Runnable() {
                                    public void run() {

                                        Intent intent = new Intent(Game.this,PopUpEndRound.class);
                                        intent.putExtra("intlevelik",intLevel);
                                        intent.putExtra("historySteps",intHistorySteps);

                                        if(intLevel!=intLevelGetIntent){

                                        }
                                        else {
                                            intLevel++;
                                        }


                                        editor.putInt("levelRound",intLevel);
                                        editor.remove("lastWord");
                                        editor.remove("historyCounts");
                                        editor.remove("historyWords");
                                        editor.commit();

                                        editor2.putString("historyWords",sb.toString());
                                        editor2.putInt("historyCounts",intHistorySteps);
                                        editor2.putString("lastWord",stringLastWord);
                                        editor2.commit();
                                        startActivity(intent);
                                        finish();

                                    }
                                };

                                handler2.postDelayed(r2, 8000);


                            }
                        }
                        else {
                            MediaPlayer mp = MediaPlayer.create(context, R.raw.errorsound);
                            mp.start();
                            LinearLayoutEnterWord.removeAllViews();
                            loadWordEnter();
                            frameLayoutEnter[idPicker].setBackground(getDrawable(R.drawable.border_red));
                            Animation animation = AnimationUtils.loadAnimation(context,R.anim.shake);
                            frameLayoutEnter[idPicker].setAnimation(animation);
                            final Handler handler = new Handler();
                            final Runnable r = new Runnable() {
                                public void run() {
                                    frameLayoutEnter[idPicker].setBackground(getDrawable(R.drawable.border_enter));
                                    endHandler= true;
                                    idPicker=-1;
                                    //handler.postDelayed(this, 3000);
                                }
                            };

                            handler.postDelayed(r, 500);
                        }

                    }
                    else if(idPicker<0){
                        Toast.makeText(context,"Vyber písmeno!",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        imageButtonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stepBack(imageButtonBack);
            }
        });

        textViewZpet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stepBack(textViewZpet);
            }
        });
    }

    private boolean checkWord(String stringWordCheck){
        DB_PATH = getApplicationContext().getApplicationInfo().dataDir+"/databases/";
        int limitSQL = returnLimit(stringWordCheck.substring(0,1).toLowerCase());
        int limitSQLMin = stringWordCheck.substring(0,1).charAt(0) -1;//Posune pismenko hodnotu dolu jedna, aby nasel dolni limit
        String letter = Character.toString((char) limitSQLMin).toLowerCase();
        limitSQLMin = returnLimit(letter);
        String sql2 = "SELECT * FROM slova WHERE slovo = '" + stringWordCheck.toLowerCase() + "' AND id BETWEEN '"+limitSQLMin+"' AND '"+limitSQL+"'  ";
        String path = DB_PATH + DB_NAME;
        mDataBase = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READONLY);
        cursor = mDataBase.rawQuery(sql2, null);
        booleanWordExist = cursor.getCount() > 0;
        return booleanWordExist;
    }

    private int returnLimit(String letter){
        int limit = 0;
        switch (letter){
            case "a":
                limit=14856;
                break;
            case "b":
                limit=34105;
                break;
            case "c":
                limit=46609;
                break;
            case "d":
                limit=85084;
                break;
            case "e":
                limit=92966;
                break;
            case "f":
                limit=101693;
                break;
            case "g":
                limit=105997;
                break;
            case "h":
                limit=125136;
                break;
            case "i":
                limit=132671;
                break;
            case "j":
                limit=141821;
                break;
            case "k":
                limit=183819;
                break;
            case "l":
                limit=199580;
                break;
            case "m":
                limit=228821;
                break;
            case "n":
                limit=390515;
                break;
            case "o":
                limit=450153;
                break;
            case "p":
                limit=592205;
                break;
            case "q":
                limit=592227;
                break;
            case "r":
                limit=631742;
                break;
            case "s":
                limit=698626;
                break;
            case "t":
                limit=724165;
                break;
            case "u":
                limit=747664;
                break;
            case "v":
                limit=819543;
                break;
            case "w":
                limit=820166;
                break;
            case "x":
                limit=820386;
                break;
            case "y":
                limit=820570;
                break;
            case "z":
                limit=893952;
                break;
        }
        return limit;
    }




    public void stepBack(View view){
        boolean stepBackOK = true;
        if(intHistorySteps<=1 || intLevelGetIntent!=intLevel){
            Toast.makeText(context,"Nelze tento krok vrátit",Toast.LENGTH_SHORT).show();
        }
        else {
            intHistorySteps = intHistorySteps-2;
            stringWordEnter = stringStepsHistory[intHistorySteps];
            stringWordHistory = stringStepsHistory[intHistorySteps];
            stringLastWord = stringStepsHistory[intHistorySteps];
            sb = new StringBuilder();
            for(int d=0;d<=intHistorySteps;d++){
                sb.append(stringStepsHistory[d]).append(",");

            }
            intHistorySteps++;
            editor.putString("historyWords",sb.toString());
            editor.putInt("historyCounts",intHistorySteps);
            editor.putString("lastWord",stringLastWord);
            editor.commit();
            LinearLayoutHistory.removeAllViews();
            TextView textViewZkouska = new TextView(context);
            textViewZkouska.setText("Kroky:");
            textViewZkouska.setGravity(Gravity.CENTER);
            LinearLayoutHistory.addView(textViewZkouska,0);
            loadHistorySteps();
            for(int d=0;d<intHistorySteps;d++){
                stringWordHistory = stringStepsHistory[d];
                int docasne = intHistorySteps;
                intHistorySteps=d+1;
                loadWordHistory();
                intHistorySteps = docasne;
            }
            LinearLayoutEnterWord.removeAllViews();
            loadWordEnter();

        }

    }

    private void loadHistorySteps(){
        if(intHistorySteps>0 && intLevel == intLevelGetIntent){
            stringStepsHistoryPreferences = Objects.requireNonNull(preferences.getString("historyWords", "")).split(",");
            for(int d=0;d<stringStepsHistoryPreferences.length;d++){
                stringStepsHistory[d] = stringStepsHistoryPreferences[d];
            }
        }
        else if(intLevel != intLevelGetIntent && intLevel>1){
            System.out.println("Toto se melo odehrat!");
            intHistorySteps = preferences2.getInt("historyCounts",0);
            stringStepsHistoryPreferences = Objects.requireNonNull(preferences2.getString("historyWords", "")).split(",");
            System.out.println("Nactena slova = "+preferences2.getString("historyWords",""));
            stringLastWord = preferences2.getString("lastWord","");
            for(int d=0;d<stringStepsHistoryPreferences.length;d++){
                stringStepsHistory[d] = stringStepsHistoryPreferences[d];
            }
        }
        else {
            // stringStepsHistoryPreferences[0] = stringLastWord;
            for(int d=0;d<stringStepsHistoryPreferences.length;d++){

                stringStepsHistory[d] = stringStepsHistoryPreferences[d];
            }
        }
    }

    private void loadItems(){


        imageButtonOK = findViewById(R.id.imageButtonOK);
        imageButtonBack = findViewById(R.id.imageButtonBack);
        LinearLayoutEnterWord = findViewById(R.id.LinearLayoutEnterWord);
        LinearLayoutExitWord = findViewById(R.id.LinearLayoutExitWord);
        LinearLayoutHistory = findViewById(R.id.LinearLayoutHistory);
        ScrollViewHistory = findViewById(R.id.ScrollViewHistory);
        LinearLayoutOkClick = findViewById(R.id.LinearLayoutOkClick);
        imageViewArrowDown = findViewById(R.id.imageViewArrowDown);
        imageViewStars = findViewById(R.id.imageViewStar);
        linearLayoutEnd = findViewById(R.id.LinearLayoutEnd);
        linearLayoutMain = findViewById(R.id.LinearLayoutMain);
        FrameLayoutStars = findViewById(R.id.FrameLayoutStars);
        imageViewStar1 = findViewById(R.id.imageViewStar1);
        imageViewStar2 = findViewById(R.id.imageViewStar2);
        imageViewStar3 = findViewById(R.id.imageViewStar3);
        imageViewStar4 = findViewById(R.id.imageViewStar4);
        imageViewStar5 = findViewById(R.id.imageViewStar5);
        tableRowControl=findViewById(R.id.tableRowControl);
        textViewZpet = findViewById(R.id.textView5);
    }

}
