package com.example.monthly_household_account_book;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.Editable;
import android.util.Log;

import com.example.monthly_household_account_book.main_adapter.Items;

import java.util.ArrayList;

public class DataBaseHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    private ArrayList<Items> dataArr = new ArrayList<Items>();
    private SQLiteDatabase db;



    public DataBaseHelper(Context context) {
        super(context, "datadb", null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //tb_data -> id
        //tb_fixed
        String dataSQL = "create table tb_data " +
                "(id varchar(20) primary key," +
                "date varchar(10)," +
                "time varchar(10)," +
                "category varchar(20)," +
                "kind varchar(10)," +
                "fixedMoney int," +
                "money int," +
                "memo varchar(30))";

        String fixedSQL = "create table tb_fixed " +
                "(id_m varchar(10) primary key," +
                "fixedMoney int)";

        db.execSQL(dataSQL);
        db.execSQL(fixedSQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(newVersion==DATABASE_VERSION){
            db.execSQL("drop table tb_data");
            db.execSQL("drop table tb_fixed");
            onCreate(db);
        }
    }

    //테스트 보기
    public String getAllData(){
        db = getReadableDatabase();

        Cursor cursor = db.rawQuery("select * from tb_fixed",null);
        String result ="";
        int i = 0;
        while (cursor.moveToNext()){
           result +=cursor.getString(cursor.getColumnIndex("id_m"))+"\n";
           result += cursor.getInt(cursor.getColumnIndex("fixedMoney"))+"\n";
           i++;

        }

        return result;
    }

    public void insert(String id, String date, String time, String category, String kind, String money, String memo) {
        db = getWritableDatabase();

        db.execSQL("insert into tb_data (id, date, time, category, kind, money, memo) " +
                "values ('" + id + "','" + date + "','" + time + "','" + category + "','" + kind + "','" + money + "','" + memo + "')");
        Log.e("error", "SQLite insert error");

    }

    public String getIncome(String date) {
        db = getReadableDatabase();

        String result = "";

        Cursor income = db.rawQuery("select sum(money) from tb_data where date like '%" + date + "%' and kind='지출'", null);
        while (income.moveToNext()) {

            result = income.getString(0);
        }


        return result;

    }

    public String getOutgoing(String date) {
        db = getReadableDatabase();

        Cursor outgoing = db.rawQuery("select sum(money) from tb_data where date like '%" + date + "%' and kind='수입'", null);
        String result = "";
        while (outgoing.moveToNext()) {
            result = outgoing.getString(0);
        }
        return result;

    }

    //Array 객체에 넣어 리턴
    public ArrayList<Items> getltems(String date) {
        System.out.println("array 리턴");
        db = getReadableDatabase();
        Items item;

        String query = "select * from tb_data where id like '%"+date+"%'";
        Cursor forList = db.rawQuery(query,null);

        while(forList.moveToNext()){
            item = new Items();

            String id = forList.getString(forList.getColumnIndex("id"));
            item.setId(id);
            String kind = forList.getString(forList.getColumnIndex("kind"));
            item.setKind(kind);
            String category = forList.getString(forList.getColumnIndex("category"));
            item.setCategory(category);
            String money = forList.getString(forList.getColumnIndex("money"));
            item.setMoney(money);
            String dates = forList.getString(forList.getColumnIndex("date"));
            item.setDate(dates);

            dataArr.add(item);
        }


        return dataArr;
    }

    public String getBelence(String date) {

        return null;
    }

    public void setFixedMoney(String fixedMoney, String date) {
        db = getReadableDatabase();
        String query = "select * from tb_fixed where id_m='"+date+"'";

        boolean check = true;
        Cursor cursor = db.rawQuery(query,null);
        int fixedM = Integer.parseInt(fixedMoney);

        while (cursor.moveToNext()){
            db= getWritableDatabase();
            db.execSQL("update tb_fixed set fixedMoney='"+fixedM+"' where id_m='"+date+"'");
            check = false;
        }

        if(check){
            db.execSQL("insert into tb_fixed (id_m, fixedMoney)  values ('"+date+"', '"+fixedM+"')");
        }
    }

    public String getFixedMoney(String date){
        db = getReadableDatabase();
        System.out.println(date);
        String query = "select fixedMoney from tb_fixed where id_m='"+date+"'";

        String result ="";
        Cursor cursor = db.rawQuery(query,null);

        while (cursor.moveToNext()){
            result = String.valueOf(cursor.getInt(cursor.getColumnIndex("fixedMoney")));
        }
        return result;
    }
}
