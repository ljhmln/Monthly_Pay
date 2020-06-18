package com.example.monthly_household_account_book.main_adapter;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.example.monthly_household_account_book.DataBaseHelper;
import com.example.monthly_household_account_book.MainActivity;
import com.example.monthly_household_account_book.Money;
import com.example.monthly_household_account_book.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Add_Activity extends BottomSheetDialogFragment  {

    TextView cancle, confirm;
    RadioButton btn_income, btn_outgoing;
    EditText edit_money;
    DecimalFormat decimalFormat = new DecimalFormat("###,###");
    String money_result = "";


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_add, container, false);

        cancle = (TextView) view.findViewById(R.id.btn_cancle);
        confirm = (TextView) view.findViewById(R.id.btn_confirm);
        edit_money = (EditText) view.findViewById(R.id.edit_money);
        btn_income = (RadioButton) view.findViewById(R.id.btn_income);
        btn_outgoing = (RadioButton) view.findViewById(R.id.btn_outgoing);
        //comma 추가를 위한 watcher 설정
        edit_money.addTextChangedListener(watcher);
        final String inputDate = "";
        final String inputTime = "";

        //정보 전달을 위한 객체 생성
        final Fragment fragment = getParentFragment();

        final Bundle bundle = new Bundle(1);

        //프래그먼트 레이아웃 숨김 잠금
        setCancelable(false);

        /*
        Bundle을 통한 Money Fragment에 정보 전달.
        result : 0 -> 취소 버튼
        result : 1 -> 추가 버튼
         */

        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getContext(), "취소를 눌렀습니다.", Toast.LENGTH_SHORT).show();
                bundle.putInt("result", 0);
                fragment.setArguments(bundle);
                dismiss();
            }
        });
        confirm.setOnClickListener(new View.OnClickListener() {
            String kind;
            @Override
            public void onClick(View v) {
                if (btn_income.isChecked())
                    kind = "수입";
                else if (btn_outgoing.isChecked())
                    kind = "지출";


                 /* 데이터 베이스 테스트 코드 시작*/

                DataBaseHelper helper = new DataBaseHelper(getContext());
                String nowdate = MainActivity.dateRun.getId();

                //String id, String date, String time, String category, String kind, String money, String memo
                helper.insert(nowdate,MainActivity.dateRun.getMonthDay(),MainActivity.dateRun.getTimeforUser(),null,kind,(edit_money.getText()).toString().replace(",",""),null);



                /*데이터베이스 테스트 코드 끝*/


                //Item Array에 저장
//                saveData(kind);
                //리스트 뷰에 동적으로 추가하기
//                getParentFragment().adapter.notifyDataSetChanged();

                // Money 프래그먼트에 정보 전달
                bundle.putInt("result", 1);
                fragment.setArguments(bundle);
                //Money fagment reset
                fragment.getFragmentManager().beginTransaction().detach(fragment).attach(fragment).commit();
                dismiss();
            }
        });

        //Bundle 비우기
        //필요 여부 테스트 해보기!!!!
        if (!bundle.isEmpty()) {
            bundle.remove("result");
            bundle.clear();
        }

        return view;
    }

    //    EditText 글자 변경 감지 객체
    TextWatcher watcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (!TextUtils.isEmpty(s.toString()) && !s.toString().equals(money_result)) {
                money_result = decimalFormat.format(Double.parseDouble(s.toString().replace(",", "")));
                edit_money.setText(money_result);
                edit_money.setSelection(money_result.length());
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };


    @Override
    public void onResume() {
        super.onResume();
    }

    //MainActivity 참조를 위한 객체 생성.
//    void saveData(String kind) {
//
//        FragmentActivity activity = getActivity();
//
//        Items item = new Items();
//        item.setKind(kind);
//        item.setCategory("테스트");
//        item.setDay("07");
//        item.setMonth("06");
//        String remoney = edit_money.getText().toString().replace(",", "");
//        item.setMoney(remoney);
//
//        ((MainActivity) activity).setItemsArr(item);
//    }

    // 시간 받기
    String getInputDate(String what) {

        String form ="";

        switch (what){
            case "time" :
                form ="HHmmss";
                break;
            case "date":
                form = "YYYYMMdd";
                break;
            default:
                Log.e("error","must Insert params  \"time\" or \"date\" into getNowDate() ");
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat(form);
        long now = System.currentTimeMillis();
        Date nowDate = new Date(now);


        String time;

        return time = dateFormat.format(nowDate);
    }

    String test() {

        SimpleDateFormat timeFormat = new SimpleDateFormat("HHmmss");
        SimpleDateFormat dateFormat = new SimpleDateFormat("YYYYMMdd");
        long now = System.currentTimeMillis();
        Date nowDate = new Date(now);

        String date = dateFormat.format(nowDate);
        String time = timeFormat.format(nowDate);

        return date+time ;
    }
}
