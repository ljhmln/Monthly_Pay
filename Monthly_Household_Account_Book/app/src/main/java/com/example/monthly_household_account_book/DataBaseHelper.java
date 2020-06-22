package com.example.monthly_household_account_book;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import android.util.Log;

import com.example.monthly_household_account_book.main_adapter.Items;

import java.util.ArrayList;

public class DataBaseHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    private ArrayList<Items> dataArr = new ArrayList<Items>();
    private SQLiteDatabase dbWrite = getWritableDatabase();
    private SQLiteDatabase dbRead = getReadableDatabase();


    public DataBaseHelper(Context context) {
        super(context, "datadb", null, DATABASE_VERSION);

    }

    public boolean isEmpty(){
        // true -> Empty
        // false -> not Empty
        Cursor c = dbRead.rawQuery("select * from tb_fixed",null);
        if(c == null || c.getCount() == 0){
            return true;
        }
        return false;
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
        if (newVersion == DATABASE_VERSION) {
            db.execSQL("drop table tb_data");
            db.execSQL("drop table tb_fixed");
            onCreate(db);
        }
    }

    //테스트 보기
    public String getAllData() {

        Cursor cursor = dbRead.rawQuery("select * from tb_data where kind='지출'", null);
        String result = "";

//        while (cursor.moveToNext()) {
//            result += cursor.getString(cursor.getColumnIndex("id_m")) + "\n";
//            result += cursor.getInt(cursor.getColumnIndex("fixedMoney")) + "\n";
//        }
        while (cursor.moveToNext()){
            result += cursor.getInt(cursor.getColumnIndex("money"))+"\n";
            System.out.println(result);
        }

        return result;
    }

    public void insert(String id, String date, String time, String category, String kind, String money, String memo) {


        dbWrite.execSQL("insert into tb_data (id, date, time, category, kind, money, memo) " +
                "values ('" + id + "','" + date + "','" + time + "','" + category + "','" + kind + "','" + money + "','" + memo + "')");
        Log.e("error", "SQLite insert error");

    }

    public String getIncome(String date) {


        if(!isEmpty()){
            int result = 0;

            Cursor income = dbRead.rawQuery("select * from tb_data where id like '%" + date + "%' and kind='수입'", null);
//            while (income.moveToFirst()) {
//                result = income.getInt(0);
//            }
            int i =0;
            System.out.println("getIncome date 값 = "+date);
            while (income.moveToNext()) {
                result += income.getInt(income.getColumnIndex("money"));
                System.out.println("income 값= "+(i++)+">"+result);
                System.out.println(income.getInt(income.getColumnIndex("money")));
            }
            return String.valueOf(result);
        }

       return null;

    }

    public String getOutgoing(String date) {
        System.out.println("getOutgoing 들어옴");
        if(!isEmpty()){
            Cursor outgoing = dbRead.rawQuery("select * from tb_data where id like '%" + date + "%' and kind='지출'", null);
            int result = 0;

            System.out.println("isEmpty : "+ isEmpty());


                while (outgoing.moveToNext()) {
                    result += outgoing.getInt(outgoing.getColumnIndex("money"));
                    System.out.println("outgoing result : "+ result);
                }
                if(result!=0)
                    return String.valueOf(result);
                else
                    return "0";


        }else {
            return "0";
        }

    }

    public String getBelence(String date) {

        String income = getIncome(date);
        String outgoing = getOutgoing(date);
        String fixedMoney = getFixedMoney(date);

        double result = 0;

        if(fixedMoney!=null){
            result = Double.parseDouble(fixedMoney);
        }

        if(!isEmpty()){

            if(income!=null)
                result += Double.parseDouble(income);
            if(outgoing!=null)
                result -= Double.parseDouble(outgoing);

            return String.valueOf(result);

        }else {
            return "0";
        }




    }

    //Array 객체에 넣어 리턴
    public ArrayList<Items> getltems(String date) {
        System.out.println("array 리턴");
        Log.d("TAG", "" + getClass().getName() + "의 getItems 인자값 :" + date);

        Items item;

        String query = "select * from tb_data where id like '%" + date + "%'";
        Cursor forList = dbRead.rawQuery(query, null);

        while (forList.moveToNext()) {
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



    public void setFixedMoney(String fixedMoney, String date) {

        String query = "select * from tb_fixed where id_m='" + date + "'";

        boolean check = true;
        Cursor cursor = dbRead.rawQuery(query, null);
        int fixedM = Integer.parseInt(fixedMoney);

        while (cursor.moveToNext()) {
            dbWrite.execSQL("update tb_fixed set fixedMoney='" + fixedM + "' where id_m='" + date + "'");
            check = false;
        }

        if (check) {
            dbWrite.execSQL("insert into tb_fixed (id_m, fixedMoney)  values ('" + date + "', '" + fixedM + "')");
        }
    }

    public String getFixedMoney(String date) {

        if(!isEmpty()){
            System.out.println(date);
            String query = "select fixedMoney from tb_fixed where id_m='" + date + "'";

            String result = "";
            Cursor cursor = dbRead.rawQuery(query, null);

            while (cursor.moveToNext()) {
                result = String.valueOf(cursor.getInt(cursor.getColumnIndex("fixedMoney")));
            }
            return result;
        }else {
            return "0";
        }


    }
}
