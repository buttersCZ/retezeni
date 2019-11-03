package com.example.davidmacak.etzen;

import android.support.annotation.NonNull;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SaveScore {

    int level;
    int skore;
    String login;

    public SaveScore(int level, int skore, String login){

        this.level = level;
        this.skore = skore;
        this.login = login;
        FireBase fireBase = new FireBase();
        fireBase.FireBaseSave(level,skore,login);
    }


}
