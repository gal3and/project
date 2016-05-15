package com.example.chohee.myapplication;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.StringTokenizer;

/**
 * Created by chohee on 2016-04-24.
 */
public class DBAdapter {

    //데이터 베이스를 이용하는 컨텍스트
    private Context context;
    //데이터 연동객체
     private SQLiteDatabase db;


    public DBAdapter(Context context) {
        this.context = context;
        this.open();
        //test code
       // this.insert_dialog("1 day class","start english study 1day");

        Log.d("kingreturn", "db insert done");
    }

    public void open() {
        try {
            db = (new DBHelper(context).getWritableDatabase());

        } catch(SQLiteException e) {
            e.printStackTrace();
            db = (new DBHelper(context).getReadableDatabase());
        }
    }



    public void insert_account(String Date, String item,int money) {
        try {
            ContentValues values = new ContentValues();
            values.put("account_date",Date);
            values.put("account_list",item);
            values.put("account_money",money);
            long result = db.insert("account",null,values);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public Cursor select_all_account() {
        Cursor c = db.query("account", //table name
                new String[]{"account_id", "account_date", "account_list", "account_money"}, //colum 명세
                null, //where
                null, //where 절에 전달할 데이터
                null, //group by
                null, //having
                null//order by
        );
        return c;
    }

    public ArrayList<String> Select_All_Account_Data(){
        ArrayList<String> input_data=new ArrayList<>();
        Cursor c = select_all_account();
        int cnt=0;
        if(c.moveToFirst()) {
            //int indexId = c.getColumnIndex("_id");
            int indexDate = c.getColumnIndex("account_date");
            int indexitem = c.getColumnIndex("account_list");
            int indexMoney=c.getColumnIndex("account_money");
            do {

                String Date = c.getString(indexDate);
                String item = c.getString(indexitem);
                String money=c.getString(indexMoney);

                input_data.add(cnt,Date+"\t\t"+item+"\t\t"+money);
                cnt++;
            } while(c.moveToNext());
        }
     return input_data;
    }


    public ArrayList<account_Data> Get_Email_Data() {
        ArrayList<account_Data> itemlist = new ArrayList<account_Data>();
        ArrayList<account_Data> input_data=new ArrayList<>();
        Cursor c = select_all_account();
        int cnt=0;
        if(c.moveToFirst()) {
            //int indexId = c.getColumnIndex("_id");
            int indexDate = c.getColumnIndex("account_date");
            int indexitem = c.getColumnIndex("account_list");
            int indexMoney=c.getColumnIndex("account_money");
            do {

                String Date = c.getString(indexDate);
                String item = c.getString(indexitem);
                String money=c.getString(indexMoney);

                input_data.add(cnt,new account_Data(Date,item, money));
                cnt++;
            } while(c.moveToNext());
        }

       if(input_data.size()>1)
            itemlist=item_Sorting(input_data);
        else if(input_data.size()==1)
            itemlist.add(input_data.get(0));
        return itemlist;
    }


    ArrayList<account_Data> swap (ArrayList<account_Data> input_data,int j){
        account_Data swap;


            swap=input_data.get(j);
            input_data.set(j,input_data.get(j-1));
            input_data.set(j - 1, swap);

        return  input_data;
    }



    ArrayList<account_Data> item_Sorting(ArrayList<account_Data> input_data){

        account_Data swap;
        ArrayList<account_Data> itemlist = new ArrayList<account_Data>();

        Log.d("size",String.valueOf(input_data.size()));


            for(int i=0;i<input_data.size()-1;i++){

                for(int j=i+1;j>0;j--){

                    StringTokenizer st1=new StringTokenizer(input_data.get(j).getDate(),"/");
                    StringTokenizer st2=new StringTokenizer(input_data.get(j-1).getDate(),"/");

                    while (st1.hasMoreTokens()&&st2.hasMoreTokens()){
                        int first1=Integer.valueOf(st1.nextToken().trim());
                        int first2=Integer.valueOf(st2.nextToken().trim());
                        if(first1<first2){
                            input_data=swap(input_data, j);
                            break;
                        }
                    }


                }

                itemlist.add(i,input_data.get(i));
            }



        return itemlist;
    }




     /* public void delete_dialog(String id) {
            db.delete(dbname, //table name
                    "_id = ?", //where 절
                    new String[] {id} //where절 들어갈 데이터
            );
        }*/


    public void Close() {
        db.close();
    }

}


class account_Data{
    private String date,item,money;

    public account_Data(String data, String item, String money) {
        this.date = data;
        this.item = item;
        this.money = money;
    }

    public String getDate() {
        return date;
    }

     public String getItem() {
        return item;
    }

    public String getMoney() {
        return money;
    }
}