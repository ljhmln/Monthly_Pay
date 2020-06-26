package com.example.monthly_household_account_book.money;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.monthly_household_account_book.DataBaseHelper;
import com.example.monthly_household_account_book.MainActivity;
import com.example.monthly_household_account_book.R;
import com.example.monthly_household_account_book.money.Items;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class ListviewAdapter extends CursorAdapter {
    private ArrayList<Items> itemsArr = new ArrayList<Items>();
    private Context con;
    LayoutInflater inflater;
    DataBaseHelper helper;
    TextView date_txt, kind_txt, category_txt, money_txt;

    public ListviewAdapter(DataBaseHelper helper, Context context, Cursor cursor) {
        super(context, cursor);


        this.helper = helper;
//        addItem();

        for (Items item : itemsArr) {
            //테스트
            System.out.println("아이템 : " + item.getKind());
        }
        Log.d("TAG", "ListViewAdapter 생성됨. [Is itemsArr Empty?] : " + itemsArr.isEmpty());
        notifyDataSetChanged();
    }


    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        //커서가 가리키는 데이터를 새로운 뷰를 만들어 리턴하는 기능
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.layout_listview, parent, false);

        System.out.println("뉴뷰");
        notifyDataSetChanged();

        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        //뷰에 커서가 가리키는 데이터를 대입하는 기능
        ViewHolder holder;
        View rootView = null;
        //custom list view layout 넣기
//        if(view == null){
//            con = context;
//            holder = new ViewHolder();
//            holder.date_txt = (TextView)view.findViewById(R.id.date_txt);
//            holder.kind_txt = (TextView)view.findViewById(R.id.kind_txt);
//            holder.category_txt = (TextView)view.findViewById(R.id.category_txt);
//            holder.money_txt = (TextView)view.findViewById(R.id.money_txt);
//            view.setTag(holder);
//        }else{
//            holder = (ViewHolder)rootView.getTag();
//        }

        date_txt = (TextView) view.findViewById(R.id.date_txt);
        kind_txt = (TextView) view.findViewById(R.id.kind_txt);
        category_txt = (TextView) view.findViewById(R.id.category_txt);
        money_txt = (TextView) view.findViewById(R.id.money_txt);

//        if((listItem.getKind()).equals("수입")){
//            holder.kind_txt.setTextColor(Color.BLUE);
//        }else if((listItem.getKind()).equals("지출")){
//            holder.kind_txt.setTextColor(Color.RED);
//        }
        DecimalFormat decimalFormat = new DecimalFormat("###,###");
        StringBuffer dateWithdot = new StringBuffer(cursor.getColumnIndex("date"));
        System.out.println(cursor.getString(cursor.getColumnIndex("_id")));
//        date_txt.setText(dateWithdot.insert(2, "."));
        kind_txt.setText(cursor.getString(cursor.getColumnIndex("kind")));
        category_txt.setText(cursor.getString(cursor.getColumnIndex("kind")));
        money_txt.setText(String.valueOf(decimalFormat.format((double) cursor.getInt(cursor.getColumnIndex("money"))) + "원"));
        System.out.println("바인듭뷰");
        notifyDataSetChanged();
    }

    class ViewHolder {

    }


    //ArrayList<Items> arr
    public void addItem() {
        if (itemsArr != null) {
            System.out.println("itemArr != null");
            itemsArr.clear();
            notifyDataSetChanged();
            System.out.println("itemArr size : " + itemsArr.size());
            itemsArr.addAll(helper.getltems(MainActivity.dateRun.getNowYear_Month()));
            notifyDataSetChanged();

        } else if (itemsArr == null) {
            System.out.println("itemArr == null");
            itemsArr.addAll(helper.getltems(MainActivity.dateRun.getNowYear_Month()));
        }
//        notifyDataSetChanged();

    }

    public String getItemTest(int position) {
        return itemsArr.get(position).getId();
    }

    @Override
    public int getCount() {
        return itemsArr.size();
    }

    @Override
    public Object getItem(int position) {
        return itemsArr.get(position);
    }


    @Override
    public long getItemId(int position) {
        return position;
    }


}
