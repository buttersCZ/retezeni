package com.example.davidmacak.etzen;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Set;



public class Db extends SQLiteOpenHelper {


    private static String DB_PATH = "";
    public static String DB_NAME="slovnik.sqlite";
    public SQLiteDatabase mDataBase;
    private Context mcontext;




    public Db(Context context) {
        super(context, DB_NAME, null, 1);
        if(Build.VERSION.SDK_INT>=17){
            DB_PATH = context.getApplicationInfo().dataDir+"/databases/";
        }
        else {
            DB_PATH = "/data/data/"+context.getPackageName()+"/databases/";
        }

        mcontext = context;
    }



    @Override
    public synchronized void close(){

        if (mDataBase!=null){
            mDataBase.close();
        }

        super.close();
    }

    private boolean checkDataBase(){
        SQLiteDatabase tempDb= null;
        try {
            String path = DB_PATH+DB_NAME;
            tempDb = SQLiteDatabase.openDatabase(path,null,SQLiteDatabase.OPEN_READWRITE);
        }

        catch (Exception ex){

        }

        if(tempDb!=null){
            tempDb.close();
        }
        return tempDb!=null?true:false;

    }

    public void copyDataBase(){
        try {
            InputStream myInput = mcontext.getAssets().open(DB_NAME);
            String outputFileName = DB_PATH+DB_NAME;
            OutputStream myOutput = new FileOutputStream(outputFileName);
            byte[] buffer = new byte[1024];
            int lenght;

            while ((lenght=myInput.read(buffer))>0){
                myOutput.write(buffer,0,lenght);
            }

            myOutput.flush();
            myOutput.close();
            myInput.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void createDatabase(){
        boolean isIdExist = checkDataBase();
        if(isIdExist){
        }
        else {
            this.getReadableDatabase();
            try {
                copyDataBase();

            }
            catch (Exception ex){

            }
        }

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
