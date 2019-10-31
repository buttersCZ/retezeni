package com.example.davidmacak.etzen;

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
