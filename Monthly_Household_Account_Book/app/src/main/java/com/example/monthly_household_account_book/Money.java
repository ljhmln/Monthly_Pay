package com.example.monthly_household_account_book;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.example.monthly_household_account_book.main_adapter.Add_Activity;
import com.example.monthly_household_account_book.main_adapter.Items;
import com.example.monthly_household_account_book.main_adapter.ListviewAdapter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static android.view.Gravity.BOTTOM;


public class Money extends Fragment {

    private View view;
    //Main Adapter Field
     ListView listView;
    private ListviewAdapter adapter;
    private ArrayList<Items> itemsArr = new ArrayList<Items>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.money, container, false);

        ListView listView = (ListView)view.findViewById(R.id.listview);
        //Main Adapter Custem ListView
        adapter = new ListviewAdapter(itemsArr);
        listView.setAdapter(adapter);
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                return false;
            }
        });

        for(int i=0; i<10; i++){
            Items item = new Items();
            item.setKind("수입");
            item.setCategory("용돈");
            item.setMoney("100,000");
            itemsArr.add(item);
            Items item2 = new Items();
            item2.setKind("지출");
            item2.setCategory("용돈");
            item2.setMoney("100,000");
            itemsArr.add(item2);
        }

        adapter.addItem(itemsArr);

        Button add_btn = (Button)view.findViewById(R.id.add_btn);

        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Add_Activity add_activity = new Add_Activity();
                add_activity.show(getFragmentManager(),"추가하기");
            }
        });




        return view;
    }

    String testGetDate() {
        long now = System.currentTimeMillis();
        Date nowDate = new Date(now);

        SimpleDateFormat dateFormat = new SimpleDateFormat("MM");
        String time;

        return time = dateFormat.format(nowDate);
    }







    @Override
    public void onResume() {
        super.onResume();
        FragmentActivity activity = getActivity();


        if (activity != null) {
            String date = testGetDate();
            //MainActivity의 메소드 사용하여 액션바 타이틀 변경.
            ((MainActivity) activity).setActionBarTitle(date+"월 수입 지출 현황");
        }
    }
}
