package com.example.chohee.myapplication;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by chohee on 2016-04-24.
 */
public class DBHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "test.db";
    private static final int DB_VER = 1;

    public DBHelper(Context context) {
        super(context,DB_NAME, null, DB_VER);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql="CREATE TABLE account("+"account_id integer primary key autoincrement," +
                " account_date NUMERIC ,account_list text,"
                +" account_money integer);";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
