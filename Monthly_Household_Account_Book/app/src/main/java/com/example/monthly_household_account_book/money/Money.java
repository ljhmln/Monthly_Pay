package com.example.monthly_household_account_book.money;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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
import androidx.appcompat.widget.AlertDialogLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.monthly_household_account_book.DataBaseHelper;
import com.example.monthly_household_account_book.MainActivity;
import com.example.monthly_household_account_book.R;

import java.text.DecimalFormat;


public class Money extends Fragment {

    private View view;
    private TextView belence_txt, fixed_money_edit, total_outgoing_txt;

    ListView listView;
    AddFragmant add_fragmant = new AddFragmant();
    DataBaseHelper helper;
    ListviewAdapter adapter;
    RefreshHandler refreshHandler;
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
        refreshHandler = new RefreshHandler(this);



        try{
            fixed_money_edit.setText(helper.getFixedMoney(MainActivity.dateRun.getNowYear_Month()));
        }catch (NullPointerException e){
            e.printStackTrace();
            Toast.makeText(getContext(),"null!!",Toast.LENGTH_SHORT).show();
        }

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
    boolean test = false;
    @Override
    public void onResume() {
        super.onResume();
        FragmentActivity activity = getActivity();

        if(test){
//            if(!this.isHidden()){
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.detach(this).attach(this).commit();
                test = false;
//            }
        }


        if (activity != null) {
            String nowMonth = MainActivity.dateRun.getMonth();

            if(Integer.parseInt(nowMonth)<10){
                nowMonth = String.valueOf(Integer.parseInt(nowMonth));
            }
            //MainActivity의 메소드 사용하여 액션바 타이틀 변경.
            ((MainActivity) activity).setActionBarTitle(nowMonth+"월 수입 지출 현황");
        }

//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
//                Object item = (Object)parent.getAdapter().getItem(position);
//                System.out.println("click item : "+item);
//                System.out.println("click Item Array id : "+adapter.getItemTest(position));
////                adapter.getItemTest(position);
//
////                Cursor item1 = (Cursor)
//
//            }
//        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getContext());
                dialogBuilder.setMessage("삭제 할까요?")
                        .setCancelable(false)
                        .setPositiveButton("삭제", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                helper.dropData(adapter.getItemTest(position));
                                adapter.addItem();
//                                Message m = refreshHandler.obtainMessage(1);
                                refreshHandler.sendEmptyMessage(1);

//                                test = true;
                                dialog.dismiss();
                            }
                        })
                        .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                dialogBuilder.show();
                return false;
            }
        });
    }

    public class RefreshHandler extends Handler{
        Fragment fragment;
        public RefreshHandler(Fragment fragment){
            this.fragment = fragment;
        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if(msg.what == 1){
                System.out.println("handle message");
                System.out.println(this.fragment.getClass().toString());
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.detach(this.fragment).attach(this.fragment).commit();
            }
        }
    }


}
