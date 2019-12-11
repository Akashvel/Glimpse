package com.example.glimpse;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;

import androidx.annotation.RequiresApi;

public class DatabaseHelper extends SQLiteOpenHelper {
    public DatabaseHelper(Context context) {
        super(context,"login.db",null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table user(name text,whatsappnum text,alternatenum text,email text,linkedin text,working text,workingcity text,address text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("drop table if exists user");
    }

    public boolean insert(String name,String whatsappnum,String alternatenum,String email,String linkedin,String working,String workingcity,String address){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name",name);
        contentValues.put("whatsappnum",whatsappnum);
        contentValues.put("alternatenum",alternatenum);
        contentValues.put("email",email);
        contentValues.put("linkedin",linkedin);
        contentValues.put("working",working);
        contentValues.put("workingcity",workingcity);
        contentValues.put("address",address);
        long ins = db.insert("user",null,contentValues);
        if(ins==-1) return false;
        else return true;
    }
    public Boolean chkname(String name){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from user where name=?",new String[]{name});
        if(cursor.getCount()>0) return false;
        else return true;
    }
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public Cursor alldata(String str){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select *from user where name=?",new String[]{str},null);
        return cursor;
    }
    public Cursor contacts(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select *from user order by name asc",null);
        return cursor;
    }
    public Cursor contactsimilar(String s1){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from user where name like ? order by name asc",new String[]{"%"+s1+"%"},null);
        return cursor;
    }

    public void update(String s1,String s2,int flag){
        SQLiteDatabase db = this.getWritableDatabase();
        String query;
        if(flag==1){
            db.execSQL("update user set whatsappnum=? where name=?",new String[]{s2,s1});
        }
        else if(flag==2){
            db.execSQL("update user set alternatenum=? where name=?",new String[]{s2,s1});
        }
        else if(flag==3){
            db.execSQL("update user set email=? where name=?",new String[]{s2,s1});
        }
        else if(flag==6){
            db.execSQL("update user set workingcity=? where name=?",new String[]{s2,s1});
        }
        else if(flag==5){
            db.execSQL("update user set working=? where name=?",new String[]{s2,s1});
        }
        else if(flag==7){
            db.execSQL("update user set address=? where name=?",new String[]{s2,s1});
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public Cursor getdetailsby(int flag, String s1){
        SQLiteDatabase db = this.getWritableDatabase();
        if(flag==5){
            Cursor cursor = db.rawQuery("select *from user where working=?",new String[]{s1},null);
            return cursor;
        }
        else{
            Cursor cursor = db.rawQuery("select *from user where workingcity=?",new String[]{s1},null);
            return cursor;
        }
    }
}
