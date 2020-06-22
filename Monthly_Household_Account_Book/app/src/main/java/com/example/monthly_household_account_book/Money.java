package com.example.monthly_household_account_book;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import com.example.monthly_household_account_book.main_adapter.AddFragmant;
import com.example.monthly_household_account_book.main_adapter.ListviewAdapter;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


public class Money extends Fragment {

    private View view;
    private TextView belence_txt, fixed_money_edit, total_outgoing_txt;
    ListviewAdapter adapter;
    ListView listView;
    AddFragmant add_fragmant = new AddFragmant();
    DataBaseHelper helper;

    //프래그먼트 refresh
    public interface OnDateChanged{
         void refresh(ListviewAdapter adapter);

    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        System.out.println("Money 프래그먼트 onAttach");

        helper = new DataBaseHelper(getContext());
        adapter = new ListviewAdapter(helper);
        adapter.notifyDataSetChanged();

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.money, container, false);


        // 초기화
        listView = (ListView)view.findViewById(R.id.listview);
        total_outgoing_txt = (TextView)view.findViewById(R.id.total_outgoing_txt);
        belence_txt  = (TextView)view.findViewById(R.id.belence_txt);
        Button add_btn = (Button)view.findViewById(R.id.add_btn);
        fixed_money_edit = (TextView)view.findViewById(R.id.fixed_money_edit);


        listView.setAdapter(adapter);
        // 자동 스크롤
        listView.setSelection(adapter.getCount()-1);



        try{
            fixed_money_edit.setText(helper.getFixedMoney(MainActivity.dateRun.getNowYear_Month()));
        }catch (NullPointerException e){
            e.printStackTrace();
            Toast.makeText(getContext(),"null!!",Toast.LENGTH_SHORT).show();
        }


//        Bundle bundle = getArguments();
//        if(bundle!=null){
//            fixed_money_edit.setText(bundle.getString("fixedMoney"));
//        }



//        itemsArr = helper.getltems(MainActivity.dateRun.getNowYear_Month());




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
                .add(add_fragmant, "child")
                .addToBackStack(null);


        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                add_fragmant.show(child,"child");


//
            }
        });



        if(getArguments() != null){
            System.out.println("데이터 전달....");
            if(!add_fragmant.isHidden()){
                add_fragmant.dismiss();
            }
            if(getArguments().getInt("result")==1){
                changeMoney();
                adapter.addItem();
//                adapter.notifyDataSetChanged();

                getArguments().remove("result");
                getArguments().clear();

            }
        }






        return view;
    }


    public void changeMoney() {
        //세 자리 콤마 추가
        DecimalFormat decimalFormat = new DecimalFormat("###,###");

        helper = new DataBaseHelper(getContext());
        String moneyFormat = decimalFormat.format(
                Double.parseDouble(helper.getBelence(MainActivity.dateRun.getNowYear_Month())));

        String outgoingFormat = decimalFormat.format(
                Double.parseDouble(helper.getOutgoing(MainActivity.dateRun.getNowYear_Month())));

        belence_txt.setText(moneyFormat);
        total_outgoing_txt.setText(outgoingFormat);


        /*테스트 출력*/
        System.out.println(helper.getOutgoing(MainActivity.dateRun.getNowYear_Month()));

    }

    @Override
    public void onResume() {
        super.onResume();
        FragmentActivity activity = getActivity();


        if (activity != null) {
            String nowMonth = MainActivity.dateRun.getMonth();

            if(Integer.parseInt(nowMonth)<10){
                nowMonth = String.valueOf(Integer.parseInt(nowMonth));
            }
            //MainActivity의 메소드 사용하여 액션바 타이틀 변경.
            ((MainActivity) activity).setActionBarTitle(nowMonth+"월 수입 지출 현황");
        }
    }
}
