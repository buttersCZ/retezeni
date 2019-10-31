package com.example.davidmacak.etzen;

import android.support.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class FireBase {

    FirebaseDatabase database = FirebaseDatabase.getInstance();


    public void FireBaseSave(int level,int skore, String login){
        DatabaseReference myRef = database.getReference(String.valueOf(level));
        myRef.child(login).setValue(String.valueOf(skore));

    }



}
