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

import com.example.monthly_household_account_book.MainActivity;
import com.example.monthly_household_account_book.Money;
import com.example.monthly_household_account_book.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class Add_Activity extends BottomSheetDialogFragment {
    DrawerLayout add_drawer;
    TextView cancle, confirm;
    RadioButton btn_income, btn_outgoing;
    EditText edit_money;
    DecimalFormat decimalFormat = new DecimalFormat("###,###");
    String money_result="";


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_add, container, false);

        cancle = (TextView)view.findViewById(R.id.btn_cancle);
        confirm = (TextView)view.findViewById(R.id.btn_confirm);
        edit_money = (EditText)view.findViewById(R.id.edit_money);
        btn_income = (RadioButton) view.findViewById(R.id.btn_income);
        btn_outgoing = (RadioButton) view.findViewById(R.id.btn_outgoing);
        setCancelable(false);


        /*
        result : 0 -> 취소
        result : 1 -> 추가
         */
         final Fragment fragment = getParentFragment();
        final Bundle bundle = new Bundle(1);


        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "취소를 눌렀습니다.",Toast.LENGTH_SHORT).show();
                bundle.putInt("result",0);
                fragment.setArguments(bundle);
                dismiss();
            }
        });
        confirm.setOnClickListener(new View.OnClickListener() {

            String kind;
            @Override
            public void onClick(View v) {

                if(btn_income.isChecked())
                    kind = "수입";
                else if(btn_outgoing.isChecked())
                    kind = "지출";

                //리스트 뷰 동적 추가
                saveData(kind);
                ((MainActivity)getActivity()).adapter.notifyDataSetChanged();

                bundle.putInt("result",1);
                fragment.setArguments(bundle);


                fragment.getFragmentManager().beginTransaction().detach(fragment).attach(fragment).commit();

                dismiss();
            }
        });

        if(!bundle.isEmpty()){
            bundle.remove("result");
            bundle.clear();
        }

        edit_money.addTextChangedListener(watcher);

        return view;
    }

//    EditText 글자 변경 감지
   TextWatcher watcher = new TextWatcher() {
       @Override
       public void beforeTextChanged(CharSequence s, int start, int count, int after) {

       }

       @Override
       public void onTextChanged(CharSequence s, int start, int before, int count) {
            if(!TextUtils.isEmpty(s.toString()) && !s.toString().equals(money_result)){
               money_result = decimalFormat.format(Double.parseDouble(s.toString().replace(",","")));
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

    void saveData(String kind) {
        FragmentActivity activity = getActivity();


        Items item = new Items();
            item.setKind(kind);
            item.setCategory("테스트");
            item.setDay("07");
            item.setMonth("06");
            String remoney = edit_money.getText().toString().replace(",","");
            item.setMoney(remoney);

        ((MainActivity) activity).setItemsArr(item);
    }
}
