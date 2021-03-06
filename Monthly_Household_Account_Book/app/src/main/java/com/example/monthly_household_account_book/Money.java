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
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.monthly_household_account_book.main_adapter.Add_Activity;
import com.example.monthly_household_account_book.main_adapter.Items;
import com.example.monthly_household_account_book.main_adapter.ListviewAdapter;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static android.view.Gravity.BOTTOM;


public class Money extends Fragment {

    private View view;
    private TextView belence_txt ;

    //Main Adapter Field
    private ArrayList<Items> itemsArr;
    Add_Activity add_activity = new Add_Activity();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.money, container, false);

        // 초기화
        ListView listView = (ListView)view.findViewById(R.id.listview);
        final TextView total_outgoing_txt = (TextView)view.findViewById(R.id.total_outgoing_txt);
        belence_txt  = (TextView)view.findViewById(R.id.belence_txt);
        Button add_btn = (Button)view.findViewById(R.id.add_btn);

        //Main Adapter Custom ListView
        itemsArr = ((MainActivity)getActivity()).getItemsArr();
        listView.setAdapter(((MainActivity)getActivity()).adapter);

//        FragmentTransaction ft = getFragmentManager().beginTransaction();
//        ft.detach(this).attach(this).commit();

        changeMoney();

        /*
        child Fragment Manager Test
         */

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                return false;
            }
        });

       final FragmentManager child = getChildFragmentManager();
        child.beginTransaction()
                .add(add_activity, "child")
                .addToBackStack(null);


        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                add_activity.show(child,"child");

//
            }
        });



        if(getArguments() != null){
            System.out.println("데이터 전달....");
            if(!add_activity.isHidden()){
                add_activity.dismiss();
            }
            if(getArguments().getInt("result")==1){
                changeMoney();

                getArguments().remove("result");
                getArguments().clear();

            }
        }






        return view;
    }

    String testGetDate() {
        long now = System.currentTimeMillis();
        Date nowDate = new Date(now);

        SimpleDateFormat dateFormat = new SimpleDateFormat("MM");
        String time;

        return time = dateFormat.format(nowDate);
    }




    public void changeMoney() {
        DecimalFormat decimalFormat = new DecimalFormat("###,###");
        belence_txt.setText(decimalFormat.format(((MainActivity)getActivity()).getBeleance()));
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
