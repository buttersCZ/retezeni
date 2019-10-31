package com.example.davidmacak.etzen;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableRow;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class Score extends AppCompatActivity {
    Context context;
    String list[][] = new String[20][3];
    LinearLayout linearLayout[] = new LinearLayout[26];
    LinearLayout linearLayoutDruhy[]= new LinearLayout[26];
    LinearLayout linearLayoutTreti[]= new LinearLayout[26];
    LinearLayout linearLayoutScoreMain;
    TableRow tableRowPrvni[]= new TableRow[26];
    TableRow tableRowDruhy[]= new TableRow[26];
    Button buttonPrvni[]= new Button[26];
    Button buttonDruhy[]= new Button[26];
    Button buttonTreti[]= new Button[26];
    LinearLayout.LayoutParams layoutParams;
    LinearLayout.LayoutParams layoutParamsButton;
    int poradi = 0;
    int control = 0;
    FirebaseDatabase database = FirebaseDatabase.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.score);
        context = this;
        linearLayoutScoreMain = findViewById(R.id.layoutScoreMainScroll);

        for(int q=0;q<20;q++){
            getScore(q);
        }


    }

    private void getScore(final int level){
        final DatabaseReference myRef = database.getReference(String.valueOf(level));
        final Query query = myRef.orderByValue();
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                control++;
                if(dataSnapshot.getValue()!=null){
                    for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                        if(poradi<3 && snapshot.getValue()!=null){
                            list[level][poradi] = snapshot.getKey()+" - "+ snapshot.getValue();
                            poradi++;
                        }
                    }


                }
                if(control==20){
                    vykresliSkore();
                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }


        });

        poradi = 0;
    }

    private void vykresliSkore(){
        System.out.println("Ted zacal vykreslovat");
        for(int level=0;level<20;level++) {
            layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            TableRow.LayoutParams paramsButton = new TableRow.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            LinearLayout.LayoutParams paramsNextRow = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            linearLayout[level] = new LinearLayout(context);
            linearLayout[level].setLayoutParams(layoutParams);
            linearLayout[level].setOrientation(LinearLayout.VERTICAL);
            linearLayoutDruhy[level] = new LinearLayout(context);
            linearLayoutTreti[level] = new LinearLayout(context);
            tableRowPrvni[level] = new TableRow(context);
            tableRowPrvni[level].setGravity(Gravity.CENTER);
            tableRowPrvni[level].setLayoutParams(layoutParams);
            tableRowPrvni[level].setPadding(0, 50, 0, 0);
            tableRowDruhy[level] = new TableRow(context);
            tableRowDruhy[level] = new TableRow(context);
            tableRowDruhy[level].setGravity(Gravity.CENTER);
            tableRowDruhy[level].setLayoutParams(layoutParams);
            linearLayoutDruhy[level].setLayoutParams(paramsNextRow);
            linearLayoutTreti[level].setLayoutParams(paramsNextRow);
            linearLayoutTreti[level].setPadding(0, 50, 0, 0);

            buttonPrvni[level] = new Button(context);
            buttonPrvni[level].setBackground(getDrawable(R.drawable.border_skore));
            buttonPrvni[level].setElevation(4);
            buttonPrvni[level].setText(list[level][0]);
            buttonPrvni[level].setLayoutParams(paramsButton);

            buttonDruhy[level] = new Button(context);
            buttonDruhy[level].setBackground(getDrawable(R.drawable.border_silver));
            buttonDruhy[level].setElevation(4);
            buttonDruhy[level].setText(list[level][1]);
            buttonDruhy[level].setLayoutParams(paramsButton);

            buttonTreti[level] = new Button(context);
            buttonTreti[level].setBackground(getDrawable(R.drawable.border_bronze));
            buttonTreti[level].setElevation(4);
            buttonTreti[level].setText(list[level][2]);
            buttonTreti[level].setLayoutParams(paramsButton);

            tableRowPrvni[level].addView(buttonPrvni[level]);
            tableRowDruhy[level].addView(buttonDruhy[level]);
            tableRowDruhy[level].addView(buttonTreti[level]);

            linearLayout[level].addView(tableRowPrvni[level]);
            linearLayout[level].addView(tableRowDruhy[level]);
            linearLayoutScoreMain.addView(linearLayout[level]);
        }
    }

}
