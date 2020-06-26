package com.example.monthly_household_account_book.money;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.monthly_household_account_book.Calendar;
import com.example.monthly_household_account_book.DataBaseHelper;
import com.example.monthly_household_account_book.MainActivity;
import com.example.monthly_household_account_book.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AddFragmant extends BottomSheetDialogFragment  {

    TextView cancle, confirm,date_txt,time_txt;
    RadioButton btn_income, btn_outgoing;
    EditText edit_money;
    DecimalFormat decimalFormat = new DecimalFormat("###,###");
    String money_result = "";
    int selYear,selMonth,selDay;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add, container, false);

        cancle = (TextView) view.findViewById(R.id.btn_cancle);
        confirm = (TextView) view.findViewById(R.id.btn_confirm);
        edit_money = (EditText) view.findViewById(R.id.edit_money);
        btn_income = (RadioButton) view.findViewById(R.id.btn_income);
        btn_outgoing = (RadioButton) view.findViewById(R.id.btn_outgoing);
        date_txt = (Button)view.findViewById(R.id.date_txt);
        time_txt = (TextView)view.findViewById(R.id.time_txt);

        //comma 추가를 위한 watcher 설정
        edit_money.addTextChangedListener(watcher);

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

        time_txt.setText(""+new SimpleDateFormat("HH:mm").format(new Date(System.currentTimeMillis())));

        cancle.setOnClickListener((v)->{

                Toast.makeText(getContext(), "취소를 눌렀습니다.", Toast.LENGTH_SHORT).show();
                bundle.putInt("result", 0);
                fragment.setArguments(bundle);
                dismiss();

        });
        confirm.setOnClickListener((v)-> {

            String kind = null;
                if (btn_income.isChecked())
                    kind = "수입";
                else if (btn_outgoing.isChecked())
                    kind = "지출";

            /* 데이터 베이스 테스트 코드 시작*/
                DataBaseHelper helper = new DataBaseHelper(getContext());
                String nowdate = MainActivity.dateRun.getId();

              String userSeleDate ="";

                if(selMonth<10)
                    userSeleDate += "0"+selMonth;
                else
                    userSeleDate += selMonth;
                if(selDay<10)
                    userSeleDate += "0"+selDay;
                else
                    userSeleDate += selDay;

                //String id, String date, String time, String category, String kind, String money, String memo
                helper.insert(nowdate,userSeleDate,MainActivity.dateRun.getTimeforUser(),null,kind,(edit_money.getText()).toString().replace(",",""),null);
            /*데이터베이스 테스트 코드 끝*/

                // Money 프래그먼트에 정보 전달
                bundle.putInt("result", 1);
                fragment.setArguments(bundle);
                //Money fagment reset
                fragment.getFragmentManager().beginTransaction().detach(fragment).attach(fragment).commit();
                dismiss();
        });
        date_txt.setOnClickListener((v)->{showDateDialog();});

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

    private void showDateDialog() {

        int nowy = Integer.parseInt(new SimpleDateFormat("yyyy").format(new Date(System.currentTimeMillis())));
        int nowm = Integer.parseInt(new SimpleDateFormat("MM").format(new Date(System.currentTimeMillis())));
        int nowd = Integer.parseInt(new SimpleDateFormat("dd").format(new Date(System.currentTimeMillis())));

        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                selYear = year;
                selMonth = month+1;
                selDay = dayOfMonth;
                date_txt.setText(selYear+"년 "+selMonth+"월 "+selDay+"일");

            }

        },nowy,nowm-1,nowd);

        datePickerDialog.show();

    }

}