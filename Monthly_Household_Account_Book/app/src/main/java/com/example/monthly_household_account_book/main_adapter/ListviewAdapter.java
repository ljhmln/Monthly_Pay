package com.example.monthly_household_account_book.main_adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.monthly_household_account_book.R;

import java.util.ArrayList;

public class ListviewAdapter extends BaseAdapter {
    private ArrayList<Items> itemsArr;
    private Context con;
    LayoutInflater inflater;

    public ListviewAdapter( ArrayList itemsArr) {
        this.itemsArr = new ArrayList<Items>();
        this.itemsArr.addAll(itemsArr);

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        con = parent.getContext();
        this.inflater = (LayoutInflater)this.con.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if(convertView == null){
            convertView = inflater.inflate(R.layout.layout_listview,parent, false);
        }
        TextView kind_txt = (TextView)convertView.findViewById(R.id.kind_txt);
        TextView category_txt = (TextView)convertView.findViewById(R.id.category_txt);
        TextView money_txt = (TextView)convertView.findViewById(R.id.money_txt);

        Items listItem = itemsArr.get(position);
        if(listItem != null){
            if((listItem.getKind()).equals("수입")){
                kind_txt.setTextColor(Color.BLUE);
            }else if((listItem.getKind()).equals("지출")){
                kind_txt.setTextColor(Color.RED);
            }
            kind_txt.setText(String.valueOf(listItem.getKind()));
            category_txt.setText(String.valueOf(listItem.getCategory()));
            money_txt.setText("￦ "+String.valueOf(listItem.getMoney())+"원");
        }

        return convertView;
    }



    public void addItem(ArrayList<Items> arr){
        if(itemsArr != null){
            itemsArr.clear();
            itemsArr.addAll(arr);
            notifyDataSetChanged();
        }

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
