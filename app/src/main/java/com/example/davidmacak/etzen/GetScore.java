package com.example.davidmacak.etzen;

public class GetScore {

    int level;
    int skore;
    String login;

    public GetScore(int level, int skore, String login){

        this.level = level;
        this.skore = skore;
        this.login = login;
        FireBase fireBase = new FireBase();
    }
}
