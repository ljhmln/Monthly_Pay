package com.example.monthly_household_account_book.money;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.monthly_household_account_book.DataBaseHelper;
import com.example.monthly_household_account_book.MainActivity;
import com.example.monthly_household_account_book.R;
import com.example.monthly_household_account_book.money.Items;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class ListviewAdapter extends BaseAdapter {
    private ArrayList<Items> itemsArr = new ArrayList<Items>();
    private Context con;
    LayoutInflater inflater;
    DataBaseHelper helper;

    public ListviewAdapter(DataBaseHelper helper) {
        this.helper =helper;
        addItem();

        for(Items item : itemsArr){
            //테스트
            System.out.println("아이템 : "+ item.getKind());
        }
        Log.d("TAG","ListViewAdapter 생성됨. [Is itemsArr Empty?] : "+itemsArr.isEmpty());

    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        View rootView = null;
        //custom list view layout 넣기
        if(convertView == null){
            con = parent.getContext();
            this.inflater = (LayoutInflater)this.con.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rootView = inflater.inflate(R.layout.layout_listview,parent, false);
            holder = new ViewHolder();
            holder.date_txt = (TextView)convertView.findViewById(R.id.date_txt);
            holder.kind_txt = (TextView)convertView.findViewById(R.id.kind_txt);
            holder.category_txt = (TextView)convertView.findViewById(R.id.category_txt);
            holder.money_txt = (TextView)convertView.findViewById(R.id.money_txt);
            rootView.setTag(holder);
        }else{
            convertView = rootView;
            holder = (ViewHolder)rootView.getTag();
        }
        DecimalFormat decimalFormat = new DecimalFormat("###,###");

        Items listItem = itemsArr.get(position);
        if(listItem != null){

            if((listItem.getKind()).equals("수입")){
                holder.kind_txt.setTextColor(Color.BLUE);
            }else if((listItem.getKind()).equals("지출")){
                holder.kind_txt.setTextColor(Color.RED);
            }
            StringBuffer dateWithdot = new StringBuffer(listItem.getDate());
            holder.date_txt.setText(dateWithdot.insert(2,"."));
            holder.kind_txt.setText(String.valueOf(listItem.getKind()));
            holder.category_txt.setText(String.valueOf(listItem.getCategory()));
            holder.money_txt.setText(String.valueOf(decimalFormat.format(Double.parseDouble(listItem.getMoney())))+"원");

        }

        System.out.println("리스트 어댑터");

        return rootView;
    }

    class ViewHolder {
        TextView date_txt, kind_txt, category_txt, money_txt;
    }


//ArrayList<Items> arr
    public void addItem(){
        if(itemsArr != null){
            System.out.println("itemArr != null");
            itemsArr.clear();
            notifyDataSetChanged();
            System.out.println("itemArr size : "+itemsArr.size());
            itemsArr.addAll(helper.getltems(MainActivity.dateRun.getNowYear_Month()));
            notifyDataSetChanged();

        }else if(itemsArr == null){
            System.out.println("itemArr == null");
            itemsArr.addAll(helper.getltems(MainActivity.dateRun.getNowYear_Month()));
        }
//        notifyDataSetChanged();

    }

    public String getItemTest (int position){
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
